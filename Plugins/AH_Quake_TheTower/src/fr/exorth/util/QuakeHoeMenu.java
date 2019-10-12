package fr.exorth.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.Quake;

public class QuakeHoeMenu {
	
	public static FileConfiguration config = Quake.getInstance().getConfig();
	
	private static ItemStack modify(ItemStack itemstack , String displayname , List<String> lores){
		ItemStack New = itemstack;
		ItemMeta NewM = New.getItemMeta();
		NewM.setDisplayName(displayname);
		NewM.setLore(lores);
		New.setItemMeta(NewM);
		
		return New;
	}

	public static void open(Player p) {
		
		Inventory hoemenu = Bukkit.createInventory(null, 27, "§3Choix du Gun");
		
		ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)0);
		ItemStack hoeb = new ItemStack(Material.WOOD_HOE,1);
		ItemStack hoep = new ItemStack(Material.STONE_HOE,1);
		ItemStack hoef = new ItemStack(Material.IRON_HOE,1);
		ItemStack hoed = new ItemStack(Material.DIAMOND_HOE,1);
		
		modify(hoeb,"§cGun primaire",Arrays.asList("§e Temps de rechargement :","   §e" + config.getString("reload.wood") + "sec"));
		modify(hoep,"§aGun basique",Arrays.asList("§e Temps de rechargement :","   §e" + config.getString("reload.stone") + "sec"));
		modify(hoef,"§dGun avancé",Arrays.asList("§e Temps de rechargement :","   §e" + config.getString("reload.iron") + "sec"));
		modify(hoed,"§bGun extrême",Arrays.asList("§e Temps de rechargement :","   §e" + config.getString("reload.diamond") + "sec"));
		
		
		for(int i=0;i<10;i++){
			hoemenu.setItem(i, glass);
		}
		for(int i=17;i<hoemenu.getSize();i++){
			hoemenu.setItem(i, glass);
		}
		hoemenu.setItem(11, glass);
		hoemenu.setItem(13, glass);
		hoemenu.setItem(15, glass);
		hoemenu.setItem(10, hoeb);
		hoemenu.setItem(12, hoep);
		hoemenu.setItem(14, hoef);
		hoemenu.setItem(16, hoed);
		
		p.openInventory(hoemenu);
		
	}
	

	
	

}
