package fr.exorth.events;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.exorth.MagicChest;
import fr.exorth.util.ChestAnimation;
import fr.exorth.util.ChestMenu;
import fr.exorth.util.Utils;

public class ChestMenuInteract implements Listener {
	
	@EventHandler
	public void onChestMenuInteract(InventoryClickEvent e){
		
		if(e.getSlotType() != SlotType.OUTSIDE){
			
			if(e.getClickedInventory().getName().equals("§l§0Coffre Magique")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.ENDER_CHEST){
					
					Player p = (Player) e.getWhoClicked();
									
					if(!MagicChest.getInstance().inTirage.containsKey(p.getName())){
						
						if(Utils.checkForKey(p)){
						
							new ChestAnimation();
							ChestAnimation.playAnimation(p);
						
						}						
					}else{
						p.sendMessage("§cVous devez attendre la fin de votre tirage actuel");
					}
					
				}
				if(e.getCurrentItem().getType() == Material.PAPER){
					
					Player p = (Player) e.getWhoClicked();
					p.performCommand("gains");
					ChestMenu.open(p);
					
				}
				
				if(e.getCurrentItem().getType() == Material.TRIPWIRE_HOOK){
					
					Player p = (Player) e.getWhoClicked();
					
					if(Utils.getBoughtKeyNumber(p.getUniqueId().toString())>0){
						
						
						if(!invFull(p)){
							
							MagicChest.getInstance().getServer().dispatchCommand(MagicChest.getInstance().getServer().getConsoleSender(), "givemagickey " + p.getName() + " 1");
							
							Utils.removeOneBoughtKeys(p.getUniqueId().toString());
							ChestMenu.open(p);

						}else{
							p.sendMessage("§cVotre inventaire est plein !");
						}
						
					}else{
						
						p.sendMessage("§cVous n'avez pas de clé à retirer");
						
					}

					
				}
			}
			if(e.getClickedInventory().getName().contains("Tirage")){
				e.setCancelled(true);
			}
			
		if(e.getWhoClicked().getOpenInventory() !=null){
			if(e.getWhoClicked().getOpenInventory().getTitle().equals("§l§0Coffre Magique")){
				e.setCancelled(true);
			}
		}
			
		}
	}
	
	@EventHandler
	public void onTirageMenuClose(InventoryCloseEvent e){
		
		if(e.getInventory().getName().contains("Tirage") && MagicChest.getInstance().inTirage.containsKey(e.getPlayer().getName())){
			e.getPlayer().sendMessage("§6Votre Tirage est toujours en cours ...");
		}
		
		
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;          
	}

}
