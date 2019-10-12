package net.heroyn.heroynserverapi.nms.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("unused")
public class ReflectedArmorStand
{
    private static Class<?> craftItemStackClass = Reflection.getClass("{obc}.inventory.CraftItemStack");
    private static Class<?> packetEquipClass = Reflection.getClass("{nms}.PacketPlayOutEntityEquipment");
    private static Class<?> packetTeleportClass = Reflection.getMinecraftClass("PacketPlayOutEntityTeleport");
    private static Class<?> packetRotationClass = Reflection.getMinecraftClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
    private static Class<?> nmsWorldField = Reflection.getClass("{nms}.World");
    private static Class<?> packetSpawnEntityLivingClass = Reflection.getClass("{nms}.PacketPlayOutSpawnEntityLiving");
    private static Class<?> entityArmorStandClass = Reflection.getClass("{nms}.EntityArmorStand");
    private static Class<?> entityClass = Reflection.getClass("{nms}.Entity");
    private static Class<?> entityLivingClass = Reflection.getClass("{nms}.EntityLiving");
    private static Class<?> itemStackClass = Reflection.getMinecraftClass("ItemStack");
    private static Class<?> enumItemSlotClass = Reflection.getMinecraftClass("EnumItemSlot");
    private static Class<?> vector3fClass = Reflection.getMinecraftClass("Vector3f");
    private static Class<?> datawatcherClass  = Reflection.getMinecraftClass("DataWatcher");
    private static Class<?> nbtTagCompoundClass  = Reflection.getMinecraftClass("NBTTagCompound");
    private static Reflection.MethodInvoker getIdMethod = Reflection.getMethod(ReflectedArmorStand.entityClass, "getId", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker asNMSCopyMethod = Reflection.getMethod(ReflectedArmorStand.craftItemStackClass, "asNMSCopy", ItemStack.class);
    private static Reflection.MethodInvoker getHandleWorldMethod  = Reflection.getMethod("{obc}.CraftWorld", "getHandle", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setPositionMethod = Reflection.getMethod(ReflectedArmorStand.entityClass, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    private static Reflection.MethodInvoker setCustomeNameMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setCustomName", String.class);
    private static Reflection.MethodInvoker setCustomeNameVisibleMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setCustomNameVisible", Boolean.TYPE);
    private static Reflection.MethodInvoker setInvisibleMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setInvisible", Boolean.TYPE);
    private static Reflection.MethodInvoker setSmallMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setSmall", Boolean.TYPE);
    private static Reflection.MethodInvoker setHeadPoseMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setHeadPose", ReflectedArmorStand.vector3fClass);
    private static Reflection.MethodInvoker setRightArmPosePoseMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setRightArmPose", ReflectedArmorStand.vector3fClass);
    private static Reflection.MethodInvoker setArmsMethod = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setArms", Boolean.TYPE);
    private static Reflection.MethodInvoker setBasePlateMethod  = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setBasePlate", Boolean.TYPE);
    private static Reflection.MethodInvoker setMarkerMethod  = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "setMarker", Boolean.TYPE);
    private static Reflection.MethodInvoker getBukkitEntityMethod  = Reflection.getMethod(ReflectedArmorStand.entityClass, "getBukkitEntity", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker setEntityNBTTag = Reflection.getMethod(ReflectedArmorStand.entityClass, "f", ReflectedArmorStand.nbtTagCompoundClass);
    private static Reflection.MethodInvoker setArmorStandNBTTag = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "a", ReflectedArmorStand.nbtTagCompoundClass);
    private static Reflection.MethodInvoker getEquipmentMethod  = Reflection.getMethod(ReflectedArmorStand.entityArmorStandClass, "getEquipment", ReflectedArmorStand.enumItemSlotClass);
    private static Reflection.ConstructorInvoker packetmetaConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutEntityMetadata"), Integer.TYPE, ReflectedArmorStand.datawatcherClass, Boolean.TYPE);
    private static Reflection.ConstructorInvoker armorStandConstructor = Reflection.getConstructor(ReflectedArmorStand.entityArmorStandClass, ReflectedArmorStand.nmsWorldField);
    private static Reflection.ConstructorInvoker packetEquipConstructor = Reflection.getConstructor(ReflectedArmorStand.packetEquipClass, (Class<?>[])new Class[0]);
    private static Reflection.ConstructorInvoker packetSpawnConstructor = Reflection.getConstructor(ReflectedArmorStand.packetSpawnEntityLivingClass, ReflectedArmorStand.entityLivingClass);
    private static Reflection.ConstructorInvoker packetRotationContrustuctor = Reflection.getConstructor(ReflectedArmorStand.packetRotationClass, Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE);
    private static Reflection.ConstructorInvoker packetTeleportContrustuctor  = Reflection.getConstructor(ReflectedArmorStand.packetTeleportClass, ReflectedArmorStand.entityClass);
    private static Reflection.ConstructorInvoker vector3fConstructor  = Reflection.getConstructor(ReflectedArmorStand.vector3fClass, Float.TYPE, Float.TYPE, Float.TYPE);
    private static Reflection.FieldAccessor<?> IDPacketEquipField = Reflection.getField(ReflectedArmorStand.packetEquipClass, (Class<?>)Integer.TYPE, 0);
    private static Reflection.FieldAccessor<?> enumItemSlotPacketEquipField = Reflection.getField(ReflectedArmorStand.packetEquipClass, ReflectedArmorStand.enumItemSlotClass, 0);
    private static Reflection.FieldAccessor<?> itemStackPacketEquipField = Reflection.getField(ReflectedArmorStand.packetEquipClass, ReflectedArmorStand.itemStackClass, 0);
    private static Reflection.FieldAccessor<?> datawatcherField = Reflection.getField(ReflectedArmorStand.entityClass, "datawatcher", ReflectedArmorStand.datawatcherClass);
    private Object packetSpawn;
    private Entity bukkitEntity;
    private List<Object> packetEquip;
    private Object packetRotation;
    private List<Object> packets;
    private int ID;
    private Location location;
    private String displayName;
    private boolean visible;
	private boolean hide;
    private boolean small;
    private boolean arms;
    private boolean marker;
    private boolean basePlate;
    private float yaw;
    private float pitch;
    private Object armorStand;
    
    public ReflectedArmorStand(final Location location) {
        this.packetEquip = new ArrayList<Object>();
        this.displayName = "";
        this.visible = true;
        this.hide = false;
        this.small = false;
        this.arms = false;
        this.marker = false;
        this.basePlate = false;
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.location = location;
        this.armorStand = ReflectedArmorStand.armorStandConstructor.invoke(ReflectedArmorStand.getHandleWorldMethod.invoke(location.getWorld(), new Object[0]));
        ReflectedArmorStand.setPositionMethod.invoke(this.armorStand, location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        this.packetSpawn = ReflectedArmorStand.packetSpawnConstructor.invoke(this.armorStand);
        this.ID = (int)ReflectedArmorStand.getIdMethod.invoke(this.armorStand, new Object[0]);
        this.bukkitEntity = (Entity)ReflectedArmorStand.getBukkitEntityMethod.invoke(this.armorStand, new Object[0]);
    }
    
    public void updateName(final String s) {
        ReflectedArmorStand.setCustomeNameMethod.invoke(this.armorStand, CC.colored(s));
        NMSPlayer.sendPacket(ReflectedArmorStand.packetmetaConstructor.invoke(this.ID, ReflectedArmorStand.datawatcherField.get(this.armorStand), true));
    }
    
    public void updateName(final String s, final Player player) {
        ReflectedArmorStand.setCustomeNameMethod.invoke(this.armorStand, CC.colored(s));
        NMSPlayer.sendPacket(player, ReflectedArmorStand.packetmetaConstructor.invoke(this.ID, ReflectedArmorStand.datawatcherField.get(this.armorStand), true));
    }
    
    public void updateName(final String s, final List<UUID> list) {
        ReflectedArmorStand.setCustomeNameMethod.invoke(this.armorStand, s);
        NMSPlayer.sendPacketExclude(list, ReflectedArmorStand.packetmetaConstructor.invoke(this.ID, ReflectedArmorStand.datawatcherField.get(this.armorStand), true));
    }
    
    public ReflectedArmorStand setVisible(final boolean visible) {
        ReflectedArmorStand.setInvisibleMethod.invoke(this.armorStand, !visible);
        this.visible = visible;
        return this;
    }
    
    public ReflectedArmorStand setSmall(final boolean small) {
        ReflectedArmorStand.setSmallMethod.invoke(this.armorStand, small);
        this.small = small;
        return this;
    }
    
    public ReflectedArmorStand setBasePlate(final boolean b) {
        ReflectedArmorStand.setBasePlateMethod.invoke(this.armorStand, b);
        this.basePlate = b;
        return this;
    }
    
    public ReflectedArmorStand setHeadPose(final EulerAngle eulerAngle) {
        final EulerAngle nms = this.toNMS(eulerAngle);
        ReflectedArmorStand.setHeadPoseMethod.invoke(this.armorStand, ReflectedArmorStand.vector3fConstructor.invoke((float)nms.getX(), (float)nms.getY(), (float)nms.getZ()));
        return this;
    }
    
    public ReflectedArmorStand setRightArmPose(final EulerAngle eulerAngle) {
        final EulerAngle nms = this.toNMS(eulerAngle);
        ReflectedArmorStand.setRightArmPosePoseMethod.invoke(this.armorStand, ReflectedArmorStand.vector3fConstructor.invoke((float)nms.getX(), (float)nms.getY(), (float)nms.getZ()));
        return this;
    }
    
    public ReflectedArmorStand setRotation(final float yaw, final float pitch) {
        this.packetRotation = ReflectedArmorStand.packetRotationContrustuctor.invoke(this.ID, UtilMath.toPackedByte(yaw), UtilMath.toPackedByte(pitch), false);
        this.yaw = yaw;
        this.pitch = pitch;
        NMSPlayer.sendPacket(this.packetRotation);
        return this;
    }
    
    public ReflectedArmorStand setRotation(final Player player, final float yaw, final float pitch) {
        this.packetRotation = ReflectedArmorStand.packetRotationContrustuctor.invoke(this.ID, UtilMath.toPackedByte(yaw), UtilMath.toPackedByte(pitch), false);
        this.yaw = yaw;
        this.pitch = pitch;
        NMSPlayer.sendPacket(player, this.packetRotation);
        return this;
    }
    
    public void updateMetadata() {
        NMSPlayer.sendPacket(ReflectedArmorStand.packetmetaConstructor.invoke(this.ID, ReflectedArmorStand.datawatcherField.get(this.armorStand), true));
    }
    
    public ReflectedArmorStand cloneNBTTag(final Object o) {
        ReflectedArmorStand.setEntityNBTTag.invoke(this.armorStand, o);
        ReflectedArmorStand.setArmorStandNBTTag.invoke(this.armorStand, o);
        for (int i = 0; i <= 5; ++i) {
            final Object invoke = ReflectedArmorStand.packetEquipConstructor.invoke(new Object[0]);
            ReflectedArmorStand.IDPacketEquipField.set(invoke, this.ID);
            ReflectedArmorStand.enumItemSlotPacketEquipField.set(invoke, ReflectedArmorStand.enumItemSlotClass.getEnumConstants()[i]);
            ReflectedArmorStand.itemStackPacketEquipField.set(invoke, ReflectedArmorStand.getEquipmentMethod.invoke(this.armorStand, ReflectedArmorStand.enumItemSlotClass.getEnumConstants()[i]));
            this.packetEquip.add(invoke);
        }
        return this;
    }
    
    public ReflectedArmorStand setEquipment(final int n, final ItemStack itemStack) {
        final Object invoke = ReflectedArmorStand.packetEquipConstructor.invoke(new Object[0]);
        ReflectedArmorStand.IDPacketEquipField.set(invoke, this.ID);
        ReflectedArmorStand.enumItemSlotPacketEquipField.set(invoke, ReflectedArmorStand.enumItemSlotClass.getEnumConstants()[n]);
        ReflectedArmorStand.itemStackPacketEquipField.set(invoke, ReflectedArmorStand.asNMSCopyMethod.invoke(ReflectedArmorStand.craftItemStackClass, itemStack));
        this.packetEquip.add(invoke);
        return this;
    }
    
    public ReflectedArmorStand setDisplayName(final String displayName) {
        ReflectedArmorStand.setCustomeNameMethod.invoke(this.armorStand, displayName);
        ReflectedArmorStand.setCustomeNameVisibleMethod.invoke(this.armorStand, true);
        this.displayName = displayName;
        return this;
    }
    
    public ReflectedArmorStand setMarker(final boolean marker) {
        ReflectedArmorStand.setMarkerMethod.invoke(this.armorStand, marker);
        this.marker = marker;
        return this;
    }
    
    public ReflectedArmorStand setArms(final boolean arms) {
        ReflectedArmorStand.setArmsMethod.invoke(this.armorStand, arms);
        this.arms = arms;
        return this;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
    
    public int getID() {
        return this.ID;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public boolean isSmall() {
        return this.small;
    }
    
    public void teleport(final Player player, final Location location) {
        ReflectedArmorStand.setPositionMethod.invoke(this.armorStand, location.getX(), location.getY(), location.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(player, ReflectedArmorStand.packetTeleportContrustuctor.invoke(this.armorStand));
        this.location = location;
    }
    
    public void faketeleport(final Player player, final Location location) {
        ReflectedArmorStand.setPositionMethod.invoke(this.armorStand, location.getX(), location.getY(), location.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(player, ReflectedArmorStand.packetTeleportContrustuctor.invoke(this.armorStand));
    }
    
    public void teleport(final Location location) {
        ReflectedArmorStand.setPositionMethod.invoke(this.armorStand, location.getX(), location.getY(), location.getZ(), this.yaw, this.pitch);
        NMSPlayer.sendPacket(ReflectedArmorStand.packetTeleportContrustuctor.invoke(this.armorStand));
        this.location = location;
    }
    
    public void hide() {
        NMSPlayer.destroyEntity(this.ID);
        this.hide = true;
    }
    
    public void hide(final Player player) {
        NMSPlayer.destroyEntity(player, this.ID);
        this.hide = true;
    }
    
    public void show(final Player player) {
        NMSPlayer.sendPacket(player, this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendPacket(player, o));
        this.hide = false;
    }
    
    public void show() {
        NMSPlayer.sendPacket(this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendPacket(o));
        this.hide = false;
    }
    
    public void remove() {
        NMSPlayer.destroyEntity(this.ID);
        if (this.packets != null) {
            NMSPlayer.getPacketsToSend().removeAll(this.packets);
        }
    }
    
    public ReflectedArmorStand spawnArmorStand() {
        NMSPlayer.sendPacketNearby(this.location, this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendPacketNearby(this.location, o));
        return this;
    }
    
    public ReflectedArmorStand spawnConstantArmorStand() {
        this.packets = NMSPlayer.sendConstantPacket(this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendConstantPacket(o));
        this.packets.addAll(this.packetEquip);
        return this;
    }
    
    public ReflectedArmorStand spawnArmorStand(final Player player) {
        NMSPlayer.sendPacket(player, this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendPacket(o));
        return this;
    }
    
    public ReflectedArmorStand spawnArmorStandExlude(final Player player) {
        NMSPlayer.sendPacketExclude(Bukkit.getOnlinePlayers(), player, this.packetSpawn, this.packetRotation);
        this.packetEquip.forEach(o -> NMSPlayer.sendPacketExclude(Bukkit.getOnlinePlayers(), player, o));
        return this;
    }
    
    public Entity getBukkitEntity() {
        return this.bukkitEntity;
    }
    
    private EulerAngle toNMS(final EulerAngle eulerAngle) {
        return new EulerAngle((double)(float)Math.toDegrees(eulerAngle.getX()), (double)(float)Math.toDegrees(eulerAngle.getY()), (double)(float)Math.toDegrees(eulerAngle.getZ()));
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public float getYaw() {
        return this.yaw;
    }
}
