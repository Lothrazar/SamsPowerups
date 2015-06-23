package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 

public class SpellEnderPearl extends BaseSpellExp implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "pearl";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
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
		return new ItemStack(Items.ender_pearl);
	}
	
	@Override
	public int getExpCost() 
	{
		return ModSpells.cfg.pearl;
	}
}
