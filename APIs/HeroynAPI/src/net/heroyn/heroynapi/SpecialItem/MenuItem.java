package net.heroyn.heroynapi.SpecialItem;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.heroyn.heroynapi.utils.ItemBuilder;

public abstract class MenuItem
  implements MenuItemInterface
{
  static List<MenuItem> allMenuItem = new ArrayList<>();
  Player player;
  ItemStack item;
  String name;
  List<String> Lore = new ArrayList<>();
  ItemMeta meta;
  boolean vip;
  boolean effect;
  
  public MenuItem(String name, List<String> lore, ItemStack item)
  {
    this.item = item;
    this.name = name;
    if (lore != null) {
      this.Lore = lore;
    }
    allMenuItem.add(this);
  }
  
  public MenuItem(String name, String[] lore, ItemStack item)
  {
    this.item = item;
    this.name = name;
    if (lore != null) {
      this.Lore = Arrays.asList(lore);
    }
    allMenuItem.add(this);
  }
  
  public MenuItem(String name, String[] lore, ItemStack item, boolean effect)
  {
    this.item = item;
    this.name = name;
    this.effect = effect;
    if (lore != null) {
      this.Lore = Arrays.asList(lore);
    }
    allMenuItem.add(this);
  }
  
  public static MenuItem getMenuItem(ItemStack item)
  {
    for (MenuItem menuitem : allMenuItem) {
      if (menuitem.getItems().equals(item)) {
        return menuitem;
      }
    }
    return null;
  }
  
  public abstract void onUse(Player paramPlayer);
  
  public void RightClick(Player player) {}
  
  public void InteractOnUse(Player player) {}
  
  public void InteractLeftOnUse(Player player) {}
  
  public String getName()
  {
    return this.name;
  }
  
  public List<String> getLore()
  {
    return this.Lore;
  }
  
  public ItemStack get()
  {
    return this.item;
  }
  
  public ItemMeta getMeta()
  {
    return this.meta;
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
  public static List<MenuItem> getAllMenuItem()
  {
	    return allMenuItem;
  }
  
  public void close(Player player)
  {
    player.closeInventory();
  }
  
  public void setVipItem()
  {
    this.vip = true;
  }
  
  public boolean ItemIsVip()
  {
    return this.vip;
  }
  
  public ItemStack getItems()
  {
    return new ItemBuilder(this.item.getType()).name(this.name).lore(this.Lore).amount(this.item.getAmount()).data(this.item.getDurability()).enchants(this.item.getEnchantments()).glow(this.effect).build();
  }
  
  public void setItems(ItemStack item)
  {
    this.item = item;
    this.name = item.getItemMeta().getDisplayName();
    this.Lore = item.getItemMeta().getLore();
  }
  
}
