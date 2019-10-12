package net.heroyn.heroynserverapi.utils;

import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.magicchest.CosmeticsEnum;

public class MsgUtils {

	public static String BOUTIQUE_PREFIX = "§7[§bBoutique§7] ";
	
	public static void achatCosmetics(Player player, CosmeticsEnum cosmetics) {
		player.sendMessage(BOUTIQUE_PREFIX + " §7Vous gagné: " + cosmetics.getRarity().getPrefix() + cosmetics.getName());
	}
}
