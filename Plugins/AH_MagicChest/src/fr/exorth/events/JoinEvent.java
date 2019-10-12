package fr.exorth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.exorth.util.GainUtils;

public class JoinEvent implements Listener {
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		if(GainUtils.getGainsNumber(p)>0){
			p.sendMessage("§6Bonjour " + p.getName() + ", vous avez §a" + GainUtils.getGainsNumber(p) + " §6gains de coffre magique en attente");
			p.sendMessage("§6Pensez a les récuperer au coffre magique ou avec /gains");
		}
		
	}

}
