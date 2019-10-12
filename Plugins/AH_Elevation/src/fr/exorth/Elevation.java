package fr.exorth;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.SetElevationZoneCommand;
import fr.exorth.events.MoveEvent;

public class Elevation extends JavaPlugin{
	
	static Elevation instance;
	
	public static Elevation getInstance(){
		return instance;
	}

	public ArrayList <Player> onDown = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		
		instance = this;
		
		getCommand("setelevationzone").setExecutor(new SetElevationZoneCommand());
		Bukkit.getPluginManager().registerEvents(new MoveEvent(), this);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		super.onDisable();
	}

	

}
