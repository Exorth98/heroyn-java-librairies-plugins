package fr.exorth.events;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import fr.exorth.pickaxes.PickaxeType;

public class MGToolEvent implements Listener {
	
	@EventHandler
	public void onToolDamage(PlayerItemDamageEvent e){
		
		ItemStack item = e.getItem();
		
		if(isSpecialPickaxe(item)){
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		
		if(isSpecialPickaxe(e.getItemDrop().getItemStack())){
			e.setCancelled(true);
		}
		
	}

	private boolean isSpecialPickaxe(ItemStack item) {
		
		ArrayList<ItemStack> pickaxes = new ArrayList<>();
		for(PickaxeType pt: PickaxeType.values()){
			pickaxes.add(pt.getItem());
		}
		
		for(ItemStack pickaxe : pickaxes){
			
			if(pickaxe.equals(item)){
				return true;
			}
			
		}
		
		return false;
	}

}
