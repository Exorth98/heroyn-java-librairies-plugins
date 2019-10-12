package net.heroyn.heroynapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.heroyn.heroynapi.HeroynAPI;
import net.md_5.bungee.api.ChatColor;

public class FloatingTextUtils {

	public static void displayHealReceived(Player player) {
		Bukkit.getScheduler().runTaskLater(HeroynAPI.getInstance(), new Runnable() {
			double life = player.getHealth();

			public void run() {
				if (player.isValid()) {
					double health = player.getHealth() - this.life;
					if (health > 0.0D) {
						FloatingTextUtils.displayFloatingTextAtLocation(player, player.getEyeLocation(),
								"§a+" + String.format("%.2f", Double.valueOf(health)), (long) 1.5D, 0.2D);
					}
				}
			}
		}, 1L);
	}

	public static void diplayDamageTaken(Player player) {
		Bukkit.getScheduler().runTaskLater(HeroynAPI.getInstance(), new Runnable() {
			double life = player.getHealth();

			public void run() {
				if (player.isValid()) {
					double damages = this.life - player.getHealth();
					if (damages > 0.0D)
						FloatingTextUtils.displayFloatingTextAtLocation(player, player.getEyeLocation(),
								"§c-" + String.format("%.2f", Double.valueOf(damages)), 3L, 0.2D);
				}
			}
		}, 1L);
	}

	public static void displayFloatingTextAtLocation(Player player, Location location, String text, long timer,
			double offset) {
		location.add(0.0D, 1.0D, 0.0D);
		sendFloatingTextToPlayer(text, player, location, timer);
	}

	@SuppressWarnings("deprecation")
	private static void sendFloatingTextToPlayer(String text, final Player player, Location location, long timer) {
		String coloredName = ChatColor.translateAlternateColorCodes('&', text);

		ArmorStand as = (ArmorStand) Bukkit.getServer().getWorld(player.getWorld().getName()).spawn(location, ArmorStand.class);
		as.setVisible(false);
		as.setBasePlate(false);
		as.setSmall(true);
		as.setGravity(false);
		as.setCustomName(coloredName);
		as.setCustomNameVisible(true);
		player.setPassenger(as);
		Bukkit.getScheduler().runTaskLater(HeroynAPI.getInstance(), new Runnable() {
			public void run() {
				as.remove();
				FloatingTextUtils.remove(player);
			}

		}, timer * 20L);
	}

	@SuppressWarnings("deprecation")
	private static void remove(Player player) {
		Entity en = player.getPassenger();
		if ((en != null) && (en.getType() == EntityType.ARMOR_STAND)) {
			en.remove();
		}
	}
}
