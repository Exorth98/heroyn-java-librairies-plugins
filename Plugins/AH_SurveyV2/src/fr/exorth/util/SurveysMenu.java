package fr.exorth.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.surveys.Survey;

public class SurveysMenu {

	public void open(Player p) {
		
		
		Inventory Menu = Bukkit.createInventory(null, SurveyUtils.getMenuSize(),"§0Sondages");
		
		int pos=0;
		
		for(Survey sv : SurveyUtils.getVisibleSurveysList()){
			
			ItemStack surv = new ItemStack(Material.PAPER,1);
			surv = SurveyItemUtil.setName(surv, "§6" +sv.getDisplayName());
			surv = SurveyItemUtil.setLores(surv, Arrays.asList("§aClic pour répondre au sondage"));
			
			Menu.setItem(pos, surv);
			
			pos +=2;
		}
		
		p.openInventory(Menu);
	}

}
