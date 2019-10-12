package fr.exorth.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.util.ZonesUtils;
import zone.Zone;

public class ZoneCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(s instanceof Player) {
			
			Player p = (Player) s;
			
			if(args.length==0) {
				
				s.sendMessage("§6===== Commandes Zone =====");
				s.sendMessage("§e/zone create <nom>");
				s.sendMessage("§e/zone remove <nom>");
				s.sendMessage("§e/zone list");
				s.sendMessage("§e/zone set <nom> <pos1/pos2>");
				s.sendMessage("§e/zone set <nom> entrymsg <message>");
				s.sendMessage("§e/zone set <nom> exitmsg <message>");
				
			}else {
				
				if(args[0].equalsIgnoreCase("create")) {
					
					if(args.length==2) {
						
						String name = args[1];
						
						if(!ZonesUtils.zoneExist(name)) {
							
							Zone zone = new Zone(name);
							zone.saveZone();
							
							s.sendMessage("§aZone crée");
						}else {
							s.sendMessage("§cUne zone a ce nom existe déjà");
						}
						
					}else{
						s.sendMessage("§e/zone create <nom>");
					}
					
					
				}
				else if(args[0].equalsIgnoreCase("remove")) {
					
					if(args.length==2) {
						
						String name = args[1];
						
						if(ZonesUtils.zoneExist(name)) {
							
							Zone zone = Zone.getZone(name);
							zone.removeZone();
							
							s.sendMessage("§aZone supprimée");
						}else{
							s.sendMessage("§cCette zone n'existe pas");
						}
						
					}else{
						s.sendMessage("§e/zone remove <nom>");
					}
					
				}
				else if(args[0].equalsIgnoreCase("list")) {
					
					ZonesUtils.listZones(p);
					
				}
				else if(args[0].equalsIgnoreCase("set")) {
					
					if(args.length==3 || args.length>=4) {
						
						String name = args[1];
						
						if(ZonesUtils.zoneExist(name)) {
							
							if(args.length==3) {
								
								if(args[2].equalsIgnoreCase("pos1")) {
									
									Location loc = p.getLocation();
									Zone zone = Zone.getZone(name);
									zone.setPos1(loc);
									
									s.sendMessage("§aLa pos1 de la zone §b"+name+" §aà été définie à votre position");
									
								}else if(args[2].equalsIgnoreCase("pos2")) {
									
									Location loc = p.getLocation();
									Zone zone = Zone.getZone(name);
									zone.setPos2(loc);
									
									s.sendMessage("§aLa pos2 de la zone §b"+name+" §aà été définie à votre position");
									
								}else {
									s.sendMessage("§e/zone set <nom> <pos1/pos2>");
									s.sendMessage("§e/zone set <nom> entrymsg <message>");
									s.sendMessage("§e/zone set <nom> exitmsg <message>");
								}
								
							}
							else {
								
								if(args[2].equalsIgnoreCase("entrymsg")) {
									
									StringBuilder sb = new StringBuilder();
									
									for(int i =3;i<args.length;i++) {sb.append(args[i].replace("&", "§")+" ");}
									
									Zone zone = Zone.getZone(name);
									zone.setEntrymsg(sb.toString());
									
									s.sendMessage("§aMessage d'entrée dans la zone §b"+name+" §achangé");
									
								}
								else if(args[2].equalsIgnoreCase("exitmsg")) {
									
									StringBuilder sb = new StringBuilder();
									
									for(int i =3;i<args.length;i++) {sb.append(args[i].replace("&", "§")+" ");}
									
									Zone zone = Zone.getZone(name);
									zone.setExitmsg(sb.toString());
									
									s.sendMessage("§aMessage de sortie de la zone §b"+name+" §achangé");
									
								}
							}
							
						}else{
							s.sendMessage("§cCette zone n'existe pas");
						}
						

						
					}else {
						s.sendMessage("§e/zone set <nom> <pos1/pos2>");
						s.sendMessage("§e/zone set <nom> entrymsg <message>");
						s.sendMessage("§e/zone set <nom> exitmsg <message>");
					}
					
					
				}else {
					s.sendMessage("§cCommande inconnue (/zone pour avoir les commandes)");
				}
			}
			
		}else {
			s.sendMessage("§cCommande reservée aux joueurs");
		}
		
		return false;
	}

}
