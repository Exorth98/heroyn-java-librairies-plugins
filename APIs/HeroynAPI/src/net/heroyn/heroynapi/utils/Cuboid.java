package net.heroyn.heroynapi.utils;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Cuboid implements Zone, Iterable<Block>, Cloneable, ConfigurationSerializable {
  protected String worldName;
  protected final int xPos1;
  protected final int yPos1;
  protected final int zPos1;
  protected final int xPos2;
  protected final int yPos2;
  protected final int zPos2;

  public Cuboid(Location loc)
  {
    this(loc, loc);
  }

  public Cuboid(Cuboid cuboid) {
    this(cuboid.getWorld(false), cuboid.xPos1, cuboid.yPos1, cuboid.zPos1, cuboid.xPos2, cuboid.yPos2, cuboid.zPos2);
  }

  public Cuboid(Location loc1, Location loc2) {
    if ((loc1 != null) && (loc2 != null)) {
      if ((loc1.getWorld() != null) && (loc2.getWorld() != null) &&
              (!loc1.getWorld().equals(loc2.getWorld()))) {
        throw new IllegalStateException("The 2 locations of the cuboid must be in the same world!"); }
      this.worldName = (loc1.getWorld() != null ? loc1.getWorld().getName() : "");
      this.xPos1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
      this.yPos1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
      this.zPos1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
      this.xPos2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
      this.yPos2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
      this.zPos2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
    }
    else
    {
      this.worldName = "";
      this.xPos1 = 0;
      this.yPos1 = 0;
      this.zPos1 = 0;
      this.xPos2 = 0;
      this.yPos2 = 0;
      this.zPos2 = 0;
    }
  }

  public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
    this.worldName = world.getName();
    this.xPos1 = Math.min(x1, x2);
    this.xPos2 = Math.max(x1, x2);
    this.yPos1 = Math.min(y1, y2);
    this.yPos2 = Math.max(y1, y2);
    this.zPos1 = Math.min(z1, z2);
    this.zPos2 = Math.max(z1, z2);
  }

  public Cuboid(String world, int x1, int y1, int z1, int x2, int y2, int z2) {
    this.worldName = world;
    this.xPos1 = Math.min(x1, x2);
    this.xPos2 = Math.max(x1, x2);
    this.yPos1 = Math.min(y1, y2);
    this.yPos2 = Math.max(y1, y2);
    this.zPos1 = Math.min(z1, z2);
    this.zPos2 = Math.max(z1, z2);
  }

  public Cuboid(Map<String, Object> map) {
    this.worldName = ((String)map.get("worldName"));
    this.xPos1 = ((Integer)map.get("x1")).intValue();
    this.xPos2 = ((Integer)map.get("x2")).intValue();
    this.yPos1 = ((Integer)map.get("y1")).intValue();
    this.yPos2 = ((Integer)map.get("y2")).intValue();
    this.zPos1 = ((Integer)map.get("z1")).intValue();
    this.zPos2 = ((Integer)map.get("z2")).intValue();
  }

  public List<Block> getBlocks() {
    List<Block> blockList = new ArrayList<>();
    World world = getWorld(true);
    if (world != null)
    {
      for (int x = this.xPos1; x <= this.xPos2; x++) {
        for (int y = this.yPos1; y <= this.yPos2; y++) {
          for (int z = this.zPos1; z <= this.zPos2; z++) {
            blockList.add(world.getBlockAt(x, y, z));
          }
        }
      }
      return blockList;
    }
    return new ArrayList<>(); }

  public boolean contains(Location location) {
    return location != null && location.getWorld().getName().equals(this.worldName) && location.toVector().isInAABB(new Vector(xPos1, yPos1, zPos1), new Vector(xPos2, yPos2, zPos2));
  }

  public boolean contains(Entity entity) {
    return contains(entity.getLocation());
  }

  public List<Player> getPlayers() {
    List<Player> playerList = new ArrayList<>();
    World world = getWorld(true);
    if (world != null)
    {
      List<Player> worldPlayers = world.getPlayers();
      for (int x = this.xPos1; x <= this.xPos2; x++) {
        for (int y = this.yPos1; y <= this.yPos2; y++) {
          for (int z = this.zPos1; z <= this.zPos2; z++)
          {
            Player player;
            label126:
            for (Iterator<Player> localIterator = worldPlayers.iterator(); localIterator.hasNext(); playerList.add(player))
            {
              player = (Player)localIterator.next();
              Location pLoc = player.getLocation();
              if (((int)pLoc.getX() != x) || ((int)pLoc.getY() != y) || ((int)pLoc.getZ() != z)) {
                break label126;
              }
            }
          }
        }
      }
      return playerList;
    }
    return new ArrayList<>();
  }

  public boolean isInZone(Location loc) {
    World world = getWorld(true);
    if (world != null)
    {
      for (int x = this.xPos1; x <= this.xPos2; x++) {
        for (int y = this.yPos1; y <= this.yPos2; y++) {
          for (int z = this.zPos1; z <= this.zPos2; z++)
          {
            Location here = new Location(world, x,y,z);
            if(loc.getBlock().getLocation().equals(here))return true;
          }
        }
      }
    }
    return false;
  }

  public List<Entity> getEntities() {
    List<Entity> entitiesList = new ArrayList<>();
    World world = getWorld(true);
    if (world != null)
    {
      List<Entity> worldEntities = world.getEntities();
      for (int x = this.xPos1; x <= this.xPos2; x++) {
        for (int y = this.yPos1; y <= this.yPos2; y++) {
          for (int z = this.zPos1; z <= this.zPos2; z++)
          {
            Entity entity;
            label126:
            for (Iterator<Entity> localIterator = worldEntities.iterator(); localIterator.hasNext(); entitiesList.add(entity))
            {
              entity = (Entity)localIterator.next();
              Location eLoc = entity.getLocation();
              if (((int)eLoc.getX() != x) || ((int)eLoc.getY() != y) || ((int)eLoc.getZ() != z)) {
                break label126;
              }
            }
          }
        }
      }
      return entitiesList;
    }
    return new ArrayList<>();
  }


  public int getLowerX()
  {
    return this.xPos1;
  }

  public int getLowerY()
  {
    return this.yPos1;
  }

  public int getLowerZ()
  {
    return this.zPos1;
  }

  public int getUpperX()
  {
    return this.xPos2;
  }

  public int getUpperY()
  {
    return this.yPos2;
  }

  public int getUpperZ()
  {
    return this.zPos2;
  }

  public int getVolume()
  {
    return (this.xPos2 - this.xPos1 + 1) * (this.yPos2 - this.yPos1 + 1) * (this.zPos2 - this.zPos1 + 1);
  }

  public Location getLowerLocation()
  {
    return new Location(getWorld(false), this.xPos1, this.yPos1, this.zPos1);
  }

  public Location getUpperLocation()
  {
    return new Location(getWorld(false), this.xPos2, this.yPos2, this.zPos2);
  }

  public World getWorld(boolean bypassErrors) {
    World world = Bukkit.getWorld(this.worldName);
    if ((world == null) &&
            (!bypassErrors)) {
      throw new IllegalStateException("World '" + this.worldName + "' is not loaded");
    }
    return world;
  }

  public void setWorld(World world)
  {
    if (world != null) {
      this.worldName = world.getName();
    }
  }

  public Map<String, Object> serialize() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("worldName", this.worldName);
    map.put("x1", Integer.valueOf(this.xPos1));
    map.put("y1", Integer.valueOf(this.yPos1));
    map.put("z1", Integer.valueOf(this.zPos1));
    map.put("x2", Integer.valueOf(this.xPos2));
    map.put("y2", Integer.valueOf(this.yPos2));
    map.put("z2", Integer.valueOf(this.zPos2));
    return map;
  }

  public Iterator<Block> iterator()
  {
    return getBlocks().iterator();
  }

  public Cuboid clone()
  {
    return new Cuboid(this);
  }

  public String toString() {
    return this.worldName + ":" + this.xPos1 + " " + this.yPos1 + " " + this.zPos1 + ":" + this.xPos2 + " " + this.yPos2 + " " + this.zPos2;
  }

  public Cuboid getBoundingCuboid() {
    int xMin = Math.min(this.getLowerX(), getLowerX());
    int yMin = Math.min(this.getLowerY(), getLowerY());
    int zMin = Math.min(this.getLowerZ(), getLowerZ());
    int xMax = Math.max(this.getUpperX(), getUpperX());
    int yMax = Math.max(this.getUpperY(), getUpperY());
    int zMax = Math.max(this.getUpperZ(), getUpperZ());

    return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax);
  }

}
