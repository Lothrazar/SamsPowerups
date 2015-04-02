package com.lothrazar.samscontent;

import com.lothrazar.util.SamsUtilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePotion implements IMessage, IMessageHandler<MessagePotion, IMessage>
{

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IMessage onMessage(MessagePotion message, MessageContext ctx)
	{ 
		
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();

		SamsUtilities.spawnParticle(world, EnumParticleTypes.SNOWBALL, new BlockPos(0,5,0));
	 
			System.out.println("onMessage isRemote ==  "+world.isRemote);
	 
System.out.println("got the message. client==null:   "+(ctx.getClientHandler() == null));
/*
EntityPlayer player = ctx.getServerHandler().playerEntity; 

System.out.println(player.worldObj.isRemote);
*/
		return null;
	}
}
