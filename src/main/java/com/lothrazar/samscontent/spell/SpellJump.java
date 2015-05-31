package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellJump extends BaseSpellExp implements ISpell
{ 
	private static int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?

	@Override
	public String getSpellID()
	{
		return "jump";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		Util.addOrMergePotionEffect(player,new PotionEffect(Potion.jump.id,fiveSeconds,4));
	}

	@Override
	public int getExpCost()
	{
		return 30;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_jump_dummy);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.haste;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.ghost;
	}
}
