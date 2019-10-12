package fr.exorth;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Sign;

import fr.exorth.SqlConnection;

public class SignEvent implements Listener {
	
	private SqlConnection sql;
	
	public FileConfiguration config = Main.getInstance().getConfig();

	public SignEvent(SqlConnection sql) {
		this.sql = sql;
	}
	
	@EventHandler
	public void onSignPlace(SignChangeEvent e){		
		
			if(e.getLine(0).equalsIgnoreCase("[Quake]")){
			//	if(e.getLine(1)!=" " && e.getLine(2)!=" " && e.getLine(3)=="ok"){
					
					String arenaname = e.getLine(1);
					int joueursmax = Integer.parseInt(e.getLine(2));
					
					@SuppressWarnings("unchecked")
					List<String> arenas = (List<String>)config.getList("arenas");
					@SuppressWarnings("unchecked")
					List<Location> signlocations = (List<Location>)config.getList("arenasigns");
					
					if( (arenas != null && !arenas.contains(arenaname) ) || arenas == null){
						
						sql.createArena(arenaname,joueursmax);
						
						e.setLine(0, "§9[Quake]");
						
						if(signlocations != null){
							signlocations.add(e.getBlock().getLocation());
							config.set("arenasigns", signlocations);
						}else{
							ArrayList<Location> signlocation2 = new ArrayList<>();
							signlocation2.add(e.getBlock().getLocation());
							config.set("arenasigns", signlocation2);
						}
						
						if(arenas != null){
							arenas.add(arenaname);
							config.set("arenas", arenas);
						}else{
							ArrayList<String> arenas2 = new ArrayList<>();
							arenas2.add(arenaname);
							config.set("arenas", arenas2);
						}
						e.getPlayer().sendMessage("§a" + arenaname + " a étée ajoutée aux configs à la base de donnée.");
						Main.getInstance().saveConfig();
					}else{
						e.getPlayer().sendMessage("§cNom d'arène déjà utilisé");
					}
				
					
				//}else{
				//	e.getPlayer().sendMessage("§cFormat invalide");
				//}
			}		
	}
	
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		
		
		List<Location> signlocations = (List<Location>)config.getList("arenasigns");
		List<String> arenas = (List<String>)config.getList("arenas");
		
		for (Location loc : signlocations){
			  Sign sign = (Sign) loc.getBlock().getState().getData();		  
			  Block attachedBlock = loc.getBlock().getRelative(sign.getAttachedFace());
			  Location locb = attachedBlock.getLocation();
			  
			  org.bukkit.block.Sign sign2 = (org.bukkit.block.Sign) loc.getBlock().getState();
			  String arenaname = sign2.getLine(1);
			  
			  if(e.getBlock().getLocation().equals(loc) || e.getBlock().getLocation().equals(locb)){

					  if(e.getPlayer().getItemInHand().getType() != Material.BARRIER){
						  e.setCancelled(true);
					  }else{
							signlocations.remove(e.getBlock().getLocation());
							config.set("arenasigns", signlocations);
							arenas.remove(arenaname);
							config.set("arenas", arenas);
							Main.getInstance().saveConfig();
							
							sql.delete(arenaname);
							
							e.getPlayer().sendMessage("§c" + arenaname + " a étée supprimée des configs et de la base de donnée.");
					  }
					  
					 
			  }

		}

	}

}
