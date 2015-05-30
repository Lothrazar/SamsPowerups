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

public class SpellEnderEye extends BaseSpell implements ISpell
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
	public int getExpCost()
	{
		return 500;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.ender_eye);
	}

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.bowtoss);

		super.onCastSuccess(world, player, pos);
	}
}
