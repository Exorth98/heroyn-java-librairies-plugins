package fr.exorth.util;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.Quake;

public class QuakeReload extends BukkitRunnable{
	
	public static FileConfiguration config = Quake.getInstance().getConfig();
	
	private Player player;
	private int reloadtime;
	private double count;
 
    public QuakeReload(Player p, Material mat) {
    	player = p;
    	String path = "reload.wood";
    	
    	switch (mat) {
		case WOOD_HOE:
			path="reload.wood";
			break;
		case STONE_HOE:
			path="reload.stone";
			break;
		case IRON_HOE:
			path="reload.iron";
			break;
		case DIAMOND_HOE:
			path="reload.diamond";
			break;
		default:
			break;
		}
    	count = (config.getDouble(path)*4)-1;
    	reloadtime=  (int) count+1;
    	
	}

	public void run() {
		if(count==0){
		player.setExp(1F);
		}
        if(player.getExp() < 1F) {
            player.setExp(player.getExp() + (1F/reloadtime)); 
            count--;
        } else {          
            Quake.getInstance().ReloadingPlayers.remove(player);
            Quake.getInstance().getServer().getWorld("world").playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
            this.cancel();
        }
        
        
        
        
        
    }

}
