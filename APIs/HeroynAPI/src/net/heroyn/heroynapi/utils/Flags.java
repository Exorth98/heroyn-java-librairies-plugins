package net.heroyn.heroynapi.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;

import net.heroyn.heroynapi.utils.Task.TaskManager;

public class Flags
{
  protected static Map<Entity, List<Integer>> allFlags = new HashMap<>();
  protected static Map<Entity, Map<Integer, String>> stringFlags = new HashMap<>();
  
  public static void setStringFlag(Entity entity, int flags, String string) {
	  Map<Integer, String> map = new HashMap<>();
	  map.put(flags, string);
	  stringFlags.put(entity, map);
  }
  
  public static String readStringFlag(Entity entity, int flags) {
	  Map<Integer, String> map = stringFlags.get(entity);
	  return map.get(flags);
  }
  
  /**
   * Ajoute un flags temporaire
   * @param entity
   * @param flag
   * @param secondes
   */
  public static void setTempararyFlag(Entity entity, int flag, int secondes)
  {
	List<Integer> list = allFlags.get(entity);
	if (list == null) list = new ArrayList<>();
	list.add(flag);
    allFlags.put(entity, list);
    TaskManager.runTaskLater(new Runnable()
    {
      public void run()
      {
        removeFlag(entity, flag);
      }
    }, secondes);
  }
  
  /**
   * Ajoute un flags permanent
   * @param entity
   * @param flag
   */
  public static void setFlag(Entity entity, int flag)
  {
	List<Integer> list = allFlags.get(entity);
	if (list == null) list = new ArrayList<>();
	list.add(flag);
    allFlags.put(entity, list);
  }
  
  /**
   * Supprime un flags
   * @param entity
   */
  public static void removeFlag(Entity entity, int flags)
  {
	  List<Integer> list = allFlags.get(entity);
	  if (list == null) return;
	  else {
		  list.remove(flags);
		  allFlags.put(entity, list);
	  }
  }
  
  /**
   * return un flags
   * @param entity
   * @param flag
   * @return
   */
  public static boolean hasFlag(Entity entity, int flag)
  {
	List<Integer> list = allFlags.get(entity);
	if (list == null) return false;
    for (Integer it : list) {
    	if (it.equals(flag)) {
    		return true;
    	}
    }
	return false; 
  }
  
  
  
  
  public enum FlagsEnum {
	  
	  GHOST(1),
	  GODMODE(2),
	  NOPICKITEM(3),
	  LastDamager(4);
	  
	  public int id;
	  
	  FlagsEnum(int id) {
		  this.id = id;
	  }
	  
	  public int getId() {
		return id;
	}
  }
}
