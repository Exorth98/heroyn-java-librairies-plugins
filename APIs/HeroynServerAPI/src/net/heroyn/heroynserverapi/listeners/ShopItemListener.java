package net.heroyn.heroynserverapi.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.SpecialItem.shopItemInterface;
import net.heroyn.heroynserverapi.SpecialItem.Item.ItemMenuConfirmShop;
import net.heroyn.heroynserverapi.SpecialItem.virtualMenu.VirtualMenuConfirmShop;
import net.heroyn.heroynserverapi.player.PlayerInfo;

public class ShopItemListener implements Listener {

	public static Map<Player, String> shopItemName = new HashMap<>();
	public static Map<Player, String> shopItemPermissions = new HashMap<>();
	public static Map<Player, Double> shopItemPrice = new HashMap<>();
	
	
	@EventHandler
	public void onPlayerInteract(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if (item == null) {
			return;
		}
		for (ShopItemBuyableOnceDreams shop : ShopItemBuyableOnceDreams.getAllShopItem()) {
			if (((shop instanceof shopItemInterface)) && (item.isSimilar(shop.getItems()))) {
				event.setCancelled(true);
				if (!PlayerInfo.instanceOf(player).hasPermissions(shop.getPermissions())) {
					// Shop is Noel Item ?
					if (shop.isNoelItem() && (!HeroynServerAPI.isNoel)) {
						player.sendMessage("§7[§bBoutique§7] §cVous pouvez acheter cette item durant no"+"Ë".toLowerCase()+"l !");
						shop.close(player);
						break;
					}
					// Shop is Halloween Item ?
					if (shop.isHalloweenItem() && (!HeroynServerAPI.isHalloween)) {
						player.sendMessage("§7[§bBoutique§7] §cVous pouvez acheter cette item durant Halloween !");
						shop.close(player);
						break;
					}
					
					new VirtualMenuConfirmShop(player, item);
					shopItemName.put(player, shop.getName());
					shopItemPermissions.put(player, shop.getPermissions());
					shopItemPrice.put(player, shop.getPrice());
					break;
					
				} else if (PlayerInfo.instanceOf(player).hasPermissions(shop.getPermissions())) {
					shop.onUse(player);
					shop.close(player);
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		if ((item == null) || (!item.hasItemMeta())) return;
		if(!item.getItemMeta().hasDisplayName())return;
		if(!item.getItemMeta().getDisplayName().equalsIgnoreCase(ItemMenuConfirmShop.displayName))return;
		
		buy(player);
	}
	
	private void buy(Player player) {
		PlayerInfo pp = PlayerInfo.instanceOf(player);
		String permissions = shopItemPermissions.get(player);
		String name = shopItemName.get(player);
		double price = shopItemPrice.get(player);
		if (pp.getDreams() < price) {
			player.sendMessage("§7[§bBoutique§7] §Vous ne possédez pas assez de Dreams !");
			player.closeInventory();
			clear(player);
			return;
		}
		pp.removeDreams(price);
		pp.addPermissions(permissions);
		player.sendMessage("§7[§bBoutique§7] §6Achat réussi " + name + " §e-" + price);
		player.closeInventory();
		clear(player);
	}
	
	public void clear(Player player) {
		shopItemName.remove(player);
		shopItemPermissions.remove(player);
		shopItemPermissions.remove(player);
	}
}
