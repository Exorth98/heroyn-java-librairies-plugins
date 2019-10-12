package net.heroyn.heroynserverapi;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynserverapi.commands.MaintenanceCommand;
import net.heroyn.heroynserverapi.commands.MajCommand;
import net.heroyn.heroynserverapi.listeners.PlayerPreLoginListener;
import net.heroyn.heroynserverapi.maintenance.MaintenanceState;
import net.heroyn.heroynserverapi.updatesystem.Update;
import net.heroyn.heroynserverapi.updatesystem.listeners.ChatListener;
import net.minecraft.server.v1_13_R1.BlockStairs;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.material.Directional;
import org.bukkit.material.Stairs;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.heroyn.heroynapi.utils.LogUtils;
import net.heroyn.heroynserverapi.commands.testCmd;
import net.heroyn.heroynserverapi.cosmetics.mount.CustomEntityType;
import net.heroyn.heroynserverapi.economy.moneyCommand;
import net.heroyn.heroynserverapi.listeners.PlayerJoinListener;
import net.heroyn.heroynserverapi.listeners.PlayerLeaveListener;
import net.heroyn.heroynserverapi.listeners.ShopItemListener;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.setupmanager.SetupUpdater;
import net.heroyn.heroynserverapi.updater.Updater;

public class HeroynServerAPI extends JavaPlugin {

	static HeroynServerAPI instance;
	public static boolean isNoel;
	public static boolean isHalloween;

	@Override
	public void onEnable() {
		ServerAnniversary.test();
		getCommand("test").setExecutor(new testCmd());
		getCommand("money").setExecutor(new moneyCommand());
		getCommand("maj").setExecutor(new MajCommand());
		getCommand("maintenance").setExecutor(new MaintenanceCommand());
		Bukkit.getPluginManager().registerEvents(new PlayerPreLoginListener(), this);
		instance = this;

		// Setup
		new SetupUpdater(this);
		new Updater(this);
		CustomEntityType.registerEntities();
		// Listener
		initListener();
		// Config
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		if (this.getConfig().getBoolean("created")) {
			isNoel = this.getConfig().getBoolean("isnoel");
			LogUtils.Log("isNoel: " + isNoel);
			isHalloween = this.getConfig().getBoolean("ishalloween");
			LogUtils.Log("isHalloween: " + isHalloween);
		} else {
			this.getConfig().set("isnoel", false);
			this.getConfig().set("ishalloween", false);
			this.getConfig().set("created", true);
			this.saveConfig();
		}
		Update.Utils.initFile();
		MaintenanceCommand.CONFIG = new ConfigManager(net.heroyn.heroynserverapi.HeroynServerAPI.getInstance(), "", "maintenance");
		MaintenanceCommand.CONFIG.setupCustomConfig();
		MaintenanceCommand.STATE = new MaintenanceState(false, "");
	}

	@Override
	public void onDisable() {
		for (PlayerInfo pp : PlayerInfo.getPlayerInfos().values()) {
			removeAllCosmetics(pp);
		}
		// CustomEntityType.unregisterEntities();
	}

	public static void removeAllCosmetics(PlayerInfo pp) {
		if (pp.getMinion() != null)
			pp.getMinion().removeMinion();
		if (pp.getPet() != null)
			pp.getPet().removePet();
		if (pp.getMorph() != null)
			// pp.getMorph().removeMorph();
			if (pp.getParticle() != null)
				pp.getParticle().stopPlayingParticle();
	}

	private void initListener() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerLeaveListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new ShopItemListener(), this);

		pm.registerEvents(new ChatListener(), this);
	}

	public static HeroynServerAPI getInstance() {
		return instance;
	}
}
