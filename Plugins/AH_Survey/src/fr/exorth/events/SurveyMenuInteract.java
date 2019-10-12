package fr.exorth.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.Survey;
import fr.exorth.util.SurveyL;
import fr.exorth.util.SurveyUtils;

public class SurveyMenuInteract implements Listener {
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		
		if(e.getSlotType() != SlotType.OUTSIDE){
			
			if(e.getClickedInventory().getName().contains("Question")){
				
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.PAPER){
					
					Player p = (Player) e.getWhoClicked();
					
					int questionNumber = Integer.parseInt(e.getClickedInventory().getName().split("Question ")[1]);
					int awnserNumber = ((e.getSlot()-18)/2)+1;
					
					Survey.getInstance().SurveyResult.get(p).add(questionNumber + ":" + awnserNumber);
					
					String SurveyName = e.getClickedInventory().getName().split(" | ")[0];					
					
					if(questionNumber == SurveyUtils.GetQuestionNumber(SurveyName)){
						p.closeInventory();
						p.sendMessage("§6Merci d'avoir répondu !");
						Bukkit.broadcastMessage("§b" + p.getName() + " §avient de répondre au sondage §b" + SurveyName + "§a !" );
						SurveyUtils.endSurvey(p);
					}else{
						new SurveyL(SurveyName,p).OpenQuestion(questionNumber+1);
					}
					
					
				}
				
			}
			
			
			if(e.getClickedInventory().getName().contains("Sondages")){
				
				e.setCancelled(true);
				
				if(e.getCurrentItem().getType() == Material.PAPER){
					
					Player p = (Player) e.getWhoClicked();				
					String SurveyName = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
					
					p.performCommand("survey open " + SurveyName);					
				}
				
			}
			
			
			
			
		}
		
		
	}
	
	@EventHandler
	public void InventoryClose(InventoryCloseEvent e){
		
		if(e.getInventory().getName().contains("Question")){
			
	        new BukkitRunnable() {
	            
	            @Override
	            public void run() {
	            	
	            	if(e.getPlayer().getOpenInventory() == null){
		            		
		        			Survey.getInstance().SurveyResult.remove(e.getPlayer());
		        			Bukkit.broadcastMessage("§cSORTIT");
	            		
	            	}else if(!e.getPlayer().getOpenInventory().getTitle().contains("Question")){
	            		
	        			Survey.getInstance().SurveyResult.remove(e.getPlayer());
	            	}
	            	
	            }
	            
	        }.runTaskLater(Survey.getInstance(), 12);
			

			
		}
		
	}

}
