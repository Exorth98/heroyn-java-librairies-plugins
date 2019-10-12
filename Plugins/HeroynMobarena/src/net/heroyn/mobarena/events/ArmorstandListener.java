package net.heroyn.mobarena.events;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import net.heroyn.mobarena.armorstands.MaClassArmorstand;
import net.heroyn.mobarena.classe.ClassesInfos;
import net.heroyn.mobarena.menus.ClasseInfosMenu;

public class ArmorstandListener implements Listener {

	@EventHandler
	public void onArmorstandInteract(PlayerInteractAtEntityEvent e) {
		
		if(e.getRightClicked() instanceof ArmorStand /*&& HeroynMobarenaUtils.isSuitingup(e.getPlayer())*/){	
			
			MaClassArmorstand as = MaClassArmorstand.getFromLoc(e.getRightClicked().getLocation());
			if(as != null) {
				e.setCancelled(true);
				e.getPlayer().performCommand("ma choose "+as.getClassName());
			}						
		}				
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArmorstandDamage(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player && e.getEntity() instanceof ArmorStand) {		
			Player p = (Player) e.getDamager();
			MaClassArmorstand as = MaClassArmorstand.getFromLoc(e.getEntity().getLocation());
			if(as != null) {		
				e.setCancelled(true);
				boolean destroy = false;
				if(p.isOp()) {
					if(p.getItemInHand() != null) {
						if(p.getItemInHand().getType() == Material.BARRIER) {
							as.unregisterInConfig();
							e.getEntity().remove();
							destroy = true;
						}	
					}		
				}
				if(!destroy) {					
					new ClasseInfosMenu(as.getClassName(), ClassesInfos.getInfos(as.getClassName()).getSymbol()).open(p);				
				}
			}				
		}		
	}
	
	
}
