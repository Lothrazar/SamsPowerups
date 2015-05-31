package com.lothrazar.samscontent.spell;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellLightningbolt extends BaseSpellExp implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "lightningbolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
	}

	@Override
	public int getExpCost()
	{
		return 300;
	}
 
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_lightning_dummy);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.firebolt;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.soulstone;
	}
}
