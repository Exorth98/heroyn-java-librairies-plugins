package net.heroyn.mobarena.customEvents;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MaMobDamageEvent extends Event {

	private Entity mobEntity;
	private double resistance;
	private double damage;
	
	
	
	public MaMobDamageEvent(Entity mobEntity, double resistance,double damage) {
		this.mobEntity = mobEntity;
		this.resistance = resistance;
		this.damage = damage;
	}

	

	public Entity getMobEntity() {
		return mobEntity;
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
