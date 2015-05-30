package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public abstract class BaseSpellItem implements ISpell
{
	public abstract EnumSpellType getSpellType();
	 
	public abstract void cast(World world, EntityPlayer player, BlockPos pos);
	public abstract ItemStack getIconDisplay();
	public abstract Item getItemCost();

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);
 
		ItemStack is;
		for(int i = 0; i < player.getInventoryEnderChest().getSizeInventory(); i++)
		{
			is = player.getInventoryEnderChest().getStackInSlot(i);
			
			if(is != null && is.getItem() == this.getItemCost())
			{
				player.getInventoryEnderChest().decrStackSize(i, 1);
				
				break;
			}
		}
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.wood_click);
	}
 
	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		System.out.println("TODO: canPlayerCast you must extend for baseitem");
		// TODO Auto-generated method stub
		return false;
	} 
}
