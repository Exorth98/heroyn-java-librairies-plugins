package fr.exorth.util;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Survey;

public class SurveyUtils {
	
	public static FileConfiguration config = Survey.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> surveys = (ArrayList<String>) config.getList("surveys");
	
	public static void DisplaySurveys(CommandSender s){
		
		if(surveys != null){
			
			s.sendMessage("§6=== Survey List : ===");
			s.sendMessage("§c ");
			
			for(String survey : surveys ){
				
				if(isSurveyOk(survey)){
					
					s.sendMessage("§e- " + survey);
				}
				else{
					
					s.sendMessage("§e- §c[!] §e" + survey);
				}
			}
		}else{
			s.sendMessage("§6Aucun sondage à afficher");
		}

		
	}

	public static boolean SurveyExist(String nom) {
		
		if(surveys==null){return false;}
		
		else{return surveys.contains(nom);}
		
	}
	
	public static boolean isQuestionNumberOk(String SurveyName, int number) {
		
		ConfigurationSection survey = config.getConfigurationSection(SurveyName);
		
		survey.getKeys(false).toString();
		
		return true;
	}
	
	public static boolean isSurveyOk(String Name){
		
		ConfigurationSection questions = config.getConfigurationSection(Name);
		
		if(questions != null){
		
			for(String question : questions.getKeys(false)){
				
				
				if(question.contains("[X]")){return false;}
				
			}
			
			
		}else{
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<String> okSurveysList(){
		
		ArrayList<String> list= new ArrayList<>();
		
		for(String survey : surveys){
			if (isSurveyOk(survey)){
				list.add(survey);
			}
		}
		
		return list;
	}

	public static int getInventorySize(String name, int question) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		String questionline = questions.getKeys(false).toArray()[question-1].toString();

		int AwnsersNumber = config.getList(name + "." + questionline).size();
		
		if(AwnsersNumber <=5){return 45;}
		else{return 54;}
	}
	
	public static int getAwnsersNumber(String name, int question) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		String questionline = questions.getKeys(false).toArray()[question-1].toString();
		
		int AwnsersNumber = config.getList(name + "." + questionline).size();
		
		return AwnsersNumber;
	}

	public static String GetQuestionName(String name, int question) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		String questionline = questions.getKeys(false).toArray()[question-1].toString();
		
		return questionline;
		
	}

	public static int GetQuestionNumber(String name) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		return questions.getKeys(false).size();
		
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getAwnsers(String name, int question) {
		
		ConfigurationSection questions = config.getConfigurationSection(name);
		String questionline = questions.getKeys(false).toArray()[question-1].toString();
		
		return (ArrayList<String>) config.getList(name + "." + questionline);
		
	}

	public static void endSurvey(Player p) {
		
		ArrayList<String> result = Survey.getInstance().SurveyResult.get(p);
		String name = result.get(0);
		
		
		ConfigurationSection questions = config.getConfigurationSection(name);
		
		for(int i=1; i<result.size();i++){
			

			
			int questionNumber = Integer.parseInt(result.get(i).split(":")[0]);
			int awnserNumber = Integer.parseInt(result.get(i).split(":")[1]);
			
			String questionLine = questions.getKeys(false).toArray()[questionNumber-1].toString();
			
			@SuppressWarnings("unchecked")
			ArrayList<String> awns = (ArrayList<String>) config.getList(name + "." + questionLine);
			
			
			String awnsLine = awns.get(awnserNumber-1);
			
			
			String awnsName = awnsLine.split("/")[0];
			int actualAwnsScore = Integer.parseInt(awnsLine.split("/")[1]);
			int newAwnsScore = actualAwnsScore+1;
			
			String newAwnsLine = awnsName + "/" + newAwnsScore;
			
			awns.set(awnserNumber-1, newAwnsLine);
			
			config.set(name + "." + questionLine, awns);
	
		}
		
		Survey.getInstance().saveConfig();
		
		
	}

	public static int getMenuSize() {
		
		int size=9;
		
		if(surveys != null){
			
			int n = okSurveysList().size();
			
			if(n>5 && n<=9){size=18;}
			else if(n>9 && n<=14){size=27;}
			else if(n>14 && n<=18){size=36;}
			else if(n>18 && n<=23){size=45;}
			else if(n>23 && n<=27){size=54;}
			
			
		}
	
		return size;
	}


	

}
