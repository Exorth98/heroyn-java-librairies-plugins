package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import fr.exorth.util.NPCMenu;

public class MGnpcListener implements Listener {
	
    @EventHandler
    public void NPCDamage(EntityDamageEvent e){
       
        Entity ent = e.getEntity();
        
        if(isCustomNPC(ent)) {
            e.setCancelled(true);
        }
       
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void NPCDamage2(EntityDamageByEntityEvent e){
       
        Entity ent = e.getEntity();
        
        if(isCustomNPC(ent)) {
            
            if(e.getDamager() instanceof Player){
            	Player p = (Player) e.getDamager();
            	if(p.getItemInHand().getType() == Material.BARRIER && p.isOp()){
            		ent.remove();
            	}else{
                    NPCMenu.choose(p);
            	}            	
            }
            e.setCancelled(true);
        }
       
    }
    
   
    @EventHandler
    public void interactwithNPC(PlayerInteractEntityEvent e){
       
        Player p = e.getPlayer();
        Entity ent = e.getRightClicked();
       
        if(isCustomNPC(ent)){
            e.setCancelled(true);
            NPCMenu.choose(p);
           
        }
       
       
    }
 
    private boolean isCustomNPC(Entity ent) {
       
        if(ent instanceof Villager){
                   
            Villager npc = (Villager) ent;
           
            if(npc.isCustomNameVisible() && npc.getCustomName() != null && npc.getCustomName().equalsIgnoreCase("§b§lLocation et gestion des Pioches")){
                return true;
            }
           
        }
 
        return false;
    }

}
