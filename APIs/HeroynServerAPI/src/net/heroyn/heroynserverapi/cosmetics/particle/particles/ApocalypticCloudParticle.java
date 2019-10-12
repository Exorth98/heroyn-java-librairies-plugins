package net.heroyn.heroynserverapi.cosmetics.particle.particles;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.particle.Particles;
import net.heroyn.heroynserverapi.utils.UtilLocation;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;
import net.heroyn.heroynserverapi.utils.particle.ColoredParticle;

public class ApocalypticCloudParticle extends Particles
{
    public ApocalypticCloudParticle(final Player player) {
        super(player, Particle.CLOUD, null);
    }
    
    @Override
    public void update() {
        final Location location = this.player.getLocation();
        for (int i = 0; i < 2; ++i) {
            UtilParticle.sendParticle(location.clone().add((double)UtilMath.randomRange(-1.0f, 1.0f), 2.5, (double)UtilMath.randomRange(-1.0f, 1.0f)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0f);
            UtilParticle.sendParticle(location.clone().add((double)UtilMath.randomRange(-1.0f, 1.0f), 2.7, (double)UtilMath.randomRange(-1.0f, 1.0f)), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0), 0.0f);
        }
        if (this.playerInfo.getGlobalTime() % 3 == 0) {
            UtilParticle.sendParticle(location.clone().add((double)UtilMath.randomRange(-0.8f, 0.8f), 2.5, (double)UtilMath.randomRange(-0.8f, 0.8f)), Particle.FLAME, 0, new Vector(0.0, -1.5, 0.0), 0.1f);
        }
        new ColoredParticle(Particle.REDSTONE, location.clone().add((double)UtilMath.randomRange(-0.8f, 0.8f), 2.5, (double)UtilMath.randomRange(-0.8f, 0.8f)), 255, 128, 0).send();
        if (this.playerInfo.getGlobalTime() % 120 == 0) {
            this.playThunder(location.clone().add((double)UtilMath.randomRange(-0.5f, 0.5f), 2.7, (double)UtilMath.randomRange(-0.5f, 0.5f)));
        }
    }
    
    private void playThunder(final Location location) {
        UtilLocation.getClosestPlayersFromLocation(location, 4.0).forEach(player -> {
            player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 0.05f, 0.0f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 1));
            return;
        });
        final Location clone = location.clone();
        Vector vector = UtilMath.getRandomVector();
        vector.setY(-Math.abs(vector.getY() - 2.0));
        final int randomRange = UtilMath.randomRange(20, 40);
        for (int i = 0; i < 50; ++i) {
            clone.add(vector.clone().multiply(i * 0.06f / 28.0f));
            new ColoredParticle(Particle.REDSTONE, clone, 255, 0, 0).send();
            if (i == randomRange || i == randomRange + 10) {
                vector = UtilMath.getRandomVector();
                vector.setY(-Math.abs(vector.getY()));
            }
        }
    }
}
