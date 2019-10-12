package net.heroyn.heroynapi.utils;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import net.heroyn.heroynapi.utils.Task.TaskManager;

public class FireWorkUtils
{
  public static void createFirewortEffect(Location location, Color Color, FireworkEffect.Type Type, boolean kill)
  {
    Firework fw = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
    FireworkMeta fm = fw.getFireworkMeta();
    Random r = new Random();
    
    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color).withFade(Color).with(Type).trail(r.nextBoolean()).build();
    fm.addEffect(effect);
    int rp = r.nextInt(2) + 1;
    fm.setPower(rp);
    fw.setFireworkMeta(fm);
    if (kill) {
      TaskManager.runTaskLater(new Runnable()
      {
        public void run()
        {
          fw.detonate();
        }
      }, 2);
    }
  }
  
  public static FireworkEffect.Type getRandomFireworkType()
  {
    Random r = new Random();
    FireworkEffect.Type type = FireworkEffect.Type.BALL;
    int rt = r.nextInt(4) + 1;
    if (rt == 1) {
      type = FireworkEffect.Type.BALL;
    }
    if (rt == 2) {
      type = FireworkEffect.Type.BALL_LARGE;
    }
    if (rt == 3) {
      type = FireworkEffect.Type.BURST;
    }
    if (rt == 4) {
      type = FireworkEffect.Type.CREEPER;
    }
    if (rt == 5) {
      type = FireworkEffect.Type.STAR;
    }
    return type;
  }
  
  public static Color RandomColor()
  {
    Random r = new Random();
    int nbr = r.nextInt(10) + 1;
    Color color = null;
    if (nbr == 1) {
      color = Color.RED;
    }
    if (nbr == 2) {
      color = Color.BLUE;
    }
    if (nbr == 3) {
      color = Color.GREEN;
    }
    if (nbr == 4) {
      color = Color.GRAY;
    }
    if (nbr == 5) {
      color = Color.TEAL;
    }
    if (nbr == 6) {
      color = Color.LIME;
    }
    if (nbr == 7) {
      color = Color.SILVER;
    }
    if (nbr == 8) {
      color = Color.PURPLE;
    }
    if (nbr == 9) {
      color = Color.YELLOW;
    }
    if (nbr == 10) {
      color = Color.ORANGE;
    }
    return color;
  }
}
