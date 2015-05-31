package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellWaterwalk extends BaseSpellExp implements ISpell
{ 
	private static int seconds = Reference.TICKS_PER_SEC * 10; 
	
	@Override
	public String getSpellID()
	{
		return "waterwalk";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,seconds,0));

	}
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.drink);

		super.onCastSuccess(world, player, pos);
	}
	@Override
	public int getExpCost()
	{
		return 5;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_waterwalk_dummy);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.slowfall;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.haste;
	}
}
