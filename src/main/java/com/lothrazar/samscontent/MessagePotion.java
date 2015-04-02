package com.lothrazar.samscontent;

import com.lothrazar.util.SamsUtilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

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
		System.out.println("      ?? " );
		if(ctx.side.isClient())// == Side.CLIENT
		{
			//  http://www.minecraftforge.net/forum/index.php?topic=21195.0
			World world = Minecraft.getMinecraft().thePlayer.worldObj;//Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
 

			System.out.println("CLIENT  onMessage   " +world.isRemote);
			
			SamsUtilities.spawnParticleSixAround(world, EnumParticleTypes.SNOWBALL, new BlockPos(0,5,0));
			SamsUtilities.spawnParticleSixAround(world, EnumParticleTypes.SNOWBALL, new BlockPos(0,10,0));
			
		}
		else
		{
 
			System.out.println("SERVER  onMessage   " );	
		}
		/*
		

		
	 
System.out.println("got the message. client==null:   "+(ctx.getClientHandler() == null));
/*
/*
EntityPlayer player = ctx.getServerHandler().playerEntity; 

System.out.println(player.worldObj.isRemote);
*/
		return null;
	}
}
