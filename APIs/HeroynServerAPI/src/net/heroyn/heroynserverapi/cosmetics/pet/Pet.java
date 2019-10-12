package net.heroyn.heroynserverapi.cosmetics.pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.heroyn.heroynapi.utils.Flags;
import net.heroyn.heroynapi.utils.Flags.FlagsEnum;
import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.pet.pets.DraculaPet;
import net.heroyn.heroynserverapi.nms.other.NMS;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.UtilItem;

public abstract class Pet implements Listener
{
    private static List<CustomPet> list;
    protected Player player;
    protected PlayerInfo pp;
    protected UpdateType updateTime;
    protected Entity entity;
    
    static {
    	Pet.list = new ArrayList<CustomPet>(
        		Arrays.asList(new CustomPet(new UtilItem(263, (byte) 0, "&8Dracula").getItem(), DraculaPet.class))); 
    }
    
    public static List<CustomPet> getList() {
        return Pet.list;
    }
    
    public Pet(final Player player) {
        this.player = player;
        this.pp = PlayerInfo.instanceOf(player);
        if (this.pp.getPet() != null) {
            if (this.pp.getPet().getClass().equals(this.getClass())) {
                this.pp.getPet().removePet();
                return;
            }
            this.pp.getPet().removePet();
        }
        int n = 0;
        final Iterator<PlayerInfo> iterator = PlayerInfo.getPlayerInfos().values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getPet() != null) {
                ++n;
            }
        }
        Flags.setFlag(getEntity(), FlagsEnum.GODMODE.getId());
        NMS.setSilent(entity);
        this.spawnPet();
        HeroynServerAPI.getInstance().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)HeroynServerAPI.getInstance());
        PlayerInfo.instanceOf(player).setPet(this);
    }
    
    public void spawnPet() {
        new BukkitRunnable() {
            public void run() {
            	Pet.this.setPet();
            }
        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 10L);
    }
    
    public void showDisplayName(final Entity entity, final String s) {
        entity.setCustomName(CC.colored(this.player.getName()));
        entity.setCustomNameVisible(true);
    }
    
    protected abstract void setPet();
    
    public void update() {
    }
    
    public void removePet() {
        HandlerList.unregisterAll((Listener)this);
        PlayerInfo.instanceOf(this.player).setPet(null);
    }
    
    public void setUpdateTime(final UpdateType updateTime) {
        this.updateTime = updateTime;
    }
    
    public UpdateType getUpdateTime() {
        return this.updateTime;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public static class CustomPet
    {
        private Class<? extends Pet> petClass;
        private ItemStack item;
        
        public CustomPet(final ItemStack item, final Class<? extends Pet> petClass) {
            this.petClass = petClass;
            this.item = item;
        }
        
        public Class<? extends Pet> getPetClass() {
            return this.petClass;
        }
        
        public ItemStack getItem() {
            return this.item;
        }
    }
}
