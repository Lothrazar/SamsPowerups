package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellWaterwalk implements ISpell
{ 
	private static int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?
	
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.waterwalk;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,fiveSeconds,0));

	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}
 
	private int cost = 1;
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
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);
		
		Util.playSoundAt(player, Reference.sounds.bowtoss);
		Util.drainExp(player, getExpCost());
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_waterwalk_dummy);
	}

}
