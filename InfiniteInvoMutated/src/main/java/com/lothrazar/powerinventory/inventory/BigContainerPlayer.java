package com.lothrazar.powerinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.ModInv;
import com.lothrazar.powerinventory.ModSettings;
import com.lothrazar.powerinventory.inventory.client.GuiBigInventory;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigContainerPlayer extends ContainerPlayer
{	
	public final static int OFFSCREEN = -999;
	public final static int armorSize = 4; 
	public final static int hotbarSize = 9; 
	private final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
	public int scrollPos = 0;
	private BigInventoryPlayer invo;
    public boolean isLocalWorld;
    private final EntityPlayer thePlayer;
    private Slot[] slots = new Slot[ModSettings.invoSize];

	@SuppressWarnings("unchecked")
	public BigContainerPlayer(BigInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);

        boolean onHold = false;
        int[] holdSlot = new int[5];//because 3x3 - 2x2 = 5
        int[] holdX = new int[holdSlot.length];
        int[] holdY = new int[holdSlot.length];

        int i,j,cx,cy,craft=0,h = 0,slotNumber = 0, rows=3, cols=9;//rows and cols of vanilla, not extra

        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, slotNumber, 152, 42));
    
        
        for (i = 0; i < craftSize; ++i)
        {
        	onHold = false;
        	if( i == this.craftSize-1) onHold = true; //hold right and bottom column
        	
            for (j = 0; j < craftSize; ++j)
            {
            	if(j == this.craftSize-1)onHold = true; //hold right and bottom column
            	
            	slotNumber = j + i * this.craftSize;
            
            	cx = 81 + j * GuiBigInventory.square;
            	cy = 26 + i * GuiBigInventory.square;
            	if(onHold)
            	{
            		//save these to add at the end
            		holdSlot[h] = slotNumber;
            		holdX[h] = cx;
            		holdY[h] = cy;
            		h++;
            	}
            	else
            	{
        			cx = 81 + ((craft%2) * GuiBigInventory.square );
        			cy = 20 + ((craft/2) * GuiBigInventory.square );
      
            		this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy));
            		//System.out.println("crafting "+slotNumber);
            		craft++;
            	}
            }
        }

        for (i = 0; i < armorSize; ++i)
        {
        	cx = 8;
        	cy = 8 + i * GuiBigInventory.square;
            final int k = i;
            slotNumber =  playerInventory.getSizeInventory() - 1 - i           -1;
System.out.println("ARMOR = "+slotNumber);//384-387
            this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy)
            {
             //   private static final String __OBFID = "CL_00001755";
 
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

        for (i = 0; i < rows; ++i)      //inventory is 3 rows by 9 columns
        {
            for (j = 0; j < cols; ++j)
            {
            	slotNumber = j + (i + 1) * cols;
               // System.out.println("plain invo = "+slotNumber);
            	cx = 8 + j * GuiBigInventory.square;
            	cy = 84 + i * GuiBigInventory.square;
                this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy));
            }
        }

        for (i = 0; i < cols; ++i)//hotbar
        {
        	slotNumber = i;
        	cx = 8 + i * GuiBigInventory.square;
        	cy = 142 + (GuiBigInventory.square * ModSettings.MORE_ROWS);
           // System.out.println("hotbar = "+slotNumber);
        	//hotbar[i] = ;
            this.addSlotToContainer(new Slot(playerInventory, slotNumber, cx, cy));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
		this.invo = (BigInventoryPlayer)playerInventory;
		
		for(i = cols; i < 4*cols; i++)
		{
			// Add all the previous inventory slots to the organised array
			 Slot os = (Slot)this.inventorySlots.get(i);
			 
			 Slot ns = new Slot(os.inventory, os.getSlotIndex(), os.xDisplayPosition, os.yDisplayPosition);
			 ns.slotNumber = os.slotNumber;
			 this.inventorySlots.set(i, ns);
			 ns.onSlotChanged();
			 slots[i - cols] = ns;
		}
		
        int enderslot=-1;
//int enderslot = ModSettings.invoSize+hotbarSize+armorSize-1;
        for ( i = rows; i < MathHelper.ceiling_float_int((float)(ModSettings.invoSize/9F)); ++i)
        {
            for ( j = 0; j < cols; ++j)
            {
            	if(j + (i * cols) >= ModSettings.invoSize && ModSettings.invoSize > rows*cols)
            	{
            		break;
            	} 
            	else
            	{
            		// Moved off screen to avoid interaction until screen scrolls over the row
            		slotNumber =  j + (i + 1) * cols;


            		cx = OFFSCREEN;
            		cy = OFFSCREEN;
                  //  System.out.println("new slots = "+slotNumber);
            		Slot ns = new Slot(playerInventory,slotNumber, cx,cy);
            		slots[slotNumber - cols] = ns;
            		this.addSlotToContainer(ns);
                	//enderslot=slotNumber+1;
            	}
            }
        }
        
        for(h = 0; h < holdSlot.length; ++h)
        {
        	slotNumber = holdSlot[h];
    		cx = holdX[h];
    		cy = holdY[h] - 6;
    		//System.out.println("crafting "+slotNumber);
    		Slot ns = new Slot(this.craftMatrix, slotNumber, cx , cy );
        	this.addSlotToContainer(ns);
        }
      //  System.out.println("invoSize "+ModSettings.invoSize);//375
        enderslot = 388;//ModSettings.invoSize+hotbarSize -1;//SB 383 ,exactly right under armor
        System.out.println("enderslot "+enderslot);  System.out.println("enderslot "+enderslot);  System.out.println("enderslot "+enderslot);  System.out.println("enderslot "+enderslot); 
 
        this.addSlotToContainer(new SlotEnderPearl(playerInventory, ModSettings.invoSize+hotbarSize+armorSize, 182, 42));

       // this.inventorySlots.add(new SlotEnderPearl(playerInventory, ModSettings.invoSize+hotbarSize+armorSize, 182, 42));
       // this.inventoryItemStacks.add((Object)null);
        
        this.updateScroll();
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

        if(playerIn.capabilities.isCreativeMode == false) //i think we were dropping stuff from hotbar?
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

	public void updateScroll()
	{
		if(scrollPos > MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(ModSettings.ALL_COLS)) - (3 + ModSettings.MORE_ROWS))
		{
			scrollPos = MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(ModSettings.ALL_COLS)) - (3 + ModSettings.MORE_ROWS);
		}
		
		if(scrollPos < 0)
		{
			scrollPos = 0;
		}
		
		for(int i = 0; i < MathHelper.ceiling_float_int((float)ModSettings.invoSize/(float)(ModSettings.ALL_COLS)); i++)
		{
			
            for (int j = 0; j < ModSettings.ALL_COLS; ++j)
            {
            	int index = j + (i * ModSettings.ALL_COLS);
            	//System.out.println("updateScroll from "+scrollPos+"::"+index);
            	if(index >= ModSettings.invoSize && index >= 3*hotbarSize)
            	{
            		break;
            	} else
            	{
            		if(i >= scrollPos && i < scrollPos + 3 + ModSettings.MORE_ROWS && index < invo.getUnlockedSlots() - hotbarSize && index < ModSettings.invoSize)
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = 8 + j * GuiBigInventory.square;
            			s.yDisplayPosition = 84 + (i - scrollPos) * GuiBigInventory.square;
            		} else
            		{
            			Slot s = slots[index];
            			s.xDisplayPosition = OFFSCREEN;
            			s.yDisplayPosition = OFFSCREEN;
            		}
            	}
            }
		}
	}

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int craft)
    {
		int vLocked = invo.getUnlockedSlots() < 36? 36 - invo.getUnlockedSlots() : 0;
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(craft);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (craft == 0) // Crafting result
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (craft >= 1 && craft < 5) // Crafting grid
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (craft >= 5 && craft < 9) // Armor
            {
                if (!this.mergeItemStack(itemstack1, 9, 45, false))
                {
                    return null;
                }
            }
            else if (itemstack.getItem() instanceof ItemArmor && !((Slot)this.inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) // Inventory to armor
            {
                int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false))
                {
                    return null;
                }
            }
            else if ((craft >= 9 && craft < 36) || (craft >= 45 && craft < invo.getUnlockedSlots() + 9))
            {
                if (!this.mergeItemStack(itemstack1, 36, 45, false))
                {
                    return null;
                }
            }
            else if (craft >= 36 && craft < 45) // Hotbar
            {
                if (!this.mergeItemStack(itemstack1, 9, 36 - vLocked, false) && (invo.getUnlockedSlots() - 36 <= 0 || !this.mergeItemStack(itemstack1, 45, 45 + (invo.getUnlockedSlots() - 36), false)))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 9, invo.getUnlockedSlots() + 9, false)) // Full range
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, itemstack1);
        }

        return itemstack;
    }
}
