package net.heroyn.mobarena.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.customEvents.MaLeaveReason;
import net.heroyn.mobarena.customEvents.MaPlayerLeaveEvent;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.InMobarenaPlayer;
import net.heroyn.mobarena.utils.Arena;


public class leaveEvents implements Listener {

	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();			
		InMobarenaPlayer inMaP = InMobarenaPlayer.getFromPlayer(p);
			
		if(inMaP != null) {
			Arena ar = inMaP.getArena();
			MobarenaGame game = HeroynMobarena.getInstance().games.get(ar.getName());				
			Bukkit.getPluginManager().callEvent(new MaPlayerLeaveEvent(game, p, MaLeaveReason.DISCONNECT));	
		}
	}
	
	@EventHandler
	public void onUnload(PluginDisableEvent e) {
		
		if(!HeroynMobarena.getInstance().games.isEmpty())		
			for(MobarenaGame game : HeroynMobarena.getInstance().games.values())			
				for(InMobarenaPlayer maP : game.getAllPlayers())				
					Bukkit.getPluginManager().callEvent(new MaPlayerLeaveEvent(game, maP.getPlayer(), MaLeaveReason.UNLOAD));
	
	}
	
	/*@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		Player p = e.getEntity();	
		MobarenaFighter maF = MobarenaFighter.getFromPlayer(p);
		if(maF != null) {
			
			e.getDrops().clear();
			e.setDroppedExp(0);
			e.setDeathMessage("§a[§b"+maF.getClasse().getName()+"§a] §b"+ maF.getPlayer().getName()+" §a est mort au combat en Mobarena");
			
			//FEU D'ARTIFICE
			
			MobarenaGame game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());	
			Bukkit.getPluginManager().callEvent(new MaPlayerLeaveEvent(game, maF.getPlayer(), MaLeaveReason.DEATH));
		}
	
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		
		Player p = e.getPlayer();	
		
		if(MobarenaGame.playersDatas.containsKey(p)) {

			MobarenaDataCard datas = MobarenaGame.playersDatas.get(p);
			Location loc = datas.getLoc();
			p.setHealth(datas.getHealth());
			MobarenaGame.playersDatas.remove(p);
			e.setRespawnLocation(loc);
		}
		
	}*/
	
	
	
	
}
