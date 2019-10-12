package fr.exorth;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.DreamsCommand;
import fr.exorth.events.JoinEvent;
import net.milkbowl.vault.economy.Economy;

public class AHEconomy extends JavaPlugin{

	public Economy economy = null;
	
	
	static AHEconomy instance;
	
	public static AHEconomy getInstance(){
		return instance;
	}
	
	@Override
	public void onEnable() {
		
		instance=this;
		
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		getCommand("dreams").setExecutor(new DreamsCommand());
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		
		super.onDisable();
	}
	
    public boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
	
}
