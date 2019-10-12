package net.heroyn.heroynapi.villagernpc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import net.heroyn.heroynapi.config.ConfigManager;

public class VillagerNpcManager {
	
	ConfigManager cfgM;
	
	public VillagerNpcManager(ConfigManager cfgM) {
		
		this.cfgM = cfgM;
		cfgM.setupAndSave();
	}
	
	public void addNpc(VillagerNpc npc) {
		
		FileConfiguration cfg = cfgM.getCustomConfig();
		
		List<String> npcs = cfg.get("NPCs") != null ? cfg.getStringList("NPCs") : new ArrayList<String>();
		npcs.add(npc.getName());
		cfg.set("NPCs", npcs);
		
		cfg.set(npc.getName()+".DisplayName",npc.getDisplayName() );
		
		if(npc instanceof MenuVillagerNpc) {
			
			cfg.set(npc.getName()+".Type", "MENU");
			cfg.set(npc.getName()+".MenuRef",((MenuVillagerNpc) npc).getMenuRef());
		}
		else if(npc instanceof CmdVillagerNpc) {
			
			cfg.set(npc.getName()+".Type", "COMMAND");
			cfg.set(npc.getName()+".Command",((CmdVillagerNpc) npc).getCommand());
		}
		
		cfg.set(npc.getName()+".Location",npc.getLocation() );
		
		cfgM.saveCustomConfig();
		VillagerNpc.allSpawnedVillagerNpc.put(npc.getName(), npc);
		
	}
	
	public void DeleteNpc(VillagerNpc npc) {
		
		FileConfiguration cfg = cfgM.getCustomConfig();
		
		List<String> npcs = cfg.get("NPCs") != null ? cfg.getStringList("NPCs") : new ArrayList<String>();
		npcs.remove(npc.getName());
		cfg.set("NPCs", npcs);
		
		cfgM.getCustomConfig().set(npc.getName(), null);
		
		cfgM.saveCustomConfig();
		VillagerNpc.allSpawnedVillagerNpc.remove(npc.getName());
	}
	
	public void loadNpc(String npcName) {
		
		FileConfiguration cfg = cfgM.getCustomConfig();
		
		String displayName = cfg.getString(npcName+".DisplayName");
		Location loc = (Location) cfg.get(npcName+".Location");
		String type = cfg.getString(npcName+".Type");
		
		if(type.equals("MENU")) {
			
			String menuRef = cfg.getString(npcName+".MenuRef");
			MenuVillagerNpc MenuNpc = new MenuVillagerNpc(npcName,displayName , loc, menuRef);
			VillagerNpc.allSpawnedVillagerNpc.put(npcName, MenuNpc);
			
		}
		else if(type.equals("COMMAND")) {
			
			String cmd = cfg.getString(npcName+".Command");
			CmdVillagerNpc CmdNpc = new CmdVillagerNpc(npcName,displayName , loc, cmd);
			VillagerNpc.allSpawnedVillagerNpc.put(npcName, CmdNpc);
		}
		
	}
	
	public void loadAllNpcs() {
		
		FileConfiguration cfg = cfgM.getCustomConfig();
		
		List<String> npcs = cfg.get("NPCs") != null ? cfg.getStringList("NPCs") : new ArrayList<String>();
		
		for(String npcName : npcs)
			loadNpc(npcName);
		
	}
	
	
	
	

}
