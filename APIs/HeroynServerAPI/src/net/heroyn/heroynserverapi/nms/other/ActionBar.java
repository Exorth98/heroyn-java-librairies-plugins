package net.heroyn.heroynserverapi.nms.other;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.nms.player.NMSPlayer;

public class ActionBar
{
    private static Class<?> packetOutChatClass;
    private static Class<?> iChatBaseComponentClass;
    private static Class<?> chatSerializerClass;
    private static Reflection.ConstructorInvoker packetChatConstructor;
    private static String version;
    private static Reflection.MethodInvoker getAMethod;
    private static Reflection.MethodInvoker getEnumAction;
    
    static {
        ActionBar.packetOutChatClass = Reflection.getMinecraftClass("PacketPlayOutChat");
        ActionBar.iChatBaseComponentClass = Reflection.getMinecraftClass("IChatBaseComponent");
        ActionBar.chatSerializerClass = Reflection.getMinecraftClass("IChatBaseComponent$ChatSerializer");
        ActionBar.version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        ActionBar.getAMethod = Reflection.getMethod(ActionBar.chatSerializerClass, "a", String.class);
        if (ActionBar.version.equals("v1_12_R1") || ActionBar.version.equals("v1_12_R2")) {
            ActionBar.getEnumAction = Reflection.getMethod(Reflection.getMinecraftClass("PacketPlayOutTitle$EnumTitleAction"), "a", String.class);
            ActionBar.packetChatConstructor = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutTitle"), Reflection.getMinecraftClass("PacketPlayOutTitle$EnumTitleAction"), ActionBar.iChatBaseComponentClass);
        }
        else {
            ActionBar.packetChatConstructor = Reflection.getConstructor(ActionBar.packetOutChatClass, ActionBar.iChatBaseComponentClass, Byte.TYPE);
        }
    }
    
    public static void send(final String s, final Player player) {
        Object o;
        if (ActionBar.version.equals("v1_12_R1") || ActionBar.version.equals("v1_12_R2")) {
            o = ActionBar.packetChatConstructor.invoke(ActionBar.getEnumAction.invoke(Reflection.getMinecraftClass("PacketPlayOutTitle$EnumTitleAction"), "ACTIONBAR"), ActionBar.getAMethod.invoke(ActionBar.chatSerializerClass, "{\"text\":\"" + s + "\"}"));
        }
        else {
            o = ActionBar.packetChatConstructor.invoke(ActionBar.getAMethod.invoke(ActionBar.chatSerializerClass, "{\"text\":\"" + s + "\"}"), (byte)2);
        }
        NMSPlayer.sendPacket(player, o);
    }
}
