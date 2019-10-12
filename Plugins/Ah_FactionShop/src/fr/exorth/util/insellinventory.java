package fr.exorth.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.exorth.factionshop;
import net.milkbowl.vault.economy.Economy;

public class insellinventory {
	
	public Economy economy = null;
	
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = factionshop.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

	
	public void createInventories(Player p, int page, int count){	
		
		ArrayList <ItemStack> insellitems = filtrer(factionshop.getInstance().itemsinsell,p);
		
		if(page==1){
			factionshop.getInstance().sellinventories.remove(p);
			factionshop.getInstance().sellinventories.put(p,new ArrayList<>());
		}
		
		
		
		Inventory sell = Bukkit.createInventory(null, 54, "§l§0Shop joueurs | Page: " + page);
		
		for(int i=0; i<9; i++){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		}
		for(int i=36; i<45; i++){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		}
		/*for(int i=10;i<=34 ;i+=2){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		for(int i=9;i<=35 ;i+=2){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)14));
		}*/
		for(int i=45;i<=53 ;i++){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		
		double dreams = 0.0;
		if(setupEconomy()){
			
			dreams = economy.getBalance(p);
			
		}
		
		ItemStack money = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta moneyM = (SkullMeta) money.getItemMeta();
		moneyM.setDisplayName("§6Mes Dreams: §3" + dreams);
		moneyM.setOwner(p.getName());
		money.setItemMeta(moneyM);
		sell.setItem(47, money);
		
		ItemStack own = new ItemStack(Material.CHEST,1);
		ItemMeta ownM = own.getItemMeta();
		ownM.setDisplayName("§6[Mes items]");
		ArrayList<String> lores = new ArrayList<>();
		lores.add("§aClic gauche : §bMes items en vente");
		lores.add("§aClic droit : §bMes items expirés");
		ownM.setLore(lores);
		own.setItemMeta(ownM);
		sell.setItem(49,own );
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		ItemMeta infosM = infos.getItemMeta();
		infosM.setDisplayName("§6Informations");
		infosM.setLore(Arrays.asList(
				"§1  ",
				"§3Cliquer sur un item pour l'acheter",
				"§6-----------------------------------------------",
				"§3Pour mettre en vente un item : /hdv sell <prix> <temps>",
				"§3avec le stack à vendre en main",
				"§6-----------------------------------------------",
				"§cATTENTION:",
				"§cLors d'une mise en vente, vous payez une taxe",
				"§cde 10%, 15% ou 20% prelevée sur le prix, par",
				"§crapport au temps de mise en vente de l'item",
				"§cCette taxe n'est jamais remboursée."
				));
		infos.setItemMeta(infosM);
		sell.setItem(51,infos );
		
		
		
		
		if(insellitems != null){
			
			if(page==1){
				count = insellitems.size();
			}else{
				addPreviewArrow(sell);
			}
			
			
			for(int i=9;i<=35 && count>0 ;i+=2){
				sell.setItem(i,insellitems.get(count-1));
				count--;
			}
			
			
			if(count>0){
				
				addNextArrow(sell);
				createInventories(p,page+1,count);
			}
		}
		
		factionshop.getInstance().sellinventories.get(p).add(sell);
		
	} 
	
	private ArrayList<ItemStack> filtrer(ArrayList<ItemStack> insellitemsunfiltred, Player p) {
		
		if(insellitemsunfiltred != null){
			ArrayList <ItemStack > mysellitems = new ArrayList<>();
			for (int j =0; j<insellitemsunfiltred.size(); j++){
				/*@SuppressWarnings("deprecation")
				ItemStack item = new ItemStack(insellitemsunfiltred.get(j).getType(),insellitemsunfiltred.get(j).getAmount(),insellitemsunfiltred.get(j).getData().getData());
				
				itemM.setLore(insellitemsunfiltred.get(j).getItemMeta().getLore());
				item.setItemMeta(itemM);*/
				ItemStack item = insellitemsunfiltred.get(j).clone();
				ItemMeta itemM = item.getItemMeta();
				
				List<String> lores = itemM.getLore();

				if(!lores.get(lores.size()-2).contains("Item éxpiré")){
					mysellitems.add(item);
				}	
			}	
			return mysellitems;	
		}
		return null;
	}

	private static void addNextArrow(Inventory sell) {
		
		ItemStack next = new ItemStack(Material.ARROW,1);
		ItemMeta nextM = next.getItemMeta();
		nextM.setDisplayName("§6Page suivante");
		next.setItemMeta(nextM);
		
			sell.setItem(53, next);		
	}
	
	private static void addPreviewArrow(Inventory sell) {
		
		ItemStack previous = new ItemStack(Material.ARROW,1);
		ItemMeta previousM = previous.getItemMeta();
		previousM.setDisplayName("§6Page précédente");
		previous.setItemMeta(previousM);
		
		sell.setItem(45, previous);	
	}

}
