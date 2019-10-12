package fr.exorth;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.game.UHCState;
import fr.exorth.scoreboards.ScoreboardRunnable;
import fr.exorth.util.Bordures;

public class UHCRun extends JavaPlugin{
	
	
	public static UHCRun instance;
	
	//Liste des joueurs en jeu
	public ArrayList<UUID> playerInGame = new ArrayList<>();
	
	public static UHCRun getInstance(){
		return instance;
		
	}
	
	@Override
	public void onEnable() {
		instance=this;
		super.onEnable();
		
		EventsManager.registerEvents(this);
		
		UHCState.setState(UHCState.WAIT);
		
		Bukkit.getWorld("world").setPVP(false);
		
		new Bordures().setBorder(1500.0);
		
		new ScoreboardRunnable().runTaskTimer(this, 20L, 20L);
	}

}
