package fr.exorth.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.exorth.Quake;
import fr.exorth.game.QuakeGame;

public class CustomScoreboardManager implements ScoreboardManager{
	
	public Player player;
	public Scoreboard scoreboard;
	public Objective objective;
	public static HashMap<Player, CustomScoreboardManager> sb = new HashMap<>();
	
	private String name = "quake";
	
	public CustomScoreboardManager(Player p){

		
		
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.player = p;
		
		//SI IL EST DANS LA MAP ON NE FAIT RIEN
		if(sb.containsKey(p)) return;
		
		//SINON SI IL NA PAS DE SCOREBOARD ON LUI EN CREER MAIS CUSTOMISER
		sb.put(p, this);
		
		Random r = new Random();
		
		this.name = "sb."+r.nextInt(1000000);
		
		objective = scoreboard.registerNewObjective(name, "dummy");
		objective = scoreboard.getObjective(name);
		objective.setDisplayName("§6Kills");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
	}

	
	
	//ON MET UN JOUR UNIQUEMENT LES ELEMENTS DYNAMIQUE
	public void refresh(){
		
		//Refresh kills
		
		
		
		  // Ajout des entrées de la map à une liste
		  final List<Entry<Player, Integer>> entries = new ArrayList<Entry<Player, Integer>>(Quake.getInstance().kills.entrySet());
		 
		  // Tri de la liste sur la valeur de l'entrée
		  Collections.sort(entries, new Comparator<Entry<Player, Integer>>() {
		    public int compare(final Entry<Player, Integer> e1, final Entry<Player, Integer> e2) {
		      return e1.getValue().compareTo(e2.getValue());
		    }
		  });
		
		int j = 4;
		for(final Entry<Player, Integer> entry : entries){
			String pname = entry.getKey().getDisplayName();
			Integer kills = entry.getValue();
			String pref = "§f";
			
			if(kills>9){pref="§e";}
			if(kills>19){pref="§b";}
			if(kills>29){pref="§d";}
			if(kills>39){pref="§c";}
			if(kills>49){pref="§a§l";}							
			
			for(String ligne : scoreboard.getEntries()){
				if(ligne.contains(pname)){
					scoreboard.resetScores(ligne);
					
					String New = pref + pname + ": " + kills;
					
					if(player == entry.getKey()){
						New = pref + "> " + New + " <";
					}
					
					objective.getScore(New).setScore(j);
				}
			}
			j++;
		}
		 
		  // Affichage du résultat
		  //for (final Entry<String, Integer> entry : entries) {
		  //  System.out.println(entry.getKey() + " " + entry.getValue());
		  //}
		
				
		//Refresh timer
		int timer = QuakeGame.timer;
		int min = timer/60;
		String sec = Integer.toString(timer%60);
		if(Integer.parseInt(sec)<10){
			sec = "0" + sec;
		}
		
		
		
		
		
		
		
		
		for(String ligne : scoreboard.getEntries()){
			if(ligne.contains("§6Timer")){
				scoreboard.resetScores(ligne);
				
				String last = ligne.split("=")[0];
				String New = last + "= " + min + ":" + sec;
				
				objective.getScore(New).setScore(2);
			}
		}

	}
	
	
	
	//ON PLACE LES ELEMENTS QUI NE VONT PAS BOUGER
	public void getLine(){
		
		int i=4;
		for(UUID uuid : Quake.getInstance().playerInGame){
			
			String pname = Bukkit.getPlayer(uuid).getDisplayName();
			objective.getScore(pname + ": 0").setScore(i);
			i++;			
		}
		
		objective.getScore("§4 ").setScore(i);
		objective.getScore("§3 ").setScore(3);
		objective.getScore("§6Timer = 00:00 ").setScore(2);
		objective.getScore("§2 ").setScore(1);
		objective.getScore("§3AngelicHeart.fr").setScore(0);
		
	}
	
	
	
	//ON RECUP LE SCOREBOARD CUSTOM DU JOUEUR
	public static CustomScoreboardManager getScoreboard(Player p){
		return sb.get(p);
	}
	
	
	@Override
	public Scoreboard getMainScoreboard() {
		return scoreboard;
	}

	
	@Override
	public Scoreboard getNewScoreboard() {
		return null;
	}

}
