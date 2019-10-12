package net.heroyn.mobarena.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.heroyn.mobarena.classe.classes.Mage;
import net.heroyn.mobarena.game.player.MobarenaFighter;

public class testCmda implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		
		if (args[0].equalsIgnoreCase("heal")) {
			MobarenaFighter.getFromPlayer(player).setClasse(new Mage(player, 1));
			MobarenaFighter.getFromPlayer(player).getClasse().sendKitPlayer();
		}
		
		return false;
	}

}
