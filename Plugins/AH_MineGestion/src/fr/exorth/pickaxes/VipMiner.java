package fr.exorth.pickaxes;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.MineGestion;

public class VipMiner {
	
	FileConfiguration prefs = MineGestion.getInstance().cfgmP.getCustomConfig();
	
	UUID owner;
	
	public VipMiner(Player p) {
		
		this.owner=p.getUniqueId();
	}
	
	public void setPref(String pref){
		
		prefs.set(owner.toString(), pref);
		MineGestion.getInstance().cfgmP.saveCustomConfig();
		
	}
	
	public boolean hasPref(){
		
		return prefs.get(owner.toString()) !=null;
		
	}
	
	public String getPref(){
		
		if(!hasPref()){
			setPref("rent");
			return "rent";
		}else{
			
			String pref = (String) prefs.get(owner.toString());
			return pref;
			
		}
				

		
	}
	
	
}
