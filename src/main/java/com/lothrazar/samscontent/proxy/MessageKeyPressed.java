package com.lothrazar.samscontent.proxy;
  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.command.CommandBindMacro;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.spell.ISpell;
import com.lothrazar.samscontent.spell.UtilBlockTransform;
import com.lothrazar.samscontent.spell.UtilPistonSpell;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
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
		World world = player.worldObj;
		BlockPos posMouse = Minecraft.getMinecraft().objectMouseOver.getBlockPos();
		//THANKS TO THIS
		//www.minecraftforge.net/forum/index.php/topic,20135.0.html
		int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?
 
		if( message.keyPressed == ClientProxy.keyShiftUp.getKeyCode())
 	    {    
			shiftSlotUp(player, player.inventory.currentItem); 
		} 
		else if( message.keyPressed == ClientProxy.keyShiftDown.getKeyCode())
	 	{  
			shiftSlotDown(player, player.inventory.currentItem); 
		} 
		else if( message.keyPressed == ClientProxy.keyBarUp.getKeyCode())
	 	{   
			shiftSlotUp(player, 0); 
			shiftSlotUp(player, 1); 
			shiftSlotUp(player, 2); 
			shiftSlotUp(player, 3); 
			shiftSlotUp(player, 4); 
			shiftSlotUp(player, 5); 
			shiftSlotUp(player, 6); 
			shiftSlotUp(player, 7); 
			shiftSlotUp(player, 8); 
	 	}
		else if( message.keyPressed == ClientProxy.keyBarDown.getKeyCode())
	 	{  
			shiftSlotDown(player, 0); 
			shiftSlotDown(player, 1); 
			shiftSlotDown(player, 2); 
			shiftSlotDown(player, 3); 
			shiftSlotDown(player, 4); 
			shiftSlotDown(player, 5); 
			shiftSlotDown(player, 6); 
			shiftSlotDown(player, 7); 
			shiftSlotDown(player, 8); 
	 	} 
		else if( message.keyPressed == ClientProxy.keyBindMacro1.getKeyCode())//TODO: better code structure here?
	 	{
			CommandBindMacro.tryExecuteMacro(player,Reference.keyBind1Name);
	 	}
		else if( message.keyPressed == ClientProxy.keyBindMacro2.getKeyCode())
	 	{
			CommandBindMacro.tryExecuteMacro(player, Reference.keyBind2Name);
	 	}
		else if( message.keyPressed == ClientProxy.keyPush.getKeyCode())
	 	{
			UtilPistonSpell.moveBlockTo(player.worldObj, player, posMouse, posMouse.offset(player.getHorizontalFacing()),false);
	 	}
		else if( message.keyPressed == ClientProxy.keyPull.getKeyCode())
	 	{  
			UtilPistonSpell.moveBlockTo(player.worldObj, player, posMouse, posMouse.offset(player.getHorizontalFacing().getOpposite()),false);
	 	}
		else if( message.keyPressed == ClientProxy.keyTransform.getKeyCode())
	 	{ 
			UtilBlockTransform.transformBlock(player, player.worldObj, null, posMouse);
	 	} 
		else if( message.keyPressed == ClientProxy.keySpellCast.getKeyCode())
	 	{ 
			//isRemote == false always
			//System.out.println("cast current spell isremote = "+player.worldObj.isRemote);
			EnumSpellType spell = SpellRegistry.getPlayerCurrentSpell(player);
			//ISpell//TODO:
			
			SpellRegistry.cast(spell, world, player,posMouse);
	 	}
		else if( message.keyPressed == ClientProxy.keySpellUp.getKeyCode())
	 	{
			SpellRegistry.shiftUp(player);
	 	}
		else if( message.keyPressed == ClientProxy.keySpellDown.getKeyCode())
	 	{ 
			SpellRegistry.shiftDown(player);
		}
	 
		//TODO: search spawner??? with particle directors?

		return null;
	}

	//TODO: move function to spellbook
	private void shiftSlotDown(EntityPlayer player, int currentItem) 
	{
		int topNumber = currentItem + 9;
		int midNumber = topNumber + 9;
		int lowNumber = midNumber + 9;
		//so if we had the final slot hit (8 for keyboard 9) we would go 8, 17, 26, 35
		 
		ItemStack bar = player.inventory.getStackInSlot(currentItem);
		ItemStack top = player.inventory.getStackInSlot(topNumber);
		ItemStack mid = player.inventory.getStackInSlot(midNumber);
		ItemStack low = player.inventory.getStackInSlot(lowNumber);
  
		player.inventory.setInventorySlotContents(currentItem, null);
		player.inventory.setInventorySlotContents(currentItem, top);//lot so 0 gets what 9 had

		player.inventory.setInventorySlotContents(topNumber, null);
		player.inventory.setInventorySlotContents(topNumber, mid);

		player.inventory.setInventorySlotContents(midNumber, null);
		player.inventory.setInventorySlotContents(midNumber, low);
		
		player.inventory.setInventorySlotContents(lowNumber, null);
		player.inventory.setInventorySlotContents(lowNumber, bar);
	}

	private void shiftSlotUp(EntityPlayer player, int currentItem) 
	{
		//so we move each up by nine
		int topNumber = currentItem + 9;
		int midNumber = topNumber + 9;
		int lowNumber = midNumber + 9;
		//so if we had the final slot hit (8 for keyboard 9) we would go 8, 17, 26, 35
		 
		ItemStack bar = player.inventory.getStackInSlot(currentItem);
		ItemStack top = player.inventory.getStackInSlot(topNumber);
		ItemStack mid = player.inventory.getStackInSlot(midNumber);
		ItemStack low = player.inventory.getStackInSlot(lowNumber);
  
		player.inventory.setInventorySlotContents(currentItem, null);
		player.inventory.setInventorySlotContents(currentItem, low);//lot so 0 gets what 9 had
 
		player.inventory.setInventorySlotContents(lowNumber, null);
		player.inventory.setInventorySlotContents(lowNumber, mid);
 
		player.inventory.setInventorySlotContents(midNumber, null);
		player.inventory.setInventorySlotContents(midNumber, top);
 
		player.inventory.setInventorySlotContents(topNumber, null);
		player.inventory.setInventorySlotContents(topNumber, bar);
	}
}
 
