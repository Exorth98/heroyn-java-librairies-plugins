package net.heroyn.heroynserverapi.cosmetics.minion;

import java.util.Iterator;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.utils.Flags;
import net.heroyn.heroynapi.utils.Flags.FlagsEnum;
import net.heroyn.heroynserverapi.cosmetics.minion.minions.MinerMinion;
import net.heroyn.heroynserverapi.listeners.CancelListener;
import net.heroyn.heroynserverapi.nms.other.NMS;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("unused")
public abstract class Minion
{
    protected Player player;
    protected PlayerInfo pp;
    protected ArmorStand minion;
    protected ItemStack head;
    private Color stuff;
	private String name;
    private Location location;
    private Vector vector;
    private boolean headOcupped;
    private boolean loop;
    public boolean visible;
    private float step;
    private double noMoveTime;
    private double movementSpeed;
    
    public Minion(final Player player, final ItemStack head, final Color stuff, final String name) {
        this.vector = new Vector(0, 0, 0);
        this.headOcupped = false;
        this.loop = false;
        this.visible = true;
        this.step = 0.0f;
        this.noMoveTime = 0.0;
        this.movementSpeed = 0.2;
        this.player = player;
        this.pp = PlayerInfo.instanceOf(player);
        this.name = name;
        this.head = head;
        this.stuff = stuff;
        if (this.pp.getMinion() != null) {
            if (this.pp.getMinion().getClass().equals(this.getClass())) {
                this.pp.getMinion().removeMinion();
                this.pp.setMinion(null);
                return;
            }
            this.pp.getMinion().removeMinion();
        }
        int n = 0;
        final Iterator<PlayerInfo> iterator = PlayerInfo.getPlayerInfos().values().iterator();
        this.setPet();
        Flags.setFlag(this.minion, FlagsEnum.GODMODE.getId());
        NMS.setSilent(minion);
        PlayerInfo.instanceOf(player).setMinion(this);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPet() {
        (this.location = this.player.getLocation().add(0.0, 0.5, 0.0)).setPitch(0.0f);
        this.location.setYaw(this.location.getYaw() + 50.0f);
        this.location.add(this.location.getDirection().normalize().multiply(1.5));
        this.location.setYaw(this.player.getLocation().getYaw());
        this.location.setPitch(this.player.getLocation().getPitch());
        (this.minion = (ArmorStand)this.player.getWorld().spawn(this.location, (Class)ArmorStand.class)).setSmall(true);
        this.minion.setGravity(false);
        this.minion.setArms(true);
        this.minion.setBasePlate(false);
        this.minion.setCanPickupItems(false);
        this.minion.setCollidable(false);
        this.addEquipements();
        CancelListener.getProtectedEntity().add((Entity)this.minion);
    }
    
    public void update() {
        if (this.minion != null) {
            final Location location = this.player.getLocation();
            location.setPitch(0.0f);
            location.setYaw(location.getYaw() + 50.0f);
            location.add(location.getDirection().normalize().multiply(3));
            final double distance = this.player.getEyeLocation().distance(this.location);
            final double distance2 = this.location.distance(location);
            if (UtilMath.random.nextDouble() > 0.5) {
                this.noMoveTime = System.currentTimeMillis() + UtilMath.randomRange(0.0, 2000.0);
            }
            if (this.player.getEyeLocation().distance(this.location) < 4.0) {
                this.movementSpeed = ((this.noMoveTime > System.currentTimeMillis()) ? Math.max(0.0, this.movementSpeed - 0.0075) : Math.min(0.1, this.movementSpeed + 0.0075));
            }
            else {
                this.noMoveTime = 0.0;
                this.movementSpeed = Math.min(0.15 + distance * 0.05, this.movementSpeed + 0.02);
            }
            this.vector.add(location.toVector().subtract(this.location.toVector()).multiply(0.2));
            if (this.vector.length() < 1.0) {
                this.movementSpeed *= this.vector.length();
            }
            this.vector = this.vector.normalize();
            if (distance2 > 0.1) {
                this.location.add(this.vector.clone().multiply(this.movementSpeed));
            }
            this.location.setY(this.player.getLocation().getY() + 0.5);
            this.minion.teleport(this.location);
            if (!this.headOcupped) {
                final Location location2 = this.player.getLocation();
                location2.setDirection(location2.clone().add(0.0, 0.5, 0.0).toVector().subtract(this.minion.getLocation().toVector()).normalize());
                this.location.setYaw(location2.getYaw());
                this.minion.setHeadPose(new EulerAngle(Math.toRadians(location2.getPitch()), 0.0, 0.0));
            }
            if (!this.pp.isMoving()) {
                if (!this.loop) {
                    this.step += 0.005;
                    this.minion.teleport(this.location.clone().add(0.0, (double)this.step, 0.0));
                    if (this.step >= 0.2f) {
                        this.loop = true;
                    }
                }
                else {
                    this.step -= 0.005;
                    this.minion.teleport(this.location.clone().add(0.0, (double)this.step, 0.0));
                    if (this.step <= 0.0f) {
                        this.loop = false;
                    }
                }
            }
        }
    }
    
    public abstract void Update();
    
    public void getVisible() {
    	if (!this.visible) {
    		this.hideMinion();
    	} else {
    		this.addEquipements();
    	}
    }
    
    private void addEquipements() {
        this.minion.setCustomName(this.player.getName());
        this.minion.setCustomNameVisible(true);
        this.minion.setHelmet(this.head);
        this.minion.setBoots(UtilItem.getColorArmor(Material.LEATHER_BOOTS, this.stuff));
        this.minion.setLeggings(UtilItem.getColorArmor(Material.LEATHER_LEGGINGS, this.stuff));
        this.minion.setChestplate(UtilItem.getColorArmor(Material.LEATHER_CHESTPLATE, this.stuff));
        if (this.getClass().equals(MinerMinion.class)) {
        	this.minion.setItemInHand(new UtilItem(Material.IRON_PICKAXE, (byte)0).getItem());
        }
    }
    
    private void hideMinion() {
         this.minion.setCustomNameVisible(false);
         this.minion.setHelmet(null);
         this.minion.setBoots(null);
         this.minion.setLeggings(null);
         this.minion.setChestplate(null);
         this.minion.setVisible(false);
         this.minion.setItemInHand(null);
    }
    
    public void removeMinion() {
        if (this.minion != null) {
            CancelListener.getProtectedEntity().remove(this.minion);
            this.minion.remove();
        }
    }
    
    public void setHeadOcupped(final boolean headOcupped) {
        this.headOcupped = headOcupped;
    }
    
    public boolean isHeadOcupped() {
        return this.headOcupped;
    }
    
    public static class CustomMinion
    {
        private Class<? extends Minion> minionClass;
        private ItemStack item;
        
        public CustomMinion(final ItemStack item, final Class<? extends Minion> minionClass) {
            this.minionClass = minionClass;
            this.item = item;
        }
        
        public Class<? extends Minion> getMinionClass() {
            return this.minionClass;
        }
        
        public ItemStack getItem() {
            return this.item;
        }
    }
}
