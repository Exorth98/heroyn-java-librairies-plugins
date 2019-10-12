package net.heroyn.heroynserverapi.economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.utils.MathUtils;

public class moneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("money")) {
			
			if(s instanceof Player) {
				
				Player p = (Player) s;
				
				if(args.length == 0) {
					
					double playerBalance = HeroynAPI.getMoneyInstance().getBalance(p.getUniqueId().toString());
					
					p.sendMessage("�aVous avez �b" + playerBalance +" Dreams");
					
				}
				else{
					
					if(args[0].equalsIgnoreCase("help")) {
						
						p.sendMessage("�6===== Commandes d'�conomie =====");
						p.sendMessage("�6");
						p.sendMessage("�e/money �f/Pour de consulter votre solde");
						p.sendMessage("�e/money send <Joueur> <monant> �f/Pour envoyer de l'argent � un joueur");
						p.sendMessage("�6");
						p.sendMessage("�6================================");
					}
					
					else if(args[0].equalsIgnoreCase("check") && p.hasPermission("heroynapi.money.check")) {
						
						if(args.length ==2) {
															
							String uuid = "X";							
							if(Bukkit.getPlayer(args[1]) != null)uuid = Bukkit.getPlayer(args[1]).getUniqueId().toString();
							else {
								@SuppressWarnings("deprecation")
								OfflinePlayer offP = Bukkit.getOfflinePlayer(args[1]);
								
								if(offP.hasPlayedBefore()) uuid = offP.getUniqueId().toString();
							}							
							if(!uuid.equals("X")) {		
								
								double balance = HeroynAPI.getMoneyInstance().getBalance(uuid);							
								p.sendMessage("�aSolde de �b"+args[1]+" �a: �b"+balance+" �aDreams");
								
							}else {
								p.sendMessage("�cCe joueur ne s'est jamais connect�");
							}
							
						}else {
							p.sendMessage("�cSyntaxe correcte : /money check <joueur>");
						}
						
					}
				
					else if(args[0].equalsIgnoreCase("send")) {
							
							if(args.length == 3) {
								
								String uuid = "X";							
								if(Bukkit.getPlayer(args[1]) != null)uuid = Bukkit.getPlayer(args[1]).getUniqueId().toString();
								else {
									@SuppressWarnings("deprecation")
									OfflinePlayer offP = Bukkit.getOfflinePlayer(args[1]);
									
									if(offP.hasPlayedBefore()) uuid = offP.getUniqueId().toString();
								}							
								if(!uuid.equals("X")) {		
									
									if(!uuid.equals(p.getUniqueId().toString())) {
										
										if(MathUtils.isDouble(args[2])) {
											
											double money = Double.parseDouble(args[2]);
											
											if(money>0) {					
												
												HeroynAPI.getMoneyInstance().exchangeMoney(p.getUniqueId().toString(), uuid, money);
												
												p.sendMessage("�aVous avez envoy� �b"+money+" �aDreams � �b"+args[1]);
												
											}else {
												p.sendMessage("�cMerci d'entrer un montant sup�rieur � 0");
											}										
											
										}else {
											
											p.sendMessage("�cMerci d'entrer un montant num�rique");
										}
										
									}else {
										p.sendMessage("�cVous ne pouvez pas vous envoyer de l'argent � vous-m�me");
									}
									
								}else {
									p.sendMessage("�cCe joueur ne s'est jamais connect�");
								}
							
							}else {
								p.sendMessage("�cSyntaxe correcte : /money send <Joueur> <monant>");							
							}						
					}
					else if(args[0].equalsIgnoreCase("add") && p.hasPermission("heroynapi.money.add")) {
						
						if(args.length == 3) {
							
							String uuid = "X";							
							if(Bukkit.getPlayer(args[1]) != null)uuid = Bukkit.getPlayer(args[1]).getUniqueId().toString();
							else {
								@SuppressWarnings("deprecation")
								OfflinePlayer offP = Bukkit.getOfflinePlayer(args[1]);
								
								if(offP.hasPlayedBefore()) uuid = offP.getUniqueId().toString();
							}							
							if(!uuid.equals("X")) {		
								
								if(MathUtils.isDouble(args[2])) {
									
									double money = Double.parseDouble(args[2]);
									
									if(money>0) {					
										
										HeroynAPI.getMoneyInstance().addMoney(uuid, money);
										
										p.sendMessage("�aVous avez ajout� �b"+money+" �aDreams � �b"+args[1]);
										
									}else {
										p.sendMessage("�cMerci d'entrer un montant sup�rieur � 0");
									}
									
									
								}else {
									
									p.sendMessage("�cMerci d'entrer un montant num�rique");
								}
								
							}else {
								p.sendMessage("�cCe joueur ne s'est jamais connect�");
							}
						
						}else {
							p.sendMessage("�cSyntaxe correcte : /money add <Joueur> <monant>");							
						}
						
					}
					else if(args[0].equalsIgnoreCase("remove") && p.hasPermission("heroynapi.money.remove")) {
						
						if(args.length == 3) {
							
							String uuid = "X";							
							if(Bukkit.getPlayer(args[1]) != null)uuid = Bukkit.getPlayer(args[1]).getUniqueId().toString();
							else {
								@SuppressWarnings("deprecation")
								OfflinePlayer offP = Bukkit.getOfflinePlayer(args[1]);
								
								if(offP.hasPlayedBefore()) uuid = offP.getUniqueId().toString();
							}							
							if(!uuid.equals("X")) {		
								
								if(MathUtils.isDouble(args[2])) {
									
									double money = Double.parseDouble(args[2]);
									
									if(money>0) {					
										
										HeroynAPI.getMoneyInstance().removeMoney(uuid, money);
										
										p.sendMessage("�aVous avez enlev� �b"+money+" �aDreams � �b"+args[1]);
										
									}else {
										p.sendMessage("�cMerci d'entrer un montant sup�rieur � 0");
									}									
									
								}else {
									
									p.sendMessage("�cMerci d'entrer un montant num�rique");
								}
								
							}else {
								p.sendMessage("�cCe joueur ne s'est jamais connect�");
							}
														
						}else {
							p.sendMessage("�cSyntaxe correcte : /money remove <Joueur> <monant>");							
						}
						
					}else {
						p.performCommand("money help");
					}
				}
					
					
			}else {
				s.sendMessage("�cCommande reserv�e aux joueurs");
			}			
		}
		
		return false;
	}
}