package fr.exorth.util;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.exorth.factionshop;
import net.milkbowl.vault.economy.Economy;

public class Buy {
	
	public Economy economy = null;
	
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = factionshop.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
	public void doBuy(ItemStack item, Player p){
		

		ItemMeta itemM = item.getItemMeta();
		List<String> lores = itemM.getLore();
		
		String saler = lores.get(lores.size()-4).split(": ")[1];
		double Prix = Double.parseDouble(lores.get(lores.size()-3).split(": ")[1]);
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
					
					UUID uuid = factionshop.getInstance().refs.get(refiteminsell);			
					Player salerv = Bukkit.getPlayer(uuid);	
					
					double dreams1=0;
					
					if(setupEconomy()){
						economy.depositPlayer(salerv, Prix); //deposit
						dreams1=economy.getBalance(salerv);
					}
					
					if(Bukkit.getOnlinePlayers().contains(salerv)){
						salerv.sendMessage("§6Vous venez de ganer §3"+Prix+" Dreams §6car §3" +p.getName()+" §6vient de vous acheter quelque chose à l'hôtel des ventes" );
						p.sendMessage("§6Votre nouveau solde est de §3" + dreams1 + " Dreams");
					}	
					
					factionshop.getInstance().numberofsales.put(uuid,factionshop.getInstance().numberofsales.get(uuid)-1 );
					
					
					factionshop.getInstance().refs.remove(refitem);
					factionshop.getInstance().dates.remove(refitem);
					factionshop.getInstance().itemsinsell.remove(iteminsell);
					
					item = OtherUtils.restoreLores(item);
					
					double dreams2=0;
					if(setupEconomy()){
						economy.withdrawPlayer(p, Prix);//withdraw
						dreams2 = economy.getBalance(p);
					}
					
					p.sendMessage("§6Vous avez fait un achat à §3" + saler + " §6pour §3" + Prix + " Dreams");
					p.sendMessage("§6Votre nouveau solde est de §3" + dreams2 + " Dreams");
					

					
					
					p.getInventory().addItem(item);
					p.closeInventory();	
					
				}else{
					p.sendMessage("§6Tu n'as pas de place dans ton inventaire pour récuperer cet item!");
					p.closeInventory();
				}
			
			}else{
				p.sendMessage("§cL'item n'est plus disponible !");
				p.closeInventory();
			}
		}else{
			p.sendMessage("§cL'item n'est plus disponible");
		}		
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;            
	}

}
