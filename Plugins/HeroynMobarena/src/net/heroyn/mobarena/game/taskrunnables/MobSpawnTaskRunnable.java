package net.heroyn.mobarena.game.taskrunnables;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;

import io.netty.util.internal.ThreadLocalRandom;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.mobs.MaMobStats;
import net.heroyn.mobarena.mobs.MobarenaMob;

public class MobSpawnTaskRunnable implements Runnable {

	MobarenaGame game;
	
	int timer;
	int wave;
	int mobNumber;
	List<MaMobStats> mobs;
	
	
	public MobSpawnTaskRunnable(MobarenaGame game) {	
		this.game = game;
	}
	




	@Override
	public void run() {
		
		//DATAS ACTUALIZATION
		game.addWave();
		this.timer = game.getTimer();
		this.wave = game.getWave();
		this.mobNumber = getMobNumber();
		this.mobs = getMobs();

		List<Location> locs = getIntelligentSpawnPoints();
		int size = locs.size();
		int diff = getDiff();
		
		int mobPerSpawn = mobNumber/(size - diff);
		
		for(int i = 0; i< size - diff; i++) {
			
			Location loc = locs.get(i);
			
			for(int j = 0; j<mobPerSpawn; j++) {				
				
				MaMobStats mob = getRandomMob();
				try {new MobarenaMob(game, mob).SpawnMaMob(loc);					
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException | SecurityException e) {e.printStackTrace();}

			}					
		}		
	}
	
	private int getDiff() {
		
		int size = getIntelligentSpawnPoints().size();
		int diff = 0;
		
		while(mobNumber/(size - diff) <1)diff++;
		
		return diff;
	}





	private MaMobStats getRandomMob() {
			
		int i = ThreadLocalRandom.current().nextInt(0, mobs.size());	
		return mobs.get(i);	
	}





	private List<Location> getIntelligentSpawnPoints() {

		List<Location> allSpawnPoints = game.getArena().getSpawnPoints();
		List<Location> SelectionedPoints = new ArrayList<>();
		
		for(Location loc : allSpawnPoints) {		
			if(getNearPlayers(loc) >0)SelectionedPoints.add(loc);			
		}
		
		return (SelectionedPoints.size() > 0) ? SelectionedPoints : allSpawnPoints;
	}
	

	private int getNearPlayers(Location loc) {
		
		int count =0;
		int radius = 20; //(blocs)
		
		for(MobarenaFighter maF : game.getFighters()) {
			
			Location fLoc = maF.getPlayer().getLocation();
			
			if(Math.sqrt( Math.pow( loc.getX() - fLoc.getX(), 2) + Math.pow( loc.getX() - fLoc.getX(), 2) + Math.pow( loc.getX() - fLoc.getX(), 2) ) < radius)
				count++;
			
		}
		return count;
	}

	
	public List<MaMobStats> getMobs(){
		
		List<MaMobStats> mobs = new ArrayList<>();
		
		for(MaMobStats stats : MaMobStats.values())
			for(int i = 0; i< stats.getSpawnAmount(wave);i++)
				mobs.add(stats);

		Collections.shuffle(mobs);Collections.shuffle(mobs);Collections.shuffle(mobs);
		return mobs;		
	}
	
	private int getMobNumber() {
		
		int minutes = timer/60;
		int supMobs = minutes/5; // UN MOB SPLEMENTAIRE PAR JOUEUR TOUTES LES 5 MINUTES
		
		return game.getFighters().size() *(2+supMobs) ;
	}

}
