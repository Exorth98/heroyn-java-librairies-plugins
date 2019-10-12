package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.Survey;
import fr.exorth.util.SurveyL;
import fr.exorth.util.SurveyResultUtils;
import fr.exorth.util.SurveyUtils;

public class SurveyCommand implements CommandExecutor {
	
	public static FileConfiguration config = Survey.getInstance().getConfig();
	
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> surveys = (ArrayList<String>) config.getList("surveys");

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase("survey")){
			
			if(args.length==0 && s.hasPermission("survey.survey.admin")){
				
				s.sendMessage("§6===== Survey Commands =====");
				s.sendMessage("§c ");
				s.sendMessage("§eAlias possible : /sv");
				s.sendMessage("§c ");
				s.sendMessage("§e/survey list");
				s.sendMessage("§e/survey open <survey name>");
				s.sendMessage("§e/survey add <survey name>");
				s.sendMessage("§e/survey remove <survey name>");
				s.sendMessage("§c ");
				s.sendMessage("§e/survey questions <...>");
				s.sendMessage("§eou /survey qu <...>");
				s.sendMessage("§c ");
				s.sendMessage("§e/survey results <survey name>");
			}
			
			if(args.length > 0){
				
				if(args[0].equalsIgnoreCase("open")){
					
					if(s instanceof Player){
						
						Player p = (Player) s;
						
						if(args.length == 2){
							
							String nom = args[1];
							
							if(SurveyUtils.SurveyExist(nom)){
								
								if(SurveyUtils.isSurveyOk(nom)){
									
									
									new SurveyL(nom,p).OpenQuestion(1);
									ArrayList<String> infos =  new ArrayList<>();
									infos.add(nom);
									
									Survey.getInstance().SurveyResult.put(p,infos);
									
								}else{
									s.sendMessage("§cCe sondage est mal configuré");
								}
								
							}else{
								s.sendMessage("§cCe sondage n'existe pas");
							}
							
						}else{
							s.sendMessage("§c/survey open <survey name>");
						}
						
					}else{
						s.sendMessage("§cCommande reservée aux joueurs");
					}

					
				}
				else if(args[0].equalsIgnoreCase("list") && s.hasPermission("survey.survey.admin")){				
					if(args.length == 1){
						
						SurveyUtils.DisplaySurveys(s);
						
					}else{
						s.sendMessage("§c/survey list");
					}
					
				}
				else if(args[0].equalsIgnoreCase("add") && s.hasPermission("survey.survey.admin")){
					if(args.length == 2){
						
						String nom = args[1];
						
						if(!SurveyUtils.SurveyExist(nom)){

							
							if(surveys == null){surveys = new ArrayList<String>();}
							surveys.add(nom);
							
							config.set("surveys", surveys);
							
							
							s.sendMessage("§6Sondage §a" + nom + " §6àjouté");
							
							Survey.getInstance().saveConfig();
							
						}else{
							s.sendMessage("§cCe nom de sondage est déjà utilisé");
						}

						
						
					}else{
						s.sendMessage("§c/survey add <survey name>");
					}
					
				}
				else if(args[0].equalsIgnoreCase("remove") && s.hasPermission("survey.survey.admin")){
					if(args.length == 2){
						
						String nom = args[1];
						
						if(SurveyUtils.SurveyExist(nom)){

							surveys.remove(nom);
							config.set(nom,null);
							
							config.set("surveys", surveys);
							Survey.getInstance().saveConfig();
							
							s.sendMessage("§6Sondage §a" + nom + " §6supprimé");
							
							
						}else{
							s.sendMessage("§cCe sondage n'existe pas");
						}
						
						
						
					}else{
						s.sendMessage("§c/survey remove <survey name>");
					}
					
				}
				else if((args[0].equalsIgnoreCase("questions") || args[0].equalsIgnoreCase("qu")) && s.hasPermission("survey.survey.admin")){
					
					if(args.length == 3 || args.length == 4 ){
						
						if(args[1].equalsIgnoreCase("list")){
							
							if(args.length == 3){
								
								String nom = args[2];
								
								if(SurveyUtils.SurveyExist(nom)){
									
									ConfigurationSection questions = config.getConfigurationSection(nom);
									
									if(questions == null){
										s.sendMessage("§6Aucune question configurée pour ce sondage");
									}
									else{
										
										s.sendMessage("§6===== Questions pour §a" + nom + "§6=====");
										s.sendMessage("§c ");
										
										int i = 1;
										for(String question : questions.getKeys(false)){
											
											
											String pref;
											if(question.contains("[X]")){pref = "§c[!] [" + i + "] - §6" ;}
											else{pref = "§9[" + i + "] - §6" ;}
											
											s.sendMessage("§6- " + pref + question.toString());
											
											i++;
										}
										
									}
									
									
								}else{
									s.sendMessage("§cCe sondage n'existe pas");
								}
								
								
								
							}else{
								s.sendMessage("§c/survey questions list <survey name>");
							}
							
						}
						if(args[1].equalsIgnoreCase("add")){
							
							if(args.length == 4 ){
								
								if(isNumeric(args[3])){
									
									int number = Integer.parseInt(args[3]);
									
									if(number >1){
										
										String nom = args[2];
										
										if(SurveyUtils.SurveyExist(nom)){
											
											ArrayList<String> awns = new ArrayList<>();
											
											for(int i=1; i<= number; i++){
												
												awns.add("Réponse "+ i +" ici/0");
												
											}
											ConfigurationSection questions = config.getConfigurationSection(nom);
											int questionNumber;
											
											if(questions == null){questionNumber=1;}
											else{questionNumber = questions.getKeys(false).size()+1;}
											
											config.set(nom + ".[X]Question " + questionNumber + " ici[X]", awns);
											Survey.getInstance().saveConfig();
											
											s.sendMessage("§6Question ajoutée au sondage §a" + nom);
											
										}else{
											s.sendMessage("§cCe sondage n'existe pas");
										}
										
									}else{
										s.sendMessage("§cVous devez proposer au moins 2 possibilités de réponses");
									}
									
								}else{
									s.sendMessage("§cVeuillez entrer un nombre de réponses correct");
								}
								
							}else{
								s.sendMessage("§c/survey question add <survey name> <number of awnsers>");
							}
							
						}
						if(args[1].equalsIgnoreCase("remove")){
							
							
							if(args.length == 4 ){
								
								if(isNumeric(args[3])){
									
									int number = Integer.parseInt(args[3]);
									String nom = args[2];
									
									if(SurveyUtils.SurveyExist(nom)){
										
										ConfigurationSection questions = config.getConfigurationSection(nom);
										
										if(questions != null){
											
											if((number <= questions.getKeys(false).size()) && number >0){
												
											     //questions.getKeys(false).remove(number-1);
												config.set(nom + "." + questions.getKeys(false).toArray()[number-1], null);
												Survey.getInstance().saveConfig();
												s.sendMessage("§6Question supprimée du sondage §a" + nom);
											
											}else{
												s.sendMessage("§cVeuillez entrer un numéro de question correct, '/survey question list' permet de voir les numéros");
											}
											
										}
																				
										
									}else{
										s.sendMessage("§cCe sondage n'existe pas");
									}
								
								}else{
									s.sendMessage("§cVeuillez entrer un chiffre pour le numéro de question");
								}
								
							}else{
								s.sendMessage("§c/survey question remove <survey name> <number of the question>");
							}
							
						}
						
					}else{
						
						s.sendMessage("§6===== Survey Questions Commands =====");
						s.sendMessage("§c ");
						s.sendMessage("§eAlias possible : /sv qu");
						s.sendMessage("§c ");
						s.sendMessage("§e/survey questions list <survey name>");
						s.sendMessage("§e/survey questions add <survey name> <number of awnsers>");
						s.sendMessage("§e/survey questions remove <survey name> <number>");
						
					}
				
					
				}
				else if(args[0].equalsIgnoreCase("results") && s.hasPermission("survey.survey.admin")){
					
					if(s instanceof Player){
						
						Player p = (Player) s;
						
						
						if(args.length == 2){
							
							String nom = args[1];
							
							if(SurveyUtils.SurveyExist(nom)){
								
								if(SurveyUtils.isSurveyOk(nom)){
									
									
									ItemStack book = SurveyResultUtils.getResultBook(nom);
									
									p.getInventory().addItem(book);
									
									p.sendMessage("§6Résultats reçus !");
									
								}else{
									s.sendMessage("§cLa configuration de ce sondage n'est pas complète");
								}
								
							}else{
								s.sendMessage("§cCe sondage n'existe pas");
							}
						
						}else{
							p.sendMessage("§c/survey results <Survey Name>");
						}
						
					}else{
						s.sendMessage("§cCommande reservée aux joueurs");
					}
					
				}else{
					
					if(s.hasPermission("survey.survey.admin")){
						
						s.sendMessage("§6===== Survey Commands =====");
						s.sendMessage("§c ");
						s.sendMessage("§e/survey list");
						s.sendMessage("§e/survey open <survey name>");
						s.sendMessage("§e/survey add <survey name>");
						s.sendMessage("§e/survey remove <survey name>");
						s.sendMessage("§c ");
						s.sendMessage("§e/survey questions <...>");
						s.sendMessage("§c ");
						s.sendMessage("§e/survey results <survey name>");
						
					}

					
				}
				
				
				
				
			}
			
			
		}
		
		
		return false;
	}
	
	
	private boolean isNumeric(String str)  
	{  
	  try  
	  {  
		  Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}


