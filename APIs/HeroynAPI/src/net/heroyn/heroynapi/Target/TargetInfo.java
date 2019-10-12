package net.heroyn.heroynapi.Target;

import org.bukkit.entity.Player;

public class TargetInfo
{
  private Player player;
  private double distance;
  
  public TargetInfo(Player player, double distance)
  {
    setPlayer(player);
    setDistance(distance);
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public void setPlayer(Player player)
  {
    this.player = player;
  }
  
  public double getDistance()
  {
    return this.distance;
  }
  
  public void setDistance(double distance)
  {
    this.distance = distance;
  }
}
