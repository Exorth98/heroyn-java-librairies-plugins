package net.heroyn.heroynapi.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface Zone {
	
	public List<Block> getBlocks();	
	public List<Player> getPlayers();
	public List<Entity> getEntities();
	
	  public int getLowerX();	  
	  public int getLowerY();	  
	  public int getLowerZ();	  
	  public int getUpperX();	  
	  public int getUpperY();	  
	  public int getUpperZ();
	  
	  public int getVolume();
	  
	  public boolean isInZone(Location loc);
	  
	  public Location getLowerLocation();
	  public Location getUpperLocation();
	  
	  public World getWorld(boolean bypassErrors);
	  
	  public void setWorld(World world);
	  
	  public Map<String, Object> serialize();
	  public Iterator<Block> iterator();
	  
	  public Cuboid clone();
	  
	  public String toString();

}
