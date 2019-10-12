package net.heroyn.mobarena.menus.menuitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynapi.virtualmenu.VirtualMenu;

public class MenuChangeItem extends MenuItem {

	VirtualMenu menuToOpen;
	
	public MenuChangeItem(String name, List<String> lore, ItemStack item, VirtualMenu menu) {
		super(name, lore, item);		
		this.menuToOpen = menu;
	}
	public MenuChangeItem(String name, ItemStack item, VirtualMenu menu) {
		super(name, new ArrayList<>(), item);		
		this.menuToOpen = menu;
	}

	@Override
	public void onUse(Player p) {	
		this.menuToOpen.open(p);
	}

}
