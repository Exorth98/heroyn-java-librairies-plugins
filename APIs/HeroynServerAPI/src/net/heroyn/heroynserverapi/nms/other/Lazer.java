package net.heroyn.heroynserverapi.nms.other;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class Lazer
{
    private static Class<?> packetSpawnEntityLivingClass;
    private static Class<?> packetMetaClass;
    private static Class<?> nmsWorldClass;
    private static Class<?> datawatcherClass;
    private static Class<?> datawatcherObjectClass;
    private static Class<?> datawatcherSerializerClass;
    private static Class<?> entityClass;
    private static Class<?> guardianClass;
    private static Class<?> entityLivingClass;
    private static Class<?> packetTeleportClass;
    private static Reflection.ConstructorInvoker packetSpawnConstructor;
    private static Reflection.ConstructorInvoker datawatcherConstructor;
    private static Reflection.ConstructorInvoker datawatcherObjectConstructor;
    private static Reflection.ConstructorInvoker packetMetaConstructor;
	private static Reflection.ConstructorInvoker packetTeleportContrustuctor;
    private static Reflection.MethodInvoker getHandleWorldMethod;
    private static Reflection.MethodInvoker getHandleMethod;
    private static Reflection.MethodInvoker setPositionMethod;
    private static Reflection.MethodInvoker getIdMethod;
    private static Reflection.MethodInvoker setTargetMethod;
    private static Reflection.MethodInvoker registerDataWatcherMethod;
    private static Reflection.MethodInvoker setDataWatcherMethod;
    private static Reflection.MethodInvoker getDataWatcherMethod;
    private static Reflection.FieldAccessor datawatcherRegistry;
    private static Reflection.FieldAccessor entityDatawatcherField;
    private List<Object> packets;
    private Location startLocation;
    private Location endLocation;
    private Object lazer;
    private ArmorStand target;
    private int ID;
    private int targetID;
    private Object datawatcherObject;
    
    static {
        Lazer.packetSpawnEntityLivingClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
        Lazer.packetMetaClass = Reflection.getClass("{nms}.PacketPlayOutEntityMetadata");
        Lazer.nmsWorldClass = Reflection.getClass("{nms}.World");
        Lazer.datawatcherClass = Reflection.getClass("{nms}.DataWatcher");
        Lazer.datawatcherObjectClass = Reflection.getClass("{nms}.DataWatcherObject");
        Lazer.datawatcherSerializerClass = Reflection.getClass("{nms}.DataWatcherSerializer");
        Lazer.entityClass = Reflection.getClass("{nms}.Entity");
        Lazer.guardianClass = Reflection.getClass("{nms}.EntityGuardian");
        Lazer.entityLivingClass = Reflection.getClass("{nms}.EntityLiving");
        Lazer.packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
        Lazer.packetSpawnConstructor = Reflection.getConstructor(Lazer.packetSpawnEntityLivingClass, Lazer.entityLivingClass);
        Lazer.datawatcherConstructor = Reflection.getConstructor(Lazer.datawatcherClass, Lazer.entityClass);
        Lazer.datawatcherObjectConstructor = Reflection.getConstructor(Lazer.datawatcherObjectClass, Integer.TYPE, Lazer.datawatcherSerializerClass);
        Lazer.packetMetaConstructor = Reflection.getConstructor(Lazer.packetMetaClass, Integer.TYPE, Lazer.datawatcherClass, Boolean.TYPE);
        Lazer.packetTeleportContrustuctor = Reflection.getConstructor(Lazer.packetTeleportClass, Lazer.entityClass);
        Lazer.getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
        Lazer.getHandleMethod = Reflection.getMethod("{obc}.entity.CraftEntity", "getHandle", (Class<?>[])new Class[0]);
        Lazer.setPositionMethod = Reflection.getMethod(Lazer.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        Lazer.getIdMethod = Reflection.getMethod(Lazer.entityClass, "getId", (Class<?>[])new Class[0]);
        Lazer.registerDataWatcherMethod = Reflection.getMethod(Lazer.datawatcherClass, "register", Lazer.datawatcherObjectClass, Object.class);
        Lazer.setDataWatcherMethod = Reflection.getMethod(Lazer.datawatcherClass, "set", Lazer.datawatcherObjectClass, Object.class);
        Lazer.getDataWatcherMethod = Reflection.getMethod(Lazer.entityClass, "getDataWatcher", (Class<?>[])new Class[0]);
        Lazer.datawatcherRegistry = Reflection.getField(Reflection.getMinecraftClass("DataWatcherRegistry"), Object.class, 1);
        Lazer.entityDatawatcherField = Reflection.getField(Lazer.entityClass, Lazer.datawatcherClass, 0);
    }
    
	public Lazer(final Location startLocation, final Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        try {
            final String s = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            if (s.equals("v1_9_R1") || s.equals("v1_9_R2") || s.equals("v1_10_R2") || s.equals("v1_10_R1")) {
                Lazer.setTargetMethod = Reflection.getMethod(Lazer.guardianClass, "b", Integer.TYPE);
            }
            else {
                Lazer.setTargetMethod = Reflection.getMethod(Lazer.guardianClass, "a", Integer.TYPE);
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        this.lazer = Reflection.getConstructor(Reflection.getMinecraftClass("EntityGuardian"), Lazer.nmsWorldClass).invoke(Lazer.getHandleWorldMethod.invoke(startLocation.getWorld(), new Object[0]));
        Lazer.setPositionMethod.invoke(this.lazer, startLocation.getX(), startLocation.getY(), startLocation.getZ(), startLocation.getYaw(), startLocation.getPitch());
        this.ID = (int)Lazer.getIdMethod.invoke(this.lazer, new Object[0]);
        final Object invoke = Lazer.packetSpawnConstructor.invoke(this.lazer);
        (this.target = (ArmorStand)endLocation.getWorld().spawn(endLocation, (Class)ArmorStand.class)).setVisible(false);
        this.target.setGravity(false);
        this.targetID = (int)Lazer.getIdMethod.invoke(Lazer.getHandleMethod.invoke(this.target, new Object[0]), new Object[0]);
        Lazer.setTargetMethod.invoke(this.lazer, this.targetID);
        final Object invoke2 = Lazer.datawatcherConstructor.invoke(this.lazer);
        this.datawatcherObject = Lazer.datawatcherObjectConstructor.invoke(0, Lazer.datawatcherRegistry.get(Reflection.getMinecraftClass("DataWatcherRegistry")));
        Lazer.registerDataWatcherMethod.invoke(invoke2, this.datawatcherObject, (byte)32);
        this.packets = NMSPlayer.sendConstantPacket(invoke, Lazer.packetMetaConstructor.invoke(this.ID, invoke2, true));
    }
    
    public void setLazerGravity(final boolean gravity) {
        this.target.setGravity(gravity);
    }
    
    public void hideLazer() {
        Lazer.setTargetMethod.invoke(this.lazer, 0);
        final Object value = Lazer.entityDatawatcherField.get(this.lazer);
        this.datawatcherObject = Lazer.datawatcherObjectConstructor.invoke(0, Lazer.datawatcherRegistry.get(Reflection.getMinecraftClass("DataWatcherRegistry")));
        Lazer.setDataWatcherMethod.invoke(value, this.datawatcherObject, (byte)32);
        NMSPlayer.sendPacket(Lazer.packetMetaConstructor.invoke(this.ID, value, true));
    }
    
    public void showLazer() {
        Lazer.setTargetMethod.invoke(this.lazer, this.targetID);
        final Object value = Lazer.entityDatawatcherField.get(this.lazer);
        this.datawatcherObject = Lazer.datawatcherObjectConstructor.invoke(0, Lazer.datawatcherRegistry.get(Reflection.getMinecraftClass("DataWatcherRegistry")));
        Lazer.setDataWatcherMethod.invoke(value, this.datawatcherObject, (byte)32);
        this.packets = NMSPlayer.sendConstantPacket(Lazer.packetMetaConstructor.invoke(this.ID, value, true));
    }
    
    public void setEndLocation(final Location endLocation) {
        this.endLocation = endLocation;
        this.target.teleport(endLocation);
    }
    
    public void remove() {
        this.target.remove();
        NMSPlayer.destroyEntity(this.ID);
        if (this.packets != null) {
            NMSPlayer.getPacketsToSend().removeAll(this.packets);
        }
    }
    
    public Location getEndLocation() {
        return this.endLocation;
    }
}
