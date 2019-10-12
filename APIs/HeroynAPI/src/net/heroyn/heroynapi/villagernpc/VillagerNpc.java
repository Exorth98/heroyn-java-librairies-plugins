package net.heroyn.heroynapi.villagernpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

public abstract class VillagerNpc {
	
	public static Map<String, VillagerNpc> allSpawnedVillagerNpc= new HashMap<>();
	
	private String displayName;
	private String name;
	private Villager villager;
	private Location loc;
	
	public VillagerNpc(String name, Location loc) {
		
		this.name = name;
		this.displayName = name;
		this.loc = loc;
	}

	public VillagerNpc(String name, String displayName, Location loc) {
		
		this.name = name;
		this.displayName = displayName;
		this.loc = loc;
	}
	
	public void createEntity() {
		
        Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        Entity nmsVillager = (Entity) ((CraftEntity) npc).getHandle();
        nmsVillager.setCustomName(displayName);
        nmsVillager.setCustomNameVisible(true);
        nmsVillager.teleport(new Location(nmsVillager.getLocation().getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch()));
        npc.setAI(false);
		this.villager = npc;
		
		allSpawnedVillagerNpc.putIfAbsent(this.name, this);
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public Villager getVillager() {
		return villager;
	}

	public Location getLocation() {
		return loc;
	}

	public static boolean isNpcLoc(Location entityLoc) {
		
		for(VillagerNpc npc : allSpawnedVillagerNpc.values()) {
			
			if (entityLoc.equals(npc.getLocation()))
					return true;
			
		}
		
		return false;
	}

	public static VillagerNpc getNpc(Location entityLoc) {
		
		VillagerNpc npc = null;
		
		for(VillagerNpc vNpc : allSpawnedVillagerNpc.values()) {
			
			if (entityLoc.equals(vNpc.getLocation()))
					npc = vNpc;
			
		}

		return npc;
	}
	
	public boolean isSpawned() {
		
		List<LivingEntity> entities = loc.getWorld().getLivingEntities();
		
		for(LivingEntity ent : entities)
			if(ent instanceof Villager && ent.getLocation().equals(loc))
				return true;
		
		return false;
		
	}
	
	

}
