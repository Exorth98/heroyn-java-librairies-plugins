package fr.exorth.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.exorth.Quake;
import fr.exorth.util.QuakeStuff;
import fr.exorth.util.QuakeTeleport;


public class QuakeGame {
	
	public static int timer = 0;
	static int task;

	public static void start() {
		
		QuakeState.setState(QuakeState.GAME);
		
		for (UUID uuid : Quake.getInstance().playerInGame){
			Player pl = Bukkit.getPlayer(uuid);
			
			//initialisation des kills
			Quake.getInstance().kills.put(pl, 0);
			
			//Boost vitesse
			pl.getInventory().clear();
			
			pl.addPotionEffect( new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2),true);
			
			//Give de la Hoe
			QuakeStuff.giveStuff(pl);
			
			//Tp en arene
			QuakeTeleport.teleport(pl);
			
		}
		
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Quake.getInstance(),new Runnable(){

			@Override
			public void run() {
				
				for (UUID uuid : Quake.getInstance().playerInGame){
					Player pl = Bukkit.getPlayer(uuid);										
					pl.addPotionEffect( new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2),true);
				
				}
				
				if(QuakeState.isState(QuakeState.GAME)){
					timer++;
				}
				
				
				if(timer == 1800){
					Bukkit.broadcastMessage("§9[Quake] §6Le jeu à commencé il y a 30min, plus que 5min avant la fin ! ");
				}
				
				if(timer == 1860 || timer == 1920 || timer == 1980 || timer == 2040){
					Bukkit.broadcastMessage("§9[Quake] §6Fin du jeu dans §c" +(35-(timer/60)) + "§6min !");
				}
				
				if(timer == 1100){
					
					Bukkit.shutdown();
				
				}
				
				
			}	
			
		}, 20, 20);
	}

}
