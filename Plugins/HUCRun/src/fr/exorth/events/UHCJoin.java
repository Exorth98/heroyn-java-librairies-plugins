package fr.exorth.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.exorth.UHCRun;
import fr.exorth.api.Sounds;
import fr.exorth.api.Title;
import fr.exorth.game.UHCGame;
import fr.exorth.game.UHCState;
import fr.exorth.scoreboards.CustomScoreboardManager;
import fr.exorth.scoreboards.ScoreboardRunnable;

public class UHCJoin implements Listener{
	
	static int task;
	public static int timer = 30;
	
	@EventHandler
	public void join(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		new Sounds(p).playSound(Sound.BLOCK_NOTE_PLING);
		
		if(UHCState.isState(UHCState.WAIT)){
			
			if(!UHCRun.getInstance().playerInGame.contains(p.getUniqueId())){
				UHCRun.getInstance().playerInGame.add(p.getUniqueId());
	
				
				if(UHCRun.getInstance().playerInGame.size()==1){
					
					
					
					new CustomScoreboardManager(p);
					ScoreboardRunnable.sendLine();
					p.setScoreboard(CustomScoreboardManager.getScoreboard(p).getMainScoreboard());					
					
					//STart du chrono
					
					task = Bukkit.getScheduler().scheduleSyncRepeatingTask(UHCRun.getInstance(), new Runnable(){

						@Override
						public void run() {
							timer--;
							setLevel(timer);
							
							if(timer == 15 || timer ==10 || (timer <=5 && timer !=0)){
								
								for(UUID uuid : UHCRun.getInstance().playerInGame){
									Bukkit.getPlayer(uuid).sendMessage("§6Le jeu Commence dans " + timer + " sec");
									Title.sendTitle(Bukkit.getPlayer(uuid),"UHCRun", "§6Le jeu Commence dans " + timer + " sec", 20);
								}
								
							}
							
							if(timer == 0){
								//arrte chrono
								Bukkit.getScheduler().cancelTask(task);
								//lancer game
								UHCGame.start();
							}
							
						}
						
					},20,20);
				}
			}
			
		}
		else{
			p.sendMessage("Le jeu a déjà commancé, vous êtes Spectateur");
			p.setGameMode(GameMode.SPECTATOR);
		}
		
	}
	
	public void setLevel(int timer){
		for(UUID uuid : UHCRun.getInstance().playerInGame){
			Bukkit.getPlayer(uuid).setLevel(timer);
			
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		UHCRun.getInstance().playerInGame.remove(p.getUniqueId());	
		CustomScoreboardManager.sb.remove(p);
	}

}
