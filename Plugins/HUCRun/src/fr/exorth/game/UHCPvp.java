package fr.exorth.game;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.exorth.UHCRun;
import fr.exorth.api.WorldSounds;
import fr.exorth.util.UHCSkullRegen;

public class UHCPvp implements Listener {
	
	public static HashMap<Player, Integer> kills = new HashMap<>();
	
	
	@EventHandler
	public void fakeDeath2(EntityDamageByEntityEvent e){
		
		if(!UHCState.isState(UHCState.WAIT) || !UHCState.isState(UHCState.PREGAME)){
			if(e.getEntity() instanceof Player){
				
				double damage = e.getDamage();
				Player victim = (Player) e.getEntity();
				double health = victim.getHealth();
				
				if(damage >= health){
					victim.setGameMode(GameMode.SPECTATOR);
					Bukkit.broadcastMessage("Le Joueur "+ victim.getName() + "est éliminé");
					UHCRun.getInstance().playerInGame.remove(victim.getUniqueId());
					new WorldSounds(victim.getLocation()).playSound(Sound.ENTITY_LIGHTNING_THUNDER);
					
					
					if(e.getDamager() instanceof Player){
						Player killer = (Player) e.getDamager();
						
						if(!kills.containsKey(killer)){
							kills.put(killer,0);
						}
						
						kills.put(killer,kills.get(killer)+1);
						UHCSkullRegen.dropSkull(victim);
						
					}
					
					if(e.getDamager() instanceof Arrow){
						Arrow fleche = (Arrow) e.getDamager();
						
						if(fleche.getShooter() instanceof Player){
							Player killer = (Player) fleche.getShooter();
							
							if(!kills.containsKey(killer)){
								kills.put(killer,0);
							}
							
							kills.put(killer,kills.get(killer)+1);
							UHCSkullRegen.dropSkull(victim);
						}

						

						
					}	
				}
				
			}
		}
		else{
			e.setCancelled(true);
		}

		
	}

}
