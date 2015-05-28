package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType; 
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellPhasing implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.phase;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		EnumFacing face = EnumFacing.getFacingFromVector(
				(float)player.getLookVec().xCoord
				, (float)player.getLookVec().yCoord
				, (float)player.getLookVec().zCoord);

		System.out.println("TODO: bugfix phase  "+face.getName()+"  "+Util.posToString(pos));
		
		//.getHorizontal(MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);

		//ItemWallCompass.wallPhase(player.worldObj,player,pos,facing);
		
		int dist = 1;
		if(face.getOpposite() == EnumFacing.DOWN)
		{
			 dist = 2;//only move two when going down - player is 2 tall
		}
		
		BlockPos offs = pos.offset(face, dist);//was .getOpposite()
		
		//not 2, depends on block pos?
		if(world.isAirBlock(offs) && world.isAirBlock(offs.up()))
		{
			player.setPositionAndUpdate(offs.getX(), offs.getY(), offs.getZ()); 

			this.onCastSuccess(world, player, pos);
		}

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
		player.swingItem();
		 
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);  
		Util.spawnParticle(world, EnumParticleTypes.PORTAL, pos);
		
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.brick);
	}

}
