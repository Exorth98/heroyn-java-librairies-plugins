package net.heroyn.mobarena.game.player;

import org.bukkit.entity.Player;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.utils.Arena;

public class MobarenaSpectator extends InMobarenaPlayer{

	
	public MobarenaSpectator(Player p, Arena ar) {		
		super(p,ar);		
	}



	public static MobarenaSpectator getFromPlayer(Player p) {
		
		MobarenaSpectator maSpectator = null ;
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaSpectator maS : game.getSpectators()) {
				
				if(maS.getPlayer().equals(p)) maSpectator = maS;				
			}		
		}		
		return maSpectator;
	}
	

}
