package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.exorth.events.QuakeBuild;
import fr.exorth.events.QuakeChat;
import fr.exorth.events.QuakeDamage;
import fr.exorth.events.QuakeDrop;
import fr.exorth.events.QuakeInteract;
import fr.exorth.events.QuakeJoin;
import fr.exorth.events.QuakeMenuInteract;
import fr.exorth.events.QuakeShot;
import fr.exorth.events.QuakeStats;

public class EventsManager {
	
	public static void registerEvents(Quake pl) {
		
	PluginManager pm = Bukkit.getPluginManager();
	
		 pm.registerEvents(new QuakeJoin(Quake.sql), pl);
		 pm.registerEvents(new QuakeBuild(), pl);
		 pm.registerEvents(new QuakeStats(), pl);
		 pm.registerEvents(new QuakeDrop(), pl);
		 pm.registerEvents(new QuakeDamage(), pl);
		 pm.registerEvents(new QuakeShot(), pl);
		 pm.registerEvents(new QuakeInteract(), pl);
		 pm.registerEvents(new QuakeMenuInteract(), pl);
		 pm.registerEvents(new QuakeChat(), pl);	
		 
	}
	
}
