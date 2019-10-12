package net.heroyn.heroynserverapi.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import net.heroyn.heroynserverapi.nms.Reflection;

@SuppressWarnings({"rawtypes", "unchecked"})
public class UtilFirework
{
    static int visibleDistance;

	private static Reflection.FieldAccessor expectedLife;
    private static Reflection.FieldAccessor ticksFlown;
    private static Reflection.MethodInvoker getHandleMethod;
    
    static {
        UtilFirework.visibleDistance = Bukkit.getServer().getViewDistance() * 16;
        UtilFirework.expectedLife = Reflection.getField(Reflection.getMinecraftClass("EntityFireworks"), "expectedLifespan", Integer.TYPE);
        UtilFirework.ticksFlown = Reflection.getField(Reflection.getMinecraftClass("EntityFireworks"), "ticksFlown", Integer.TYPE);
        UtilFirework.getHandleMethod = Reflection.getMethod("{obc}.entity.CraftFirework", "getHandle", (Class<?>[])new Class[0]);
    }
    
    public static void playRandomFireworkColor(final Location location, final FireworkEffect.Type type, final int power) {
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        final Random random = new Random();
        final Firework firework = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        builder.flicker(random.nextBoolean());
        builder.trail(random.nextBoolean());
        builder.with(type);
        builder.withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        builder.withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        final FireworkEffect build = builder.build();
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(build);
        fireworkMeta.setPower(power);
        firework.setFireworkMeta(fireworkMeta);
        if (power <= 0) {
            final Object invoke = UtilFirework.getHandleMethod.invoke(firework, new Object[0]);
            UtilFirework.ticksFlown.set(invoke, (int)UtilFirework.expectedLife.get(invoke) - 1);
        }
    }
    
    public static Firework playFirework(final Location location, final FireworkEffect fireworkEffect, final int power) {
		final Firework firework = (Firework)location.getWorld().spawn(location, (Class)Firework.class);
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(fireworkEffect);
        fireworkMeta.setPower(power);
        firework.setFireworkMeta(fireworkMeta);
        if (power <= 0) {
            final Object invoke = UtilFirework.getHandleMethod.invoke(firework, new Object[0]);
            UtilFirework.ticksFlown.set(invoke, (int)UtilFirework.expectedLife.get(invoke) - 1);
        }
        return firework;
    }
    
    public static Firework playRandomFirework(final Location location, final int power) {
        final Random random = new Random();
        final Firework firework = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().flicker(random.nextBoolean()).trail(random.nextBoolean()).with(FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)]).withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256))).withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256))).build());
        fireworkMeta.setPower(power);
        firework.setFireworkMeta(fireworkMeta);
        if (power <= 0) {
            final Object invoke = UtilFirework.getHandleMethod.invoke(firework, new Object[0]);
            UtilFirework.ticksFlown.set(invoke, (int)UtilFirework.expectedLife.get(invoke) - 1);
        }
        return firework;
    }
    
    public static Firework spawnRandomFireworkColor(final Location location, final FireworkEffect.Type type, final int power) {
        final Firework firework = (Firework)location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        final FireworkMeta fireworkMeta = firework.getFireworkMeta();
        final FireworkEffect.Builder builder = FireworkEffect.builder();
        final Random random = new Random();
        builder.flicker(random.nextBoolean());
        builder.trail(random.nextBoolean());
        builder.with(type);
        builder.withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        builder.withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        fireworkMeta.addEffect(builder.build());
        fireworkMeta.setPower(power);
        firework.setFireworkMeta(fireworkMeta);
        if (power <= 0) {
            final Object invoke = UtilFirework.getHandleMethod.invoke(firework, new Object[0]);
            UtilFirework.ticksFlown.set(invoke, (int)UtilFirework.expectedLife.get(invoke) - 1);
        }
        return firework;
    }
}
