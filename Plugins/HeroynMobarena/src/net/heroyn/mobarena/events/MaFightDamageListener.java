package net.heroyn.mobarena.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.customEvents.FighterDamageByMaMobEvent;
import net.heroyn.mobarena.customEvents.FighterDamageEvent;
import net.heroyn.mobarena.customEvents.MaLeaveReason;
import net.heroyn.mobarena.customEvents.MaMobDamageByFighterEvent;
import net.heroyn.mobarena.customEvents.MaMobDamageEvent;
import net.heroyn.mobarena.customEvents.MaPlayerLeaveEvent;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.mobs.MaMobStats;

public class MaFightDamageListener implements Listener {
	
	@EventHandler
	public void onFigherDamage(FighterDamageEvent e) {
		
		
		
	}
	
	@EventHandler
	public void onMaMobDamage(MaMobDamageEvent e) {
		
		
		
	}
	
	@EventHandler
	public void onDamageByFighter(MaMobDamageByFighterEvent e) {
		
		Player p = e.getMaF().getPlayer();
		MobarenaFighter maF = e.getMaF();
		LivingEntity ent = e.getMobEntity();
		MaMobStats mob = e.getMobStats();
		
		if(p.getEquipment().getItemInMainHand() != null) {
			if(p.getEquipment().getItemInMainHand().getType() != Material.AIR) {						
				ItemStack inHand = p.getEquipment().getItemInMainHand();					
				if(inHand.isSimilar(maF.getClasse().getWeapon())) {
			
					double damage = maF.getClasse().getDamage()  * (1-mob.getResistance());
					if(!e.isArrowThrow()) damage = maF.getClasse().getSecondaryDamage();
					
					//THE MOB DIE
					if(ent.getHealth() <= damage) {
						MobarenaGame game = HeroynMobarena.getInstance().games.get(maF.getArena().getName());
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
		
	}
	
	@EventHandler
	public void onDamageByMob(FighterDamageByMaMobEvent e) {
		
		
		Player p = e.getMaF().getPlayer();
		MobarenaFighter maF = e.getMaF();
		MaMobStats mob = e.getMobStats();

		double damage = mob.getDamage()  * (1-maF.getClasse().getResistance());			

		Bukkit.broadcastMessage(""+damage);
		
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
		
	}

}
