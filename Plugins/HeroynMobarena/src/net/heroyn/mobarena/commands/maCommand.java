package net.heroyn.mobarena.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.classe.ClassesInfos;
import net.heroyn.mobarena.customEvents.MaLeaveReason;
import net.heroyn.mobarena.customEvents.MaPlayerLeaveEvent;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.InMobarenaPlayer;
import net.heroyn.mobarena.game.player.MobarenaSpectator;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;
import net.heroyn.mobarena.menus.MobarenaMenu;
import net.heroyn.mobarena.utils.Arena;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;

public class maCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("ma")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				
				//Aucun argument --> aide
				if(args.length == 0) {
					
					new MobarenaMenu().open(p);
				}
				else if( args.length == 1) {
					
					if(args[0].equalsIgnoreCase("help")) {
						
						p.sendMessage("§e================ MobArena =================");
						p.sendMessage("");
						p.sendMessage("§e/ma join <arena> ,§fpour rejoindre une arène");
						p.sendMessage("§e/ma spectate <arena> ,§fPour specate une arène");
						p.sendMessage("§e/ma spectate <arena> ,§fPour specate une arène");
						p.sendMessage("§e/ma start,§fPour rejoindre le bataille quand vous êtes équipé");
						p.sendMessage("§e/ma list, §fPour lister les arènes disponibles");
						p.sendMessage("");
						p.sendMessage("§e===========================================");
						
					}
					else if(args[0].equalsIgnoreCase("list")) {
						
						List<String> arenas = Arena.getArenasNames();
						
						p.sendMessage("§e=== Arènes ===");
						p.sendMessage("");
						for(String arena : arenas) {
							p.sendMessage("- " + arena);
						}
						p.sendMessage("§e==============");
						
					}
					else if(args[0].equalsIgnoreCase("leave")) {						
							
							if(HeroynMobarenaUtils.isInGame(p)) {
								
								InMobarenaPlayer inMaP = InMobarenaPlayer.getFromPlayer(p);
								Arena ar = inMaP.getArena();
								MobarenaGame game = HeroynMobarena.getInstance().games.get(ar.getName());
								
								Bukkit.getPluginManager().callEvent(new MaPlayerLeaveEvent(game, p, MaLeaveReason.LEAVECOMMAND));	
							}
							else {
								p.sendMessage("§cTu n'es pas en Mobarena");
							}
						
					}
					else if(args[0].equalsIgnoreCase("start")) {
						
						if(HeroynMobarenaUtils.isSuitingup(p)) {
							
							MobarenaSuitingupPlayer maSupP = MobarenaSuitingupPlayer.getFromPlayer(p);
							Arena ar = maSupP.getArena();
							MobarenaGame game = HeroynMobarena.getInstance().games.get(ar.getName());
							
							if(!game.isFull()) {
								
								//=========JUSTE POUR LE TEST============
								//maSupP.setSelectionnedCharacter(new Tank(p, 5));
								//=======================================
								
								if(maSupP.getSelectionnedCharacter() != null) {
									
									game.startPlayer(maSupP);
									
								}else {								
									p.sendMessage("§cSelectiones une classe pour rejoindre le combat !");
								}								
							}else {
								p.sendMessage("§cArène pleine !");
							}
						}else {
							p.sendMessage("§cTu n'es pas en zone d'équipement !");
						}												
					}
					else p.performCommand("ma help");
				}
				else if(args.length == 2) {
					
					if(args[0].equalsIgnoreCase("join")) {
						
						if(!HeroynMobarenaUtils.isInFight(p)) {
							
							if(!HeroynMobarenaUtils.isSuitingup(p)) {
								
								if(!HeroynMobarenaUtils.isInGameSpectate(p)) {
									
									String name = args[1];
									
									if(Arena.exists(name)) {
										
										Arena arena = new Arena(name);
										
										if(arena.isEnabled()) {
											
											if(arena.isInGame()) {
												
												MobarenaGame game = HeroynMobarena.getInstance().games.get(arena.getName());
												
												if(!game.getBannedPlayers().contains(p))										
												game.playerJoinSuitup(p);
												
												else p.sendMessage("§cTu as quitté cette partie, tu ne peux plus y retourner avant que l'arène redémarre");												
											}
											else {
												
												MobarenaGame game = new MobarenaGame(arena);
												game.playerJoinSuitup(p);
											}
										}else {
											p.sendMessage("§cCette arène est désactivée");
										}														
									}else {
										p.sendMessage("§cCette arène n'existe pas");
									}									
								}else {
									p.sendMessage("§cQuitte le mode spectateur pour rejoindre avant de rejoindre l'arène (/ma leave)");
								}
							}else {
								p.sendMessage("§cTu es déjà en zone d'équipement, pour lancer le combat fais /ma start");
							}
						}else{							
							p.sendMessage("§cTu es déjà en Mobarena !");
						}						
					}
					else if(args[0].equalsIgnoreCase("spectate")) {
						
						
						if(!HeroynMobarenaUtils.isInFight(p)) {
							
							String name = args[1];
							
							if(Arena.exists(name)) {
								
								Arena arena = new Arena(name);
								
								if(arena.isEnabled()) {
									
									if(arena.isInGame()) {
										
										MobarenaGame game = HeroynMobarena.getInstance().games.get(arena.getName());
										MobarenaSpectator maS = new MobarenaSpectator(p, arena);
										game.playerSpectate(maS);
										
									}else {										
										p.sendMessage("§cCette arène n'est pas en jeu");									
									}																		
								}else {
									p.sendMessage("§cCette arène est désactivée");
								}														
							}else {
								p.sendMessage("§cCette arène n'existe pas");
							}														
						}else{							
							p.sendMessage("§cTu es en Mobarena !");
						}												
					}
					else if(args[0].equalsIgnoreCase("choose")){
						
						if(HeroynMobarenaUtils.isSuitingup(p)) {
							
							MobarenaSuitingupPlayer sP = MobarenaSuitingupPlayer.getFromPlayer(p);
							String className = args[1];
							ClassesInfos infos = ClassesInfos.getInfos(className);
							
							if(infos != null) {							
								int level = HeroynMobarenaUtils.getClassLevel(p, infos.getName());
								ClasseBase sel = ClassesInfos.getClasseBase(infos.getName(), p, level);
								sP.setSelectionnedCharacter(sel);
							
							}else {
								p.sendMessage("§cCette classe n'existe pas");
							}
						}else {
							p.sendMessage("§cTu n'es pas en zone d'équipement");
						}
					}
					else {
						p.performCommand("ma help");
					}					
				}
				else {					
					p.performCommand("ma help");					
				}								
			}else {
				s.sendMessage("§cCommande reservée aux joueurs");
			}						
		}		
		return false;
	}
}
