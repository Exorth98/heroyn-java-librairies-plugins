package net.heroyn.mobarena.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MobarenaDataCard {
	
	Player p;
	ItemStack[] inv;
	int levels;
	float exp;
	double health;
	int food;
	Location loc;
	
	public MobarenaDataCard(Player p) {
		
		this.p=p;
		this.inv = p.getInventory().getContents();
		this.levels = p.getLevel();
		this.exp = p.getExp();
		this.health = p.getHealth();
		this.food = p.getFoodLevel();
		this.loc = p.getLocation();
		
		
	}
	
	public void restore() {
		
		p.getInventory().setContents(this.inv);
		p.updateInventory();
		p.teleport(this.loc);
		p.setLevel(this.levels);
		p.setExp(this.exp);
		p.setHealth(this.health);
		p.setFoodLevel(this.food);		
	}
	

	public Player getP() {
		return p;
	}

	public ItemStack[] getInv() {
		return inv;
	}

	public int getLevels() {
		return levels;
	}

	public float getExp() {
		return exp;
	}

	public double getHealth() {
		return health;
	}

	public double getFood() {
		return food;
	}

	public Location getLoc() {
		return loc;
	}


	
	
	
	

}
