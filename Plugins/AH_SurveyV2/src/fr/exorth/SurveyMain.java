package fr.exorth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.SurveyCommand;
import fr.exorth.commands.SurveyEditCommand;
import fr.exorth.commands.SurveysCommand;
import fr.exorth.events.SurveyChatListener;
import fr.exorth.events.SurveyCloseEvent;
import fr.exorth.events.SurveyMenuInteract;
import fr.exorth.events.SurveysMenuInteract;
import fr.exorth.runnable.SurveyRunnable;
import fr.exorth.surveys.Survey;

public class SurveyMain extends JavaPlugin{
	
	static SurveyMain instance; 
	
	public HashMap<String,SurveyConfigManager> ConfigManagers = new HashMap<>();
	
	public HashMap<Player,String []> OnEdit = new HashMap<>();
	
	public HashMap<Player,String> SurveySelection = new HashMap<>();
	public HashMap<Player,Integer> QuestionSelection = new HashMap<>();
	
	public HashMap<Player,Survey> inSurvey = new HashMap<>();
	public HashMap<Player,ArrayList<String>> inSurveyAwnsers = new HashMap<>();
	
	public HashMap<Player,Survey> inSurveyCalibration = new HashMap<>();
	public HashMap<Player,ArrayList<String>> inSurveyCalibrationTimes = new HashMap<>();
	
	public HashMap<Player,Date> DatesforTimes = new HashMap<>();
	
	
	public static SurveyMain getInstance(){
		return instance;
	}


	
	@Override
	public void onEnable() {

		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance = this;
		
		loadCustomConfigs();
		
		getCommand("survey").setExecutor(new SurveyCommand());
		getCommand("surveyedit").setExecutor(new SurveyEditCommand());
		getCommand("surveys").setExecutor(new SurveysCommand());
		Bukkit.getPluginManager().registerEvents(new SurveyMenuInteract(), this);
		Bukkit.getPluginManager().registerEvents(new SurveysMenuInteract(), this);
		Bukkit.getPluginManager().registerEvents(new SurveyChatListener(), this);
		Bukkit.getPluginManager().registerEvents(new SurveyCloseEvent(), this);
		
		new SurveyRunnable().runTaskTimer(this, 0L, 20L);
		
		super.onEnable();
	}
	
	public void loadCustomConfigs() {
		
		@SuppressWarnings("unchecked")
		ArrayList<String> surveys = (ArrayList<String>) getConfig().get("Surveys");
		if(surveys == null){surveys= new ArrayList<String>();}
		
		for(String survey : surveys){
			
			String type = (String) getConfig().get("Types." + survey);
			
			SurveyConfigManager cfgm = new SurveyConfigManager(type,survey);
			cfgm.setupCustomConfig();
			cfgm.saveCustomConfig();
			cfgm.reloadCustomConfig();
			
			SurveyConfigManager cfgm2 = new SurveyConfigManager("Calibrations",survey);
			cfgm2.setupCustomConfig();
			cfgm2.saveCustomConfig();
			cfgm2.reloadCustomConfig();
			
			SurveyConfigManager cfgm3 = new SurveyConfigManager("Results",survey);
			cfgm3.setupCustomConfig();
			cfgm3.saveCustomConfig();
			cfgm3.reloadCustomConfig();
			
			ConfigManagers.put(survey, cfgm);
			ConfigManagers.put(survey+"Cal", cfgm2);
			ConfigManagers.put(survey+"Res", cfgm3);
			
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
	
	
	@Override
	public void onDisable() {
		
		saveCustomConfigs();
		
		
		super.onDisable();
	}

}
