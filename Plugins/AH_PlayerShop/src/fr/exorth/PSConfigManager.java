package fr.exorth;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PSConfigManager {
	
	private PlayerShop plugin = PlayerShop.getPlugin(PlayerShop.class);

	public FileConfiguration cfg;
	public File file;
	
	public String fileName;

	public PSConfigManager(String filename) {
		
		this.fileName=filename;
		
	}

	public void setupCustomConfig() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		file = new File(plugin.getDataFolder(), fileName+".yml");

		if (!file.exists()) {
			/*try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			plugin.saveResource(fileName+".yml", true);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "The "+fileName+".yml file has been created");
		}

		cfg = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getCustomConfig() {
		return cfg;
	}

	public void saveCustomConfig() {
		try {
			cfg.save(file);
			//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "The "+fileName+".yml file has been saved");

		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the "+fileName+".yml file");
		}
	}

	public void reloadCustomConfig() {
		cfg = YamlConfiguration.loadConfiguration(file);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "The "+fileName+".yml file has been reload");

	}

}
