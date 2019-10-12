package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;
import fr.exorth.util.ItemUtil;
import fr.exorth.util.Utils;

public class giveMagicKeyCommand implements CommandExecutor {
	
	public FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<String> keys = (ArrayList<String>) config.getList("keys");
	
	@SuppressWarnings("unchecked")
	ArrayList<String> bkeys = (ArrayList<String>) config.getList("boughtkeys");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		

			if(cmd.getName().equalsIgnoreCase("givemagickey")){
				
				
				
				if(args.length == 2|| args.length == 3 ){
					
				
						
					Player receiver = Bukkit.getPlayer(args[0])	;
					

						
						if(isNumeric(args[1])){
							
							int number = Integer.parseInt(args[1]);
							if(number >0){
								
								if(receiver != null){
									
									if(args.length == 2 || (args.length == 3 && !args[2].equalsIgnoreCase("true"))){
										
										if(!invFull(receiver)){
											
											if(keys == null){keys = new ArrayList<String>();}
											
											for(int i =0;i<number;i++){
												
												ItemStack key = Utils.getMagicKey();
												keys.add(ItemUtil.getRef(key));	
												receiver.getInventory().addItem(key);
											}

											config.set("keys",keys);
											MagicChest.getInstance().saveConfig();								
																			
											sender.sendMessage("§6Vous avez donné §a" + number + " §6Clé(s) Magique(s) à §a" + receiver.getName());
											receiver.sendMessage("§6Vous avez reçu §a" + number + " §6Clé(s) Magique(s) !");
											
											return true;
										}else{
											sender.sendMessage("Ce joueur a l'inventaire plein !");
										}
										
									}
									
									if(args.length == 3 && args[2].equalsIgnoreCase("true")){
											
											String UUID = receiver.getUniqueId().toString();
											
											Utils.addBoughtKeys(UUID, number);
											
																		
										receiver.sendMessage("§6Vous avez acheté §a" + number + " §6Clé(s) Magique(s) !");
										
										return true;
										
									}
																				
							}else{
								sender.sendMessage("§cCe joueur n'est pas connecté");
							}
						}else{
							sender.sendMessage("§cMerci d'entrer un nombre correct");
						}
					}else{
						sender.sendMessage("§cMerci d'entrer un nombre correct");
					}
						
						
						
						
					
									
				}else{
					sender.sendMessage("§cSynthaxe correcte : /givemagickey <Joueur> <Quantité>");
				}			
			}
			
		return false;
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;          
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
