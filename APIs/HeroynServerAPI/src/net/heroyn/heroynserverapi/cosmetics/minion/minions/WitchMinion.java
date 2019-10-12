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

public class WitchMinion extends Minion
{
    private ReflectedArmorStand cauldron;
    private int time;
    
    public WitchMinion(final Player player) {
    	 super(player, UtilItem.getSkull(Head.WITCH.getTexture(), CC.colored("§dWitch")), Color.PURPLE, "§dWitch");
        this.time = 0;
    }
    
    @Override
    public void Update() {
        if (this.pp.getGlobalTime() % 3 == 0) {
            UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.5, 0.0), Particle.SPELL_WITCH, 1, new Vector(0.2, 0.2, 0.2), 0.0f);
        }
        if (this.pp.getGlobalTime() % 10 == 0 && !this.pp.isMoving() && UtilMath.random.nextDouble() > 0.98 && this.cauldron == null) {
            this.setHeadOcupped(true);
            this.minion.getWorld().playSound(this.minion.getLocation(), Sound.ENTITY_WITCH_AMBIENT, 0.5f, 0.0f);
            final Location location = this.minion.getLocation();
            location.setPitch(40.0f);
            this.cauldron = new ReflectedArmorStand(this.minion.getLocation().add(location.getDirection().multiply(0.5)).subtract(0.0, 0.4, 0.0));
            UtilParticle.sendParticle(this.cauldron.getLocation().add(0.0, 0.5, 0.0), Particle.DRAGON_BREATH, 2, new Vector(0.05, 0.05, 0.05), 0.05f);
            this.cauldron.setSmall(true);
            this.cauldron.setVisible(false);
            this.cauldron.setEquipment(5, UtilItem.getSkull(Head.CAULDRON.getTexture()));
            this.cauldron.setRightArmPose(new EulerAngle(Math.toRadians(325.0), Math.toRadians(276.0), 0.0));
            this.cauldron.spawnArmorStand();
            this.minion.setRightArmPose(new EulerAngle(Math.toRadians(318.0), Math.toRadians(329.0), 0.0));
            this.minion.setLeftArmPose(new EulerAngle(Math.toRadians(318.0), Math.toRadians(23.0), 0.0));
        }
        if (this.cauldron != null) {
            UtilParticle.sendParticle(this.minion.getLocation(), Particle.END_ROD, 1, new Vector(0.2, 0.0, 0.2), 0.0f);
            UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.5, 0.0), Particle.DRAGON_BREATH, 1, new Vector(0.2, 0.2, 0.2), 0.05f);
            UtilParticle.sendParticle(this.cauldron.getLocation().add(0.0, 1.5, 0.0), Particle.ENCHANTMENT_TABLE, 5, new Vector(0, 0, 0), 1.0f);
            if (this.pp.getGlobalTime() % 5 == 0) {
                this.minion.getWorld().playSound(this.minion.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 0.1f, 0.0f);
            }
            final Location location2 = this.minion.getLocation();
            location2.setPitch(40.0f);
            this.cauldron.teleport(this.minion.getLocation().add(location2.getDirection().multiply(0.5)).subtract(0.0, 0.4, 0.0));
            final Location location3 = this.cauldron.getLocation();
            location3.setDirection(location3.toVector().subtract(this.minion.getLocation().toVector()).normalize());
            this.minion.setHeadPose(new EulerAngle(Math.toRadians(location3.getPitch() - 20.0f), Math.toRadians(UtilMath.randomRange(-10, 10)), Math.toRadians(UtilMath.randomRange(-10, 10))));
            ++this.time;
            if (this.time >= 100) {
                this.time = 0;
                this.cauldron.remove();
                this.cauldron = null;
                this.setHeadOcupped(false);
                this.minion.setRightArmPose(new EulerAngle(0.0, 0.0, 0.0));
                this.minion.setLeftArmPose(new EulerAngle(0.0, 0.0, 0.0));
            }
        }
        super.update();
    }
    
    @Override
    public void setPet() {
        super.setPet();
    }
    
    @Override
    public void removeMinion() {
        super.removeMinion();
        if (this.cauldron != null) {
            this.cauldron.remove();
        }
    }


}
