package net.heroyn.mobarena.mobs;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.heroyn.mobarena.game.MobarenaGame;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.World;

public class MobarenaMob{
	
	MobarenaGame game;
	World world;
	EntityLiving entity;
	MaMobStats stats;
	
	public MobarenaMob(MobarenaGame game, MaMobStats mobStats)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {	
		
		World world = ((CraftWorld)game.getArena().getZone().getWorld(true)).getHandle();
		
		this.game = game;
		this.entity = (EntityLiving) mobStats.getMaEntityClass().getConstructor(World.class).newInstance(world);
		this.world = world;
		this.stats = mobStats;
		
		setMobStats();
	}
	
	@SuppressWarnings("deprecation")
	private void setMobStats() {
		
		//IN CLASS
		((MaMobInterface)entity).setPathFinders();
		((MaMobInterface)entity).equip();
		
		//HEALTH
		((LivingEntity) entity.getBukkitEntity() ).setMaxHealth(stats.getHealth());
		entity.setHealth( (float) stats.getHealth()); 
		
		//EFFECTS
		if(stats.getSpeed() != -1)((LivingEntity)entity.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, stats.getSpeed()));
		if(stats.getJump() != -1)((LivingEntity)entity.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, stats.getJump()));
		if(stats.getRegeneration() != -1)((LivingEntity)entity.getBukkitEntity()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, stats.getRegeneration()));
		
		
		
	}

	public void SpawnMaMob(Location loc) {
				
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		entity.getWorld().addEntity(entity);
	}

	public MobarenaGame getGame() {
		return this.game;		
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public World getWorld() {
		return this.world;
	}
}
