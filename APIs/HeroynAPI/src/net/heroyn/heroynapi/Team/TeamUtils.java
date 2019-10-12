package net.heroyn.heroynapi.Team;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class TeamUtils
{
  static List<TeamUtils> allTeam = new ArrayList<>();
  public List<Player> playerInTeam = new ArrayList<>();
  public List<Player> spectator = new ArrayList<>();
  public String name;
  public int maxPlayer;
  public String prefix;
  public String color;
  public byte data;
  
  public TeamUtils(String name, String prefix, String color)
  {
    this.name = name;
    this.prefix = prefix;
    this.color = color;
    allTeam.add(this);
  }
  
  public TeamUtils(String name, String prefix, String color, byte data)
  {
    this.name = name;
    this.prefix = prefix;
    this.color = color;
    this.data = data;
    allTeam.add(this);
  }
  
  public boolean playerIsInTeam(Player player)
  {
    return getPlayerInTeam().contains(player);
  }
  
  public void removePlayerInTeam(Player player)
  {
    this.playerInTeam.remove(player);
  }
  
  public void addPlayerInTeam(Player player)
  {
    for (TeamUtils team : allTeam) {
      if (team.playerIsInTeam(player)) {
        team.removePlayerInTeam(player);
      }
    }
    this.playerInTeam.add(player);
    player.sendMessage("?6Vous rejoignez la team: " + getPrefix());
  }
  
  public void setPlayerSpectator(Player player)
  {
    this.playerInTeam.remove(player);
    this.spectator.add(player);
  }
  
  public static boolean areInTheSameTeam(Player player, Player target)
  {
    for (TeamUtils team : allTeam) {
      if ((team.playerIsInTeam(player)) && (team.playerIsInTeam(target))) {
        return true;
      }
    }
    return false;
  }
  
  public static TeamUtils getTeamWithName(String name)
  {
    for (TeamUtils team : allTeam) {
      if (team.getName().equalsIgnoreCase(name)) {
        return team;
      }
    }
    return null;
  }
  
  public static TeamUtils getTeamPlayer(Player player)
  {
    for (TeamUtils team : allTeam) {
      if ((team.getPlayerInTeam().contains(player)) || (team.getSpectator().contains(player))) {
        return team;
      }
    }
    return null;
  }
  
  public static boolean areNotInTheAllTeam(Player player)
  {
    for (TeamUtils team : allTeam) {
      if (team.playerIsInTeam(player)) {
        return false;
      }
    }
    return true;
  }
  
  public void setMaxPlayer(int maxPlayer)
  {
    this.maxPlayer = maxPlayer;
  }
  
  public String getPrefix()
  {
    return this.prefix;
  }
  
  public static List<TeamUtils> getAllTeam()
  {
    return allTeam;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getColor()
  {
    return this.color;
  }
  
  public byte getData()
  {
    return this.data;
  }
  
  public int getCount()
  {
    return getPlayerInTeam().size();
  }
  
  public int getMaxPlayer()
  {
    return this.maxPlayer;
  }
  
  public List<Player> getAllPlayerInTeam()
  {
    List<Player> list = new ArrayList<>();
    list.addAll(getPlayerInTeam());
    list.addAll(getSpectator());
    return list;
  }
  
  public List<Player> getPlayerInTeam()
  {
    return this.playerInTeam;
  }
  
  public List<Player> getSpectator()
  {
    return this.spectator;
  }
}
