package fr.exorth.game;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.exorth.UHCRun;
import fr.exorth.util.Bordures;
import fr.exorth.util.UHCTeleport;

public class UHCGame {
	
	public static int timer = 0;
	static int task;
	
	public static void start(){
		
		UHCState.setState(UHCState.PREGAME);
		
		for (UUID uuid : UHCRun.getInstance().playerInGame){
			Player pl = Bukkit.getPlayer(uuid);
			//Boost vitesse
			pl.addPotionEffect( new PotionEffect(PotionEffectType.SPEED,1200,2));
			
			//tp aléatoire sur la map
			UHCTeleport.tpRandom(pl);
			
		}
		
		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(UHCRun.getInstance(),new Runnable(){

			@Override
			public void run() {
				timer++;
				
				if(timer == 30){
					UHCState.setState(UHCState.GAME);
				}
				
				if(timer == 600){
					Bukkit.getWorld("world").setPVP(true);
					UHCState.setState(UHCState.GAMEPVP);
					
					//les bordures retrecissent
					
					if(timer >=630){
						if(timer > 0){
							Bordures b = new Bordures();
							b.decreaseBorder();	
						}
					}
					
					/*if(timer ==0){
						
						for(UUID uuid : UHCRun.getInstance().playerInGame){
							Bukkit.getPlayer(uuid).teleport(new Location(Bukkit.getWorld("world"),0,0,0));
						}
						
						Bordures b = new Bordures();
						b.setBorder(50.0);
					}*/
					
					
					
				}
							
			}
			
			
			
		}, 20, 20);
		
	}
}
