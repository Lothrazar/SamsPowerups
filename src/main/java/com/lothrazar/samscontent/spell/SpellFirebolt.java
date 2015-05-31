package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellFirebolt extends BaseSpellItem implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "firebolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		//so you don't burn yourself
		BlockPos infront = player.getPosition().offset(player.getHorizontalFacing(), 2).up();
 
		EntitySmallFireball f = new EntitySmallFireball(world,player
				 ,player.getLookVec().xCoord
				 ,player.getLookVec().yCoord// + 1
				 ,player.getLookVec().zCoord);
        f.setLocationAndAngles(infront.getX(), infront.getY(), infront.getZ(), player.rotationYaw, player.rotationPitch);
		
        world.spawnEntityInWorld(f);
	}

	@Override
	public Item getItemCost()
	{
		return Items.fire_charge;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.fire_charge);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.lightningbolt;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.frostbolt;
	}
}
