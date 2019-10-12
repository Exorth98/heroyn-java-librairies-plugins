package fr.exorth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.SurveyMain;

public class SurveyCloseEvent implements Listener {

	@EventHandler
	public void InventoryClose(InventoryCloseEvent e){
		
		
		if(e.getInventory().getTitle().contains("§0Sondage | Question")){
			
	        new BukkitRunnable() {
	            
	            @Override
	            public void run() {
	            	
	            	if(e.getPlayer().getOpenInventory() == null){
	            		
	            		removeFromMaps((Player)e.getPlayer());
	            		
	            	}else if(!e.getPlayer().getOpenInventory().getTitle().contains("§0Sondage | Question")){
	            		
	            		removeFromMaps((Player)e.getPlayer());
	            	}
	            	
	            }
	            
	        }.runTaskLater(SurveyMain.getInstance(), 12);
			

			
		}
		
	}
	
	private void removeFromMaps(Player p) {
		
		if(SurveyMain.getInstance().inSurvey.containsKey(p)) {
			
			SurveyMain.getInstance().inSurvey.remove(p);
			SurveyMain.getInstance().inSurveyAwnsers.remove(p);
			
		}
		
		else /*SurveyMain.getInstance().inSurveyCalibration.containsKey(p)*/ {

			
			SurveyMain.getInstance().inSurveyCalibration.remove(p);
			SurveyMain.getInstance().inSurveyCalibrationTimes.remove(p);
		}

		SurveyMain.getInstance().DatesforTimes.remove(p);
		
		
	}
	
	
}
