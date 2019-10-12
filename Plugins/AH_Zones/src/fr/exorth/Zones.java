package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.command.ZoneCommand;
import fr.exorth.events.ZonesMoveEvent;

public class Zones extends JavaPlugin{
	
	static Zones instance;
	
	public static Zones getInstance() {
		return instance;
	}

	
	@Override
	public void onEnable() {
		
		instance=this;
		
		saveDefaultConfig();
		
		getCommand("zone").setExecutor(new ZoneCommand());
		Bukkit.getPluginManager().registerEvents(new ZonesMoveEvent(), this);
		
		
		super.onEnable();
	}
	
	
}
