package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.events.EPAMoveEvent;
import fr.exorth.runnable.EPARunnable;

public class EndPortalAnimation extends JavaPlugin {

	static EndPortalAnimation instance;
	
	public static EndPortalAnimation getInstance(){
		return instance;
	}
	
	
	@Override
	public void onEnable() {
		
		instance = this;
		Bukkit.getPluginManager().registerEvents(new EPAMoveEvent(), this);
		
		new EPARunnable().runTaskTimer(this, 0L, 10L);
		
		super.onEnable();
	}
	
}
