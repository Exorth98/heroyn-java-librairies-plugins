package net.heroyn.heroynserverapi.nms.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.utils.UtilLocation;

public class NMSPlayer
{
    private static List<Object> packetsToSend  = new ArrayList<Object>();
    private static Map<UUID, List<Object>> packetsToSendPlayer  = new HashMap<UUID, List<Object>>();
    private static Map<UUID, List<Object>> packetsToSendToOthers  = new HashMap<UUID, List<Object>>();
    private static Class<?> packetSpawnNamedClass  = Reflection.getClass("{nms}.PacketPlayOutNamedEntitySpawn");
    private static Class<?> entityHumanClass  = Reflection.getClass("{nms}.EntityHuman");
    private static Class<?> packetDestroyClass  = Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
    private static Class<?> packetEquipClass  = Reflection.getMinecraftClass("PacketPlayOutEntityEquipment");
    private static Class<?> enumItemSlotClass  = Reflection.getMinecraftClass("EnumItemSlot");
    private static Class<?> craftItemStackClass  = Reflection.getClass("{obc}.inventory.CraftItemStack");
    private static Class<?> itemStackClass  = Reflection.getMinecraftClass("ItemStack");
    private static Reflection.FieldAccessor<?> playerConnectionField  = Reflection.getField("{nms}.EntityPlayer", "playerConnection", (Class<?>)Object.class);
    private static Reflection.FieldAccessor<?> IDPacketEquipField  = Reflection.getField(NMSPlayer.packetEquipClass, (Class<?>)Integer.TYPE, 0);
    private static Reflection.FieldAccessor<?> packetDestroyIDField  = Reflection.getField(NMSPlayer.packetDestroyClass, (Class<?>)Object.class, 0);
    private static Reflection.FieldAccessor<?> enumItemSlotPacketEquipField  = Reflection.getField(NMSPlayer.packetEquipClass, NMSPlayer.enumItemSlotClass, 0);
    private static Reflection.FieldAccessor<?> itemStackPacketEquipField  = Reflection.getField(NMSPlayer.packetEquipClass, NMSPlayer.itemStackClass, 0);
    private static Reflection.MethodInvoker getHandlePlayerMethod  = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker getHandleMethod = Reflection.getMethod("{obc}.entity.CraftEntity", "getHandle", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker getIdMethod = Reflection.getMethod("{nms}.Entity", "getId", (Class<?>[])new Class[0]);
    private static Reflection.MethodInvoker sendPacket = Reflection.getMethod("{nms}.PlayerConnection", "sendPacket", Reflection.getMinecraftClass("Packet"));
    private static Reflection.MethodInvoker asNMSCopyMethod  = Reflection.getMethod(NMSPlayer.craftItemStackClass, "asNMSCopy", ItemStack.class);
    private static Reflection.ConstructorInvoker packetSpawnHumanConstructor  = Reflection.getConstructor(NMSPlayer.packetSpawnNamedClass, NMSPlayer.entityHumanClass);
    private static Reflection.ConstructorInvoker packetEquipConstructor  = Reflection.getConstructor(NMSPlayer.packetEquipClass, (Class<?>[])new Class[0]);
    private static Reflection.ConstructorInvoker packetDestroyConstructor  = Reflection.getConstructor(NMSPlayer.packetDestroyClass, (Class<?>[])new Class[0]);
    
    
    public static void sendPacket(final Player player, final Object... array) {
        for (final Object o : array) {
            if (o != null) {
                NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player, new Object[0])), o);
            }
        }
    }
    
    public static void sendPacket(final List<Player> list, final Object... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
                final Object o = array[i];
                list.forEach(player -> NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player, new Object[0])), o));
            }
        }
    }
    
    public static void sendPacketExclude(final Collection<? extends Player> collection, final Player player2, final Object... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
            	final Object o = array[i];
                collection.stream().filter(player -> player != null && player2 != null && !player.equals(player2)).forEach(player3 -> NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player3, new Object[0])), o));
            }
        }
    }
    
    public static void sendPacketExclude(final Player player, final Object... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
            	final Object o = array[i];
                Bukkit.getOnlinePlayers().stream().filter(player2 -> !player2.equals(player)).forEach(player3 -> NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player3, new Object[0])), o));
            }
        }
    }
    
    public static void sendPacketExclude(final List<UUID> list, final Object... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
            	final Object o = array[i];
                Bukkit.getOnlinePlayers().stream().filter(player -> !list.contains(player.getUniqueId())).forEach(player2 -> {
                    if (player2 != null) {
                        NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player2, new Object[0])), o);
                    }
                    return;
                });
            }
        }
    }
    
    public static List<Object> sendConstantPacketExclude(final Collection<? extends Player> collection, final Player player, final Object... array) {
        NMSPlayer.packetsToSendToOthers.putIfAbsent(player.getUniqueId(), new ArrayList<Object>());
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
            	final Object o = array[i];
                collection.stream().filter(player2 -> !player2.equals(player)).forEach(player3 -> {
                    NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player3, new Object[0])), o);
                    NMSPlayer.packetsToSendToOthers.get(player.getUniqueId()).add(o);
                    return;
                });
            }
        }
        return NMSPlayer.packetsToSendToOthers.get(player);
    }
    
    public static void sendPacket(final Object... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] != null) {
            	final Object o = array[i];
                Bukkit.getOnlinePlayers().forEach(player -> NMSPlayer.sendPacket.invoke(NMSPlayer.playerConnectionField.get(NMSPlayer.getHandleMethod.invoke(player, new Object[0])), o));
            }
        }
    }
    
    public static List<Object> sendConstantPacket(final Object... array) {
        sendPacket(array);
        addPacket(array);
        return new ArrayList<Object>(Arrays.asList(array));
    }
    
    public static List<Object> sendConstantPacketNearby(final Location location, final Object... array) {
        sendPacketNearby(location, array);
        addPacket(array);
        return new ArrayList<Object>(Arrays.asList(array));
    }
    
    public static void sendPacketNearby(final Location location, final Object... array) {
        UtilLocation.getClosestPlayersFromLocation(location, 64.0).forEach(p1 -> sendPacket(array));
    }
    
    public static Object equipPlayer(final Entity entity, final int n, final ItemStack itemStack, final boolean b) {
        final int intValue = (int)NMSPlayer.getIdMethod.invoke(NMSPlayer.getHandleMethod.invoke(entity, new Object[0]), new Object[0]);
        final Object invoke = NMSPlayer.packetEquipConstructor.invoke(new Object[0]);
        NMSPlayer.IDPacketEquipField.set(invoke, intValue);
        NMSPlayer.enumItemSlotPacketEquipField.set(invoke, NMSPlayer.enumItemSlotClass.getEnumConstants()[n]);
        NMSPlayer.itemStackPacketEquipField.set(invoke, NMSPlayer.asNMSCopyMethod.invoke(NMSPlayer.craftItemStackClass, itemStack));
        sendPacket(invoke);
        if (b) {
            addPacket(invoke);
        }
        return invoke;
    }
    
    public static Object equipPlayer(final Entity entity, final int n, final ItemStack itemStack) {
        final int intValue = (int)NMSPlayer.getIdMethod.invoke(NMSPlayer.getHandleMethod.invoke(entity, new Object[0]), new Object[0]);
        final Object invoke = NMSPlayer.packetEquipConstructor.invoke(new Object[0]);
        NMSPlayer.IDPacketEquipField.set(invoke, intValue);
        NMSPlayer.enumItemSlotPacketEquipField.set(invoke, NMSPlayer.enumItemSlotClass.getEnumConstants()[n]);
        NMSPlayer.itemStackPacketEquipField.set(invoke, NMSPlayer.asNMSCopyMethod.invoke(NMSPlayer.craftItemStackClass, itemStack));
        sendPacket(invoke);
        return invoke;
    }
    
    public static Object getHandle(final Player player) {
        return NMSPlayer.getHandlePlayerMethod.invoke(player, new Object[0]);
    }
    
    public static void respawnPlayer(final Player player, final Player player2) {
        sendPacket(player2, NMSPlayer.packetSpawnHumanConstructor.invoke(NMSPlayer.getHandleMethod.invoke(player, new Object[0])));
        equipPlayer((Entity)player, 0, player.getInventory().getItemInMainHand());
        equipPlayer((Entity)player, 1, player.getInventory().getItemInOffHand());
        equipPlayer((Entity)player, 2, player.getInventory().getBoots());
        equipPlayer((Entity)player, 3, player.getInventory().getLeggings());
        equipPlayer((Entity)player, 4, player.getInventory().getChestplate());
        equipPlayer((Entity)player, 5, player.getInventory().getHelmet());
    }
    
    public static void destroyEntity(final int... array) {
        final Object invoke = NMSPlayer.packetDestroyConstructor.invoke(new Object[0]);
        NMSPlayer.packetDestroyIDField.set(invoke, array);
        sendPacket(invoke);
    }
    
    public static void destroyEntity(final Player player, final int... array) {
        final Object invoke = NMSPlayer.packetDestroyConstructor.invoke(new Object[0]);
        NMSPlayer.packetDestroyIDField.set(invoke, array);
        sendPacket(player, invoke);
    }
    
    public static String getPlayerLanguage(final Player player) {
        return Reflection.getField(Reflection.getMinecraftClass("EntityPlayer"), "locale", String.class).get(getHandle(player));
    }
    
    public static void addPacket(final Object... array) {
        NMSPlayer.packetsToSend.addAll(Arrays.asList(array));
    }
    
    public static Map<UUID, List<Object>> getPacketsToSendToOthers() {
        return NMSPlayer.packetsToSendToOthers;
    }
    
    public static List<Object> getPacketsToSend() {
        return NMSPlayer.packetsToSend;
    }
    
    public static Map<UUID, List<Object>> getPacketsToSendPlayer() {
        return NMSPlayer.packetsToSendPlayer;
    }
}
