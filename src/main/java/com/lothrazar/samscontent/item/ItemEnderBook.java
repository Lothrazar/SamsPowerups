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

public class ItemEnderBook extends ItemTool
{ 
	public final static String KEY_LOC = "location"; 
	public static int DURABILITY;
	
	public ItemEnderBook()
	{  
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{ 
	     if (itemStack.getTagCompound() == null) 
	     { 
        	 //list.add("Right Click while sneaking to set location" );
        	 list.add("Save your current location while sneaking");
	    	 return;
	     }
	      
 		 String csv = itemStack.getTagCompound().getString(KEY_LOC);

		 if(csv != null && csv.isEmpty() == false) 
		 {
			 Location loc = new Location(csv);
	    	 list.add(EnumChatFormatting.DARK_GREEN + loc.toDisplayShort());//was NoCords
		 }  
	}

	public void saveCurrentLocation(World world, EntityPlayer entityPlayer, ItemStack itemStack) 
	{   
    	Location loc = new Location(0
    			,entityPlayer.posX
    			,entityPlayer.posY
    			,entityPlayer.posZ
    			,entityPlayer.dimension 
    			,""//,biome.biomeName //could get from world
    			);
    	 
    	SamsUtilities.setItemStackNotNull(itemStack);
    	itemStack.getTagCompound().setString(KEY_LOC, loc.toCSV());		
	} 
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{	 
		ItemStack itemStack = event.entityPlayer.getCurrentEquippedItem();

		if (itemStack == null || 
			itemStack.getItem() == null || 
			ItemRegistry.itemEnderBook == null ||
			itemStack.getItem() != ItemRegistry.itemEnderBook )
		{ 
			return; 
		}

		//left or right click with THIS book does the corresponding action
		 
		if (event.action.RIGHT_CLICK_BLOCK == event.action)
			if(event.entityPlayer.isSneaking())
			{ 			 
				saveCurrentLocation(event.world,event.entityPlayer, itemStack);		 
			} 
			else
			{ 
				teleport(event.world,event.entityPlayer, itemStack);	
			}
		
		event.entityPlayer.swingItem();
	}  
	
	public void teleport(World world, EntityPlayer entityPlayer, ItemStack enderBookInstance) 
	{ 
		int slot = entityPlayer.inventory.currentItem + 1;
    	//String KEY = ItemEnderBook.KEY_LOC + "_" + slot;
    	
		String csv = enderBookInstance.getTagCompound().getString(KEY_LOC);
		
		if(csv == null || csv.isEmpty()) {return;}
		
		Location loc = new Location(csv);
		 
		if(loc.dimension == Reference.Dimension.end) 
		{
			entityPlayer.setFire(4);//TODO: config file
		} 
		else if(loc.dimension == Reference.Dimension.nether) 
		{
			entityPlayer.heal(-15);//TODO: config file
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
	}

	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.itemEnderBook), 
				"eee", 
				"ebe",
				"eee", 
				'e', Items.ender_pearl, 
				'b', Items.book);

		if(ModSamsContent.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.itemEnderBook, 
					new ItemStack(Items.ender_pearl, 8), 0);
	}
}
 