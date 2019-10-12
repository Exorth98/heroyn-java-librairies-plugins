package fr.exorth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Awnser;
import fr.exorth.surveys.OneShotSurvey;
import fr.exorth.surveys.Question;
import fr.exorth.surveys.Survey;
import fr.exorth.surveys.TemporarySurvey;

public class SurveyUtils {
	
	static FileConfiguration config = SurveyMain.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> surveys = (ArrayList<String>) config.get("Surveys");
	
	
	

	public static boolean isSurveyType(String type, String svName) {
		
		String realType = (String) config.get("Types." + svName);
				
		return type.equals(realType);
	}


	public static int getInventorySize(Survey sv, int QuN) {

		int AwnsersNumber = sv.getQuestions().get(QuN-1).getAwnsers().size();
		
		if(AwnsersNumber <=5){return 45;}
		else{return 54;}
	}
	
	public static boolean surveyExist(String surveyname) {
		
		if(surveys==null){surveys = new ArrayList<String>();}
		
		return surveys.contains(surveyname);
	}


	public static void list(Player p) {
		
		if(surveys==null){
			
			p.sendMessage("§eAucun sondage");
			return;
			
		}else if(surveys.size()==0){
			
			p.sendMessage("§eAucun sondage");
			return;
			
		}else{
			
			p.sendMessage("§a===== Liste des Sondages =====");
			
			for(String survey : surveys){
				p.sendMessage("§b- " + survey);
			}
			
		}
		
		
	}


	public static void removeSurvey(String name) {
		
		surveys.remove(name);
		config.set("Surveys", surveys);
		config.set("Types." + name,null);
		SurveyMain.getInstance().saveConfig();
		
	}


	public static void saveSurvey(String name,String type) {
		
		if(surveys==null){surveys = new ArrayList<String>();}
		
		if(!surveyExist(name)){
			surveys.add(name);
			config.set("Surveys", surveys);
			
			config.set("Types."+name, type);
			
			SurveyMain.getInstance().saveConfig();
		}
		
	}


	public static Survey getSurvey(String name) {
		
		String type = (String) config.get("Types."+name);
		
		Survey sv=null;
		
		switch (type){
		
		case "ONESHOT":
			OneShotSurvey OsSurvey = new OneShotSurvey().getSurvey(name);
			sv = (Survey) OsSurvey;
			break;
			
		case "TEMPORARY":
			TemporarySurvey TeSurvey = new TemporarySurvey().getSurvey(name);
			sv = (Survey) TeSurvey;
			break;
		
		}
		
		
		return sv;
	}


	public static boolean isDateFormatOk(String string) {
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		try {
			df.parse(string);
		} catch (ParseException e) {		
			return false;
		}
		
		return true;

	}


	public static boolean isNumeric(String string) {
		
		try{
			Double.parseDouble(string);
		} catch (NumberFormatException nfe){
			return false;
		}
		
		return true;
	}


	public static boolean isQuestionNumberOk(String name, int questionNumber) {
		
		Survey sv = getSurvey(name);
		int taille = sv.getQuestions().size();
		
		return (questionNumber>0 && questionNumber<=taille);
	}


	public static void listQuestions(String name, Player p) {
		Survey sv = getSurvey(name);
		
		p.sendMessage("§c");p.sendMessage("§c");
		p.sendMessage("§6===== Questions du sondage §e"+name+" §6=====");
		
		int i =1;
		
		for(Question qu : sv.getQuestions()){
			String quest = qu.getLabel();
			
			p.sendMessage("§9["+i+"] - §e"+quest);
			
			i++;
		}
		
		
	}


	public static void listAwnsers(String name, int quN, Player p) {
		
		Survey sv = getSurvey(name);
		
		p.sendMessage("§c");p.sendMessage("§c");
		p.sendMessage("§6===== Réponses question §e"+quN+" §6sondage §e"+name+" §6=====");
		
		Question qu = sv.getQuestions().get(quN-1);
		
		int i =1;
		for(Awnser aw : qu.getAwnsers()){
			String awns = aw.getLabel();
			
			p.sendMessage("§9["+i+"] - §e"+awns);
			
			i++;
		}
		
	}


	public static boolean isAwnserNumberOk(String name, int quN, int awN) {
		
		Survey sv = getSurvey(name);
		int taille = sv.getQuestions().get(quN-1).getAwnsers().size();
		
		
		return (awN>0 && awN<=taille);
	}


	public static double getScore(double time, double calibrationTime) {
		
		double dif = calibrationTime-time;
		
		
		if(dif<=0) {
			return 1;
		}
		else {
			

			double x = time/calibrationTime;			
			x = Math.pow(x, 4);
			
			return x;
			
			
		}
	}


	public static double getReliability(Player p) {
		
		
		
		return 0;
	}


	public static int getCalibrationTime(Survey sv, int QuN) {
		
		FileConfiguration cfg = SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Cal").getCustomConfig();
		
		List<Integer> CalTimes = cfg.getIntegerList("Question "+QuN);
		int total =0;
		int number =0;
		
		for(int CalTime : CalTimes) {
			
			total+=CalTime;
			number++;
		}
		
		return (total/number);
	}
	
	public static int getMenuSize() {
		
		int size=9;
		
		if(surveys != null){
			
			int n = getVisibleSurveysList().size();
			
			if(n>5 && n<=9){size=18;}
			else if(n>9 && n<=14){size=27;}
			else if(n>14 && n<=18){size=36;}
			else if(n>18 && n<=23){size=45;}
			else if(n>23 && n<=27){size=54;}
			
			
		}
	
		return size;
	}


	public static ArrayList<Survey> getVisibleSurveysList() {
		
		ArrayList<Survey> List = new ArrayList<>();
		

		
		for(String survey : surveys) {
			
			Survey sv = getSurvey(survey);
			if(sv.isVisible()) {
				Bukkit.broadcastMessage( sv.getName());
				List.add(sv);
			}
			
		}
		
		return List;
	}


	public static ArrayList<TemporarySurvey> getTemporarySurveys() {
		
		ArrayList<TemporarySurvey> survs = new ArrayList<>();
		
		for(String survey : surveys) {
			
			if(SurveyUtils.isSurveyType("TEMPORARY", survey)) {
				
				TemporarySurvey Tesv = new TemporarySurvey().getSurvey(survey);
				survs.add(Tesv);
			}
			
		}
		
		
		return survs;
	}

}
