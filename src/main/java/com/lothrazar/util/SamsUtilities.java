package com.lothrazar.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World; 

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard; 

import com.lothrazar.samscontent.ModLoader;
import com.sun.istack.internal.logging.Logger;

public class SamsUtilities
{    
	public static void dropItemStackInWorld(World worldObj, BlockPos pos, Block block)
	{
		dropItemStackInWorld(worldObj, pos, new ItemStack(block)); 
 
	}
	public static void dropItemStackInWorld(World worldObj, BlockPos pos, Item item)
	{
		dropItemStackInWorld(worldObj, pos, new ItemStack(item)); 
	}
	public static void dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 
    	worldObj.spawnEntityInWorld(entityItem);
	}
	public static void drainHunger(EntityPlayer player)
	{
		if(player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
	}
	public static void damageOrBreakHeld(EntityPlayer player)
	{ 
		if(player.getCurrentEquippedItem().getItemDamage() < player.getCurrentEquippedItem().getMaxDamage()) 
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			playSoundAt(player, "random.break");
		} 
	}
	public static void playSoundAt(EntityPlayer player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }
	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		float f = (float)x + 0.5F;
        float f1 = (float)y + 0.0F + 6.0F / 16.0F;
        float f2 = (float)z + 0.5F;
        float f3 = 0.52F;
        float f4 = 0.6F - 0.3F;
    	world.spawnParticle(type, (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
    }
	public static double distanceBetweenHorizontal(BlockPos start, BlockPos end)
	{
		int xDistance =  Math.abs(start.getX() - end.getX() );
		int zDistance =  Math.abs(start.getZ() - end.getZ() );
		//ye olde pythagoras
		return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
	}
	public static double distanceBetween(BlockPos start, BlockPos end)
	{
		int xDistance =  Math.abs(start.getX() - end.getX() );
		int yDistance =  Math.abs(start.getY() - end.getY() );
		int zDistance =  Math.abs(start.getZ() - end.getZ() );
		//ye olde pythagoras
		return Math.sqrt(xDistance * xDistance + zDistance * zDistance + yDistance * yDistance);
	}
	public static ArrayList<BlockPos> findBlocks(EntityPlayer player, Block blockHunt, int RADIUS ) 
	{        
		ArrayList<BlockPos> found = new ArrayList<BlockPos>();
		int xMin = (int) player.posX - RADIUS;
		int xMax = (int) player.posX + RADIUS;

		int yMin = (int) player.posY - RADIUS;
		int yMax = (int) player.posY + RADIUS;

		int zMin = (int) player.posZ - RADIUS;
		int zMax = (int) player.posZ + RADIUS;
		 
		int xDistance, zDistance, distance , distanceClosest = RADIUS * RADIUS;
		
		BlockPos posCurrent = null; 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{  
					posCurrent = new BlockPos(xLoop, yLoop, zLoop);
					if(player.worldObj.getBlockState(posCurrent).getBlock().equals(blockHunt))
					{ 
						xDistance = (int) Math.abs(xLoop - player.posX );
						zDistance = (int) Math.abs(zLoop - player.posZ );
						
						distance = (int) distanceBetweenHorizontal(player.getPosition(), posCurrent);
						
						found.add(posCurrent);
					} 
				}
			}
		}
		
		return found; 
	}
	public static BlockPos findClosestBlock(EntityPlayer player, Block blockHunt, int RADIUS )// Blocks.mob_spawner
	{        
		int xMin = (int) player.posX - RADIUS;
		int xMax = (int) player.posX + RADIUS;

		int yMin = (int) player.posY - RADIUS;
		int yMax = (int) player.posY + RADIUS;

		int zMin = (int) player.posZ - RADIUS;
		int zMax = (int) player.posZ + RADIUS;
		 
		int xDistance, zDistance, distance , distanceClosest = RADIUS * RADIUS;
		
		BlockPos posCurrent = null;
		BlockPos posFound = null;
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{  
					posCurrent = new BlockPos(xLoop, yLoop, zLoop);
					if(player.worldObj.getBlockState(posCurrent).getBlock().equals(blockHunt))
					{ 
						xDistance = (int) Math.abs(xLoop - player.posX );
						zDistance = (int) Math.abs(zLoop - player.posZ );
						
						distance = (int) distanceBetweenHorizontal(player.getPosition(), posCurrent);
						 
						if(posFound == null || distance < distanceClosest)
						{ 
							distanceClosest = distance;
							posFound = posCurrent;//new BlockPos(xLoop, yLoop, zLoop); 
						} 
					} 
				}
			}
		}
		
		return posFound; 
	}
	 
	private static void decrementPlayerHunger(EntityPlayer player)
	{ 
		if( player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
	}
	private void onSuccess(EntityPlayer player)
	{
		player.swingItem();
	 
		if(player.getCurrentEquippedItem().getItemDamage() < player.getCurrentEquippedItem().getMaxDamage() - 1)//if about to die
		{
			player.getCurrentEquippedItem().damageItem(1, player);
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
	}
	public static String getItemStackNBT(ItemStack item, String prop) 
	{
		if(item.getTagCompound() == null) item.setTagCompound(new NBTTagCompound());
		String s = item.getTagCompound().getString(prop);
		if(s == null) { s = ""; }
		return s;
	} 
	public static void setItemStackNBT(ItemStack item,	String prop, String value) 
	{
		if(item.getTagCompound() == null) item.setTagCompound(new NBTTagCompound());
		item.getTagCompound().setString(prop, value);
	} 
	public static void incrementItemStackIntegerNBT(ItemStack item,	String prop, int inc) 
	{
		int prev = getItemStackIntegerNBT(item,prop);

		prev += inc;//can be negative
		if(prev < 0) {prev = 0;}
		item.getTagCompound().setInteger(prop, prev);
	} 
	public static int getItemStackIntegerNBT(ItemStack heldWand, String prop) 
	{
		if(heldWand.getTagCompound() == null) heldWand.setTagCompound(new NBTTagCompound());
		return heldWand.getTagCompound().getInteger(prop);
	}
	public static void incrementPlayerIntegerNBT(EntityPlayer player, String prop, int inc)
	{
		int prev = getPlayerIntegerNBT(player,prop);
		prev += inc;//can be negative
		if(prev < 0) {prev = 0;}
		player.getEntityData().setInteger(prop, prev);
	}
	 
	public static ArrayList<Block> getBlockListFromCSV(String csv)
	{
		 ArrayList<Block> blocks = new ArrayList<Block>();
		 String[] ids = csv.split(",");  
		 Block b; 
		 
		 for(String id : ids)
		 {
			 b = Block.getBlockFromName(id);
			 
			 if(b == null)
			 {
				 ModLoader.logger.log(Level.WARN, "getBlockListFromCSV : Block not found : "+id);
			 }
			 else 
			 {
				 blocks.add(b);
			 }
		 } 
		 
		 return blocks;
	}
	
	public static ArrayList<Item> getItemListFromCSV(String csv)
	{
		ArrayList<Item> items = new ArrayList<Item>();
		String[] ids = csv.split(","); 
		Item isItNull = null;
		Block b;
		
		for(int i = 0; i < ids.length; i++)
		{
			isItNull = Item.getByNameOrId(ids[i]);
			if(isItNull == null)  //try to get block version  
			{ 
				b = Block.getBlockFromName(ids[i]);
				if(b != null)	isItNull = Item.getItemFromBlock(b); 
			} 
			 
			if(isItNull == null)
			{
				ModLoader.logger.log(Level.WARN, "Item not found : "+ ids[i]);
			}
			else
			{
				items.add(isItNull);
			} 
		}  
		
		return items;
	}
	
	public static int getPlayerIntegerNBT(EntityPlayer player, String prop)
	{ 
		return player.getEntityData().getInteger(prop);
	}

	public static void teleportWallSafe(EntityPlayer player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		while (!world.getCollidingBoundingBoxes(player, player.getEntityBoundingBox()).isEmpty())
		{
			player.setPositionAndUpdate(player.posX, player.posY + 1.0D, player.posZ);
		}
		  
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}
	
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT); 
	}
/*//when an action is used
	private void onSuccess(EntityPlayer player,ItemStack heldWand)
	{
		player.swingItem();
		 
		if(drainsHunger && player.getFoodStats().getFoodLevel() > 0)
		{
			player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1 );
		}
		
		//make it take damage, or get destroyed
  
		if(player.getCurrentEquippedItem().getItemDamage() < ItemWandDungeon.DURABILITY - 1)//if about to die
		{ 
			player.getCurrentEquippedItem().damageItem(1, player); 
		}
		else
		{ 
			player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
 
			player.worldObj.playSoundAtEntity(player, "random.break", 1.0F, 1.0F);
		} 
		
		player.worldObj.spawnParticle(EnumParticleTypes.FLAME, player.posX,player.posY,player.posZ,1,1,1,1); 
		player.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, player.posX,player.posY,player.posZ,1,1,1,1); 
		player.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}*/
}
