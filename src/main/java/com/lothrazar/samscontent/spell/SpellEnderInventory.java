package com.lothrazar.samscontent.spell;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellEnderInventory extends BaseSpell implements ISpell
{
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.enderinv;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		player.displayGUIChest(player.getInventoryEnderChest()); 
		
	}

	@Override
	public int getExpCost()
	{
		return 1;
	}
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_enderinv_dummy);
	}


	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.chestopen);

		super.onCastSuccess(world, player, pos);
	}
}
