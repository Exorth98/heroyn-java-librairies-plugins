package net.heroyn.heroynserverapi.SpecialItem.Item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMenuConfirmShop {

	public static String displayName = "§aConfirm";

	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.SLIME_BALL);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(displayName);
		item.setItemMeta(im);
		return item;
	}

}
