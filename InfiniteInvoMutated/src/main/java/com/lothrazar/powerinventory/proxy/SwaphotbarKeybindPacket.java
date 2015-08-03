package com.lothrazar.powerinventory.proxy;

import com.lothrazar.powerinventory.*;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class SwaphotbarKeybindPacket implements IMessage , IMessageHandler<SwaphotbarKeybindPacket, IMessage>
{
	public SwaphotbarKeybindPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public SwaphotbarKeybindPacket(NBTTagCompound ptags)
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
	public IMessage onMessage(SwaphotbarKeybindPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		 
		
		//so we have to get&set current page, and swap page numbers
		//
		
		
		UtilInventory.swapHotbar(p.inventory);
 	
		return null;
	}
}
