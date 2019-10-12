package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilLocation;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class TsunamiGadget extends IGadget
{
    public TsunamiGadget(final Player player) {
        super(player, new UtilItem(351, (byte) 4, "&3Tsunami").getItem(), 10);
    }
    
    @Override
    public void start() {
        final Location subtract = this.player.getLocation().subtract(0.0, 0.0, 0.0);
        subtract.setPitch(0.0f);
        new BukkitRunnable() {
            int time = 0;
            Location valloc;
            @SuppressWarnings("deprecation")
			public void run() {
                if (subtract.getBlock().getType() != Material.AIR && subtract.getBlock().getType().isSolid()) {
                    subtract.add(0.0, 1.0, 0.0);
                }
                if (subtract.clone().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR && subtract.clone().getBlock().getType().getId() != 43 && subtract.clone().getBlock().getType().getId() != 44) {
                    subtract.add(0.0, -1.0, 0.0);
                }
                for (int i = 0; i < 6; ++i) {
                    UtilParticle.sendParticle(subtract.clone().add(UtilMath.randomRange(-1.5, 1.5), UtilMath.randomRange(0.0, 0.5), UtilMath.randomRange(-1.5, 1.5)), Particle.EXPLOSION_NORMAL, 1, new Vector(0, 0, 0), 0.05f);
                    UtilParticle.sendParticle(subtract.clone().add(UtilMath.randomRange(-1.5, 1.5), UtilMath.randomRange(0.0, 0.5), UtilMath.randomRange(-1.5, 1.5)), Particle.DRIP_WATER, 3, new Vector(0.5, 0.0, 0.5), 0.05f);
                }

                UtilLocation.getClosestPlayersFromLocation(subtract, 2.0).stream().filter(player -> !player.equals(TsunamiGadget.this.player)).findFirst().ifPresent(entity -> {
                    valloc = subtract;
                    UtilMath.bumpEntity(entity, valloc, 0.8, 0.20000000298023224);
                    valloc.getWorld().playSound(valloc, Sound.ENTITY_PLAYER_SWIM, 0.3f, 0.0f);
                    return;
                });
                subtract.getWorld().playSound(subtract, Sound.BLOCK_WATER_AMBIENT, 0.3f, (float)((UtilMath.randomRange(0, 1) > 0) ? 0 : 2));
                subtract.add(subtract.getDirection().normalize().multiply(0.3));
                ++this.time;
                if (this.time >= 80) {
                    this.cancel();
                }
            }
        }.runTaskTimer((Plugin)HeroynServerAPI.getInstance(), 1L, 1L);
    }
    
    @Override
    public void stop() {
    }
}
