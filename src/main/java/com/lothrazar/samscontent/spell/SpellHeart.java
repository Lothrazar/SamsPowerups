package com.lothrazar.samscontent.spell;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellHeart extends BaseSpell implements ISpell
{

	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.heart;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
	 
		player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth()+2);
 
	}

	@Override
	public int getExpCost()
	{
		return 1000;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_heart_dummy);
	}

}
