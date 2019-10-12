package fr.exorth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.exorth.commands.LoadCommand;
import fr.exorth.commands.npcCommand;
import fr.exorth.commands.shopCommand;
import fr.exorth.commands.shopZoneCommand;
import fr.exorth.events.confirmationInteract;
import fr.exorth.events.npcListener;
import fr.exorth.events.shopInteract;
import fr.exorth.runnable.ShopRunnable;
import net.milkbowl.vault.economy.Economy;

public class factionshop extends JavaPlugin{
	
	static factionshop instance;

	public Economy economy = null;
	
	public static factionshop getInstance(){
		return instance;
	}
	
	public HashMap<Player,ArrayList<Inventory>> sellinventories = new HashMap<>();
	public HashMap<Player,ArrayList<Inventory>> mysellinventories = new HashMap<>();
	public HashMap<Player,ArrayList<Inventory>> myexpsellinventories = new HashMap<>();
	
	public HashMap<UUID,Integer> numberofsales = new HashMap<>();	
	public HashMap<String,UUID> refs = new HashMap<>();
	public HashMap<String,Date> dates = new HashMap<>();
	public ArrayList<ItemStack> itemsinsell = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		instance=this;
		
		loadCacheFromCfg();
		
		Bukkit.getPluginManager().registerEvents(new shopInteract(), this);
		Bukkit.getPluginManager().registerEvents(new confirmationInteract(), this);
		Bukkit.getPluginManager().registerEvents(new npcListener(), this);
		getCommand("hdv").setExecutor(new shopCommand());
		getCommand("facshopnpc").setExecutor(new npcCommand());
		getCommand("setshopzone").setExecutor(new shopZoneCommand());
		getCommand("factionshopload").setExecutor(new LoadCommand());
		
		new ShopRunnable().runTaskTimer(this, 0L, 20L);
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		saveCacheInCfg();
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
	
	@SuppressWarnings("unchecked")
	private void loadCacheFromCfg(){
		
		//numberofsales
		for( String ligne : getConfig().getStringList("numberofsales")){
			numberofsales.put(UUID.fromString(ligne.split(":")[0]), Integer.parseInt(ligne.split(":")[1]));
		}
		
		//itemsinsell
		if(getConfig().getList("insellitems") != null){
			itemsinsell = (ArrayList<ItemStack>) getConfig().getList("insellitems");
		}else{
			itemsinsell = new ArrayList<ItemStack>();
		}
		
		//Refs
		for( String ligne : getConfig().getStringList("references")){
			refs.put(ligne.split(":")[0], UUID.fromString(ligne.split(":")[1]));
		}
		
		//dates
		for( String ligne : getConfig().getStringList("insaledates")){
			String sdate = ligne.split("=")[1];
		    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		    try{
		       Date date = df.parse(sdate);
		        dates.put(ligne.split("=")[0], date);
		    }
		    catch ( Exception ex ){
		        System.out.println(ex);
		    }
		}
	}
	
	
	
	private void saveCacheInCfg(){
		
		//numberofsales
		ArrayList<String> numberofsalesl = new ArrayList<>();
		for(UUID uuid : numberofsales.keySet()){
		numberofsalesl.add(uuid.toString() + ":" + numberofsales.get(uuid).toString());
		}
		getConfig().set("numberofsales", numberofsalesl);
		
		//refs1
		ArrayList<String> refsl = new ArrayList<>();
		for(String ref : refs.keySet()){
		refsl.add(ref + ":" + refs.get(ref).toString());
		}
		getConfig().set("references", refsl);
		
		//Dates1
		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		ArrayList<String> datesl = new ArrayList<>();
		for(String ref : dates.keySet()){
		datesl.add(ref + "=" + df.format(dates.get(ref)));
		}
		getConfig().set("insaledates", datesl);
		
		//itemsinsell
		getConfig().set("insellitems", itemsinsell);

		
		saveConfig();
		
		
	}

}
