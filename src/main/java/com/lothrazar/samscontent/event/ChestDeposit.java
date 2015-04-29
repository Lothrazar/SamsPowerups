package com.lothrazar.samscontent.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestDeposit
{    
 
  	public static void sortFromPlayerToChestEntity(World world, TileEntityChest chest, EntityPlayer entityPlayer)
  	{ 
  		int totalItemsMoved = 0; 
  		int totalSlotsFreed = 0;
  		 
		ItemStack chestItem;
		ItemStack invItem;
		int room;
		int toDeposit;
		int chestMax;
		
		//player inventory and the small chest have the same dimensions 
		
		int START_CHEST = 0; 
		int END_CHEST =  START_CHEST + Reference.PlayerInventory.SIZE; 
		
		//inventory and chest has 9 rows by 3 columns, never changes. same as 64 max stack size
		for(int islotChest = START_CHEST; islotChest < END_CHEST; islotChest++)
		{ 
			chestItem = chest.getStackInSlot(islotChest);
		
			if(chestItem == null)
			{  
				continue;
			}//not an error; empty chest slot
			 
			for(int islotInv = Reference.PlayerInventory.START; islotInv < Reference.PlayerInventory.END; islotInv++)
  			{ 
				invItem = entityPlayer.inventory.getStackInSlot(islotInv);
				
				if(invItem == null) 
				{ 
					continue;
			    }//empty inventory slot
		 
  				if( invItem.getItem().equals(chestItem.getItem()) && invItem.getItemDamage() ==  chestItem.getItemDamage() )
  				{  
  					//same item, including damage (block state)
  					
  					chestMax = chestItem.getItem().getItemStackLimit(chestItem);
  					room = chestMax - chestItem.stackSize;
  					 
  					if(room <= 0) {continue;} // no room, check the next spot
  			 
  					//so if i have 30 room, and 28 items, i deposit 28.
  					//or if i have 30 room and 38 items, i deposit 30
  					toDeposit = Math.min(invItem.stackSize,room);
 
  					chestItem.stackSize += toDeposit;
  					chest.setInventorySlotContents(islotChest, chestItem);

  					invItem.stackSize -= toDeposit;

  					totalItemsMoved += toDeposit;
  					//totalTypesMoved++;
  					
  					if(invItem.stackSize <= 0)//because of calculations above, should not be below zero
  					{
  						//item stacks with zero count do not destroy themselves, they show up and have unexpected behavior in game so set to empty
  						entityPlayer.inventory.setInventorySlotContents(islotInv,null); 
  						
  						totalSlotsFreed++;
  					}
  					else
  					{
  						//set to new quantity
  	  					entityPlayer.inventory.setInventorySlotContents(islotInv, invItem); 
  					} 
  				}//end if items match   
  			}//close loop on player inventory items 
		}//close loop on chest items
 
		if( totalSlotsFreed > 0) 
		{ 
			 
		//particles dont work, this only happens on server side (remote==false always)
			//SamsUtilities.spawnParticle(world,EnumParticleTypes.SLIME,chest.getPos().up()); 
		}
  	}
}
