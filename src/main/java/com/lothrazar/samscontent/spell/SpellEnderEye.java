package com.lothrazar.samscontent.spell;

import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellEnderEye implements ISpell
{

	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.endereye;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{  
		world.spawnEntityInWorld(new EntityEnderEye(world,pos.getX(),pos.getY(),pos.getZ()	 )); 
		   
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
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
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{

		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);
		
		Util.playSoundAt(player, Reference.sounds.bowtoss);

		Util.drainExp(player, getExpCost());
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.ender_eye);
	}

}
