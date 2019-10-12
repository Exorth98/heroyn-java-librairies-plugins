package fr.exorth;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.SurveyCommand;
import fr.exorth.commands.SurveysCommand;
import fr.exorth.events.SurveyMenuInteract;

public class Survey extends JavaPlugin{
	
	static Survey instance; 
	
	public static Survey getInstance(){
		return instance;
	}


	public HashMap<Player,ArrayList<String>> SurveyResult = new HashMap<>();
	
	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance = this;
		
		getCommand("survey").setExecutor(new SurveyCommand());
		getCommand("surveys").setExecutor(new SurveysCommand());
		Bukkit.getPluginManager().registerEvents(new SurveyMenuInteract(), this);
		
		super.onEnable();
	}
	
	
	@Override
	public void onDisable() {
		
		
		
		
		super.onDisable();
	}

}
