package fr.exorth;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class SignRefreshRunnable extends BukkitRunnable{
	
	private SqlConnection sql;
	
	public FileConfiguration config = Main.getInstance().getConfig();

	public SignRefreshRunnable(SqlConnection sql) {
		this.sql = sql;
	}
	



	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		List<String> arenas = (List<String>)config.getList("arenas");
		List<Location> signlocations = (List<Location>)config.getList("arenasigns");
		
		if(signlocations != null){
			
			for( Location loc : signlocations){
				
				
				if(loc.getBlock().getType() == Material.WALL_SIGN){
					
					
					Sign sign = (Sign) loc.getBlock().getState();
					
					for( String name : arenas){
						
						if(sign.getLine(1).equals(name)){
							
							
							sign.setLine(0,"§9[Quake]");
							
							sign.setLine(2, "§b" + sql.getplayers(name) + "/" + sql.getmaxplayers(name));
							sign.setLine(3,stateModifier(sql.getstate(name)));
							sign.update();
							
						}
					}
				}
			}
		}		
	}




	private String stateModifier(String state) {
		
		switch(state){
		
		case "WAIT":
			return "§a[Rejoindre]";
		case "GAME":
			return "§6[En jeu]";
		case "FINISH":
			return "§6[Fin de partie]";
		case "RESTARTING":
			return "§c[Arrêt...]";
		default:
			return "§4[Non Reliée]";
		}
		
	}

	
	

}
