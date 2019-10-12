package net.heroyn.heroynapi.utils;

import java.util.List;
import java.util.Map;

import net.minecraft.server.v1_13_R1.ItemStack;
import net.minecraft.server.v1_13_R1.NBTTagCompound;
import net.minecraft.server.v1_13_R1.NBTTagList;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemBuilder {


	private Material material;
	private int amount = 1;
	private byte data;
	private String name;
	private List<String> lore = Lists.newArrayList();
	private Map<Enchantment, Integer> enchants = Maps.newHashMap();
	private boolean glow = false;

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder data(int data) {
		this.data = ((byte) data);
		return this;
	}

	public ItemBuilder name(String name) {
		this.name = name;
		return this;
	}

	public ItemBuilder lore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public ItemBuilder glow(boolean value) {
		this.glow = value;
		return this;
	}

	public ItemBuilder enchant(Enchantment enchantement, int level) {
		this.enchants.put(enchantement, Integer.valueOf(level));
		return this;
	}

	public ItemBuilder enchants(Map<Enchantment, Integer> echants) {
		this.enchants = echants;
		return this;
	}

	public boolean isClowing() {
		return this.glow;
	}

	public org.bukkit.inventory.ItemStack build()
	  {
	    Validate.notNull(this.material, "Erreur dans la méthode build");
	    org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(this.material, this.amount, this.data);
	    ItemMeta meta = stack.getItemMeta();
	    if(meta == null) return stack;
	    if (this.glow) {
	      meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    }
	    if (this.name != null) {
	      meta.setDisplayName(this.name);
	    }
	    if (this.lore != null) {
	      meta.setLore(this.lore);
	    }
	    if (!this.enchants.isEmpty()) {
	      for (Map.Entry<Enchantment, Integer> enchants : this.enchants.entrySet()) {
	        meta.addEnchant((Enchantment)enchants.getKey(), ((Integer)enchants.getValue()).intValue(), true);
	      }
	    }
	    stack.setItemMeta(meta);
	    if (this.glow)
	    {
	      ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
	      if (nmsStack == null) {
	        return stack;
	      }
	      NBTTagCompound tag = null;
	      if (!nmsStack.hasTag())
	      {
	        tag = new NBTTagCompound();
	        nmsStack.setTag(tag);
	      }
	      if (tag == null) {
	        tag = nmsStack.getTag();
	      }
	      NBTTagList ench = new NBTTagList();
	      tag.set("ench", ench);
	      nmsStack.setTag(tag);
	      stack = CraftItemStack.asCraftMirror(nmsStack);
	    }
	    return stack;
	  }
}
