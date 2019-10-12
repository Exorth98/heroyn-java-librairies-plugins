package net.heroyn.heroynapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.customevents.PlayerChangeServerEvent;

public class ServerUtils {
	
	  public static void changeServer(Player player, String serverName)
	  {
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    out.writeUTF("Connect");
	    out.writeUTF(serverName);
	    player.sendPluginMessage(HeroynAPI.getInstance(), "BungeeCord", out.toByteArray());
	    Bukkit.getServer().getPluginManager().callEvent(new PlayerChangeServerEvent(player, serverName));
	  }

}
