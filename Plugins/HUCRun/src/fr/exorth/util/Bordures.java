package fr.exorth.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class Bordures {
	
	public Bordures() {
		
	}
	
	//set les bordures par defaut
	public void decreaseBorder(){
		
		for(World ws : Bukkit.getWorlds()){
			WorldBorder wb = ws.getWorldBorder();
			wb.setSize(wb.getSize()-1.0);
		}
		
		
	}
	
	public static double getBorder(){
		WorldBorder wb = Bukkit.getWorld("world").getWorldBorder();
		return wb.getSize();
	}
	
	public void setBorder(double taille){
		
		for(World ws : Bukkit.getWorlds()){
			WorldBorder wb = ws.getWorldBorder();
			wb.setCenter(0, 0);
			wb.setSize(taille);
			
			wb.setWarningDistance(10);
		}
		
		
	}
	

}
