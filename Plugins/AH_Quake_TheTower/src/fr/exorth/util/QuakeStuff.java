package fr.exorth.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.Quake;

public class QuakeStuff {

	public static void giveStuff(Player pl) {
		
		Material matp = Quake.getInstance().hoes.get(pl);
		ItemStack hoe = new ItemStack(matp,1);
		ItemMeta hoeM = hoe.getItemMeta();
		hoeM.setDisplayName("§5RailGun");
		hoe.setItemMeta(hoeM);
		
		pl.getInventory().addItem(hoe);
		pl.updateInventory();
		
	}

}
