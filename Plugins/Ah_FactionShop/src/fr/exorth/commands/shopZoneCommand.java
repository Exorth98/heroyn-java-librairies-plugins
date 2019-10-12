package fr.exorth.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.factionshop;

public class shopZoneCommand implements CommandExecutor {
	
	public FileConfiguration config = factionshop.getInstance().getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(args.length== 1 && (args[0].equalsIgnoreCase("pos1") || args[0].equalsIgnoreCase("pos2"))){
				
				Location loc = p.getLocation();
				
				if(args[0].equalsIgnoreCase("pos1")){
					config.set("shopzone.pos1",loc);
					p.sendMessage("§6Pos1 de la zone marchande configurée!");
				}
				else if (args[0].equalsIgnoreCase("pos2")){
					config.set("shopzone.pos2",loc);
					p.sendMessage("§6Pos2 de la zone marchande configurée!");
				}
				
				factionshop.getInstance().saveConfig();
				
			}else{
				p.sendMessage("§cSynthaxe correcte : /setshopzone <pos1/po2>");
			}			
		}
		
		
		return false;
	}

}
