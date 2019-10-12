package fr.exorth.board;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.board.CustomScoreboardManager;

public class Board extends JavaPlugin implements Listener{
	
	public HashMap<Player, CustomScoreboardManager> sb = new HashMap<>();
	public static Board instance;
	public static Board getInstance(){
		return instance;
	}
	

	public void onEnable() {
		instance = this;
		new ScoreboardRunnable().runTaskTimer(this,0L,2L);
		getServer().getPluginManager().registerEvents(this,this);
		super.onEnable();
		
	}
	
	
	@EventHandler
	public void Onjoin(PlayerJoinEvent event){		
		Player player = event.getPlayer();	
		CustomScoreboardManager board = new CustomScoreboardManager(player);
		board.sendLine();
		board.setScoreboard();
	}

}
