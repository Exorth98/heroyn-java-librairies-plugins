package net.heroyn.mobarena.mobs;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;

import net.heroyn.mobarena.mobs.entities.MaBlaze;
import net.heroyn.mobarena.mobs.entities.MaCaveSpider;
import net.heroyn.mobarena.mobs.entities.MaCreeper;
import net.heroyn.mobarena.mobs.entities.MaEnderman;
import net.heroyn.mobarena.mobs.entities.MaEndermite;
import net.heroyn.mobarena.mobs.entities.MaEvoker;
import net.heroyn.mobarena.mobs.entities.MaHusk;
import net.heroyn.mobarena.mobs.entities.MaIllusioner;
import net.heroyn.mobarena.mobs.entities.MaIronGolem;
import net.heroyn.mobarena.mobs.entities.MaMagmaCube;
import net.heroyn.mobarena.mobs.entities.MaPigZombie;
import net.heroyn.mobarena.mobs.entities.MaPolarBear;
import net.heroyn.mobarena.mobs.entities.MaSilverFish;
import net.heroyn.mobarena.mobs.entities.MaSkeleton;
import net.heroyn.mobarena.mobs.entities.MaSkeletonHorse;
import net.heroyn.mobarena.mobs.entities.MaSlime;
import net.heroyn.mobarena.mobs.entities.MaSpider;
import net.heroyn.mobarena.mobs.entities.MaStray;
import net.heroyn.mobarena.mobs.entities.MaVex;
import net.heroyn.mobarena.mobs.entities.MaVindicator;
import net.heroyn.mobarena.mobs.entities.MaWitch;
import net.heroyn.mobarena.mobs.entities.MaWitherSkeleton;
import net.heroyn.mobarena.mobs.entities.MaZombie;
import net.heroyn.mobarena.mobs.entities.MaZombieHorse;
import net.heroyn.mobarena.mobs.entities.MaZombieVillager;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityLiving;

public enum MaMobStats {
	
//	 NAME					    ENTITY              		CLASS			   COINS   SCORE  HEALTH  RESISTANCE  DAMAGE  SPEED  REGENERATION  JUMP      SPAWNING
	ZOMBIE  		(	EntityType.ZOMBIE	  	  ,  MaZombie.class 	 	,  0.10  ,  05  ,  30.0  ,	 0.05    ,  3.0  ,	 0  ,	  -1     ,	-1  ,  "[1-20]:50/2"),
	SPIDER  		(   EntityType.SPIDER     	  ,  MaSpider.class  	 	,  0.15  ,  05  ,  25.0  ,   0.15    ,  4.0  ,   0  ,     -1     ,  -1	,  "[1-20]:50/2"),					
	SKELETON		(	EntityType.SKELETON   	  ,  MaSkeleton.class	 	,  0.15  ,  05  ,  30.0  ,   0.05    ,  5.0  ,   0  ,     -1     ,  -1  ,  "[1-20]:50/2"),
	BLAZE			(   EntityType.BLAZE	  	  ,  MaBlaze.class	    	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	CAVESPIDER		(	EntityType.CAVE_SPIDER	  ,  MaCaveSpider.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	CREEPER			(	EntityType.CREEPER		  ,  MaCreeper.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	ENDERMAN		(	EntityType.ENDERMAN		  ,  MaEnderman.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	ENDERMITE		(	EntityType.ENDERMITE	  ,  MaEndermite.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	EVOKER			(	EntityType.EVOKER		  ,  MaEvoker.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	HUSK			(	EntityType.HUSK			  ,  MaHusk.class 			,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	MAGMACUBE		(	EntityType.MAGMA_CUBE 	  ,  MaMagmaCube.class  	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	POLARBEAR		(	EntityType.POLAR_BEAR	  ,  MaPolarBear.class		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	SILVERFISH		(	EntityType.SILVERFISH	  ,  MaSilverFish.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	SKELETONHORSE	(	EntityType.SKELETON_HORSE ,  MaSkeletonHorse.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	SLIME			(	EntityType.SLIME		  ,  MaSlime.class  		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	STRAY			(	EntityType.STRAY		  ,  MaStray.class 			,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	VEX				(	EntityType.VEX			  ,  MaVex.class 			,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	VINDICATOR		(	EntityType.VINDICATOR	  ,  MaVindicator.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	WITCH			(	EntityType.WITCH		  ,  MaWitch.class 			,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	WITHERSKELETON	(	EntityType.WITHER_SKELETON,  MaWitherSkeleton.class ,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	ZOMBIEHORSE		(	EntityType.ZOMBIE_HORSE	  ,  MaZombieHorse.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	PIGZOMBIE		(	EntityType.PIG_ZOMBIE	  ,  MaPigZombie.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	ZOMBIEVILLAGER	(	EntityType.ZOMBIE_VILLAGER,  MaZombieVillager.class ,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	IRONGOLEM		(	EntityType.IRON_GOLEM     ,  MaIronGolem.class 		,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,        "X"    ),
	ILLUSIONER		(	EntityType.ILLUSIONER	  ,  MaIllusioner.class 	,  0.00  ,  05  ,  20.0  ,   0.10    ,  5.0  ,   0  ,     -1     ,  -1  ,  "[1-20]:100"    );
			
	
	
	private EntityType type;
	private Class<? extends EntityLiving> maEntity;
	private double coins;
	private int score;
	private double health;
	private double resistance;
	private double damage;
	private int speed;
	private int regeneration;
	private int jump;
	private String spawning;
	
	private MaMobStats(EntityType type, Class<? extends EntityLiving> maEntity, double coins, int score, double health, double resistance,
			double damage, int speed, int regeneration, int jump, String spawning) {
		
		this.type = type;
		this.maEntity = maEntity;
		this.coins = coins;
		this.score = score;
		this.health = health;
		this.resistance = resistance;
		this.damage = damage;
		this.speed = speed;
		this.regeneration = regeneration;
		this.jump = jump;
		this.spawning =spawning;
	}

	public EntityType getType() {
		return type;
	}
	
	public Class<? extends Entity> getMaEntityClass() {
		return maEntity;
	}

	public double getCoins() {
		return coins;
	}

	public int getScore() {
		return score;
	}

	public double getHealth() {
		return health;
	}

	public double getResistance() {
		return resistance;
	}

	public double getDamage() {
		return damage;
	}

	public int getSpeed() {
		return speed;
	}

	public int getRegeneration() {
		return regeneration;
	}

	public int getJump() {
		return jump;
	}
	
	public String getSpawning() {
		return spawning;
	}
	
	public int getSpawnAmount(int wave) {	
		
		int amount = 0;
		SpawningInfos si = new SpawningInfos(getSpawning());	
		if(si.getAmounts().get(wave) != null)amount = si.getAmounts().get(wave);
		
		return amount;
	}

	public static MaMobStats getStats(org.bukkit.entity.Entity entity) {
			
		EntityType type = entity.getType();	
		if(type == EntityType.ARROW)	
			type = ((org.bukkit.entity.Entity) ((Arrow) entity).getShooter()).getType();
		
		for(MaMobStats stats : MaMobStats.values())
			if(stats.getType() == type)
				return stats;
		
		return null;
	}
	
	
	
	
	
}
