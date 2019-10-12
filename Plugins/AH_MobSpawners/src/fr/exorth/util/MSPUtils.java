package fr.exorth.util;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.util.ItemUtil;

import fr.exorth.MobSpawners;

public class MSPUtils {
	
	static FileConfiguration config = MobSpawners.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static
	ArrayList<String> refs = (ArrayList<String>) config.get("refs");

	
	
	public static ItemStack getPickAxe() {
		
		if(refs==null){refs = new ArrayList<String>();}
				
		Random r = new Random();
		String ref = Integer.toString(1000000 + r.nextInt(9999999 - 1000000));	
		
		refs.add(ref);
		config.set("refs", refs);
		MobSpawners.getInstance().saveConfig();
		
		ItemStack pickAxe = new ItemStack(Material.DIAMOND_PICKAXE,1);
		//pickAxe = ItemUtil.addFakeEnchant(pickAxe);
		ItemMeta pickaxeM = pickAxe.getItemMeta();
		pickaxeM.addEnchant(Enchantment.DURABILITY, 1, true);
		pickaxeM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		pickAxe.setItemMeta(pickaxeM);
		
		pickAxe = ItemUtil.setName(pickAxe,"§aPioche à Spawner");
		pickAxe = ItemUtil.addLore(pickAxe,"§cUtilisation Unique");
		pickAxe = ItemUtil.addLore(pickAxe,"§8ref:"+ref);
		
		return pickAxe;
	}



	public static void removeref(String ref) {
		
		refs.remove(ref);
		config.set("refs", refs);
		MobSpawners.getInstance().saveConfig();
		
	}

}
