package net.heroyn.heroynapi.utils;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MathUtils
{
  public static final HashMap<Player, Integer> countdown_id = new HashMap<>();
  
  public static double cos(double t)
  {
    return Math.cos(t);
  }
  
  public static double sin(double t)
  {
    return Math.sin(t);
  }
  
  public static boolean isDouble(String s) {
	  
	  try {
		  
		  Double.parseDouble(s);
		  return true;
		  
	  }
	  catch (Exception e) {
		  
		  return false;
	  }
	  
  }
  
  public static boolean isInt(String s) {
	  
	  try {
		  
		  Integer.parseInt(s);
		  return true;
		  
	  }
	  catch (Exception e) {
		  
		  return false;
	  }
	  
  }
  
  public static Random random = new Random();
  
  public static void applyVelocity(Entity ent, Vector v)
  {
    if (ent.hasMetadata("NPC")) {
      return;
    }
    ent.setVelocity(v);
  }
  
  public static final int random(int start, int end)
  {
    return start + random.nextInt(end - start + 1);
  }
  
  public static double randomDouble(double min, double max)
  {
    return Math.random() < 0.5D ? (1.0D - Math.random()) * (max - min) + min : Math.random() * (max - min) + min;
  }
  
  public static final int random(int range)
  {
    return random.nextInt(range + 1);
  }
  
  public static final float random(float range)
  {
    return random.nextFloat() * range;
  }
  
  public static final float random(float start, float end)
  {
    return start + random.nextFloat() * (end - start);
  }
  
  public static long d(double d0)
  {
    long i = (long) d0;
    
    return d0 < i ? i - 1L : i;
  }
  
  public static final Vector rotateAroundAxisX(Vector v, double angle)
  {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    double y = v.getY() * cos - v.getZ() * sin;
    double z = v.getY() * sin + v.getZ() * cos;
    return v.setY(y).setZ(z);
  }
  
  public static final Vector rotateAroundAxisY(Vector v, double angle)
  {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    double x = v.getX() * cos + v.getZ() * sin;
    double z = v.getX() * -sin + v.getZ() * cos;
    return v.setX(x).setZ(z);
  }
  
  public static final Vector rotateAroundAxisZ(Vector v, double angle)
  {
    double cos = Math.cos(angle);
    double sin = Math.sin(angle);
    double x = v.getX() * cos - v.getY() * sin;
    double y = v.getX() * sin + v.getY() * cos;
    return v.setX(x).setY(y);
  }
}
