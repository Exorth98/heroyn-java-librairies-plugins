package fr.exorth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.exorth.surveys.Survey;
import fr.exorth.util.SurveyOpen;
import fr.exorth.util.SurveyUtils;

public class SurveysMenuInteract implements Listener {

	@EventHandler
	public void onMenuClick(InventoryClickEvent e) {
		
		if(e.getSlotType() != SlotType.OUTSIDE) {
			
			Player p = (Player) e.getWhoClicked();
			
			if(e.getClickedInventory().getName().contains("§0Sondages")) {
				
				int SurveyNumber = e.getSlot()/2;
				Survey sv = SurveyUtils.getVisibleSurveysList().get(SurveyNumber);
				
				SurveyOpen.inClassicMode(p, sv);
				
				
			}			
		}		
	}
		

	
}
