package fr.exorth.util;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import fr.exorth.Elevation;

public class ElevationUtils {
	
	public static FileConfiguration config = Elevation.getInstance().getConfig();
	
	static Location center = (Location) config.get("center");
	static int radius = config.getInt("radius");
	static int height = config.getInt("height");
	static int platform = config.getInt("platform");

	public static boolean isInZone(Location loc) {
		
		Location diskMax = center.clone();
		
			diskMax.add(0.0, (height-1), 0.0);
			
			if(diskMax.getBlockY()>= loc.getBlockY() && loc.getBlockY()>= center.getBlockY()){
			
				
				if(inDisk(loc)){
					
					return true;
					
				}
				
			}	
		
		return false;
	}
	
	public static boolean isInSecondZone(Location loc) {
		
		Location diskMax = center.clone();
		Location diskMin = center.clone();
		
			diskMax.add(0.0, (height + platform + -1), 0.0);
			diskMin.add(0.0, height, 0.0);
			
			if(diskMax.getBlockY()>= loc.getBlockY() && loc.getBlockY()>= diskMin.getBlockY()){
			
				
				if(inDisk(loc)){
					
					return true;
					
				}
				
			}	
		
		return false;
	}



	private static boolean inDisk(Location ploc)  {
		
		int difX = Math.abs(ploc.getBlockX()-center.getBlockX());
		int difZ = Math.abs(ploc.getBlockZ()-center.getBlockZ());
		int distance = (int) Math.sqrt((difX*difX)+(difZ*difZ));
		
		return distance <= radius;
		
	}
	
	public static boolean inDisk2(Location ploc)  {
		
		int difX = Math.abs(ploc.getBlockX()-center.getBlockX());
		int difZ = Math.abs(ploc.getBlockZ()-center.getBlockZ());
		int distance = (int) Math.sqrt((difX*difX)+(difZ*difZ));
		
		return distance <= radius;
		
	}



	public static boolean isAtUp(Location loc) {
		
		
		
		return loc.getBlockY()== (center.clone().getBlockY()+height-1);
	}
	
	public static boolean isAtUp2(Location loc) {
		
		
		
		return loc.getBlockY()== (center.clone().getBlockY()+height+platform-1);
	}

	public static boolean isJumpOk(Location from) {

		return center.getBlockY()+height == from.getBlockY();
		
	}


}