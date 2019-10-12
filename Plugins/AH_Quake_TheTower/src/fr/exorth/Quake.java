package fr.exorth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.QuakeAddSpawnCommand;
import fr.exorth.commands.QuakeForceStartCommand;
import fr.exorth.commands.QuakeLobbyCommand;
import fr.exorth.database.SqlConnection;
import fr.exorth.game.QuakeState;
import fr.exorth.scoreboard.ScoreboardRunnable;

public class Quake extends JavaPlugin{
	
	
	
	static Quake instance;
	public static SqlConnection sql;
	
	private String arenaname;
	
	public static Quake getInstance(){
		return instance;
	}
	
	public ArrayList<UUID> playerInGame = new ArrayList<>();
	public ArrayList<Player> ReloadingPlayers = new ArrayList<Player>();
	public HashMap<Player, Integer> kills = new HashMap<>();
	public HashMap<Player,Material> hoes = new HashMap<>();
	
	@Override
	public void onEnable() {
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		instance=this;
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		arenaname = getConfig().getString("database.arenaname");
		
        sql = new SqlConnection("jdbc:mysql://","localhost","quake","root","",arenaname);
        sql.connection();
        
        sql.setPlayers(0);
        
		QuakeState.setState(QuakeState.WAIT);
		EventsManager.registerEvents(this);
		new ScoreboardRunnable().runTaskTimer(this, 5L, 5L);		
		getCommand("addspawn").setExecutor(new QuakeAddSpawnCommand());
		getCommand("forcestart").setExecutor(new QuakeForceStartCommand());
		getCommand("lobby").setExecutor(new QuakeLobbyCommand());
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if(!QuakeState.isState(QuakeState.RESTARTING)){
			QuakeState.setState(QuakeState.RESTARTING);
		}
		
		sql.disconnect();
		super.onDisable();
	}

}
