package net.heroyn.heroynserverapi.shop.particle;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.cosmetics.particle.particles.WingsParticle;
import net.heroyn.heroynserverapi.nms.Reflection;

public class ShopParticleWings extends ShopItemBuyableOnceDreams {

	public ShopParticleWings(Player player) {
		super(player, "§6Ailes", new String[] {"§7Ayez du style avec ses ailes !"}, new ItemStack(Material.ELYTRA), "Particle_Wings", 0);
	}

	@Override
	public void onUse(Player player) {
		Reflection.getConstructor(WingsParticle.class, Player.class, WingsParticle.WingType.class).invoke(player, WingsParticle.WingType.DEMON_WINGS);
	}

	@Override
	public Map<Integer, String[]> buildLevelInfoBuyable() {
		Map<Integer, String[]> levelInfo = new HashMap<>();
		levelInfo.put(0, new String[] {"", "§aClic gauche: débloquer §b" + getPrice() +" §6Dreams"});
		levelInfo.put(1, new String[] {"§aacheté"});
		return levelInfo;
	}
}
