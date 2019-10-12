package net.heroyn.heroynserverapi.shop.Minion;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.cosmetics.minion.minions.GhostMinion;
import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.utils.Head;
import net.heroyn.heroynserverapi.utils.UtilItem;

public class ShopMinionGhost extends ShopItemBuyableOnceDreams {

	public ShopMinionGhost(Player player) {
		super(player, "§aGhost", new String[]{"§6Etes-vous hantée ?"}, UtilItem.convertSkull(Head.GHOST_REDEYE.getTexture()), "Minion_Ghost", 0);
	}
	@Override
	public void onUse(Player player) {
		PlayerInfo pp = PlayerInfo.instanceOf(player);
		if (pp.getMinion() != null) {
			if (pp.isSameFamilliers(GhostMinion.class)) {
				player.sendMessage("§cVous avez déjà invoqué familliers !");
				return;
			} else {
				pp.getMinion().removeMinion();
				pp.setMinion(null);
				Reflection.getConstructor(GhostMinion.class, Player.class).invoke(player);
			}
		} else
		Reflection.getConstructor(GhostMinion.class, Player.class).invoke(player);
	}

	@Override
	public Map<Integer, String[]> buildLevelInfoBuyable() {
		Map<Integer, String[]> levelInfo = new HashMap<>();
		levelInfo.put(0, new String[] {"", "§aClic gauche: débloquer §b" + getPrice() +" §6Dreams"});
		levelInfo.put(1, new String[] {"§aacheté"});
		return levelInfo;
	}

}
