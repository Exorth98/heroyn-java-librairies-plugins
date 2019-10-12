package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.nms.entity.ReflectedArmorStand;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;
import net.heroyn.heroynserverapi.utils.UtilFirework;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilLocation;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

@SuppressWarnings("deprecation")
public class MeteorGadget extends IGadget {
	private List<Meteor> meteors;
	private Location location;

	public MeteorGadget(final Player player) {
		super(player,
				new UtilItem(385, (byte) 0,
						"&6Meteor",
					null).getItem(),
				60);
		this.meteors = new ArrayList<Meteor>();
	}

	@Override
	public void start() {
		this.location = this.player.getLocation();
		new BukkitRunnable() {
			public void run() {
				if (MeteorGadget.this.location != null) {
					MeteorGadget.this.stop();
				}
			}
		}.runTaskLater((Plugin) HeroynServerAPI.getInstance(), 400L);
	}

	@Override
	public void stop() {
		this.location = null;
		this.meteors.forEach(meteor -> meteor.removeMeteor());
		this.meteors.clear();
	}

	@EventHandler
	public void move(final UpdaterEvent updaterEvent) {
		if (this.location == null) {
			return;
		}
		if (updaterEvent.getType() == UpdateType.TICK) {
			this.meteors.removeIf(meteor -> {
				if (meteor.start.distance(meteor.end) <= 1.0) {
					meteor.removeMeteor();
					meteor.explode(meteor.end.clone().add(0.0, 1.0, 0.0));
					return true;
				} else {
					meteor.move();
					return false;
				}
			});
		} else if (updaterEvent.getType() == UpdateType.SEC) {
			this.meteors.add(new Meteor(this.location));
		}
	}

	private static class Meteor {
		private List<ReflectedArmorStand> as;
		private Location start;
		public Location end;
		private Vector v;

		public Meteor(final Location location) {
			this.as = new ArrayList<ReflectedArmorStand>();
			this.end = location.clone().add((double) UtilMath.getRandomWithExclusion(-20, 20, 0), -1.0,
					(double) UtilMath.getRandomWithExclusion(-20, 20, 0));
			this.start = location.clone().add((double) UtilMath.getRandomWithExclusion(-20, 20, 0, 1), 50.0,
					(double) UtilMath.getRandomWithExclusion(-20, 10, 0));
			(this.v = this.end.toVector().subtract(this.start.toVector()).normalize()).multiply(0.7);
			this.spawnMeteor();
		}

		public void spawnMeteor() {
			for (int i = 0; i < 10; ++i) {
				final ReflectedArmorStand reflectedArmorStand = new ReflectedArmorStand(
						this.start.clone().subtract(0.0, 1.7, 0.0));
				reflectedArmorStand.setVisible(false);
				reflectedArmorStand.setEquipment(5, new ItemStack(Material.COAL_BLOCK));
				reflectedArmorStand.setHeadPose(new EulerAngle((double) UtilMath.randomRange(-30, 30),
						(double) UtilMath.randomRange(-30, 30), (double) UtilMath.randomRange(-30, 30)));
				reflectedArmorStand.spawnArmorStand();
				this.as.add(reflectedArmorStand);
			}
		}

		public void removeMeteor() {
			this.as.forEach(reflectedArmorStand -> reflectedArmorStand.remove());
			this.as.clear();
		}

		public void move() {
			this.start.getWorld().playSound(this.start, Sound.ENTITY_ZOMBIE_INFECT, 0.05f, 0.0f);
			this.start.add(this.v);
			final Location location = this.start;
			this.as.forEach(reflectedArmorStand -> {
				reflectedArmorStand.teleport(this.start.clone().add((double) UtilMath.randomRange(-0.5f, 0.5f),
						0.5 + UtilMath.randomRange(-0.5f, 0.5f), (double) UtilMath.randomRange(-0.5f, 0.5f)));
				this.start.clone().add(this.v.clone().multiply(-6));
				UtilParticle.sendParticle(location, Particle.EXPLOSION_NORMAL, 1, new Vector(0.2, 0.2, 0.2), 0.1f);
				UtilParticle.sendParticle(location, Particle.EXPLOSION_LARGE, 1, new Vector(0, 0, 0), 0.0f);
				UtilParticle.sendParticle(location, Particle.FLAME, 1, new Vector(0.2, 0.2, 0.2), 0.1f);
				UtilParticle.sendParticle(location, Particle.BLOCK_CRACK, 1, new Vector(0.5, 0.5, 0.5),
						new MaterialData(Material.REDSTONE_BLOCK));
				UtilParticle.sendParticle(location, Particle.BLOCK_CRACK, 1, new Vector(0.5, 0.5, 0.5),
						new MaterialData(Material.COAL_BLOCK));
			});
		}

		public void explode(final Location location) {
			UtilLocation.getClosestPlayersFromLocation(location, 4.0)
					.forEach(entity -> UtilMath.bumpEntity(entity, location, 1.0, 0.5));
			UtilFirework.playRandomFireworkColor(location, FireworkEffect.Type.BURST, 0);
			UtilParticle.sendParticle(location, Particle.EXPLOSION_HUGE, 1, new Vector(0, 0, 0), 0.0f);
			location.getWorld().playSound(location, Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.5f, 0.0f);
		}
	}
}
