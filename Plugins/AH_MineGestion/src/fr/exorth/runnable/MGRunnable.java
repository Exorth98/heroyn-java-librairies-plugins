package fr.exorth.runnable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.MGTitleManager;
import fr.exorth.MineGestion;
import fr.exorth.pickaxes.RentPickaxe;
import fr.exorth.pickaxes.VipMiner;
import fr.exorth.util.MGUtil;

public class MGRunnable extends BukkitRunnable{

	@Override
	public void run() {
		
		for(Player p : MineGestion.getInstance().inZone){
			
			boolean bool = true;
			
			if(MGUtil.isVipMiner(p)){
				VipMiner vm = new VipMiner(p);
				if(vm.getPref().equals("vip")){
					bool=false;
				}
			}
			
			if(RentPickaxe.hasPickaxe(p.getUniqueId()) && bool){
				
				
				
				
				RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
				rp.setExpiration(rp.getExpiration()-1000);
				
				if(rp.getExpiration()>600000){
					MGTitleManager.sendActionBarPost(p, ChatColor.GREEN + MGUtil.getRentCoolDown(rp));
				}else{
					MGTitleManager.sendActionBarPost(p, ChatColor.RED + MGUtil.getRentCoolDown(rp));
				}

				
				if(rp.getExpiration()<=0){
					p.getInventory().removeItem(rp.getType().getItem());
					rp.deletePickaxe(rp.getRef());
					p.sendMessage("§cVotre pioche vient d'expirer");
				}
				
				
				
			}
			
		}
		
		
	}

}
