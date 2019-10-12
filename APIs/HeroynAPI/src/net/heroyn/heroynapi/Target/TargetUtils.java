package net.heroyn.heroynapi.Target;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.Team.TeamUtils;

public class TargetUtils
{
  public static Player getTargetPlayer(Player player, int range, double aiming, boolean WallHack, boolean teamIgnore)
  {
    TargetInfo targetinfo = getTarget(player, range, aiming, WallHack, teamIgnore, false);
    return targetinfo != null ? targetinfo.getPlayer() : null;
  }
  
  public static TargetInfo getTargetInfoPlayerTeamOnly(Player player, int range, double aiming, boolean wallHack)
  {
    return getTarget(player, range, aiming, wallHack, false, true);
  }
  
  public static TargetInfo getTargetInfoPlayer(Player player, int range, double aiming, boolean wallHack, boolean teamIgnore)
  {
    return getTarget(player, range, aiming, wallHack, teamIgnore, false);
  }
  
  public static TargetInfoEntity getTargetInfoEntity(Player player, int range, double aiming, boolean wallHack, boolean teamIgnore)
  {
    return getTargetEntity(player, range, aiming, wallHack, teamIgnore, false);
  }
  
  public static TargetInfo getTarget(Player player, int maxRange, double aiming, boolean wallHack, boolean teamIgnore, boolean teamOnly)
  {
    Player target = null;
    double distance = 0.0D;
    Location playerEyes = player.getEyeLocation();
    Vector direction = playerEyes.getDirection().normalize();
    
    List<Player> targets = new ArrayList<>();
    for (Player online : Bukkit.getOnlinePlayers()) {
      if ((online != player) && 
        (online.getLocation().distanceSquared(playerEyes) <= maxRange * maxRange) && 
        ((!teamIgnore) || (!TeamUtils.areInTheSameTeam(online, player))) && (
        (!teamOnly) || (TeamUtils.areInTheSameTeam(online, player)))) {
        targets.add(online);
      }
    }
    if (targets.size() > 0)
    {
      Location loc = playerEyes.clone();
      
      Vector progress = direction.clone().multiply(0.7D);
      maxRange = 100 * maxRange / 70;
      int loop = 0;
      while (loop < maxRange)
      {
        loop++;
        loc.add(progress);
        Block block = loc.getBlock();
        if ((!wallHack) && (block.getType().isSolid())) {
          break;
        }
        double lx = loc.getX();
        double ly = loc.getY();
        double lz = loc.getZ();
        for (Player possibleTarget : targets) {
          if (possibleTarget != player)
          {
            Location testLoc = possibleTarget.getLocation().add(0.0D, 0.85D, 0.0D);
            double px = testLoc.getX();
            double py = testLoc.getY();
            double pz = testLoc.getZ();
            
            boolean dX = Math.abs(lx - px) < 0.7D * aiming;
            boolean dY = Math.abs(ly - py) < 1.7D * aiming;
            boolean dZ = Math.abs(lz - pz) < 0.7D * aiming;
            if ((dX) && (dY) && (dZ))
            {
              target = possibleTarget;
              break;
            }
          }
        }
        if (target != null)
        {
          distance = loop * 70 / 100;
          break;
        }
      }
    }
    if (target != null)
    {
      targets.remove(target);
      return new TargetInfo(target, distance);
    }
    return null;
  }
  
  public static TargetInfoEntity getTargetEntity(Player player, int maxRange, double aiming, boolean wallHack, boolean teamIgnore, boolean teamOnly)
  {
    Entity target = null;
    double distance = 0.0D;
    Location playerEyes = player.getEyeLocation();
    Vector direction = playerEyes.getDirection().normalize();
    
    List<Entity> targets = new ArrayList<>();
    for (Entity online : Bukkit.getWorlds().get(0).getEntities()) {
      if ((online != player) && 
        (online.getLocation().distanceSquared(playerEyes) <= maxRange * maxRange)) {
    	  targets.add(online);
      }
    }
    if (targets.size() > 0)
    {
      Location loc = playerEyes.clone();
      
      Vector progress = direction.clone().multiply(0.7D);
      maxRange = 100 * maxRange / 70;
      int loop = 0;
      while (loop < maxRange)
      {
        loop++;
        loc.add(progress);
        Block block = loc.getBlock();
        if ((!wallHack) && (block.getType().isSolid())) {
          break;
        }
        double lx = loc.getX();
        double ly = loc.getY();
        double lz = loc.getZ();
        for (Entity possibleTarget : targets) {
          if (possibleTarget != player)
          {
            Location testLoc = possibleTarget.getLocation().add(0.0D, 0.85D, 0.0D);
            double px = testLoc.getX();
            double py = testLoc.getY();
            double pz = testLoc.getZ();
            
            boolean dX = Math.abs(lx - px) < 0.7D * aiming;
            boolean dY = Math.abs(ly - py) < 1.7D * aiming;
            boolean dZ = Math.abs(lz - pz) < 0.7D * aiming;
            if ((dX) && (dY) && (dZ))
            {
              target = possibleTarget;
              break;
            }
          }
        }
        if (target != null)
        {
          distance = loop * 70 / 100;
          break;
        }
      }
    }
    if (target != null)
    {
      targets.remove(target);
      return new TargetInfoEntity(target, distance);
    }
    return null;
  }
}
