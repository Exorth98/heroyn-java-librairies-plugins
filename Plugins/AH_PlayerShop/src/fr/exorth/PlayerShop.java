package fr.exorth;

import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.HDVCommand;

public class PlayerShop extends JavaPlugin{

	static PlayerShop instance;
	
	public PSConfigManager iCfgm;
	public PSConfigManager sCfgm;
	
	public static PlayerShop getInstance() {
		return instance;
	}
	
	
	
	
	@Override
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		loadCustomConfigs();
		
		getCommand("hdv").setExecutor(new HDVCommand());
		
		instance=this;
		
		
		super.onEnable();
	}
	
	private void loadCustomConfigs() {
		
		iCfgm= new PSConfigManager("ForSaleItemStacks");
		iCfgm.setupCustomConfig();
		
		sCfgm= new PSConfigManager("Sales");
		sCfgm.setupCustomConfig();
		
	}
	
	
	@Override
	public void onDisable() {
		
		
		super.onDisable();
	}
	
}
