package net.heroyn.heroynserverapi.utils;

import java.util.LinkedList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class UtilMath
{
    public static final Random random;
    public static final BlockFace[] axis;
    public static final byte[] axisByte;
    
    static {
        random = new Random(System.nanoTime());
        axis = new BlockFace[] { BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST };
        axisByte = new byte[] { 3, 4, 2, 5 };
    }
    
    public static double offset(final Location location, final Location location2) {
        return offset(location.toVector(), location2.toVector());
    }
    
    public static double offset(final Vector vector, final Vector vector2) {
        return vector.subtract(vector2).length();
    }
    
    public static float randomRange(final float n, final float n2) {
        return n + (float)Math.random() * (n2 - n);
    }
    
    public static int randomRange(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }
    
    public static double randomRange(final double n, final double n2) {
        return (Math.random() < 0.5) ? ((1.0 - Math.random()) * (n2 - n) + n) : (Math.random() * (n2 - n) + n);
    }
    
    public static double arrondi(final double n, final int n2) {
        return (int)(n * Math.pow(10.0, n2) + 0.5) / Math.pow(10.0, n2);
    }
    
    public static int getRandomWithExclusion(final int n, final int n2, final int... array) {
        int n3 = n + UtilMath.random.nextInt(n2 - n + 1 - array.length);
        for (int length = array.length, n4 = 0; n4 < length && n3 >= array[n4]; ++n3, ++n4) {}
        return n3;
    }
    
    public static float getLookAtYaw(final Vector vector) {
        final double x = vector.getX();
        final double z = vector.getZ();
        double n = 0.0;
        if (x != 0.0) {
            double n2;
            if (x < 0.0) {
                n2 = 4.71238898038469;
            }
            else {
                n2 = 1.5707963267948966;
            }
            n = n2 - Math.atan(z / x);
        }
        else if (z < 0.0) {
            n = 3.141592653589793;
        }
        return (float)(-n * 180.0 / 3.141592653589793 - 90.0);
    }
    
    public static boolean elapsed(final long n, final long n2) {
        return System.currentTimeMillis() - n > n2;
    }
    
    public static Vector getBumpVector(final Entity entity, final Location location, final double n) {
        final Vector normalize = entity.getLocation().toVector().subtract(location.toVector()).normalize();
        normalize.multiply(n);
        return normalize;
    }
    
    public static Vector getPullVector(final Entity entity, final Location location, final double n) {
        final Vector normalize = location.toVector().subtract(entity.getLocation().toVector()).normalize();
        normalize.multiply(n);
        return normalize;
    }
    
    public static void bumpEntity(final Entity entity, final Location location, final double n) {
        entity.setVelocity(getBumpVector(entity, location, n));
    }
    
    public static void bumpEntity(final Entity entity, final Location location, final double n, final double y) {
        if (entity instanceof Player && entity.hasMetadata("NPC")) {
            return;
        }
        final Vector bumpVector = getBumpVector(entity, location, n);
        bumpVector.setY(y);
        entity.setVelocity(bumpVector);
    }
    
    public static void pullEntity(final Entity entity, final Location location, final double n) {
        entity.setVelocity(getPullVector(entity, location, n));
    }
    
    public static void pullEntity(final Entity entity, final Location location, final double n, final double y) {
        final Vector pullVector = getPullVector(entity, location, n);
        pullVector.setY(y);
        entity.setVelocity(pullVector);
    }
    
    public static final Vector rotateAroundAxisX(final Vector vector, final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        return vector.setY(vector.getY() * cos - vector.getZ() * sin).setZ(vector.getY() * sin + vector.getZ() * cos);
    }
    
    public static final Vector rotateAroundAxisY(final Vector vector, final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        return vector.setX(vector.getX() * cos + vector.getZ() * sin).setZ(vector.getX() * -sin + vector.getZ() * cos);
    }
    
    public static final Vector rotateAroundAxisZ(final Vector vector, final double n) {
        final double cos = Math.cos(n);
        final double sin = Math.sin(n);
        return vector.setX(vector.getX() * cos - vector.getY() * sin).setY(vector.getX() * sin + vector.getY() * cos);
    }
    
    public static final Vector rotateVector(final Vector vector, final double n, final double n2, final double n3) {
        rotateAroundAxisX(vector, n);
        rotateAroundAxisY(vector, n2);
        rotateAroundAxisZ(vector, n3);
        return vector;
    }
    
    public static Vector rotate(Vector vector, final Location location) {
        final double n = location.getYaw() / 180.0f * 3.141592653589793;
        vector = rotateAroundAxisX(vector, location.getPitch() / 180.0f * 3.141592653589793);
        vector = rotateAroundAxisY(vector, -n);
        return vector;
    }
    
    public static byte toPackedByte(final float n) {
        return (byte)(n * 256.0f / 360.0f);
    }
    
    public static Vector getRandomVector() {
        return new Vector(UtilMath.random.nextDouble() * 2.0 - 1.0, UtilMath.random.nextDouble() * 2.0 - 1.0, UtilMath.random.nextDouble() * 2.0 - 1.0).normalize();
    }
    
    public static Vector getRandomCircleVector() {
        final double n = UtilMath.random.nextDouble() * 2.0 * 3.141592653589793;
        return new Vector(Math.cos(n), Math.sin(n), Math.sin(n));
    }
    
    public static Vector getRandomVectorline() {
        final int n = -5;
        final int n2 = 5;
        final int n3 = (int)(Math.random() * (n2 - n) + n);
        final int n4 = (int)(Math.random() * (n2 - n) + n);
        final double n5 = -5.0;
        return new Vector((double)n4, Math.random() * (-1.0 - n5) + n5, (double)n3).normalize();
    }
    
    public static Location rotate(final Location location, Location location2, final double n) {
        location.setY(location2.getY());
        final float n2 = location2.getYaw() % 360.0f;
        final double distance = location.distance(location2);
        final double acos = Math.acos((location2.getX() - location.getX()) / distance);
        if (location2.getZ() < location.getZ()) {
            final double n3 = 6.283185307179586 - acos + n * 3.141592653589793 / 180.0;
            location2 = location.clone().add(Math.cos(n3) * distance, 0.0, Math.sin(n3) * distance);
            location2.setYaw((float)((n2 + n) % 360.0));
            return location2;
        }
        final double n4 = acos + n * 3.141592653589793 / 180.0;
        location2 = location.clone().add(Math.cos(n4) * distance, 0.0, Math.sin(n4) * distance);
        location2.setYaw((float)((n2 + n) % 360.0));
        return location2;
    }
    
    public static LinkedList<Vector> createCircle(final double n, final double n2) {
        final double n3 = n * n2;
        final double n4 = 6.283185307179586 / n3;
        final LinkedList<Vector> list = new LinkedList<Vector>();
        for (int n5 = 0; n5 < n3; ++n5) {
            final double n6 = n5 * n4;
            list.add(new Vector(n * Math.cos(n6), 0.0, n * Math.sin(n6)));
        }
        return list;
    }
}
