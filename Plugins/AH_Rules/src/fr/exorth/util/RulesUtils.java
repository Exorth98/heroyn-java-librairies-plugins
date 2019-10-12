package fr.exorth.util;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Rules;

public class RulesUtils {
	
	static FileConfiguration config = Rules.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> rules = (ArrayList<String>) config.get("Rules");

	public static void sendRules(Player p) {
		
		if(rules==null){rules = new ArrayList<>();}
		
		for(String rule : rules){
						
			p.sendMessage(rule.replace("&", "§"));
		}
		

			
		
	}

}
