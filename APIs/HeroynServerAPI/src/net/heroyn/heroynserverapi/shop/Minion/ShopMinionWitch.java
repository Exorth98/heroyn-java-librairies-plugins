package net.heroyn.heroynserverapi.shop.Minion;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.cosmetics.minion.minions.WitchMinion;
import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.player.PlayerInfo;

public class ShopMinionWitch extends ShopItemBuyableOnceDreams {

	@SuppressWarnings("deprecation")
	public ShopMinionWitch(Player player) {
		super(player, "§aWitch", new String[]{"§6Une sorcière avec vous", "§dElle fais même des potions magiques !"}, new ItemStack(Material.LEGACY_CAULDRON_ITEM), "Minion_Witch", 0);
	}
	@Override
	public void onUse(Player player) {
		PlayerInfo pp = PlayerInfo.instanceOf(player);
		if (pp.getMinion() != null) {
			if (pp.isSameFamilliers(WitchMinion.class)) {
				player.sendMessage("§cVous avez déjà invoqué ce familliers !");
				return;
			} else {
				pp.getMinion().removeMinion();
				pp.setMinion(null);
				Reflection.getConstructor(WitchMinion.class, Player.class).invoke(player);
			}
		} else 
			Reflection.getConstructor(WitchMinion.class, Player.class).invoke(player);
	}

	@Override
	public Map<Integer, String[]> buildLevelInfoBuyable() {
		Map<Integer, String[]> levelInfo = new HashMap<>();
		levelInfo.put(0, new String[] {"", "§aClic gauche: débloquer §b" + getPrice() +" §6Dreams"});
		levelInfo.put(1, new String[] {"§aacheté"});
		return levelInfo;
	}

}
