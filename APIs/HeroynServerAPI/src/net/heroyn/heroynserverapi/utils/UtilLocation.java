package net.heroyn.heroynserverapi.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class UtilLocation
{
    private static List<Block> unbreakableBlocks;
    
    static {
        UtilLocation.unbreakableBlocks = new ArrayList<Block>();
    }
    
    public static List<Player> getClosestPlayersFromLocation(final Location location, final double n) {
        final ArrayList<Player> list = new ArrayList<Player>();
        final double n2 = n * n;
        for (final Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().add(0.0, 0.85, 0.0).distanceSquared(location) <= n2 && !player.hasMetadata("NPC")) {
                list.add(player);
            }
        }
        return list;
    }
    
    public static Entity[] getNearbyEntities(final Location location, final int n) {
        final int n2 = (n < 16) ? 1 : ((n - n % 16) / 16);
        final HashSet<Entity> set = new HashSet<Entity>();
        for (int i = 0 - n2; i <= n2; ++i) {
            for (int j = 0 - n2; j <= n2; ++j) {
                Entity[] entities;
                for (int length = (entities = new Location(location.getWorld(), (double)((int)location.getX() + i * 16), (double)(int)location.getY(), (double)((int)location.getZ() + j * 16)).getChunk().getEntities()).length, k = 0; k < length; ++k) {
                    final Entity entity = entities[k];
                    if (entity.getLocation().distance(location) <= n && entity.getLocation().getBlock() != location.getBlock()) {
                        set.add(entity);
                    }
                }
            }
        }
        return set.toArray(new Entity[set.size()]);
    }
    
    public static List<Block> getUnbreakableBlocks() {
        return UtilLocation.unbreakableBlocks;
    }
    
	protected static String getDirection(final double n) {
        if (0.0 <= n && n < 22.5) {
            return "North";
        }
        if (22.5 <= n && n < 67.5) {
            return "Northeast";
        }
        if (67.5 <= n && n < 112.5) {
            return "East";
        }
        if (112.5 <= n && n < 157.5) {
            return "Southeast";
        }
        if (157.5 <= n && n < 202.5) {
            return "South";
        }
        if (202.5 <= n && n < 247.5) {
            return "Southwest";
        }
        if (247.5 <= n && n < 292.5) {
            return "West";
        }
        if (292.5 <= n && n < 337.5) {
            return "Northwest";
        }
        if (337.5 <= n && n < 360.0) {
            return "North";
        }
        return null;
    }
    
    public static byte getBlockFaceData(final float n) {
        float n2 = n;
        if (n2 < 0.0f) {
            n2 += 360.0f;
        }
        if (n2 >= 315.0f || n2 < 45.0f) {
            return 3;
        }
        if (n2 < 135.0f) {
            return 4;
        }
        if (n2 < 225.0f) {
            return 2;
        }
        if (n2 < 315.0f) {
            return 5;
        }
        return 2;
    }
    
    public static BlockFace getBlockFace(final float n) {
        float n2 = n;
        if (n2 < 0.0f) {
            n2 += 360.0f;
        }
        if (n2 >= 315.0f || n2 < 45.0f) {
            return BlockFace.SOUTH;
        }
        if (n2 < 135.0f) {
            return BlockFace.WEST;
        }
        if (n2 < 225.0f) {
            return BlockFace.NORTH;
        }
        if (n2 < 315.0f) {
            return BlockFace.EAST;
        }
        return BlockFace.NORTH;
    }
}
