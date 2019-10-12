package net.heroyn.mobarena.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.utils.ItemBuilder;
import net.heroyn.heroynapi.virtualmenu.VirtualMenu;
import net.heroyn.mobarena.menus.menuitems.ArenaMenuItem;
import net.heroyn.mobarena.menus.menuitems.MenuChangeItem;

public class MobarenaMenu extends VirtualMenu{

	public MobarenaMenu() {
		super("§0Mobarena", 5);
	}

	@Override
	public Inventory getInventory() {
		
		if(this.inventory == null) {
			
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			
			fill(new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte)7), true);
			fillLine(new ItemStack(Material.STAINED_GLASS_PANE),1,true);
			fillLine(new ItemStack(Material.STAINED_GLASS_PANE),5,true);
			
			setMenuItem(getPos(2,5), new ArenaMenuItem("LavaPeak"));
			setMenuItem(getPos(3,2), new MenuChangeItem("§6Infos sur les classes",new ItemStack(Material.PAPER),new ClassesInfosMenu()));
			setItem(getPos(3,8), new ItemBuilder(Material.SIGN).name("§6LeaderBoard").build());
			setItem(getPos(4,5), new ItemBuilder(Material.EMERALD).name("§aBoutique").build());
		}		
		return inventory;
	}

	@Override
	public void onOpen() {}

}
