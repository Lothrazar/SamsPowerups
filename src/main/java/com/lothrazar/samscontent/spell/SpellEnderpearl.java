package com.lothrazar.samscontent.spell;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellEnderpearl extends BaseSpellItem implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.pearl;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
		 
	}
 
	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		ItemStack is;
		
		for(int i = 0; i < player.getInventoryEnderChest().getSizeInventory(); i++)
		{
			is = player.getInventoryEnderChest().getStackInSlot(i);
			if(is != null)
			{ 
				if(is.getItem() == this.getItemCost())
				{
					return true;
				}
			}
		}
		
		return false;
	} 
	
	@Override
	public Item getItemCost()
	{
		return Items.ender_pearl;
	}
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.ender_pearl);
	}
}
