package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import fr.exorth.MagicChest;

public class CheckCommand implements CommandExecutor {
	
	FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<String> keys = (ArrayList<String>) config.getList("keys");

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {

		if(cmd.getName().equalsIgnoreCase("check")){
			
			if(args.length == 1){
				
				if(isNumeric(args[0])){
					
					if(args[0].length()==7){
						
						if(keys.contains(args[0])){
							s.sendMessage("§aClé valide !");
						}else{
							s.sendMessage("§cClé NON valide !");
						}
						
						
						return true;
						
					}else{
						s.sendMessage("§cLe numéro de validité doit être composé de §47 §cchiffres");
					}				
				}else{
					s.sendMessage("§cLe numéro de validité doit être composé de chiffres");
				}				
			}else{
				s.sendMessage("§c/check <numéro de validité>");
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
