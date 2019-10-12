package net.heroyn.mobarena.game.player;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.utils.Arena;

public class MobarenaSuitingupPlayer extends InMobarenaPlayer{
	
	ClasseBase SelectionnedmaC;
	
	public MobarenaSuitingupPlayer(Player p, Arena ar, ClasseBase maC) {
		
		super(p,ar);
		this.SelectionnedmaC = maC;		
	}
	public MobarenaSuitingupPlayer(Player p, Arena ar) {
		
		super(p,ar);
	}
	
	
	public ClasseBase getSelectionnedCharacter() {
		return this.SelectionnedmaC;
	}
	public void setSelectionnedCharacter(ClasseBase SelectionnedmaC) {
		if(this.SelectionnedmaC != SelectionnedmaC) {
			
			this.SelectionnedmaC = SelectionnedmaC;
			p.sendMessage("§aClasse §b" + SelectionnedmaC.getName() +" (Level "+SelectionnedmaC.getLevel()+") "+ " §aselectionnée" );
			visualizeEquipement();
			
		}else {
			p.sendMessage("§cTu as déjà selectionné cette classe");
		}

	}


	public static MobarenaSuitingupPlayer getFromPlayer(Player p) {
		
		MobarenaSuitingupPlayer maSupP = null ;
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaSuitingupPlayer maSuitingupP : game.getSuitingupPlayers()) {
				
				if(maSuitingupP.getPlayer().equals(p)) maSupP = maSuitingupP;				
			}		
		}
		
		return maSupP;
	}
	
	public void visualizeEquipement() {
		
		if(this.SelectionnedmaC != null) {
			p.getInventory().clear();
		    for (PotionEffect effect : p.getActivePotionEffects())
		        p.removePotionEffect(effect.getType());
		}

		
		SelectionnedmaC.sendKitPlayer();
		SelectionnedmaC.setupEffects();
		SelectionnedmaC.setupHealth();
		
	}

}
