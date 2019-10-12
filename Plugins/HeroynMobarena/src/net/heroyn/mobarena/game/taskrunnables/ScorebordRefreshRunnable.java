package net.heroyn.mobarena.game.taskrunnables;

import org.bukkit.entity.Player;

import net.heroyn.mobarena.game.MobarenaGame;

public class ScorebordRefreshRunnable implements Runnable {

	private MobarenaGame game;
	
	public ScorebordRefreshRunnable(MobarenaGame game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		
		for(Player p : game.getScorboards().keySet()) {
			
			game.updateScoreboard(p);
		}
		

	}

}
