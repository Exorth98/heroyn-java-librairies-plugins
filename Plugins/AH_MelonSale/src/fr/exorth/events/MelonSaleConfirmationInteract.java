package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.exorth.MelonSale;
import net.milkbowl.vault.economy.Economy;

public class MelonSaleConfirmationInteract implements Listener {
	
	public Economy economy = null;
	
	
    @EventHandler
    public void onConfInvInteract(InventoryClickEvent e){
    	
    	Player p = (Player) e.getWhoClicked();
    	
    	if(e.getSlotType() != SlotType.OUTSIDE){  		
    		
    		if(e.getClickedInventory().getName().contains("§0Confirmation de vente")){
    			
    			e.setCancelled(true);
    			
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
    				
    				double[] infos = MelonSale.getInstance().inConfirmation.get(p);
    				int amount = (int) infos[0];
    				double price = infos[1];
    				

                    p.getInventory().removeItem(new ItemStack[] {
                            new ItemStack(item.getType(), amount) });
                    
                    double balance=0.0;
                    
                    if(setupEconomy()){
                    	
                    	economy.depositPlayer(p, price);
                    	balance = economy.getBalance(p);
                    	
                    }
                    
                    String stype = getSType(item);
    				
                    p.sendMessage("§eVous avez vendu §a"+amount+" "+stype+" §epour §a"+price+" Dreams");
                    p.sendMessage("§eVotre nouveau solde est de §a"+balance+" Dreams");
                    
    				p.closeInventory();
    				
    				
    			}
    			
    			
    			
    		}    		
    	}    	
    }
    
    private String getSType(ItemStack item) {
		
    	switch(item.getType()){
    	
    	case MELON_BLOCK:
    		return "Melons";
    	case CACTUS:
    		return "Cactus";
		default:
			return "???";
    	
    	}

	}

	@EventHandler
    public void onInvClose(InventoryCloseEvent e){
    	
    	if(e.getInventory().getTitle().contains("§0Confirmation de vente")){
    		Player p = (Player) e.getPlayer();
            MelonSale.getInstance().inConfirmation.remove(p);
    	}
    	
    	
    }
    
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = MelonSale.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
