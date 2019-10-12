package fr.exorth.board;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.exorth.board.Board;

public class CustomScoreboardManager implements ScoreboardManager{
	
	public Player player;
	public Scoreboard sb;
	public Objective obj;
	public String name;
	
	public CustomScoreboardManager(Player player){
		

		this.sb =  Bukkit.getScoreboardManager().getNewScoreboard();
		this.player=player;
		
		
		if(Board.getInstance().sb.containsKey(player)) return;
		
		Board.getInstance().sb.put(player, this);
		
		this.name= "sb." + new Random().nextInt(99999);
		this.obj = sb.registerNewObjective(name,"dummy");
				
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6*-Infos-*");			
	}

	@Override
	public Scoreboard getMainScoreboard() {
		return sb;
	}

	@Override
	public Scoreboard getNewScoreboard() {
		return null;
	}
	
	public void refresh() {
		for(String ligne : sb.getEntries()){
			
			if(ligne.contains("§eJoueurs")){
				sb.resetScores(ligne);
				
				String last = ligne.split(":")[0];
				String New = last + ": §f" + Bukkit.getOnlinePlayers().size();
				
				obj.getScore(New).setScore(7);
			}
			
			if(ligne.contains("§7Morts")){
				sb.resetScores(ligne);
				
				String last = ligne.split(":")[0];
				String New = last + ": §f" + player.getStatistic(Statistic.DEATHS);
				
				obj.getScore(New).setScore(5);
			}
			
			if(ligne.contains("§7Kills (joueurs)")){
				sb.resetScores(ligne);
				
				String last = ligne.split(":")[0];
				String New = last + ": §f" + player.getStatistic(Statistic.PLAYER_KILLS);
				
				obj.getScore(New).setScore(4);
			}
			
			if(ligne.contains("§7Kills (mobs)")){
				sb.resetScores(ligne);
				
				String last = ligne.split(":")[0];
				String New = last + ": §f" + player.getStatistic(Statistic.MOB_KILLS);
				
				obj.getScore(New).setScore(3);
			}	
			if(ligne.contains("§cSanté")){
				sb.resetScores(ligne);
				
				String last = ligne.split(":")[0];
				String New = last + ": " + player.getHealth() + "/20";
				
				obj.getScore(New).setScore(1);
			}
		}
		
		
	}
	
	public void sendLine() {
		obj.getScore("       ").setScore(8);		
		obj.getScore("§eJoueurs : null").setScore(7);		
		obj.getScore(" ").setScore(6);		
		obj.getScore("§7Morts : null").setScore(5);	
		obj.getScore("§7Kills (joueurs): null").setScore(4);		
		obj.getScore("§7Kills (mobs): null").setScore(3);		
		obj.getScore("     ").setScore(2);		
		obj.getScore("§cSanté: null").setScore(1);	
	}
	
	public void setScoreboard() {
		player.setScoreboard(sb);
	}

}
