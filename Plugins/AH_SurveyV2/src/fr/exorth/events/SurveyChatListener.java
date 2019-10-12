package fr.exorth.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.exorth.SurveyMain;
import fr.exorth.surveys.Awnser;
import fr.exorth.surveys.Question;
import fr.exorth.surveys.Survey;
import fr.exorth.util.SurveyUtils;

public class SurveyChatListener implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		
		if(SurveyMain.getInstance().OnEdit.containsKey(p)){
			
			e.setCancelled(true);
			
			String [] args = SurveyMain.getInstance().OnEdit.get(p);
			String name = args[0];
			Survey sv = SurveyUtils.getSurvey(name);
			
			String msg = e.getMessage();
			
			if(msg.equalsIgnoreCase("-c")){
				
				p.sendMessage("§cVous avez annulé");
				SurveyMain.getInstance().OnEdit.remove(p);
				//SurveyUtils.getSurvey(name).Display(p);
				
			}else{
				

				int QuN = Integer.parseInt(args[1]);
				
				Question qu = sv.getQuestions().get(QuN-1);
				
				if(args.length==3){
					
					int AwN = Integer.parseInt(args[2]);
					Awnser aw = qu.getAwnsers().get(AwN-1);
					
					aw.setLabel(msg.replace("&", "§"));
					
					ArrayList<Awnser> awnsers = qu.getAwnsers();
					awnsers.set(AwN-1,aw);
					qu.setAwners(awnsers);
					
					
					
				}else{
					
					qu.setLabel(msg.replace("&", "§"));
					
					
				}
				
				sv.saveSurvey();
				sv.Display(p);
				SurveyMain.getInstance().OnEdit.remove(p);
			}
			
			
		}
		
		
	}

}
