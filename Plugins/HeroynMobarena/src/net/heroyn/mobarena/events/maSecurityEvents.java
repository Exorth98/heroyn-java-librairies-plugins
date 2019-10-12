package net.heroyn.mobarena.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.heroyn.heroynapi.utils.Cuboid;
import net.heroyn.heroynapi.utils.Zone;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;

public class maSecurityEvents implements Listener {
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		if(HeroynMobarenaUtils.isSuitingup(p)) {
			MobarenaSuitingupPlayer maS = MobarenaSuitingupPlayer.getFromPlayer(p);
			if(p.getWorld().getName().equals(maS.getArena().getZone().getWorld(true).getName())) {
				
				if(p.getWorld().getName().equalsIgnoreCase("Mobarena")) {
						
					Zone portal = new Cuboid(p.getWorld(), -212, 216, 119, -217, 223, 119);
					if(portal.isInZone(e.getTo()) && !portal.isInZone(e.getFrom()))
						p.performCommand("ma start");		
				} 
			}
		}
	}
	
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		
		Player p = e.getPlayer();
		
		if(HeroynMobarenaUtils.isInFight(p) || HeroynMobarenaUtils.isSuitingup(p)) {
			
			e.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onArmorClick(InventoryClickEvent e) {
		
		if(e.getSlotType() == SlotType.OUTSIDE)return;
		
		Player p = (Player) e.getWhoClicked();
		MobarenaFighter maF = MobarenaFighter.getFromPlayer(p);
		if(maF != null)	
			if(e.getSlotType() == SlotType.ARMOR)
				e.setCancelled(true);

		
	}
	
	@EventHandler
	public void onFoodLoose(FoodLevelChangeEvent e) {
		
		if(e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();	
			
			if(HeroynMobarenaUtils.isInFight(p) || HeroynMobarenaUtils.isSuitingup(p)){
				e.setCancelled(true);
			}
			
		}
		
	
	}
	
	@EventHandler
	public void OnItemUse(PlayerItemDamageEvent e) {
		
		Player p = e.getPlayer();
		if(MobarenaFighter.getFromPlayer(p) != null) {
			
			e.setCancelled(true);
		}
		
	}
	
	
	
	/*@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		
		Player p = e.getPlayer();
		
		if(HeroynMobarenaUtils.isInGame(p)) {
			
			p.sendMessage("§cPas de téléportation en Mobarena, quitte le pour te téléporter (/ma leave)");
			e.setCancelled(true);
		}
		
		boolean goInArena = false;
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values())
			if(game.getArena().getZone().isInZone(e.getTo()))
				goInArena = true;
		
		if(goInArena) {
			
			p.sendMessage("§cPas de téléportation dans un Mobarena !");
			e.setCancelled(true);			
		}
	}*/
	
	
	
	@EventHandler
	public void onFrienflyFire(EntityDamageByEntityEvent e) {
		
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			if(HeroynMobarenaUtils.isInGame(p)) {
				
				e.setCancelled(true);
			}
			
		}
		else if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
			
			Player p = (Player) e.getEntity();
			Arrow arrow = (Arrow) e.getDamager();
			
			if(arrow.getShooter() instanceof Player && HeroynMobarenaUtils.isInGame(p)) {
				e.setCancelled(true);
			}
			
			
		}
		
	}

}
