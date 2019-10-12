package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.RulesUtils;

public class ReglesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("regles")){
			
			if(args.length==0){
				
				if(s instanceof Player){
					
					Player p = (Player)s;
					
					RulesUtils.sendRules(p);
					
				}
				
			}else{
				
				if(args.length ==1 && s.hasPermission("rules.regles.admin")){
					
					if(Bukkit.getPlayer(args[0]) != null){
						
						Player reader = Bukkit.getPlayer(args[0]);
						RulesUtils.sendRules(reader);
						
						
					}else{
						s.sendMessage("§cCe joueur n'est pas connecté");
					}
					
				}else{
					
					s.sendMessage("§cSynthaxe correcte : /regles");
					if(s.hasPermission("rules.regles.admin")){s.sendMessage("§c ou /regles <joueur>");}
					
				}
				
				
			}
			
			
			
			
		}
		
		return false;
	}

}
