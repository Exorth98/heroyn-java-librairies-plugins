package fr.exorth.utils;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import fr.exorth.NPCforShop;

public class Utils {
	
	static FileConfiguration config = NPCforShop.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static
	ArrayList<String> NPCs = (ArrayList<String>) config.getList("NPCs");

	public static boolean nameExist(String name) {
		
		if(NPCs==null){NPCs = new ArrayList<String>();}
		
		for(String npc : NPCs){
			if(npc.equals(name)){
				return true;
			}
			
		}
		
		return false;
	}

	public static ArrayList<Location> getNPCsLoc() {
		
		if(NPCs==null){NPCs = new ArrayList<String>();}
		ArrayList<Location> locs = new ArrayList<>();
		
		for(String npc : NPCs){
			Location loc = (Location) config.get(npc + ".location");
			locs.add(loc);
		}
		
		return locs;
	}

	public static String getShopFromLoc(Location loc) {
		
		if(NPCs==null){NPCs = new ArrayList<String>();}

		String shop=null;
		
		for(String npc : NPCs){
			
			Location npcloc = (Location) config.get(npc + ".location");
			if(loc.equals(npcloc)){
				shop = (String) config.get(npc + ".shop");
			}

		}
		
		return shop;
	}

}
