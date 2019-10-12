package fr.exorth;

import org.bukkit.plugin.PluginManager;
import org.bukkit.Bukkit;

import fr.exorth.util.UHCAutoLeafDecay;
import fr.exorth.util.UHCBlockDrops;
import fr.exorth.util.UHCSkullRegen;
import fr.exorth.util.speedRecipes;
import fr.exorth.events.UHCJoin;
import fr.exorth.events.UHCListener;
import fr.exorth.game.UHCPvp;

public class EventsManager {

	public static void registerEvents(UHCRun pl) {
	PluginManager pm = Bukkit.getPluginManager();
		 pm.registerEvents(new UHCJoin(), pl);
		 pm.registerEvents(new speedRecipes(), pl);
		 pm.registerEvents(new UHCSkullRegen(), pl);
		 pm.registerEvents(new UHCBlockDrops(), pl);
		 pm.registerEvents(new UHCPvp(), pl);
		 pm.registerEvents(new UHCListener(), pl);
		 pm.registerEvents(new UHCAutoLeafDecay(), pl);
		
	}
	
	

}
