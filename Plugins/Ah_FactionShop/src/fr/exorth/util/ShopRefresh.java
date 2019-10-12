package fr.exorth.util;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.factionshop;

public class ShopRefresh {	
	
	public static void refreshItem(ItemStack item, Date now){
		
		String cooldown = OtherUtils.getItemCooldown(item, now);
		OtherUtils.setItemLoreCooldown(item, cooldown);
		
		
	}

	public void refreshInventories() {
		
		for (Player p : Bukkit.getOnlinePlayers()){
			
			if(p.getOpenInventory() != null){
				
				if(p.getOpenInventory().getTitle().contains("Shop joueurs")){
					new insellinventory().createInventories(p, 1, 0);	
					int indPage = OtherUtils.getPageInd_sellinventories(Integer.parseInt(p.getOpenInventory().getTitle().split(": ")[1]),p);	
					if(indPage != -1){
						copyInventory(factionshop.getInstance().sellinventories.get(p).get(indPage),p);
						//p.openInventory(factionshop.getInstance().sellinventories.get(p).get(indPage));
					}else{
						p.openInventory(factionshop.getInstance().sellinventories.get(p).get(0));
					}	
				}
				if(p.getOpenInventory().getTitle().contains("items en vente")){
					int indPage = OtherUtils.getPageInd_mysellinventories(Integer.parseInt(p.getOpenInventory().getTitle().split(": ")[1]),p);
					new mysellsinventory().createInventories(p, 1, 0);
					if(indPage != -1){
						//p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(indPage));
						copyInventory(factionshop.getInstance().mysellinventories.get(p).get(indPage),p);
					}else{
						p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(0));
					}
				}			
			}
		}
		
	}

	private void copyInventory(Inventory inv, Player p) {
		
		for(int i=0;i<inv.getSize();i++){
			p.getOpenInventory().setItem(i, inv.getItem(i));
		}
		
		
	}

}
