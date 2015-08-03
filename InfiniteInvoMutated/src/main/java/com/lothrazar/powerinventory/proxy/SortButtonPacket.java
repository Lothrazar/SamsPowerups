package com.lothrazar.powerinventory.proxy;

import java.util.LinkedList;
import java.util.Queue;

import com.lothrazar.powerinventory.*;
import com.lothrazar.powerinventory.inventory.BigInventoryPlayer;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class SortButtonPacket implements IMessage , IMessageHandler<SortButtonPacket, IMessage>
{
	public SortButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public SortButtonPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}

	public static final String NBT_SORT = "sort";

	@Override
	public IMessage onMessage(SortButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
 
		int sortType = message.tags.getInteger(NBT_SORT);
		
		//p.inventoryContainer

		 
		
		switch(sortType)
		{
		case Const.SORT_PAGE_DOWN:
	 
			if(p.inventory instanceof BigInventoryPlayer)
			{
				BigInventoryPlayer big = (BigInventoryPlayer)p.inventory;
				int oldPage = big.getCurrentPage();
				if(big.incrementPage())
				{
					//really, we should swap 0 with old, and old with new.
					//so if going from page 3 to page 4
					//this means that 0(visible 3) goes into pageslot 3 and then that content swaps into 4, and 4 becomes visible
					
					if(0 != oldPage)
						UtilInventory.swapPage(p.inventory,0, oldPage);
					
					
					//so if going 0 to 1, do only this
					UtilInventory.swapPage(p.inventory,0, big.getCurrentPage());
					
				}
				
				System.out.printf("\ncurrentPage %d",big.getCurrentPage());

				p.getEntityData().setInteger("page", big.getCurrentPage());
			}
			
			break;
		case Const.SORT_PAGE_UP:


			if(p.inventory instanceof BigInventoryPlayer)
			{
				BigInventoryPlayer big = (BigInventoryPlayer)p.inventory;
				int oldPage = big.getCurrentPage();

				if(big.decrementPage())
				{
					
					
					//so could go 4 to 3, etc 2 to 1, 1 to 0.
					
					//really, we should swap 0 with old, and old with new.
					//so if going from page 4 to page 3
					//this means that 0(visible 3) goes to hiding
					
					if(0 != oldPage)
						UtilInventory.swapPage(p.inventory,0, oldPage);
					
					
					//so if going 0 to 1, do only this
					UtilInventory.swapPage(p.inventory,0, big.getCurrentPage());
					
					//UtilInventory.swapPage(p.inventory,big.getCurrentPage(),oldPage);
				}

				System.out.printf("\ncurrentPage %d",big.getCurrentPage());
				p.getEntityData().setInteger("page", big.getCurrentPage());
			}
			
			
			break;
		case Const.SORT_LEFTALL:
			UtilInventory.shiftLeftAll(p.inventory);
			break;
		case Const.SORT_RIGHTALL:
			UtilInventory.shiftRightAll(p.inventory);
			break;
		}
	  
		return null;
	}
	
}
