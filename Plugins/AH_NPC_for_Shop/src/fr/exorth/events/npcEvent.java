package fr.exorth.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import fr.exorth.NPCforShop;
import fr.exorth.utils.Utils;

public class npcEvent implements Listener {
	
    @EventHandler
    public void NPCDamage(EntityDamageEvent e){
       
        Entity ent = e.getEntity();
        
        if(isCustomNPC(ent)) {
            e.setCancelled(true);
        }
       
    }
    
    @EventHandler
    public void NPCDamage2(EntityDamageByEntityEvent e){
       
        Entity ent = e.getEntity();
        
        if(isCustomNPC(ent)) {
            
            if(e.getDamager() instanceof Player){
            	String pName =  ((Player)e.getDamager()).getName();
            	Location loc = ent.getLocation();
            	String shop = Utils.getShopFromLoc(loc);
            	
            	NPCforShop.getInstance().getServer().dispatchCommand(NPCforShop.getInstance().getServer().getConsoleSender(), "shop open page "+ shop + " " + pName);
            	
            }
            e.setCancelled(true);
        }
       
    }
    
   
    @EventHandler
    public void interactwithNPC(PlayerInteractEntityEvent e){
       
        Player p = e.getPlayer();
        Entity ent = e.getRightClicked();
       
        if(isCustomNPC(ent)){
        	
        	String pName =  p.getName();
        	Location loc = ent.getLocation();
        	String shop = Utils.getShopFromLoc(loc);
        	
        	NPCforShop.getInstance().getServer().dispatchCommand(NPCforShop.getInstance().getServer().getConsoleSender(), "shop open page "+ shop + " " + pName);
            e.setCancelled(true);
            
           
        }
       
       
    }
 
    private boolean isCustomNPC(Entity ent) {
       
        if(ent instanceof Villager){
                   
            Villager npc = (Villager) ent;
           
            if(npc.isCustomNameVisible() && npc.getCustomName() != null && Utils.getNPCsLoc().contains(npc.getLocation())){
                return true;
            }
           
        }
 
        return false;
    }

}
