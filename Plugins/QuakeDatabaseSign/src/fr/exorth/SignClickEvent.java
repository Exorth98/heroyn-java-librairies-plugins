package fr.exorth;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SignClickEvent implements Listener {
	
	public FileConfiguration config = Main.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	List<Location> signlocations = (List<Location>)config.getList("arenasigns");
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		
		if(e.getClickedBlock() != null){
			
			if(e.getClickedBlock().getType()==Material.WALL_SIGN && e.getAction()== Action.RIGHT_CLICK_BLOCK){
				Sign sign = (Sign) e.getClickedBlock().getState();
				Location loc = sign.getLocation();
				
				if(signlocations.contains(loc)){
					String server = sign.getLine(1);
		              ByteArrayDataOutput out = ByteStreams.newDataOutput();
		              out.writeUTF("Connect");
		              out.writeUTF(server); // = nazwa serwera dla BungeeCord
		           e.getPlayer().sendPluginMessage(Main.getInstance(), "BungeeCord", out.toByteArray());
	             		            
				}								
			}
			
		}
		
	}

}
