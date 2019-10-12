package fr.exorth.surveys;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import fr.exorth.SurveyMain;

public class Question {
	
	String surveyName;
	String label;
	
	ArrayList<Awnser> awnsers;
	
	FileConfiguration cfg;
	
	public Question(String surveyName) {
		
		this.surveyName=surveyName;
		
	}
	
	public Question(String surveyName,String label, ArrayList<Awnser> awnsers) {
		
		this.surveyName=surveyName;
		this.label=label;
		this.awnsers=awnsers;
		
		this.cfg = SurveyMain.getInstance().ConfigManagers.get(surveyName).getCustomConfig();
		
	}
	
	public Question getQuestion(String surveyName,int QuN){
		
		String label = (String) cfg.get("Questions."+QuN+".Label");
		
		ArrayList<Awnser> awnsers = new ArrayList<>();			
		@SuppressWarnings("unchecked")
		ArrayList<String> awns = (ArrayList<String>) cfg.get("Questions."+QuN+".Awnsers");			
		for(String awn : awns){
			
			Awnser awner = new Awnser(surveyName,QuN,awn);
			awnsers.add(awner);
			
		}
		
		Question question = new Question(surveyName,label,awnsers);
		
		return question;
	}
	
	public String getSurveyName(){
		
		return this.surveyName;
		
	}
	
	public String getLabel(){
		
		if(this.label==null){ return "[X] Question ici";}
		return this.label;
		
	}
	
	public void setLabel(String label){
		
		this.label = label.replace("&", "§");
		//saveQuestion();
		
	}
	
	public void CompleteQuestion(){
		
		if(this.label==null){this.label="[X] Question ici";}
		if(this.awnsers==null){this.awnsers= new ArrayList<Awnser>();}
		
	}

	/*public void saveQuestion() {
		
		CompleteQuestion();
		
		int QuN = getNumber();
		
		ArrayList<String> awns = new ArrayList<>();
		for(Awnser awnser : this.awnsers){
			
			awns.add(awnser.getLabel());
		}
		
		cfg.set("Questions."+QuN+".Label", this.label);
		cfg.set("Questions."+QuN+".Awnsers", awns);
		
	}*/
	
	public void saveQuestion(FileConfiguration cfg2, int QuN) {
		
		CompleteQuestion();
		
		ArrayList<String> awns = new ArrayList<>();
		for(Awnser awnser : this.awnsers){
			
			awns.add(awnser.getLabel());
		}
		
		cfg2.set("Questions."+QuN+".Label", this.label);
		cfg2.set("Questions."+QuN+".Awnsers", awns);
		
	}
	
	public void removeQuestion() {
		
		///
		
		
	}
	
	public void addAwnser(Awnser a){
		
		this.awnsers.add(a);
		//saveQuestion();
	}
	
	public void removeAwnser(Awnser a){
		
		this.awnsers.remove(a);
		//saveQuestion();
	}
	
	public ArrayList<Awnser> getAwnsers(){
		
		if(this.awnsers==null){return new ArrayList<Awnser>();}
		return this.awnsers;
		
	}
	
	public void setAwners(ArrayList<Awnser> awnsers){
		this.awnsers=awnsers;
	}

	public int getAwnserNumber(Awnser awnser) {
		
		for(int i=0; i<this.awnsers.size();i++){
			
			if(this.awnsers.get(i).equals(awnser)){
				
				return i+1;
			}
			
		}
		
		
		return 0;
	}


	

}
