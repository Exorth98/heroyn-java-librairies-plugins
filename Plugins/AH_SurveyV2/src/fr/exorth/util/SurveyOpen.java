package fr.exorth.util;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Awnser;
import fr.exorth.surveys.Survey;

public class SurveyOpen {

	
	public static void inCalibrationMode(Player p, Survey sv) {
		
		SurveyMain.getInstance().inSurveyCalibration.put(p, sv);
		SurveyMain.getInstance().inSurveyCalibrationTimes.put(p, new ArrayList<String>());
		
		OpenQuestion(p,sv,1);
		
	}

	public static void inClassicMode(Player p, Survey sv) {
		
		SurveyMain.getInstance().inSurvey.put(p, sv);
		SurveyMain.getInstance().inSurveyAwnsers.put(p, new ArrayList<String>());
		
		OpenQuestion(p,sv,1);
		
	}


	public static void OpenQuestion(Player p, Survey sv, int QuN) {
		
		
		Inventory Question = Bukkit.createInventory(null,SurveyUtils.getInventorySize(sv,QuN),"§0Sondage | Question "+QuN+"/"+sv.getQuestions().size());
		
		for(int i =0; i<9; i++){
			Question.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		for(int i = Question.getSize()-1; i>Question.getSize()-10; i--){
			Question.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}
		
		ItemStack quest = new ItemStack(Material.EMERALD,1);
		quest = SurveyItemUtil.setName(quest, "§b" + sv.getQuestions().get(QuN-1).getLabel());
		
		Question.setItem(4, quest);
		
		int i = 18;
		for(Awnser awnser : sv.getQuestions().get(QuN-1).getAwnsers()){
			
			ItemStack awns = new ItemStack(Material.PAPER,1);
			awns = SurveyItemUtil.setName(awns, "§a" + awnser.getLabel());
			
			Question.setItem(i, awns);
			
			i+=2;
		}
		
		p.openInventory(Question);
		SurveyMain.getInstance().DatesforTimes.put(p, new Date());
		
		
	}

}
