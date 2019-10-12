package fr.exorth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getFloderCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("getfloder")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				if(args.length == 1) {
					
					String pseudo = args[0];
					
					Floder dossier = new Floder(pseudo);
					
					p.getInventory().addItem(dossier.getFolder());
					
					p.sendMessage("§eDossiermédical de §a"+ pseudo + " §ereçu");
					
				}else {
					p.sendMessage("§c Usage : /getfloder <pseudo>");
				}
				
				
			}else {
				s.sendMessage("§cCommande reservée aux joueurs");
			}
			
			
		}
		
		
		return false;
	}

}
