package net.heroyn.heroynserverapi.nms.other;

import java.lang.reflect.Array;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
import net.heroyn.heroynserverapi.utils.Skin;
import net.heroyn.heroynserverapi.utils.UtilJava;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("rawtypes")
public class NPC
{
    private static Class<?> datawatcherSerializerClass;
    private static Class<?> datawatcherClass;
    private static Class<?> datawatcherObjectClass;
    private static Class<?> playerClass;
    private static Class<?> entityClass;
    private static Class<?> minecraftServerClass;
    private static Class<?> enumInfoClass;
    private static Class<?> interactClass;
    private static Class<?> worldClass;
    private static Class<?> itemStackClass;
    private static Class<?> enumItemSlotClass;
    private static Class<?> packetTeleportClass;
    private static Reflection.MethodInvoker getHandleWorldMethod;
    private static Reflection.MethodInvoker getServerMethod;
    private static Reflection.ConstructorInvoker entityPlayerConstructor;
    private static Reflection.ConstructorInvoker interactManagerConstructor;
    private static Reflection.MethodInvoker setPositionMethod;
    private static Reflection.MethodInvoker getIdMethod;
    private static Reflection.MethodInvoker registerDataWatcherMethod;
    private static Class<?> packetEquipClass;
    private static Class<?> packetRotationPitchClass;
    private static Class<?> packetMetaClass;
    private static Reflection.ConstructorInvoker packetRotationPitchContrustuctor;
    private static Class<?> packetRotationYawClass;
    private static Reflection.ConstructorInvoker packetRotationYawContrustuctor;
    private static Reflection.ConstructorInvoker spawnConstructor;
    private static Reflection.ConstructorInvoker infoConstructor;
    private static Class<?> craftItemStackClass;
    private static Reflection.MethodInvoker asNMSCopyMethod;
    private static Reflection.ConstructorInvoker packetEquipConstructor;
    private static Class<?> packetDestroyClass;
    private static Reflection.ConstructorInvoker packetDestroyConstructor;
    private static Reflection.ConstructorInvoker datawatcherConstructor;
    private static Reflection.ConstructorInvoker datawatcherObjectConstructor;
    private static Reflection.ConstructorInvoker packetMetaConstructor;
    private static Reflection.ConstructorInvoker packetTeleportContrustuctor;
	private static Reflection.FieldAccessor datawatcherRegistry;
    private static Reflection.FieldAccessor<?> IDPacketEquipField;
    private static Reflection.FieldAccessor<?> enumItemSlotPacketEquipField;
    private static Reflection.FieldAccessor<?> itemStackPacketEquipField;
    private static Reflection.FieldAccessor<?> packetDestroyIDField;
    private Location l;
    private Object server;
    private Object world;
    private Object npc;
    private Object interact;
    private Object packetSpawn;
    private Object packetInfoAdd;
    private Object packetInfoRemove;
    private Object packetEquip;
    private Object pitch;
    private Object yaw;
    private Object packetDestroy;
    private Object datawatcherObject;
    private Object packetMeta;
    private GameProfile profile;
    private int id;
    
