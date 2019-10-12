package fr.exorth.Item;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.exorth.PlayerShop;

public class ForSaleItemStack {
	
	static FileConfiguration items = PlayerShop.getInstance().iCfgm.getCustomConfig();
	
	String ref;
	UUID owner;
	double price;
	long expiration;
	ItemStack item;
	
	public ForSaleItemStack(String ref, UUID owner,double price,long expiration, ItemStack item) {
		
		this.ref = ref;
		this.owner = owner;
		this.expiration = expiration;
		this.price = price;
		this.item = item;
		
	}
	
	
	public ForSaleItemStack(String ref) {
		
		this.ref = ref;
		createItem(ref);
		
	}
	
	private void createItem(String ref) {
		
		this.owner = UUID.fromString(items.getString(ref + "Owner"));
		this.price = items.getDouble(ref + "Price");
		this.expiration = items.getLong(ref + "Expiration");
		this.item = items.getItemStack(ref + "Item");
	}
	
	
	public ItemStack getItem() {
		
		return this.item;
	}
	
	public UUID getOwner() {
		
		return this.owner;
	}
	
	public double getPrice() {
		
		return this.price;
	}
	
	public long getExpiration() {
		
		return this.expiration;
	}

}
