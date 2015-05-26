package com.lothrazar.samscontent.spell;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Util;

public class SpellEnderpearl implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.chest;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
		 
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public void drainExpCost(EntityPlayer player)
	{ 
		 Util.drainExp(player, getExpCost());
	}

	private int cost = 10;
	@Override
	public void setExpCost(int c)
	{
		cost = c;
	}
	@Override
	public int getExpCost()
	{
		return cost;
	}

}
