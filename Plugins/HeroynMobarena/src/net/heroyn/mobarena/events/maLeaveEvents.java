package net.heroyn.mobarena.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.mobarena.customEvents.MaLeaveReason;
import net.heroyn.mobarena.customEvents.MaPlayerLeaveEvent;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.MobarenaGameState;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.game.player.MobarenaSpectator;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;


public class maLeaveEvents implements Listener {

	
	@EventHandler
	public void onMaLeave(MaPlayerLeaveEvent e) {
		
		MobarenaGame game = e.getMobarenaGame();
		
		if(e.playerIsFighter()) {
			
			MobarenaFighter maF = e.getMobarenaFighter();
			
			if(e.getReason() == MaLeaveReason.LEAVECOMMAND) {				
				game.leaveFighter(maF);				
			}
			else if(e.getReason() == MaLeaveReason.DISCONNECT) {
				game.leaveFighter(maF);
			}
			else if(e.getReason() == MaLeaveReason.UNLOAD) {
				game.FinishFighter(maF);
			}
			else if(e.getReason() == MaLeaveReason.DEATH) {
				
				game.DieFighter(maF);
			}
			
		}
		else if(e.playerIsSpectator()) {
					
			MobarenaSpectator maS = e.getMobarenaSpectator();
			game.leaveSpectator(maS);
			
		}
		else if(e.isPlayerSuitingup()) {
			
			MobarenaSuitingupPlayer maSupP = e.getMobarenaSuitingupPlayer();
			game.leaveSuitingup(maSupP);
			
		}
		
		Bukkit.broadcastMessage("joueurs après :" + game.getInGamePlayerNumber());
		
		//CHECK IF ARENA IS EMPTY
		if(game.getInGamePlayerNumber()==0 && game.getState() == MobarenaGameState.FIGHTING) {
			
			game.end();
		}
		
	}
	
	
	
	
}
