package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;
import fr.exorth.pickaxes.VipMiner;
import fr.exorth.util.MGconfirmation;
import fr.exorth.util.NPCMenu;

public class MGMenuInteractEvent implements Listener {
	
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent e){
		
		if(e.getSlotType() != SlotType.OUTSIDE){
			
			String name = e.getInventory().getName();
			
			if(name.equals("§0Location de pioches")){
				
				e.setCancelled(true);
				PickaxeType pt = PickaxeType.getTypeFromItem(e.getCurrentItem());
				if(pt != null){
					NPCMenu.openRentChoseTimeMenu((Player) e.getWhoClicked(), pt);
				}
				
				if(e.getCurrentItem().getType()==Material.ARROW){
					
					NPCMenu.choose((Player) e.getWhoClicked());
					
				}
				
			}
			else if (name.equals("§0Choix du temps de location")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType()== Material.PAPER){
					
					int amount = e.getCurrentItem().getAmount();
					PickaxeType pt = PickaxeType.getTypeFromItem(e.getInventory().getItem(4));
					
					MGconfirmation.CreateRentConfirmation((Player) e.getWhoClicked(),amount,pt);
					
				}
				
				if(e.getCurrentItem().getType()==Material.ARROW){
					
					NPCMenu.openRentMenu((Player) e.getWhoClicked());
					
				}
				
			}
			else if (name.equals("§0Ajout de temps de location")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType()== Material.PAPER){
					
					int amount = e.getCurrentItem().getAmount();
					
					MGconfirmation.CreateTimeAddConfirmation((Player) e.getWhoClicked(),amount);
					
				}
				if(e.getCurrentItem().getType()==Material.ARROW){
					
					NPCMenu.openRentOptionsMenu((Player) e.getWhoClicked());
					
				}
				
			}
			else if (name.equals("§0Gestion de mes pioches")){
				e.setCancelled(true);
				
				
				PickaxeType pt = PickaxeType.getTypeFromItem(e.getCurrentItem());
				if(pt != null){
					
					if(e.getSlot()==20){
						
						NPCMenu.openRentOptionsMenu((Player) e.getWhoClicked());
					}
				}else{
					
					if(e.getCurrentItem().getType()== Material.EMERALD){
						
						NPCMenu.openRentMenu((Player) e.getWhoClicked());
						
					}else{
						
						
						@SuppressWarnings("deprecation")
						int id = e.getCurrentItem().getTypeId();
						
						if(id == 351){
							
							@SuppressWarnings("deprecation")
							byte data = e.getCurrentItem().getData().getData();
							
							if(data == 8){
								
								VipMiner vm = new VipMiner((Player) e.getWhoClicked());
								
								if(e.getSlot()==11){
									vm.setPref("rent");
								}
								else if(e.getSlot()==15){
									vm.setPref("vip");
								}
								
								NPCMenu.openPersonnalMenu((Player) e.getWhoClicked(), "both");
							}
						}
						
					}

					
				}
				
			}
			else if (name.equals("§0Options de location")){
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType()== Material.PAPER){
					
					PickaxeType pt = RentPickaxe.getPickaxe(((Player)e.getWhoClicked()).getUniqueId()).getType();
					NPCMenu.openRentAddTimeMenu((Player) e.getWhoClicked(), pt);;
					
				}else{
					
					PickaxeType pt = PickaxeType.getTypeFromItem(e.getCurrentItem());
					if(pt != null){
						NPCMenu.openChangePickaxeMenu((Player) e.getWhoClicked());
					}
					
					if(e.getCurrentItem().getType()==Material.ARROW){
												
						NPCMenu.choose((Player) e.getWhoClicked());
						
					}
					
				}
				
			}
			else if (name.equals("§0Changement de pioche")){
				e.setCancelled(true);
				
				PickaxeType ptw = PickaxeType.getTypeFromItem(e.getCurrentItem());
				PickaxeType pto = RentPickaxe.getPickaxe(((Player)e.getWhoClicked()).getUniqueId()).getType();
				if(ptw != null){
					
					if(pto != ptw){
						MGconfirmation.CreatePickaxeChangeConfirmation((Player)e.getWhoClicked(),ptw);
					}				
					
				}
				
				if(e.getCurrentItem().getType()==Material.ARROW){
					
					NPCMenu.openRentOptionsMenu((Player) e.getWhoClicked());
					
				}
				
			}
		}
		
	}

}
