package fr.exorth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class Floder {
	
	FileConfiguration cfg = HealthFloder.getInstance().getConfig();
	
	String pseudo;
	String sexe;
	int age;
	String gs;
	int poids;
	int phys;
	int taille;
	int myopie;
	int hyper;
	int ast;
	ArrayList<String> chirurgies;
	
	public Floder(String pseudo) {
		
		this.pseudo = pseudo;
		this.hydrate(pseudo);
		
	}

	@SuppressWarnings("unchecked")
	private void hydrate(String pseudo) {
		
		this.sexe = cfg.getString(pseudo + ".sexe");
		this.age = cfg.getInt(pseudo + ".age");
		this.gs = cfg.getString(pseudo + ".gs");
		this.poids = cfg.getInt(pseudo + ".poids");
		this.taille = cfg.getInt(pseudo + ".taille");
		this.myopie = cfg.getInt(pseudo + ".myopie");
		this.hyper = cfg.getInt(pseudo + ".hyper");
		this.ast = cfg.getInt(pseudo + ".ast");
		this.phys = cfg.getInt(pseudo + ".phys");
		this.chirurgies = (ArrayList<String>) cfg.get(pseudo + ".chirurgies");
		
	}
	
	public ItemStack getFolder() {
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bm = (BookMeta)book.getItemMeta();
		
		bm.setAuthor("§bHeals Up");
		bm.setTitle("§eDossier médical de '§a" + this.pseudo + "§e'");
		
		ArrayList<String> pages = new ArrayList<String>();
		
	    SimpleDateFormat ftd =  new SimpleDateFormat ("dd/MM/yyyy");
	    SimpleDateFormat fth =  new SimpleDateFormat ("HH'h'mm'm'ss's'");
	    
		Date now = new Date();
	    String Date = ftd.format(now);
	    String Heure = fth.format(now);
		
		String Coverpage =
		"§aHeals Up"
		+ "\n\n"
		+ "§1Dossier médical :\n§4"
		+ this.pseudo
		+ "\n\n\n\n"
		+ "§9Datant du:\n§3"
		+ Date
		+ "\n\n"
		+ "§9A: §3" + Heure;

		
		pages.add(Coverpage);
		
		
		String gene =    "§3Généralités :\n\n"
						 + "§2Sexe : §a" + this.sexe + "\n\n"
						 + "§2Age : §a" + this.age + "\n\n"
						 + "§2Taille : §a" + this.taille + " cm\n\n"
					     + "§2Poids : §a" + this.poids + "kg\n\n";
		
		
		String gs = "§3Groupe Sanguin :\n\n"
			     + "§2Groupe : §c" + this.gs + "\n\n"
			     + "§2Donneur : §cNon\n\n";
		
		String vue = "§3 Vue :\n\n"
				 + "§2Myope : §a\n" 
				 + getPercentage(this.myopie) + "\n\n"
			     + "§2Hypermetrope : §a\n" 
				 + getPercentage(this.hyper) + "\n\n"
			     + "§2Astigmate : §a\n" 
				 + getPercentage(this.ast);
		
		String actphys = "§3 Activité physique :\n\n"
				 + getPercentage(this.phys);
		
		
		String chirurgies = "§3Chirurgies :\n\n";
		for(int i = 0; i< this.chirurgies.size(); i++) {
			
			chirurgies+= "§a- " + this.chirurgies.get(i) + "\n\n";
		}
		
		
		pages.add(gene);
		pages.add(gs);
		pages.add(vue);		
		pages.add(actphys);	
		pages.add(chirurgies);
		
		bm.setPages(pages);		
		book.setItemMeta(bm);			
		return book;
		
		
	}
	
	
	private static String getPercentage(int percent) {

		String Percentage = "";
		
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
		
		return Percentage;
		
	}
	
	

}
