package net.heroyn.mobarena.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
	
	public static void resetItemInHandDurability(Player player)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		item.setDurability((byte) 100);
		player.getInventory().setItemInMainHand(item);
	}
}
