package net.heroyn.heroynapi.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.heroyn.heroynapi.HeroynAPI;

public class PlayerJoinListener implements Listener {
	
	HeroynAPI api;
	
	public PlayerJoinListener(HeroynAPI api) {
		this.api = api;
	}

	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		
		
		// init le joueur
		initPlayer(player);
		
		//init balance
		if(!HeroynAPI.getMoneyInstance().hasAccount(player)) {			
			HeroynAPI.getMoneyInstance().createAcount(player);
		}
	}
	
	
	private void initPlayer(Player player) {
		//HeroynAPI.getUserInstance().createAcount(player);
	}
}
