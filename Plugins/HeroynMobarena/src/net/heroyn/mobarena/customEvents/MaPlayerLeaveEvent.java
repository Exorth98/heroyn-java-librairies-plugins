package net.heroyn.mobarena.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.InMobarenaPlayer;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.game.player.MobarenaSpectator;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;

public class MaPlayerLeaveEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private MobarenaGame game;
	private Player p;
	private InMobarenaPlayer inMaP;
	private MobarenaFighter maF;
	private MobarenaSpectator maS;
	private MobarenaSuitingupPlayer maSupP;
	private MaLeaveReason reason;
	
	public MaPlayerLeaveEvent(MobarenaGame game, Player p, MaLeaveReason reason) {
		
		this.game = game;
		this.p = p;
		this.inMaP = InMobarenaPlayer.getFromPlayer(p);
		this.maF = MobarenaFighter.getFromPlayer(p);
		this.maS = MobarenaSpectator.getFromPlayer(p);
		this.maSupP = MobarenaSuitingupPlayer.getFromPlayer(p);
		this.reason = reason;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	public MaLeaveReason getReason() {
		return this.reason;
	}
	
	public boolean playerIsSpectator() {
		
		return (inMaP instanceof MobarenaSpectator);		
	}
	
	public boolean playerIsFighter() {
		
		return (inMaP instanceof MobarenaFighter);
	}
	public boolean isPlayerSuitingup() {

		return (inMaP instanceof MobarenaSuitingupPlayer);
	}
	
	public MobarenaFighter getMobarenaFighter() {
		return this.maF;
	}
	
	public MobarenaSpectator getMobarenaSpectator() {
		return this.maS;
	}
	public MobarenaSuitingupPlayer getMobarenaSuitingupPlayer() {
		return this.maSupP;
	}
	
	public MobarenaGame getMobarenaGame() {
		return this.game;
	}
	
	@Override
	public HandlerList getHandlers() {

		return handlers;
	}
	public static HandlerList getHandlerList() {

		return handlers;
	}



}
