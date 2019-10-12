package net.heroyn.heroynserverapi.cosmetics.gadget;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.cooldown.Cooldown;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public abstract class IGadget implements Listener
{
    private static HeroynServerAPI heroynApi;
    protected Player player;
    protected PlayerInfo pp;
    private ItemStack item;
    private int cooldownTime;
    
    public void init() {
    	IGadget.heroynApi = HeroynServerAPI.getInstance();
    	register();
        }
        
    public IGadget(final Player player, final ItemStack item, final int cooldownTime) {
    	init();
        this.player = player;
        this.pp = PlayerInfo.instanceOf(player);
        this.item = item;
        this.cooldownTime = cooldownTime;
        if (this.pp.getGadget() != null) {
            if (this.pp.getGadget().getClass().equals(this.getClass())) {
                this.pp.getGadget().remove();
                return;
            }
            this.pp.getGadget().remove();
        }
        player.getInventory().setItem(4, item);
        IGadget.heroynApi.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)HeroynServerAPI.getInstance());
        PlayerInfo.instanceOf(player).setGadget(this);
    }
    
    public boolean tryStartGadget() {
        return true;
    }
    
    public abstract void start();
    
    public void tryStartCooldownGadget() {
        if (!this.tryStartGadget()) {
            return;
        }
        if (this.cooldownTime > 0) {
            if (Cooldown.getCooldowns().containsKey(this.player.getUniqueId())) {
                final List<Cooldown> list = Cooldown.getCooldowns().get(this.player.getUniqueId());
                list.stream().filter(cooldown -> cooldown.getGadget().equals(this.getClass())).findFirst().ifPresent(cooldown2 -> {
                    if (cooldown2.getTime() <= 0) {
                        this.start();
                    }
                    return;
                });
                if (!list.stream().filter(cooldown3 -> cooldown3.getGadget().equals(this.getClass())).findFirst().isPresent()) {
                    Cooldown.getCooldowns().get(this.player.getUniqueId()).add(new Cooldown(this.getClass(), this.cooldownTime, this.item));
                    this.start();
                }
            }
            else {
                Cooldown.getCooldowns().putIfAbsent(this.player.getUniqueId(), new ArrayList<Cooldown>());
                Cooldown.getCooldowns().get(this.player.getUniqueId()).add(new Cooldown(this.getClass(), this.cooldownTime, this.item));
                this.start();
            }
        }
        else {
            this.start();
        }
    }
    
    public abstract void stop();
    
    public void remove() {
        this.player.getInventory().remove(this.item);
        this.stop();
        HandlerList.unregisterAll((Listener)this);
        PlayerInfo.instanceOf(this.player).setGadget(null);
    }
    
    public void update() {
    }
    
    @EventHandler
    public void updateEvent(final UpdaterEvent updaterEvent) {
        if (updaterEvent.getType() == UpdateType.TICK) {
            this.update();
        }
    }
    
    @EventHandler
    public void breakBlock(final BlockBreakEvent blockBreakEvent) {
        if (blockBreakEvent.getPlayer().equals(this.player) && blockBreakEvent.getPlayer().getInventory().getItemInMainHand().equals((Object)this.item)) {
            blockBreakEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerInteractGadget(final PlayerInteractEvent playerInteractEvent) {
        if (playerInteractEvent.getItem() != null && (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) && playerInteractEvent.getItem().equals((Object)this.item) && playerInteractEvent.getPlayer().equals(this.player)) {
            this.tryStartCooldownGadget();
            playerInteractEvent.setCancelled(true);
            playerInteractEvent.getPlayer().updateInventory();
        }
    }
    
    @EventHandler
    public void onDrop(final PlayerDropItemEvent playerDropItemEvent) {
        if (playerDropItemEvent.getPlayer().equals(this.player) && playerDropItemEvent.getItemDrop().getItemStack().equals((Object)this.item)) {
            playerDropItemEvent.setCancelled(true);
            playerDropItemEvent.getPlayer().updateInventory();
        }
    }
    
    @EventHandler
    public void itemframe(final PlayerInteractEntityEvent playerInteractEntityEvent) {
        if (playerInteractEntityEvent.getPlayer().equals(this.player)) {
            final Player player = playerInteractEntityEvent.getPlayer();
            if (playerInteractEntityEvent.getRightClicked() instanceof ItemFrame && player.getInventory().getItemInMainHand().equals((Object)this.item)) {
                playerInteractEntityEvent.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onClick(final InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getWhoClicked().equals(this.player)) {
            if (inventoryClickEvent.getCurrentItem() == null) {
                return;
            }
            if (inventoryClickEvent.getCurrentItem().equals((Object)this.item)) {
                ((Player)inventoryClickEvent.getWhoClicked()).updateInventory();
                inventoryClickEvent.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onDrag(final InventoryDragEvent inventoryDragEvent) {
        if (((Player)inventoryDragEvent.getWhoClicked()).equals(this.player) && inventoryDragEvent.getCursor() != null && inventoryDragEvent.getCursor().equals((Object)this.item)) {
            inventoryDragEvent.setCancelled(true);
            ((Player)inventoryDragEvent.getWhoClicked()).updateInventory();
            inventoryDragEvent.getWhoClicked().closeInventory();
        }
    }
    
    private void register() {
        HeroynServerAPI.getInstance().getServer().getPluginManager().registerEvents((Listener)new Listener() {
            @EventHandler
            public void swap(final PlayerSwapHandItemsEvent playerSwapHandItemsEvent) {
                if (playerSwapHandItemsEvent.getPlayer().equals(IGadget.this.player) && (playerSwapHandItemsEvent.getMainHandItem().equals((Object)IGadget.this.item) || playerSwapHandItemsEvent.getOffHandItem().equals((Object)IGadget.this.item))) {
                    playerSwapHandItemsEvent.setCancelled(true);
                }
            }
        }, (Plugin)HeroynServerAPI.getInstance());
    }
    
    public static class CustomGadget
    {
        private Class<? extends IGadget> gadgetClass;
        private ItemStack item;
        
        public CustomGadget(final ItemStack item, final Class<? extends IGadget> gadgetClass) {
            this.gadgetClass = gadgetClass;
            this.item = item;
        }
        
        public Class<? extends IGadget> getGadgetClass() {
            return this.gadgetClass;
        }
        
        public ItemStack getItem() {
            return this.item;
        }
    }
}
