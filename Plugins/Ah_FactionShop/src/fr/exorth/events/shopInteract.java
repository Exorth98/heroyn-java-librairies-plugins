package fr.exorth.events;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.factionshop;
import fr.exorth.util.OtherUtils;
import fr.exorth.util.confirmation;
import fr.exorth.util.insellinventory;
import fr.exorth.util.myexpsellsinventory;
import fr.exorth.util.mysellsinventory;

public class shopInteract implements Listener {
	

	
	@EventHandler
	public void onShopInteract(InventoryClickEvent e){
		
		if(e.getSlotType() != SlotType.OUTSIDE && e.getCurrentItem().getType() != Material.AIR){
			
			//Shop principal
			
			Player p = (Player) e.getWhoClicked();
			
			//p.getOpenInventory().getTitle().contains("Shop")
			if((e.getAction() != InventoryAction.COLLECT_TO_CURSOR) && p.getOpenInventory().getTitle().contains("Shop"))
			{
				e.setCancelled(true);
			}
			
			if(e.getClickedInventory().getName().contains("Shop")){
				e.setCancelled(true);
				
				
				if(e.getCurrentItem().getItemMeta().getLore()!=null){
					if(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size()-1).contains("§8ref:")){
						
						//new Buy(sql).doBuy(e.getCurrentItem(),p);
						if(factionshop.getInstance().itemsinsell.contains(e.getCurrentItem())){
							
							//if not full
							
							ItemStack item =e.getCurrentItem();
							ItemMeta itemM = item.getItemMeta();
							List<String> lores = itemM.getLore();

							String refitem = lores.get(lores.size()-1).split(":")[1];

							UUID uuid = factionshop.getInstance().refs.get(refitem);
							
							if(!p.getUniqueId().equals(uuid)){
								
								new confirmation().createConfirmation(e.getCurrentItem(), p, "Achat");
								
							}else{
								p.sendMessage("§cVous ne pouvez pas acheter votre propre item !");
							}
							
						}else{
							p.sendMessage("§6Pas de chance, l'item est déjà vendu, actualisation du shop...");
							new insellinventory().createInventories((Player) p, 1, 0);			
							p.openInventory(factionshop.getInstance().sellinventories.get(p).get(factionshop.getInstance().sellinventories.get(p).size()-1));
						}
						
					}
				}
				
				if(e.getCurrentItem().getType() == Material.ARROW){
					
					int page = Integer.parseInt(e.getClickedInventory().getName().split(": ")[1]);
					int i = factionshop.getInstance().sellinventories.get(p).size() - page;
					new insellinventory().createInventories((Player) p, 1, 0);
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page précédente")){			
						p.openInventory(factionshop.getInstance().sellinventories.get(p).get(i+1));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page suivante")){			
						p.openInventory(factionshop.getInstance().sellinventories.get(p).get(i-1));
					}
					
					
					
				}
				
				if(e.getCurrentItem().getType() == Material.CHEST && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6[Mes items]")){
					
					if(e.getClick() == ClickType.LEFT){
						new mysellsinventory().createInventories((Player) p, 1, 0);			
						p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(factionshop.getInstance().mysellinventories.get(p).size()-1));
					}
					else if (e.getClick() == ClickType.RIGHT){
						new myexpsellsinventory().createInventories((Player) p, 1, 0);			
						p.openInventory(factionshop.getInstance().myexpsellinventories.get(p).get(factionshop.getInstance().myexpsellinventories.get(p).size()-1));
					}

				}
		
			}
			
			
			
			
			//mes items en vente
			
			
			
			
			if(e.getClickedInventory().getName().contains("Mes items en vente")){
				e.setCancelled(true);			
				
				if(e.getCurrentItem().getType()== Material.ENDER_CHEST && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Retour au shop")){ 
					factionshop.getInstance().mysellinventories.remove(p);
					new insellinventory().createInventories((Player) p, 1, 0);			
					p.openInventory(factionshop.getInstance().sellinventories.get(p).get(factionshop.getInstance().sellinventories.get(p).size()-1));
				}
				if(e.getCurrentItem().getType() == Material.ARROW){
					
					int page = Integer.parseInt(e.getClickedInventory().getName().split(": ")[1]);
					int i = factionshop.getInstance().mysellinventories.get(p).size() - page;
					new mysellsinventory().createInventories((Player) p, 1, 0);
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page précédente")){			
						p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(i+1));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page suivante")){			
						p.openInventory(factionshop.getInstance().mysellinventories.get(p).get(i-1));
					}				
				}
				
				//item en vente
				if(e.getCurrentItem().getItemMeta().getLore()!=null){
					if(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size()-1).contains("§8ref:")){
						
						//new Sell(sql).cancelSell(e.getCurrentItem(), p);
						new confirmation().createConfirmation(e.getCurrentItem(), p, "Retirer de la vente");
						
					}
				}
			
			}
			
			if(e.getClickedInventory().getName().contains("Mes items expirés")){
				e.setCancelled(true);			
				
				if(e.getCurrentItem().getType()== Material.ENDER_CHEST && e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Retour au shop")){
					factionshop.getInstance().myexpsellinventories.remove(p);
					new insellinventory().createInventories((Player) p, 1, 0);			
					p.openInventory(factionshop.getInstance().sellinventories.get(p).get(factionshop.getInstance().sellinventories.get(p).size()-1));
				}
				if(e.getCurrentItem().getType() == Material.ARROW){
					
					int page = Integer.parseInt(e.getClickedInventory().getName().split(": ")[1]);
					int i = factionshop.getInstance().myexpsellinventories.get(p).size() - page;
					new myexpsellsinventory().createInventories((Player) p, 1, 0);
					
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page précédente")){			
						p.openInventory(factionshop.getInstance().myexpsellinventories.get(p).get(i+1));
					}
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Page suivante")){			
						p.openInventory(factionshop.getInstance().myexpsellinventories.get(p).get(i-1));
					}				
				}
				
				if(e.getCurrentItem().getItemMeta().getLore()!=null){
					if(e.getCurrentItem().getItemMeta().getLore().get(e.getCurrentItem().getItemMeta().getLore().size()-1).contains("§8ref:")){
						
						ItemStack item = e.getCurrentItem();
						String refItem = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1).split(":")[1];	
						
						String refitemexp = "00";
						ItemStack itemexp = null;
						
						for(int i = 0; !refItem.equals(refitemexp);i++){
							itemexp = factionshop.getInstance().itemsinsell.get(i);
							List<String> lores2 = itemexp.getItemMeta().getLore();
							refitemexp = lores2.get(lores2.size()-1).split(":")[1];
							}
						
						if(!invFull(p)){
							
							factionshop.getInstance().itemsinsell.remove(itemexp);
							factionshop.getInstance().refs.remove(refItem);
							factionshop.getInstance().dates.remove(refItem);
							
							item = OtherUtils.restoreLores(item);
							
							p.getInventory().addItem(item);
							
							p.sendMessage("§6Item récupéré avec succès");
							
							new myexpsellsinventory().createInventories((Player) p, 1, 0);
							p.openInventory(factionshop.getInstance().myexpsellinventories.get(p).get(0));
						}else{
							p.sendMessage("§cTu n'as pas de place dans ton inventaire pour récuperer cet item !");
						}
						

					}
				}
			
			}
			
		}
		
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;          
	}

}
