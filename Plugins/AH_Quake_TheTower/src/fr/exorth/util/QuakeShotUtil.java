package fr.exorth.util;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.exorth.Quake;
import fr.exorth.game.QuakeState;

public class QuakeShotUtil {
	
	static int task;
	static int timer = 25;

	public static void doShot(Player victim,Player killer) {
		
		//respawn victim
		victim.getInventory().clear();
		QuakeTeleport.teleport(victim);
		QuakeStuff.giveStuff(victim);
		
		//messages
		//victim.sendMessage("§9[Quake] Tué par §c" + killer.getDisplayName());
		//killer.sendMessage("§9[Quake] Vous avez tué §c" + victim.getDisplayName());
		Bukkit.broadcastMessage("§9[Quake] §c" + killer.getDisplayName() + " §9à tué §c" + victim.getDisplayName());
		
		//Donne un kill au killer
		Quake.getInstance().kills.put(killer, Quake.getInstance().kills.get(killer)+1);
		
	}


	public static void checkVictory() {
		
		for(Player p : Quake.getInstance().kills.keySet()){
			
			if(Quake.getInstance().kills.get(p) >= 50){
				endGame(p);
			}
			
		}
		
	}


	public static void endGame(Player p) {
		QuakeState.setState(QuakeState.FINISH);
		
		Bukkit.broadcastMessage("§6§l=======================================");
		Bukkit.broadcastMessage("    ");
		Bukkit.broadcastMessage("§6§l            Victoire de");
		Bukkit.broadcastMessage("§a§l            " + p.getDisplayName());
		Bukkit.broadcastMessage("    ");
		Bukkit.broadcastMessage("§6§l=======================================");
		Bukkit.broadcastMessage("§cFermeture du serveur dans 25sec...");
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Quake.getInstance(),new Runnable(){

			@Override
			public void run() {
					timer--;
					
				
						
				if(timer == 0){
					Bukkit.shutdown();
					QuakeState.setState(QuakeState.RESTARTING);
				}
							
			}	
			
		}, 20, 20);
		
		
		
	}
	
	public static void endGame2() {
		if(QuakeState.getState() == QuakeState.GAME){
			QuakeState.setState(QuakeState.FINISH);
			
			Bukkit.broadcastMessage("§cFermeture du serveur dans 10sec...(Plus assez de joueurs)");
			timer = 10;
			
			task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Quake.getInstance(),new Runnable(){

				@Override
				public void run() {
						timer--;
						
					
							
					if(timer == 0){
						Bukkit.shutdown();
						QuakeState.setState(QuakeState.RESTARTING);
					}
								
				}	
				
			}, 20, 20);
		}
	
	}


	public static void killstreak(Player p, int killstreak) {
		if(killstreak == 2){
			Bukkit.broadcastMessage("§9[Quake] §cDouble kill §9de §c" + p.getDisplayName());
		}
		if(killstreak == 3){
			Bukkit.broadcastMessage("§9[Quake] §cTriple kill §9de §c" + p.getDisplayName());
		}
		if(killstreak >3){
			Bukkit.broadcastMessage("§9[Quake] §cMultiple kill §9de §c" + p.getDisplayName() + "(x" + killstreak + ")");
		}
		
	}

}
