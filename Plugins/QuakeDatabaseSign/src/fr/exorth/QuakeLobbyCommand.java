package fr.exorth;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class QuakeLobbyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
				
				if(cmd.getName().equalsIgnoreCase("lobby")){
					
		              ByteArrayDataOutput out = ByteStreams.newDataOutput();
		              out.writeUTF("Connect");
		              out.writeUTF("lobby"); // = nazwa serwera dla BungeeCord
		            p.sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());

				}
		}else{
			sender.sendMessage("Commande reservée aux joueurs");
		}
		
		return false;
	}

}
