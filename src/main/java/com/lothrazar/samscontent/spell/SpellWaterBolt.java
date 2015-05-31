package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.entity.projectile.EntityWaterBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellWaterBolt extends BaseSpellItem implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "waterbolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityWaterBolt(world,player)); 
	}
  
	@Override
	public Item getItemCost()
	{
		return Item.getItemFromBlock(Blocks.ice);
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_water_dummy);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.frostbolt;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.torch;
	}
}
