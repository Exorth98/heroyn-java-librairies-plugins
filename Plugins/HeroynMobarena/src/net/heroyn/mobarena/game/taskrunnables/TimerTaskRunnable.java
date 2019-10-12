package net.heroyn.mobarena.game.taskrunnables;

import net.heroyn.mobarena.game.MobarenaGame;

public class TimerTaskRunnable implements Runnable {

	MobarenaGame game;
	
	
	public TimerTaskRunnable(MobarenaGame game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		
		this.game.timerAdd();
		
	}

}
