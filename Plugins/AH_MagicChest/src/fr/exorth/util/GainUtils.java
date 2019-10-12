package fr.exorth.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;

public class GainUtils {
	
	public static FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<ItemStack> gives = (ArrayList<ItemStack>) config.getList("pendinggives");
	
	
	public static int getGainsNumber(Player p){
		int gains = 0 ;
		
		if(gives ==null){gives = new ArrayList<ItemStack>();}
		
		for (ItemStack item : gives){
			UUID uuid = UUID.fromString(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1));
			if(uuid.equals(p.getUniqueId())){
				gains++;
			}
		}
		
		return gains;
	}
	
	public static List<ItemStack> getGains(Player p){
		
		List<ItemStack> gains = new ArrayList<>();
		
		if(gives ==null){gives = new ArrayList<ItemStack>();}
		
		for (ItemStack item : gives){
			UUID uuid = UUID.fromString(item.getItemMeta().getLore().get(item.getItemMeta().getLore().size()-1));
			if(uuid.equals(p.getUniqueId())){
				gains.add(item);
			}
		}
		
		return gains;
	}
	
	public static void giveGains(Player p){

		Iterator<ItemStack> itr = gives.iterator();
		while(itr.hasNext())
		{
			ItemStack item = itr.next();
			
			if(ItemUtil.getEmptiesSlotsNumber(p.getInventory())>0){
				
				int [] divideStack = ItemUtil.getStacksNumber(item);
				
				int completeStackNumber = divideStack[0];
				ItemStack Stack = item.clone();Stack.setAmount(64);Stack = ItemUtil.removeLastLore(Stack);
				ItemStack Reste = item.clone();Reste.setAmount(divideStack[1]);Reste = ItemUtil.removeLastLore(Reste);
				
				for(int count=0 ; ItemUtil.getEmptiesSlotsNumber(p.getInventory())>0 && count<completeStackNumber ; count++){
					
					p.getInventory().addItem(Stack);
					item.setAmount(item.getAmount()-64);
				}
				
				if(ItemUtil.getEmptiesSlotsNumber(p.getInventory())>0){
					p.getInventory().addItem(Reste);
					item.setAmount(item.getAmount()-divideStack[1]);
				}
				
				if(item.getAmount()==0){
					itr.remove();
				}
				
			}

		}
		
		
		config.set("pendinggives", gives);
		MagicChest.getInstance().saveConfig();

	}


}
