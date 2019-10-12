package fr.exorth.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuakeJoinItem {
	
	
	public static void give(Player p){
		
		ItemStack choix = new ItemStack(Material.WOOD_HOE);
		ItemMeta choixM = choix.getItemMeta();
		choixM.setDisplayName("§3Choix du Gun");
		choix.setItemMeta(choixM);
		
		ItemStack quitter = new ItemStack(Material.WOOD_DOOR);
		ItemMeta quitterM = quitter.getItemMeta();
		quitterM.setDisplayName("§cQuitter");
		quitter.setItemMeta(quitterM);
		
		p.getInventory().setItem(2, choix);
		p.getInventory().setItem(7, quitter);
		
	}

}
