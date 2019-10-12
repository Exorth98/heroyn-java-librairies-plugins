package net.heroyn.mobarena.mobs.entities;

import net.heroyn.mobarena.mobs.MaMobInterface;
import net.minecraft.server.v1_12_R1.EntityIronGolem;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.World;

public class MaIronGolem extends EntityIronGolem implements MaMobInterface{

	public MaIronGolem(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPathFinders() {
		this.targetSelector.a(10, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));	
		
	}

	@Override
	public void equip() {
		// TODO Auto-generated method stub
		
	}

}
