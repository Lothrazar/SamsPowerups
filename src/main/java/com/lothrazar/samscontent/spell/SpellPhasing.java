package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.item.ItemWallCompass;
import com.lothrazar.util.Util;

public class SpellPhasing implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.chest;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		EnumFacing facing = EnumFacing.getFacingFromVector(
				(float)player.getLookVec().xCoord
				, (float)player.getLookVec().yCoord
				, (float)player.getLookVec().zCoord);

		System.out.println("TODO: bugfix phase  "+facing.getName()+"  "+Util.posToString(pos));
		
		//.getHorizontal(MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);

		ItemWallCompass.wallPhase(player.worldObj,player,pos,facing);
 
		
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
