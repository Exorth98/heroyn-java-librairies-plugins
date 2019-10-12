package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.utils.ItemBuilder;
import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.listeners.CancelListener;
import net.heroyn.heroynserverapi.utils.UtilLocation;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

public class BatblasterGadget extends IGadget {
	private List<Bat> bats;
	private Vector direction;

	@SuppressWarnings("deprecation")
	public BatblasterGadget(final Player player) {
		super(player, new ItemBuilder(Material.LEGACY_IRON_PLATE).name("§9Bat Blaster").build(), 15);
		this.bats = new ArrayList<Bat>();
	}

	@Override
	public void start() {
		if (this.direction != null) {
			return;
		}
		final Location location = this.player.getLocation();
		new BukkitRunnable() {
			public void run() {
				if (BatblasterGadget.this.direction != null) {
					BatblasterGadget.this.stop();
				}
			}
		}.runTaskLater(HeroynServerAPI.getInstance(), 80L);
		for (int i = 0; i < 16; ++i) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Bat bat = (Bat) location.getWorld().spawn(this.player.getEyeLocation().subtract(0.0, 1.0, 0.0),
					(Class) Bat.class);
			this.bats.add(bat);
			CancelListener.getProtectedEntity().add((Entity) bat);
			this.direction = location.getDirection();
		}
	}

	@Override
	public void update() {
		super.update();
		this.bats.forEach(bat -> {
			if (bat.isValid()) {
				bat.setVelocity(
						this.direction.clone().multiply(0.5).add(new Vector(UtilMath.randomRange(-1.0, 1.0) / 3.0,
								UtilMath.randomRange(-0.5, 0.5) / 5.0, UtilMath.randomRange(-1.0, 1.0) / 3.0)));
				UtilLocation.getClosestPlayersFromLocation(bat.getLocation(), 1.5).removeIf(entity -> {
					if (((Player) entity).getLocation().distance(bat.getLocation()) <= 2.0
							&& !entity.equals(this.player)) {
						bat.remove();
						((Player) entity).getWorld().playSound(bat.getLocation(), Sound.ENTITY_BAT_HURT, 1.0f, 1.0f);
						UtilMath.bumpEntity(entity, bat.getLocation(), 0.8, 0.2);
						UtilParticle.sendParticle(bat.getLocation(), Particle.SMOKE_LARGE, 1, new Vector(0, 0, 0),
								0.0f);
						return true;
					} else {
						return false;
					}
				});
			}
		});
	}

	@Override
	public void stop() {
		this.bats.stream().filter(bat -> bat.isValid()).forEach(bat2 -> {
			bat2.remove();
			CancelListener.getProtectedEntity().remove(bat2);
			return;
		});
		this.bats.clear();
		this.direction = null;
	}
}
