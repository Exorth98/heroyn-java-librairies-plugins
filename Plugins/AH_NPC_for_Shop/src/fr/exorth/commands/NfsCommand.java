package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import fr.exorth.NPCforShop;
import fr.exorth.utils.Utils;
import net.minecraft.server.v1_12_R1.Entity;

public class NfsCommand implements CommandExecutor {
	
	FileConfiguration config = NPCforShop.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<String> NPCs = (ArrayList<String>) config.getList("NPCs");

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("nfs")){
			
			if(s instanceof Player){
				
				Player p = (Player) s;
				
				if(args.length==0){
					p.sendMessage("§6===NPC for Shop commands===");
					p.sendMessage("§e/nfs create <nom> <Nom d'apparence> <Shop>");
					p.sendMessage("§e/nfs remove <nom>");
					p.sendMessage("§e/nfs list");
				}
				else{
					if(args[0].equalsIgnoreCase("create")){
						
						if(args.length==4){
							
							String name = args[1];
							String displayName = args[2].replace("&", "§").replace("_", " ");
							String shop = args[3];
							
							if(!Utils.nameExist(name)){	
								
								//creation dans configs
								if(NPCs==null){NPCs = new ArrayList<String>();}
					            Location loc = p.getLocation();
								
								NPCs.add(name);
								config.set("NPCs", NPCs);
								
								config.set(name + ".shop", shop);
								config.set(name + ".location", loc);
								
								NPCforShop.getInstance().saveConfig();
								
								//creation NPC
					            Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
					            Entity nmsVillager = ((CraftEntity) npc).getHandle();
					            nmsVillager.setCustomName(displayName);
					            nmsVillager.setCustomNameVisible(true);
					            nmsVillager.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
					            npc.setAI(false);
								
							}else{
								p.sendMessage("§cCe nom est déjà utilisé");
							}						
						}else{
							p.sendMessage("§e/nfs create <nom> <Nom d'apparence> <Shop>");
						}
						
						
					}
					else if(args[0].equalsIgnoreCase("remove")){
						
						if(args.length==2){
							
							String name = args[1];
							
							if(Utils.nameExist(name)){
								
								if(NPCs==null){NPCs = new ArrayList<String>();}
								
								//remove entity
								Location loc = (Location) config.get(name + ".location");
								
								for(org.bukkit.entity.Entity en :  loc.getWorld().getEntities()) {
								    if(en.getLocation().distance(loc) <= 1) 
								        en.remove();
								}
								
								//Remove des configs
								NPCs.remove(name);
								config.set("NPCs", NPCs);
								
								config.set(name, null);
								NPCforShop.getInstance().saveConfig();
								
								
							}else{
								p.sendMessage("§cAucun npc à ce nom, /nfs list");
							}
						}else{
							p.sendMessage("§e/nfs remove <nom>");
						}
						
					}else if(args[0].equalsIgnoreCase("list")){
						
						if(NPCs==null){NPCs = new ArrayList<String>();}
						
						p.sendMessage("§6======NPCs List======");
						
						for(String npc : NPCs){
							String shop = (String) config.get(npc + ".shop");
							p.sendMessage("§e- NPC §a'" + npc + "' §erelié au shop §b'" + shop + "'");
						}
						
					}
				}
				
				
				
			}
			
			
			
			
		}
		
		
		
		
		return false;
	}

}
