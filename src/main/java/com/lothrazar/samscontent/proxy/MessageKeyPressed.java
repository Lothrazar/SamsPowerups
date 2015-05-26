package com.lothrazar.samscontent.proxy;
  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.command.CommandBindMacro;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.item.ItemChestSackEmpty;
import com.lothrazar.samscontent.item.ItemFoodGhost;
import com.lothrazar.samscontent.item.ItemMagicHarvester;
import com.lothrazar.samscontent.item.ItemWallCompass;
import com.lothrazar.samscontent.item.ItemWandPiston;
import com.lothrazar.samscontent.item.ItemWandTransform;
import com.lothrazar.samscontent.potion.PotionRegistry;
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
		/*
		else if( message.keyPressed == ClientProxy.keyPhasing.getKeyCode())
	 	{
			EnumFacing facing = EnumFacing.getFacingFromVector(
					(float)player.getLookVec().xCoord
					, (float)player.getLookVec().yCoord
					, (float)player.getLookVec().zCoord);

			System.out.println("phase  "+facing.getName());
			
			//.getHorizontal(MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);

			ItemWallCompass.wallPhase(player.worldObj,player,posMouse,facing);
		
	 	}*/
		else if( message.keyPressed == ClientProxy.keyPush.getKeyCode())
	 	{
			ItemWandPiston.moveBlockTo(player.worldObj, player, posMouse, posMouse.offset(player.getHorizontalFacing()),false);
	 	}
		else if( message.keyPressed == ClientProxy.keyPull.getKeyCode())
	 	{  
			ItemWandPiston.moveBlockTo(player.worldObj, player, posMouse, posMouse.offset(player.getHorizontalFacing().getOpposite()),false);
	 	}
		else if( message.keyPressed == ClientProxy.keyTransform.getKeyCode())
	 	{ 
			ItemWandTransform.transformBlock(player, player.worldObj, null, posMouse);
	 	}
		else if( message.keyPressed == ClientProxy.keyBindEnder.getKeyCode())
	 	{ 
			player.displayGUIChest(player.getInventoryEnderChest()); 
	 	}
		else if( message.keyPressed == ClientProxy.keySpellCast.getKeyCode())
	 	{ 
			
			SpellRegistry.cast(SpellRegistry.EnumSpellType.frostbolt, world, player);
			
			//Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,fiveSeconds,0));
	 	
	 	
	 	
	 	}
		else if( message.keyPressed == ClientProxy.keySpellUp.getKeyCode())
	 	{
			//ItemFoodGhost.setPlayerGhostMode(player,player.worldObj);
	 	}
		else if( message.keyPressed == ClientProxy.keySpellDown.getKeyCode())
	 	{ 
			//Util.addOrMergePotionEffect(player,new PotionEffect(Potion.jump.id,fiveSeconds,4));
	 	}/*
		else if( message.keyPressed == ClientProxy.keyBindSlowfall.getKeyCode())
	 	{ 
			Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.slowfall.id,fiveSeconds,0));
	 	}
		else if( message.keyPressed == ClientProxy.keyBindChestMover.getKeyCode())
	 	{
			ItemChestSackEmpty.convertChestToSack(player, null, (TileEntityChest)player.worldObj.getTileEntity(posMouse), posMouse);
	 	}
		else if( message.keyPressed == ClientProxy.keyFirebolt.getKeyCode())
	 	{
			BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

			world.spawnEntityInWorld(new EntitySmallFireball(world,up.getX(),up.getY(),up.getZ()
					 ,player.getLookVec().xCoord
					 ,player.getLookVec().yCoord
					 ,player.getLookVec().zCoord));

			Util.playSoundAt(player, Reference.sounds.bowtoss);
	 	}
		else if( message.keyPressed == ClientProxy.keyFrostbolt.getKeyCode())
	 	{
			BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();
			 
			EntitySnowballBolt snow = new EntitySnowballBolt(world,player);
			 
			 world.spawnEntityInWorld(snow);
		 
			Util.playSoundAt(player, Reference.sounds.bowtoss); 
			
			Util.decrHeldStackSize(player); 
	 	}
		else if( message.keyPressed == ClientProxy.keyLightningbolt.getKeyCode())
	 	{
			world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
			
			world.spawnEntityInWorld(new EntityLightningBolt(world, posMouse.getX(), posMouse.getY(), posMouse.getZ()));
	 	
	 	
			
	 	
	 	}
		else if( message.keyPressed == ClientProxy.keyHarvest.getKeyCode())
	 	{
			ItemMagicHarvester.replantField(world, player, null, posMouse);

//just testing
			
			
	 	}*/
		/*
		else if( message.keyPressed == ClientProxy.keyEnderPearl.getKeyCode())
	 	{
			//we could search inventory heere, if you are holding one then just use it
			world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
	 	}*/
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
 
