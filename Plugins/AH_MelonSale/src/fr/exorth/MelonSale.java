package fr.exorth;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.MelonSaleCommand;
import fr.exorth.events.MelonSaleConfirmationInteract;
import net.milkbowl.vault.economy.Economy;

public class MelonSale extends JavaPlugin{
	
	static MelonSale instance;

	public Economy economy = null;
	
	public HashMap<Player,double[]> inConfirmation= new HashMap<>();
	
	public static MelonSale getInstance(){
		return instance;
	}
	
	
	@Override
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance=this;
		
		
		getCommand("ms").setExecutor(new MelonSaleCommand());
		Bukkit.getPluginManager().registerEvents(new MelonSaleConfirmationInteract(), this);
		
		super.onEnable();
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
