package net.heroyn.mobarena.customEvents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.heroyn.mobarena.game.player.MobarenaFighter;

public class FighterDamageEvent extends Event {

	private Entity playerEntity;
	private Player p;
	private MobarenaFighter maF;
	private double resistance;
	private double damage;
	
	
	
	public FighterDamageEvent(Entity playerEntity, double resistance,double damage) {
		this.playerEntity = playerEntity;
		this.p = (Player) playerEntity;
		this.maF = MobarenaFighter.getFromPlayer(p);
		this.resistance = resistance;
		this.damage = damage;
	}

	

	public Entity getPlayerEntity() {
		return playerEntity;
	}



	public Player getP() {
		return p;
	}



	public MobarenaFighter getMaF() {
		return maF;
	}



	public double getResistance() {
		return resistance;
	}



	public double getDamage() {
		return damage;
	}



	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
