package com.lothrazar.samscontent.spell;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellLightningbolt extends BaseSpell implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.lightningbolt;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
	 
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public int getExpCost()
	{
		return 300;
	}
 

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_lightning_dummy);
	}

}
