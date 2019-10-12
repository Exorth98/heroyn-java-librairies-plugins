package fr.exorth.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.FactionTuto;
import fr.exorth.runnable.PlayRunnable;

public class FTUtils {
	
	static FileConfiguration config = FactionTuto.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> tutos = (ArrayList<String>) config.get("Tutos");
	

	public static void addPoint(String tutoname, String pointname, Location loc) {
		
		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		if(points==null){points = new ArrayList<String>();}
		
		points.add(pointname);		
		Tutocfg.set("Points", points);
		
		Tutocfg.set("Locations." + pointname, loc);
		
		Tutocfg.set("Messages." + pointname, getDefaultMessage(pointname));
		
		FactionTuto.getInstance().getConfigManager(tutoname).saveCustomConfig();
		
		
	}


	private static String[] getDefaultMessage(String name) {
		
		String[] msg = {
				"§6==============================================",
				"§c",
				"§eCeci est le message pour le point §a" + name ,
				"§edu Tuto",
				"§c",
				"§eIl est à configurer dans le fichier config",
				"§c"};
		
		return msg;
	}


	public static boolean pointExist(String tutoname, String pointname) {
		
		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig();
		
		if(Tutocfg==null){Bukkit.broadcastMessage("null");}
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		if(points==null){points = new ArrayList<String>();}
		
		if(points.contains(pointname)){
			
			return true;
			
		}else{
			return false;
		}

	}


	public static void removePoint(String tutoname, String pointname) {

		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig(); 
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		points.remove(pointname);
		Tutocfg.set("Points", points);
		
		Tutocfg.set("Locations." + pointname, null);
		
		Tutocfg.set("Messages." + pointname, null);
		
		FactionTuto.getInstance().getConfigManager(tutoname).saveCustomConfig();
	}


	public static void enterTuto(Player p) {
		
		p.setGameMode(GameMode.SPECTATOR);
		FactionTuto.getInstance().inTuto.put(p, p.getLocation());
		
	}
	
	public static void exitTuto(Player p) {
		
		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(FactionTuto.getInstance().inTuto.get(p));
		FactionTuto.getInstance().inTuto.remove(p);
		
	}
	

	public static boolean tutoIsPlayable(String tutoname, CommandSender s, Player p) {
		
		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		if(points==null){points = new ArrayList<String>();}
		
		if(points.size()==0){
			s.sendMessage("§cAucun point configuré dans le tuto");
			return false;
		}
		else if(FactionTuto.getInstance().inTuto.containsKey(p)){
			s.sendMessage("§cCe joueur est déja en train d'effectuer le tuto");
			return false;
		}else{
			return true;
		}
	}

	public static void cancelTuto(Player p) {
		
		FactionTuto.getInstance().requestCancel.add(p);
		p.sendMessage("§cVous avez demandé à annuler le tutoriel..");
		
	}
	

	public static void playTuto(String tutoname, Player p) {
		
		ArrayList<Integer> times = getTimes(tutoname);
		
		new PlayRunnable(tutoname,p,times).runTaskTimer(FactionTuto.getInstance(), 0, 1);
	
		
	}


	private static ArrayList<Integer> getTimes(String tutoname) {
		
		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig(); 
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		ArrayList<Integer> times = new ArrayList<>();
		
		times.add(0);
		
		int PointAmount = points.size();
		
		for(int i=0;i<PointAmount;i++){
			
			double secondTime=0;
			
			for(int j=i;j>=0;j--){
				
				secondTime += getReadTime(tutoname, j);
			}
			
			int time = (int) (secondTime*20)*2;
			
			times.add(time);
			
		}
	
		return times;
	}


	public static double getReadTime(String tutoname, int pointInd) {
		
		FileConfiguration Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> points = (ArrayList<String>) Tutocfg.get("Points");
		
		String point= points.get(pointInd);
		@SuppressWarnings("unchecked")
		List <String> msg = (ArrayList<String>) Tutocfg.get("Messages." + point);
		if(msg==null){msg= Arrays.asList("§cErreur de chargement du message");}
		
		double time = 0.0;
		
		String caracters = ""; 
		
		for(String m : msg){
			caracters += (m.replace(" ","").replace("=", ""));
		}
		
		time = (caracters.length()/28)+2;
		
		
		return time;
	}


	public static void DisplayMessage(Player p, ArrayList<String> msg) {
		
		for(String m : msg){
			p.sendMessage(m);
		}
		
		
	}


	public static boolean TutoExist(String tutoName) {
		
		if(tutos==null){tutos = new ArrayList<String>();}
		
		return tutos.contains(tutoName);
	}


	public static void createTuto(String tutoName) {
		
		if(tutos==null){tutos = new ArrayList<String>();}
		tutos.add(tutoName);
		config.set("Tutos", tutos);
		FactionTuto.getInstance().saveConfig();
		
		FactionTuto.getInstance().loadCustomConfigs();
		
	}


	public static void deleteTuto(String tutoName) {

		tutos.remove(tutoName);
		config.set("Tutos", tutos);
		FactionTuto.getInstance().saveConfig();
		
		//delete AH_FactionTuto/Tutos/tutoName.yml
				
	}


}
