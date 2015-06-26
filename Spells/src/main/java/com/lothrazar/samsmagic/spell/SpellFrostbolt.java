package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 
/*
public class SpellFrostbolt extends BaseSpellExp implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "frostbolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntitySnowballBolt(world,player));
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_frostbolt_dummy);
	}
	
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}

	@Override
	public int getExpCost() 
	{
		return ModSpells.cfg.frostbolt;
	}
}*/
