package net.heroyn.mobarena.mobs.entities;

import net.heroyn.mobarena.mobs.MaMobInterface;
import net.minecraft.server.v1_12_R1.EntityBlaze;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.World;

public class MaBlaze extends EntityBlaze implements MaMobInterface{

	public MaBlaze(World arg0) {
		super(arg0);
	}

	@Override
	public void setPathFinders() {
		this.targetSelector.a(10, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));	
	}

	@Override
	public void equip() {}

}
