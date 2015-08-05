package com.lothrazar.pagedinventory.proxy;

import com.lothrazar.pagedinventory.Const;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class OpenInventoryPacket implements IMessage , IMessageHandler<OpenInventoryPacket, IMessage>
{
	public OpenInventoryPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public OpenInventoryPacket(NBTTagCompound ptags)
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
	public IMessage onMessage(OpenInventoryPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
	//	System.out.println("open inv packet");
		//int invType = message.tags.getInteger("i");

		//switch(invType)
		//{
		//case Const.INV_ENDER:
		if(p.inventory.getStackInSlot(Const.BONUS_START+Const.type_echest) != null)
			p.displayGUIChest(p.getInventoryEnderChest());
		else 
			p.addChatMessage(new ChatComponentText("slot.enderchest"));
		//break;
		//case Const.INV_PLAYER:

			//this packet should not have been sent. but keep empty branch so i remember it
		//	break;
		//}

		return null;
	}
}
