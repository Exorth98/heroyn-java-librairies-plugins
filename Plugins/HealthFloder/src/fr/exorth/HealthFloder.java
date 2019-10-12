package fr.exorth;

import org.bukkit.plugin.java.JavaPlugin;


public class HealthFloder extends JavaPlugin{
	
	static HealthFloder instance;
	
	public static HealthFloder getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		
		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getCommand("getfloder").setExecutor(new getFloderCommand());
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
		
		super.onDisable();
	}

}
