package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 

public class SpellPhasing extends BaseSpellExp implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "phase";
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
		ModSpells.spawnParticle(world, EnumParticleTypes.PORTAL, pos);

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
	@Override
	public ISpell left()
	{
		return SpellRegistry.jump;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.pearl;
	}
}