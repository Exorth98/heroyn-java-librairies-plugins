package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Awnser;
import fr.exorth.surveys.Question;
import fr.exorth.surveys.Survey;
import fr.exorth.util.SurveyUtils;

public class SurveyEditCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String msg, String[] args) {
		
		if(s instanceof Player){
			
			Player p = (Player) s;
			
			if(args[0].equalsIgnoreCase("remove")){
				
				String name = args[1];
				Survey sv = SurveyUtils.getSurvey(name);
				
				int QuN = Integer.parseInt(args[2]);
				Question qu = sv.getQuestions().get(QuN-1);
				
				if(args.length==3){
					
					ArrayList<Question> questions = sv.getQuestions();
					questions.remove(QuN-1);
					sv.setQuestions(questions);
					sv.saveSurvey();
					
				}else{
					
					int AwN = Integer.parseInt(args[3]);
					
					ArrayList<Awnser> awnsers = qu.getAwnsers();
					awnsers.remove(AwN-1);
					qu.setAwners(awnsers);
					
					sv.saveSurvey();
					
				}
				
				sv.Display(p);
				
			}
			else if(args[0].equalsIgnoreCase("add")){
				
				String name = args[1];
				Survey sv = SurveyUtils.getSurvey(name);
				
				if(args.length==2){
					
					Question qu = new Question(name);
					ArrayList<Question> questions = sv.getQuestions();
					questions.add(qu);
					sv.setQuestions(questions);
					sv.saveSurvey();
					
				}else{
					
					int QuN = Integer.parseInt(args[2]);
					Question qu = sv.getQuestions().get(QuN-1);
					
					Awnser aw = new Awnser(name,QuN);
					
					aw.CompleteAwnser();
					
					ArrayList<Awnser> awnsers = qu.getAwnsers();
					awnsers.add(aw);
					qu.setAwners(awnsers);
					
					sv.saveSurvey();
					
				}
				
				sv.Display(p);
				
			}
			
			
			
			
			
			
			else{
				
				if(args.length==2 || args.length== 3){
					
					if(SurveyUtils.surveyExist(args[0])){
						
						String name = args[0];
						
						if(SurveyUtils.isNumeric(args[1])){
							
							int QuN = Integer.parseInt(args[1]);
							
							if(SurveyUtils.isQuestionNumberOk(name, QuN)){
														
								
								if(args.length==3){
									
									if(SurveyUtils.isNumeric(args[2])){
										
										int AwN = Integer.parseInt(args[2]);
										
										if(SurveyUtils.isAwnserNumberOk(name, QuN, AwN)){
											
											SurveyMain.getInstance().OnEdit.put(p, args);
											p.sendMessage("§aEntre l'énoncé dans le chat:");
											p.sendMessage("§eentre -c pour annuler");
											
										}
										
									}

								}else{
																	
									SurveyMain.getInstance().OnEdit.put(p, args);
									p.sendMessage("§aEntre l'énoncé dans le chat:");
									p.sendMessage("§eentre -c pour annuler");
									
								}				
								
							}
							
						}
						
						
						
					}
					
					
				}
				
			}
				
			
		}
		
		
		return false;
	}

}
