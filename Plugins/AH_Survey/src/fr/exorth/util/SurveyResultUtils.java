package fr.exorth.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import fr.exorth.Survey;

public class SurveyResultUtils {
	
	public static FileConfiguration config = Survey.getInstance().getConfig();

	
	public static ItemStack getResultBook(String nom) {
		
		
		
		
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bm = (BookMeta)book.getItemMeta();
		
		bm.setAuthor("§bAngelicHeart");
		bm.setTitle("§eResultats sondage '§a" + nom + "§e'");
		
		ArrayList<String> pages = new ArrayList<String>();
		
	    SimpleDateFormat ftd =  new SimpleDateFormat ("dd/MM/yyyy");
	    SimpleDateFormat fth =  new SimpleDateFormat ("HH'h'mm'm'ss's'");
	    
		Date now = new Date();
	    String Date = ftd.format(now);
	    String Heure = fth.format(now);
		
		String Coverpage =
		"§1Résultats pour :\n§4"
		+ nom
		+ "\n\n\n\n"
		+ "§9Datant du:\n§3"
		+ Date
		+ "\n\n"
		+ "§9A: §3" + Heure
		+ "\n\n\n"
		+ "§2Nombre\n"
		+ "de réponses: §c" + SurveyResultUtils.getTotal(nom, 0);
		
		pages.add(Coverpage);
		
		
		for(int i = 0;i< GetQuestionNumber(nom);i++){
			
			ConfigurationSection questions = config.getConfigurationSection(nom);
			
			String questionLine = questions.getKeys(false).toArray()[i].toString();
			
			String FirstPage = "§2Question n°" + (i+1) + " :\n"
							 + "§c'" + questionLine + "'\n§r"
						     + "\n";
			
			for (int j=0;j<getAwnsersNumber(nom, i+1) && j<3;j++){
				
				String awns = getAwnsName(nom, i, j);
				
				FirstPage += "§9- " + awns + " :\n";
				FirstPage += getPercentage(nom,i,j) + "\n\n";
				
			}		
			pages.add(FirstPage);
			
			String Page2 = "";
			
			for (int j=3;j<getAwnsersNumber(nom, i+1) && j<6;j++){
				
				String awns = getAwnsName(nom, i, j);
				
				Page2 += "§9- " + awns + " :\n";
				Page2 += getPercentage(nom,i,j) + "\n\n";
				
			}
			if(Page2 != "") {pages.add(Page2);}
			
			String Page3 = "";
			
			for (int j=6;j<getAwnsersNumber(nom, i+1) && j<9;j++){
				
				String awns = getAwnsName(nom, i, j);
				
				Page3 += "§9- " + awns + " :\n";
				Page3 += getPercentage(nom,i,j) + "\n\n";
				
			}
			if(Page3 != "") {pages.add(Page3);}
			
		}
		
		
		
		bm.setPages(pages);		
		book.setItemMeta(bm);			
		return book;
	}
	
	
	
	
	
	private static String getPercentage(String name, int questionind, int awnsind) {

		String Percentage = "";
		
		double max = getTotal(name,questionind);
		double score = getAwnsScore(name, questionind, awnsind);
		
		int percent = (int) Math.round((score*100)/max);
		
		int okBar = (int) (percent * 0.4);
		int NonOkBar = 40-okBar;
		
		Percentage += "§3";
		for(int i =0; i<okBar;i++){
			Percentage += "|";
		}
		Percentage += "§7";
		for(int i =0; i<NonOkBar;i++){
			Percentage+= "|";
		}
		
		Percentage += " §c" + percent + "%";
		
		return Percentage;
		
	}





	public static int getTotal(String name, int questionind){
		
		int tot=0;
		
		ConfigurationSection questions = config.getConfigurationSection(name);
		
		String questionLine = questions.getKeys(false).toArray()[questionind].toString();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> awnsers = (ArrayList<String>) config.getList(name + "." + questionLine);
		
		for(String awns : awnsers){
			int score = Integer.parseInt(awns.split("/")[1]);
			tot += score;
		}
		
		
		return tot;
	}
	
	public static int getAwnsScore(String name, int questionind, int awnsind){
		
		ConfigurationSection questions = config.getConfigurationSection(name);
		
		String questionLine = questions.getKeys(false).toArray()[questionind].toString();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> awnsers = (ArrayList<String>) config.getList(name + "." + questionLine);
		
		int Score = Integer.parseInt(awnsers.get(awnsind).split("/")[1]);
		
		return Score;
	}
	
	public static String getAwnsName(String name, int questionind, int awnsind){
		
		ConfigurationSection questions = config.getConfigurationSection(name);
		
		String questionLine = questions.getKeys(false).toArray()[questionind].toString();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> awnsers = (ArrayList<String>) config.getList(name + "." + questionLine);
		
		String awnsname = awnsers.get(awnsind).split("/")[0];
		
		return awnsname;
	}
	
	
	public static int getAwnsersNumber(String name, int question) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		String questionline = questions.getKeys(false).toArray()[question-1].toString();
		
		int AwnsersNumber = config.getList(name + "." + questionline).size();
		
		return AwnsersNumber;
	}
	
	public static int GetQuestionNumber(String name) {

		ConfigurationSection questions = config.getConfigurationSection(name);
		return questions.getKeys(false).size();
		
	}
	
	
}
