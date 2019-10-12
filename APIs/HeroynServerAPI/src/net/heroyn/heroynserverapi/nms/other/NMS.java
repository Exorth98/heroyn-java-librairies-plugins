package net.heroyn.heroynserverapi.nms.other;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("rawtypes")
public class NMS
{
    private static Class<?> entityClass;
    private static Class<?> minecraftServerClass;
    private static Reflection.MethodInvoker getHandleWorldMethod;

	private static Reflection.FieldAccessor noClipField;
    private static Reflection.MethodInvoker getHandleMethod;
    private static Reflection.MethodInvoker getServerMethod;
    private static Reflection.MethodInvoker setInvisibleMethod;
    private static Reflection.MethodInvoker setSilentMethod;
    private static Reflection.MethodInvoker setPositionMethod;
    private static Reflection.FieldAccessor worldField;
    private static Reflection.MethodInvoker broadcastentityeffectMethod;
    private static Class<?> packetRotationPitchClass;
    private static Reflection.ConstructorInvoker packetRotationPitchContrustuctor;
    private static Class<?> packetRotationYawClass;
    private static Reflection.ConstructorInvoker packetRotationYawContrustuctor;
    private static Reflection.ConstructorInvoker packetSpawnContrustuctor;
    private static Reflection.ConstructorInvoker packetStatusContrustuctor;
    private static Reflection.ConstructorInvoker dragonContrustuctor;
    private static Reflection.MethodInvoker getIdMethod;
    private static Reflection.FieldAccessor recentTps;
    private static Reflection.FieldAccessor lastX;
    private static Reflection.FieldAccessor lastY;
    private static Reflection.FieldAccessor lastZ;
    private static Reflection.ConstructorInvoker blockpositionContrustuctor;
    private static Reflection.ConstructorInvoker blockactionContrustuctor;
    private static Reflection.FieldAccessor motXField;
    private static Reflection.FieldAccessor motZField;
    private static Reflection.FieldAccessor motYField;
    private static Reflection.FieldAccessor idField;
    
