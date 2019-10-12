package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.command.dreamsCommand;
import fr.exorth.event.JoinEvent;
import fr.exorth.SqlConnect;

public class DreamsGestion extends JavaPlugin implements Listener{

	static DreamsGestion instance;
	public SqlConnect sql;
	
	public static DreamsGestion getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance=this;
		
        sql = new SqlConnect("jdbc:mysql://","sql.franceserv.fr:3306","angelicheart_db1","angelicheart","Isabelle3445");
        sql.connection(); 
		
		getCommand("dreams").setExecutor(new dreamsCommand(sql));
		
		Bukkit.getPluginManager().registerEvents(new JoinEvent(sql), this);
		super.onEnable();
	}
	
	
	@Override
	public void onDisable() {
		sql.disconnect();
		super.onDisable();
	}
	

}
