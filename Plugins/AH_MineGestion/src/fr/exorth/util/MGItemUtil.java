package fr.exorth.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MGItemUtil {

	public static ItemStack setName(ItemStack key, String name) {
		
		ItemMeta keyM = key.getItemMeta();
		keyM.setDisplayName(name);
		key.setItemMeta(keyM);
				
		return key;
	}

	public static ItemStack setLores(ItemStack key, List<String> lores) {
		
		ItemMeta keyM = key.getItemMeta();
		keyM.setLore(lores);
		key.setItemMeta(keyM);
		
		return key;
	}

	public static String getRef(ItemStack key) {
		
		List<String> lores = key.getItemMeta().getLore();
		String reflore ="default";
		
		for(String lore : lores){
			if(lore.contains("§cNuméro de validité")){
				reflore = lore;
			}
		}
		
		String ref = reflore.split(": ")[1];
		
		return ref;
	}
	
	public static int [] getStacksNumber(ItemStack stack){
		int count [] = new int [2];
		count[0] = stack.getAmount()/64;
		count[1] = stack.getAmount()%64;
		
		return count;
		
	}
	
	public static int getEmptiesSlotsNumber(Inventory inv){
		
		int count = 0;
		for (ItemStack item : inv.getStorageContents()) {
		    if (item == null || item.getType() == Material.AIR ){
		    	count++;
		    }		        
		}
		return count;
	}

	public static ItemStack addLore(ItemStack loot, String string) {
		
		ItemMeta lootM = loot.getItemMeta();
		List<String> lores;
		if(lootM.getLore() != null){lores = lootM.getLore();} 
		else{lores = new ArrayList<>();}
		
		lores.add(string);
		
		lootM.setLore(lores);
		loot.setItemMeta(lootM);
		
		return loot;
	}
	
	public static ItemStack removeLastLore(ItemStack loot) {
		
		ItemMeta lootM = loot.getItemMeta();
		List<String> lores = lootM.getLore();
		
		lores.remove(lores.size()-1);
		
		lootM.setLore(lores);
		loot.setItemMeta(lootM);
		
		return loot;
	}

	public static ItemStack addFakeEnchant(ItemStack pickAxe) {
		
		ItemMeta pickaxeM = pickAxe.getItemMeta();
		pickaxeM.addEnchant(Enchantment.DURABILITY, 1, true);
		pickaxeM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickAxe.setItemMeta(pickaxeM);
		
		return pickAxe;
	}

}
