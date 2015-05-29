package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellFirebolt extends BaseSpell implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.firebolt;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{

		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();
 
		world.spawnEntityInWorld(new EntitySmallFireball(world,player
		 ,player.getLookVec().xCoord
		 ,player.getLookVec().yCoord// + 1
		 ,player.getLookVec().zCoord));

 
	}
 
 

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.fire_charge);
	}

}
