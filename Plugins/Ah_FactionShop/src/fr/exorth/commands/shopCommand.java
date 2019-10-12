package fr.exorth.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.exorth.factionshop;
import fr.exorth.util.OtherUtils;
import fr.exorth.util.ShopZone;
import fr.exorth.util.confirmation;
import fr.exorth.util.insellinventory;
import net.milkbowl.vault.economy.Economy;

public class shopCommand implements CommandExecutor {
	
	public Economy economy = null;
	
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = factionshop.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			
			
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("hdv")){
				
				if(ShopZone.isInside(p)){
					
					if(args.length==0){
						
						new insellinventory().createInventories(p, 1, 0);			
						p.openInventory(factionshop.getInstance().sellinventories.get(p).get(factionshop.getInstance().sellinventories.get(p).size()-1));
						return true;
					}
					
					else if(args.length==3){
						
						if(args[0].equalsIgnoreCase("sell")){
							
							if(isNumeric(args[1])){
								
								if(isNumeric(args[2])){
									
									if(isOk(args[2])){
										
										if(isNumberOfSalesOk(p.getUniqueId())){
											
											int hours = Integer.parseInt(args[2]);
											
											double prix = Double.parseDouble(args[1]);
											
											double taxe = OtherUtils.getTaxe(prix,hours);
											
											double dreams = 0;
											if(setupEconomy()){
												dreams = economy.getBalance(p);
											}
											
											
											if (dreams-taxe>=0){
												
												@SuppressWarnings("deprecation")
												ItemStack item = p.getItemInHand();
												
												if(item != null){
													if(item.getType() != Material.AIR){
																											
														new confirmation().createSellConfirmation(item, prix, taxe, hours, p);
																							
													}else{
														p.sendMessage("§cPrenez l'item à vendre dans votre main");
													}
												}else{
													p.sendMessage("§cPrenez l'item à vendre dans votre main");
												}
											}else{
												p.sendMessage("§cVous n'avez pas assez d'argent pour payer la taxe de §3" + taxe + " Dreams");
											}										
										}else{
											p.sendMessage("§cTu as ateint ta limite de ventes dans le shop, tu ne peux pas vendre cet item");
										}
									}else{
										p.sendMessage("§cVeulliez entrer un temps en jours de 3, 7 ou 10");
									}
								}else{
									p.sendMessage("§cLe temps est à entrer en jours (3 / 7 / 10)");
								}
							}else{
								p.sendMessage("§cMerci d'entrer un prix");
							}
						}else{
							p.sendMessage("§cPour mettre en vente un item : /hdv sell <prix> <temps (jours)>");
						}										
					}else{
						p.sendMessage("§cPour mettre en vente un item : /hdv sell <prix> <temps (jours)>");
					}
				}else{
					p.sendMessage("§cTu dois être dans la zone marchande pour acceder au shop");
				}
				
								
			}			
		}else{
			sender.sendMessage("Commande réservée aux joueurs");
		}	
		return false;
	}
	
	
	
	private boolean isNumberOfSalesOk(UUID uuid) {
		
		int max = getMaxSales(uuid);
		
		if(factionshop.getInstance().numberofsales.containsKey(uuid)){
			return factionshop.getInstance().numberofsales.get(uuid) < max ;
		}else{
			return true;
		}
		
	}



	private int getMaxSales(UUID uuid) {
		int max = 3;
		
		for(int i=1;i<=50;i++){
			
			
			if(Bukkit.getPlayer(uuid).hasPermission("factionshop.maxsales." + i)){
				max = i;
			}
			

			
		}
		
		return max;

	}

	private boolean isOk(String string) {
		int hours = Integer.parseInt(string);
		
		if(hours == 3 || hours == 7 || hours == 10){
			return true;
		}else{
			return false;
		}
		
	}



	private static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
