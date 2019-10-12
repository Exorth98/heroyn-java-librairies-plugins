package fr.exorth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.exorth.FactionTuto;
import fr.exorth.util.FTUtils;

public class FTMoveEvent implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if(FactionTuto.getInstance().inTuto.containsKey(p)){
			
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		
		if(FactionTuto.getInstance().inTuto.containsKey(p)){
			
			FTUtils.exitTuto(p);
		}

	}

}
