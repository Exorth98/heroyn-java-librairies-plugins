package net.heroyn.mobarena.classe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynapi.utils.ItemUtils;
import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;
import org.bukkit.Material;

public abstract class ClasseBase implements Listener {
	
	private HashMap<String,ConfigManager> CfgMngrs = HeroynMobarena.getInstance().ConfigManagers;
	
	public static 	List<ClasseBase> 				allClasse 			= new ArrayList<>();
	public 			Map<String, Skill> 				skills 				= new HashMap<>();
	
	public 			String 							name;
	public 			int 							level;
	public 			Player 							player;
	public 			MobarenaFighter					maF;
	
	public			ClassesStats					stats;
	
	public			double 							life;
	public			double 							resistance;
	public			double 							primaryDamage;
	public			double 							secondaryDamage;
	public			double 							speed;
	public			double							jump;
	public			boolean							fireResistance;
	
	public			String							taskName;
	public 			float 							energy;
	public			float							maxEnergy;
	public 			float 							energyRegen;
	public 			double 							healthRegen;
	
	public			List<ItemStack> 				kit;
	public			ItemStack[]						armor;
	public			ItemStack						firstWeapon;
	public			ItemStack						secondWeapon;
	
	public ClasseBase(Player player, int level, String name) {
		
		this.player 			= player;
		this.level 				= level;
		this.name				= name;
		this.energy 			= 100.0f;
		this.maxEnergy			= 100.0f;
		this.maF		   		= MobarenaFighter.getFromPlayer(player);
		
		this.stats				= ClassesStats.getStats(name, level);
		
		this.life				= stats.getLife();
		this.resistance			= stats.getResistance();
		this.primaryDamage		= stats.getDamage();
		this.secondaryDamage	= stats.getSecondaryDamage();
		this.speed				= stats.getSpeed();
		this.jump				= stats.getJump();
		this.fireResistance 	= stats.isFireResistant();
		
		this.kit 				= getKitFromCfg();
		this.armor 				= getArmorFromCfg();
		this.firstWeapon 		= getWeaponFromCfg();
		this.secondWeapon 		= getSecondWeaponFromCfg();
		
		//this.taskName		= player.getName() + "." + getName();
		//this.setUpSkill();
		//this.setRegain();
		//this.taskRegain();
		//this.player.setExp(this.energy / 100);
		HeroynMobarena.getInstance().getServer().getPluginManager().registerEvents(this, HeroynMobarena.getInstance());
		allClasse.add(this);
	}
	
	protected abstract void setUpSkill();
	protected abstract void setRegain();
	
	@EventHandler
	public abstract void inflictDamageByEntityEvent(EntityDamageByEntityEvent event);	
	
	
	// Methode
	
	public void taskRegain() {
		// ENERGY
//		TaskManager.runTaskTimer(new Runnable() {
//			@Override
//			public void run() {
//				
//				getPlayer().setExp(getEnergy() / 100);
//				getPlayer().setLevel(0);
//				
//				if (getEnergy() < getMaxEnergy()) {
//					double energy = getEnergyRegen();
//					double e = getEnergy();
//					if (e + energy > getMaxEnergy()) {
//						setEnergy(getMaxEnergy()); 
//					} else addEnergy(getEnergyRegen());
//				}
//			}
//		}, 0, 5, taskName);
//		// HEALTH
//		TaskManager.runTaskTimer(new Runnable() {
//			@SuppressWarnings("deprecation")
//			@Override
//			public void run() {
//				if (getPlayer().getHealth() < getPlayer().getMaxHealth()) {
//					double health = getPlayer().getHealth();
//					health += getHealthRegen();
//					if (health >= player.getMaxHealth()) health = player.getMaxHealth();
//					getPlayer().setHealth(health);
//				}
//			}
//		}, 0, 20 * 2, taskName);
	}
	
	/**
	 * Envoie les items du kit au joueur
	 */
	public void sendKitPlayer() {
		
		
		//GIVE KIT
		player.getInventory().setItem(0, this.firstWeapon);
		player.getInventory().setItem(1, this.secondWeapon);
		player.getInventory().setArmorContents(this.armor);
		for(ItemStack item : this.kit)player.getInventory().addItem(item);
		player.updateInventory();
		
		//GIVE SKILLS
//		for (Skill skill : getSkills().values()) {
//			player.getInventory().addItem(skill.getBuildItem());
//		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void setupHealth() {
		
		player.setMaxHealth(life);
		player.setHealth(life);
		
	}
	
