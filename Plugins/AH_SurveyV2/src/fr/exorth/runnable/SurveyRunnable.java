package fr.exorth.runnable;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.surveys.TemporarySurvey;
import fr.exorth.util.SurveyUtils;

public class SurveyRunnable extends BukkitRunnable{

	@Override
	public void run() {
		
		
		ArrayList<TemporarySurvey> surveys = SurveyUtils.getTemporarySurveys();
		
		for(TemporarySurvey sv : surveys) {
			
			long now = new Date().getTime();
			long beggin = sv.getBeggin().getTime();
			long end = beggin + (sv.getDuration()*60*60*1000);
			
			if(beggin<=now && now<end && !sv.isVisible()) {
				sv.setVisibility(true);
			}
			
			if(beggin>now && sv.isVisible()) {
				sv.setVisibility(false);
			}
			if(end<=now && sv.isVisible()) {
				sv.setVisibility(false);
			}
		}
		
	}
	
	

}
