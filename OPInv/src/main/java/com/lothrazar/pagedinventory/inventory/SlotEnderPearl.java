package com.lothrazar.pagedinventory.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEnderPearl extends Slot
{
	public int slotIndex;//overrides the private internal one
	
	public SlotEnderPearl(IInventory inventoryIn, int index, int xPosition,int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
 
		slotIndex = index;
	}
	
	@Override
	public int getSlotIndex()
    {
        return slotIndex;
    }
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack != null && stack.getItem() == Items.ender_pearl);
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public int getSlotStackLimit()
    {
        return Items.ender_pearl.getItemStackLimit();
    }
}
