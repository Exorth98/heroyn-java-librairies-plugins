package fr.exorth.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SurveysMenu {
	
	Player p;

	public SurveysMenu(Player p) {
		this.p=p;
	}

	public void open() {
		
		Inventory Menu = Bukkit.createInventory(null, SurveyUtils.getMenuSize(),"§0Sondages");
		
		int pos=0;
		
		for(String Survey : SurveyUtils.okSurveysList()){
			
			ItemStack surv = new ItemStack(Material.PAPER,1);
			surv = ItemUtil.setName(surv, "§6" +Survey);
			surv = ItemUtil.setLores(surv, Arrays.asList("§aClic pour répondre"));
			
			Menu.setItem(pos, surv);
			
			pos +=2;
		}
		
		p.openInventory(Menu);
	}

}
