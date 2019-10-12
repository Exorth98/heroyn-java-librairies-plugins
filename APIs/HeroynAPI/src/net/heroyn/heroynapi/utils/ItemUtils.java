package net.heroyn.heroynapi.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

@SuppressWarnings("deprecation")
public class ItemUtils
{
public static ItemStack potionToItemStack(PotionType type, boolean spalsh, int level)
  {
	Potion potion = new Potion(PotionType.WEAKNESS);
    potion.setSplash(spalsh);
    potion.setLevel(level);
    ItemStack itemstack = potion.toItemStack(1);
    return itemstack;
  }
  
  public static ItemStack itemStackLeatherColor(Material mat, Color color)
  {
    ItemStack item = new ItemStack(mat);
    LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
    meta.setColor(color);
    item.setItemMeta(meta);
    return item;
  }
  
	public static ItemStack setName(ItemStack item, String name) {
		
		ItemMeta keyM = item.getItemMeta();
		keyM.setDisplayName(name);
		item.setItemMeta(keyM);
				
		return item;
	}

	public static ItemStack setLores(ItemStack item, List<String> lores) {
		
		ItemMeta keyM = item.getItemMeta();
		keyM.setLore(lores);
		item.setItemMeta(keyM);
		
		return item;
	}
	
	public static int [] getStacksNumber(ItemStack stack){
		int count [] = new int [2];
		count[0] = stack.getAmount()/64;
		count[1] = stack.getAmount()%64;
		
		return count;
		
	}
	
	public static int getEmptiesSlotsNumber(Inventory inv){
		
		int count = 0;
		for (ItemStack item : inv.getStorageContents()) {
		    if (item == null || item.getType() == Material.AIR ){
		    	count++;
		    }		        
		}
		return count;
	}
	
	public static ItemStack addLore(ItemStack item, String string) {
		
		ItemMeta itemM = item.getItemMeta();
		List<String> lores;
		if(itemM.getLore() != null){lores = itemM.getLore();} 
		else{lores = new ArrayList<>();}
		
		lores.add(string);
		
		itemM.setLore(lores);
		item.setItemMeta(itemM);
		
		return item;
	}
	
	public static ItemStack removeLastLore(ItemStack item) {
		
		ItemMeta itemM = item.getItemMeta();
		List<String> lores = itemM.getLore();
		
		lores.remove(lores.size()-1);
		
		itemM.setLore(lores);
		item.setItemMeta(itemM);
		
		return item;
	}

	public static ItemStack addFakeEnchant(ItemStack item) {
		
		ItemMeta itemM = item.getItemMeta();
		itemM.addEnchant(Enchantment.DURABILITY, 1, true);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemM);
		
		return item;
	}
}
