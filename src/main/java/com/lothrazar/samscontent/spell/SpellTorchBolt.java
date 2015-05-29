package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntityTorchBolt;
import com.lothrazar.samscontent.entity.projectile.EntityWaterBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellTorchBolt extends BaseSpell  implements ISpell
{

	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.torch;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityTorchBolt(world,player));
	}
 

	@Override
	public int getExpCost()
	{
		return 100;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.tnt_minecart);//todo:torch
	}

}
