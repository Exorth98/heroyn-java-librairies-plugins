package fr.exorth.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SurveyL {
	
	String name;
	Player p;

	public SurveyL(String nom, Player p) {
		
		this.name = nom;
		this.p = p;
		
	}

	public void OpenQuestion(int question) {
		
		Inventory Question = Bukkit.createInventory(null,SurveyUtils.getInventorySize(name,question),name + " | Question " + question);
		
		for(int i =0; i<9; i++){
			Question.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		for(int i = Question.getSize()-1; i>Question.getSize()-10; i--){
			Question.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		
		ItemStack quest = new ItemStack(Material.EMERALD,1);
		quest = ItemUtil.setName(quest, "§b" + SurveyUtils.GetQuestionName(name,question));
		
		Question.setItem(4, quest);
		
		ArrayList<String> awnsers = SurveyUtils.getAwnsers(name, question);
		
		int i = 18;
		for(String awnser : awnsers){
			
			ItemStack awns = new ItemStack(Material.PAPER,1);
			quest = ItemUtil.setName(awns, "§a" + awnser.split("/")[0]);
			
			Question.setItem(i, awns);
			
			i+=2;
		}
		
		p.openInventory(Question);
	}

}
