package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.MobSpawnersCommand;
import fr.exorth.events.MSPBreakEvent;

public class MobSpawners extends JavaPlugin{
	
	static MobSpawners instance;

	
	public static MobSpawners getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		instance=this;
		
		getCommand("msp").setExecutor(new MobSpawnersCommand());
		Bukkit.getPluginManager().registerEvents(new MSPBreakEvent(), this);
		
		super.onEnable();
	}

}
