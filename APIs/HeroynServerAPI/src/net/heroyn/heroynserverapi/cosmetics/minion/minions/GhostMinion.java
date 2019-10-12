package net.heroyn.heroynserverapi.cosmetics.minion.minions;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.minion.Minion;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.Head;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class GhostMinion extends Minion
{
    private int time;
    
    public GhostMinion(final Player player) {
        super(player, UtilItem.getSkull(Head.GHOST_REDEYE.getTexture(), CC.colored("&fGhost")), Color.GRAY, "&fGhost");
        this.time = 0;
    }
    
    @Override
    public void setPet() {
        super.setPet();
        this.minion.setVisible(false);
        this.minion.setBoots((ItemStack)null);
        this.minion.setLeggings((ItemStack)null);
    }
    
    @Override
    public void Update() {
        UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.1, 0.0), Particle.EXPLOSION_NORMAL, 1, new Vector(0, 0, 0), 0.0f);
        if (this.pp.getGlobalTime() % 10 == 0 && UtilMath.random.nextDouble() > 0.98) {
            this.minion.getWorld().playSound(this.minion.getLocation(), Sound.ENTITY_WOLF_GROWL, 0.1f, 0.0f);
            if (!this.isHeadOcupped()) {
                this.setHeadOcupped(true);
            }
        }
        if (this.isHeadOcupped()) {
            this.minion.setHeadPose(new EulerAngle(Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30))));
            UtilParticle.sendParticle(this.minion.getLocation().add(0.0, 0.5, 0.0), Particle.CLOUD, 0, new Vector(UtilMath.randomRange(-0.05f, 0.05f), -0.05f, UtilMath.randomRange(-0.05f, 0.05f)), 1.0f);
            ++this.time;
            if (this.time >= 60) {
                this.setHeadOcupped(false);
                this.time = 0;
            }
        }
        super.update();
    }
}
