package net.heroyn.heroynserverapi.threedimensions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.nms.entity.ReflectedArmorStand;
import net.heroyn.heroynserverapi.nms.entity.ReflectedLivingEntity;
import net.heroyn.heroynserverapi.utils.CC;

public class Item3D
{
    private Player player;
    private Location location;
    private ReflectedArmorStand itemDisplay;
    private ReflectedArmorStand displayName;
    private ReflectedLivingEntity selector;
    private String name;
    private ItemStack item;
    private Location dir;
    private boolean spawned;
    private boolean enable;
    private float yawRotation;
    
    public Item3D(final Player player, final ItemStack item, final String name) {
        this.spawned = false;
        this.yawRotation = 0.0f;
        this.player = player;
        this.item = item;
        this.name = name;
        this.enable = true;
        (this.dir = player.getLocation().toVector().add(player.getLocation().getDirection().multiply(1.4)).toLocation(player.getWorld(), player.getLocation().getYaw(), 0.0f)).subtract(0.0, 1.5, 0.0);
        this.displayName = new ReflectedArmorStand(this.dir.clone().add(0.0, 0.30000001192092896, 0.0)).setVisible(false).setDisplayName(CC.colored(name));
        this.itemDisplay = new ReflectedArmorStand(this.dir).setVisible(false).setEquipment(5, item);
    }
    
    public Item3D(final Player player, final ItemStack item) {
        this.spawned = false;
        this.yawRotation = 0.0f;
        this.player = player;
        this.item = item;
        this.name = item.getItemMeta().getDisplayName();
        this.enable = true;
        (this.dir = player.getLocation().toVector().add(player.getLocation().getDirection().multiply(1.4)).toLocation(player.getWorld(), player.getLocation().getYaw(), 0.0f)).subtract(0.0, 1.5, 0.0);
        this.displayName = new ReflectedArmorStand(this.dir.clone().add(0.0, 0.30000001192092896, 0.0)).setVisible(false).setDisplayName(CC.colored(this.name));
        this.itemDisplay = new ReflectedArmorStand(this.dir).setVisible(false).setEquipment(5, item);
    }
    
    public void setSmall() {
        this.itemDisplay.setSmall(true);
    }
    
    @SuppressWarnings("deprecation")
	public Item3D setPosition(final Location location, final float n) {
        Vector multiply = null;
        this.itemDisplay.setRotation(n, 0.0f);
        this.yawRotation = n;
        if (this.item.getType().isBlock() || this.item.getType() == Material.LEGACY_SKULL_ITEM) {
            this.itemDisplay.setSmall(true);
        }
        else {
            final Location clone = location.clone();
            clone.setDirection(this.player.getLocation().toVector().subtract(clone.toVector()).normalize());
            clone.setPitch(0.0f);
            multiply = clone.getDirection().multiply(0.3);
        }
        this.itemDisplay.spawnArmorStand(this.player);
        this.displayName.spawnArmorStand(this.player);
        final Location location2 = this.itemDisplay.isSmall() ? location.clone().add(0.0, 1.3, 0.0) : location;
        if (this.item.getType() == Material.LEGACY_SAPLING) {
            location2.subtract(0.0, 0.3, 0.0);
        }
        else if (this.item.getType() == Material.BARRIER) {
            location2.subtract(0.0, 0.2, 0.0);
        }
        this.itemDisplay.teleport(this.player, (multiply != null) ? location2.clone().add(multiply) : location2);
        this.displayName.teleport(this.player, location.clone().add(0.0, 0.30000001192092896, 0.0));
        (this.selector = new ReflectedLivingEntity(location.clone().add(0.0, 1.8, 0.0), EntityType.SLIME)).setSize(2);
        this.selector.setVisible(false);
        this.selector.spawnLivingEntity(this.player);
        this.selector.setYawRotation(n);
        this.location = location;
        this.spawned = true;
        return this;
    }
    
    public void move(final boolean b) {
        if (b) {
            this.itemDisplay.faketeleport(this.player, this.itemDisplay.getLocation());
            this.selector.teleport(this.selector.getLocation(), this.player);
            this.displayName.teleport(this.player, this.displayName.getLocation());
        }
        else {
            final Location clone = this.getLocation().clone();
            clone.setDirection(this.player.getLocation().toVector().subtract(clone.toVector()).normalize());
            clone.setPitch(0.0f);
            final Vector multiply = clone.getDirection().multiply(0.3);
            this.itemDisplay.faketeleport(this.player, this.itemDisplay.getLocation().clone().add(multiply));
            this.displayName.faketeleport(this.player, this.displayName.getLocation().clone().add(multiply));
            this.selector.teleport(this.selector.getLocation().clone().add(multiply), this.player);
        }
    }
    
    public void teleport(final Location location) {
        this.itemDisplay.teleport(this.player, this.itemDisplay.isSmall() ? location.clone().add(0.0, 1.3, 0.0) : location);
        this.displayName.teleport(this.player, location.clone().add(0.0, 0.30000001192092896, 0.0));
        this.selector.teleport(location.clone().add(0.0, 1.8, 0.0), this.player);
        this.location = location;
    }
    
    public int getID() {
        return (this.selector == null) ? -1 : this.selector.getID();
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void remove() {
        if (this.itemDisplay != null) {
            this.itemDisplay.remove();
        }
        if (this.displayName != null) {
            this.displayName.remove();
        }
        if (this.selector != null) {
            this.selector.removeLivingEntity();
        }
        this.spawned = false;
    }
    
    public ReflectedArmorStand getItemDisplay() {
        return this.itemDisplay;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isSpawned() {
        return this.spawned;
    }
    
    public boolean isEnable() {
        return this.enable;
    }
    
    public ReflectedLivingEntity getSelector() {
        return this.selector;
    }
    
    public ReflectedArmorStand getDisplayName() {
        return this.displayName;
    }
    
    public float getYawRotation() {
        return this.yawRotation;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
}
