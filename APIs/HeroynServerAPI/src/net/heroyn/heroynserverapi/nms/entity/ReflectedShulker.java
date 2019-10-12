package net.heroyn.heroynserverapi.nms.entity;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings({"unused", "rawtypes"})
public class ReflectedShulker
{

	private static Class<?> packetRotationPitchClass  = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
    private static Class<?> packetSpawnEntityLivingClass  = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
    private static Class<?> packetRotationYawClass  = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
    private static Class<?> nmsWorldClass  = Reflection.getClass("{nms}.World");
    private static Class<?> entityClass  = Reflection.getClass("{nms}.Entity");
    private static Class<?> entityLivingClass  = Reflection.getClass("{nms}.EntityLiving");
    private static Class<?> packetTeleportClass  = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    private static Class<?> entityAgeableClass  = Reflection.getClass("{nms}.EntityAgeable");
    private Class<?> entityTypeClass;
    private static Reflection.ConstructorInvoker packetMetaConstructor  = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityMetadata"), Integer.TYPE, Reflection.getMinecraftClass("DataWatcher"), Boolean.TYPE);
    private static Reflection.ConstructorInvoker datawatcherConstructor  = Reflection.getConstructor(Reflection.getMinecraftClass("DataWatcher"), Reflection.getClass("{nms}.Entity"));
    private static Reflection.ConstructorInvoker packetSpawnConstructor  = Reflection.getConstructor(ReflectedShulker.packetSpawnEntityLivingClass, ReflectedShulker.entityLivingClass);
    private static Reflection.ConstructorInvoker packetRotationYawContrustuctor  = Reflection.getConstructor(ReflectedShulker.packetRotationYawClass, ReflectedShulker.entityClass, Byte.TYPE);
    private static Reflection.ConstructorInvoker packetTeleportContrustuctor  = Reflection.getConstructor(ReflectedShulker.packetTeleportClass, ReflectedShulker.entityClass);
    private static Reflection.MethodInvoker getHandleWorldMethod  = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setPositionMethod  = Reflection.getMethod(ReflectedShulker.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    private static Reflection.MethodInvoker getIdMethod  = Reflection.getMethod(ReflectedShulker.entityClass, "getId", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker getBukkitEntityMethod  = Reflection.getMethod(ReflectedShulker.entityClass, "getBukkitEntity", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setInvisibleMethod  = Reflection.getMethod(ReflectedShulker.entityClass, "setInvisible", Boolean.TYPE);
    private static Reflection.MethodInvoker registerMethod = Reflection.getMethod(Reflection.getMinecraftClass("DataWatcher"), "register", Reflection.getMinecraftClass("DataWatcherObject"), Object.class);
	private static Reflection.FieldAccessor watcherField  = Reflection.getField(Reflection.getMinecraftClass("EntityShulker"), "c", Reflection.getMinecraftClass("DataWatcherObject"));
    private List<Object> packets;
    private Location location;
    private Object livingEntity;
    private Entity entityBukkit;
    private boolean isSpawned;
    private int ID;
    private float yaw;
    private float pitch;
    private Object packetSpawn;
    private Object packetYawRotation;
    private Object packetPitchRotation;
    
    public ReflectedShulker(final Location location) {
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.location = location;
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.entityTypeClass = Reflection.getMinecraftClass("EntityShulker");
        this.livingEntity = Reflection.getConstructor(Reflection.getMinecraftClass("EntityShulker"), ReflectedShulker.nmsWorldClass).invoke(ReflectedShulker.getHandleWorldMethod.invoke(location.getWorld(), new Object[0]));
        ReflectedShulker.setPositionMethod.invoke(this.livingEntity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.ID = (int)ReflectedShulker.getIdMethod.invoke(this.livingEntity, new Object[0]);
        this.packetSpawn = ReflectedShulker.packetSpawnConstructor.invoke(this.livingEntity);
        this.entityBukkit = (Entity)ReflectedShulker.getBukkitEntityMethod.invoke(this.livingEntity, new Object[0]);
    }
    
    public void setBox(final int n, final List<UUID> list) {
        final Object invoke = ReflectedShulker.datawatcherConstructor.invoke();
        ReflectedShulker.registerMethod.invoke(invoke, ReflectedShulker.watcherField.get(this.livingEntity), (byte)n);
        NMSPlayer.sendPacketExclude(list, ReflectedShulker.packetMetaConstructor.invoke(this.ID, invoke, true));
    }
    
    public void setBox(final int n, final Player player) {
        final Object invoke = ReflectedShulker.datawatcherConstructor.invoke();
        ReflectedShulker.registerMethod.invoke(invoke, ReflectedShulker.watcherField.get(this.livingEntity), (byte)n);
        NMSPlayer.sendPacket(player, ReflectedShulker.packetMetaConstructor.invoke(this.ID, invoke, true));
    }
    
    public void setVisible(final boolean b) {
        ReflectedShulker.setInvisibleMethod.invoke(this.livingEntity, !b);
    }
    
    public ReflectedShulker teleport(final Location location, final float n, final float n2) {
        ReflectedShulker.setPositionMethod.invoke(this.livingEntity, location.getX(), location.getY(), location.getZ(), n, n2);
        NMSPlayer.sendPacketNearby(location, ReflectedShulker.packetTeleportContrustuctor.invoke(this.livingEntity));
        this.location = location;
        return this;
    }
    
    public ReflectedShulker setYawRotation(final float yaw) {
        this.packetYawRotation = ReflectedShulker.packetRotationYawContrustuctor.invoke(this.livingEntity, UtilMath.toPackedByte(yaw));
        if (this.isSpawned) {
            NMSPlayer.sendPacket(this.packetYawRotation);
        }
        this.yaw = yaw;
        return this;
    }
    
    public ReflectedShulker setYawRotation(final Player player, final float yaw) {
        this.packetYawRotation = ReflectedShulker.packetRotationYawContrustuctor.invoke(this.livingEntity, UtilMath.toPackedByte(yaw));
        if (this.isSpawned) {
            NMSPlayer.sendPacket(player, this.packetYawRotation);
        }
        this.yaw = yaw;
        return this;
    }
    
    public ReflectedShulker setYawRotation(final float yaw, final List<UUID> list) {
        this.packetYawRotation = ReflectedShulker.packetRotationYawContrustuctor.invoke(this.livingEntity, UtilMath.toPackedByte(yaw));
        if (this.isSpawned) {
            NMSPlayer.sendPacketExclude(list, this.packetYawRotation);
        }
        this.yaw = yaw;
        return this;
    }
    
    public ReflectedShulker spawnLivingEntity() {
        this.packets = NMSPlayer.sendConstantPacket(this.packetSpawn, this.packetYawRotation, this.packetPitchRotation);
        this.isSpawned = true;
        return this;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void removeLivingEntity() {
        NMSPlayer.destroyEntity(this.ID);
        if (this.packets != null) {
            NMSPlayer.getPacketsToSend().removeAll(this.packets);
        }
        this.isSpawned = false;
    }
    
    public Entity getBukkitEntity() {
        return this.entityBukkit;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public Object getLivingEntity() {
        return this.livingEntity;
    }
    
    public int getID() {
        return this.ID;
    }
}
