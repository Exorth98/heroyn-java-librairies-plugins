package fr.exorth.util;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.factionshop;

public class ShopZone {
	
	public static FileConfiguration config = factionshop.getInstance().getConfig();
	
	public static boolean isInside(Player p){
		
		Location pos1 = (Location) config.get("shopzone.pos1");
		Location pos2 = (Location) config.get("shopzone.pos2");
		Location pLoc = p.getLocation();
		
	    double[] dim = new double[2];
	    
	    dim[0] = pos1.getX();
	    dim[1] = pos2.getX();
	    Arrays.sort(dim);
	    if(pLoc.getX() > dim[1] || pLoc.getX() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getZ();
	    dim[1] = pos2.getZ();
	    Arrays.sort(dim);
	    if(pLoc.getZ() > dim[1] || pLoc.getZ() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getY();
	    dim[1] = pos2.getY();
	    Arrays.sort(dim);
	    if(pLoc.getY() > dim[1] || pLoc.getY() < dim[0])
	        return false;
	 
	    return true;
	}

}
