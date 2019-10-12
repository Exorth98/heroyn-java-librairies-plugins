package fr.exorth.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Awnser;
import fr.exorth.surveys.OneShotSurvey;
import fr.exorth.surveys.Question;
import fr.exorth.surveys.Survey;
import fr.exorth.surveys.TemporarySurvey;
import fr.exorth.util.SurveyOpen;
import fr.exorth.util.SurveyUtils;

public class SurveyCommand implements CommandExecutor {

	
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(s instanceof Player){
			
			Player p = (Player) s;
			
			if(args.length==0){
				
				s.sendMessage("§c");
				s.sendMessage("§c");
				s.sendMessage("§e");
				s.sendMessage("§6===== Commandes Survey =====");
				s.sendMessage("§e/sv list");
				s.sendMessage("§e/sv create <nom> <ONESHOT/TEMPORARY> [nom d'affichage]");
				s.sendMessage("§e/sv info");
				s.sendMessage("§e/sv clear");
				s.sendMessage("§e/sv select <nom>");			
				s.sendMessage("§c ");
				s.sendMessage("§e/sv open [nom] [-cal]");
				s.sendMessage("§e/sv display [nom]");
				s.sendMessage("§e/sv setvisibility [nom] <true/false>");
				s.sendMessage("§e/sv results [nom]");
				s.sendMessage("§e/sv delete [nom]");
				s.sendMessage("§e/sv setdname <nom d'affichage>");
				s.sendMessage("§e/sv qu <...>");
				s.sendMessage("§e/sv aw <...>");
				s.sendMessage("§6============================");
				
				
			}else{
				
				
				
				
				
				if(args[0].equalsIgnoreCase("list")){
					
					if(args.length==1){
						
						SurveyUtils.list(p);
						
					}else{
						s.sendMessage("§e/sv list");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("create")){
					
					if(args.length>=3){
						
						if(!SurveyUtils.surveyExist(args[1])){
							
							String name = args[1];
							
							if(args[2].equalsIgnoreCase("ONESHOT")||args[2].equalsIgnoreCase("TEMPORARY")){
								
								String type="";
								
								if(args[2].equalsIgnoreCase("ONESHOT")){
									
									OneShotSurvey OsSurvey = new OneShotSurvey(name);
									OsSurvey.saveSurvey();
									if(args.length>3){
										
										StringBuilder sb = new StringBuilder();
										
										for(int i =3;i<args.length;i++){
											sb.append(args[i].replace("&", "§") + " ");
										}
										
										OsSurvey.setDisplayName(sb.toString());

									}
									type="ONESHOT";
									
								}
								if(args[2].equalsIgnoreCase("TEMPORARY")){
									
									TemporarySurvey TeSurvey = new TemporarySurvey(name);
									TeSurvey.saveSurvey();
									if(args.length>3){
										
										StringBuilder sb = new StringBuilder();
										
										for(int i =3;i<args.length;i++){
											sb.append(args[i].replace("&", "§") + " ");
										}										
										
										TeSurvey.setDisplayName(sb.toString());
									}
									type="TEMPORARY";
								}
								
								s.sendMessage("§aVous avez créé un nouveau sondage §b"+name+" §ade type §b"+type);
							
							}else{
								s.sendMessage("§cLes types possibles sont ONESHOT/TEMPORARY");
							}
							
						}else{
							s.sendMessage("§cNom déja utilisé");
						}

						
					}else{
						s.sendMessage("§e/sv create <nom> <type> [nom d'affichage]");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("info")){
					
					if(args.length==1){
						
						String survey = "§cAucun";
						String question = "§cAucune";
						
						if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
							survey = "§b" + SurveyMain.getInstance().SurveySelection.get(p);
						}
						if(SurveyMain.getInstance().QuestionSelection.containsKey(p)){
							question = "§bn°" + SurveyMain.getInstance().QuestionSelection.get(p);
						}
						
						s.sendMessage("§c");s.sendMessage("§c");
						s.sendMessage("§a===================================");
						s.sendMessage("§aSondage séléctionné: " + survey);
						s.sendMessage("§aQuestion séléctionnée: " + question);
						s.sendMessage("§a===================================");
						
						
						
					}else{
						s.sendMessage("§e/sv info");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("clear")){
					
					if(args.length==1){
						
						SurveyMain.getInstance().QuestionSelection.remove(p);
						SurveyMain.getInstance().SurveySelection.remove(p);
						
						s.sendMessage("§aSéléction netoyée");
						
					}else{
						s.sendMessage("§e/sv clear");
					}
					
				}
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("select")){
					
					if(args.length==2){
						
						String name = args[1];
						
						if(SurveyUtils.surveyExist(name)){
							
							if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
								
								if(!SurveyMain.getInstance().SurveySelection.get(p).equalsIgnoreCase(name)){
									
									SurveyMain.getInstance().QuestionSelection.remove(p);
									SurveyMain.getInstance().SurveySelection.remove(p);
									
									SurveyMain.getInstance().SurveySelection.put(p, name);
									s.sendMessage("§eSondage §a"+name+" §eséléctionné");
									
								}else{
									s.sendMessage("§cTu as déja séléctionné ce sondage");
								}
								

							}else{
								
								SurveyMain.getInstance().SurveySelection.put(p, name);
								s.sendMessage("§eSondage §a"+name+" §eséléctionné");
							}
						}else{
							s.sendMessage("§cCe sondage n'existe pas");
						}
						
					}else{
						s.sendMessage("§e/sv select <nom>");
					}
					
				}
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("open")){
					
					if(args.length==1 || args.length==2 || args.length==3){
						
						if(!(args.length==1 && !SurveyMain.getInstance().SurveySelection.containsKey(p))){
							
							if(args.length==1){
								
								String name = SurveyMain.getInstance().SurveySelection.get(p);
								
								if(SurveyUtils.surveyExist(name)){
																		
									Survey sv = SurveyUtils.getSurvey(name);
									
									if(SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Cal")!=null) {
										
										SurveyOpen.inClassicMode(p,sv);
									}else {
										s.sendMessage("§cIl faut calibrer ce condage au moins une fois aavant de l'ouvrir");
									}								
									
									
								}else{
									s.sendMessage("§cCe sondage n'existe pas");
								}

								
							}
							else if(args.length==2){
								
								if(args[1].equalsIgnoreCase("-cal")){
									
									if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
											
											String name = SurveyMain.getInstance().SurveySelection.get(p);
											
											if(SurveyUtils.surveyExist(name)){
																					
												Survey sv = SurveyUtils.getSurvey(name);
												
												if(sv.getQuestions().size()!=0) {
													
													SurveyOpen.inCalibrationMode(p,sv);
												}else {
													s.sendMessage("§cCe sondage n'a aucune question de configurée");
												}
												

												
											}else{
												s.sendMessage("§cCe sondage n'existe pas");
											}
										
									}else{
										s.sendMessage("§cTu dois selectionner ou préciser un sondage");
									}
									
								}else{
									
									String name = args[1];
									
									if(SurveyUtils.surveyExist(name)){
										
										Survey sv = SurveyUtils.getSurvey(name);
										
										if(SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Cal")!=null) {
											
											SurveyOpen.inClassicMode(p,sv);
										}else {
											s.sendMessage("§cIl faut calibrer ce condage au moins une fois aavant de l'ouvrir");
										}
										
									}else{
										s.sendMessage("§cCe sondage n'existe pas");
									}
									
									
								}
								
							}
							else if(args.length==3){
								
								String name = args[1];
								
								if(SurveyUtils.surveyExist(name)){
									
									if(args[2].equalsIgnoreCase("-cal")){
																				
										Survey sv = SurveyUtils.getSurvey(name);
										
										if(sv.getQuestions().size()!=0) {
											
											SurveyOpen.inCalibrationMode(p,sv);
										}else {
											s.sendMessage("§cCe sondage n'a aucune question de configurée");
										}
										
									}else{
										s.sendMessage("§cTerme incorrect");
									}

									
								}else{
									s.sendMessage("§cCe sondage n'existe pas");
								}
							}
							
						}else{
							s.sendMessage("§cTu dois selectionner ou préciser un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv open [nom] [-cal]");
					}
					
				}
				
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("results")){
					
					if(args.length==1 || args.length==2){
						
						if(!(args.length==1 && !SurveyMain.getInstance().SurveySelection.containsKey(p))){
							
							///
							
						}else{
							s.sendMessage("§cTu dois selectionner ou préciser un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv results [nom]");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("delete")){
					
					if(args.length==1 || args.length==2){
						
						if(!(args.length==1 && !SurveyMain.getInstance().SurveySelection.containsKey(p))){
							
							if(args.length==1){
								
								String name = SurveyMain.getInstance().SurveySelection.get(p);
								Survey sv = SurveyUtils.getSurvey(name);
								sv.removeSurvey();
								s.sendMessage("§aSondage §b"+name+" §asupprimé");
								
							}else{
								
								String name = args[1];
								if(SurveyUtils.surveyExist(name)){
									Survey sv = SurveyUtils.getSurvey(name);
									sv.removeSurvey();
									s.sendMessage("§aSondage §b"+name+" §asupprimé");
								}else {
									s.sendMessage("§cCe sondage n'existe pas");
								}
	
							}						
							
						}else{
							s.sendMessage("§cTu dois selectionner ou préciser un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv delete [nom]");
					}
					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("display")){
					
					if(args.length==1 || args.length==2){
						
						if(!(args.length==1 && !SurveyMain.getInstance().SurveySelection.containsKey(p))){
							
							if(args.length==1){
								
								String name = SurveyMain.getInstance().SurveySelection.get(p);
								Survey sv = SurveyUtils.getSurvey(name);
								sv.Display(p);
								
							}else{
								
								String name = args[1];
								if(SurveyUtils.surveyExist(name)){
									Survey sv = SurveyUtils.getSurvey(name);
									sv.Display(p);
								}
	
							}						
							
						}else{
							s.sendMessage("§cTu dois selectionner ou préciser un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv delete [nom]");
					}
					
				}
				
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("setvisibility")){
					
					if(args.length==2 || args.length==3){
						
						if(!(args.length==2 && !SurveyMain.getInstance().SurveySelection.containsKey(p))){
							
							if(args.length==2){
								
								String name = SurveyMain.getInstance().SurveySelection.get(p);
								Survey sv = SurveyUtils.getSurvey(name);
								
								if(args[1].equalsIgnoreCase("true")) {
									
									sv.setVisibility(true);
									s.sendMessage("§aLe sondage est mainteant §bVisible");
								}
								else if(args[1].equalsIgnoreCase("false")) {
									
									sv.setVisibility(false);
									s.sendMessage("§aLe sondage est mainteant §bInvisible");
								}
								else {
									s.sendMessage("§cIl faut entrer true ou false");
									
								}
								
							}else{
								
								String name = args[1];
								if(SurveyUtils.surveyExist(name)){
									Survey sv = SurveyUtils.getSurvey(name);
									
									if(args[2].equalsIgnoreCase("true")) {
										
										sv.setVisibility(true);
										s.sendMessage("§aLe sondage est mainteant §bVisible");
									}
									else if(args[2].equalsIgnoreCase("false")) {
										
										sv.setVisibility(false);
										s.sendMessage("§aLe sondage est mainteant §bInvisible");
									}
									else {
										s.sendMessage("§cIl faut entrer true ou false");
										
									}
									
								}else {
									s.sendMessage("§cCe sondage n'esxiste pas");
								}
	
							}						
							
						}else{
							s.sendMessage("§cTu dois selectionner ou préciser un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv setvisibility [nom] <true/false>");
					}
					
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("setbeggin")){
					
					if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
						
						String name = SurveyMain.getInstance().SurveySelection.get(p);
						
						if(SurveyUtils.isSurveyType("TEMPORARY",name)){
														
							if(args.length==2){
								
								if(SurveyUtils.isDateFormatOk(args[1].replace("_", " "))){
									
									SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
									SimpleDateFormat dfMessage = new SimpleDateFormat("'§aL''apparition du sondage §b"+name+" §aest maintenant programée le§b' dd/MM/yyyy '§aà§b' HH:mm:ss");
									
									Date beggin=null;
									try {beggin = df.parse(args[1].replace("_", " "));} catch (ParseException e) {}
									
									TemporarySurvey TeSurvey = new TemporarySurvey().getSurvey(name);
									TeSurvey.setBeggin(beggin);
									
									s.sendMessage(dfMessage.format(beggin));
									
									
								}else{
									s.sendMessage("§cLa date doit être notée sous la forme 'jj/mm/aaaa_hh:mm:ss'");
								}
								
							}else{
								s.sendMessage("§e/sv setbeggin <jj/mm/aaaa_hh:mm:ss>");
							}
							
						}else{
							s.sendMessage("§cFonctionnalité reservée aux sondages temporaires (type TEMPORARY)");
						}					
					}else{
						s.sendMessage("§cVous devez séléctionner un sondage");
					}

					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("setduration")){
					
					if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
						
						String name = SurveyMain.getInstance().SurveySelection.get(p);
						
						if(SurveyUtils.isSurveyType("TEMPORARY",name)){
														
							if(args.length==2){
								
								if(SurveyUtils.isNumeric(args[1])){
									
									TemporarySurvey TeSurvey = new TemporarySurvey().getSurvey(name);
									TeSurvey.setDuration(Integer.parseInt(args[1]));
									
									s.sendMessage("§aLe durée d'apparition du sondage §b"+name+" §aest maintenant de §b"+args[1]+"h");
									
								}else{
									s.sendMessage("§cMerci d'entrer une durée en heures");
								}
								
							}else{
								s.sendMessage("§e/sv setduration <durée(h)>");
							}
							
						}else{
							s.sendMessage("§cFonctionnalité reservée aux sondages temporaires (type TEMPORARY)");
						}					
					}else{
						s.sendMessage("§cVous devez séléctionner un sondage");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("setdname")){
					
					if(args.length>1){
						
						if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
							
							String name = SurveyMain.getInstance().SurveySelection.get(p);
							Survey sv = SurveyUtils.getSurvey(name);
							
							StringBuilder sb = new StringBuilder();
							
							for(int i =1;i<args.length;i++){
								sb.append(args[i].replace("&", "§") + " ");
							}
							
							sv.setDisplayName(sb.toString());
							
							s.sendMessage("§aNom d'affichage du sondage §b"+name+" §achangé");
							
						}else{
							s.sendMessage("§cTu dois selectionner un sondage");
						}
						
					}else{
						s.sendMessage("§e/sv setdname <nom d'affichage>");
					}
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("qu")){
					
					if(args.length==1){
						
						s.sendMessage("§c");s.sendMessage("§c");
						s.sendMessage("§e===================================");
						s.sendMessage("§e/sv qu list");
						s.sendMessage("§e/sv qu add [ennoncé]");
						s.sendMessage("§e/sv qu select <numéro>");
						s.sendMessage("§c");
						s.sendMessage("§e/sv qu remove [numéro]");
						s.sendMessage("§e/sv qu setlabel <ennoncé>");
						s.sendMessage("§e===================================");
						
						
					}else{
						
						if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
							String name = SurveyMain.getInstance().SurveySelection.get(p);
							Survey sv = SurveyUtils.getSurvey(name);
							
							
							
							if(args[1].equalsIgnoreCase("list")){
								
								if(args.length==2){
									
									
									SurveyUtils.listQuestions(name,p);
									
								}else{
									s.sendMessage("§e/sv qu list");
								}
								
							}
							
							
							
							
							
							
							
							else if(args[1].equalsIgnoreCase("add")){
									
									Question qu = new Question(name);
									
									if(args.length>2){
										
										StringBuilder sb = new StringBuilder();
										
										for(int i =2;i<args.length;i++){
											sb.append(args[i].replace("&", "§") + " ");
										}
										qu.setLabel(sb.toString());
									}
									
									ArrayList<Question> questions = sv.getQuestions();
									questions.add(qu);
									sv.setQuestions(questions);
									
									
									

									s.sendMessage("§aQuestion ajoutée au sondage §b"+name);
								
							}
							
							
							
							
							
							
							
							else if(args[1].equalsIgnoreCase("select")){
								
								
								if(args.length==3){
									
								
									if(SurveyUtils.isNumeric(args[2])){
																				
										int questionNumber = Integer.parseInt(args[2]);
										
										if(SurveyUtils.isQuestionNumberOk(name,questionNumber)){
																								
											SurveyMain.getInstance().QuestionSelection.remove(p);
											SurveyMain.getInstance().QuestionSelection.put(p, questionNumber);
											
											s.sendMessage("§aQuestion §b"+questionNumber+" §adu sondage §b"+name+" §aséléctionnée");
											
										}else{
											s.sendMessage("§cMerci d'entrer un numéro de question correct");
										}

										
									}else{
										s.sendMessage("§cMerci d'entrer un numéro de question correct");
									}

									
								}else{
									s.sendMessage("§e/sv qu select <numéro>");
								}

								
								
							}
							
							
							
							
							
							
							
							else if(args[1].equalsIgnoreCase("remove")){
								
								if(args.length==2 || args.length==3){
									
									if(!(args.length==2 && !SurveyMain.getInstance().QuestionSelection.containsKey(p))){
										
										if(args.length==2){
											
											int QuN = SurveyMain.getInstance().QuestionSelection.get(p);
											
											ArrayList<Question> questions = sv.getQuestions();
											questions.remove(QuN-1);
											sv.setQuestions(questions);
											
											s.sendMessage("§aQuestion §b"+QuN+" §adu sondage §b"+name+" §asupprimée");
											
										}else{
											
											if(SurveyUtils.isNumeric(args[2])){											
												
												int QuN = Integer.parseInt(args[2]);
												
												if(SurveyUtils.isQuestionNumberOk(name,QuN)){
													
													ArrayList<Question> questions = sv.getQuestions();
													questions.remove(QuN-1);
													sv.setQuestions(questions);
													
													s.sendMessage("§aQuestion §b"+QuN+" §adu sondage §b"+name+" §asupprimée");
													
													
												}else{
													s.sendMessage("§cMerci d'entrer un numéro de question correct");
												}

											}else{
												s.sendMessage("§cMerci d'entrer un numéro de question correct");
											}


											
											
										}
										
									}else{
										s.sendMessage("§cTu dois selectionner ou préciser une question");
									}
									
								}else{
									s.sendMessage("§e/sv qu remove [numéro]");
								}
								
							}
							
							
							
							
							
							
							
							else if(args[1].equalsIgnoreCase("setlabel")){
								
								if(args.length>2){
									
									if(SurveyMain.getInstance().QuestionSelection.containsKey(p)){
										
										StringBuilder sb = new StringBuilder();
										
										for(int i =2;i<args.length;i++){
											sb.append(args[i].replace("&", "§") + " ");
										}
										
										int QuN = SurveyMain.getInstance().QuestionSelection.get(p);
										
										Question qu = sv.getQuestions().get(QuN-1);
										qu.setLabel(sb.toString());
										sv.saveSurvey();
										
										s.sendMessage("§aEnnoncé de la question §b"+QuN+" §adu sondage §b"+name+" §achangé");
										
									}else{
										s.sendMessage("§cTu dois selectionner une question");
									}
									
								}else{
									s.sendMessage("§e/sv qu setlabel <ennoncé>");
								}
								
							}
							
							
							
							
							
						}else{
							s.sendMessage("§cTu dois selectionner un sondage");
						}			
					}
				
					
				}
				
				
				
				
				
				
				
				else if(args[0].equalsIgnoreCase("aw")){
					
					
					if(args.length==1){
						
						s.sendMessage("§c");s.sendMessage("§c");
						s.sendMessage("§e===================================");
						s.sendMessage("§e/sv aw list");
						s.sendMessage("§e/sv aw add [ennoncé]");
						s.sendMessage("§e/sv aw remove <numéro>");
						s.sendMessage("§e/sv aw setlabel <numéro> <ennoncé>");
						s.sendMessage("§e===================================");
						
						
					}else{
						
						if(SurveyMain.getInstance().SurveySelection.containsKey(p)){
							String name = SurveyMain.getInstance().SurveySelection.get(p);
							Survey sv = SurveyUtils.getSurvey(name);
							
							if(SurveyMain.getInstance().QuestionSelection.containsKey(p)){
								int QuN = SurveyMain.getInstance().QuestionSelection.get(p);
								Question qu = sv.getQuestions().get(QuN-1);
								
								
								
								
								if(args[1].equalsIgnoreCase("list")){
									
									if(args.length==2){
										
										SurveyUtils.listAwnsers(name,QuN,p);										
									}else{
										s.sendMessage("§e/sv aw list");
									}
								}
								
								
								
								
								
								
								
								else if(args[1].equalsIgnoreCase("add")){
										
									Awnser aw = new Awnser(name,QuN);
									
									if(args.length>2){
										
										StringBuilder sb = new StringBuilder();										
										for(int i =2;i<args.length;i++){sb.append(args[i].replace("&", "§") + " ");}
										
										aw.setLabel(sb.toString());
										
									}
									aw.CompleteAwnser();
									
									ArrayList<Awnser> awnsers = qu.getAwnsers();
									awnsers.add(aw);
									qu.setAwners(awnsers);
									
									sv.saveSurvey();
									
									s.sendMessage("§aRéponse ajoutée à la question §b"+QuN+" §adu sondage §b"+name);
										
								}
								
								
								
								
								
								
								
								else if(args[1].equalsIgnoreCase("remove")){
									
									if(args.length==3){

										if(SurveyUtils.isNumeric(args[2])){
											
											int AwN = Integer.parseInt(args[2]);
											
											if(SurveyUtils.isAwnserNumberOk(name, QuN, AwN)){
												
												ArrayList<Awnser> awnsers = qu.getAwnsers();
												awnsers.remove(AwN-1);
												qu.setAwners(awnsers);
												
												sv.saveSurvey();
												
												s.sendMessage("§aRéponse §b"+AwN+" §asupprimée de la question §b"+QuN+" §adu sondage §b"+name);
												
											}
										}
											

										
									}else{
										s.sendMessage("§e/sv aw remove <numéro>");
									}
									
									
								}
								
								
								
								
								
								else if(args[1].equalsIgnoreCase("setlabel")){
									
									if(args.length>3){
										
										if(SurveyUtils.isNumeric(args[2])){
											
											int AwN = Integer.parseInt(args[2]);
											
											if(SurveyUtils.isAwnserNumberOk(name, QuN, AwN)){
																								
												StringBuilder sb = new StringBuilder();
												
												for(int i =3;i<args.length;i++){
													sb.append(args[i].replace("&", "§") + " ");
												}
												
												Awnser aw = sv.getQuestions().get(QuN-1).getAwnsers().get(AwN-1);
												aw.setLabel(sb.toString());
												
												ArrayList<Awnser> awnsers = qu.getAwnsers();
												awnsers.set(AwN-1,aw);
												qu.setAwners(awnsers);
												
												sv.saveSurvey();
												
												s.sendMessage("§aEnnoncé de la réponse §b"+AwN+" de la question §b"+QuN+" §adu sondage §b"+name+" §achangé");
												
											}else{
												s.sendMessage("§cMerci d'entrer un numéro de réponse correct");
											}
											
										}else{
											s.sendMessage("§cMerci d'entrer un numéro de réponse correct");
										}																	
									}else{
										s.sendMessage("§e/sv aw setlabel <numéro> <ennoncé>");
									}
									
								}
								
								
								
								
								
								
								
								
							}else{
								s.sendMessage("§cTu dois selectionner une question");
							}													
							
						}else{
							s.sendMessage("§cTu dois selectionner un sondage");
						}
					}

					
				}
				
				
			}
			
		}
		

		
		return false;
	}

}


// list create delete info clear select results open setdname qu aw

/*

/sv list

/sv results "nom"

/sv open "nom"

/sv create "nom" "display name"
/sv create "nom"

/sv delete "nom"
 
/sv info

/sv clear

/sv select "nom"


	/sv results
	
	/sv open
	
	/sv delete
	
	/sv setdname "displayname"
	
	/sv setbeggin (temporary type)
	
	/sv setduration (temporary type)
	
	/sv qu list
	
	/sv qu add "question"
	/sv qu add
	
	(/sv qu remove "numéro")
	
	/sv qu select "numéro"
	 
	 
	 
	 
	   
	   	/sv qu setlabel "label"
		
		/sv aw list
		
		/sv aw add "reponse"
		/sv aw add
		
		/sv aw remove "numéro"
		
		/sv aw setlabel "numéro" "label"


 */


