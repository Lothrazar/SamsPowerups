package com.lothrazar.samscontent.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemMagicHarvester extends Item
{
	public static int RADIUS; //from config file
	
	public ItemMagicHarvester( )
	{   
		super(); 
    	this.setMaxStackSize(64); 
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.harvest_charge),
			Items.ender_eye, 
			Blocks.hay_block  );
	}
	   
	public static void replantField(World world, EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{  
		//http://www.minecraftforge.net/wiki/Plants
 
		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//search in a cube
		int xMin = x - RADIUS;
		int xMax = x + RADIUS; 
		int zMin = z - RADIUS;
		int zMax = z + RADIUS;
		
		int eventy = pos.getY();
		
		BlockPos posCurrent;
		
		int countHarvested = 0;
		
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				posCurrent = new BlockPos(xLoop, eventy, zLoop);
				IBlockState bs = world.getBlockState(posCurrent);
				Block blockCheck = bs.getBlock(); 

				if(blockCheck instanceof IGrowable)
				{ 
					IGrowable plant = (IGrowable) blockCheck;

					if(plant.canGrow(world, posCurrent, bs, world.isRemote) == false)
					{  
						if(world.isRemote == false)  //only drop items in serverside
							world.destroyBlock(posCurrent, true);
						//break fully grown, plant new seed
						world.setBlockState(posCurrent, blockCheck.getDefaultState());//this plants a seed. it is not 'hay_block'
					
						countHarvested++;
					} 
				} 
			}  
		} //end of the outer loop
 
		if(countHarvested > 0)//something happened
		{ 
			entityPlayer.swingItem();
	
			SamsUtilities.playSoundAt(entityPlayer, "mob.zombie.remedy");
			 
			if(world.isRemote) //client side 
				SamsUtilities.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, pos);//cant find the Bonemeal particles 
			else 
			{
				SamsUtilities.decrHeldStackSize(entityPlayer);  
			} 
		}
	}
	 
}
