package fr.exorth.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.exorth.Quake;
import fr.exorth.database.SqlConnection;
import fr.exorth.game.QuakeGame;
import fr.exorth.scoreboard.CustomScoreboardManager;
import fr.exorth.scoreboard.ScoreboardRunnable;
import fr.exorth.game.QuakeState;
import fr.exorth.util.QuakeJoinItem;
import fr.exorth.util.QuakeShotUtil;
import fr.exorth.util.QuakeTeleport;

public class QuakeJoin implements Listener {
	
	public static FileConfiguration config = Quake.getInstance().getConfig();
	
	static int task;
	public static int timer = 30;
	
	private SqlConnection sql;

	public QuakeJoin(SqlConnection sql) {
		this.sql = sql;
	}
	
	@EventHandler
	public void onQuakeJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		sql.setPlayers(Bukkit.getOnlinePlayers().size());
		
		Quake.getInstance().hoes.put(p, Material.WOOD_HOE);
		
		p.getInventory().clear();
		
		QuakeJoinItem.give(p);
		
		QuakeTeleport.teleport(p);
		
		p.addPotionEffect( new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2),true);
		
		e.setJoinMessage("");
		
		if(QuakeState.isState(QuakeState.WAIT)){
			
			if(!Quake.getInstance().playerInGame.contains(p.getUniqueId())){
				Quake.getInstance().playerInGame.add(p.getUniqueId());
				
				p.setGameMode(GameMode.SURVIVAL);
				
				p.setFoodLevel(20);
				
				e.setJoinMessage( "§9[Quake] §6 Le joueur §3" + p.getDisplayName() + " §6à rejoint le Quake !  (" + Quake.getInstance().playerInGame.size() + "/15)");
	
				
				if(Quake.getInstance().playerInGame.size()== config.getInt("arena.configs.minplayerstostart") && QuakeState.isState(QuakeState.WAIT)){
					
					for(UUID uuid : Quake.getInstance().playerInGame){
						Player pl = Bukkit.getPlayer(uuid);
						new CustomScoreboardManager(pl);
						pl.setScoreboard(CustomScoreboardManager.getScoreboard(pl).getMainScoreboard());	
						Bukkit.getPlayer(uuid).sendMessage("§9[Quake] §6Le jeu Commence dans §c30 §6sec");
					}
					ScoreboardRunnable.sendLine();
					
			
				
					//STart du chrono
					
					task = Bukkit.getScheduler().scheduleSyncRepeatingTask(Quake.getInstance(), new Runnable(){

						@Override
						public void run() {
							
							if(Quake.getInstance().playerInGame.size()<config.getInt("arena.configs.minplayerstostart")){
								for(UUID uuid : Quake.getInstance().playerInGame){
									Bukkit.getPlayer(uuid).sendMessage("§9[Quake] §6Plus assez de joueurs, reprise de l'attente");
								}
								setLevel(0);
								timer=31;
								Bukkit.getScheduler().cancelTask(task);
							}
							
							
							timer--;
							setLevel(timer);
							
							if(timer == 15 || timer ==10 || (timer <=5 && timer !=0)){
								
								for(UUID uuid : Quake.getInstance().playerInGame){
									Bukkit.getPlayer(uuid).sendMessage("§9[Quake] §6Le jeu Commence dans §c" + timer + " §6sec");
									//Title.sendTitle(Bukkit.getPlayer(uuid),"§9Quake", "§6Le jeu Commence dans " + timer + " sec", 20);
								}
								
							}
							
							if(timer == 0){
								//arrete chrono
								Bukkit.getScheduler().cancelTask(task);
								//lancer game
								QuakeGame.start();
							}
							
						}
						
					},20,20);
				}
			}
			
		}
		else{
			p.sendMessage("§9[Quake] §6Le jeu a déjà commancé, vous êtes Spectateur");
			p.setGameMode(GameMode.SPECTATOR);
			new CustomScoreboardManager(p);
			p.setScoreboard(CustomScoreboardManager.getScoreboard(p).getMainScoreboard());
			ScoreboardRunnable.sendLine();
			Bukkit.broadcastMessage("§9[Quake] §3" + p.getDisplayName() + " §6rejoint la partie en tant que spectateur");
		}
		
	}
	
	public static void setLevel(int level){
		for(UUID uuid : Quake.getInstance().playerInGame){
			Bukkit.getPlayer(uuid).setLevel(level);
			
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		
		sql.setPlayers(Bukkit.getOnlinePlayers().size()-1);
		e.setQuitMessage("§9[Quake] §6 Le joueur §3" + e.getPlayer().getDisplayName() + " §6à quitté le Quake");
		Player p = e.getPlayer();
		Quake.getInstance().playerInGame.remove(p.getUniqueId());	
		CustomScoreboardManager.sb.remove(p);
		
		if(Quake.getInstance().playerInGame.size()<2){
			QuakeShotUtil.endGame2();
		}
	}
	
	

}
