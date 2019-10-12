package fr.exorth.surveys;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public interface Survey {
	
	
	//public abstract Survey getSurvey(String name);
	
	public abstract String getName();
	
	
	
	public abstract String getDisplayName();
	
	public abstract void setDisplayName(String displayname);
	
	public abstract void setVisibility(boolean visibility);
	
	public abstract boolean isVisible();
	
	
	
	public abstract ArrayList<Question> getQuestions();
	
	public void setQuestions(ArrayList<Question> questions);
	
	
	
	
	public void completeSurvey();
	
	public abstract void saveSurvey();
	
	public abstract void removeSurvey();



	void Display(Player p);
	

	

}
