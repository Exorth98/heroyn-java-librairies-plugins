package net.heroyn.mobarena.menus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.utils.ItemBuilder;
import net.heroyn.heroynapi.virtualmenu.VirtualMenu;
import net.heroyn.mobarena.classe.ClassesStats;
import net.heroyn.mobarena.menus.menuitems.MenuChangeItem;

public class ClasseInfosMenu extends VirtualMenu {

	private String className;
	private Material symbol;
	
	public ClasseInfosMenu(String className, Material symbol) {
		super("§0 Infos du " + className, 3);
		this.className = className;
		this.symbol = symbol;
	}

	@Override
	public Inventory getInventory() {

		if(this.inventory == null) {
			
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			
			fill(new ItemStack(Material.STAINED_GLASS_PANE),true);
			fillLine(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7),2,true);
			
			int level = 1;
			for(int i = 1; i<10; i+=2, level++) {
				ClassesStats stats = ClassesStats.getStats(className, level);
				List<String> lore = Arrays.asList("§aVie : §b"+stats.getLife(),
												  "§aRésistance : §b"+(stats.getResistance()*100)+"%",
												  "§aDégats : §b"+stats.getDamage(),
												  "§aDégats secondaires : §b"+stats.getSecondaryDamage(),
												  "§aSpeed : §b"+(stats.getSpeed()+1),
												  "§aJump : §b"+(stats.getJump()+1),
												  "§ARésistance au feu : §b"+(stats.isFireResistant() ? "Oui" : "Non"));
				
				ItemStack item =  new ItemBuilder(symbol).amount(level).name("§6"+className+" Level §e"+level).lore(lore).build();
				setItem(getPos(2,i), item);
			}
			
			setMenuItem(getPos(3,1), new MenuChangeItem("§cRetour",new ItemStack(Material.BARRIER), new ClassesInfosMenu()));
			
			
		}
		return this.inventory;
	}

	@Override
	public void onOpen() {}

}
