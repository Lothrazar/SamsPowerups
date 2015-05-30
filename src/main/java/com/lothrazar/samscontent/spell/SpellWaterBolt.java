package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.entity.projectile.EntityWaterBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellWaterBolt extends BaseSpellExp implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.waterbolt;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
	 
		world.spawnEntityInWorld(new EntityWaterBolt(world,player));

	}

  

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public int getExpCost()
	{
		return 200;
	}
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_water_dummy);
	}

}
