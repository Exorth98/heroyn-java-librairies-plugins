package net.heroyn.mobarena.classe.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.classe.priest.PriestHeal;
import net.heroyn.mobarena.classe.priest.PriestHolyFire;
import net.heroyn.mobarena.utils.PlayerUtils;

public class Mage extends ClasseBase {

	public Mage(Player player, Integer level) {
		super(player, level, "Mage");
	}

	@Override
	protected void setUpSkill() {
		this.skills.put(PriestHeal.displayName, new PriestHeal(level, player));
		this.skills.put(PriestHolyFire.displayName, new PriestHolyFire(level, player));
	}

	@Override
	protected void setRegain() {
		this.energyRegen = 0.10f;
		this.healthRegen = 1;
	}
	
	protected PriestHeal skillHeal() {
		return (PriestHeal) this.getSkill(PriestHeal.displayName);
	}
	
	protected PriestHolyFire skillHolyFire() {
		return (PriestHolyFire) this.getSkill(PriestHolyFire.displayName);
	}

	@Override
	public void inflictDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			double damage = 1;
			if (skillHolyFire().hasSkillItemInHand(damager)) {
				damage = skillHolyFire().getAttackPower() / 3;
				PlayerUtils.resetItemInHandDurability(damager);
			}
			event.setDamage(damage);
		}
	}

}
