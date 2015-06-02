package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.entity.projectile.EntityTorchBolt; 

public class SpellTorchBolt extends BaseSpellItem implements ISpell
{
	@Override
	public String getSpellID()
	{
		return "torch";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityTorchBolt(world,player));
	}

	@Override
	public Item getItemCost()
	{
		return Item.getItemFromBlock(Blocks.torch);
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_torch_dummy);
	}
	//
	@Override
	public ISpell left()
	{
		return null;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.waterbolt;
	}
}
