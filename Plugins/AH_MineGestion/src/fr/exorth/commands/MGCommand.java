package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import fr.exorth.MineGestion;
import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;
import net.minecraft.server.v1_12_R1.Entity;

public class MGCommand implements CommandExecutor {
	
	FileConfiguration config = MineGestion.getInstance().getConfig();

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("mg")){
			
			if(s instanceof Player){
				
				Player p = (Player) s;
				
				if(args.length==0){
					
					s.sendMessage("§6======= MineGestion =======");
					s.sendMessage("§e/mg setzone <pos1/pos2>");
					s.sendMessage("§e/mg createnpc");
					s.sendMessage("§e/mg settype <joueur> <type>");
					s.sendMessage("§e/mg setexpiration <joueur> <expiration>");
					s.sendMessage("§6===========================");
				}else{
					
					if(args[0].equalsIgnoreCase("setzone")){
						
						if(args.length==2){
							
							if(args[1].equalsIgnoreCase("pos1")){
								
								config.set("MineZone.pos1", p.getLocation());
								MineGestion.getInstance().saveConfig();
								s.sendMessage("§6Pos1 de la zone Minière enregistrée a votre position");
								
							}
							else if(args[1].equalsIgnoreCase("pos2")){
								
								config.set("MineZone.pos2", p.getLocation());
								MineGestion.getInstance().saveConfig();
								s.sendMessage("§6Pos2 de la zone Minière enregistrée a votre position");
								
							}else{
								s.sendMessage("§e/mg setzone pos1 ou /mg setzone pos2");
							}
							
						}else{
							s.sendMessage("§e/mg setzone <pos1/pos2>");
						}
					}
					else if(args[0].equalsIgnoreCase("createnpc")){
						
						if(args.length==1){
							
							Location loc = p.getLocation();
							
				            Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
				            Entity nmsVillager = ((CraftEntity) npc).getHandle();
				           
				            //custom le mob nms
				            nmsVillager.setCustomName("§b§lLocation et gestion des Pioches");
				            nmsVillager.setCustomNameVisible(true);
				            nmsVillager.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				           
				            //desactiver l'ai
				            npc.setAI(false);
				            
				            s.sendMessage("§6NPC créé");
							
						}else{
							s.sendMessage("§e/mg createnpc");
						}
						
					}
					else if(args[0].equalsIgnoreCase("settype")){
						
						if(args.length== 3){
							
							if(Bukkit.getPlayer(args[1]) != null){
								
								Player pl = Bukkit.getPlayer(args[1]);
								
								if(RentPickaxe.hasPickaxe(pl.getUniqueId())){
									
									if(isType(args[2])){
										
										PickaxeType pt = PickaxeType.valueOf(args[2]);
										RentPickaxe rp = RentPickaxe.getPickaxe(pl.getUniqueId());
										
										rp.setType(pt);
										
										 s.sendMessage("§aType de pioche mis à §b"+pt.toString()+" §apour §b"+ pl.getName());
										
									}else{
										s.sendMessage("§cType invalide !");
										s.sendMessage("§cTypes: STONE | IRON | DIAMOND | DIAMOND_X");
									}
									
								}else{
									s.sendMessage("§cCe joueur n'as pas de location en cours");
								}
								
							}else{
								s.sendMessage("§cJoueur hors ligne");
							}
							
						}else{
							s.sendMessage("§e/mg settype <joueur> <type>");
						}
						
					}
					else if(args[0].equalsIgnoreCase("setexpiration")){
						
						if(args.length== 3){
													
							if(Bukkit.getPlayer(args[1]) != null){						
								
								Player pl = Bukkit.getPlayer(args[1]);
								
								if(RentPickaxe.hasPickaxe(pl.getUniqueId())){
									
									RentPickaxe rp = RentPickaxe.getPickaxe(pl.getUniqueId());
									
									if(isNumeric(args[2])){
										
										long exp = Long.parseLong(args[2]);
										
										rp.setExpiration(exp);
										
										s.sendMessage("§aTemps d'expiration mis à §b"+exp+" §amillisecondes pour §b"+ pl.getName());
										
									}else{
										s.sendMessage("§cEntrez un temps d'expiration en millisecondes");
									}
									
								}else{
									s.sendMessage("§cCe joueur n'as pas de location en cours");
								}							
							}else{
								s.sendMessage("§cJoueur hors ligne");
							}							
						}else{
							s.sendMessage("§e/mg setexpiration <joueur> <expiration>");
						}
					}
				}				
			}			
		}		
		return false;
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

	private boolean isType(String t) {
		
		for(PickaxeType pt : PickaxeType.values()){
			if(pt.toString().equals(t)){
				return true;
			}
		}
		
		return false;
	}
	
	

}
