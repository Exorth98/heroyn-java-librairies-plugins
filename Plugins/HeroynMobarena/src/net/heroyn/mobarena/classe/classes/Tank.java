package net.heroyn.mobarena.classe.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.heroyn.mobarena.classe.ClasseBase;

public class Tank extends ClasseBase{

	public Tank(Player player, Integer level) {
		super(player, level, "Tank");
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
