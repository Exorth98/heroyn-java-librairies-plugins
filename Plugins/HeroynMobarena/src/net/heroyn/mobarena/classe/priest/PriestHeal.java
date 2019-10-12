package net.heroyn.mobarena.classe.priest;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.Particles.Particles;
import net.heroyn.heroynapi.Target.TargetInfo;
import net.heroyn.heroynapi.Target.TargetUtils;
import net.heroyn.heroynapi.utils.FloatingTextUtils;
import net.heroyn.heroynapi.utils.Task.TaskManager;
import net.heroyn.mobarena.classe.Skill;
import net.heroyn.mobarena.utils.ClasseMsg;

public class PriestHeal extends Skill {

	public static String displayName = "§dSoins";
	
	public PriestHeal(int level, Player player) {
		super(level, player);
	}

	@Override
	protected void setCost() {
		this.cost = 20;
	}

	@Override
	protected void setAttackPower() {
		this.attackPower = (60 + level * 5f) / 10;
	}

	@Override
	protected void setTargetLimit() {
		this.targetLimit = 20;
	}

	@Override
	protected void setCooldown() {
		this.cooldown = (40 - level * 2f) /10;
	}

	@Override
	protected void setDescription() {
		this.name = displayName;
		this.description = new ArrayList<String>();
		description.add("§7Soigne votre cible ou vous-même");
		description.add("§7Coût: §b" + this.cost + " §7points d'énergie");
		description.add("§7Points de vie rendus: §b" + this.attackPower);
		description.add("§7Recharge: §b" + this.cooldown + " §7secondes");
	}

	@Override
	protected void setItemStack() {
		this.buildItem = new ItemStack(Material.INK_SACK, 1, (short) 9);
	}

	@Override
	public void use(Player player) {
		TargetInfo target = TargetUtils.getTargetInfoPlayerTeamOnly(player, targetLimit, 1.40, false);
		// pas de joueur en vue, le soin se pose sur nous meme
		if (target == null) target = new TargetInfo(player, 0);
		// full vie ?
		if (!canBeHealed(target.getPlayer())) {
			ClasseMsg.playerIsFullLife(player, (target.getPlayer() == player));
			return;
		}
		// Cooldown energie
		if (!checkForCooldownAndEnergy(player, true, true)) return;
		// Effect
		playEffect(player, target);
		// soins
		healPlayer(target.getPlayer());
	}
	
	/**
	 * Soigne le joueur
	 * @param player
	 */
	@SuppressWarnings("deprecation")
	private void healPlayer(Player player) {
		double health = player.getHealth();
		health += this.getAttackPower();
		if (health >= player.getMaxHealth()) health = player.getMaxHealth();
		FloatingTextUtils.displayHealReceived(player);
		player.setHealth(health);
	}
	
	/**
	 * Affichage du soin
	 * @param caster
	 * @param target
	 */
	public void playEffect(final Player caster, TargetInfo target) {
		Vector line = target.getPlayer().getLocation().toVector().subtract(caster.getLocation().toVector()).normalize();
		// Rayon de soins si la cible n'est pas sois meme
		if (caster != target.getPlayer()) {
			Location startPoint = caster.getEyeLocation();
			for (int x = (int) target.getDistance(); x > 0; x --) {
				startPoint.add(line);
				for (Player pls : Bukkit.getOnlinePlayers()) {
					Particles.VILLAGER_HAPPY.display(0, 0, 0, 0, 1, startPoint, pls);
					Particles.VILLAGER_HAPPY.display(0.2F, 0.2F, 0.2F, 0, 1, startPoint, pls);
				}
			}
		}
		for (Player pls : Bukkit.getOnlinePlayers()) {
			Particles.HEART.display(0.6F, 0.6F, 0.6F, 0, 6, target.getPlayer().getEyeLocation() ,pls);
		}
		playSound(target.getPlayer());
	}
	
	public void playSound(Player player) {
		for (int x = 0; x < 5; x++) {
			final float volume = 0.5F * x * 0.2F;
			int ticks = x * 3;
			TaskManager.runTaskLater(new Runnable() {
				@Override
				public void run() {
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 1, volume);
				}
			}, ticks);
		}
	}

	@SuppressWarnings("deprecation")
	/**
	 * Le joueur peut être soigné ?
	 * @param player
	 * @return
	 */
	public boolean canBeHealed(Player player) {
		return player.getHealth() < player.getMaxHealth();
	}
}
