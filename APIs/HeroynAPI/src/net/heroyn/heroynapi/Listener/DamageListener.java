package net.heroyn.heroynapi.Listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.utils.Flags;
import net.heroyn.heroynapi.utils.Flags.FlagsEnum;

public class DamageListener implements Listener {

	  HeroynAPI pl;
	  
	  public DamageListener(HeroynAPI pl)
	  {
	    this.pl = pl;
	  }
	  
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity ent = event.getEntity();
		if (Flags.hasFlag(ent, FlagsEnum.GODMODE.getId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity ent = event.getEntity();
		if (Flags.hasFlag(ent, FlagsEnum.GODMODE.getId())) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
		Entity ent = event.getEntity();
		if (Flags.hasFlag(ent, FlagsEnum.GODMODE.getId())) {
			event.setCancelled(true);
		}
	}
}
