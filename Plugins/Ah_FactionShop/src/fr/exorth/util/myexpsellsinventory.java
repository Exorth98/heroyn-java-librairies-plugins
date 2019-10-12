package fr.exorth.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

public class myexpsellsinventory {
	
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
		
		ArrayList <ItemStack> myexpitems = filtrer(factionshop.getInstance().itemsinsell,p);
		
		
	/*	for(int i =0; i<myinsellitems.size();i++){
			myinsellitems.set(i, addcancel(myinsellitems.get(i)));
		}*/
		
		if(page==1){
			factionshop.getInstance().myexpsellinventories.remove(p);
			factionshop.getInstance().myexpsellinventories.put(p,new ArrayList<>());
		}
		
		Inventory exp = Bukkit.createInventory(null, 54, "§l§0Mes items expirés | Page: " + page);
		
		for(int i=0; i<9; i++){
			exp.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)1));
		}
		for(int i=36; i<45; i++){
			exp.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)1));
		}
		/*for(int i=10;i<=34 ;i+=2){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		for(int i=9;i<=35 ;i+=2){
			sell.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)14));
		}*/
		for(int i=45;i<=53 ;i++){
			exp.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		
		double dreams =0;
		if(setupEconomy()){
			dreams = economy.getBalance(p);
		}
		
		ItemStack money = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta moneyM = (SkullMeta) money.getItemMeta();
		moneyM.setDisplayName("§6Mes Dreams: §3" + dreams);
		moneyM.setOwner(p.getName());
		money.setItemMeta(moneyM);
		exp.setItem(47, money);
		
		ItemStack own = new ItemStack(Material.ENDER_CHEST,1);
		ItemMeta ownM = own.getItemMeta();
		ownM.setDisplayName("§6Retour au shop");
		own.setItemMeta(ownM);
		exp.setItem(49,own );
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		ItemMeta infosM = infos.getItemMeta();
		infosM.setDisplayName("§6Informations");
		infosM.setLore(Arrays.asList(
				"§1  ",
				"§3Cliquer sur un item pour le récuperer"
				));
		infos.setItemMeta(infosM);
		exp.setItem(51,infos );

		
		
		if(myexpitems != null){
			
			if(page==1){
				count = myexpitems.size();
			}else{
				addPreviewArrow(exp);
			}
			
			
			for(int i=9;i<=35 && count>0 ;i+=2){
				exp.setItem(i,myexpitems.get(count-1));
				count--;
			}
			
			
			if(count>0){
				
				addNextArrow(exp);
				createInventories(p,page+1,count);
			}
		}
		
		factionshop.getInstance().myexpsellinventories.get(p).add(exp);
		
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
				
				String refitem = lores.get(lores.size()-1).split(":")[1];

				UUID uuid = factionshop.getInstance().refs.get(refitem);
				if(p.getUniqueId().equals(uuid) && lores.get(lores.size()-2).contains("Item éxpiré")){
					item = addrecup(item);
					mysellitems.add(item);
				}	
				
			}	
			return mysellitems;	
		}
		return null;
	}

	private ItemStack addrecup(ItemStack item) {
		
		ItemMeta itemM = item.getItemMeta();
		List<String> lores = itemM.getLore();
		lores.set(0,"§a[V] Récuperer l'item [V]");
		itemM.setLore(lores);
		item.setItemMeta(itemM);
		return item;
		
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
