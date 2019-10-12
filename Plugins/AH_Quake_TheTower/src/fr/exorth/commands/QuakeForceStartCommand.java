package fr.exorth.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.Quake;
import fr.exorth.events.QuakeJoin;
import fr.exorth.game.QuakeGame;
import fr.exorth.scoreboard.CustomScoreboardManager;
import fr.exorth.scoreboard.ScoreboardRunnable;

public class QuakeForceStartCommand implements CommandExecutor {
	
	private int timer= 30;
	private int task;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			Player p = (Player) sender;
			
			if(p.isOp()){
				
				if(cmd.getName().equalsIgnoreCase("forcestart")){
					
					if(args.length ==0){
						
						Bukkit.broadcastMessage("§9[Quake] §c" + p.getDisplayName() + "§9 à forcé le demarrage du jeu à §c" 
						+ Quake.getInstance().playerInGame.size() + " §9 joueurs");
						
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
								
								
								timer--;
								QuakeJoin.setLevel(timer);
								
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

						QuakeGame.start();
						
						return true;
					}else{
						p.sendMessage("§cLa synthaxe correcte est : /forcestart");
					}

				}
				
			}else{
				p.sendMessage("§cTu n'as pas la permission");
			}
		}else{
			sender.sendMessage("Commande reservée aux joueurs");
		}
		
		return false;
	}

}
