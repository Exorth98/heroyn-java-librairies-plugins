package net.heroyn.heroynserverapi.cosmetics.disguise;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.minecraft.server.v1_13_R1.EntityInsentient;
import net.minecraft.server.v1_13_R1.PathEntity;

public class Morph {

	
	public static void test(Entity ent, Player player) {
		((LivingEntity) ent).setCollidable(false);
		player.setCollidable(false);
		
		new BukkitRunnable() {
			@SuppressWarnings("unused")
			@Override
			public void run() {
				if (ent == null) {
					this.cancel();
					return;
				}
				if (!player.isOnline()) {
					ent.remove();
					this.cancel();
					return;
				}
				
				Object pObject = ((CraftEntity) ent).getHandle();
				Location loc = player.getLocation();
				if (!ent.getLocation().equals(loc)) {
					PathEntity path;
					path = ((EntityInsentient) pObject).getNavigation().a(loc.getX(), loc.getY(), loc.getZ());
					if (path != null) {
						((EntityInsentient) pObject).getNavigation().a(path, 1.0D);
						((EntityInsentient) pObject).getNavigation().a(2.0D);
					}
				}
			}
		}.runTaskTimer(HeroynServerAPI.getInstance(), 0, 20);
		
	}
}
