package net.heroyn.mobarena;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynapi.utils.ConfigUtils;
import net.heroyn.mobarena.armorstands.MaClassArmorstand;
import net.heroyn.mobarena.commands.maCommand;
import net.heroyn.mobarena.commands.maconfCommand;
import net.heroyn.mobarena.commands.testCmda;
import net.heroyn.mobarena.events.ArmorstandListener;
import net.heroyn.mobarena.events.buttonEvents;
import net.heroyn.mobarena.events.leaveEvents;
import net.heroyn.mobarena.events.maDamageEvents;
import net.heroyn.mobarena.events.maLeaveEvents;
import net.heroyn.mobarena.events.maSecurityEvents;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.mobs.CustomEntities;
import net.heroyn.mobarena.utils.Arena;


public class HeroynMobarena extends JavaPlugin {
	
	static HeroynMobarena instance;
	
	private List<String> arenaConfigs = Arrays.asList("Infos","SpawnPoints","LootPoints","Loots","BonusItems","BonusButtons");
	public HashMap<String,ConfigManager> ConfigManagers = new HashMap<>();
	
	public HashMap<String, MobarenaGame> games = new HashMap<>();
	
	@Override
	public void onEnable() {
		instance = this;
		
		saveDefaultConfig();
		loadCustomConfigs();
		
		CustomEntities.registerEntities();
		
		this.getCommand("ma").setExecutor(new maCommand());
		this.getCommand("maconf").setExecutor(new maconfCommand());
		this.getCommand("testa").setExecutor(new testCmda());

		Bukkit.getPluginManager().registerEvents(new buttonEvents(), this);
		Bukkit.getPluginManager().registerEvents(new leaveEvents(), this);
		Bukkit.getPluginManager().registerEvents(new maLeaveEvents(), this);
		Bukkit.getPluginManager().registerEvents(new maDamageEvents(), this);
		Bukkit.getPluginManager().registerEvents(new maSecurityEvents(), this);
		Bukkit.getPluginManager().registerEvents(new ArmorstandListener(),this);
		
		super.onEnable();
		

	}
	
	@Override
	public void onDisable() {
		
		saveDefaultConfig();
		saveCustomConfigs();
		
		CustomEntities.unregisterEntities();
		
	}
	
	public static HeroynMobarena getInstance() {
		return instance;
		
	}
	
	public void loadCustomConfigs() {
		
		
		//PLAYERS CALSSES LEVELS
		ConfigManager PCLcfgm = new ConfigManager(this, "" , "PlayersClassesLevels");
		PCLcfgm.setupCustomConfig();PCLcfgm.saveCustomConfig();PCLcfgm.reloadCustomConfig();				
		ConfigManagers.put("PlayersClassesLevels", PCLcfgm);
		
		//LEADERBOARD
		ConfigManager LBcfgm = new ConfigManager(this, "" , "Leaderboard");
		LBcfgm.setupCustomConfig();LBcfgm.saveCustomConfig();LBcfgm.reloadCustomConfig();		
		ConfigManagers.put("Leaderboard", LBcfgm);
		
		
		//ARENAS
		loadArenaConfigs();		
		
		//CLASSES
		@SuppressWarnings("unchecked")
		ArrayList<String> classes = (ArrayList<String>) getConfig().get("Classes");
		if(classes == null){classes = new ArrayList<String>();}		
		for(String clas : classes) {			
			ConfigManager cfgm = new ConfigManager(this, "Classes", clas);
			cfgm.setupCustomConfig();cfgm.saveCustomConfig();cfgm.reloadCustomConfig();			
			ConfigManagers.put("class." + clas, cfgm);
		}	
		
		//ARMORSTANDS
		ConfigManager AScfgm = new ConfigManager(this, "", "Armorstands");
		AScfgm.setupCustomConfig();AScfgm.saveCustomConfig();AScfgm.reloadCustomConfig();
		ConfigManagers.put("Armorstands", AScfgm);
		loadArmorstands(AScfgm);
	}
	
	
	@SuppressWarnings("unchecked")
	private void loadArmorstands(ConfigManager ascfgm) {
		
		ArrayList<String> classes = (ArrayList<String>) getConfig().get("Classes");
		if(classes == null){classes = new ArrayList<String>();}		
		for(String clas : classes) {			
			
			List<String> sLocs = (List<String>) ascfgm.getCustomConfig().get(clas);
			if(sLocs == null)sLocs = new ArrayList<>();
			
			for(String sLoc : sLocs) {			
				MaClassArmorstand mas = new MaClassArmorstand(clas, ConfigUtils.deserializeLocation(sLoc));	
				mas.register();
			}						
		}			
	}

	public void saveCustomConfigs() {
		
		for(ConfigManager cfgm : ConfigManagers.values()) {
			
			cfgm.saveCustomConfig();
			cfgm.reloadCustomConfig();
		}		
	}

	public void loadArenaConfigs() {
		
		List<String> arenas = Arena.getArenasNames();
		
		for(String arena : arenas){			
			for(String arenaCfg : arenaConfigs) {
				
				ConfigManager cfgm = new ConfigManager(this, arena, arenaCfg);
				cfgm.setupCustomConfig();cfgm.saveCustomConfig();cfgm.reloadCustomConfig();
				
				ConfigManagers.put("arena." + arena + "." + arenaCfg, cfgm);
			}		
		}	
	}
}
