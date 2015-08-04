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
public class PageButtonPacket implements IMessage , IMessageHandler<PageButtonPacket, IMessage>
{
	public final static String NBT_PAGE = "page";
	public PageButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public PageButtonPacket(NBTTagCompound ptags)
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
 
	@Override
	public IMessage onMessage(PageButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
 
		int page = message.tags.getInteger(NBT_PAGE);
	
		if(p.inventory instanceof BigInventoryPlayer)
		{
			BigInventoryPlayer big = (BigInventoryPlayer)p.inventory;
			int oldPage = big.getCurrentPage();
			if(big.setPage(page))
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
		
		return null;
	}
	
}
