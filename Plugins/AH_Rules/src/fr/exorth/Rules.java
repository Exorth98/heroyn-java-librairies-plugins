package fr.exorth;

import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.ReglesCommand;

public class Rules extends JavaPlugin{
	
	static Rules instance;
	
	public static Rules getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance = this;
		
		getCommand("regles").setExecutor(new ReglesCommand());
		
		
		super.onEnable();
	}

}
