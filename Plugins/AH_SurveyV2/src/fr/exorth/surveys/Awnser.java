package fr.exorth.surveys;

import fr.exorth.util.SurveyUtils;

public class Awnser {
	
	
	String surveyName;
	int questionNumber;
	String label;
	
	public Awnser(String surveyName, int questionNumber) {
		
		this.surveyName=surveyName;
		this.questionNumber=questionNumber;
		
	}
	
	public Awnser(String surveyName, int questionNumber, String label) {
		
		this.surveyName=surveyName;
		this.questionNumber=questionNumber;
		this.label=label;
		
	}
	
	public void CompleteAwnser(){
		
		if(label==null){label="[X] Réponse ici";}
		
	}
	
	public void saveAwnser(){
		
		///
		
	}
	
	public void removeAwnser(){
		
		///
		
	}
	
	public String getSurveyName(){
		
		return this.surveyName;
		
	}
	
	public int getQuestionNumer(){
		
		return this.questionNumber;
		
	}
	
	public String getLabel(){
		
		if(this.label==null){ return "[X] Réponse ici";}
		return this.label;
		
	}
	
	public void setLabel(String label){
		
		this.label = label.replace("&", "§");
		
	}
	
	public int getNumber(){
		
		Question qu = SurveyUtils.getSurvey(surveyName).getQuestions().get(questionNumber);
		return qu.getAwnserNumber(this);
		
	}
	

}
