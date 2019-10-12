package net.heroyn.mobarena.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.entity.EntityDeathEvent;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.customEvents.MaLeaveReason;
import net.heroyn.mobarena.customEvents.MaPlayerLeaveEvent;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.mobs.MaMobStats;

public class maDamageEvents implements Listener {
	
	//DROPS AND XP CLEARING
	@EventHandler
	public void onMobDeath(EntityDeathEvent e) {
		
		Location eLoc = e.getEntity().getLocation();		
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			if(game.getArena().getZone().isInZone(eLoc)) {
				
				e.getDrops().clear();
				e.setDroppedExp(0);
				
				MaMobStats mob = MaMobStats.getStats(e.getEntity());
				if(mob != null) {
					game.addCoins(mob.getCoins());	
					game.addScore(mob.getScore());
				}
			}


		}
	}
	
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		
		Location eLoc = e.getEntity().getLocation();	
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {		
			if(game.getArena().getZone().isInZone(eLoc)) {
				
				if(e.getCause() == DamageCause.FALL)
					e.setCancelled(true);
				else if(e.getEntity() instanceof LivingEntity && e.getCause() != DamageCause.ENTITY_ATTACK){
					
					//double resistance = 0;
					
					if(e.getEntity() instanceof Player) {
						Player p = (Player) e.getEntity();
						if(MobarenaFighter.getFromPlayer(p)!= null) {							
							//resistance = MobarenaFighter.getFromPlayer(p).getClasse().getResistance();
//  						HeroynMobarena.getInstance().getServer().getPluginManager()
//							.callEvent(new FighterDamageEvent(e.getEntity(),e.getFinalDamage(),resistance));														
						}
					}
					else {
						MaMobStats mob = MaMobStats.getStats(e.getEntity());
						if(mob != null) {
							//resistance = mob.getResistance();
//							HeroynMobarena.getInstance().getServer().getPluginManager()
//							.callEvent(new MaMobDamageEvent(e.getEntity(),e.getFinalDamage(),resistance));		
						}
					}										
				}
			}			
		}			
	}
	
	//SCORE , DAMAGES AND COINS ETABLISHER
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		//FIGHTER IS DAMAGER	
		if(!(e.getEntity() instanceof Player)) {
			
			Player p = null;
			MaMobStats mob = null;
			boolean arrow = false;
			
			if(e.getDamager() instanceof Player) {
				p = (Player) e.getDamager();
				mob = MaMobStats.getStats(e.getEntity());
			}
			else if(e.getDamager() instanceof Arrow) {
				if(((Arrow)e.getDamager()).getShooter() instanceof Player) {
					p = (Player) ((Arrow)e.getDamager()).getShooter();
					mob = MaMobStats.getStats(e.getEntity());
					arrow = true;
				}
			}
			MobarenaFighter maF = MobarenaFighter.getFromPlayer(p);
			if(maF != null && mob != null) {	
				
				//////==========================================
				
				
//				HeroynMobarena.getInstance().getServer().getPluginManager()
//				.callEv
				
				LivingEntity ent = (LivingEntity) e.getEntity();
				
				double damage = arrow ? maF.getClasse().getSecondaryDamage() : maF.getClasse().getDamage();
				damage *= (1-mob.getResistance());						
				
				if(p.getItemInHand() != null) {
					if(p.getItemInHand().getType() != Material.AIR) {						
						ItemStack inHand = p.getItemInHand();							
						if(arrow || (!arrow && inHand.isSimilar(maF.getClasse().getWeapon()))) {
					
							e.setDamage(0);
							MobarenaGame game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());
							
							//THE MOB DIE
							if(ent.getHealth() <= damage) {
								game.addCoins(mob.getCoins());	
								game.addScore(mob.getScore());
								ent.setHealth(0);
							}
							//THE MOB DOESN'T DIE
							else {
								ent.setHealth(ent.getHealth()-damage);
							}
							
						}					
					}			
				}			
				
				///=============================================
			}			
		}
		
		//MOB IS DAMAGER
		if(e.getEntity() instanceof Player) {			
			Player p = (Player) e.getEntity();
			MobarenaFighter maF = MobarenaFighter.getFromPlayer(p);
			MaMobStats mob = MaMobStats.getStats(e.getDamager());
			
			if(maF != null && mob != null) {
				
				e.setDamage(0);
				
				//=================================================
				
//				HeroynMobarena.getInstance().getServer().getPluginManager()
//				.callEvent(new FighterDamageByMaMobEvent((Player)e.getDamager(),(LivingEntity)e.getEntity(),maF,mob));

				double damage = mob.getDamage()  * (1-maF.getClasse().getResistance());			
				
				//THE PLAYER DIE
				if(p.getHealth() <= damage) {
					Bukkit.broadcastMessage("§a[§b"+maF.getClasse().getName()+"§a] §b"+ maF.getPlayer().getName()+" §a est mort au combat en Mobarena");					
					//FEU D'ARTIFICE					
					MobarenaGame game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());	
					Bukkit.getPluginManager().callEvent(new MaPlayerLeaveEvent(game, maF.getPlayer(), MaLeaveReason.DEATH));
				}
				//THE PLAYER DOESN'T DIE
				else {
					p.setHealth(p.getHealth()-damage);
				}
				
				
				//=================================================
			}
		}
		
		
		
		
	}
	
	
	

}
