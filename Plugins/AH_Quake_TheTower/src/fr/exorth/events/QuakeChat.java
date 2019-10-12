package fr.exorth.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;


@SuppressWarnings("deprecation")
public class QuakeChat implements Listener {
	
	@EventHandler
	public void onChat(PlayerChatEvent e){
		e.setCancelled(true);
		
		if(e.getPlayer().isOp()){
			Bukkit.broadcastMessage("§4[Quake Chat] " + e.getPlayer().getDisplayName() + " : " + e.getMessage());
		}else{
			Bukkit.broadcastMessage("§7[Quake Chat] " + e.getPlayer().getDisplayName() + " : " + e.getMessage());
		}
		
	}

}
