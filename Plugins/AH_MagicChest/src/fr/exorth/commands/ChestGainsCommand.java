package fr.exorth.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;
import fr.exorth.util.ItemUtil;
import fr.exorth.util.Utils;

public class ChestGainsCommand implements CommandExecutor {
	
	public FileConfiguration config = MagicChest.getInstance().getConfig();
	
	
	@SuppressWarnings("unchecked")
	ArrayList<ItemStack> gains = (ArrayList<ItemStack>) config.getList("gains");

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("chestgains")){
				
				if(args.length >0){
					
					if(args[0].equalsIgnoreCase("add")){
						
						if(args.length == 4){
							
							if(p.getItemInHand().getType() != Material.AIR){
								
								if(isOk(args[2])){
									
									if(isPercent(args[3])){
										
										if(!Utils.getGainsName().contains(args[1])){
											
											ItemStack iteminHand = p.getItemInHand();
											String name = args[1];
											String pref = Utils.getPref(args[2]);
											String percent = args[3];
											
											
											//ItemStack item = p.getItemInHand().clone();
											ItemStack item = ItemUtil.setName(iteminHand, pref + " " + name);
											item = ItemUtil.setLores(item, Arrays.asList( percent + "§e de chance de le gagner"));
											
											if(gains == null){gains = new ArrayList<ItemStack>();}
											gains.add(item);
											config.set("gains", gains);
											config.set("actions."+ name , "[X] Insérer une commande à executer");
											MagicChest.getInstance().saveConfig();
											
										}else{
											p.sendMessage("§cCe nom est déja utilisé");
										}								
									}else{
										p.sendMessage("§cMerci d'entrer une probabilité en pourcentage (Ex: '20%')");
									}
								}else{
									p.sendMessage("§cMerci de choisir la rareté selon : <Commun|Rare|Epique|Légendaire>");
								}						
							}else{
								p.sendMessage("§cMerci de prendre l'item d'apparence dans la main");
							}					
						}else{
							p.sendMessage("§c/chestgains add <nom apparant> <Rareté> <pourcentage>");
						}
					}
					
					else if(args[0].equalsIgnoreCase("list")){
						
						if(args.length == 1){
												
							int count = 1;
							
							if(gains ==null){gains = new ArrayList<ItemStack>();}
							
							for(ItemStack item : gains){
								
								String pref;
								String color;
								if(Utils.getValidGains().contains(item)){pref = "§9[" + count + "] - ";color = "§9";}else{pref = "§c[" + count + "] [!] - ";color = "§c";}
								String Name = item.getItemMeta().getDisplayName();
								String prob = item.getItemMeta().getLore().get(0).split("de")[0] + "de probabilité";
								p.sendMessage( pref + Name + color + " ///§e " + prob);
								count++;
							}
							
							if(gains.size()==0){
								p.sendMessage("§6Aucuns gains");
							}
						}else{
							p.sendMessage("§c/chestgains list");
						}
						
					}
					
					else if(args[0].equalsIgnoreCase("remove")){
						
						ConfigurationSection actions = config.getConfigurationSection("actions");
						
						if(args.length == 2 && isNumeric(args[1])){
							
							int ind = Integer.parseInt(args[1])-1;
							
							if(gains != null){
								
								if(ind>-1 && ind<gains.size()){
									
									String name = gains.get(ind).getItemMeta().getDisplayName();
									String namev = gains.get(ind).getItemMeta().getDisplayName().split("] ")[1];
									p.sendMessage(name + "§6 à été suprimé");
									gains.remove(ind);
									config.set("gains", gains);
									
									for(String action : actions.getKeys(false)){
										if(action.contains(namev)){
											config.set("actions." + namev, null);
										}
									}
									
									MagicChest.getInstance().saveConfig();
									
								}else{
									p.sendMessage("§cNuméro incorrect, penses à faire '/chestgains list' pour avoir les numéros");
								}						
							}else{
								p.sendMessage("§cIl n'y a aucun gain");
							}
							
						}else{
							p.sendMessage("§c/chestgains remove <numéro>");
						}
						
					}
					
					else if(args[0].equalsIgnoreCase("set%")){
						
						if(args.length==3 && isNumeric(args[1])){
							
							int ind = Integer.parseInt(args[1])-1;
							
							if(gains != null){
								
								if(ind>-1 && ind<gains.size()){
									
									if(isPercent(args[2])){
										
										String percent = args[2];
																			
										String name = gains.get(ind).getItemMeta().getDisplayName();
										ItemStack item = gains.get(ind);
										 item = ItemUtil.setLores(item, Arrays.asList( percent + "§e de chance de le gagner"));
										gains.set(ind, item);

										config.set("gains", gains);
										MagicChest.getInstance().saveConfig();
										
										p.sendMessage("§6Probabilité de " + name + " §6mise à " + percent);
										
									}else{
										p.sendMessage("§cMerci d'entrer une probabilité en pourcentage (Ex: '20%')");
									}
									
								}else{
									p.sendMessage("§cNuméro incorrect, penses à faire '/chestgains list' pour avoir les numéros");
								}						
							}else{
								p.sendMessage("§cIl n'y a aucun gain");
							}
						
						}else{
							p.sendMessage("§c/chestgains set% <numéro> <pourcentage>");
						}
						
					}
					
					else if(args[0].equalsIgnoreCase("help")){
						
						p.sendMessage("§6===== Commandes de Gestion des Gains =====");	
						p.sendMessage("§6  ");
						p.sendMessage("§6- /chestgains add <nom apparant> <Rareté> <pourcentage>");
						p.sendMessage("§6- /chestgains list");
						p.sendMessage("§6- /chestgains remove <numéro>");
						p.sendMessage("§6- /chestgains set% <numéro> <pourcentage>");
						
					}else{
						p.sendMessage("§c/chestgains help");
					}
					
				}else{
					p.sendMessage("§c/chestgains help");
				}
		
			}
			
		}
		
		return false;
	}

	private boolean isPercent(String s) {
		
		if(s.charAt(s.length()-1)== '%'){
			if(s.split("%")[0] != null){
				if(isNumeric(s.split("%")[0])){
					return true;
				}
			}
		}
		
		return false;
	}

		
	private static boolean isNumeric(String str)  
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
	

	private boolean isOk(String rank) {
		
		if(rank.equals("Commun") ||rank.equals("Rare") ||rank.equals("Epique") ||rank.equals("Légendaire")){
			return true;
		}else{
			return false;
		}
		
		
		
	}

}
