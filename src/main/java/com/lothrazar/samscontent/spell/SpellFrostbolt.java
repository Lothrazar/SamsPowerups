package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellFrostbolt implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.chest;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		// TODO Auto-generated method stub
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

		world.spawnEntityInWorld(new EntitySnowballBolt(world,player));

		this.onCastSuccess(world, player, pos);
		/*BlockPos up = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), 1).up();
 
		EntitySnowballBolt snow = new EntitySnowballBolt(world,entityPlayer 	 );
		 
		 world.spawnEntityInWorld(snow);
	 
		Util.playSoundAt(entityPlayer, Reference.sounds.bowtoss); 
		
		Util.decrHeldStackSize(entityPlayer); */
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		 
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
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{

		Util.playSoundAt(player, Reference.sounds.bowtoss);
		
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

}
