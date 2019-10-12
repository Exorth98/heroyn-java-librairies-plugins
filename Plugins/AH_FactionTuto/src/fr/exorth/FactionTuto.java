package fr.exorth;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.FTCommand;
import fr.exorth.events.FTMoveEvent;
import fr.exorth.util.FTUtils;

public class FactionTuto extends JavaPlugin{
	
	public HashMap<Player,Location> inTuto = new HashMap<>();
	public ArrayList<Player> requestCancel = new ArrayList<>();
	
	static FactionTuto instance;
	
	private HashMap<String,FTConfigManager> ConfigManagers = new HashMap<>();
	
	public static FactionTuto getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		loadCustomConfigs();

		
		instance = this;
		
		getCommand("ft").setExecutor(new FTCommand());
		Bukkit.getPluginManager().registerEvents(new FTMoveEvent(), this);
		
		
		super.onEnable();
	}
	
	public void loadCustomConfigs() {
		
		@SuppressWarnings("unchecked")
		ArrayList<String> tutos = (ArrayList<String>) getConfig().get("Tutos");
		if(tutos == null){tutos= new ArrayList<String>();}
		
		for(String tuto : tutos){
			
			FTConfigManager cfgm = new FTConfigManager(tuto);
			cfgm.setupCustomConfig();
			cfgm.saveCustomConfig();
			cfgm.reloadCustomConfig();
			
			ConfigManagers.put(tuto, cfgm);
			
		}

		
	}
	
	public void saveCustomConfigs() {
		
		@SuppressWarnings("unchecked")
		ArrayList<String> tutos = (ArrayList<String>) getConfig().get("Tutos");
		if(tutos == null){tutos= new ArrayList<String>();}
		
		for(String tuto : ConfigManagers.keySet()){
			
			ConfigManagers.get(tuto).saveCustomConfig();
			
		}

		
	}
	
	public FTConfigManager getConfigManager(String filename){
		
		return ConfigManagers.get(filename);
		
	}
	
	

	@Override
	public void onDisable() {
		
		for(Player p : inTuto.keySet()){
			
			FTUtils.exitTuto(p);
			
		}
		
		saveCustomConfigs();
		
		super.onDisable();
	}

}
