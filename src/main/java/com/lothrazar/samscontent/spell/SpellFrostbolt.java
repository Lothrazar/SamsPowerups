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

public class SpellFrostbolt extends BaseSpell implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.frostbolt;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

		world.spawnEntityInWorld(new EntitySnowballBolt(world,player));

	
	}

	@Override
	public int getExpCost()
	{
		return 5;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_frostbolt_dummy);
	}
	
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.bowtoss);

		super.onCastSuccess(world, player, pos);
	}

}
