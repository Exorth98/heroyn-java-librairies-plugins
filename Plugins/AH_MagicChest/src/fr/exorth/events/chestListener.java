package fr.exorth.events;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.exorth.MagicChest;
import fr.exorth.util.ChestMenu;

public class chestListener implements Listener {
	
	public FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<Location> chestsLocs = (ArrayList<Location>) config.getList("chestslocs");
	
	@EventHandler
	public void OnChestOpen(PlayerInteractEvent e){
		
		if(e.getAction()== Action.RIGHT_CLICK_BLOCK){
			
			if(e.getClickedBlock().getType() == Material.ENDER_CHEST){
			
				if(chestsLocs != null){
					if(chestsLocs.contains(e.getClickedBlock().getLocation())){
					
						e.setCancelled(true);
						Player p = (Player)e.getPlayer();
						
						if(MagicChest.getInstance().inTirageInv.containsKey(p.getName())){
							p.openInventory(MagicChest.getInstance().inTirageInv.get(p.getName()));
						}else{
							ChestMenu.open(p);
						}
						
						
					}
				}				
			}			
		}		
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		if(chestsLocs != null){
			if(chestsLocs.contains(e.getBlock().getLocation())){
				
				if(e.getPlayer().getItemInHand().getType() == Material.BARRIER && e.getPlayer().isOp()){
					
					chestsLocs.remove(e.getBlock().getLocation());
					config.set("chestslocs", chestsLocs);
					MagicChest.getInstance().saveConfig();
					
					for(Entity en :  e.getBlock().getLocation().getWorld().getEntities()) {
					    if(en.getLocation().distance(e.getBlock().getLocation()) <= 1) 
					        en.remove();
					}
					
				}else{
					e.setCancelled(true);
				}
				
			}				
		}	

	}
}
