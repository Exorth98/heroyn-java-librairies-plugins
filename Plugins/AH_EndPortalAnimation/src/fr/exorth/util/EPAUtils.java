package fr.exorth.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class EPAUtils {
	
	static Location center = new Location(Bukkit.getWorld("faction12"), -1032, 35, 479);
	static Location holediskcenter = new Location(Bukkit.getWorld("faction12"), -1032, 50, 479);
	static int height = 60;
	static int radius = 25;
	static int holediskradius = 14;
	
	
	
	public static boolean isInZone(Location loc) {
		
		Location Max = center.clone().add(0.0, (height-1), 0.0);
			
			if(Max.getBlockY()>= loc.getBlockY() && loc.getBlockY()>= center.getBlockY()){
			
				if(inDisk(loc)){
					
					return true;					
				}			
			}	
		
		return false;
	}
	

	private static boolean inDisk(Location loc) {
		
		int difX = Math.abs(loc.getBlockX()-center.getBlockX());
		int difZ = Math.abs(loc.getBlockZ()-center.getBlockZ());
		int distance = (int) Math.sqrt((difX*difX)+(difZ*difZ));
		
		return distance <= radius;

	}
	
	public static boolean isPitchOk(Player p){
		
		return p.getLocation().getPitch()>40;
		
	}
	
	public static boolean isLookingHole(Player p){
		
		final List<Block> blocks = p.getLineOfSight( null, 150);
		
		int LaserNombreBlocks = blocks.size();
		
		for (int i = LaserNombreBlocks-1; i > 0 ; i--)
		{					
			
            int acc = 1;
            for (int x = -acc; x < acc ; x++){
                for (int z = -acc; z < acc; z++){
                    for (int y = -acc; y < acc; y++){
                    	
                    	Location blocLoc = blocks.get(i).getRelative(x, y, z).getLocation();
                    	
                        if (blocLoc.getBlockY()==holediskcenter.getBlockY()) {
                        	if(isInHoleDisk(blocLoc)){
                        		
                        		return true;
                        		
                        	}               	
                        }
                    }
                }
            }
		}
		return false;
	}


	private static boolean isInHoleDisk(Location loc) {
		
		int difX = Math.abs(loc.getBlockX()-holediskcenter.getBlockX());
		int difZ = Math.abs(loc.getBlockZ()-holediskcenter.getBlockZ());
		int distance = (int) Math.sqrt((difX*difX)+(difZ*difZ));
		
		return distance <= holediskradius;

	}
	
	
	
}
