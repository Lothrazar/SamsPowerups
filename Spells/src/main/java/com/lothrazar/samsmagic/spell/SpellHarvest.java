package com.lothrazar.samsmagic.spell;

import com.lothrazar.samsmagic.spell.ISpell;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
/*
public class SpellHarvest extends BaseSpellExp implements ISpell
{
	@Override
	public String getSpellID()
	{ 
		return "harvest";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityHarvestbolt(world,player)); 
	}

	
 
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}
	
	@Override
	public ItemStack getIconDisplay()
	{ 
		return new ItemStack(ItemRegistry.spell_harvest_dummy);
	}

	@Override
	public int getExpCost()
	{
		return ModSpells.cfg.harvest;
	}
}
*/