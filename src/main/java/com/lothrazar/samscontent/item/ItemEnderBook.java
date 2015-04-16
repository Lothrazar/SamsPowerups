package com.lothrazar.samscontent.item;

import java.util.List; 

import com.google.common.collect.Sets;   
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.util.MathHelper;

public class ItemEnderBook extends Item
{ 
	public final static String KEY_LOC = "location";  
	
	public ItemEnderBook()
	{  
		super();  
		this.setMaxStackSize(1); 
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.itemEnderBook), 
				 Items.ender_pearl, 
				 Items.book);

		if(ModSamsContent.configSettings.uncraftGeneral) 
		{
			GameRegistry.addSmelting(ItemRegistry.itemEnderBook, 
					new ItemStack(Items.ender_pearl, 1), 0);
		}
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if(itemStack.getTagCompound() == null) 
	     {  
        	 list.add("Save your current location while sneaking.  Only works in the overworld.");
	    	 return;
	     }
	      
 		 String csv = itemStack.getTagCompound().getString(KEY_LOC);

		 if(csv != null && csv.isEmpty() == false) 
		 {
			 Location loc = new Location(csv);
	    	 list.add(EnumChatFormatting.DARK_GREEN + loc.toDisplay());//was toDisplayShort
		 }  
	}

	public static void saveCurrentLocation(World world, EntityPlayer entityPlayer, ItemStack itemStack) 
	{   
		if(entityPlayer.dimension != Reference.Dimension.overworld) 
		{ 
			return; 
		}
		
    	Location loc = new Location(entityPlayer.dimension
    			,entityPlayer.posX
    			,entityPlayer.posY
    			,entityPlayer.posZ
    			,entityPlayer.dimension 
    			,world.getBiomeGenForCoords(entityPlayer.getPosition()).biomeName //is this used anywhere?
    		);
    	 
    	SamsUtilities.setItemStackNotNull(itemStack);
    	itemStack.getTagCompound().setString(KEY_LOC, loc.toCSV());		
		entityPlayer.swingItem();
	} 
	  
	public static void teleport(World world, EntityPlayer entityPlayer, ItemStack enderBookInstance) 
	{  
		String csv = enderBookInstance.getTagCompound().getString(KEY_LOC);
		
		if(csv == null || csv.isEmpty()) {return;}
		
		Location loc = new Location(csv);
		 
		if(loc.dimension == Reference.Dimension.end) 
		{
			entityPlayer.setFire(4);//TODO: config file
		} 
		else if(loc.dimension == Reference.Dimension.nether) 
		{
			entityPlayer.setFire(4);//TODO: config file
		}
		
		if(entityPlayer.dimension != Reference.Dimension.overworld) 
		{ 
			return;//if its end, nether, or anything else such as from another mod
		}
		
		//do once before teleport
		 
		world.playSoundAtEntity(entityPlayer, "mob.endermen.portal", 1.0F, 1.0F);  
		SamsUtilities.spawnParticle(world, EnumParticleTypes.PORTAL, entityPlayer.getPosition());
		SamsUtilities.spawnParticle(world, EnumParticleTypes.PORTAL, entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing()));//they are suttle, so make extra
		
		SamsUtilities.teleportWallSafe(entityPlayer, world, new BlockPos(loc.X,loc.Y,loc.Z));

		//and again at new location
		world.playSoundAtEntity(entityPlayer, "mob.endermen.portal", 1.0F, 1.0F);  
		SamsUtilities.spawnParticle(world, EnumParticleTypes.PORTAL, entityPlayer.getPosition());		
		SamsUtilities.spawnParticle(world, EnumParticleTypes.PORTAL, entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing()));//they are suttle, so make extra
		
	
		entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
		entityPlayer.swingItem();
		
		SamsUtilities.decrHeldStackSize(entityPlayer);
	}
 
	public static void rightClickBlock(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{ 
		if(entityPlayer.isSneaking())
		{ 			 
			ItemEnderBook.saveCurrentLocation(world,entityPlayer, held);		 
		} 
		else
		{ 
			ItemEnderBook.teleport(world,entityPlayer, held);	
		}  
	}
}
 