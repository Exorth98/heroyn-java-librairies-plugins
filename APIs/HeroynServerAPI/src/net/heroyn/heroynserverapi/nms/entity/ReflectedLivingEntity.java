package net.heroyn.heroynserverapi.nms.entity;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
import net.heroyn.heroynserverapi.utils.UtilMath;

public class ReflectedLivingEntity
{
    private static Class<?> packetRotationPitchClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
    private static Class<?> packetSpawnEntityLivingClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
    private static Class<?> packetRotationYawClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
    private static Class<?> nmsWorldClass = Reflection.getClass("{nms}.World");
    private static Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static Class<?> entityLivingClass = Reflection.getClass("{nms}.EntityLiving");
    private static Class<?> packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    private static Class<?> entityAgeableClass = Reflection.getClass("{nms}.EntityAgeable");
    private Class<?> entityTypeClass;
    private static Reflection.ConstructorInvoker packetSpawnConstructor = Reflection.getConstructor(ReflectedLivingEntity.packetSpawnEntityLivingClass, ReflectedLivingEntity.entityLivingClass);
    private static Reflection.ConstructorInvoker packetRotationPitchContrustuctor = Reflection.getConstructor(ReflectedLivingEntity.packetRotationPitchClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
    private static Reflection.ConstructorInvoker packetRotationYawContrustuctor = Reflection.getConstructor(ReflectedLivingEntity.packetRotationYawClass, ReflectedLivingEntity.entityClass, Byte.TYPE);
    private static Reflection.ConstructorInvoker packetTeleportContrustuctor  = Reflection.getConstructor(ReflectedLivingEntity.packetTeleportClass, ReflectedLivingEntity.entityClass);
    private static Reflection.MethodInvoker getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setPositionMethod = Reflection.getMethod(ReflectedLivingEntity.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    private static Reflection.MethodInvoker getIdMethod = Reflection.getMethod(ReflectedLivingEntity.entityClass, "getId", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker getBukkitEntityMethod  = Reflection.getMethod(ReflectedLivingEntity.entityClass, "getBukkitEntity", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setInvisibleMethod  = Reflection.getMethod(ReflectedLivingEntity.entityClass, "setInvisible", Boolean.TYPE);
    private static Reflection.MethodInvoker setAgeMethod  = Reflection.getMethod(ReflectedLivingEntity.entityAgeableClass, "setAge", Integer.TYPE);
    private List<Object> packets;
    private Location location;
    private Object livingEntity;
    private Entity entityBukkit;
    private EntityType entityType;
    private boolean isSpawned;
    private int ID;
    private float yaw;
    private float pitch;
    private Object packetSpawn;
    private Object packetYawRotation;
    private Object packetPitchRotation;
    
    public ReflectedLivingEntity(final Location location, final EntityType entityType) {
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.location = location;
        this.entityType = entityType;
        this.entityTypeClass = Reflection.getMinecraftClass("Entity" + entityType.getEntityClass().getSimpleName());
        this.livingEntity = Reflection.getConstructor(Reflection.getMinecraftClass("Entity" + entityType.getEntityClass().getSimpleName()), ReflectedLivingEntity.nmsWorldClass).invoke(ReflectedLivingEntity.getHandleWorldMethod.invoke(location.getWorld(), new Object[0]));
        ReflectedLivingEntity.setPositionMethod.invoke(this.livingEntity, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.ID = (int)ReflectedLivingEntity.getIdMethod.invoke(this.livingEntity, new Object[0]);
        this.packetSpawn = ReflectedLivingEntity.packetSpawnConstructor.invoke(this.livingEntity);
        this.entityBukkit = (Entity)ReflectedLivingEntity.getBukkitEntityMethod.invoke(this.livingEntity, new Object[0]);
    }
    
    public void setSize(final int n) {
        if (this.entityType == EntityType.SLIME || this.entityType == EntityType.MAGMA_CUBE) {
            final String packageName = this.getPackageName();
            if (packageName.equalsIgnoreCase("v1_11_R1") || packageName.equalsIgnoreCase("v1_11_R1") || packageName.equalsIgnoreCase("v1_12_R1") || packageName.equalsIgnoreCase("v1_12_R2")) {
                Reflection.getMethod(this.entityTypeClass, "setSize", Integer.TYPE, Boolean.TYPE).invoke(this.livingEntity, n, false);
            }
            else {
                Reflection.getMethod(this.entityTypeClass, "setSize", Integer.TYPE).invoke(this.livingEntity, n);
            }
        }
    }
    
    public void setBaby() {
        if (ReflectedLivingEntity.entityAgeableClass.isAssignableFrom(this.entityTypeClass)) {
            ReflectedLivingEntity.setAgeMethod.invoke(this.livingEntity, -1);
        }
    }
    
    public void setVisible(final boolean b) {
        ReflectedLivingEntity.setInvisibleMethod.invoke(this.livingEntity, !b);
    }
    
    public ReflectedLivingEntity teleport(final Location location, final float n, final float n2) {
        ReflectedLivingEntity.setPositionMethod.invoke(this.livingEntity, location.getX(), location.getY(), location.getZ(), n, n2);
        NMSPlayer.sendPacketNearby(location, ReflectedLivingEntity.packetTeleportContrustuctor.invoke(this.livingEntity));
        this.location = location;
        return this;
    }
    
    public ReflectedLivingEntity teleport(final Location location, final Player player) {
        ReflectedLivingEntity.setPositionMethod.invoke(this.livingEntity, location.getX(), location.getY(), location.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(player, ReflectedLivingEntity.packetTeleportContrustuctor.invoke(this.livingEntity));
        return this;
    }
    
    public ReflectedLivingEntity setYawRotation(final float yaw) {
        this.packetYawRotation = ReflectedLivingEntity.packetRotationYawContrustuctor.invoke(this.livingEntity, UtilMath.toPackedByte(yaw));
        if (this.isSpawned) {
            NMSPlayer.sendPacketNearby(this.location, this.packetYawRotation);
        }
        this.yaw = yaw;
        return this;
    }
    
    public ReflectedLivingEntity setPitchRotation(final float pitch) {
        this.packetPitchRotation = ReflectedLivingEntity.packetRotationPitchContrustuctor.invoke(this.ID, UtilMath.toPackedByte(this.yaw), UtilMath.toPackedByte(pitch), false);
        if (this.isSpawned) {
            NMSPlayer.sendPacketNearby(this.location, this.packetPitchRotation);
        }
        this.pitch = pitch;
        return this;
    }
    
    public ReflectedLivingEntity spawnLivingEntity() {
        this.packets = NMSPlayer.sendConstantPacket(this.packetSpawn, this.packetYawRotation, this.packetPitchRotation);
        this.isSpawned = true;
        return this;
    }
    
    public ReflectedLivingEntity spawnLivingEntity(final Player player) {
        NMSPlayer.sendPacket(player, this.packetSpawn, this.packetYawRotation, this.packetPitchRotation);
        this.isSpawned = true;
        return this;
    }
    
    private String getPackageName() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf(46) + 1);
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
