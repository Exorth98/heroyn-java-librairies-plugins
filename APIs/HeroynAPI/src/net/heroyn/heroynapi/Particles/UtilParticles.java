package net.heroyn.heroynapi.Particles;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.HeroynAPI;

public class UtilParticles
{
  private static final int DEF_RADIUS = 128;
  
  public static void drawParticleLine(Location from, Location to, Particles effect, int particles, int r, int g, int b)
  {
    Location location = from.clone();
    Location target = to.clone();
    double amount = particles;
    Vector link = target.toVector().subtract(location.toVector());
    float length = (float)link.length();
    link.normalize();
    
    float ratio = length / particles;
    Vector v = link.multiply(ratio);
    Location loc = location.clone().subtract(v);
    int step = 0;
    for (int i = 0; i < particles; i++)
    {
      if (step >= amount) {
        step = 0;
      }
      step++;
      loc.add(v);
      if (effect == Particles.REDSTONE) {
        effect.display(new Particles.OrdinaryColor(r, g, b), loc, 128.0D);
      } else {
        effect.display(0.0F, 0.0F, 0.0F, 0.0F, 1, loc, 128.0D);
      }
    }
  }
  
  public static void playHelix(Location loc, final float i, final Particles effect)
  {
    BukkitRunnable runnable = new BukkitRunnable()
    {
      double radius;
      double step;
      double y;
      Location location;
      
      public void run()
      {
        double inc = 0.12566370614359174D;
        double angle = this.step * inc + i;
        Vector v = new Vector();
        v.setX(Math.cos(angle) * this.radius);
        v.setZ(Math.sin(angle) * this.radius);
        if (effect == Particles.REDSTONE) {
          UtilParticles.display(0, 0, 255, this.location);
        } else {
          UtilParticles.display(effect, this.location);
        }
        this.location.subtract(v);
        this.location.subtract(0.0D, 0.1D, 0.0D);
        if (this.location.getY() <= this.y) {
          cancel();
        }
        this.step += 4.0D;
        this.radius += 0.019999999552965164D;
      }
    };
    runnable.runTaskTimer(HeroynAPI.getInstance(), 0L, 1L);
  }
  
  public static void display(Particles effect, Location location, int amount, float speed)
  {
    effect.display(0.0F, 0.0F, 0.0F, speed, amount, location, 128.0D);
  }
  
  public static void display(Particles effect, Location location, int amount)
  {
    effect.display(0.0F, 0.0F, 0.0F, 0.0F, amount, location, 128.0D);
  }
  
  public static void display(Particles effect, Location location)
  {
    display(effect, location, 1);
  }
  
  public static void display(Particles effect, double x, double y, double z, Location location, int amount)
  {
    effect.display((float)x, (float)y, (float)z, 0.0F, amount, location, 128.0D);
  }
  
  public static void display(Particles effect, int red, int green, int blue, Location location, int amount)
  {
    for (int i = 0; i < amount; i++) {
      effect.display(new Particles.OrdinaryColor(red, green, blue), location, 128.0D);
    }
  }
  
  public static void display(int red, int green, int blue, Location location)
  {
    display(Particles.REDSTONE, red, green, blue, location, 1);
  }
  
  public static void display(Particles effect, int red, int green, int blue, Location location)
  {
    display(effect, red, green, blue, location, 1);
  }

public static int getDefRadius() {
	return DEF_RADIUS;
}
}
