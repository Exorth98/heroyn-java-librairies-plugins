package fr.exorth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.Zone;

public class ShopZoneCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("setshopzone")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				if(args.length==1) {
					
					if(args[0].equalsIgnoreCase("pos1")) {
						
						Zone.setPos1(p.getLocation());
						s.sendMessage("§aPosition 1 mise a jour");
						
					}
					else if(args[0].equalsIgnoreCase("pos1")) {
						
						Zone.setPos2(p.getLocation());
						s.sendMessage("§aPosition 2 mise a jour");
						
					}
					else {
						s.sendMessage("§c/setshopzone <pos1/pos2>");
					}
					
					
				}else {
					
					s.sendMessage("§c/setshopzone <pos1/pos2>");
					
				}
				
			}
			
			
		}
		
		return false;
	}

}
