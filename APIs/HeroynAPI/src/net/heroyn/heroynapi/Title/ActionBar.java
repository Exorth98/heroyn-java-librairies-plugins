package net.heroyn.heroynapi.Title;

import net.minecraft.server.v1_13_R1.ChatMessageType;
import net.minecraft.server.v1_13_R1.IChatBaseComponent;
import net.minecraft.server.v1_13_R1.PacketPlayOutChat;
import net.minecraft.server.v1_13_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar
{
  public static void sendActionBar(Player player, String message)
  {
    CraftPlayer craftPlayer = (CraftPlayer) player;
    PlayerConnection connection = craftPlayer.getHandle().playerConnection;

    PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}"), ChatMessageType.GAME_INFO);

    connection.sendPacket(packet);
  }
  
  public static void broadcastActionBar(String message)
  {
    for (Player pls : Bukkit.getOnlinePlayers()) {
      sendActionBar(pls, message);
    }
  }
}
