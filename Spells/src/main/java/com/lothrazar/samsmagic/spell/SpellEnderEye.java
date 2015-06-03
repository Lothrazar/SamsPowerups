package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 

public class SpellEnderEye extends BaseSpellItem implements ISpell
{
	@Override
	public String getSpellID()
	{
		return "endereye";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{  
		world.spawnEntityInWorld(new EntityEnderEye(world,pos.getX(),pos.getY(),pos.getZ())); 
	}
  
	@Override
	public Item getItemCost()
	{
		return Items.ender_eye;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.ender_eye);
	}

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.pearl;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.ghost;
	}
}
