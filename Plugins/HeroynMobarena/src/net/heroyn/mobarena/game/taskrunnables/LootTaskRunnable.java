package net.heroyn.mobarena.game.taskrunnables;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import net.heroyn.mobarena.game.MobarenaGame;

public class LootTaskRunnable implements Runnable {

	private MobarenaGame game;
	
	public LootTaskRunnable(MobarenaGame game) {
		
		this.game = game;
	}
	
	@Override
	public void run() {
		
		
		Location lootLoc = game.getRandomLootPoint();
		ItemStack loot = game.getRandomLoot();
		
		lootLoc.getWorld().dropItem(lootLoc, loot);
		

	}

}
