package net.heroyn.heroynserverapi.cosmetics.pet.pets;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.pet.Pet;
import net.heroyn.heroynserverapi.listeners.CancelListener;
import net.heroyn.heroynserverapi.nms.entity.EntityFollow;
import net.heroyn.heroynserverapi.nms.other.NMS;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class DraculaPet extends Pet
{
    private LivingEntity skeleton;
    private EntityFollow follow;
    private boolean invisible;
    
    public DraculaPet(final Player player) {
        super(player);
        this.invisible = false;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void setPet() {
        try {
           this.skeleton = (LivingEntity)this.player.getWorld().spawn(this.player.getLocation().add(1.0, 0.0, 1.0), (Class)WitherSkeleton.class);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        new BukkitRunnable() {
            public void run() {
                DraculaPet.this.skeleton.getEquipment().setItemInMainHand((ItemStack)null);
            }
        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 2L);
        CancelListener.getProtectedEntity().add((Entity)this.skeleton);
        this.follow = new EntityFollow(this.skeleton, this.player, 1.5);
        this.showDisplayName((Entity)this.skeleton, "&8Dracula");
        this.setUpdateTime(UpdateType.FASTEST);
    }
    
    @Override
    public void removePet() {
        super.removePet();
        CancelListener.getProtectedEntity().remove(this.skeleton);
        this.skeleton.remove();
        this.follow.unfollow(this.pp);
    }
    
    @Override
    public void update() {
        final Location location = this.skeleton.getLocation();
        final Vector lastLocation = NMS.getLastLocation((Entity)this.skeleton);
        if (location.getX() != lastLocation.getX() && location.getZ() != lastLocation.getZ()) {
            if (!this.invisible) {
                NMS.setInvisible((Entity)this.skeleton, true);
                this.invisible = true;
            }
            UtilParticle.sendParticle(this.skeleton.getLocation(), Particle.SMOKE_LARGE, 3, new Vector(0.2, 0.2, 0.2), 0.05f);
            UtilParticle.sendParticle(this.skeleton.getLocation().add(0.0, 1.0, 0.0), Particle.DRAGON_BREATH, 3, new Vector(0.2, 0.5, 0.2), 0.05f);
        }
        else {
            NMS.setYawRotation((Entity)this.skeleton, UtilMath.randomRange(-30, 30));
            NMS.setPitchHeadRotation((Entity)this.skeleton, UtilMath.randomRange(-30, 30));
            for (int i = 0; i < 5; ++i) {
                UtilParticle.sendParticle(this.skeleton.getLocation().add((double)UtilMath.randomRange(-1.0f, 1.0f), 0.0, (double)UtilMath.randomRange(-1.0f, 1.0f)), Particle.FLAME, 1, new Vector(0, 0, 0), 0.0f);
            }
            if (this.invisible) {
                NMS.setInvisible((Entity)this.skeleton, false);
                this.invisible = false;
            }
        }
    }
}
