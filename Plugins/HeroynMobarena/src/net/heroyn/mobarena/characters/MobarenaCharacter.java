package net.heroyn.mobarena.characters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.mobarena.HeroynMobarena;

public abstract class MobarenaCharacter {

	Plugin plugin = HeroynMobarena.getInstance();	
	private HashMap<String,ConfigManager> CfgMngrs = HeroynMobarena.getInstance().ConfigManagers;
	
	protected int level;
	
	protected String name;
	protected double life;
	protected double resistance;
	protected double damage;
	protected double speed;
	protected double jump;
	//HashMap<Integer, ItemStack> inventory;
	protected ItemStack[] kit;
	
	public MobarenaCharacter(String name, int Level) {
		
		this.name = name;
		this.life = CfgMngrs.get("class." + name).getCustomConfig().getDouble(this.level +".Life");
		this.resistance = CfgMngrs.get("class." + name).getCustomConfig().getDouble(this.level +".Resistance");
		this.damage = CfgMngrs.get("class." + name).getCustomConfig().getDouble(this.level +".Damage");
		this.speed = CfgMngrs.get("class." + name).getCustomConfig().getDouble(this.level +".Speed");
		this.jump = CfgMngrs.get("class." + name).getCustomConfig().getDouble(this.level +".Jump");	
		this.kit = getKitFromCfg();
		
	
		
	}
	
	private ItemStack[] getKitFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<ItemStack> items =  (List<ItemStack>) CfgMngrs.get("class." + name).getCustomConfig().getList("Kit");	
		if(items == null) items = new ArrayList<>();
		
		ItemStack[] kit = new ItemStack[items.size()];
		for(int i =0; i<kit.length;i++) {
			
			kit[i] = items.get(i);
			
		}
		
		return kit;
	}
	
	//GETTERS
	public String getName() {				
		return this.name;		
	}
	public double getLife() {		
		return this.life;
	}
	public double getResistance() {		
		return this.resistance;
	}	
	public double getDamage() {		
		return this.damage;
	}	
	public double getSpeed() {		
		return this.speed;
	}
	public double getJump() {		
		return this.jump;
	}
	public int getLevel() {		
		return this.level;
	}
	public ItemStack[] getKit() {
		return this.kit;
	}
	
	//SETTERS
	public static void setKit(ItemStack[] kit, String name) {
		
		HeroynMobarena.getInstance().ConfigManagers.get("class." + name).getCustomConfig().set("Kit", kit);
		HeroynMobarena.getInstance().saveCustomConfigs();
		
	}
	
	
	//ABSTRACT METHODS
	abstract void specialAttack();
	
}
