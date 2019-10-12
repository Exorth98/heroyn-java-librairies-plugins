package fr.exorth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.GainUtils;

public class GainsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(s instanceof Player){
			
			Player p = (Player) s;
			
			if(cmd.getName().equalsIgnoreCase("gains")){
				
				if(GainUtils.getGainsNumber(p)>0){
					
					GainUtils.giveGains(p);
					
					if(GainUtils.getGainsNumber(p)>0){
						p.sendMessage("�6Vous n'avez pas la place de r�cup�rer la totalit� de vos gains");
						p.sendMessage("�6Vous avez encore �a" + GainUtils.getGainsNumber(p) + " �6gains potentiellement partiels � r�cup�rer");
					}
					else{
						p.sendMessage("�6Vous avez r�cuper� la totalit� de vos gains");
					}
					
				}else{				
					p.sendMessage("�cVous n'avez aucun gain en attente");
				}
			
			}
			
			
			
		}else{
			s.sendMessage("�cCommande res�rv�e aux joueurs");
		}
		
		
		
		return false;
	}
	
	

}
