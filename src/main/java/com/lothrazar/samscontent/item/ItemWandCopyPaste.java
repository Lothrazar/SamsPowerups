package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandCopyPaste  extends Item
{
	public static int DURABILITY;


	public ItemWandCopyPaste()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	

	public static void addRecipe() {
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandCopy),
			ItemRegistry.baseWand, 
			Items.paper  );
	}

	public static void copySign(World world, EntityPlayer entityPlayer,	TileEntitySign sign, ItemStack held) 
	{  
		SamsUtilities.setItemStackNBT(held, "sign_0", sign.signText[0].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_1", sign.signText[1].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_2", sign.signText[2].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_3", sign.signText[3].getUnformattedText());

		entityPlayer.swingItem(); 
	}

	public static void pasteSign(World world, EntityPlayer entityPlayer,	TileEntitySign sign, ItemStack held) 
	{  
		//sign.setPlayer(entityPlayer);
		sign.signText[0] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_0"));
		sign.signText[1] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_1"));
		sign.signText[2] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_2"));
		sign.signText[3] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_3"));

		//sign.setEditable(false);
		entityPlayer.swingItem();
	 
		world.markBlockForUpdate(sign.getPos());//so update is refreshed on client side

		entityPlayer.swingItem();
	}

	public static void copyNote(World world, EntityPlayer entityPlayer,TileEntityNote noteblock, ItemStack held) 
	{ 
		if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
		
		held.getTagCompound().setByte("note", noteblock.note); 
	}

	public static void pasteNote(World world, EntityPlayer entityPlayer,TileEntityNote noteblock, ItemStack held) 
	{ 
		if(held.getTagCompound() == null)  {return;}//nothing ot paste
		
		noteblock.note = held.getTagCompound().getByte("note");
		world.markBlockForUpdate(noteblock.getPos());//so update is refreshed on client side

		entityPlayer.swingItem();
	} 
	
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandCopy &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			if(blockClicked == Blocks.wall_sign || blockClicked == Blocks.standing_sign )
			{
				TileEntitySign sign = (TileEntitySign)event.world.getTileEntity(event.pos);
				 
				if(event.entityPlayer.isSneaking())
				{ 
					ItemWandCopyPaste.copySign(event.world,event.entityPlayer,sign,held); 
				}
				else
				{
					ItemWandCopyPaste.pasteSign(event.world,event.entityPlayer,sign,held); 
				} 
			}
			if(blockClicked == Blocks.noteblock)
			{
				TileEntityNote noteblock = (TileEntityNote)event.world.getTileEntity(event.pos);
				 
				if(event.entityPlayer.isSneaking())
				{ 
					ItemWandCopyPaste.copyNote(event.world,event.entityPlayer,noteblock,held); 
				}
				else
				{
					ItemWandCopyPaste.pasteNote(event.world,event.entityPlayer,noteblock,held); 
				} 
			}
			//TODO: copy or paste if shift or not
		//	ItemWandCopyPaste.castExtinguish(event.world,event.entityPlayer,held); 
			
		}
  	}
}
