package net.heroyn.mobarena.game.player;

import org.bukkit.entity.Player;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.utils.Arena;

public class MobarenaFighter extends InMobarenaPlayer{
	

	double score;
	ClasseBase clas;
	
	public MobarenaFighter(Player p, Arena ar, ClasseBase clas) {
		
		super(p, ar);
		this.clas = clas;
		this.score = 0.0;	
	}

	public double getScore() {
		return this.score;
	}
	public ClasseBase getClasse() {
		return this.clas;
	}
	
	public void setClasse(ClasseBase classe) {
		
		this.clas = classe;
	}


	public static MobarenaFighter getFromPlayer(Player p) {
		
		MobarenaFighter maFighter = null ;
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaFighter maF : game.getFighters()) {
				
				if(maF.getPlayer().equals(p)) maFighter = maF;				
			}		
		}		
		return maFighter;
	}


	public void suitUp() {
		
		ClasseBase maC = (ClasseBase) this.clas;
		maC.sendKitPlayer();
		maC.setupEffects();
		maC.setupHealth();
	}

	
	
	

}
