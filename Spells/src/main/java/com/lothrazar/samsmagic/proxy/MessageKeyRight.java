package com.lothrazar.samsmagic.proxy;
  
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry;  
import com.lothrazar.samsmagic.PlayerPowerups;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MessageKeyRight implements IMessage, IMessageHandler<MessageKeyRight, IMessage>
{
	private BlockPos pos;
	private String csv;
	public static final int ID = 2;
	public MessageKeyRight()
	{ 
	}
	
	public MessageKeyRight(BlockPos pm)
	{ 
		pos = pm;
		csv = ModSpells.posToCSV(pos);
	}
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//http://www.minecraftforge.net/forum/index.php?topic=20135.0

		csv = ByteBufUtils.readUTF8String(buf); 
        
		pos = ModSpells.stringCSVToBlockPos(csv);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
        ByteBufUtils.writeUTF8String(buf, csv);
	}
	
	@Override
	public IMessage onMessage(MessageKeyRight message, MessageContext ctx)
	{  
		EntityPlayer player = ctx.getServerHandler().playerEntity; 
		PlayerPowerups props = PlayerPowerups.get(player);
	
		//www.minecraftforge.net/forum/index.php/topic,20135.0.html

		if(props.getSpellToggle() != SpellRegistry.SPELL_TOGGLE_HIDE)
		{
			SpellRegistry.shiftRight(player);
		}

		return null;
	}
}
 