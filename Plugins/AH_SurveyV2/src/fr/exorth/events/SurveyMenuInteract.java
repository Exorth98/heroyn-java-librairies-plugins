package fr.exorth.events;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Survey;
import fr.exorth.util.SurveyOpen;
import fr.exorth.util.SurveyUtils;

public class SurveyMenuInteract implements Listener {
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent e) {
		
		if(e.getSlotType() != SlotType.OUTSIDE) {
			
			Player p = (Player) e.getWhoClicked();
			
			if(e.getClickedInventory().getName().contains("§0Sondage |")) {
				
				Survey sv;
				int QuN;
				
				int AwN = ((e.getSlot()-18)/2)+1;
				
				Date now = new Date();
				Date before = SurveyMain.getInstance().DatesforTimes.get(p);
				
				int time = (int) (now.getTime() - before.getTime());
				
				if(SurveyMain.getInstance().inSurvey.containsKey(p)) {
					
					sv = SurveyMain.getInstance().inSurvey.get(p);
					QuN = SurveyMain.getInstance().inSurveyAwnsers.get(p).size()+1;		
					
					int CalibrationTime = SurveyUtils.getCalibrationTime(sv,QuN);
					
					double score = SurveyUtils.getScore(time,CalibrationTime);
					
					String line = Integer.toString(QuN) + "/" + Integer.toString(AwN) + "/" + Double.toString(score);
					SurveyMain.getInstance().inSurveyAwnsers.get(p).add(line);
					
				}
				else /*SurveyMain.getInstance().inSurveyCalibration.containsKey(p)*/ {
					
					sv = SurveyMain.getInstance().inSurveyCalibration.get(p);
					QuN = SurveyMain.getInstance().inSurveyCalibrationTimes.get(p).size()+1;
					
					String line = Integer.toString(QuN) + "/" + Integer.toString(time);
					SurveyMain.getInstance().inSurveyCalibrationTimes.get(p).add(line);
				}
				
				
				
				if(QuN!=sv.getQuestions().size()) {
					
					SurveyMain.getInstance().DatesforTimes.remove(p);
					SurveyOpen.OpenQuestion(p, sv, QuN+1);
					
					
				}else { //C'était la derniere question
					
					if(SurveyMain.getInstance().inSurvey.containsKey(p)) {
						
						FileConfiguration cfg = SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Res").getCustomConfig();
						
						ArrayList<String> AwnsersInfos = SurveyMain.getInstance().inSurveyAwnsers.get(p);	
						
						double reliabilityS=0.0;
						
						for(String AwnserInfos : AwnsersInfos) {
							
							String [] Infos = AwnserInfos.split("/");
							
							int QuestN = Integer.parseInt(Infos[0]);
							int AwnsN = Integer.parseInt(Infos[1]);
							double Score = Double.parseDouble(Infos[2]);
							
							reliabilityS+=Score;
							
							double actualScore = cfg.getDouble("Question "+QuestN+"."+"Awnser "+AwnsN);
							double newScore = actualScore+Score;
							
							cfg.set("Question "+QuestN+"."+"Awnser "+AwnsN, newScore);
						}
						
						Double reliability = (reliabilityS/sv.getQuestions().size())*100;
						
						NumberFormat nf = new DecimalFormat("0.##");
						String rel = nf.format(reliability);
						
						SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Res").saveCustomConfig();
						
						SurveyMain.getInstance().inSurvey.remove(p);
						SurveyMain.getInstance().inSurveyAwnsers.remove(p);
						
						Bukkit.broadcastMessage("§b"+p.getName()+" §aà répondu au sondage §b"+sv.getDisplayName());
						p.sendMessage("§bFiabilité : "+rel+"%");
					}
					
					else /*SurveyMain.getInstance().inSurveyCalibration.containsKey(p)*/ {
						
						
						FileConfiguration cfg = SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Cal").getCustomConfig();
						
						ArrayList<String> CalsInfos = SurveyMain.getInstance().inSurveyCalibrationTimes.get(p);	
						
						for(String CalInfos : CalsInfos) {
							
							String [] Infos = CalInfos.split("/");
							
							int QuestN = Integer.parseInt(Infos[0]);
							int CalTime = Integer.parseInt(Infos[1]);
							
							if(cfg==null) {
								Bukkit.broadcastMessage("§c cfg null");
							}
							
							List<Integer> CalTimes = cfg.getIntegerList("Question "+QuN);
							CalTimes.add(CalTime);
							cfg.set("Question "+QuestN, CalTimes);
							
						}
						
						SurveyMain.getInstance().ConfigManagers.get(sv.getName()+"Cal").saveCustomConfig();
						
						SurveyMain.getInstance().inSurveyCalibration.remove(p);
						SurveyMain.getInstance().inSurveyCalibrationTimes.remove(p);
						
						p.sendMessage("§aCalibrage terminé et bien pris en compte");
						
					}
					
					//fin du sondage
					p.closeInventory();
					SurveyMain.getInstance().DatesforTimes.remove(p);

					
				}

				
				
				
				
			}
			
			
		}
		
	}
	
	

}
