package fr.exorth.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Elevation;

public class SetElevationZoneCommand implements CommandExecutor {
	
	public FileConfiguration config = Elevation.getInstance().getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			
			if(cmd.getName().equalsIgnoreCase("setelevationzone")){
				
				Player p = (Player) sender;
				
				Location loc = p.getLocation();
				
				config.set("center", loc);
				Elevation.getInstance().saveConfig();
				
			}
			

		}else{
			sender.sendMessage("§cCommande réservée aux joueurs");
		}
		
		
		
		return false;
	}

}
