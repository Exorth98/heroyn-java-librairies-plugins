package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.FactionTuto;
import fr.exorth.util.FTUtils;

public class FTCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("ft")){
				

				
				if(args.length==0){
					
					s.sendMessage("\n\n\n§6===== FactionTuto Commands =====");
					
					if(s.hasPermission("factiontuto.ft.admin")){
						s.sendMessage("§e/ft create <nom du tuto>");
						s.sendMessage("§e/ft remove <nom du tuto>");
						s.sendMessage("§e/ft addpoint <nom du tuto> <nom du point>");
						s.sendMessage("§e/ft removepoint <nom du tuto> <nom du point>");
						s.sendMessage("§e/ft play <nom du tuto> <joueur>");
					}

					s.sendMessage("§e/ft exit§f, pour sortir d'un tuto en cours");
					s.sendMessage("§6================================");
				}else{
					
				if(args[0].equalsIgnoreCase("create") && s.hasPermission("factiontuto.ft.admin")){
					
					if(args.length==2){
						
						String tutoName = args[1];
						
						if(!FTUtils.TutoExist(tutoName)){
							
							FTUtils.createTuto(tutoName);
							s.sendMessage("§aLe tuto §b"+tutoName+" §aà été créé");
							
						}else{
							s.sendMessage("§cCe nom de Tuto est déjà utilisé");
						}
						
					}else{
						s.sendMessage("§e/ft create <nom du tuto>");
					}
		
				}
				
				
				else if(args[0].equalsIgnoreCase("remove")&& s.hasPermission("factiontuto.ft.admin")){
					
					if(args.length==2){
						
						String tutoName = args[1];
						
						if(FTUtils.TutoExist(tutoName)){
							
							FTUtils.deleteTuto(tutoName);
							s.sendMessage("§aLe tuto §b"+tutoName+" §aà été supprimé");
							
						}else{
							s.sendMessage("§cCe Tuto n'existe pas");
						}
						
					}else{
						s.sendMessage("§e/ft remove <nom du tuto>");
					}
					
				}
					
				
				else if(args[0].equalsIgnoreCase("addpoint")&& s.hasPermission("factiontuto.ft.admin")){
						if(args.length==3){
							
							if(s instanceof Player){
								
								Player p = (Player) s;
								
								String Tutoname = args[1];
								String Pointname = args[2];
								
								if(FTUtils.TutoExist(Tutoname)){
									
									if(!FTUtils.pointExist(Tutoname,Pointname)){										
										
										FTUtils.addPoint(Tutoname,Pointname,p.getLocation());
										p.sendMessage("§aLe point §b"+Pointname+" §aà été ajouté au tuto §b"+Tutoname);
										
									}else{
										s.sendMessage("§cCe nom de point existe déjà pour ce tuto");
									}
									
								}else{
									s.sendMessage("§cAucun tuto à ce nom");
								}
								
							}else{
								s.sendMessage("§cCommande reservée aux joueurs");
							}

							
						}else{s.sendMessage("§e/ft addpoint <nom du tuto> <nom du point>");}
					}
					
					
					else if(args[0].equalsIgnoreCase("removepoint")&& s.hasPermission("factiontuto.ft.admin")){
						if(args.length==3){
							
							String Tutoname = args[1];
							String Pointname = args[2];
							
							if(FTUtils.TutoExist(Tutoname)){
							
								
								if(FTUtils.pointExist(Tutoname,Pointname)){
																
									FTUtils.removePoint(Tutoname,Pointname);
									
									s.sendMessage("§aLe point §b"+Pointname+" §aà été supprimé du Tuto §b"+Tutoname);
									
								}else{
									s.sendMessage("§cCe point n'existe pas pour ce tuto");
								}
								
							}else{
								s.sendMessage("§cAucun tuto à ce nom");
							}
						
						}else{s.sendMessage("§e/ft removepoint <nom du tuto> <nom du point>");}				
					}
					
					else if(args[0].equalsIgnoreCase("exit")&& s.hasPermission("factiontuto.ft.exit")){
						
						if(s instanceof Player){
							
							Player p = (Player) s;
							
							if(FactionTuto.getInstance().inTuto.containsKey(p)){
								
								FTUtils.cancelTuto(p);
								
							}else{
								p.sendMessage("§cTu n'es pas en tuto");
							}
							
						}else{
							s.sendMessage("§cCommande reservée aux joueurs");
						}	
						
					}
					
					else if(args[0].equalsIgnoreCase("play")&& s.hasPermission("factiontuto.ft.admin")){
						
						if(args.length==3){
							
							String Tutoname = args[1];
							String joueur = args[2];
							
							if(FTUtils.TutoExist(Tutoname)){
											
								if(Bukkit.getPlayer(joueur) != null){
														
									Player pl = Bukkit.getPlayer(joueur);
									
									if(FTUtils.tutoIsPlayable(Tutoname,s,pl)){
										
										FTUtils.enterTuto(pl);
										FTUtils.playTuto(Tutoname,pl);
										
									}	
									
								}else{
									s.sendMessage("§cCe joueur n'est pas connecté");
								}
							}else{
								s.sendMessage("§cCe tuto n'existe pas");
							}
							
						}else{
							s.sendMessage("§e/ft play <nom du tuto> <joueur>");
						}
					}
					
					
					
					
					
				}				
			}			
				
		return false;
	}
}
