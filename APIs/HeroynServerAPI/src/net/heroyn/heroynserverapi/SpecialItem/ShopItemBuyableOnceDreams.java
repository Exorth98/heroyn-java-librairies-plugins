package net.heroyn.heroynserverapi.SpecialItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.heroyn.heroynapi.utils.ItemBuilder;
import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.player.PlayerInfo;

public abstract class ShopItemBuyableOnceDreams 
	implements shopItemInterface
{
	
	private static List<ShopItemBuyableOnceDreams> allShopItem = new ArrayList<>();

	public Player player;
	public ItemStack item;
	public String name;
	public String permissions;
	public double price;
	public List<String> lore = new ArrayList<>();
	public ItemMeta meta;
	public boolean effect = false;
	public boolean NoelItem;
	public boolean HalloweenItem;
	
	public ShopItemBuyableOnceDreams(Player player, String name, String[] lore, ItemStack item, String permissions, double price) {
		this.item = item;
		this.name = name;
		this.player = player;
		if (lore != null) {
			this.lore = Arrays.asList(lore);
		}
		this.price = price;
		this.permissions = permissions;
		allShopItem.add(this);
	}
	
	private List<String> setLore()
	{
		List<String> lore = new ArrayList<>();
		lore.addAll(getLore());
		
		// Is Noel ?
		if ((isNoelItem()) && (HeroynServerAPI.isNoel) && (!PlayerInfo.instanceOf(getPlayer()).hasPermissions(getPermissions()))) {
			lore.addAll(Arrays.asList((String[])buildLevelInfoBuyable().get(0)));
			return lore;
		}
		
		if ((isHalloweenItem()) && (HeroynServerAPI.isHalloween) && (!PlayerInfo.instanceOf(getPlayer()).hasPermissions(getPermissions()))) {
			lore.addAll(Arrays.asList((String[])buildLevelInfoBuyable().get(0)));
			return lore;
		}
		
		if (PlayerInfo.instanceOf(getPlayer()).hasPermissions(getPermissions())) {
			lore.addAll(Arrays.asList((String[])buildLevelInfoBuyable().get(1)));
		} else {
			lore.addAll(Arrays.asList((String[])buildLevelInfoBuyable().get(0)));
		}
		return lore;
	}
	
	
	/**
	 * Permet d'utiliser l'item ou de le payer
	 * @param player
	 */
	public abstract void onUse(Player player);
	
	/**
	 * permet d'inserer les lore achete ou non
	 * @return
	 */
	public abstract Map<Integer, String[]> buildLevelInfoBuyable();
	
	
	
	public static List<ShopItemBuyableOnceDreams> getAllShopItem() {
		return allShopItem;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermissions() {
		return permissions;
	}
	
	public double getPrice() {
		return price;
	}
	
	public boolean isNoelItem() {
		return NoelItem;
	}
	
	public boolean isHalloweenItem() {
		return HalloweenItem;
	}
	
	public void setHalloweenItem(boolean halloweenItem) {
		HalloweenItem = halloweenItem;
	}
	
	public void setNoelItem(boolean noelItem) {
		NoelItem = noelItem;
	}
	
	public void setEffect(boolean effect) {
		this.effect = effect;
	}
	
	@Override
	public ItemStack getItems() {
		return new ItemBuilder(this.item.getType()).name(this.getName()).lore(this.setLore()).amount(this.item.getAmount()).data(this.item.getDurability()).glow(this.effect).build();
	}

	public void close(Player player) {
		player.closeInventory();
	}
}
