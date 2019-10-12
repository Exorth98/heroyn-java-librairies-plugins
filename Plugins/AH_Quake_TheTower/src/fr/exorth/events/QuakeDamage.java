package fr.exorth.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.exorth.util.QuakeStuff;
import fr.exorth.util.QuakeTeleport;

public class QuakeDamage implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntityType() == EntityType.PLAYER){
			e.setCancelled(true);
		}
		
		if(e.getEntity() instanceof Player && e.getCause()==DamageCause.VOID){
			Player p = (Player)e.getEntity();
			p.getInventory().clear();
			QuakeTeleport.teleport(p);;
			QuakeStuff.giveStuff(p);
		}
	}

}
