package net.heroyn.heroynserverapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.heroyn.heroynserverapi.nms.player.NMSPlayer;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		NMSPlayer.getPacketsToSend().forEach(o -> NMSPlayer.sendPacket(player, o));
        NMSPlayer.getPacketsToSendPlayer().keySet().stream().filter(uuid -> uuid.equals(player.getUniqueId())).findAny().ifPresent(uuid2 -> NMSPlayer.sendPacket(player, NMSPlayer.getPacketsToSendPlayer().get(uuid2)));
        
        // Ajout d'item au joueur
     //   player.getInventory().setItem(8, new ItemMenuShopPrincipal().getItems());
	}
}
