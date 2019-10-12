package fr.exorth.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.factionshop;

public class OtherUtils {
	
	public static ItemStack createItemToSell(ItemStack item, double prix, String vendeur,String ref){
		
		ItemMeta itemM = item.getItemMeta();
		List<String> lores = itemM.getLore();
		
		
		if(lores== null){
			lores = new ArrayList<>();										
		}
		lores.add("§6-----------------");
		lores.add("§6Vendeur: " + vendeur);
		lores.add("§6Prix: " + prix);
		lores.add("§a48:00:00");
		lores.add("§8ref:" + ref);
		
		itemM.setLore(lores);																		
		item.setItemMeta(itemM);
		
		return item;
	}

	public static Date getExpirationDate(int days) {
		
		Date current = new Date();
		Date expiration = new Date();
		long currentm = current.getTime();
		long expirationm = currentm + (days*24*60*60*1000);
		expiration.setTime(expirationm);
		
		return expiration;
	}

	public static String getItemCooldown(ItemStack item, Date now) {
		String cooldown= null;
		
		String refItem = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1).split(":")[1];

		Date expiration = factionshop.getInstance().dates.get(refItem);
		
		if(expiration ==null){
			Bukkit.broadcastMessage("§cnull pour : " + refItem);
		}

		long expirationm = expiration.getTime();
		long nowm= now.getTime();
		
		long cooldownm = expirationm - nowm;
		
		if(cooldownm <=0){
			
			cancelByExpiration(item);
			return "§cItem éxpiré";
			
		}else{
			
			cooldown = "§a" + String.format("%02d:%02d:%02d", 
				    TimeUnit.MILLISECONDS.toHours(cooldownm),
				    TimeUnit.MILLISECONDS.toMinutes(cooldownm) - 
				    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cooldownm)),
				    TimeUnit.MILLISECONDS.toSeconds(cooldownm) - 
				    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cooldownm)));
						
			return cooldown;
		}

	}

	public static void setItemLoreCooldown(ItemStack item, String cooldown) {
		
		ItemMeta itemM = item.getItemMeta();
		List<String> lores = itemM.getLore();
		
		lores.set(lores.size()-2, cooldown);
		
		itemM.setLore(lores);
		item.setItemMeta(itemM);
		
	}
	
	private static void cancelByExpiration(ItemStack item) {
		
		String refItem = item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1).split(":")[1];		
		Player p = Bukkit.getPlayer(factionshop.getInstance().refs.get(refItem));
		
		UUID uuid = factionshop.getInstance().refs.get(refItem);
		
		int actuel = factionshop.getInstance().numberofsales.get(uuid);
		
		factionshop.getInstance().numberofsales.put(uuid,actuel-1 );
		
		if(Bukkit.getOnlinePlayers().contains(p)){
			p.sendMessage("§6Une de vos vente en shop vient d'éxpirer");
		}
		
	}

	public static ItemStack restoreLores(ItemStack item) {
		
		ItemMeta itemM = item.getItemMeta();		
		List<String> lores = itemM.getLore();
		
		for(int i = lores.size()-1 ; i>=0 ;i--){
			
			if (lores.get(i).contains("§6------") || lores.get(i).contains("§a[V]") || lores.get(i).contains("§c[X]")){
				
				lores.remove(i+4);
				lores.remove(i+3);
				lores.remove(i+2);
				lores.remove(i+1);
				lores.remove(i);			
			}
			
		}
		
		itemM.setLore(lores);
		item.setItemMeta(itemM);
		
		return item;
	}

	public static int getPageInd_sellinventories(int page, Player p) {
		int PageInd;
		int taille = factionshop.getInstance().sellinventories.get(p).size();
		PageInd = taille - page;
		return PageInd;
	}
	
	public static int getPageInd_mysellinventories(int page, Player p) {
		int PageInd;
		int taille = factionshop.getInstance().mysellinventories.get(p).size();
		PageInd = taille - page;
		return PageInd;
	}

	public static double getTaxe(double prix, int hours) {
		
		DecimalFormat df = new DecimalFormat("#.00");
		double taxe; 
		double percent;
		
		switch(hours){
			
		case 3:
			percent= 0.1;
			break;
		case 7:
			percent= 0.15;
			break;
		case 10:
			percent= 0.2;
			break;
		default:
			percent= 0.15;
			break;
		}
		
		
		
		taxe = Double.parseDouble(df.format(prix*percent).replace(",", "."));
		
		return taxe;
	}

	public static int getPage_sellinventories(int indPage,Player p) {
		int Page;
		int taille = factionshop.getInstance().sellinventories.get(p).size();
		Page = taille - indPage;
		return Page;
	}


}
