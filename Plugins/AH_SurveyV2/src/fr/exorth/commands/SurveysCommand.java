package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.SurveysMenu;

public class SurveysCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("surveys")){
			
		
				if(args.length == 0){
					
					if(s instanceof Player){
						
						Player p = (Player) s;
												
						new SurveysMenu().open(p);
					}else{
						s.sendMessage("§cCommande réservée aux joueurs");
					}

					
				}else if(args.length==1){
					
					String joueur = args[0];
					
					if(s.hasPermission("AHSurveys.svs.others")){
												
						if(Bukkit.getPlayer(joueur)!= null){
							
							Bukkit.getPlayer(joueur).performCommand("svs");
							
						}else{
							s.sendMessage("§cCe joueur n'est pas en ligne");
						}
						
					}else{
						s.sendMessage("§cVous n'avez pas la prmission pour cette commande");
					}

					
				}else{
					s.sendMessage("§c/surveys  ou /svs");
					s.sendMessage("§c/surveys <joueurs>");
				     }

		}
		
		
		return false;
	}

}
