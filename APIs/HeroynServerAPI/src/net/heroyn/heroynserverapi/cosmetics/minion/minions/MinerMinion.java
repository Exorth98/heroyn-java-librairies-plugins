package net.heroyn.heroynserverapi.cosmetics.minion.minions;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.minion.Minion;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.Head;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

@SuppressWarnings("deprecation")
public class MinerMinion extends Minion
{
    private ArmorStand diamond;
    private int time;
    private boolean loop;
    
    public MinerMinion(final Player player) {
        super(player, UtilItem.getSkull(Head.MINER.getTexture(), CC.colored("&6Miner")), Color.MAROON, "&6Miner");
        this.time = 0;
        this.loop = true;
        this.minion.setItemInHand(new UtilItem(Material.IRON_PICKAXE, (byte)0).getItem());
    }
    
    @SuppressWarnings({"unchecked", "rawtypes" })
	@Override
    public void Update() {
        super.update();
        UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.5, 0.0), Particle.CRIT, 1, new Vector(UtilMath.randomRange(-0.05f, 0.05f), UtilMath.randomRange(-0.05f, 0.05f), UtilMath.randomRange(-0.05f, 0.05f)), 0.5f);
        UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.5, 0.0), Particle.SMOKE_NORMAL, 1, new Vector(UtilMath.randomRange(-0.05f, 0.05f), UtilMath.randomRange(-0.05f, 0.05f), UtilMath.randomRange(-0.05f, 0.05f)), 0.05f);
        if (this.pp.getGlobalTime() % 10 == 0 && !this.pp.isMoving() && UtilMath.random.nextDouble() > 0.97 && this.diamond == null) {
            this.setHeadOcupped(true);
            this.minion.getWorld().playSound(this.minion.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 0.5f, 0.0f);
            final Location location = this.minion.getLocation();
            location.setPitch(0.0f);
            
            (this.diamond = (ArmorStand)this.player.getWorld().spawn((this.minion.getLocation().add(location.getDirection()).subtract(0, 0.5, 0)), (Class)ArmorStand.class)).setSmall(true);
            UtilParticle.sendParticle(this.diamond.getLocation().add(0.0, 0.5, 0.0), Particle.END_ROD, 2, new Vector(0.05, 0.05, 0.05), 0.05f);
            this.diamond.setVisible(false);
            this.diamond.setSmall(true);
            this.diamond.setCollidable(false);
            this.diamond.setGravity(false);
            this.diamond.setHelmet(UtilItem.getSkull(Head.DIAMOND_ORE.getTexture()));
            this.diamond.setRightArmPose(new EulerAngle(Math.toRadians(325.0), Math.toRadians(276.0), 0.0));
        }
        if (this.diamond != null) {
            final Location location2 = this.minion.getLocation();
            location2.setPitch(40.0f);
            this.diamond.teleport(this.minion.getLocation().add(location2.getDirection().multiply(0.5)).subtract(0.0, 0.4, 0.0));
            final Location add = this.diamond.getLocation().clone().add(0.0, 0.5, 0.0);
            add.setDirection(add.toVector().subtract(this.minion.getLocation().toVector()).normalize());
            this.minion.setHeadPose(new EulerAngle(Math.toRadians(add.getPitch() - 20.0f), 0.0, 0.0));
            if (this.time % 2 == 0) {
                for (int i = 0; i < 5; ++i) {
                    UtilParticle.sendParticle((this.minion.getLocation().add(this.minion.getLocation().getDirection()).subtract(0, 0.5, 0)), Particle.BLOCK_CRACK, 0, new Vector(0, 0, 0), new MaterialData(Material.DIAMOND_ORE, (byte)0));
                }
                if (this.loop) {
                    location2.getWorld().playSound(location2, Sound.BLOCK_STONE_BREAK, 0.1f, 1.0f);
                    this.minion.setRightArmPose(new EulerAngle(Math.toRadians(294.0), Math.toRadians(333.0), Math.toRadians(7.0)));
                    this.minion.setLeftArmPose(new EulerAngle(Math.toRadians(290.0), Math.toRadians(40.0), Math.toRadians(0.0)));
                    this.loop = false;
                }
                else {
                    this.minion.setRightArmPose(new EulerAngle(Math.toRadians(318.0), Math.toRadians(333.0), Math.toRadians(7.0)));
                    this.minion.setLeftArmPose(new EulerAngle(Math.toRadians(318.0), Math.toRadians(40.0), Math.toRadians(0.0)));
                    this.loop = true;
                }
            }
            ++this.time;
            if (this.time >= 60) {
                location2.getWorld().playSound(location2, Sound.BLOCK_ANVIL_LAND, 0.1f, 1.0f);
                UtilParticle.sendParticle(this.diamond.getLocation().add(0.0, 0.4, 0.0), Particle.CLOUD, 5, new Vector(0.05, 0.05, 0.05), 0.05f);
                this.time = 0;
                this.diamond.remove();
                this.diamond = null;
                this.setHeadOcupped(false);
                this.minion.setRightArmPose(new EulerAngle(0.0, 0.0, 0.0));
                this.minion.setLeftArmPose(new EulerAngle(0.0, 0.0, 0.0));
            }
        }
    }
    
    @Override
    public void removeMinion() {
        super.removeMinion();
        if (this.diamond != null) {
            this.diamond.remove();
        }
    }
}
