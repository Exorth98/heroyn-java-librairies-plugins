package net.heroyn.heroynapi.utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageBungeeUtils
{
  public static void SendMessageWithDescription(Player player, String message, String Description)
  {
    TextComponent component = new TextComponent();
    component.setText(message);
    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Description).create()));
    player.sendMessage("?6" + GameMessage.line);
    player.spigot().sendMessage(component);
    player.sendMessage("?6" + GameMessage.line);
  }
  
  public static void BroadcastMessageWithHover(String message, String Description)
  {
    TextComponent component = new TextComponent();
    component.setText(message);
    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Description).create()));
    for (Player player : Bukkit.getOnlinePlayers())
    {
      player.sendMessage("?6" + GameMessage.line);
      player.spigot().sendMessage(component);
      player.sendMessage("?6" + GameMessage.line);
    }
  }
}
