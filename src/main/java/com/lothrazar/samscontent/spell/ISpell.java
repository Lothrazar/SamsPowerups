package com.lothrazar.samscontent.spell;

import com.lothrazar.samscontent.SpellRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISpell
{
	public SpellRegistry.EnumSpellType getSpellType();
	
	public void cast(World world, EntityPlayer player, BlockPos pos);
	
	public boolean canPlayerCast(EntityPlayer player);

	public void setExpCost(int cost);
	public int getExpCost();
	
	public void drainExpCost(EntityPlayer player);
}
