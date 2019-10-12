package net.heroyn.heroynapi.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

	private Plugin plugin;
	private FileConfiguration cfg;
	private File file;
	
	private String folder;
	private String fileName;
	
	public ConfigManager(Plugin plugin, String folder, String fileName) {
		
		this.plugin = plugin;
		this.folder = folder;
		this.fileName=fileName;
		
	}

	public void setupCustomConfig() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		file = new File(plugin.getDataFolder(), folder + "/" + fileName + ".yml");

		boolean create = false;

		if (!file.exists()) {
			try {
				file.createNewFile();
				create = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The "+fileName+".yml file has been created");
		}
		cfg = YamlConfiguration.loadConfiguration(file);
		if(create) onFileCreated(file);
	}

	protected void onFileCreated(File file) {

	}

	public FileConfiguration getCustomConfig() {
		return cfg;
	}

	public void saveCustomConfig() {
		try {
			cfg.save(file);
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The "+fileName+".yml file has been saved");

		} catch (IOException e) {
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the "+fileName+".yml file");
		}
	}
	
	public void setupAndSave() {
		
		this.setupCustomConfig();
		this.saveCustomConfig();
		this.reloadCustomConfig();
		
	}

	public void reloadCustomConfig() {
		cfg = YamlConfiguration.loadConfiguration(file);
		//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The "+fileName+".yml file has been reload");

	}

}