	public void setupEffects() {
		
		if(stats.getSpeed() != -1)	player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, stats.getSpeed()));
		if(stats.getJump() != -1)	player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, stats.getJump()));	
		if(fireResistance)			player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
	}
	

	public Skill getSkill(String skillName) {
		return this.skills.get(skillName);
	}
	
	/**
	 * Enleve de l'energy
	 * @param energy
	 */
	public void removeEnergy(float energy) {
		this.energy -= energy;
	}
	/**
	 * Ajoute de l'energy
	 * @param energy
	 */
	public void addEnergy(float energy) {
		this.energy += energy;
	}
	/**
	 * set l'energy
	 * @param energy
	 */
	public void setEnergy(float energy) {
		this.energy = energy;
	}
	
	public float getEnergy() {
		return energy;
	}
	
	public float getMaxEnergy() {
		return maxEnergy;
	}
	
	public MobarenaFighter getMaPlayer() {
		return maF;
	}
	
	public float getEnergyRegen() {
		return energyRegen;
	}
	
	public double getHealthRegen() {
		return healthRegen;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String getName() {
		return name;
	}
	
	private List<ItemStack> getKitFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<ItemStack> items =  (List<ItemStack>) CfgMngrs.get("class." + this.name).getCustomConfig().getList("Kit");	
		if(items == null) items = new ArrayList<>();
		
		
		return items;
	}
	
	private ItemStack[] getArmorFromCfg() {
		
		@SuppressWarnings("unchecked")
		List<ItemStack> items =  (List<ItemStack>) CfgMngrs.get("class." + this.name).getCustomConfig().getList("Armor");	
		if(items == null) items = new ArrayList<>();
		
		ItemStack[] armor = new ItemStack[4];
		for(int i =0; i<armor.length;i++) {
			List<String> lore = new ArrayList<>();
			lore.add( "§aRésistance : §b" + ((double)(this.resistance*100.00)) + "%");
			if(fireResistance) lore.add("§eRésistance au feu");
			ItemStack armorItem = ItemUtils.setLores(items.get(i).clone(), lore);
//			ItemArmor ar = new ItemArmor(EnumArmorMaterial.GOLD, 1 , EnumItemSlot.CHEST);
			armor[i] = armorItem;
			
		}
		
		return armor;
	}
	
	private ItemStack getWeaponFromCfg() {
		
		ItemStack weapon = CfgMngrs.get("class." + this.name).getCustomConfig().getItemStack("Weapon");	
		List<String> lore = new ArrayList<>();
		lore.add("§cDégats : §e" + (double)this.primaryDamage);
		ItemStack weaponItem = ItemUtils.setLores(weapon.clone(), lore);
		return weaponItem;
	}
	
	private ItemStack getSecondWeaponFromCfg() {
		
		ItemStack weapon = CfgMngrs.get("class." + this.name).getCustomConfig().getItemStack("SecondaryWeapon");	
		if(weapon.getType() != Material.AIR) {
			List<String> lore = new ArrayList<>();
			lore.add("§cDégats : §e" + (double)this.secondaryDamage);
			ItemStack weaponItem = ItemUtils.setLores(weapon.clone(), lore);
			return weaponItem;
		}
		else return weapon;
	}
	
	public List<ItemStack> getKit() {
		return this.kit;
	}
	
	public static void setKit(ItemStack weapon, ItemStack sWeapon, ItemStack[] armor, ItemStack[] contents, String name) {
		
		FileConfiguration ClassCfg = HeroynMobarena.getInstance().ConfigManagers.get("class." + name).getCustomConfig();
		
		ClassCfg.set("Weapon", weapon);
		ClassCfg.set("SecondaryWeapon", sWeapon);
		ClassCfg.set("Armor", armor);
		ClassCfg.set("Kit", HeroynMobarenaUtils.getKitList(contents,weapon, sWeapon,armor));
		HeroynMobarena.getInstance().saveCustomConfigs();
		
	}
	
	public static List<ClasseBase> getAllClasse() {
		return allClasse;
	}
	
	public Map<String, Skill> getSkills() {
		return skills;
	}
	
	public Player getPlayer() {
		return player;
	}

	public MobarenaFighter getMaF() {
		return maF;
	}

	public double getLife() {
		return life;
	}

	public double getResistance() {
		return resistance;
	}

	public double getDamage() {
		return primaryDamage;
	}
	
	public double getSecondaryDamage() {
		return secondaryDamage;
	}

	public double getSpeed() {
		return speed;
	}

	public double getJump() {
		return jump;
	}

	public boolean isFireResistance() {
		return fireResistance;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public ItemStack getWeapon() {
		return firstWeapon;
	}
	
	public ItemStack getSecondWeapon() {
		return secondWeapon;
	}
	
	
}
