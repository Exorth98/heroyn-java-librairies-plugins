package net.heroyn.heroynserverapi.cosmetics.particle.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.cosmetics.particle.Particles;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("unused")
public class WingsParticle extends Particles
{
    private WingType type;
    private Location location;
    private double xPos;
	private double zPos;
    private double yPos;
    private static double pi;
    private Vector vector;
    private Vector vector2;
    private int time;
    private boolean loop;
    private boolean x;
    private boolean o;
    private double space;
    private boolean[][] shape;
    private boolean[][] shape2;
    
    static {
        WingsParticle.pi = 3.141592653589793;
    }
    
    public WingsParticle(final Player player, final WingType type) {
        super(player, Particle.CLOUD, type);
        this.vector = new Vector(0, 0, 0);
        this.vector2 = new Vector(0, 0, 0);
        this.time = 0;
        this.loop = false;
        this.x = true;
        this.o = false;
        this.space = 0.2;
        this.shape = new boolean[][] { { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.o, this.o } };
        this.shape2 = new boolean[][] { { this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o }, { this.o, this.o, this.o, this.o, this.x, this.x, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o, this.o } };
        this.type = type;
    }
    
	@Override
    public void update() {
        (this.location = this.player.getLocation()).setPitch(0.0f);
        final Vector normalize = this.location.getDirection().normalize();
        normalize.multiply(-0.3);
        this.location.add(normalize);
        final double xPos = this.location.getX() - this.space * this.shape[0].length / 2.0 + this.space;
        this.yPos = this.location.clone().getY() + 2.0;
        for (int i = 0; i < this.shape.length; ++i) {
            for (int j = 0; j < this.shape[i].length; ++j) {
                if (this.shape[i][j]) {
                    final Location clone = this.location.clone();
                    clone.setX(this.xPos);
                    clone.setY(this.yPos);
                    final Vector subtract = clone.toVector().subtract(this.location.toVector());
                    UtilMath.rotateAroundAxisY(subtract, Math.toRadians(-this.player.getLocation().getYaw()) - this.time * WingsParticle.pi / 62.0);
                    this.location.add(subtract);
                    final int n = (this.type == WingType.DEMON_WINGS) ? UtilMath.randomRange(100, 255) : UtilMath.randomRange(224, 255);
                    //this.player.getWorld().spigot().playEffect(this.location, Effect.DRAGON_BREATH, 1, 1, (float)n, (float)((this.type == WingType.DEMON_WINGS) ? 0 : n), (float)((this.type == WingType.DEMON_WINGS) ? 0 : n), 1.0f, 0, 64);
                    this.location.subtract(subtract);
                }
                this.xPos += this.space;
            }
            this.yPos -= this.space;
            this.xPos = xPos;
        }
        final double xPos2 = this.location.getX() - this.space * this.shape2[0].length / 2.0 + this.space;
        this.yPos = this.location.clone().getY() + 2.0;
        for (int k = 0; k < this.shape2.length; ++k) {
            for (int l = 0; l < this.shape2[k].length; ++l) {
                if (this.shape2[k][l]) {
                    final Location clone2 = this.location.clone();
                    clone2.setX(this.xPos);
                    clone2.setY(this.yPos);
                    final Vector subtract2 = clone2.toVector().subtract(this.location.toVector());
                    UtilMath.rotateAroundAxisY(subtract2, Math.toRadians(-this.player.getLocation().getYaw()) + this.time * WingsParticle.pi / 62.0);
                    this.location.add(subtract2);
                    final int n2 = (this.type == WingType.DEMON_WINGS) ? UtilMath.randomRange(100, 255) : UtilMath.randomRange(224, 255);
                    //this.player.getWorld().spigot().playEffect(this.location, Effect.DRAGON_BREATH, 1, 1, (float)n2, (float)((this.type == WingType.DEMON_WINGS) ? 0 : n2), (float)((this.type == WingType.DEMON_WINGS) ? 0 : n2), 1.0f, 0, 64);
                    this.location.subtract(subtract2);
                }
                this.xPos += this.space;
            }
            this.yPos -= this.space;
            this.xPos = xPos2;
        }
        if (this.loop) {
            ++this.time;
            if (this.time >= 0) {
                this.loop = false;
            }
        }
        else {
            --this.time;
            if (this.time <= -14) {
                this.loop = true;
            }
        }
    }
    
    public enum WingType
    {
        RAINBOW_WINGS("RAINBOW_WINGS", 0), 
        DEMON_WINGS("DEMON_WINGS", 1);
        
        private WingType(final String s, final int n) {
        }
    }
}
