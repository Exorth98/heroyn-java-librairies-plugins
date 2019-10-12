package net.heroyn.heroynapi.villagernpc;

import org.bukkit.Location;

public class MenuVillagerNpc extends VillagerNpc{

	String menuRef;
	
	public MenuVillagerNpc(String name, Location loc, String menuRef) {
		super(name, loc);
		this.menuRef = menuRef;
	}
	
	public MenuVillagerNpc(String name, String DisplayName, Location loc, String menuRef) {
		super(name, DisplayName, loc);
		this.menuRef = menuRef;
	}
	
	public String getMenuRef(){
		
		return this.menuRef;
	}

}
