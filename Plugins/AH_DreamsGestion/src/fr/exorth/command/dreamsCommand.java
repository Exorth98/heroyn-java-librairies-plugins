package fr.exorth.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.SqlConnect;

public class dreamsCommand implements CommandExecutor {

    private SqlConnect sql;
    
    public dreamsCommand(SqlConnect sql) {
        this.sql = sql;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("dreams")){
				
				if(args.length ==0){
	                double balance = sql.getBalance(p);
	                p.sendMessage("§6Vous avez : §3"+balance+" Dreams");
				}else{
					p.sendMessage("§cSynthaxe correcte : /dreams");
				}
				return true;
			}
		
		}else{
			sender.sendMessage("Commande reservée aux joueurs");
		}
		return false;
	}

}
