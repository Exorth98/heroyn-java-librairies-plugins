package net.heroyn.mobarena.customEvents;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.mobs.MaMobStats;

public class MaMobDamageByFighterEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private MobarenaFighter maF;
	private MaMobStats mobStats;
	private Player fighterEntity;
	private LivingEntity mobEntity;
	private MobarenaGame game;	
	private boolean arrow;
	
	public MaMobDamageByFighterEvent(Player fighterEntity, LivingEntity mobEntity, MobarenaFighter maF, MaMobStats mobStats, boolean arrow) {

		this.maF = maF;
		this.mobStats = mobStats;
		this.game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());
		this.fighterEntity = fighterEntity;
		this.mobEntity = mobEntity;
		this.arrow = arrow;
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
	
	public boolean isArrowThrow() {
		return arrow;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
