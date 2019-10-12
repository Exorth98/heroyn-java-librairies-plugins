package fr.exorth.surveys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.SurveyConfigManager;
import fr.exorth.SurveyMain;
import fr.exorth.util.SurveyUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TemporarySurvey implements Survey{

	
	String name;
	String displayName;
	boolean visibility;
	
	ArrayList<Question> questions;
	
	Date beggin;
	int duration;
	
	FileConfiguration cfg;
	
	
	public TemporarySurvey() {}
	
	public TemporarySurvey(String name, String displayName) {
		
		this.name=name;
		this.displayName=displayName;
		
	}
	
	public TemporarySurvey(String name) {
		
		this.name=name;
		
	}
	
	public TemporarySurvey(String name, String displayName, ArrayList<Question> questions) {
		
		this.name=name;
		this.displayName=displayName;
		this.questions=questions;
		
	}
	
	public TemporarySurvey(String name, String displayName, ArrayList<Question> questions, Date beggin) {
		
		this.name=name;
		this.displayName=displayName;
		this.questions=questions;
		this.beggin=beggin;
		
	}
	
	public TemporarySurvey(String name, String displayName, ArrayList<Question> questions, boolean visibility, Date beggin, int duration) {
		
		this.name=name;
		this.displayName=displayName;
		this.questions=questions;
		this.beggin=beggin;
		this.duration=duration;
		this.visibility=visibility;
		
		this.cfg = SurveyMain.getInstance().ConfigManagers.get(name).getCustomConfig();
		
	}
	
	public TemporarySurvey getSurvey(String name){
		
		FileConfiguration cfg = SurveyMain.getInstance().ConfigManagers.get(name).getCustomConfig();
		
		String dName = (String) cfg.get("DisplayName");
		
		boolean visibility = cfg.getBoolean("Visibility");
		
		ArrayList<Question> questions = new ArrayList<>();
		
		int max;
		
		if(cfg.getConfigurationSection("Questions")!=null){
			max = cfg.getConfigurationSection("Questions").getKeys(false).size();
		}else{
			max=0;
		}
		
		for(int i=1;i<=max;i++){
			
			String label = (String) cfg.get("Questions."+i+".Label");
			
			ArrayList<Awnser> awnsers = new ArrayList<>();	
			
			@SuppressWarnings("unchecked")
			ArrayList<String> awns = (ArrayList<String>) cfg.get("Questions."+i+".Awnsers");	
			
			if(awns==null){awns= new ArrayList<String>();}
			
			for(String awn : awns){
				
				Awnser awner = new Awnser(name,i,awn);
				awnsers.add(awner);
				
			}
			
			Question question = new Question(name,label,awnsers);
			questions.add(question);
			
		}
		
		String begginS = (String) cfg.get("Beggin");
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date beggin=null;
		try {
			beggin = df.parse(begginS);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int duration = cfg.getInt("Duration");
		
		TemporarySurvey sv = new TemporarySurvey(name,dName,questions,visibility,beggin,duration);
		
		return sv;
		
	}
	
	
	
	@Override
	public String getName() {
		
		return this.name;
	}

	@Override
	public String getDisplayName(){
		
		if(displayName==null){return this.name;}
		return this.displayName;
		
	}

	@Override
	public void setDisplayName(String displayname) {
		
		this.displayName=displayname.replace("&", "§").replace("_", " ");
		saveSurvey();
		
	}

	@Override
	public ArrayList<Question> getQuestions(){
		
		if(questions==null){return new ArrayList<Question>();}
		return this.questions;
		
		
	}

	@Override
	public void setQuestions(ArrayList<Question> questions) {
		
		this.questions=questions;
		saveSurvey();
		
	}

	@Override
	public void completeSurvey(){
		
		if(displayName==null){displayName=name;}
		if(questions==null){questions= new ArrayList<Question>();}
		if(beggin==null){beggin = new Date();}
		if(duration==0){duration=720;}
		
		if(!SurveyMain.getInstance().ConfigManagers.containsKey(name)){
			SurveyConfigManager cfgm = new SurveyConfigManager("TEMPORARY",name);
			cfgm.setupCustomConfig();
			cfgm.saveCustomConfig();
			cfgm.reloadCustomConfig();
			
			SurveyMain.getInstance().ConfigManagers.put(name, cfgm);
		}
		
		SurveyUtils.saveSurvey(name,"TEMPORARY");
		
	}

	@Override
	public void saveSurvey() {
		
		completeSurvey();
		
		FileConfiguration cfg = SurveyMain.getInstance().ConfigManagers.get(name).getCustomConfig();
		
		cfg.set("DisplayName", displayName);
		cfg.set("Duration", duration);
		cfg.set("Visibility", visibility);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String begginS = df.format(beggin);
		
		cfg.set("Beggin", begginS);
		
		cfg.set("Questions", null);
		
		int i =1;
		for(Question qu : questions){	
			
			qu.saveQuestion(cfg,i);
			i++;
		}
		
		SurveyMain.getInstance().ConfigManagers.get(name).saveCustomConfig();
		
	}

	@Override
	public void removeSurvey(){
		
		SurveyUtils.removeSurvey(name);		
	}
	
	
	public Date getBeggin(){
		
		if(beggin==null){beggin = new Date();}
		return this.beggin;
	}
	
	public void setBeggin(Date beggin){
		
		this.beggin=beggin;
		saveSurvey();
	}
	
	public int getDuration(){
		
		if(duration==0){duration=720;}
		return this.duration;		
	}
	
	public void setDuration(int duration){
		
		this.duration=duration;
		saveSurvey();
	}

	@Override
	public void Display(Player p){
		
		//clear chat
		for(int i =0;i<100;i++){
			p.sendMessage("§c");
		}
		
		//entête
		p.sendMessage("§6===== Sondage "+displayName+" §6=====");
		p.sendMessage("§c");
		
		
		for (int QuN=1;QuN<=questions.size();QuN++){
			
			Question qu = questions.get(QuN-1);
			
			TextComponent tcqu = new TextComponent("- "+qu.getLabel());
			tcqu.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit "+name+" "+QuN));
			tcqu.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§aChanger l'énoncé").create()));			
			
			TextComponent tcqu2 = new TextComponent("          §c[X]");
			tcqu2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit remove "+name+" "+QuN));
			tcqu2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§cSupprimer la question").create()));
			
			tcqu.addExtra(tcqu2);
			
			p.spigot().sendMessage(tcqu);
			
			for(int AwN = 1; AwN <= qu.getAwnsers().size();AwN++){
				
				Awnser aw = qu.getAwnsers().get(AwN-1);
				
				TextComponent tcaw = new TextComponent("   - "+aw.getLabel());
				tcaw.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit "+name+" "+QuN+" "+AwN));
				tcaw.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§aChanger l'énoncé").create()));			
				
				TextComponent tcaw2 = new TextComponent("          §c[X]");
				tcaw2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit remove "+name+" "+QuN+" "+AwN));
				tcaw2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§cSupprimer la réponse").create()));
				
				tcaw.addExtra(tcaw2);
				
				p.spigot().sendMessage(tcaw);
				
			}
			
			TextComponent tcawp = new TextComponent("   - §a[+]");
			tcawp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit add "+name+" "+QuN));
			tcawp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§aAjouter une réponse").create()));
			
			p.spigot().sendMessage(tcawp);
			
		}
		
		TextComponent tcqup = new TextComponent("- §a[+]");
		tcqup.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/svedit add "+name));
		tcqup.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§aAjouter une question").create()));
		
		p.spigot().sendMessage(tcqup);
		
	}

	@Override
	public void setVisibility(boolean visibility) {
		
		this.visibility=visibility;
		saveSurvey();
		
	}

	@Override
	public boolean isVisible() {

		return visibility;
	}

}
