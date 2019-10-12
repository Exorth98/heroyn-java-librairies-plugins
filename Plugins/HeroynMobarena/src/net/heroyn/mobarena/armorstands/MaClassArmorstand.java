package net.heroyn.mobarena.armorstands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynapi.utils.ConfigUtils;
import net.heroyn.mobarena.HeroynMobarena;

public class MaClassArmorstand {
	
	public static Map<Location, MaClassArmorstand> allArmorstands = new HashMap<>();
	
	private String className;
	private Location loc;
	
	public MaClassArmorstand(String className, Location loc) {
		this.className = className;
		this.loc = loc;
	}
	public void register() {
		allArmorstands.put(loc, this);
	}
	
	public void registerInConfig() {
		
		ConfigManager cfgm = HeroynMobarena.getInstance().ConfigManagers.get("Armorstands");
		FileConfiguration cfg = cfgm.getCustomConfig();
		
		String sLoc = ConfigUtils.serializeLocation(this.loc);
		@SuppressWarnings("unchecked")
		List<String> sLocs = (List<String>) cfg.get(this.className);
		if(sLocs == null)sLocs = new ArrayList<String>();
		
		sLocs.add(sLoc);
		
		cfg.set(this.className, sLocs);		
		
		cfgm.saveCustomConfig();
		cfgm.reloadCustomConfig();
	}
	
	public void unregisterInConfig() {
		
		ConfigManager cfgm = HeroynMobarena.getInstance().ConfigManagers.get("Armorstands");
		FileConfiguration cfg = cfgm.getCustomConfig();
		
		String sLoc = ConfigUtils.serializeLocation(this.loc);
		@SuppressWarnings("unchecked")
		ArrayList<String> sLocs = (ArrayList<String>) cfg.get(this.className);
		if(sLocs == null)sLocs = new ArrayList<String>();
		
		sLocs.remove(sLoc);
		
		cfg.set(this.className, sLocs);	
		
		cfgm.saveCustomConfig();
		cfgm.reloadCustomConfig();
	}
	
	public static MaClassArmorstand getFromLoc(Location loc) {
		
		for(MaClassArmorstand as : allArmorstands.values()) {			
			if(loc.equals(as.getLoc())) {
				return as;
			}
		}

		
		return null;
	}
	

	public String getClassName() {
		return className;
	}

	public Location getLoc() {
		return loc;
	}
	

}
