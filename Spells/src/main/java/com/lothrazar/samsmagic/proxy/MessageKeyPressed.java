package com.lothrazar.samsmagic.proxy;
  
import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.SpellRegistry;  
import com.lothrazar.samsmagic.PlayerPowerups;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MessageKeyPressed implements IMessage, IMessageHandler<MessageKeyPressed, IMessage>
{
	private byte keyPressed;
	  
	public static final int ID = 0;
	public MessageKeyPressed()
	{ 
	}
	
	public MessageKeyPressed(int keyCode)
	{ 
		this.keyPressed = (byte)keyCode;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.keyPressed = buf.readByte();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(keyPressed);
	}
	
	@Override
	public IMessage onMessage(MessageKeyPressed message, MessageContext ctx)
	{  
		EntityPlayer player = ctx.getServerHandler().playerEntity; 
		PlayerPowerups props = PlayerPowerups.get(player);
		World world = player.worldObj;
		BlockPos posMouse = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		//THANKS TO THIS
		//www.minecraftforge.net/forum/index.php/topic,20135.0.html
		int fiveSeconds = 20 * 5;//TODO : config? reference? cost?
 if( message.keyPressed == ClientProxy.keySpellCast.getKeyCode())
	 	{ 
			if(props.getSpellToggle() != SpellRegistry.SPELL_TOGGLE_HIDE)
			{
				SpellRegistry.cast(SpellRegistry.getPlayerCurrentISpell(player), world, player,posMouse);
			}
	 	}
		else if( message.keyPressed == ClientProxy.keySpellUp.getKeyCode())
	 	{
			if(props.getSpellToggle() != SpellRegistry.SPELL_TOGGLE_HIDE)
			{
				SpellRegistry.shiftLeft(player);
				
				ModMain.playSoundAt(player, "random.orb");
				
			}
	 	}
		else if( message.keyPressed == ClientProxy.keySpellDown.getKeyCode())
	 	{ 
			if(props.getSpellToggle() != SpellRegistry.SPELL_TOGGLE_HIDE)
			{
				SpellRegistry.shiftRight(player);
				ModMain.playSoundAt(player, "random.orb");
			}
			
		}
		else if( message.keyPressed == ClientProxy.keySpellToggle.getKeyCode())
	 	{  

        	System.out.println("MessageKeyPressed spelltoggle pressed");
			//int tog = props.getSpellToggle() == 0 ? 1 : 0;
			//System.out.println("toggle from "+props.getSpellToggle());
			int next = props.getSpellToggleNext();
			props.setSpellToggle(next);
			

			//System.out.println("TO "+props.getSpellToggle());
		}
		
		//TODO: search spawner??? with particle directors?

		return null;
	}
}
 