    static {
        NPC.datawatcherSerializerClass = Reflection.getClass("{nms}.DataWatcherSerializer");
        NPC.datawatcherClass = Reflection.getClass("{nms}.DataWatcher");
        NPC.datawatcherObjectClass = Reflection.getClass("{nms}.DataWatcherObject");
        NPC.playerClass = Reflection.getMinecraftClass("EntityPlayer");
        NPC.entityClass = Reflection.getMinecraftClass("Entity");
        NPC.minecraftServerClass = Reflection.getClass("{nms}.MinecraftServer");
        NPC.enumInfoClass = Reflection.getClass("{nms}.PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
        NPC.interactClass = Reflection.getClass("{nms}.PlayerInteractManager");
        NPC.worldClass = Reflection.getClass("{nms}.WorldServer");
        NPC.itemStackClass = Reflection.getMinecraftClass("ItemStack");
        NPC.enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
        NPC.packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
        NPC.getHandleWorldMethod = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
        NPC.getServerMethod = Reflection.getMethod(NPC.minecraftServerClass, "getServer", (Class<?>[])new Class[0]);
        NPC.entityPlayerConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("EntityPlayer"), NPC.minecraftServerClass, NPC.worldClass, GameProfile.class, NPC.interactClass);
        NPC.interactManagerConstructor = Reflection.getConstructor(NPC.interactClass, Reflection.getClass("{nms}.World"));
        NPC.setPositionMethod = Reflection.getMethod(NPC.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        NPC.getIdMethod = Reflection.getMethod(NPC.entityClass, "getId", (Class<?>[])new Class[0]);
        NPC.registerDataWatcherMethod = Reflection.getMethod(NPC.datawatcherClass, "register", NPC.datawatcherObjectClass, Object.class);
        NPC.packetEquipClass = Reflection.getMinecraftClass("PacketPlayOutEntityEquipment");
        NPC.packetRotationPitchClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
        NPC.packetMetaClass = Reflection.getMinecraftClass("PacketPlayOutEntityMetadata");
        NPC.packetRotationPitchContrustuctor = Reflection.getConstructor(NPC.packetRotationPitchClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
        NPC.packetRotationYawClass = Reflection.getMinecraftClass("PacketPlayOutEntityHeadRotation");
        NPC.packetRotationYawContrustuctor = Reflection.getConstructor(NPC.packetRotationYawClass, NPC.entityClass, Byte.TYPE);
        NPC.spawnConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutNamedEntitySpawn"), Reflection.getMinecraftClass("EntityHuman"));
        NPC.infoConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), NPC.enumInfoClass, Array.newInstance(NPC.playerClass, 0).getClass());
        NPC.craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
        NPC.asNMSCopyMethod = Reflection.getMethod(NPC.craftItemStackClass, "asNMSCopy", ItemStack.class);
        NPC.packetEquipConstructor = Reflection.getConstructor(NPC.packetEquipClass, (Class<?>[])new Class[0]);
        NPC.packetDestroyClass = Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
        NPC.packetDestroyConstructor = Reflection.getConstructor(NPC.packetDestroyClass, (Class<?>[])new Class[0]);
        NPC.datawatcherConstructor = Reflection.getConstructor(NPC.datawatcherClass, NPC.entityClass);
        NPC.datawatcherObjectConstructor = Reflection.getConstructor(NPC.datawatcherObjectClass, Integer.TYPE, NPC.datawatcherSerializerClass);
        NPC.packetMetaConstructor = Reflection.getConstructor(NPC.packetMetaClass, Integer.TYPE, NPC.datawatcherClass, Boolean.TYPE);
        NPC.packetTeleportContrustuctor = Reflection.getConstructor(NPC.packetTeleportClass, NPC.entityClass);
        NPC.datawatcherRegistry = Reflection.getField(Reflection.getMinecraftClass("DataWatcherRegistry"), Object.class, 1);
        NPC.IDPacketEquipField = Reflection.getField(NPC.packetEquipClass, (Class<?>)Integer.TYPE, 0);
        NPC.enumItemSlotPacketEquipField = Reflection.getField(NPC.packetEquipClass, NPC.enumItemSlotClass, 0);
        NPC.itemStackPacketEquipField = Reflection.getField(NPC.packetEquipClass, NPC.itemStackClass, 0);
        NPC.packetDestroyIDField = Reflection.getField(NPC.packetDestroyClass, (Class<?>)Object.class, 0);
    }
    
