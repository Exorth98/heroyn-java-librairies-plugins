package net.heroyn.mobarena.classe.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.heroyn.mobarena.classe.ClasseBase;

public class Knight extends ClasseBase{

	public Knight(Player player, Integer level) {
		super(player, level, "Knight");
	}

	@Override
	protected void setUpSkill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setRegain() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inflictDamageByEntityEvent(EntityDamageByEntityEvent event) {
		// TODO Auto-generated method stub
		
	}

}
