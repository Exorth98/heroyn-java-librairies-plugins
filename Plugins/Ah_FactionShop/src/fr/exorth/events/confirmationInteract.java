package fr.exorth.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.util.Buy;
import fr.exorth.util.Sell;

public class confirmationInteract implements Listener {
	
    
    @EventHandler
    public void onConfInvInteract(InventoryClickEvent e){
    	
    	Player p = (Player) e.getWhoClicked();
    	
    	if(e.getSlotType() != SlotType.OUTSIDE){
    		
    		if(e.getClickedInventory().getName().contains("§cConfirmation")){
    			e.setCancelled(true);
    			
    			if(e.getClickedInventory().getName().contains(":")){
        			String type = e.getClickedInventory().getName().split(": ")[1];
        			
        			ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
        			ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
        			ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
        			ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
        			
        			ItemStack item = e.getClickedInventory().getItem(13);
        			
        			if(e.getCurrentItem().equals(cancel)){
        				p.sendMessage("§cTransaction annulée");
        				p.closeInventory();
        			}
        			else if(e.getCurrentItem().equals(confirm)) {
            			if(type.equalsIgnoreCase("Achat")){
            				new Buy().doBuy(item,p);
            			}
            			else if(type.equalsIgnoreCase("Retirer de la vente")){
            				new Sell().cancelSell(item, p);
            			}
        			}
    			}else{
        			ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
        			ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
        			ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
        			ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
        			
        			if(e.getCurrentItem().equals(cancel)){
        				p.sendMessage("§cTransaction annulée");
        				p.closeInventory();
        			}
        			
        			else if(e.getCurrentItem().equals(confirm)) {
        				
        				ItemStack item = e.getClickedInventory().getItem(12);
        				List<String> infos = e.getClickedInventory().getItem(14).getItemMeta().getLore();
        				
        				int hours = Integer.parseInt(infos.get(infos.size()-2).split(": §a")[1]);
        				double prix = Double.parseDouble(infos.get(infos.size()-3).split(": §a")[1]);;
        				double taxe = Double.parseDouble(infos.get(infos.size()-1).split(": §4")[1]);;
        				
        				
        				if(p.getInventory().contains(item)){
        					new Sell().doSell(item, p, taxe, prix, hours);
        				}else{
        					p.sendMessage("§cTu n'as plus l'item dans ton inventaire !");
        				}
        				
        				
        				p.closeInventory();
        				
        				
        			}
    				
    				
    			}

  			
    			
    		}
    		
    		
    	}
    	
    	
    }
	
}
