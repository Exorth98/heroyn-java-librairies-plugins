package fr.exorth;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.MGCommand;
import fr.exorth.events.MGConfirmationInteractEvent;
import fr.exorth.events.MGMenuInteractEvent;
import fr.exorth.events.MGMoveEvent;
import fr.exorth.events.MGTeleportEvent;
import fr.exorth.events.MGToolEvent;
import fr.exorth.events.MGnpcListener;
import fr.exorth.runnable.MGRunnable;

public class MineGestion extends JavaPlugin{
	
	static MineGestion instance;
	
	public MGConfigManager cfgmRS;	
	public MGConfigManager cfgmP;
	
	public HashMap<Player,double[]> inRentConfirmation = new HashMap<>();
	public HashMap<Player,double[]> inTimeAddConfirmation = new HashMap<>();
	public HashMap<Player,Double> inPickaxeChangeConfirmation = new HashMap<>();
	
	public ArrayList<Player> inZone = new ArrayList<>();
	
	public static MineGestion getInstance(){
		return instance;
	}
	
	
	
	@Override
	public void onEnable() {
		
		loadCustomConfigs();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance=this;
		
		getCommand("mg").setExecutor(new MGCommand());
		Bukkit.getPluginManager().registerEvents(new MGnpcListener(), this);
		Bukkit.getPluginManager().registerEvents(new MGMenuInteractEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MGConfirmationInteractEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MGMoveEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MGTeleportEvent(), this);
		Bukkit.getPluginManager().registerEvents(new MGToolEvent(), this);
		
		new MGRunnable().runTaskTimer(this, 0L, 20L);
		
		super.onEnable();
	}
	
	private void loadCustomConfigs() {
		
		cfgmRS= new MGConfigManager("Rents_Storage");
		cfgmP= new MGConfigManager("Vip_Preferences");
				
		cfgmRS.setupCustomConfig();
		cfgmP.setupCustomConfig();
		
	}



	@Override
	public void onDisable() {
		
		
		
		super.onDisable();
	}

}
