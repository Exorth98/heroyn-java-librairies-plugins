package fr.exorth.util;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Zones;

public class ZonesUtils {
	
	static FileConfiguration config = Zones.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> zones = (ArrayList<String>) config.get("Zones");

	public static boolean zoneExist(String name) {
		
		if(zones==null) {zones= new ArrayList<String>();}	
		return zones.contains(name);
	}

	public static void removeZone(String name) {
		
		if(zones==null) {zones= new ArrayList<String>();}
		zones.remove(name);
		config.set("Zones", zones);
		Zones.getInstance().saveConfig();
		
	}

	public static void completeZone(String name) {
		
		if(zones==null) {zones= new ArrayList<String>();}
		if(!zones.contains(name)) {
			zones.add(name);
			config.set("Zones", zones);
			Zones.getInstance().saveConfig();
		}

	}

	public static void listZones(Player p) {
		
		if(zones==null) {zones= new ArrayList<String>();}
		p.sendMessage("§6==== Zones list =====");
		
		for(String zone : zones) {
			p.sendMessage("§e- " + zone);
		}
		
	}

}
