package fr.exorth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.exorth.MagicChest;

public class LoadCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("magicchestload")){
			
			MagicChest.getInstance().reloadConfig();
			
		}
		
		
		return false;
	}

}
