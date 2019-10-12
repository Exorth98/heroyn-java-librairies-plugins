package net.heroyn.mobarena.classe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.player.MobarenaFighter;

public abstract class Skill implements Listener {

	public static List<Skill> allSkill = new ArrayList<>();

	public String name;
	public int level;
	public Player player;
	public float attackPower;
	public float cooldown;
	public int targetLimit;
	public int cost;
	public int task;
	public ItemStack buildItem;
	public List<String> description;
	public boolean isInCooldown;
	public String taskName;

	public Skill(int level, Player player) {
		this.level = level;
		this.player = player;
		this.isInCooldown = false;
		this.setCost();
		this.setAttackPower();
		this.setTargetLimit();
		this.setCooldown();
		this.setDescription();
		this.setItemStack();
		this.taskName = name + "." + player.getName();
		HeroynMobarena.getInstance().getServer().getPluginManager().registerEvents((Listener) this, (Plugin) HeroynMobarena.getInstance());
		allSkill.add(this);
	}

	protected abstract void setCost();

	protected abstract void setAttackPower();

	protected abstract void setTargetLimit();

	protected abstract void setCooldown();

	protected abstract void setDescription();

	protected abstract void setItemStack();

	public abstract void use(Player player);

	/**
	 * Utilisation de l'item
	 */
	@EventHandler
	public void onPlayerInteractGadget(final PlayerInteractEvent playerInteractEvent) {
		if (playerInteractEvent.getItem() != null
				&& (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR
						|| playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& playerInteractEvent.getItem().equals((Object) this.buildItem)
				&& playerInteractEvent.getPlayer().equals(this.player)) {

			this.use(playerInteractEvent.getPlayer());
			playerInteractEvent.setCancelled(true);
			playerInteractEvent.getPlayer().updateInventory();
		}
	}

	// methode
	public boolean hasSkillItemInHand(Player damager) {
		return damager.getInventory().getItemInMainHand().isSimilar(getBuildItem());
	}

	@SuppressWarnings("deprecation")
	protected void coolDown() {
    	this.isInCooldown = true;
		Bukkit.getScheduler().scheduleAsyncDelayedTask(HeroynMobarena.getInstance(), new Runnable() {
			@Override
			public void run() {
					isInCooldown = false;
					getPlayer().sendMessage("SKill pret !");
			}
		}, 20 * (long) this.getCooldown());
    }

	/**
	 * 
	 * @param player
	 * @param energy -> a assez d'energie pour utiliser ?
	 * @param cooldown -> est en rechargement ?
	 * @return
	 */
	public boolean checkForCooldownAndEnergy(Player player, boolean energy, boolean cooldown) {
		if (energy && (MobarenaFighter.getFromPlayer(player).getClasse().getEnergy() < this.getCost())) {
			player.sendMessage("§fVous n'avez pas assez d'énergie !");
			return false;
		}
		if (cooldown && this.isInCooldown) {
			player.sendMessage("§fEn cours de rechargement...");
			return false;
		}

		coolDown();
		MobarenaFighter.getFromPlayer(this.getPlayer()).getClasse().removeEnergy(this.getCost());
		return true;
	}

	public float getAttackPower() {
		return attackPower;
	}

	public float getCooldown() {
		return cooldown;
	}

	public int getTargetLimit() {
		return targetLimit;
	}

	public int getCost() {
		return cost;
	}

	public List<String> getDescription() {
		return description;
	}

	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public ItemStack getBuildItem() {
		ItemMeta meta = this.buildItem.getItemMeta();
		meta.setDisplayName(this.getName());
		meta.setLore(this.getDescription());
		this.buildItem.setItemMeta(meta);
		return this.buildItem;
	}

	public static List<Skill> getAllSkill() {
		return allSkill;
	}
}
