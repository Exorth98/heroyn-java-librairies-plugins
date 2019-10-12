package net.heroyn.heroynserverapi.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class UtilParticle
{
    public static void sendParticle(final Location location, final Particle particle, final int n, final Vector vector, final float n2) {
        location.getWorld().spawnParticle(particle, location, n, vector.getX(), vector.getY(), vector.getZ(), (double)n2);
    }
    
    public static void sendParticle(final Location location, final Particle particle, final int n, final Vector vector, final float n2, final Player player) {
        player.spawnParticle(particle, location, n, vector.getX(), vector.getY(), vector.getZ(), (double)n2);
    }
    
    public static void sendParticle(final Location location, final Particle particle, final int n, final Vector vector,final MaterialData materialData) {
        location.getWorld().spawnParticle(particle, location, n, vector.getX(), vector.getY(), vector.getZ(), 1.0, (Object)materialData);
    }
    
    public static void sendParticle(final Location location, final Particle particle, final Vector vector, final ItemStack itemStack) {
        location.getWorld().spawnParticle(particle, location, 0, vector.getX(), vector.getY(), vector.getZ(), 0.0, (Object)itemStack);
    }
}
