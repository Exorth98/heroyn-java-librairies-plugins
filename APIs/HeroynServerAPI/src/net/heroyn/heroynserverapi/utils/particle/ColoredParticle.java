package net.heroyn.heroynserverapi.utils.particle;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ColoredParticle
{
    private Particle particle;
    private Location loc;
    int r;
    int g;
    int b;
    
    public ColoredParticle(final Particle particle, final Location loc, final int r, final int g, final int b) {
        this.particle = particle;
        this.loc = loc;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public void send() {
        this.loc.getWorld().spawnParticle(this.particle, this.loc.getX(), this.loc.getY(), this.loc.getZ(), 0, this.color(this.r), this.color(this.g), this.color(this.b), 1.0);
    }
    
    public void send(final Player player) {
        player.spawnParticle(this.particle, this.loc.getX(), this.loc.getY(), this.loc.getZ(), 0, this.color(this.r), this.color(this.g), this.color(this.b), 1.0);
    }
    
    private double color(double n) {
        n = ((n <= 0.0) ? -1.0 : n);
        return n / 255.0;
    }
}
