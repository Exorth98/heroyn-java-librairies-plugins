package net.heroyn.mobarena.utils;

import org.bukkit.entity.Player;

public class ClasseMsg {

	public static void playerIsFullLife(Player player, boolean self) {
		player.sendMessage(self ? "§cVous êtes a votre maximum de votre points de vie" : "Le joueur est au maximum de ses points de vie");
	}
	
	public static void invalidTarget(Player player) {
		player.sendMessage("§7Pas de cible ou cible trop loin !");
	}
	
}
