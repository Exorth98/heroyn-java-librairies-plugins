package net.heroyn.heroynserverapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import net.heroyn.heroynserverapi.HeroynServerAPI;

@SuppressWarnings("unchecked")
public class ConfigMagicChest {
	
	public static void addChest(Location loc) {

		List<Location> list = (List<Location>) HeroynServerAPI.getInstance().getConfig().get("magicchest");
		if (list == null) list = new ArrayList<>();
		list.add(loc);
		HeroynServerAPI.getInstance().getConfig().set("magicchest", list);
		HeroynServerAPI.getInstance().saveConfig();
	}
	
	public static Location getChest(Location loc) {
		List<Location> list = (List<Location>) HeroynServerAPI.getInstance().getConfig().get("magicchest");
		for (Location locs : list) {
			if(loc.equals(locs)) {
				return locs;
			}
		}
		return null;
	}
	
	public static List<Location> getAllChest() {
		List<Location> list = (List<Location>) HeroynServerAPI.getInstance().getConfig().get("magicchest");
		if (list == null) return null;
		return list;
	}

}
