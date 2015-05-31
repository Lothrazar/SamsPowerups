package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public abstract class BaseSpellItem implements ISpell
{
	public abstract ISpell next();
	public abstract ISpell prev();
	public abstract String getSpellID();
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
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.wood_click);
		String name = (new ItemStack(this.getItemCost())).getDisplayName();
		Util.addChatMessage(player, Util.lang("spell.item.missing")+name);
	}


	@Override
	public ItemStack getIconDisplayHeader()
	{
		return new ItemStack(ItemRegistry.spell_enderinv_dummy);
	}
}
