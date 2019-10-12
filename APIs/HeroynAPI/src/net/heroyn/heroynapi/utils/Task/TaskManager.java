package net.heroyn.heroynapi.utils.Task;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import net.heroyn.heroynapi.HeroynAPI;

@SuppressWarnings("deprecation")
public class TaskManager
{
  public static Map<String, Integer> Task_ID = new HashMap<>();
  

public static void runTaskTimer(Runnable runnable, int args0, int args1, String taskName)
  {
    addTask(taskName, Bukkit.getScheduler().scheduleAsyncRepeatingTask(HeroynAPI.getInstance(), runnable, args0, args1));
  }
  
  public static void runTaskLater(Runnable runnable, int args0)
  {
    Bukkit.getScheduler().scheduleAsyncDelayedTask(HeroynAPI.getInstance(), runnable, args0);
  }
  
  public static int scheduleSyncRepeatingTask(Runnable runnable, int args0, int args1) {
	  return Bukkit.getScheduler().scheduleAsyncRepeatingTask(HeroynAPI.getInstance(), runnable, args0, args1);
  }
  
  public static void cancelTaskByName(String taskName)
  {
	int id = Task_ID.get(taskName);
	Bukkit.broadcastMessage(taskName + "   " + id);
    Bukkit.getScheduler().cancelTask(id);
    Task_ID.remove(taskName);
  }
  
  public static void addTask(String taskName, int id)
  {
	Bukkit.broadcastMessage(taskName + "   " + id);
    Task_ID.put(taskName, id);
  }
}
