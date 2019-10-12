package fr.exorth.util;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.exorth.factionshop;
import net.milkbowl.vault.economy.Economy;

public class Sell {
	
	public Economy economy = null;
	
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = factionshop.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

	public void cancelSell(ItemStack currentitem, Player p){
		
		List<String> lores = currentitem.getItemMeta().getLore();
		String refitem = lores.get(lores.size()-1).split(":")[1];

		String refiteminsell = "00";
		ItemStack iteminsell = null;
		
		if(factionshop.getInstance().itemsinsell != null){
			
			for(int i = 0; !refitem.equals(refiteminsell) && i<factionshop.getInstance().itemsinsell.size();i++){
				iteminsell = factionshop.getInstance().itemsinsell.get(i);
				List<String> lores2 = iteminsell.getItemMeta().getLore();
				refiteminsell = lores2.get(lores2.size()-1).split(":")[1];
				}
			
			if(refitem.equals(refiteminsell)){
				
				if(!invFull(p)){
					factionshop.getInstance().numberofsales.put(p.getUniqueId(),factionshop.getInstance().numberofsales.get(p.getUniqueId())-1 );
					
					factionshop.getInstance().itemsinsell.remove(iteminsell);
					factionshop.getInstance().refs.remove(refiteminsell);
					factionshop.getInstance().dates.remove(refiteminsell);		
					
					iteminsell = OtherUtils.restoreLores(iteminsell);

					p.getInventory().addItem(iteminsell);					
					p.sendMessage("§6Item récupéré avec succès");
					
					new mysellsinventory().createInventories((Player) p, 1, 0);			
					p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(factionshop.getInstance().mysellinventories.get(p).size()-1));
				}else{
					p.sendMessage("§6Tu n'as pas de place dans ton inventaire pour récuperer cet item!");
					p.closeInventory();
				}
			}else{
				p.sendMessage("§6Pas de chance, l'item est déjà vendu, actualisation du shop...");
				new mysellsinventory().createInventories((Player) p, 1, 0);			
				p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(factionshop.getInstance().mysellinventories.get(p).size()-1));
			}
			
		}else{
			p.sendMessage("§6Pas de chance, l'item est déjà vendu, actualisation du shop...");
			new mysellsinventory().createInventories((Player) p, 1, 0);			
			p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(factionshop.getInstance().mysellinventories.get(p).size()-1));
		}
		
	}
	
	
	public void doSell(ItemStack item, Player p, double taxe, double prix, int hours){
		
		double dreams=0;
		
		if(setupEconomy()){
			economy.withdrawPlayer(p, taxe); //withdraw
			dreams = economy.getBalance(p);
		}

		p.getInventory().removeItem(item);
		
		Random r = new Random();
		String ref = Integer.toString(1000000 + r.nextInt(9999999 - 1000000));											
		
		ItemStack itemtosell = OtherUtils.createItemToSell(item, prix, p.getName(), ref);
		
		Date expirationdate = OtherUtils.getExpirationDate(hours);
		
		if(factionshop.getInstance().numberofsales.containsKey(p.getUniqueId())){
			factionshop.getInstance().numberofsales.put(p.getUniqueId(),factionshop.getInstance().numberofsales.get(p.getUniqueId())+1 );
		}
		else{
			factionshop.getInstance().numberofsales.put(p.getUniqueId(),1 );
		}		
		factionshop.getInstance().refs.put(ref, p.getUniqueId());
		factionshop.getInstance().dates.put(ref, expirationdate);		
		factionshop.getInstance().itemsinsell.add(itemtosell);
		
		p.sendMessage("§6Vous avez mis en vente un item à §3" + prix + " Dreams");
		p.sendMessage("§6Vous avez payé §3" + taxe + " Dreams §6de taxe");
		p.sendMessage("§6Votre nouveau solde est de §3" + dreams + " Dreams");
		
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;          
	}
	
	
}
