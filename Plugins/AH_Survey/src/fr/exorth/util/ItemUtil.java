package fr.exorth.util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemUtil {

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

}
