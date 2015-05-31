package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellSlowfall extends BaseSpellExp implements ISpell
{ 
	private static int seconds = Reference.TICKS_PER_SEC * 10;
	
	@Override
	public String getSpellID()
	{
		return "slowfall";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.slowfall.id,seconds,0));
	}

	@Override
	public int getExpCost()
	{
		return 5;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{ 
		return new ItemStack(Items.feather);
	}

	@Override
	public ISpell left()
	{
		return null;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.waterwalk;
	}
}