    static {
        NMS.entityClass = Reflection.getClass("{nms}.Entity");
        NMS.minecraftServerClass = Reflection.getClass("{nms}.MinecraftServer");
        NMS.getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
        NMS.noClipField = Reflection.getField(Reflection.getMinecraftClass("Entity"), "noclip", Boolean.TYPE);
        NMS.getHandleMethod = Reflection.getMethod("{obc}.entity.CraftEntity", "getHandle", (Class<?>[])new Class[0]);
        NMS.getServerMethod = Reflection.getMethod(NMS.minecraftServerClass, "getServer", (Class<?>[])new Class[0]);
        NMS.setInvisibleMethod = Reflection.getMethod(NMS.entityClass, "setInvisible", Boolean.TYPE);
        NMS.setPositionMethod = Reflection.getMethod(NMS.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        NMS.worldField = Reflection.getField(NMS.entityClass, Reflection.getMinecraftClass("World"), 0);
        NMS.broadcastentityeffectMethod = Reflection.getMethod(Reflection.getMinecraftClass("World"), "broadcastEntityEffect", NMS.entityClass, Byte.TYPE);
        NMS.packetRotationPitchClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
        NMS.packetRotationPitchContrustuctor = Reflection.getConstructor(NMS.packetRotationPitchClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
        NMS.packetRotationYawClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
        NMS.packetRotationYawContrustuctor = Reflection.getConstructor(NMS.packetRotationYawClass, NMS.entityClass, Byte.TYPE);
        NMS.packetSpawnContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutSpawnEntityLiving"), Reflection.getMinecraftClass("EntityLiving"));
        NMS.packetStatusContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityStatus"), NMS.entityClass, Byte.TYPE);
        NMS.dragonContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("EntityEnderDragon"), Reflection.getMinecraftClass("World"));
        NMS.getIdMethod = Reflection.getMethod(NMS.entityClass, "getId", (Class<?>[])new Class[0]);
        NMS.recentTps = Reflection.getField(NMS.minecraftServerClass, "recentTps", double[].class);
        NMS.lastX = Reflection.getField(NMS.entityClass, "lastX", Double.TYPE);
        NMS.lastY = Reflection.getField(NMS.entityClass, "lastY", Double.TYPE);
        NMS.lastZ = Reflection.getField(NMS.entityClass, "lastZ", Double.TYPE);
        NMS.blockpositionContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("BlockPosition"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
        NMS.blockactionContrustuctor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutBlockAction"), Reflection.getMinecraftClass("BlockPosition"), Reflection.getMinecraftClass("Block"), Integer.TYPE, Integer.TYPE);
        NMS.motXField = Reflection.getField(NMS.entityClass, "motX", Double.TYPE);
        NMS.motZField = Reflection.getField(NMS.entityClass, "motZ", Double.TYPE);
        NMS.motYField = Reflection.getField(NMS.entityClass, "motY", Double.TYPE);
        NMS.idField = Reflection.getField(NMS.entityClass, "id", Integer.TYPE);
        try {
            NMS.setSilentMethod = Reflection.getMethod(Reflection.getMinecraftClass("Entity"), "setSilent", Boolean.TYPE);
        }
        catch (IllegalStateException ex) {
            NMS.setSilentMethod = null;
        }
    }
    
    public static void setNoClip(final Entity entity, final boolean b) {
        NMS.noClipField.set(NMS.getHandleMethod.invoke(entity, new Object[0]), b);
    }
    
    public static void broadcastEntityEffect(final Entity entity, final byte b) {
        NMS.broadcastentityeffectMethod.invoke(NMS.worldField.get(NMS.getHandleMethod.invoke(entity, new Object[0])), NMS.getHandleMethod.invoke(entity, new Object[0]), b);
    }
    
    public static void ironGolemAttack(final IronGolem ironGolem) {
        NMS.broadcastentityeffectMethod.invoke(NMS.worldField.get(NMS.getHandleMethod.invoke(ironGolem, new Object[0])), NMS.getHandleMethod.invoke(ironGolem, new Object[0]), (byte)4);
    }
    
    public static void setPitchHeadRotation(final Entity entity, final float n) {
        NMSPlayer.sendPacketNearby(entity.getLocation(), NMS.packetRotationPitchContrustuctor.invoke(NMS.getIdMethod.invoke(NMS.getHandleMethod.invoke(entity, new Object[0]), new Object[0]), UtilMath.toPackedByte(entity.getLocation().getYaw()), UtilMath.toPackedByte(n), false));
    }
    
    public static void setYawRotation(final Entity entity, final float n) {
        NMSPlayer.sendPacketNearby(entity.getLocation(), NMS.packetRotationYawContrustuctor.invoke(NMS.getHandleMethod.invoke(entity, new Object[0]), UtilMath.toPackedByte(n)));
    }
    
    public static Vector getLastLocation(final Entity entity) {
        final Object invoke = NMS.getHandleMethod.invoke(entity, new Object[0]);
        return new Vector((double)NMS.lastX.get(invoke), (double)NMS.lastY.get(invoke), (double)NMS.lastZ.get(invoke));
    }
    
    public static void setChestOpen(final Block block, final boolean b) {
        NMSPlayer.sendPacketNearby(block.getLocation(), NMS.blockactionContrustuctor.invoke(NMS.blockpositionContrustuctor.invoke(block.getX(), block.getY(), block.getZ()), Reflection.getField(Reflection.getMinecraftClass("Blocks"), "CHEST", Reflection.getMinecraftClass("Block")).get(Reflection.getMinecraftClass("Blocks")), 1, (int)(b ? 1 : 0)));
    }
    
    public static void setMotion(final Entity entity, final double n, final double n2) {
        final Object invoke = NMS.getHandleMethod.invoke(entity, new Object[0]);
        NMS.motXField.set(invoke, n);
        NMS.motZField.set(invoke, n2);
    }
    
    public static Vector getMotion(final Entity entity) {
        final Object invoke = NMS.getHandleMethod.invoke(entity, new Object[0]);
        return new Vector((double)NMS.motXField.get(invoke), (double)NMS.motYField.get(invoke), (double)NMS.motZField.get(invoke));
    }
    
    public static int spawnDragonEffect(final Location location) {
        final Object invoke = NMS.dragonContrustuctor.invoke(NMS.getHandleWorldMethod.invoke(location.getWorld(), new Object[0]));
        NMS.setPositionMethod.invoke(invoke, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        NMSPlayer.sendPacket(NMS.packetSpawnContrustuctor.invoke(invoke), NMS.packetStatusContrustuctor.invoke(invoke, (byte)3));
        return (int)NMS.getIdMethod.invoke(invoke, new Object[0]);
    }
    
    public static void setInvisible(final Entity entity, final boolean b) {
        NMS.setInvisibleMethod.invoke(NMS.getHandleMethod.invoke(entity, new Object[0]), b);
    }
    
    public static int getID(final Entity entity) {
        return (int)NMS.getIdMethod.invoke(NMS.getHandleMethod.invoke(entity, new Object[0]), new Object[0]);
    }
    
    public static void setId(final Entity entity, final Entity entity2) {
        NMS.idField.set(NMS.getHandleMethod.invoke(entity, new Object[0]), getID(entity2));
    }
    
    public static double getTPS() {
        return ((double[])NMS.recentTps.get(NMS.getServerMethod.invoke(NMS.minecraftServerClass, new Object[0])))[0];
    }
    
    public static void setSilent(final Entity entity) {
        if (NMS.setSilentMethod == null) {
            return;
        }
        NMS.setSilentMethod.invoke(NMS.getHandleMethod.invoke(entity, new Object[0]), true);
    }
}
