package fr.exorth.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.PSEconomy;
import fr.exorth.numeric.Numeric;
import fr.exorth.seller.ItemSeller;
import fr.exorth.util.PSUtil;
import fr.exorth.util.Zone;

public class HDVCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("hdv")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				if(Zone.isInZone(p)) {
					
					if(args.length==0) {
						
						//Ouvrir Shop
						
					}
					else if(args.length==3 && args[0].equalsIgnoreCase("sell")){
						
						if(Numeric.isDouble(args[1])) {
							
							double price = Double.parseDouble(args[1]);
							
							if(Numeric.isInteger(args[2])) {
					
								int days =Integer.parseInt(args[2]);
								
								if(days == 3 || days == 7 || days == 10) {
									
									double taxe = PSUtil.getTaxe(price, days);
									
									double playerBalance = PSEconomy.getBalance(p);
									
									if(playerBalance - taxe >0) {
										
										ItemSeller seller = new ItemSeller(p.getUniqueId());
										
										if(seller.canSell()) {
											
											@SuppressWarnings("deprecation")
											ItemStack item = p.getItemInHand();
											
											if(item != null){
												if(item.getType() != Material.AIR){
																										
													//confirmation
																						
												}else{
													p.sendMessage("§cPrenez l'item à vendre dans votre main");
												}
											}else{
												p.sendMessage("§cPrenez l'item à vendre dans votre main");
											}
											
										}else {
											
											p.sendMessage("Tu as déjà trop d'items en vente, deviens VIP pour augmenter cette limite");
										}
										
									}else {
										
										s.sendMessage("§cTu n'as pas assez d'argent pour payer la taxe");
									}
																
									
								}else {
									s.sendMessage("La durée doit être de 3,7,ou 10 jours");
								}
								
							}else {
								s.sendMessage("Merci d'entrer une durée de mise en vente correcte (en jours)");
							}
													
							
						}else {
							s.sendMessage("Merci d'entrer un prix Correct");
						}
						
						
						
					}
					else {
						s.sendMessage("§e/hdv");
						s.sendMessage("§e/hdv sell <prix> <temps (jours)>");
					}				
				}else {
					p.sendMessage("§cTu doit être dans la zone Marchande");
				}				
			}		
		}	
		return false;
	}

}
/*

/hdv

/hdv sell <prix> <temps>



*/