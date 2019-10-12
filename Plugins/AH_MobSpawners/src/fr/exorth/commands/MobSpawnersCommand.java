package fr.exorth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.util.MSPUtils;

public class MobSpawnersCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("msp")){
			
			if(args.length==0){
				
				s.sendMessage("§6===== MobSpawners commands =====");
				s.sendMessage("§e/msp givepickaxe <joueur>");
				s.sendMessage("§6================================");
			}else{
				
				if(args[0].equalsIgnoreCase("givepickaxe")){
					if(args.length==2){
						
						String joueur = args[1];
						
						if(Bukkit.getPlayer(joueur) != null){
							
							Player p = Bukkit.getPlayer(joueur);
							
							if(!invFull(p)){
								
								ItemStack pickAxe = MSPUtils.getPickAxe();
								p.getInventory().addItem(pickAxe);
								
							}else{
								s.sendMessage("§cCe joueur n'as pas de place dans son inventaire");
							}
							
						}else{
							s.sendMessage("§cCe joueur n'est pas en ligne");
						}
						
					}else{
						s.sendMessage("§e/msp givepickaxe <joueur>");
					}
				}
				
				
			}
			
		}
		
		return false;
	}
	
	public boolean invFull(Player p) {          
		return p.getInventory().firstEmpty() == -1;          
	}

}
