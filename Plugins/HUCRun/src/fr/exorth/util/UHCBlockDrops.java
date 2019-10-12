package fr.exorth.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class UHCBlockDrops implements Listener {
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e){
		
		if(e.getBlock().getType()==Material.IRON_ORE){
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			
			Location breakLoc = e.getBlock().getLocation();
			breakLoc.getWorld().dropItemNaturally(breakLoc, new ItemStack(Material.IRON_INGOT,2));
			
		}
		
		if(e.getBlock().getType()==Material.GRAVEL){
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			
			Location breakLoc = e.getBlock().getLocation();
			breakLoc.getWorld().dropItemNaturally(breakLoc, new ItemStack(Material.ARROW,2));
			
		}
		
	}

}
