package fr.exorth.events;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.exorth.Quake;
import fr.exorth.game.QuakeState;
import fr.exorth.util.QuakeHoeMenu;

public class QuakeInteract implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		if(!e.getPlayer().isOp()){
			e.setCancelled(true);
		}
		
		Player p = e.getPlayer();
		
		if(QuakeState.isState(QuakeState.WAIT)){
			
			Material matp = Quake.getInstance().hoes.get(p);
			
			if(e.getMaterial() == Material.WOOD_DOOR && (e.getAction()== Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
				p.performCommand("lobby");
			}
			
			if(e.getMaterial() == matp && (e.getAction()== Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)){
				QuakeHoeMenu.open(p);
			}
			
		}
		
		
		

	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		
		if(e.getClickedBlock().getType()==Material.WALL_SIGN && e.getAction()== Action.RIGHT_CLICK_BLOCK){
			
			if (e.getClickedBlock().getType()== Material.WALL_SIGN){
				Sign sign = (Sign) e.getClickedBlock().getState();

				e.getPlayer().sendMessage("ok");
					String server = sign.getLine(1);
		              ByteArrayDataOutput out = ByteStreams.newDataOutput();
		              out.writeUTF("Connect");
		              out.writeUTF(server); // = nazwa serwera dla BungeeCord
		              e.getPlayer().sendPluginMessage(Quake.getInstance(), "BungeeCord", out.toByteArray());
		              
			}


             
	            
			
			
			
		}
		
	}

}
