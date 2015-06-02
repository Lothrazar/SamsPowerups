package com.lothrazar.samsmagic.spell;

import com.lothrazar.samsmagic.SpellRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISpell
{
	public ISpell left();
	public ISpell right();
	
	public String getSpellID();
	
	public void cast(World world, EntityPlayer player, BlockPos pos);
	
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos);
	
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos);
	
	public ItemStack getIconDisplay();
	
	public ItemStack getIconDisplayHeader();
	
	public boolean canPlayerCast(EntityPlayer player);
}
