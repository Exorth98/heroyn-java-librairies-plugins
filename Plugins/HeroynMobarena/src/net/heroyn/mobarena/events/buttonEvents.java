package net.heroyn.mobarena.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import net.heroyn.mobarena.utils.Arena;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;

public class buttonEvents implements Listener {
	
	
	//BUTTON CONFIG
	@EventHandler
	public void onConfigure(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		if(e.getClickedBlock() != null) {
			
			Material mat = e.getClickedBlock().getType();
			Location loc = e.getClickedBlock().getLocation();
			
			if(mat == Material.WOOD_BUTTON || mat == Material.STONE_BUTTON) {
				
				if(HeroynMobarenaUtils.configuratingButton.containsKey(p)) {
					
					Arena ar = HeroynMobarenaUtils.configuratingButton.get(p);
					HeroynMobarenaUtils.configureButtonSuccess(p, ar, loc);
					
				}		
			}			
		}			
	}
	
	
	
	//BUTTON DETECTION
	//
	//TODO
	

}
