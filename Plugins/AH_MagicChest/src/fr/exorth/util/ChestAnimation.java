package fr.exorth.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;

import fr.exorth.runnable.AnimationRunnable;

public class ChestAnimation {
	
	//static List<ItemStack> List = TirageUtils.getList();

	public static void playAnimation(Player p) {
		
		Inventory Tirage = Bukkit.createInventory(null, 27,"§0Tirage...");
		
		for(int i = 0;i<9;i++){Tirage.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i = 18;i<27;i++){Tirage.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		Tirage.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)4));
		Tirage.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)4));
		
		p.openInventory(Tirage);
		
		MagicChest.getInstance().inTirage.put(p.getName(),p.getUniqueId());
		MagicChest.getInstance().inTirageInv.put(p.getName(),Tirage);		
		
		new AnimationRunnable(p,Tirage).runTaskTimer(MagicChest.getInstance(), 0, 2);
			
	}

}
