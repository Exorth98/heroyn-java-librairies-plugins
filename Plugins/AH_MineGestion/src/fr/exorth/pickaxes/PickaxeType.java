package fr.exorth.pickaxes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.util.MGItemUtil;

public enum PickaxeType {
	
	STONE(Material.STONE_PICKAXE,1,"§ePioche Banale",1) , IRON(Material.IRON_PICKAXE,2,"§bPioche Intermediare",2), DIAMOND(Material.DIAMOND_PICKAXE,3,"§cPioche Avancée",3), DIAMOND_X(Material.DIAMOND_PICKAXE,7,"§l§dPioche Extra",4);	
	
	Material mat;
	int level;
	String name;
	int ordernumber;
	
	PickaxeType(Material mat, int level, String name, int ordernumber) {
		
		this.mat=mat;
		this.level=level;
		this.name=name;
		this.ordernumber=ordernumber;
		
	}
	
	public ItemStack getItem(){
		
		ItemStack item = new ItemStack(mat,1);
		ItemMeta itemM = item.getItemMeta();
		itemM.addEnchant(Enchantment.DIG_SPEED, level, true);
		item.setItemMeta(itemM);
		item = MGItemUtil.setName(item, name);
		
		return item;
	}
	
	public int getOrderNumber(){
		
		return ordernumber;
		
	}

	public static PickaxeType getTypeFromItem(ItemStack item) {
		
		for(PickaxeType pt : values()){
			if(pt.getMat().equals(item.getType())){
				
				if(!pt.getMat().equals(Material.DIAMOND_PICKAXE)){
					return pt;
				}else{
					int level = item.getEnchantmentLevel(Enchantment.DIG_SPEED);
					if(level== pt.getLevel()){
						return pt;
					}
					
				}
				
			}
		}
		
		return null;
	}

	private int getLevel() {

		return this.level;
	}

	private Material getMat() {
		return this.mat;
	}
	
}
