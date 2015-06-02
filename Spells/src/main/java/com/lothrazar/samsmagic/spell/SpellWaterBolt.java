package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.entity.projectile.EntityWaterBolt; 

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
		return SpellRegistry.torch;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.frostbolt;
	}
}
