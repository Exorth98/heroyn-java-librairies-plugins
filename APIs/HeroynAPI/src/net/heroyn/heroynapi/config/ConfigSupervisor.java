package net.heroyn.heroynapi.config;

import java.util.HashMap;
import java.util.List;

import org.bukkit.plugin.Plugin;

public abstract class ConfigSupervisor {
	
	protected String name;
	protected HashMap<String, ConfigManager> CfgManagers = new HashMap<>();
	protected Plugin pl;;
	
	public ConfigSupervisor(String name, Plugin pl) {
		this.name = name;
		this.pl = pl;
	}
	
	public abstract void loadAllCustomConfigs();
	
	
	public ConfigSupervisor addCustomConfigManager(String name, ConfigManager cfgM) {	
		cfgM.setupAndSave();
		this.CfgManagers.put(name, cfgM);	
		return this;
	}
	
	public ConfigSupervisor removeCustomConfigManager(String name) {	
		this.CfgManagers.remove(name);	
		return this;
	}
	
	public ConfigManager getCustomConfigManager(String name) {
		
		return this.CfgManagers.get(name);
	}
	
	public void saveAllCustomConfigs() {
		
		for(ConfigManager cfgM : this.CfgManagers.values()) {
			cfgM.saveCustomConfig();
		}
		
	}
	public void reloadAllCustomConfigs() {
		
		for(ConfigManager cfgM : this.CfgManagers.values()) {

			cfgM.reloadCustomConfig();
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<ConfigManager> getManagersList(){
		return (List<ConfigManager>) this.CfgManagers.values();
	}
	
	public HashMap<String, ConfigManager> getManagersMap(){
		return this.CfgManagers;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setManagers(HashMap<String, ConfigManager> map) {
		this.CfgManagers = map;
		
	}
}
