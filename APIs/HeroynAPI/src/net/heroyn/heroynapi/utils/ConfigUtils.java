package net.heroyn.heroynapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConfigUtils {
	
	public static String serializeLocation(Location loc) {
		
		return loc.getWorld().getName()+"/"+loc.getX()+"/"+loc.getY()+"/"+loc.getZ()+"/"+loc.getYaw()+"/"+loc.getPitch();	
	}
	
	public static Location deserializeLocation(String serializeLoc) {
		
		Location loc = null;
		
		if(serializeLoc != null) {
			
			if(serializeLoc.contains("/")) {
				
				String[] infos = serializeLoc.split("/");
				if(infos.length == 6) {
					
					loc = new Location(Bukkit.getWorld(infos[0]),
									   Double.parseDouble(infos[1]), Double.parseDouble(infos[2]), Double.parseDouble(infos[3]),
									   Float.parseFloat(infos[4]), Float.parseFloat(infos[5]));				
				}
			}		
		}
		return loc;
	}
	
	public static List<String> serializeLocationList(List<Location> locs){
		
		List<String> sLocs = new ArrayList<>();
		for(Location loc : locs)
			sLocs.add(serializeLocation(loc));
		
		return sLocs;		
	}

	public static List<Location> deserializeLocationList(List<String> sLocs){
		
		List<Location> locs = new ArrayList<>();
		for(String sLoc : sLocs)
			locs.add(deserializeLocation(sLoc));
		
		return locs;		
	}
	
}
