package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.NfsCommand;
import fr.exorth.events.npcEvent;

public class NPCforShop extends JavaPlugin{
	
	static NPCforShop instance;

	
	public static NPCforShop getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {		
		
	    instance=this;	
		
	    getConfig().options().copyDefaults(true);
	    saveConfig();
	    
	    getCommand("nfs").setExecutor(new NfsCommand());
	    Bukkit.getPluginManager().registerEvents(new npcEvent(), this);
	    
		super.onEnable();
	}
	
	
	

}
