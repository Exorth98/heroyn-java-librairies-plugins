package net.heroyn.heroynserverapi.cosmetics.minion.minions;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.minion.Minion;
import net.heroyn.heroynserverapi.nms.entity.ReflectedArmorStand;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.Head;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class AstronautMinion extends Minion
{
    private ReflectedArmorStand earth;
    private int time;
    
    public AstronautMinion(final Player player) {
        super(player, UtilItem.getSkull(Head.ASTRONAUT.getTexture(), CC.colored("&8Astronaut")), Color.WHITE, "&8Astronaut");
        this.time = 0;
    }
    
    @Override
    public void Update() {
        super.update();
        UtilParticle.sendParticle(this.minion.getLocation().subtract(0.0, 0.1, 0.0), Particle.CLOUD, 0, new Vector(UtilMath.randomRange(-0.05f, 0.05f), -0.05f, UtilMath.randomRange(-0.05f, 0.05f)), 1.0f);
        UtilParticle.sendParticle(this.minion.getLocation().subtract(0.0, 0.1, 0.0), Particle.END_ROD, 0, new Vector(UtilMath.randomRange(-0.05f, 0.05f), -0.05f, UtilMath.randomRange(-0.05f, 0.05f)), 1.0f);
        if (this.pp.getGlobalTime() % 10 == 0 && !this.pp.isMoving() && UtilMath.random.nextDouble() > 0.98 && this.earth == null) {
            this.setHeadOcupped(true);
            this.minion.getWorld().playSound(this.minion.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 0.5f, 0.0f);
            final Location location = this.minion.getLocation();
            location.setPitch(-50.0f);
            this.earth = new ReflectedArmorStand(this.minion.getLocation().add(location.getDirection()));
            UtilParticle.sendParticle(this.earth.getLocation().add(0.0, 0.5, 0.0), Particle.END_ROD, 2, new Vector(0.05, 0.05, 0.05), 0.05f);
            this.earth.setSmall(true);
            this.earth.setVisible(false);
            this.earth.setEquipment(0, UtilItem.getSkull(Head.EARTH.getTexture()));
            this.earth.setRightArmPose(new EulerAngle(Math.toRadians(325.0), Math.toRadians(276.0), 0.0));
            this.earth.spawnArmorStand();
            this.minion.setRightArmPose(new EulerAngle(222.0, 16.0, 0.0));
            this.minion.setLeftArmPose(new EulerAngle(224.0, 345.0, 0.0));
        }
        if (this.earth != null) {
            final Location location2 = this.minion.getLocation();
            location2.setPitch(-50.0f);
            this.earth.teleport(this.minion.getLocation().add(location2.getDirection()));
            final Location add = this.earth.getLocation().clone().add(0.0, 1.0, 0.0);
            add.setDirection(add.toVector().subtract(this.minion.getLocation().toVector()).normalize());
            this.minion.setHeadPose(new EulerAngle(Math.toRadians(add.getPitch() + 40.0f), 0.0, 0.0));
            ++this.time;
            if (this.time >= 100) {
                this.time = 0;
                this.earth.remove();
                this.earth = null;
                this.setHeadOcupped(false);
                this.minion.setRightArmPose(new EulerAngle(0.0, 0.0, 0.0));
                this.minion.setLeftArmPose(new EulerAngle(0.0, 0.0, 0.0));
            }
        }
    }
    
    @Override
    public void removeMinion() {
        super.removeMinion();
        if (this.earth != null) {
            this.earth.remove();
        }
    }
}
