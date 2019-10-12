package net.heroyn.heroynserverapi.shop.particle;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.cosmetics.particle.particles.ApocalypticCloudParticle;
import net.heroyn.heroynserverapi.nms.Reflection;

public class ShopParticleApocalyptic extends ShopItemBuyableOnceDreams {

	@SuppressWarnings("deprecation")
	public ShopParticleApocalyptic(Player player) {
		super(player, "§6Apocalypse", new String[] {"§7L'apocalypse autour de vous !"}, new ItemStack(Material.LEGACY_CONCRETE_POWDER), "Particle_Apocalypse", 0);
		setHalloweenItem(true);
	}

	@Override
	public void onUse(Player player) {
		Reflection.getConstructor(ApocalypticCloudParticle.class, Player.class).invoke(player);
	}

	@Override
	public Map<Integer, String[]> buildLevelInfoBuyable() {
		Map<Integer, String[]> levelInfo = new HashMap<>();
		if (HeroynServerAPI.isHalloween) {
			levelInfo.put(0, new String[] {"", "é§aClic gauche: débloquer §b" + getPrice() +" §6Dreams"});
		} else levelInfo.put(0, new String[] {"§cAchetable durant Halloween"});
		levelInfo.put(1, new String[] {"§aacheté"});
		return levelInfo;
	}
}
