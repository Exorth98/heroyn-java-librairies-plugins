package net.heroyn.heroynserverapi.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

import net.heroyn.heroynserverapi.utils.UtilLocation;

@SuppressWarnings("deprecation")
public class CancelListener implements Listener
{
    private static List<Item> protectedItems;
    private static List<Entity> protectedEntity;
    
    static {
        CancelListener.protectedItems = new ArrayList<Item>();
        CancelListener.protectedEntity = new ArrayList<Entity>();
    }
    
    @EventHandler
    public void unbreackableblock(final BlockBreakEvent blockBreakEvent) {
        if (UtilLocation.getUnbreakableBlocks().contains(blockBreakEvent.getBlock())) {
            blockBreakEvent.setCancelled(true);
        }
        
//        if (BlockToRestore.getBlocks().contains(blockBreakEvent.getBlock())) {
//            blockBreakEvent.setCancelled(true);
//        }
    }
    
    /**
     * 
     * @param inventoryClickEvent

    @EventHandler
    public void emote(final InventoryClickEvent inventoryClickEvent) {
        final ProdigyMood mood = ProdigyPlayer.instanceOf((Player)inventoryClickEvent.getWhoClicked()).getMood();
        final ProdigySuit suit = ProdigyPlayer.instanceOf((Player)inventoryClickEvent.getWhoClicked()).getSuit();
        if ((mood != null || suit != null) && inventoryClickEvent.getCurrentItem() != null && inventoryClickEvent.getCurrentItem().getItemMeta() != null && (inventoryClickEvent.getSlot() == 39 || inventoryClickEvent.getSlot() == 38 || inventoryClickEvent.getSlot() == 37 || inventoryClickEvent.getSlot() == 36)) {
            inventoryClickEvent.setCancelled(true);
            ((Player)inventoryClickEvent.getWhoClicked()).updateInventory();
        }
    }     */
    
    
    /**
     * 
     * @param inventoryDragEvent
   
    @EventHandler
    public void onDrag(final InventoryDragEvent inventoryDragEvent) {
        if (inventoryDragEvent.getCursor() != null) {
            final ProdigyMood mood = ProdigyPlayer.instanceOf((Player)inventoryDragEvent.getWhoClicked()).getMood();
            final ProdigySuit suit = ProdigyPlayer.instanceOf((Player)inventoryDragEvent.getWhoClicked()).getSuit();
            if (suit != null && inventoryDragEvent.getCursor().getItemMeta().hasDisplayName() && inventoryDragEvent.getCursor().getItemMeta().getDisplayName().equals(suit.getCustomSuit().getItem().getItemMeta().getDisplayName())) {
                inventoryDragEvent.setCancelled(true);
                ((Player)inventoryDragEvent.getWhoClicked()).updateInventory();
            }
            if (mood != null && inventoryDragEvent.getCursor().getItemMeta().hasDisplayName() && inventoryDragEvent.getCursor().getItemMeta().getDisplayName().equals(String.valueOf(CC.red) + "-")) {
                inventoryDragEvent.setCancelled(true);
                ((Player)inventoryDragEvent.getWhoClicked()).updateInventory();
            }
        }
    }  */
    
    @EventHandler
    public void unexplosableblock(final BlockExplodeEvent blockExplodeEvent) {
        if (UtilLocation.getUnbreakableBlocks().contains(blockExplodeEvent.getBlock())) {
            blockExplodeEvent.setCancelled(true);
        }
    }
    
	@EventHandler
    public void pickupItem(final PlayerPickupItemEvent playerPickupItemEvent) {
        if (CancelListener.protectedItems.contains(playerPickupItemEvent.getItem())) {
            playerPickupItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractAtEntityEvent playerInteractAtEntityEvent) {
        if (CancelListener.protectedEntity.contains(playerInteractAtEntityEvent.getRightClicked())) {
            playerInteractAtEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerInteractAtEntity(final PlayerInteractEntityEvent playerInteractEntityEvent) {
        if (CancelListener.protectedEntity.contains(playerInteractEntityEvent.getRightClicked())) {
            playerInteractEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void entityTarget(final EntityTargetLivingEntityEvent entityTargetLivingEntityEvent) {
        if (CancelListener.protectedEntity.contains(entityTargetLivingEntityEvent.getEntity())) {
            entityTargetLivingEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void entitySheared(final PlayerShearEntityEvent playerShearEntityEvent) {
        if (playerShearEntityEvent.getEntity() instanceof Sheep && CancelListener.protectedEntity.contains(playerShearEntityEvent.getEntity())) {
            playerShearEntityEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void entityCombus(final EntityCombustEvent entityCombustEvent) {
        if (CancelListener.protectedEntity.contains(entityCombustEvent.getEntity())) {
            entityCombustEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onInteract(final EntityInteractEvent entityInteractEvent) {
        if (CancelListener.protectedEntity.contains(entityInteractEvent.getEntity())) {
            entityInteractEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent entityDamageEvent) {
        if (CancelListener.protectedEntity.contains(entityDamageEvent.getEntity()) || CancelListener.protectedItems.contains(entityDamageEvent.getEntity())) {
            entityDamageEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void blockFormEvent(final EntityBlockFormEvent entityBlockFormEvent) {
        if (CancelListener.protectedEntity.contains(entityBlockFormEvent.getEntity())) {
            entityBlockFormEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void blockchange(final EntityChangeBlockEvent entityChangeBlockEvent) {
        if (CancelListener.protectedEntity.contains(entityChangeBlockEvent.getEntity())) {
            entityChangeBlockEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void hopper(final InventoryPickupItemEvent inventoryPickupItemEvent) {
        if (CancelListener.protectedItems.contains(inventoryPickupItemEvent.getItem())) {
            inventoryPickupItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntitySpawn(final CreatureSpawnEvent creatureSpawnEvent) {
        if (creatureSpawnEvent.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            creatureSpawnEvent.setCancelled(false);
        }
    }
    
    @EventHandler
    public void portal(final EntityPortalEvent entityPortalEvent) {
        if (CancelListener.protectedItems.contains(entityPortalEvent.getEntity()) || CancelListener.protectedEntity.contains(entityPortalEvent.getEntity())) {
            entityPortalEvent.setCancelled(true);
        }
    }
    
    public static List<Entity> getProtectedEntity() {
        return CancelListener.protectedEntity;
    }
    
    public static List<Item> getProtectedItems() {
        return CancelListener.protectedItems;
    }
}
