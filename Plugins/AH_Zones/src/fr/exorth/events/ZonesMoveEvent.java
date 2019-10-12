package fr.exorth.events;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.exorth.Zones;
import zone.Zone;

public class ZonesMoveEvent implements Listener {

	
	static FileConfiguration config = Zones.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> zones = (ArrayList<String>) config.get("Zones");
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		if(zones!=null) {
			
			Location from = e.getFrom();
			Location to = e.getTo();
			
			for(String zone : zones) {
				
				Zone z = Zone.getZone(zone);
				
				if(isEnteringZone(from,to,z)) {
					
					e.getPlayer().sendMessage(z.getEntrymsg());
					
				}
				if(isExitingZone(from,to,z)) {
					
					e.getPlayer().sendMessage(z.getExitmsg());
					
				}
				
				
				
			}
			
		}
		
	}

	
	private boolean isInZone(Location Loc, Zone z) {
		
		Location pos1 = z.getPos1();
		Location pos2 = z.getPos2();
		
	    double[] dim = new double[2];
	    
	    dim[0] = pos1.getX();
	    dim[1] = pos2.getX();
	    Arrays.sort(dim);
	    if(Loc.getX() > dim[1] || Loc.getX() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getZ();
	    dim[1] = pos2.getZ();
	    Arrays.sort(dim);
	    if(Loc.getZ() > dim[1] || Loc.getZ() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getY();
	    dim[1] = pos2.getY();
	    Arrays.sort(dim);
	    if(Loc.getY() > dim[1] || Loc.getY() < dim[0])
	        return false;
	 
	    return true;
		
	}
	

	private boolean isExitingZone(Location from, Location to, Zone z) {

		return (isInZone(from,z) && !isInZone(to,z));
		
	}


	private boolean isEnteringZone(Location from, Location to, Zone z) {
		
		return (!isInZone(from,z) && isInZone(to,z));
	}
	
}
