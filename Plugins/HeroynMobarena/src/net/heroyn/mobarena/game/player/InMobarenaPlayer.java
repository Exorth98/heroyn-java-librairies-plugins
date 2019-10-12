package net.heroyn.mobarena.game.player;

import org.bukkit.entity.Player;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.utils.Arena;

public class InMobarenaPlayer {
	
	Player p;
	Arena arena;
	
	public InMobarenaPlayer(Player p, Arena ar) {
		
		this.p = p;
		this.arena = ar;
		
	}
	
	
	public Player getPlayer() {
		return this.p;
	}
	public Arena getArena() {
		return this.arena;
	}


	public static InMobarenaPlayer getFromPlayer(Player p) {
		
		InMobarenaPlayer inMaPlayer = null ;
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(InMobarenaPlayer maP : game.getAllPlayers()) {
				
				if(maP.getPlayer().equals(p)) inMaPlayer = maP;				
			}		
		}		
		return inMaPlayer;
		
		
	}
	

}
