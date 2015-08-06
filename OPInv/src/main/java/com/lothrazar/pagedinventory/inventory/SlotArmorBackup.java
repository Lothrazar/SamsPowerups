package com.lothrazar.pagedinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SlotArmorBackup extends Slot
{
	public int slotIndex;//overrides the private internal one
	EntityPlayer thePlayer;
	int atype;
	public SlotArmorBackup(EntityPlayer player,int type,IInventory inventoryIn, int index, int xPosition,int yPosition) 
	{
		super(inventoryIn, index, xPosition, yPosition);
 thePlayer = player;
 atype=type;
		slotIndex = index;
	}
	public int getSlotStackLimit()
    {
        return 1;
    }
    public boolean isItemValid(ItemStack stack)
    {
        if (stack == null) return false;
        return stack.getItem().isValidArmor(stack, atype, thePlayer);
    }
    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return ItemArmor.EMPTY_SLOT_NAMES[atype];
    }
	/*
	@Override
	public int getSlotIndex()
    {
        return slotIndex;
    }
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
		return (stack != null && 
				(stack.getItem() == Items.glass_bottle || 
				 stack.getItem() == Items.experience_bottle ));
    }*/
}
