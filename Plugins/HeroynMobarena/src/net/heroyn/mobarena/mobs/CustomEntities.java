package net.heroyn.mobarena.mobs;

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
import net.minecraft.server.v1_12_R1.EntityBlaze;
import net.minecraft.server.v1_12_R1.EntityCaveSpider;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.EntityEnderman;
import net.minecraft.server.v1_12_R1.EntityEndermite;
import net.minecraft.server.v1_12_R1.EntityEvoker;
import net.minecraft.server.v1_12_R1.EntityHorseSkeleton;
import net.minecraft.server.v1_12_R1.EntityHorseZombie;
import net.minecraft.server.v1_12_R1.EntityIllagerIllusioner;
import net.minecraft.server.v1_12_R1.EntityIronGolem;
import net.minecraft.server.v1_12_R1.EntityMagmaCube;
import net.minecraft.server.v1_12_R1.EntityPigZombie;
import net.minecraft.server.v1_12_R1.EntityPolarBear;
import net.minecraft.server.v1_12_R1.EntitySilverfish;
import net.minecraft.server.v1_12_R1.EntitySkeleton;
import net.minecraft.server.v1_12_R1.EntitySkeletonStray;
import net.minecraft.server.v1_12_R1.EntitySkeletonWither;
import net.minecraft.server.v1_12_R1.EntitySlime;
import net.minecraft.server.v1_12_R1.EntitySpider;
import net.minecraft.server.v1_12_R1.EntityTypes;
import net.minecraft.server.v1_12_R1.EntityVex;
import net.minecraft.server.v1_12_R1.EntityVindicator;
import net.minecraft.server.v1_12_R1.EntityWitch;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.EntityZombieHusk;
import net.minecraft.server.v1_12_R1.EntityZombieVillager;
import net.minecraft.server.v1_12_R1.MinecraftKey;

public enum CustomEntities {

    MA_ZOMBIE			("MaZombie"			, 54 , EntityType.ZOMBIE		 , EntityZombie.class		 	, MaZombie.class),
	MA_SPIDER			("MaSpider"			, 52 , EntityType.SPIDER		 , EntitySpider.class		 	, MaSpider.class),
	MA_SKELETON			("MaSkeleton"		, 51 , EntityType.SKELETON		 , EntitySkeleton.class		 	, MaSkeleton.class),
	MA_BLAZE			("MaBlaze"			, 61 , EntityType.BLAZE			 , EntityBlaze.class		 	, MaBlaze.class),
	MA_CAVESPIDER		("MaCaveSpider"		, 59 , EntityType.CAVE_SPIDER	 , EntityCaveSpider.class	 	, MaCaveSpider.class),
	MA_CREEPER			("MaCreeper" 		, 50 , EntityType.CREEPER		 , EntityCreeper.class		 	, MaCreeper.class),
	MA_ENDERMAN			("MaEnderman" 		, 58 , EntityType.ENDERMAN		 , EntityEnderman.class		 	, MaEnderman.class),
	MA_ENDERMITE		("MaEndermite" 		, 67 , EntityType.ENDERMITE		 , EntityEndermite.class	 	, MaEndermite.class),
	MA_EVOKER			("MaEvoker" 		, 34 , EntityType.EVOKER		 , EntityEvoker.class		 	, MaEvoker.class),
	MA_HUSK				("MaHusk" 			, 23 , EntityType.HUSK			 , EntityZombieHusk.class	 	, MaHusk.class),
	MA_MAGMACUBE		("MaMagmaCube" 		, 62 , EntityType.MAGMA_CUBE	 , EntityMagmaCube.class	 	, MaMagmaCube.class),
	MA_POLARBEAR		("MaPolarBear" 		,102 , EntityType.POLAR_BEAR	 , EntityPolarBear.class	 	, MaPolarBear.class),
	MA_SILVERFISH		("MaSilverFish"		, 60 , EntityType.SILVERFISH	 , EntitySilverfish.class	 	, MaSilverFish.class),
	MA_SKELETONHORSE	("MaSkeletonHorse" 	, 28 , EntityType.SKELETON_HORSE , EntityHorseSkeleton.class 	, MaSkeletonHorse.class),
	MA_SLIME			("MaSlime"	 		, 55 , EntityType.SLIME			 , EntitySlime.class		 	, MaSlime.class),
	MA_STRAY			("MaStray"	 		,  6 , EntityType.STRAY			 , EntitySkeletonStray.class 	, MaStray.class),
	MA_VEX				("MaVex"	 		, 35 , EntityType.VEX			 , EntityVex.class			 	, MaVex.class),
	MA_VINDICATOR		("MaVindicator"		, 36 , EntityType.VINDICATOR	 , EntityVindicator.class	 	, MaVindicator.class),
	MA_WITCH			("MaWitch" 			, 66 , EntityType.WITCH			 , EntityWitch.class		 	, MaWitch.class),
	MA_WITHERSKELETON	("MaWitherSkeleton"	,  5 , EntityType.WITHER_SKELETON, EntitySkeletonWither.class	, MaWitherSkeleton.class),
	MA_ZOMBIEHORSE		("MaZombieHorse"	, 29 , EntityType.ZOMBIE_HORSE	 , EntityHorseZombie.class	 	, MaZombieHorse.class),
	MA_PIGZOMBIE		("MaPigZombie" 		, 57 , EntityType.PIG_ZOMBIE	 , EntityPigZombie.class	 	, MaPigZombie.class),
	MA_ZOMBIEVILLAGER	("MaZombieVillager"	, 27 , EntityType.ZOMBIE_VILLAGER, EntityZombieVillager.class	, MaZombieVillager.class),
	MA_IRONGOLEM		("MaIronGolem" 		, 99 , EntityType.IRON_GOLEM     , EntityIronGolem.class	 	, MaIronGolem.class),
	MA_ILLUSIONER		("MaIllusioner"		, 37 , EntityType.ILLUSIONER	 , EntityIllagerIllusioner.class, MaIllusioner.class);
	
    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends Entity> customClass;
    private MinecraftKey key;
    private MinecraftKey oldKey;

    private CustomEntities(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
        this.oldKey = EntityTypes.b.b(nmsClass);
    }

    public static void registerEntities() { for (CustomEntities ce : CustomEntities.values()) ce.register(); }
    public static void unregisterEntities() { for (CustomEntities ce : CustomEntities.values()) ce.unregister(); }

    private void register() {
        EntityTypes.d.add(key);
        EntityTypes.b.a(id, key, customClass);
    }

    private void unregister() {
        EntityTypes.d.remove(key);
        EntityTypes.b.a(id, oldKey, nmsClass);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }
}
