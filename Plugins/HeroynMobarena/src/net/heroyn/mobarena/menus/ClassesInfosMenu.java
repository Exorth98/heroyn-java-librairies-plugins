package net.heroyn.mobarena.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.virtualmenu.VirtualMenu;
import net.heroyn.mobarena.menus.menuitems.MenuChangeItem;

public class ClassesInfosMenu extends VirtualMenu {
	
	public ClassesInfosMenu() {
		super("§0Infos sur les classes", 3);
	}

	@Override
	public Inventory getInventory() {
		
		if(this.inventory == null) {
			
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			
			fill(new ItemStack(Material.STAINED_GLASS_PANE), true);
			
			setMenuItem(getPos(2,2), new MenuChangeItem("§6Mage", new ItemStack(Material.GOLD_SWORD), new ClasseInfosMenu("Mage",Material.GOLD_SWORD)));
			setMenuItem(getPos(2,4), new MenuChangeItem("§6Tank", new ItemStack(Material.IRON_SWORD), new ClasseInfosMenu("Tank",Material.IRON_SWORD)));
			setMenuItem(getPos(2,6), new MenuChangeItem("§6Knight", new ItemStack(Material.DIAMOND_SWORD), new ClasseInfosMenu("Knight",Material.DIAMOND_SWORD)));
			setMenuItem(getPos(2,8), new MenuChangeItem("§6Ranger", new ItemStack(Material.BOW), new ClasseInfosMenu("Ranger",Material.BOW)));
			
			setMenuItem(getPos(3,1), new MenuChangeItem("§cRetour", new ItemStack(Material.BARRIER), new MobarenaMenu()));
		}
		return this.inventory;
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub

	}

}
