package fr.exorth.util;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Teleportations;

public class TeleportationsUtils {
	
	static FileConfiguration config = Teleportations.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static
	ArrayList<String> points = (ArrayList<String>) config.get("TpList");

	public static void ListPoints(Player p) {
		
		if(points==null){points = new ArrayList<String>();}
		if(points.size()==0){
			p.sendMessage("§cAucun point disponible");
		}else{
			p.sendMessage("\n\n§6====Liste de points de Tp rapide====");
			
			for(String point : points){
				p.sendMessage("§e- " + point);
			}
			
		}
		
	}

	public static void createPoint(String point, Location loc) {
		
		if(points==null){points = new ArrayList<String>();}
		points.add(point);
		config.set("TpList", points);
		config.set(point, loc);
		Teleportations.getInstance().saveConfig();
		
	}
	
	public static boolean pointExist(String point) {
		
		if(points==null){points = new ArrayList<String>();}
		
		for(String pt : points){
			if(pt.equalsIgnoreCase(point)){
				return true;
			}
		}
		
		return false;
		
	}

	public static void removePoint(String point) {
		
		if(points==null){points = new ArrayList<String>();}
		points.remove(point);
		config.set("TpList", points);
		config.set(point, null);
		Teleportations.getInstance().saveConfig();
		
	}

	public static void sendPlayer(Player p, String point) {
		
		for(String pt : points){
			if(pt.equalsIgnoreCase(point)){
				point=pt;
			}
		}
		
		Location loc = (Location) config.get(point);
		
		p.teleport(loc);
		
	}

}
