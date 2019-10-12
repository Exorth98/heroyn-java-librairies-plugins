package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;





public class Main extends JavaPlugin {
	
	static Main instance;
	public SqlConnection sql;
	
	public static Main getInstance(){
		return instance;
	}
	
	
	@Override
	public void onEnable() {
		
        sql = new SqlConnection("jdbc:mysql://","localhost","quake","root","");
        sql.connection();        
        
		instance=this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Bukkit.getPluginManager().registerEvents(new SignEvent(sql), this);
		Bukkit.getPluginManager().registerEvents(new SignClickEvent(), this);
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		getCommand("lobby").setExecutor(new QuakeLobbyCommand());
	    
	    
		
		new SignRefreshRunnable(sql).runTaskTimer(this, 5L, 5L);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		sql.disconnect();
		super.onDisable();
	}

}
