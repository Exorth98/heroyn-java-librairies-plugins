package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MineGestion;
import fr.exorth.pickaxes.RentPickaxe;
import fr.exorth.pickaxes.VipMiner;
import fr.exorth.util.MGUtil;

public class MGMoveEvent implements Listener {
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if((MGUtil.isInZone(e.getFrom()) && MGUtil.isInZone(e.getTo()) && !MineGestion.getInstance().inZone.contains(p))){
			MineGestion.getInstance().inZone.add(p);
		}
		
		if(!MGUtil.isInZone(e.getFrom()) && MGUtil.isInZone(e.getTo())){
			
			if(!MineGestion.getInstance().inZone.contains(p)){
				MineGestion.getInstance().inZone.add(p);
			}
			
			if(MGUtil.isVipMiner(p) || MGUtil.hasRent(p)){
				
				if(!invFull(p)){				
					
					if(MGUtil.isVipMiner(p) && !MGUtil.hasRent(p)){ //vip
										
						giveAutoPickaxe(MGUtil.getVipPickaxe(p),p);
						p.sendMessage("§aPioche Vip reçue !");
						
						}
					if(!MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){ // rent
						
						giveAutoPickaxe(RentPickaxe.getPickaxe(p.getUniqueId()).getType().getItem(),p);
						p.sendMessage("§aPioche de Location reçue !");
						if(RentPickaxe.getPickaxe(p.getUniqueId()).getExpiration()<600000){
							p.sendMessage("§cAttention ! ta pioche expire bientôt");
						}
						
						
						
						}
					if(MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){ //both
						
						VipMiner vm = new VipMiner(p);
						String pref = vm.getPref();
						
						if(pref.equals("rent")){ //both pref rent
							
							giveAutoPickaxe(RentPickaxe.getPickaxe(p.getUniqueId()).getType().getItem(),p);
							p.sendMessage("§aPioche de Location reçue !");
							p.sendMessage("§eSi tu veux ta pioche vip, change cette préférence auprès du loueur");
							if(RentPickaxe.getPickaxe(p.getUniqueId()).getExpiration()<600000){
								p.sendMessage("§cAttention ! ta pioche expire bientôt");
							}
							
						}
						else if(pref.equals("vip")){ //both pref vip
							
							giveAutoPickaxe(MGUtil.getVipPickaxe(p),p);
							p.sendMessage("§aPioche Vip reçue !");
							p.sendMessage("§eSi tu veux ta pioche de location, change cette préférence auprès du loueur");
							
						}
						
					}
					
				}else{
					p.sendMessage("§cInventaire plein, impossible de te donner ta pioche automatique");
				}

				
			}else{
				p.sendMessage("§bTu n'as aucune pioche automatique,");
				p.sendMessage("§bva voir le loueur si tu en veux une !");
				}
			
			
			
			
			
			
		}
		else if(MGUtil.isInZone(e.getFrom()) && !MGUtil.isInZone(e.getTo())){
			
				MineGestion.getInstance().inZone.remove(p);
			
			
			if(MGUtil.isVipMiner(p) || MGUtil.hasRent(p)){
				
				
				if(MGUtil.isVipMiner(p) && !MGUtil.hasRent(p)){ //vip
									
					removeAutoPickaxe(MGUtil.getVipPickaxe(p),p);
					
					}
				if(!MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){ // rent
					
					removeAutoPickaxe(RentPickaxe.getPickaxe(p.getUniqueId()).getType().getItem(),p);
					
					
					}
				if(MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){ //both
					
					VipMiner vm = new VipMiner(p);
					String pref = vm.getPref();
					
					if(pref.equals("rent")){ //both pref rent
						
						removeAutoPickaxe(RentPickaxe.getPickaxe(p.getUniqueId()).getType().getItem(),p);
						
					}
					else if(pref.equals("vip")){ //both pref vip
						
						removeAutoPickaxe(MGUtil.getVipPickaxe(p),p);
						
					}
					
				}
				
			}
			
		}
		
	}

	private void removeAutoPickaxe(ItemStack item, Player p) {
		
		p.getInventory().remove(item);
		p.updateInventory();
		
	}

	private void giveAutoPickaxe(ItemStack Pickaxe,Player p) {
		
		int firstEmpty = p.getInventory().firstEmpty();
		int held = p.getInventory().getHeldItemSlot();
		
		ItemStack replace = p.getInventory().getItem(held);
		
		p.getInventory().setItem(held, Pickaxe);
		
		if(replace != null){
			if(replace.getType() != Material.AIR){
				
				p.getInventory().setItem(firstEmpty, replace);
				
			}
		}
		
		p.updateInventory();
		
	}
	
	private boolean invFull(Player p){
		return p.getInventory().firstEmpty() == -1; 
	}

}
