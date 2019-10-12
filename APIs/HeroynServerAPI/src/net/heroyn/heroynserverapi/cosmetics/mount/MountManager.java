package net.heroyn.heroynserverapi.cosmetics.mount;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import net.heroyn.heroynapi.utils.Task.TaskManager;
import net.heroyn.heroynserverapi.cosmetics.mount.mounts.RideableSpider;
import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityAgeable;
import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.World;

public class MountManager {

	public static double mountSpeed = 0.15;
	public static double mountFlySpeed = 1.25;
	public static double maxHealth = 2.0;
	
	
	public static void RideSpider(final Player player) {
		final Location loc = player.getLocation();
		final World nmsWorld = (World)((CraftWorld)loc.getWorld()).getHandle();
		final RideableSpider nmsEntity = new RideableSpider(nmsWorld);
		make(nmsEntity, player);
	}
	
	
	
	
	
	@SuppressWarnings("deprecation")
	private static void make(final EntityLiving nmsEntity, final Player player) {
        if (!canSummonMount(player.getLocation())) {
            player.sendMessage("§cimpossible d'utiliser la monture ici !");
            return;
        }
        final LivingEntity mount = (LivingEntity)nmsEntity.getBukkitEntity();
        if (mount instanceof EntityAgeable) {
            ((EntityAgeable)mount).setAge(0);
        }
        final Location loc = player.getLocation();
        final World nmsWorld = (World)((CraftWorld)loc.getWorld()).getHandle();
        nmsEntity.setPosition(loc.getX(), loc.getY() + 0.3, loc.getZ());
        nmsWorld.addEntity((Entity)nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        mount.setMaxHealth(MountManager.maxHealth);
        mount.setPassenger((org.bukkit.entity.Entity)player);
        player.closeInventory();
    }
    
    private static boolean canSummonMount(final Location location) {
        final org.bukkit.World world = location.getWorld();
        Block block = location.getBlock();
        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; ++x) {
            for (int y = location.getBlockY(); y <= location.getBlockY() + 1; ++y) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; ++z) {
                    block = world.getBlockAt(x, y, z);
                    if (block.getType().isSolid()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static void shouldDie(final EntityLiving mount, final Player rider) {
        mount.die();
    }
    
    public static void HeadFollowPlayer(final EntityLiving entity, final Player player) {
        TaskManager.runTaskTimer((Runnable)new Runnable() {
            @Override
            public void run() {
                if (!player.isValid() || !entity.isAlive()) {
                    TaskManager.cancelTaskByName(String.valueOf(player.getName()) + "_" + entity.getName());
                }
                final Location loc = player.getLocation();
                entity.getBukkitEntity().getLocation().setYaw(loc.getYaw());
                entity.getBukkitEntity().getLocation().setPitch(loc.getPitch());
            }
        }, 0, 10, String.valueOf(player.getName()) + "_" + entity.getName());
    }
	
}
