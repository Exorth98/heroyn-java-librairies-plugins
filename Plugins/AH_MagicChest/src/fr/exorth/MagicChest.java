package fr.exorth;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.AddGainCommand;
import fr.exorth.commands.CheckCommand;
import fr.exorth.commands.ChestGainsCommand;
import fr.exorth.commands.GainsCommand;
import fr.exorth.commands.LoadCommand;
import fr.exorth.commands.giveMagicKeyCommand;
import fr.exorth.commands.spawnChestCommand;
import fr.exorth.events.ChestMenuInteract;
import fr.exorth.events.JoinEvent;
import fr.exorth.events.chestListener;

public class MagicChest extends JavaPlugin{
	
	static MagicChest instance;

	public HashMap<String,Inventory> inTirageInv = new HashMap<>();	;	
	public HashMap<String,UUID> inTirage = new HashMap<>();	
	
	public static MagicChest getInstance(){
		return instance;
	}
	

	@Override
	public void onEnable() {
		
		this.saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance = this;
		
		Bukkit.getPluginManager().registerEvents(new chestListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChestMenuInteract(), this);
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		getCommand("spawnmagicchest").setExecutor(new spawnChestCommand());
		getCommand("givemagickey").setExecutor(new giveMagicKeyCommand());
		getCommand("chestgains").setExecutor(new ChestGainsCommand());
		getCommand("addgain").setExecutor(new AddGainCommand());
		getCommand("gains").setExecutor(new GainsCommand());
		getCommand("check").setExecutor(new CheckCommand());
		getCommand("magicchestload").setExecutor(new LoadCommand());
		
		
		
		super.onEnable();
	}
	
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	
	
}
