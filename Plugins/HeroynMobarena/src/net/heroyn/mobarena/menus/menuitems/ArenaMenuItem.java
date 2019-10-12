package net.heroyn.mobarena.menus.menuitems;

import java.util.Arrays;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import org.bukkit.Material;

public class ArenaMenuItem extends MenuItem {

	private String arenaName;
	
	public ArenaMenuItem(String name) {
		super("§6"+name, 
			  Arrays.asList("§aClique gauche : §bRejoindre",
					        "§aClique droit : §bSpectate"), 
			  new ItemStack(Material.IRON_SWORD));	
		this.arenaName = name;
	}

	@Override
	public void onUse(Player p) {		
		p.performCommand("ma join " +this.arenaName);
	}
	
	@Override
	public void RightClick(Player p) {
		p.performCommand("ma spectate "+this.arenaName);
	}

}
