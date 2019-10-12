package fr.exorth.runnable;

import java.util.Date;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.factionshop;
import fr.exorth.util.ShopRefresh;

public class ShopRunnable extends BukkitRunnable{

	@Override
	public void run() {
		
		Date now = new Date();
		
		for(ItemStack item : factionshop.getInstance().itemsinsell){
			
			if(!isExpirate(item)){
				ShopRefresh.refreshItem(item, now);		
			}		
		}		
		new ShopRefresh().refreshInventories();
		
	}

	private boolean isExpirate(ItemStack item) {
		String cooldown = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-2);
		return cooldown.contains("Item éxpiré");
	}

}
