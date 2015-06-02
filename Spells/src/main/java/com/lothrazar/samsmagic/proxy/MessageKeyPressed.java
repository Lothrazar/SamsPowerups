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
			//int tog = props.getSpellToggle() == 0 ? 1 : 0;
			//System.out.println("toggle from "+props.getSpellToggle());
			int next = props.getSpellToggleNext();
			props.setSpellToggle(next);
			

			//System.out.println("TO "+props.getSpellToggle());
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
 
