package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellFirebolt implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.chest;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{

		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();
/*
		world.spawnEntityInWorld(new EntitySmallFireball(world,up.getX(),up.getY(),up.getZ()
				 ,player.getLookVec().xCoord
				 ,player.getLookVec().yCoord
				 ,player.getLookVec().zCoord));*/
		world.spawnEntityInWorld(new EntitySmallFireball(world,player
		 ,player.getLookVec().xCoord
		 ,player.getLookVec().yCoord
		 ,player.getLookVec().zCoord));

		Util.playSoundAt(player, Reference.sounds.bowtoss);

		
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public void drainExpCost(EntityPlayer player)
	{ 
		 Util.drainExp(player, getExpCost());
	}

	private int cost = 10;
	@Override
	public void setExpCost(int c)
	{
		cost = c;
	}
	@Override
	public int getExpCost()
	{
		return cost;
	}
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

}
