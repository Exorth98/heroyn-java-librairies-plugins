package net.heroyn.heroynserverapi.SpecialItem.virtualMenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.SpecialItem.Item.ItemMenuAnnulerShop;
import net.heroyn.heroynserverapi.SpecialItem.Item.ItemMenuConfirmShop;

public class VirtualMenuConfirmShop {

	public VirtualMenuConfirmShop(Player player, ItemStack item) {
		getInventory(player, item);
	}
	
	public void getInventory(Player player, ItemStack item) {
		Inventory inv = Bukkit.createInventory(null, 36, "§aConfirmation d'achat");
		inv.setItem(13, item);
		inv.setItem(21, ItemMenuConfirmShop.getItem());
		inv.setItem(23, new ItemMenuAnnulerShop().getItems());
		player.openInventory(inv);
	}
	
}
