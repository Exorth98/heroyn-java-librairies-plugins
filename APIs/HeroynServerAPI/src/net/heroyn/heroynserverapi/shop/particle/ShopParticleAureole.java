package net.heroyn.heroynserverapi.shop.particle;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.SpecialItem.ShopItemBuyableOnceDreams;
import net.heroyn.heroynserverapi.cosmetics.particle.particles.AureoleParticle;
import net.heroyn.heroynserverapi.nms.Reflection;

public class ShopParticleAureole extends ShopItemBuyableOnceDreams {

	public ShopParticleAureole(Player player) {
		super(player, "§6Couronne de flamme", new String[] {"§7Une couronne qui tourne", "§7au dessus de votre tête"}, new ItemStack(Material.BLAZE_POWDER), "Particle_Aureole", 0);
	}

	@Override
	public void onUse(Player player) {
		Reflection.getConstructor(AureoleParticle.class, Player.class, Particle.class).invoke(player, Particle.FLAME);
	}

	@Override
	public Map<Integer, String[]> buildLevelInfoBuyable() {
		Map<Integer, String[]> levelInfo = new HashMap<>();
		levelInfo.put(0, new String[] {"", "§aClic gauche: débloquer §b" + getPrice() +" §6Dreams"});
		levelInfo.put(1, new String[] {"§aacheté"});
		return levelInfo;
	}
}
