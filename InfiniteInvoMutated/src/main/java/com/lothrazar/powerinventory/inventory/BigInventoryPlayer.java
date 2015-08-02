package com.lothrazar.powerinventory.inventory;

import com.lothrazar.powerinventory.Const; 

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class BigInventoryPlayer extends InventoryPlayer
{
    @SideOnly(Side.CLIENT)
    private ItemStack currentItemStack;
 
    public ItemStack[] bonusInventory;// = new ItemStack[Const.BONUS_SIZE];
   
	public BigInventoryPlayer(EntityPlayer player)
	{
		super(player);
		this.mainInventory = new ItemStack[Const.ALL_ROWS * Const.ALL_COLS + Const.HOTBAR_SIZE];
		bonusInventory = new ItemStack[Const.BONUS_SIZE];
 
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
    {
   
        if (index >= this.mainInventory.length && index < this.mainInventory.length + Const.ARMOR_SIZE)//armor slots depend on being right at the end like 384
        {
            index -= this.mainInventory.length;
            //now index is in [0,3]
            //??
//armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots

        //    aitemstack = this.armorInventory;
            return this.armorInventory[index];
        }
        else if (index >= this.mainInventory.length + Const.ARMOR_SIZE && index < Const.BONUS_START + Const.BONUS_SIZE)//armor slots depend on being right at the end like 384
        {
        	//a reminder that BONUS_START = ALL_ROWS * ALL_COLS + Const.ARMOR_SIZE; ==aitemstack.length + Const.ARMOR_SIZE 
        	
        	int type = index - Const.BONUS_START;
        	if(type >= 0 && type < bonusInventory.length){
	        	//System.out.println("index "+index+" tried for type "+type);
	        	return bonusInventory[type];
	        }
        	/*
        	if(index == Const.enderPearlSlot){return enderPearlStack;}//bonus + 0, and so on
            else if(index == Const.enderChestSlot){return enderChestStack;} 
            else if(index == Const.clockSlot){return clockStack;}
            else if(index == Const.compassSlot){return compassStack;} 
            else if(index == Const.bottleSlot){return bottleStack;}  
        	*/
        	/* 388 tried for type 0
[12:03:37] [Client thread/INFO] [STDOUT]: [com.lothrazar.powerinventory.inventory.BigInventoryPlayer:getStackInSlot:92]: index 389 tried for type 1
[12:03:37] [Client thread/INFO] [STDOUT]: [com.lothrazar.powerinventory.inventory.BigInventoryPlayer:getStackInSlot:92]: index 390 tried for type 2
[12:03:37] [Client thread/INFO] [STDOUT]: [com.lothrazar.powerinventory.inventory.BigInventoryPlayer:getStackInSlot:92]: index 391 tried for type 3
[12:03:37] [Client thread/INFO] [STDOUT]: [com.lothrazar.powerinventory.inventory.BigInventoryPlayer:getStackInSlot:92]: index 392 tried for type 4*/
        	///int type = index - Const.BONUS_START;
        	//return bonusInventory[Const.BONUS_START + type];
        	
        	//TODO: 
        	/*
    public static final int type_epearl = 0;
    public static final int type_echest = 1;
    public static final int type_clock=2;
    public static final int type_compass=3;
    public static final int type_bottle=4;
    public static final int enderPearlSlot = BONUS_START+type_epearl; 
    public static final int enderChestSlot = BONUS_START+type_echest;
    public static final int clockSlot = BONUS_START+type_clock;
    public static final int compassSlot = BONUS_START+type_compass;
    public static final int bottleSlot = BONUS_START+type_bottle;*/
        	/*
		bonusInventory[Const.type_epearl] = enderPearlStack;
		bonusInventory[Const.type_echest] = enderChestStack;
		bonusInventory[Const.type_clock] = clockStack;
		bonusInventory[Const.type_compass] = compassStack;
		bonusInventory[Const.type_bottle] = bottleStack;*/
          //  index -= aitemstack.length;
            //now index is in [0,3]
            //??
//armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots

        //    aitemstack = this.armorInventory;
            //return this.armorInventory[index];
        }
        return this.mainInventory[index];
    }
	
	@Override
	public void dropAllItems()
	{
		super.dropAllItems();
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
    {
		if(this.player.capabilities.isCreativeMode && this.player.worldObj.isRemote)
		{
            Minecraft.getMinecraft().playerController.sendSlotPacket(stack, slot);
		}
		if(slot == Const.BONUS_START+Const.type_epearl)
		{
			bonusInventory[Const.type_epearl] = stack;  
		}
		else if(slot == Const.BONUS_START+Const.type_echest)
		{
			bonusInventory[Const.type_echest] = stack;  
		}
		else if(slot == Const.BONUS_START+Const.type_clock)
		{
			bonusInventory[Const.type_clock] = stack;  
		}
		else if(slot == Const.BONUS_START+Const.type_compass)
		{
			bonusInventory[Const.type_compass] = stack;  
		}
		else if(slot == Const.BONUS_START+Const.type_bottle)
		{
			bonusInventory[Const.type_bottle] = stack;  
		}
		else
		{
			super.setInventorySlotContents(slot, stack);
		}
    }
	 
    private int func_146029_c(Item stack)
    {
        for (int i = 0; i < this.getSizeInventory() - Const.ARMOR_SIZE; ++i)
        {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == stack)
            {
                return i;
            }
        }

        return -1;
    }

    private int storeItemStack(ItemStack stack)
    {
        for (int i = 0; i < this.getSizeInventory() - Const.ARMOR_SIZE; ++i)
        {
            if (this.mainInventory[i] != null && this.mainInventory[i].getItem() == stack.getItem() && this.mainInventory[i].isStackable() && this.mainInventory[i].stackSize < this.mainInventory[i].getMaxStackSize() && this.mainInventory[i].stackSize < this.getInventoryStackLimit() && (!this.mainInventory[i].getHasSubtypes() || this.mainInventory[i].getItemDamage() == stack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.mainInventory[i], stack))
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getFirstEmptyStack()
    {
        for (int i = 0; i < this.getSizeInventory() - Const.ARMOR_SIZE; ++i)
        {
            if (this.mainInventory[i] == null)
            {
                return i;
            }
        }

        return -1;
    }

    @SideOnly(Side.CLIENT)
    private int func_146024_c(Item item, int meta)//getSlotNumberForItem
    {
        for (int j = 0; j < this.getSizeInventory() - Const.ARMOR_SIZE; ++j)
        {
            if (this.mainInventory[j] != null && this.mainInventory[j].getItem() == item && this.mainInventory[j].getItemDamage() == meta)
            {
                return j;
            }
        }

        return -1;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void setCurrentItem(Item item, int meta, boolean var3flag, boolean var4flag)
    {
        this.currentItemStack = this.getCurrentItem();
        int k;

        if (var3flag)
        {
            k = this.func_146024_c(item, meta);
        }
        else
        {
            k = this.func_146029_c(item);
        }

        if (k >= 0 && k < 9)
        {
            this.currentItem = k;
        }
        else
        {
            if (var4flag && item != null)
            {
                int j = this.getFirstEmptyStack();

                if (j >= 0 && j < 9)
                {
                    this.currentItem = j;
                }

                this.func_70439_a(item, meta);
            }
        }
    }

    @SideOnly(Side.CLIENT)  // @Override
    public void func_70439_a(Item item, int meta)//set item in slot
    {
    
        if (item != null)
        { 
            if (this.currentItemStack != null && this.currentItemStack.isItemEnchantable() && this.func_146024_c(this.currentItemStack.getItem(), this.currentItemStack.getItemDamage()) == this.currentItem)
            {
                return;
            }

            int j = this.func_146024_c(item, meta);

            if (j >= 0)
            {
                int k = this.mainInventory[j].stackSize;
                this.mainInventory[j] = this.mainInventory[this.currentItem];
                this.mainInventory[this.currentItem] = new ItemStack(item, k, meta);
            }
            else
            {
                this.mainInventory[this.currentItem] = new ItemStack(item, 1, meta);
            }
        }
    }

    /**
     * This function stores as many items of an ItemStack as possible in a matching slot and returns the quantity of
     * left over items.
     */
    private int storePartialItemStack(ItemStack stack)
    {
        Item item = stack.getItem();
        int i = stack.stackSize;
        int j;

        if (stack.getMaxStackSize() == 1)
        {
            j = this.getFirstEmptyStack();

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (this.mainInventory[j] == null)
                {
                    this.mainInventory[j] = ItemStack.copyItemStack(stack);
                }

                return 0;
            }
        }
        else
        {
            j = this.storeItemStack(stack);

            if (j < 0)
            {
                j = this.getFirstEmptyStack();
            }

            if (j < 0)
            {
                return i;
            }
            else
            {
                if (this.mainInventory[j] == null)
                {
                    this.mainInventory[j] = new ItemStack(item, 0, stack.getItemDamage());

                    if (stack.hasTagCompound())
                    {
                        this.mainInventory[j].setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
                    }
                }

                int k = i;

                if (i > this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize)
                {
                    k = this.mainInventory[j].getMaxStackSize() - this.mainInventory[j].stackSize;
                }

                if (k > this.getInventoryStackLimit() - this.mainInventory[j].stackSize)
                {
                    k = this.getInventoryStackLimit() - this.mainInventory[j].stackSize;
                }

                if (k == 0)
                {
                    return i;
                }
                else
                {
                    i -= k;
                    this.mainInventory[j].stackSize += k;
                    this.mainInventory[j].animationsToGo = 5;
                    return i;
                }
            }
        }
    }

    /**
     * Adds the item stack to the inventory, returns false if it is impossible.
     */
    @SuppressWarnings("rawtypes")
	@Override
    public boolean addItemStackToInventory(final ItemStack stack)
    {
        if (stack != null && stack.stackSize != 0 && stack.getItem() != null)
        {
            try
            {
                int i;

                if (stack.isItemDamaged())
                {
                    i = this.getFirstEmptyStack();

                    if (i >= 0)
                    {
                        this.mainInventory[i] = ItemStack.copyItemStack(stack);
                        this.mainInventory[i].animationsToGo = 5;
                        stack.stackSize = 0;
                        return true;
                    }
                    else if (this.player.capabilities.isCreativeMode)
                    {
                        stack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = stack.stackSize;
                        stack.stackSize = this.storePartialItemStack(stack);
                    }
                    while (stack.stackSize > 0 && stack.stackSize < i);

                    if (stack.stackSize == i && this.player.capabilities.isCreativeMode)
                    {
                        stack.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return stack.stackSize < i;
                    }
                }
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
                crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(stack.getItem())));
                crashreportcategory.addCrashSection("Item data", Integer.valueOf(stack.getItemDamage()));
                crashreportcategory.addCrashSectionCallable("Item name", new Callable()
                {
                    public String call()
                    {
                        return stack.getDisplayName();
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Modified from the original to allow for more than 255 inventory slots
     */
    public NBTTagList writeToNBT(NBTTagList tags)
    {
        int i;
        NBTTagCompound nbttagcompound;

        for (i = 0; i < this.mainInventory.length; ++i)
        {
            if (this.mainInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger(Const.NBT_SLOT, i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                tags.appendTag(nbttagcompound);
            }
        }
/*
        if(this.enderChestStack != null)
        {
        	nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(Const.NBT_SLOT, Const.enderChestSlot);  
            this.enderChestStack.writeToNBT(nbttagcompound);
            tags.appendTag(nbttagcompound);
        }
        if(this.enderPearlStack != null)
        {
        	nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(Const.NBT_SLOT, Const.enderPearlSlot);  
            this.enderPearlStack.writeToNBT(nbttagcompound);
            tags.appendTag(nbttagcompound);
        }
        if(this.clockStack != null)
        {
        	nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(Const.NBT_SLOT, Const.clockSlot);  
            this.clockStack.writeToNBT(nbttagcompound);
            tags.appendTag(nbttagcompound);
        }
        if(this.compassStack != null)
        {
        	nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(Const.NBT_SLOT, Const.compassSlot);  
            this.compassStack.writeToNBT(nbttagcompound);
            tags.appendTag(nbttagcompound);
        }
        if(this.bottleStack != null)
        {
        	nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(Const.NBT_SLOT, Const.bottleSlot);  
            this.bottleStack.writeToNBT(nbttagcompound);
            tags.appendTag(nbttagcompound);
        }
*/
        for (i = 0; i < this.bonusInventory.length; ++i)
        {
            if (this.bonusInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger(Const.NBT_SLOT, i + Const.BONUS_START); // Give armor slots the last 100 integer spaces
                this.bonusInventory[i].writeToNBT(nbttagcompound);
                tags.appendTag(nbttagcompound);
                
            }
        }
        for (i = 0; i < this.armorInventory.length; ++i)
        {
            if (this.armorInventory[i] != null)
            {
                nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger(Const.NBT_SLOT, i + (Integer.MAX_VALUE - 100)); // Give armor slots the last 100 integer spaces
                this.armorInventory[i].writeToNBT(nbttagcompound);
                tags.appendTag(nbttagcompound);
                
            }
        }
        
        return tags;
    }
    
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack itemstack;
//TODO: these ifelse brnaches are almost all identical. find a way to share code? make function?
    	if(index == Const.BONUS_START+Const.type_echest)
    	{ 
            itemstack = this.bonusInventory[Const.type_echest];
            this.bonusInventory[Const.type_echest] = null;
            return itemstack;
    	}    	
    	else if(index == Const.BONUS_START+Const.type_epearl)
    	{
    		 if (this.bonusInventory[Const.type_epearl].stackSize <= count)
             {
                 itemstack = this.bonusInventory[Const.type_epearl];
                 this.bonusInventory[Const.type_epearl] = null;
                 return itemstack;
             }
    		 else
             {
                 itemstack = this.bonusInventory[Const.type_epearl].splitStack(count);

                 if (this.bonusInventory[Const.type_epearl].stackSize == 0)
                 {
                	 this.bonusInventory[Const.type_epearl] = null;
                 }

                 return itemstack;
             }
    	}	
    	else if(index == Const.BONUS_START+Const.type_clock)
    	{
    		 if (this.bonusInventory[Const.type_clock].stackSize <= count)
             {
                 itemstack = this.bonusInventory[Const.type_clock];
                 this.bonusInventory[Const.type_clock] = null;
                 return itemstack;
             }
    		 else
             {
                 itemstack = this.bonusInventory[Const.type_clock].splitStack(count);

                 if (this.bonusInventory[Const.type_clock].stackSize == 0)
                 {
                	 this.bonusInventory[Const.type_clock] = null;
                 }

                 return itemstack;
             }
    	}
    	else if(index == Const.BONUS_START+Const.type_compass)
    	{
    		 if (this.bonusInventory[Const.type_compass].stackSize <= count)
             {
                 itemstack = this.bonusInventory[Const.type_compass];
                 this.bonusInventory[Const.type_compass] = null;
                 return itemstack;
             }
    		 else
             {
                 itemstack = this.bonusInventory[Const.type_compass].splitStack(count);

                 if (this.bonusInventory[Const.type_compass].stackSize == 0)
                 {
                	 this.bonusInventory[Const.type_compass] = null;
                 }

                 return itemstack;
             }
    	}
    	else if(index == Const.BONUS_START+Const.type_bottle)
    	{
    		 if (this.bonusInventory[Const.type_bottle].stackSize <= count)
             {
                 itemstack = this.bonusInventory[Const.type_bottle];
                 this.bonusInventory[Const.type_bottle] = null;
                 return itemstack;
             }
    		 else
             {
                 itemstack = this.bonusInventory[Const.type_bottle].splitStack(count);

                 if (this.bonusInventory[Const.type_bottle].stackSize == 0)
                 {
                	 this.bonusInventory[Const.type_bottle] = null;
                 }

                 return itemstack;
             }
    	}
    	else 
    		return super.decrStackSize(index, count);
    }

    /**
     * Modified from the original to allow for more than 255 inventory slots
     */
	@Override
    public void readFromNBT(NBTTagList tags)
    {
        this.mainInventory = new ItemStack[Const.ALL_ROWS * Const.ALL_COLS + Const.HOTBAR_SIZE];
        this.armorInventory = new ItemStack[armorInventory == null? Const.ARMOR_SIZE : armorInventory.length]; // Just in case it isn't standard size
        
        for (int i = 0; i < tags.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = tags.getCompoundTagAt(i);
            int j = nbttagcompound.getInteger(Const.NBT_SLOT);
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack != null)
            {
            	if(j == Const.BONUS_START+Const.type_epearl)
            	{
            		bonusInventory[Const.type_epearl] = itemstack;
            	}
            	if(j == Const.BONUS_START+Const.type_echest)
                {
            		bonusInventory[Const.type_echest] = itemstack;
                }
            	if(j == Const.BONUS_START+Const.type_clock)
                {
            		bonusInventory[Const.type_clock] = itemstack;
                }
            	if(j == Const.BONUS_START+Const.type_compass)
                {
            		bonusInventory[Const.type_compass] = itemstack;
                }
            	if(j == Const.BONUS_START+Const.type_bottle)
                {
            		bonusInventory[Const.type_bottle] = itemstack;
                }
                if (j >= 0 && j < this.mainInventory.length)
                {
            		this.mainInventory[j] = itemstack;
                }
                if (j >= (Integer.MAX_VALUE - 100) && j <= Integer.MAX_VALUE && j - (Integer.MAX_VALUE - 100) < this.armorInventory.length)
                {
            		this.armorInventory[j - (Integer.MAX_VALUE - 100)] = itemstack;
                }
            }
        }
    }
}
