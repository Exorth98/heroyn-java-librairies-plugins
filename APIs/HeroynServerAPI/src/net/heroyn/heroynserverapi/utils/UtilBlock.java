package net.heroyn.heroynserverapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class UtilBlock
{
    public static List<Block> getInRadius(final Location location, final double n) {
        final ArrayList<Block> list = new ArrayList<Block>();
        for (int n2 = (int)n + 1, i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                for (int k = -n2; k <= n2; ++k) {
                    final Block relative = location.getBlock().getRelative(i, k, j);
                    if (UtilMath.offset(location, relative.getLocation()) <= n) {
                        list.add(relative);
                    }
                }
            }
        }
        return list;
    }
    
    public static List<Block> getIn2DRadius(final Location location, final double n) {
        final ArrayList<Block> list = new ArrayList<Block>();
        for (int n2 = (int)n + 1, i = -n2; i <= n2; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                final Block block = location.getWorld().getBlockAt((int)(location.getX() + i), (int)location.getY(), (int)(location.getZ() + j));
                if (UtilMath.offset(location, block.getLocation().add(0.5, 0.5, 0.5)) <= n) {
                    list.add(block);
                }
            }
        }
        return list;
    }
}
