package com.lothrazar.samscontent.item;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemPaperCarbon  extends ItemBaseWand
{ 
	//2 items cons
	//carbon paper!!!

	public ItemPaperCarbon()
	{  
		super();    
		this.setCreativeTab(ModSamsContent.tabSamsContent);
		this.setMaxStackSize(64);    
	}
  
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.carbon_paper),
			ItemRegistry.baseWand, 
			Items.paper  );
	}
	private static final String KEY_SIGN0 = "sign_0";
	private static final String KEY_SIGN1 = "sign_1";
	private static final String KEY_SIGN2 = "sign_2";
	private static final String KEY_SIGN3 = "sign_3";
	private static final String KEY_NOTE = "note";

	public static void copySign(World world, EntityPlayer entityPlayer,	TileEntitySign sign, ItemStack held) 
	{  
		SamsUtilities.setItemStackNBT(held, KEY_SIGN0, sign.signText[0].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, KEY_SIGN1, sign.signText[1].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, KEY_SIGN2, sign.signText[2].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, KEY_SIGN3, sign.signText[3].getUnformattedText());

		entityPlayer.swingItem(); 
	}

	public static void pasteSign(World world, EntityPlayer entityPlayer, TileEntitySign sign, ItemStack held) 
	{   
		sign.signText[0] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, KEY_SIGN0));
		sign.signText[1] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, KEY_SIGN1));
		sign.signText[2] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, KEY_SIGN2));
		sign.signText[3] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, KEY_SIGN3));
 
		entityPlayer.swingItem();
	 
		world.markBlockForUpdate(sign.getPos());//so update is refreshed on client side

		entityPlayer.swingItem();
	}

	public static void copyNote(World world, EntityPlayer entityPlayer,TileEntityNote noteblock, ItemStack held) 
	{ 
		if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
		
		held.getTagCompound().setByte(KEY_NOTE, noteblock.note); 
	}

	public static void pasteNote(World world, EntityPlayer entityPlayer,TileEntityNote noteblock, ItemStack held) 
	{ 
		if(held.getTagCompound() == null)  {return;}//nothing ot paste
		
		noteblock.note = held.getTagCompound().getByte(KEY_NOTE);
		world.markBlockForUpdate(noteblock.getPos());//so update is refreshed on client side

		entityPlayer.swingItem();
	} 
  
	@Override
	public void addInformation(ItemStack held, EntityPlayer player, List list, boolean par4) 
	{  
		if(held.getTagCompound() == null)
		{
			list.add("Shift click to copy a sign or noteblock"); 
			return;
		}
		
		String sign = SamsUtilities.getItemStackNBT(held, KEY_SIGN0)
				+ SamsUtilities.getItemStackNBT(held, KEY_SIGN1)
				+ SamsUtilities.getItemStackNBT(held, KEY_SIGN2)
				+ SamsUtilities.getItemStackNBT(held, KEY_SIGN3);
		
		if(sign.length() > 0)
		{ 
			list.add(SamsUtilities.getItemStackNBT(held, KEY_SIGN0));
			list.add(SamsUtilities.getItemStackNBT(held, KEY_SIGN1));
			list.add(SamsUtilities.getItemStackNBT(held, KEY_SIGN2));
			list.add(SamsUtilities.getItemStackNBT(held, KEY_SIGN3));
		}
 
		String s = SamsUtilities.noteToString(held.getTagCompound().getByte(KEY_NOTE));

		if(s != null)
		{
			list.add("Note: " + s);
		} 
	}

	
	public static void rightClickBlock(PlayerInteractEvent event) 
	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		TileEntity container = event.world.getTileEntity(event.pos);
		World world = event.world;
		EntityPlayer entityPlayer = event.entityPlayer;
		boolean isValid = false;
		boolean wasCopy = false;

		if(blockClicked == Blocks.wall_sign || blockClicked == Blocks.standing_sign )
		{
			TileEntitySign sign = (TileEntitySign)container;
			 
			if(entityPlayer.isSneaking())
			{ 
				ItemPaperCarbon.copySign(world,entityPlayer,sign,held); 
				wasCopy = true;
			}
			else
			{
				ItemPaperCarbon.pasteSign(world,entityPlayer,sign,held); 
				wasCopy = false;
			} 
			
			isValid = true; 
		}
		if(blockClicked == Blocks.noteblock)
		{
			TileEntityNote noteblock = (TileEntityNote)container;
			 
			if(entityPlayer.isSneaking())
			{ 
				ItemPaperCarbon.copyNote(world,entityPlayer,noteblock,held);
				wasCopy = true; 
			}
			else
			{
				ItemPaperCarbon.pasteNote(world,entityPlayer,noteblock,held); 
				wasCopy = false;
			} 
			
			isValid = true; 
		} 
		
		if(isValid)
		{
			if(event.world.isRemote)
			{	
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.PORTAL, event.pos); 
			}
			else
			{ 
				if(wasCopy == false)//on paste, we consume the item
				{
					SamsUtilities.decrHeldStackSize(entityPlayer);
				}
			}
			
			SamsUtilities.playSoundAt(event.entityPlayer, "random.fizz"); 
		}  
	}  
}
