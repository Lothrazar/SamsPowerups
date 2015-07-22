package com.lothrazar.powerinventory.inventory;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModInv;
import com.lothrazar.powerinventory.inventory.client.GuiBigInventory;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigContainerPlayer extends ContainerPlayer
{	
	private final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
    private final EntityPlayer thePlayer;
 
	public BigInventoryPlayer invo;
    public boolean isLocalWorld;

	//these get used here for actual slot, and in GUI for texture
	public final int pearlX = GuiBigInventory.texture_width - Const.square-6; 
	public final int pearlY = GuiBigInventory.texture_height - Const.square-6; 
	public final int echestX = pearlX - 2*Const.square;
	public final int echestY = pearlY;

	public BigContainerPlayer(BigInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
		
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
 
        int i,j,cx,cy,slotIndex = 0;//rows and cols of vanilla, not extra

        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, slotIndex, 
        		174, 
        		40));
    
        for (i = 0; i < craftSize; ++i)
        { 
            for (j = 0; j < craftSize; ++j)
            { 
            	slotIndex = j + i * this.craftSize;
       
    			cx = 88 + j * Const.square ;
    			cy = 20 + i * Const.square ;

        		this.addSlotToContainer(new Slot(this.craftMatrix, slotIndex, cx , cy)); 
            }
        }

        for (i = 0; i < Const.armorSize; ++i)
        {
        	cx = 8;
        	cy = 8 + i * Const.square;
            final int k = i;
            slotIndex =  playerInventory.getSizeInventory() - 1 - i;
 
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy)
            { 
            	public int getSlotStackLimit()
	            {
	                return 1;
	            }
	           
	            public boolean isItemValid(ItemStack stack)
	            {
	                if (stack == null) return false;
	                return stack.getItem().isValidArmor(stack, k, thePlayer);
	            }
	            @SideOnly(Side.CLIENT)
	            public String getSlotTexture()
	            {
	                return ItemArmor.EMPTY_SLOT_NAMES[k];
	            }
            }); 
        }

        for (i = 0; i < Const.hotbarSize; ++i)
        {
        	slotIndex = i;
        	cx = 8 + i * Const.square;
        	cy = 142 + (Const.square * Const.MORE_ROWS);
 
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
        }
        int sn = Const.hotbarSize;
        for( i = 0; i < MathHelper.ceiling_float_int((float)Const.invoSize/(float)(Const.ALL_COLS)); i++)
		{
            for ( j = 0; j < Const.ALL_COLS; ++j)
            {
            	slotIndex = sn;//j + (i + 1) * cols;
            	sn++;
            	cx = 8 + j * Const.square;
            	cy = 84 + i * Const.square;
                this.addSlotToContainer(new Slot(playerInventory, slotIndex, cx, cy));
            }
        }

        this.addSlotToContainer(new SlotEnderPearl(playerInventory, Const.enderPearlSlot, pearlX, pearlY));
        this.addSlotToContainer(new SlotEnderChest(playerInventory, Const.enderChestSlot, echestX, echestY)); 
        
        this.onCraftMatrixChanged(this.craftMatrix);
		this.invo = (BigInventoryPlayer)playerInventory;
	}
  
	@Override
	public Slot getSlotFromInventory(IInventory invo, int id)
	{
		Slot slot = super.getSlotFromInventory(invo, id);
		if(slot == null)
		{
			Exception e = new NullPointerException();
			 
			ModInv.logger.log(Level.FATAL, e.getStackTrace()[1].getClassName() + "." + e.getStackTrace()[1].getMethodName() + ":" + e.getStackTrace()[1].getLineNumber() + " is requesting slot " + id + " from inventory " + invo.getName() + " (" + invo.getClass().getName() + ") and got NULL!", e);
		}
		return slot;
	}
	
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

       // if(playerIn.capabilities.isCreativeMode == false) //i think we were dropping stuff from hotbar?
        for (int i = 0; i < craftSize*craftSize; ++i) // was 4
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }
 
    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int craft)
    {  
        ItemStack stackCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(craft); //333-319
        int playerSlot = craft - 14;//why is this the magic number? iunno 9+4+1?
    	System.out.println("ts "+craft+"  -> "  +playerSlot);

        if (slot != null && slot.getHasStack())
        {
            ItemStack stackOrig = slot.getStack();
            stackCopy = stackOrig.copy();

            if (craft == 0) // Crafting result - verified
            {
            	System.out.println("craft result");
                if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, true))
                {
                    return null;
                }

                slot.onSlotChange(stackOrig, stackCopy);
            }
            else if (craft >= 1 && craft <= 9) // Crafting grid - verified
            {
            	System.out.println("cGRID");
                if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false))
                {
                    return null;
                }
            }
            else if (craft >= 10 && craft <= 13) // from Armor - verified BUT i dont want it going to hotbar probably?
            {
            	System.out.println("from ARMOR");
                if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false))
                {
                    return null;
                }
            }
            else if (stackCopy.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)stackCopy.getItem()).armorType)).getHasStack()) // Inventory to armor
            {
            	System.out.println("intoarmor");
            	if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false))
                {
                    return null;
                } 
            }
            else if (stackCopy.getItem() == Items.ender_pearl)  
            {
            	System.out.println("TODO shift PEARL"+Const.enderPearlSlot);
            	
            	if(!simpleMerge(p, playerSlot, Const.enderPearlSlot,stackOrig))
            	{
            		//tried to put in my static slot, if it doesnt work then move on to as normal stuff
            		if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false)) 
                    {
                        return null;
                    } 
            	}
            	
            }
            else if (stackCopy.getItem() == Item.getItemFromBlock(Blocks.ender_chest))  
            {
            	System.out.println("TODO shift chest"+Const.enderChestSlot);
 
            }
            else if ((craft >= 9 && craft < 36) || (craft >= 45 && craft < invo.getSlotsNotArmor() + 9))
            {
            	System.out.println("shift PLAIN INvo");
                if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false))//was 36,45
                {
                    return null;
                }
            }
            else if (craft >= 36 && craft < 45) // Hotbar
            {
            	System.out.println("shift hotbar");
                if (!this.mergeItemStack(stackOrig, 10, Const.invoSize-10, false) && (invo.getSlotsNotArmor() - 36 <= 0 || !this.mergeItemStack(stackOrig, 45, 45 + (invo.getSlotsNotArmor() - 36), false)))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stackOrig, 10, invo.getSlotsNotArmor() + 9, false)) // Full range
            {
            	System.out.println("shift full range");
                return null;
            }

            if (stackOrig.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stackOrig.stackSize == stackCopy.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, stackOrig);
        }

        return stackCopy;
    }

	private boolean simpleMerge(EntityPlayer p, int slotFrom, int slotTo, ItemStack stackOrig) 
	{
		ItemStack pearls = p.inventory.getStackInSlot(slotTo);
		
		if(pearls == null)
		{
			p.inventory.setInventorySlotContents(slotTo, stackOrig);
			p.inventory.setInventorySlotContents(slotFrom, null);
			return true;
		}
		else
		{
			int room = Items.ender_pearl.getItemStackLimit() - pearls.stackSize;
			if(room > 0)
			{ 
				int toDeposit = Math.min(pearls.stackSize,room);
			
				pearls.stackSize += toDeposit;
				p.inventory.setInventorySlotContents(slotTo, pearls);

				stackOrig.stackSize -= toDeposit;
 
				if(stackOrig.stackSize <= 0)//because of calculations above, should not be below zero
				{
					//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
					p.inventory.setInventorySlotContents(slotFrom,null);  
				}
				else
				{ 
					p.inventory.setInventorySlotContents(slotFrom, stackOrig); 
				} 
				return true;
			}
			else return false;
		}
	}
}
