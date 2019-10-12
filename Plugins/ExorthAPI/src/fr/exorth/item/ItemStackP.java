package fr.exorth.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackP{
	
	ItemStack ItemStack;
	
	public ItemStackP(ItemStack item) {
		
		this.ItemStack = item;
		
	}
	
	public ItemStackP(Material mat ,int amount) {
		
		ItemStack item = new ItemStack(mat, amount) ;
		this.ItemStack=item;	
	}
	public ItemStackP(int ID ,int amount) {
		
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(ID, amount) ;
		this.ItemStack=item;	
	}
	
	public ItemStackP(Material mat ,int amount, String name, List<String>lore) {
		
		ItemStack item = new ItemStack(mat, amount) ;
		if(name !=null) {this.setName(name);}
		if(lore !=null) {this.setLore(lore);}
		this.ItemStack=item;
		
		
	}
	public ItemStackP(Material mat ,int amount, byte data,  String name, List<String>lore) {
		
		ItemStack item = new ItemStack(mat, amount, data) ;
		if(name !=null) {setName(name);}
		if(lore !=null) {setLore(lore);}
		this.ItemStack=item;
		
	}
	
	public ItemStackP(int ID ,int amount, String name, List<String>lore) {
		
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(ID, amount) ;
		if(name !=null) {setName(name);}
		if(lore !=null) {setLore(lore);}
		this.ItemStack=item;
		
		
	}
	public ItemStackP(int ID ,int amount, byte data,  String name, List<String>lore) {
		
		@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(ID, amount, data) ;
		if(name !=null) {setName(name);}
		if(lore !=null) {setLore(lore);}
		this.ItemStack=item;
		
	}
	
	public ItemStack getItemStack() {
		
		return this.ItemStack;
	}
	
	public void setNameAndLore(String name, List<String> lore) {
		
		ItemMeta itemM = this.ItemStack.getItemMeta();
		itemM.setDisplayName(name);
		itemM.setLore(lore);
		this.ItemStack.setItemMeta(itemM);	
	}
	
	public void setName(String name) {
		
		ItemMeta itemM = this.ItemStack.getItemMeta();
		itemM.setDisplayName(name);
		this.ItemStack.setItemMeta(itemM);	
	}
	
	public void setLore(List<String> lore) {
		
		ItemMeta itemM = this.ItemStack.getItemMeta();
		itemM.setLore(lore);
		this.ItemStack.setItemMeta(itemM);	
	}

	public void addEnchantEffect() {
		ItemMeta itemM = this.ItemStack.getItemMeta();
		itemM.addEnchant(Enchantment.DURABILITY, 1, true);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		this.ItemStack.setItemMeta(itemM);
	}
}
