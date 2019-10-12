package fr.exorth.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class speedRecipes implements Listener {
	
	@EventHandler
	public void changeCraft(PrepareItemCraftEvent e){
		
		if(e.getInventory() instanceof CraftingInventory){
			
			CraftingInventory inv = (CraftingInventory) e.getInventory();
			
			if(inv.getResult().getType() == Material.WOOD_PICKAXE){
				ItemStack customresult = new ItemStack(Material.STONE_PICKAXE);
				ItemMeta customm = customresult.getItemMeta();
				customm.addEnchant(Enchantment.DIG_SPEED, 2, true);
				customm.addEnchant(Enchantment.DURABILITY, 3, true);
				customresult.setItemMeta(customm);
				
				inv.setResult(customresult);
				
			}
		}
	}

}
