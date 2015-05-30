package com.lothrazar.samscontent.spell;

import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class BaseSpellExp implements ISpell
{
	public abstract SpellRegistry.EnumSpellType getSpellType();
	
	public abstract void cast(World world, EntityPlayer player, BlockPos pos);
	public abstract ItemStack getIconDisplay();
	public abstract int getExpCost();
	
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);
		//
		//Util.playSoundAt(player, Reference.sounds.bowtoss);
		Util.drainExp(player, getExpCost());
	}

	
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.wood_click);
	}
	
	
	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}
 
}
