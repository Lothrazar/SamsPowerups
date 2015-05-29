package com.lothrazar.samscontent.spell;

import com.lothrazar.samscontent.SpellRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISpell
{
	public SpellRegistry.EnumSpellType getSpellType();
	
	public void cast(World world, EntityPlayer player, BlockPos pos);
	
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos);
	
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos);
	
	public ItemStack getIconDisplay();
	
	public boolean canPlayerCast(EntityPlayer player);

	public int getExpCost();
}
