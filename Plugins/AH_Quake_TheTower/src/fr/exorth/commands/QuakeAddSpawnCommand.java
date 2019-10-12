package fr.exorth.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Quake;

public class QuakeAddSpawnCommand implements CommandExecutor {
	
	public FileConfiguration config = Quake.getInstance().getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(p.isOp()){
				
				if(cmd.getName().equalsIgnoreCase("addspawn")){
					
					if(args.length ==0){
						@SuppressWarnings("unchecked")
						List<Location> spawnList = (List<Location>)config.getList("arena.spawns");
						
						if(spawnList != null){
							spawnList.add(p.getLocation());
							config.set("arena.spawns", spawnList);
						}else{
							ArrayList<Location> SpawnList2 = new ArrayList<>();
							SpawnList2.add(p.getLocation());
							config.set("arena.spawns", SpawnList2);
						}
						
						
						Quake.getInstance().saveConfig();
						
						p.sendMessage("§6Spawn ajouté");
						
						return true;
					}else{
						p.sendMessage("§cLa synthaxe correcte est : /addspawn");
					}

				}
				
			}else{
				p.sendMessage("§cTu n'as pas la permission");
			}
		}else{
			sender.sendMessage("Commande reservée aux joueurs");
		}
		
		return false;
	}

}
