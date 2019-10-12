package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.MelonSaleUtils;

public class MelonSaleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("ms")){
			
			if(args.length==2){
				
				if(args[0].equalsIgnoreCase("melon") || args[0].equalsIgnoreCase("cactus")){
					
					String type = args[0];
					
					if(Bukkit.getPlayer(args[1]) != null){
						
						Player p = Bukkit.getPlayer(args[1]);
						
						MelonSaleUtils.CreateConfirmation(p,type);
						
					}
					
				}

			}else{
				s.sendMessage("\n\n§e/ms <melon/cactus> <joueur>");
			}
			
			
		}
		
		
		return false;
	}

}
