package fr.exorth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class QuakeStats implements Listener {
	
	@EventHandler
	public void onlosingfood(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}

}
