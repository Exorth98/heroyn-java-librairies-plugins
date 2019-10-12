package fr.exorth.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.MGEconomy;
import fr.exorth.MineGestion;
import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;
import fr.exorth.util.NPCMenu;

public class MGConfirmationInteractEvent implements Listener {
	
	private ItemStack getConfirm(){
		
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				

		return confirm;
	}
	
	private ItemStack getCancel(){
			
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		
		return cancel;
	}

	@EventHandler
	public void onMenuClick(InventoryClickEvent e){
		
		if(e.getSlotType() != SlotType.OUTSIDE){
			
			String name = e.getInventory().getName();
			Player p = (Player) e.getWhoClicked();
			
			
			
			
			
			
			if(name.equals("§0Confirmation de Location")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().equals(getCancel())){
					
					MineGestion.getInstance().inRentConfirmation.remove(p);
					NPCMenu.openRentChoseTimeMenu(p,PickaxeType.getTypeFromItem(e.getInventory().getItem(12)));
					
				}
				else if(e.getCurrentItem().equals(getConfirm())){
					
					double[] infos = MineGestion.getInstance().inRentConfirmation.get(p);
					
					double hours = infos[0];
					double price = infos[1];
					
					if(MGEconomy.getBalance(p)>= price){
												
						PickaxeType pt = PickaxeType.getTypeFromItem(e.getInventory().getItem(12));
						
						Random r = new Random();
						String ref = Integer.toString(1000000 + r.nextInt(9999999 - 1000000));
						
						long exp = (long) hours*60*60*1000;
						
						new RentPickaxe(ref, pt, p, exp).SavePickaxe();
						
						MineGestion.getInstance().inRentConfirmation.remove(p);
						p.closeInventory();
						
						MGEconomy.removeMoney(p, price);
						
						p.sendMessage("§aVous venez de louer une pioche !");
						p.sendMessage("§eEntrez dans la zone minière pour l'essayer");
						
					}

					
				}

			}
			
			
			
			
			
			else if(name.equals("§0Confirmation de prolongement de Location")){
				e.setCancelled(true);
				
				
				if(e.getCurrentItem().equals(getCancel())){
					
					MineGestion.getInstance().inTimeAddConfirmation.remove(p);
					NPCMenu.openRentAddTimeMenu(p, RentPickaxe.getPickaxe(p.getUniqueId()).getType());
					
				}
				else if(e.getCurrentItem().equals(getConfirm())){					
					
					double[] infos = MineGestion.getInstance().inTimeAddConfirmation.get(p);
					
					double hours = infos[0];
					double price = infos[1];
					
					if(MGEconomy.getBalance(p)>= price){
						
						RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
						
						long actualexp = rp.getExpiration();
						
						long newexp = (long) (actualexp + (hours*60*60*1000));
						

						rp.setExpiration(newexp);
						
						MineGestion.getInstance().inTimeAddConfirmation.remove(p);
						p.closeInventory();
						
						MGEconomy.removeMoney(p, price);
						
						p.sendMessage("§aVous avez prolongé votre location de §a"+hours+"h");
						
					}
					

				}
				
				
				
			}
			
			
			
			
			
			else if(name.equals("§0Confirmation de changement de pioche")){
				e.setCancelled(true);
				
				
				if(e.getCurrentItem().equals(getCancel())){
					
					MineGestion.getInstance().inPickaxeChangeConfirmation.remove(p);
					NPCMenu.openChangePickaxeMenu(p);
					
				}
				else if(e.getCurrentItem().equals(getConfirm())){
					
					double price = MineGestion.getInstance().inPickaxeChangeConfirmation.get(p);
					
					if(MGEconomy.getBalance(p)>= price){
						
						
						RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
						PickaxeType type = PickaxeType.getTypeFromItem(e.getInventory().getItem(12));

						rp.setType(type);
						
						MineGestion.getInstance().inPickaxeChangeConfirmation.remove(p);
						p.closeInventory();
						
						MGEconomy.removeMoney(p, price);
						
						p.sendMessage("§aVous avez une nouvelle pioche de location");
						
					}
					
				}
				
			}
		}
		
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent e){
		
		String name = e.getInventory().getName();
		Player p = (Player) e.getPlayer();
		
		if(name.equals("§0Confirmation de Location")){
			
			MineGestion.getInstance().inRentConfirmation.remove(p);
			
		}
		else if(name.equals("§0Confirmation de prolongement de Location")){
			
			MineGestion.getInstance().inTimeAddConfirmation.remove(p);
			
		}
		else if(name.equals("§0Confirmation de changement de pioche")){
			
			MineGestion.getInstance().inPickaxeChangeConfirmation.remove(p);
			
		}
	}
		

	
}
