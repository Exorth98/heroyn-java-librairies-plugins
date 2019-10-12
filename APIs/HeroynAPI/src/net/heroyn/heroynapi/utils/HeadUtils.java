package net.heroyn.heroynapi.utils;

import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadUtils
{
  @SuppressWarnings("deprecation")
public static ItemStack getOwnedHead(Player player)
  {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    item.setDurability((short)3);
    SkullMeta meta = (SkullMeta)item.getItemMeta();
    meta.setDisplayName(player.getName());
    meta.setOwner(player.getName());
    item.setItemMeta(meta);
    return item;
  }
  
  @SuppressWarnings("deprecation")
public static ItemStack getOwnedHead(String player_name)
  {
    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
    item.setDurability((short)3);
    SkullMeta meta = (SkullMeta)item.getItemMeta();
    meta.setOwner(player_name);
    item.setItemMeta(meta);
    return item;
  }
  
  public static ItemStack getSkull(String url)
  {
    ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
    if ((url == null) || (url.isEmpty())) {
      return skull;
    }
    SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
    GameProfile profile = new GameProfile(UUID.randomUUID(), null);
    byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", new Object[] { url }).getBytes());
    profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
    Field profileField = null;
    try
    {
      profileField = skullMeta.getClass().getDeclaredField("profile");
    }
    catch (NoSuchFieldException|SecurityException e)
    {
      e.printStackTrace();
    }
    profileField.setAccessible(true);
    try
    {
      profileField.set(skullMeta, profile);
    }
    catch (IllegalArgumentException|IllegalAccessException e)
    {
      e.printStackTrace();
    }
    skull.setItemMeta(skullMeta);
    return skull;
  }
}
