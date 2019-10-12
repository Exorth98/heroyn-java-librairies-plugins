package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.Quake;

public class QuakeMenuInteract implements Listener {
	
	@EventHandler
	public void onMenuInteract(InventoryClickEvent e){
		
		e.setCancelled(true);
		
		Player p = (Player) e.getWhoClicked();
		
		
		if(e.getSlotType() != SlotType.OUTSIDE){
			
			if(e.getCurrentItem().getType() != null){
				
				Material mat = e.getCurrentItem().getType();
				
				if(e.getInventory().getName().equalsIgnoreCase("§3Choix du gun")){
					
					if(mat != Material.STAINED_GLASS_PANE && mat != Material.AIR){
						
						p.getInventory().remove(new ItemStack(Quake.getInstance().hoes.get(p),1));
						
						ItemStack newhoe = new ItemStack(mat,1);
						ItemMeta hoeM = newhoe.getItemMeta();
						hoeM.setDisplayName("§3Choix du Gun");
						newhoe.setItemMeta(hoeM);
						
						p.getInventory().setItem(2, newhoe);
						
						Quake.getInstance().hoes.remove(p);
						Quake.getInstance().hoes.put(p, mat);
						
						p.sendMessage("§3Vous avez changé de Gun !");
						
						p.closeInventory();				
					}
				}
			}
			
		}
		
	}
}
