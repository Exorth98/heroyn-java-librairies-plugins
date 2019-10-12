package fr.exorth.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.AHEconomy;
import net.milkbowl.vault.economy.Economy;

public class DreamsCommand implements CommandExecutor {
	
	public Economy economy = AHEconomy.getInstance().economy;

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		

		
		if(cmd.getName().equalsIgnoreCase("dreams")){
			

			
			if(args.length==0){
				if(s instanceof Player){
					
					Player p = (Player) s;
					p.sendMessage("§3Vous avez: " + "§6" + economy.getBalance(p) + " §3Dreams");
					
				}else{
					s.sendMessage("§cSeuls les joueurs ont une balance");
				}
			}
			else if (args.length == 1){
				
				if(args[0]=="add"){
					
					s.sendMessage("§c/dreams add <joueur> <montant>");
					
				}
				else if(args[0]=="remove"){
					
					s.sendMessage("§c/dreams remove <joueur> <montant>");
					
				}
				else{
					String PlayerName = args[0];
					
					if(economy.hasAccount(PlayerName)){
						s.sendMessage("§3" + PlayerName + " à: " + "§6" + economy.getBalance(PlayerName) + " §3Dreams");
					}else{
						s.sendMessage("§cCe joueur n'a pas de compte");
					}
				}

			}
			
			if(args.length >1){
				
				if(args[0]=="add"){
					if(args.length==3){
						
						String PlayerName = args[1];
						
						if(economy.hasAccount(PlayerName)){
							
							if(isNumeric(args[2])){
								
								Double montant = Double.parseDouble(args[2]);
								
								economy.bankDeposit(PlayerName, montant);
								
								s.sendMessage("§3Vous avez ajouté §6" + montant + " §3Dreams à la banque de §6" + PlayerName);
								
							}else{
								s.sendMessage("§cVeuillez entrer un montant correct");
							}
							
							
						}else{
							s.sendMessage("§cCe joueur n'a pas de compte");
						}
						
						
					}else{
						s.sendMessage("§c/dreams add <joueur> <montant>");	
					}
				}
				else if(args[0]=="remove"){
					if(args.length==3){
						
						String PlayerName = args[1];
						
						if(economy.hasAccount(PlayerName)){
							
							if(isNumeric(args[2])){
								
								Double montant = Double.parseDouble(args[2]);
								
								economy.bankWithdraw(PlayerName, montant);
								
								s.sendMessage("§3Vous avez retiré §6" + montant + " §3Dreams de la banque de §6" + PlayerName);
								
							}else{
								s.sendMessage("§cVeuillez entrer un montant correct");
							}
							
							
						}else{
							s.sendMessage("§cCe joueur n'a pas de compte");
						}
						
					}else{
						s.sendMessage("§c/dreams remove <joueur> <montant>");
					}
				}
				
			}
			
		}
		
		return false;
	}
	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
		  Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
