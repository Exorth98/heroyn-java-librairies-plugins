package net.heroyn.mobarena.customEvents;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.mobs.MaMobStats;

public class FighterDamageByMaMobEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private MobarenaFighter maF;
	private MaMobStats mobStats;
	private Player fighterEntity;
	private LivingEntity mobEntity;
	private MobarenaGame game;	
	
	


	public FighterDamageByMaMobEvent(Player fighterEntity, LivingEntity mobEntity, MobarenaFighter maF, MaMobStats mobStats){

		this.maF = maF;
		this.mobStats = mobStats;
		this.fighterEntity = fighterEntity;
		this.mobEntity = mobEntity;
		this.game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());
	}

	
	
	public MobarenaFighter getMaF() {
		return maF;
	}

	public MaMobStats getMobStats() {
		return mobStats;
	}

	public Player getFighterEntity() {
		return fighterEntity;
	}

	public LivingEntity getMobEntity() {
		return mobEntity;
	}

	public MobarenaGame getGame() {
		return game;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
