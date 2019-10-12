package fr.exorth;

import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.TPRCommand;

public class Teleportations extends JavaPlugin{

	static Teleportations instance; 
	
	public static Teleportations getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getCommand("tpr").setExecutor(new TPRCommand());
		
		
		super.onEnable();
	}
	
	
}
