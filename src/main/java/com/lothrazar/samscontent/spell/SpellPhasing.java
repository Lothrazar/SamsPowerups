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

public class SpellPhasing extends BaseSpellExp implements ISpell
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
 
		}
	}
 
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);  
		Util.spawnParticle(world, EnumParticleTypes.PORTAL, pos);

		super.onCastSuccess(world, player, pos);
	}

	@Override
	public int getExpCost()
	{
		return 2;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.brick);
	}
}
