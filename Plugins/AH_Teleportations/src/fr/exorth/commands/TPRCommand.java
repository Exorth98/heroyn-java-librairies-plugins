package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.TeleportationsUtils;

public class TPRCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("tpr")){
			
			if(args.length>0){
				
				 if(args[0].equalsIgnoreCase("other") && s.hasPermission("Teleportations.tpr.admin")){
						
						if(args.length == 3){
							
							String player = args[1];
							String point = args[2];
							
							if(Bukkit.getPlayer(player) != null){
								
								Player traveler = Bukkit.getPlayer(player);
								
								if(TeleportationsUtils.pointExist(point)){
									
									TeleportationsUtils.sendPlayer(traveler,point);
									s.sendMessage("§a" + traveler.getName() + " §e envoyé au point §a" + point);
									
								}else{
									s.sendMessage("§cCe point n'existe pas (/tpr list pour avoir la liste)");
								}
															
							}else{
								s.sendMessage("§cLe joueur n'est pas connecté");
							}
							
						}else{
							s.sendMessage("§e/tpr other <joueur> <destination>");
						}
						
				}
				
			}
			
			if(s instanceof Player){
				
				Player p = (Player) s;
				
				if(args.length==0){
					
					p.sendMessage("§6=====Téléportations Commandes=====");
					p.sendMessage("§e/tpr <Destination>§r, pour se téléporter à un point");
					p.sendMessage("§e/tpr list§r, pour lister les points disponibles");
					
					if(p.hasPermission("Teleportations.tpr.admin")){
						
						p.sendMessage("§e/tpr create <nom du point>");
						p.sendMessage("§e/tpr remove <nom du point>");
						p.sendMessage("§e/tpr other <joueur> <destination>");
						
					}
					
				}else{
					
					if(args[0].equalsIgnoreCase("list")){
						
						TeleportationsUtils.ListPoints(p);
						
					}
					else if(args[0].equalsIgnoreCase("create") && p.hasPermission("Teleportations.tpr.admin")){
						
						if(args.length == 2){
							
							String point = args[1];
							TeleportationsUtils.createPoint(point,p.getLocation());
							p.sendMessage("§eLe point §a"+point+" §ea été créé à votre position");
							
							
						}else{
							p.sendMessage("§e/tpr create <nom du point>");
						}
						
					}
					else if(args[0].equalsIgnoreCase("remove") && p.hasPermission("Teleportations.tpr.admin")){
						
						if(args.length == 2){
							
							String point = args[1];
							
							if(TeleportationsUtils.pointExist(point)){
								
								TeleportationsUtils.removePoint(point);
								p.sendMessage("§eLe point §a"+point+" §ea été supprimé");
								
							}else{
								p.sendMessage("§cCe point n'existe pas (/tpr list pour avoir la liste)");
							}
							
						}else{
							p.sendMessage("§e/tpr remove <nom du point>");
						}
						
					}
					else{
						
						if(!args[0].equalsIgnoreCase("other")){
							
							
							String point = args[0];
							
							if(TeleportationsUtils.pointExist(point)){
								
								TeleportationsUtils.sendPlayer(p,point);
								
							}else{
								s.sendMessage("§cCe point n'existe pas (/tpr list pour avoir la liste)");
							}
							
						}
						
					}
					
				}
				
				
			}else{


				 
			}
			
			
		
			
		}
	
		return false;
	}

}
