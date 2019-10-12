package net.heroyn.heroynserverapi.cosmetics.particle.particles;

import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.particle.Particles;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class AureoleParticle extends Particles
{
    static LinkedList<Vector> locations;
    private Location center;
    private int inc;
    
    static {
        (AureoleParticle.locations = new LinkedList<Vector>()).addAll(UtilMath.createCircle(0.5, 26.0));
    }
    
    public AureoleParticle(final Player player, final Particle particle) {
        super(player, particle, null);
        this.inc = 0;
    }
    
    @Override
    public void update() {
        this.center = this.playerInfo.getSecondPlayerLocation();
        if (this.center == null) {
            return;
        }
        final Vector add = AureoleParticle.locations.get(this.inc).clone().add(new Vector(0, 2, 0));
        this.center.add(add);
        UtilParticle.sendParticle(this.center, Particle.FLAME, 1, new Vector(0, 0, 0), 0.0f);
        this.center.subtract(add);
        ++this.inc;
        if (this.inc >= AureoleParticle.locations.size()) {
            this.inc = 0;
        }
    }
}
