package net.heroyn.heroynserverapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.player.PlayerInfo;

public class PlayerLeaveListener implements Listener {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		PlayerInfo pp = PlayerInfo.instanceOf(event.getPlayer());
		HeroynServerAPI.removeAllCosmetics(pp);
	}

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		PlayerInfo pp = PlayerInfo.instanceOf(event.getPlayer());
		if (pp.getMinion() != null) {
			pp.getMinion().visible = !pp.getMinion().visible;
		}
	}
	
	@EventHandler
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		if (event.getPlayer().isOp())return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerBreak(BlockBreakEvent event) {
		if (event.getPlayer().isOp())return;
		event.setCancelled(true);
	}
}