    public NPC(final Location l, final String s, final Skin skin) {
        this.l = l;
        this.server = NPC.getServerMethod.invoke(NPC.minecraftServerClass, new Object[0]);
        this.world = NPC.getHandleWorldMethod.invoke(l.getWorld(), new Object[0]);
        this.interact = NPC.interactManagerConstructor.invoke(this.world);
        this.npc = NPC.entityPlayerConstructor.invoke(this.server, this.world, new ProfileLoader(s).loadProfile(skin), this.interact);
        NPC.setPositionMethod.invoke(this.npc, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        this.id = (int)NPC.getIdMethod.invoke(this.npc, new Object[0]);
        this.packetSpawn = NPC.spawnConstructor.invoke(this.npc);
        final Object array = UtilJava.getArray(NPC.playerClass, this.npc);
        this.packetInfoAdd = NPC.infoConstructor.invoke(NPC.enumInfoClass.getEnumConstants()[0], array);
        this.packetInfoRemove = NPC.infoConstructor.invoke(NPC.enumInfoClass.getEnumConstants()[4], array);
        this.pitch = NPC.packetRotationPitchContrustuctor.invoke(this.id, UtilMath.toPackedByte(l.getYaw()), UtilMath.toPackedByte(l.getPitch()), false);
        this.yaw = NPC.packetRotationYawContrustuctor.invoke(this.npc, UtilMath.toPackedByte(l.getYaw()));
        this.packetEquip = NPC.packetEquipConstructor.invoke(new Object[0]);
        NPC.IDPacketEquipField.set(this.packetEquip, this.id);
        this.packetDestroy = NPC.packetDestroyConstructor.invoke(new Object[0]);
        NPC.packetDestroyIDField.set(this.packetDestroy, new int[] { this.id });
    }
    
    public NPC(final Location l, final Player player) {
        this.l = l;
        final GameProfile gameProfile = Reflection.getField(Reflection.getMinecraftClass("EntityHuman"), GameProfile.class, 0).get(NMSPlayer.getHandle(player));
        this.profile = new GameProfile(UUID.randomUUID(), player.getName());
        Reflection.getField(GameProfile.class, PropertyMap.class, 0).set(this.profile, Reflection.getField(GameProfile.class, PropertyMap.class, 0).get(gameProfile));
        this.server = NPC.getServerMethod.invoke(NPC.minecraftServerClass, new Object[0]);
        this.world = NPC.getHandleWorldMethod.invoke(l.getWorld(), new Object[0]);
        this.interact = NPC.interactManagerConstructor.invoke(this.world);
        this.npc = NPC.entityPlayerConstructor.invoke(this.server, this.world, this.profile, this.interact);
        NPC.setPositionMethod.invoke(this.npc, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
        this.id = (int)NPC.getIdMethod.invoke(this.npc, new Object[0]);
        this.packetSpawn = NPC.spawnConstructor.invoke(this.npc);
        final Object array = UtilJava.getArray(NPC.playerClass, this.npc);
        this.packetInfoAdd = NPC.infoConstructor.invoke(NPC.enumInfoClass.getEnumConstants()[0], array);
        this.packetInfoRemove = NPC.infoConstructor.invoke(NPC.enumInfoClass.getEnumConstants()[4], array);
        this.pitch = NPC.packetRotationPitchContrustuctor.invoke(this.id, UtilMath.toPackedByte(l.getYaw()), UtilMath.toPackedByte(l.getPitch()), false);
        this.yaw = NPC.packetRotationYawContrustuctor.invoke(this.npc, UtilMath.toPackedByte(l.getYaw()));
        this.packetEquip = NPC.packetEquipConstructor.invoke(new Object[0]);
        NPC.IDPacketEquipField.set(this.packetEquip, this.id);
        this.packetDestroy = NPC.packetDestroyConstructor.invoke(new Object[0]);
        NPC.packetDestroyIDField.set(this.packetDestroy, new int[] { this.id });
        final Object invoke = NPC.datawatcherConstructor.invoke(this.npc);
        this.datawatcherObject = NPC.datawatcherObjectConstructor.invoke(12, NPC.datawatcherRegistry.get(Reflection.getMinecraftClass("DataWatcherRegistry")));
        NPC.registerDataWatcherMethod.invoke(invoke, this.datawatcherObject, (byte)(-1));
        this.packetMeta = NPC.packetMetaConstructor.invoke(this.id, invoke, true);
    }
    
    public void teleport(final Location l) {
        NPC.setPositionMethod.invoke(this.npc, l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        NMSPlayer.sendPacket(NPC.packetTeleportContrustuctor.invoke(this.npc));
        this.l = l;
    }
    
    public void setDirection(final Location location) {
        final Location clone = location.clone();
        clone.setDirection(location.toVector().subtract(this.l.toVector()));
        this.pitch = NPC.packetRotationPitchContrustuctor.invoke(this.id, UtilMath.toPackedByte(clone.getYaw()), UtilMath.toPackedByte(clone.getPitch()), false);
        this.yaw = NPC.packetRotationYawContrustuctor.invoke(this.npc, UtilMath.toPackedByte(clone.getYaw()));
        NMSPlayer.sendPacket(this.pitch, this.yaw);
    }
    
    public void setDirection(final float n, final float n2) {
        this.pitch = NPC.packetRotationPitchContrustuctor.invoke(this.id, UtilMath.toPackedByte(n), UtilMath.toPackedByte(n2), false);
        this.yaw = NPC.packetRotationYawContrustuctor.invoke(this.npc, UtilMath.toPackedByte(n));
        NMSPlayer.sendPacket(this.pitch, this.yaw);
    }
    
    public void equip(final int n, final ItemStack itemStack) {
        NPC.enumItemSlotPacketEquipField.set(this.packetEquip, NPC.enumItemSlotClass.getEnumConstants()[n]);
        NPC.itemStackPacketEquipField.set(this.packetEquip, NPC.asNMSCopyMethod.invoke(NPC.craftItemStackClass, itemStack));
        NMSPlayer.sendPacket(this.packetEquip);
    }
    
    public void spawnNPC() {
        if (this.packetMeta == null) {
            NMSPlayer.sendPacket(this.packetInfoAdd, this.packetSpawn);
        }
        else {
            NMSPlayer.sendPacket(this.packetInfoAdd, this.packetSpawn, this.packetMeta);
        }
        new BukkitRunnable() {
            public void run() {
                NMSPlayer.sendPacket(NPC.this.packetInfoRemove, NPC.this.pitch, NPC.this.yaw);
            }
        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 5L);
    }
    
    public Location getLocation() {
        return this.l;
    }
    
    public void remove() {
        NMSPlayer.sendPacket(this.packetDestroy);
    }
}
