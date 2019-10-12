package net.heroyn.heroynserverapi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;

public abstract class VirtualShop
{
  public String title;
  public int size;
  public Inventory inventory;
  static Map<Integer, VirtualShop> allVirtualShop = new HashMap<>();
  public Map<Integer, ShopItemBuyableOnceDreams> myItems = new ConcurrentHashMap<>();
  
  public VirtualShop(String title, int size)
  {
    this.title = title;
    this.size = size;
  }
  
  public abstract Inventory getInventory();
  
  public abstract void open(Player paramPlayer);
  
  
  public void addAndRegisterShopItem(int pos, ShopItemBuyableOnceDreams item)
  {
    this.myItems.put(pos, item);
  }
  
  public void addMenuShopItem(ShopItemBuyableOnceDreams item)
  {
    getInventory().addItem(new ItemStack[] { item.getItems() });
  }
  
  public void setShopItem(int pos, ShopItemBuyableOnceDreams item)
  {
    getInventory().setItem(pos, item.getItems());
  }
  
  public void addItem(ItemStack item)
  {
    getInventory().addItem(new ItemStack[] { item });
  }
  
  public void setItem(int pos, ItemStack item)
  {
    getInventory().setItem(pos, item);
  }
  
  public String getTitleWithExtraText(String text)
  {
    return getTitle() + text;
  }
  
  public int getSize()
  {
    return this.size * 9;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public int getPos(int line, int slot)
  {
    return line * 9 - 9 + slot - 1;
  }
  
  public Map<Integer, ShopItemBuyableOnceDreams> getMyItems()
  {
    return this.myItems;
  }
  
  public static Map<Integer, VirtualShop> getAllVirtualShop()
  {
    return allVirtualShop;
  }
}
