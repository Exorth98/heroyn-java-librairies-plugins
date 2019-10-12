package net.heroyn.heroynapi.villagernpc;

import org.bukkit.Location;

public class CmdVillagerNpc extends VillagerNpc{

	String cmd;
	
	public CmdVillagerNpc(String name, Location loc, String cmd) {
		super(name, loc);
		this.cmd = cmd;
	}
	
	public CmdVillagerNpc(String name, String DisplayName, Location loc, String cmd) {
		super(name, DisplayName, loc);
		this.cmd = cmd;
	}
	
	public String getCommand(){
		
		return this.cmd;
	}

}
