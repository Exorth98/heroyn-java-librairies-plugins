package fr.exorth.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;
import fr.exorth.util.ItemUtil;
import fr.exorth.util.Utils;

public class AddGainCommand implements CommandExecutor {
	
	public FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<ItemStack> gives = (ArrayList<ItemStack>) config.getList("pendinggives");
	@SuppressWarnings("unchecked")
	ArrayList<String> keys = (ArrayList<String>) config.getList("keys");

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("addgain")){
			
			if(args.length == 3){
				
				UUID receiver = MagicChest.getInstance().inTirage.get(args[0]);
				
				if(receiver != null){
					
					if(isNumeric(args[2])){
						int amount = Integer.parseInt(args[2]);
						if(amount>0){
							
							if(args[1].equalsIgnoreCase("magickey")){
								
								if(keys == null){keys = new ArrayList<String>();}
								if(gives == null){gives = new ArrayList<ItemStack>();}
								
								if(Bukkit.getPlayer(receiver)!=null){
									
									Player p = Bukkit.getPlayer(receiver);
									
									int emptySlots= ItemUtil.getEmptiesSlotsNumber(p.getInventory());
									
									if(amount <= emptySlots){

										for(int i =0;i<amount;i++){
											
											ItemStack key = Utils.getMagicKey();
											keys.add(ItemUtil.getRef(key));	
											p.getInventory().addItem(key);
										}
										
										p.sendMessage("§6Votre récompense vient de vous être donnée");
										
									}else{
									
										ItemStack key = Utils.getMagicKey();
										
										key = ItemUtil.addLore(key, receiver.toString());
										key.setAmount(amount);
										
										for(int i =0;i<amount;i++){
											
											keys.add(ItemUtil.getRef(key));	
											
										}
										gives.add(key);

										config.set("keys",keys);
										MagicChest.getInstance().saveConfig();	
										
										config.set("pendinggives",gives);
										MagicChest.getInstance().saveConfig();	
										
										p.sendMessage("§6Vous n'avez pas assez de place dans votre inventaire pour récuperer votre récompense, celle-ci à étée ajoutée a vos gains");
									
									}
									
								}else{
									
									
									ItemStack key = Utils.getMagicKey();
									
									key = ItemUtil.addLore(key, receiver.toString());
									key.setAmount(amount);
									
									for(int i =0;i<amount;i++){
										
										keys.add(ItemUtil.getRef(key));	
										
									}
									gives.add(key);

									config.set("keys",keys);
									MagicChest.getInstance().saveConfig();	
									
									config.set("pendinggives",gives);
									MagicChest.getInstance().saveConfig();	
									
									
								}
								

								
							}
							else{
								
								
								int ID;
								byte data = 0;
								
								if(args[1].contains(":")){
									ID=Integer.parseInt(args[1].split(":")[0]);
									data=Byte.parseByte(args[1].split(":")[1]);
								}else{
									ID=Integer.parseInt(args[1]);
								}
								@SuppressWarnings("deprecation")
								Material mat = Material.getMaterial(ID);
								
								ItemStack Loot = new ItemStack(mat,amount,data);
								

								
								
								if(Bukkit.getPlayer(receiver)!=null){
									
									Player p = Bukkit.getPlayer(receiver);
									
									int emptySlots= ItemUtil.getEmptiesSlotsNumber(p.getInventory());
									int [] Stack = ItemUtil.getStacksNumber(Loot);
									int StackNumber = Stack[0];
									if(Stack[1]>0){StackNumber++;}
									
									if(StackNumber <= emptySlots){
										p.getInventory().addItem(Loot);
										p.sendMessage("§6Votre récompense vient de vous être donnée");
										
									}else{
										
										Loot = ItemUtil.addLore(Loot,receiver.toString());								
										if(gives==null){gives = new ArrayList<ItemStack>();}								
										gives.add(Loot);
										config.set("pendinggives",gives);
										MagicChest.getInstance().saveConfig();	
										p.sendMessage("§6Vous n'avez pas assez de place dans votre inventaire pour récuperer votre récompense, celle-ci à étée ajoutée a vos gains");
									}
									
								}else{
									
									//si dans gain
									Loot = ItemUtil.addLore(Loot,receiver.toString());								
									if(gives==null){gives = new ArrayList<ItemStack>();}								
									gives.add(Loot);
									config.set("pendinggives",gives);
									MagicChest.getInstance().saveConfig();	
									
								}
								
							}					

							
						}else{
							sender.sendMessage("§camount");
						}				
					}else{
						sender.sendMessage("§cEntrez une quantité correcte");
					}					
				}else{
					sender.sendMessage("§cCe joueur n'est pas en tirage");
				}				
			}else{
				sender.sendMessage("§c/addgain <joueur> <ID> <quantité>");
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
