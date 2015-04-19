package com.lothrazar.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World; 

import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard; 

import com.lothrazar.samscontent.ModSamsContent;
import com.sun.istack.internal.logging.Logger;

public class SamsUtilities
{    
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, Block block)
	{
		return dropItemStackInWorld(worldObj, pos, new ItemStack(block));  
	}
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, Item item)
	{
		return dropItemStackInWorld(worldObj, pos, new ItemStack(item)); 
	}
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 
    	worldObj.spawnEntityInWorld(entityItem);
    	return entityItem;
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
	
	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	public static void setBlockIfAir(World world, BlockPos pos, IBlockState state)
	{
		if(world.isAirBlock(pos))
			world.setBlockState(pos, state); 
	}
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }
	public static void spawnParticleSixAround(World world, EnumParticleTypes type, BlockPos pos)
	{
		spawnParticle(world,type,pos.up());
		spawnParticle(world,type,pos.down());
		spawnParticle(world,type,pos.east());
		spawnParticle(world,type,pos.west());
		spawnParticle(world,type,pos.south());
		spawnParticle(world,type,pos.north());
    }
	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double)0.8, y + world.rand.nextDouble() * (double)1.5 - (double)0.1, z + (world.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
		} 
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
		setItemStackNotNull(item);
		
		String s = item.getTagCompound().getString(prop);
		if(s == null) { s = ""; }
		return s;
	} 
	
	public static void setItemStackNotNull(ItemStack item)
	{
		if(item.getTagCompound() == null) {item.setTagCompound(new NBTTagCompound());}
	}
	
	public static void setItemStackNBT(ItemStack item,	String prop, String value) 
	{
		setItemStackNotNull(item);
		 
		item.getTagCompound().setString(prop, value);
	} 
	
	public static void incrementItemStackIntegerNBT(ItemStack item,	String prop, int inc) 
	{
		int prev = getItemStackIntegerNBT(item,prop);

		prev += inc;//can be negative
		if(prev < 0) {prev = 0;}
		item.getTagCompound().setInteger(prop, prev);
	} 
	
	public static int getItemStackIntegerNBT(ItemStack item, String prop) 
	{
		setItemStackNotNull(item);
		return item.getTagCompound().getInteger(prop);
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
				 ModSamsContent.logger.log(Level.WARN, "getBlockListFromCSV : Block not found : "+id);
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
				ModSamsContent.logger.log(Level.WARN, "Item not found : "+ ids[i]);
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

		moveEntityWallSafe(player, world);
	}
	
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	
	public static boolean isShiftKeyDown()
	{
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT); 
	}

	public static void printChatMessage(String string) 
	{ 
		printChatMessage(new ChatComponentTranslation(string)); 
	}
	
	public static void printChatMessage(IChatComponent string) 
	{ 
		 Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(string); 
	}
	
	public static String posToString(BlockPos position) 
	{ 
		return "["+ position.getX() + ", "+position.getY()+", "+position.getZ()+"]";
	} 
	
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}

	public static String noteToString(byte note) 
	{
		String s = null;
		
		switch(note)
		{
		case 0:   s = EnumChatFormatting.YELLOW + "F#";  	break;//yellow
		case 1:   s = EnumChatFormatting.YELLOW + "G";  	break;
		case 2:   s = EnumChatFormatting.YELLOW + "G#";  	break;
		case 3:   s = EnumChatFormatting.YELLOW + "A";  	break;//or
		case 4:   s = EnumChatFormatting.YELLOW + "A#";  	break;//or
		case 5:   s = EnumChatFormatting.RED + "B";  		break;//red
		case 6:   s = EnumChatFormatting.RED + "C";  		break;//red
		case 7:   s = EnumChatFormatting.DARK_RED + "C#";  	break;
		case 8:   s = EnumChatFormatting.DARK_RED + "D";  	break;
		case 9:   s = EnumChatFormatting.LIGHT_PURPLE + "D#";  	break;//pink
		case 10:  s = EnumChatFormatting.LIGHT_PURPLE + "E";  	break;
		case 11:  s = EnumChatFormatting.DARK_PURPLE + "F";  	break;//purp
		case 12:  s = EnumChatFormatting.DARK_PURPLE + "F#";  	break;
		case 13:  s = EnumChatFormatting.DARK_PURPLE + "G";  	break;
		case 14:  s = EnumChatFormatting.DARK_BLUE + "G#";  	break;
		case 15:  s = EnumChatFormatting.DARK_BLUE + "A";  	break;//blue
		case 16:  s = EnumChatFormatting.BLUE + "A#";  	break;
		case 17:  s = EnumChatFormatting.BLUE + "B";  	break;
		case 18:  s = EnumChatFormatting.DARK_AQUA + "C";  	break;//lt blue?
		case 19:  s = EnumChatFormatting.AQUA + "C#";  	break;
		case 20:  s = EnumChatFormatting.AQUA + "D";  	break;//EnumChatFormatting.GREEN
		case 21:  s = EnumChatFormatting.GREEN + "D#";  	break;//there is no light green or dark green...
		case 22:  s = EnumChatFormatting.GREEN + "E";  	break;
		case 23:  s = EnumChatFormatting.AQUA + "F";  	break;
		case 24:  s = EnumChatFormatting.AQUA + "F#";  	break;//EnumChatFormatting.GREEN
		}
		
		return s;
	}
	
	
	public static TileEntityChest getChestAdj(TileEntityChest chest) 
	{
		TileEntityChest teAdjacent = null;
		if(chest.adjacentChestXNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXNeg; 
  	  	}
  		if(chest.adjacentChestXPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestXPos; 
  	  	}
  		if(chest.adjacentChestZNeg != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZNeg ; 
  	  	}
  		if(chest.adjacentChestZPos != null)
  	  	{
  	  		teAdjacent = chest.adjacentChestZPos; 
  	  	}
		return teAdjacent;
	}
	
	public static boolean isBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		return (heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal); 
	}
	 
	
	public static boolean isLivestock(Entity entity)
	{
		return  (entity instanceof EntityPig) || 
				(entity instanceof EntitySheep)|| 
				(entity instanceof EntityChicken) || 
				(entity instanceof EntityHorse) || 
				(entity instanceof EntityCow) ||
				(entity instanceof EntitySquid) ||
				(entity instanceof EntityRabbit); 
	}
	
	public static boolean isPet(Entity entity)
	{
		return  (entity instanceof EntityWolf) || 
				(entity instanceof EntityOcelot) || 
				(entity instanceof EntityVillager) || 
				(entity instanceof EntityBat)||
				(entity instanceof EntityRabbit) ||
				(entity instanceof EntityHorse)  ; 
	}

	public static ItemStack buildEnchantedNametag(String customNameTag) 
	{
		//build multi-level NBT tag so it matches a freshly enchanted one
		
		ItemStack nameTag = new ItemStack(Items.name_tag, 1); 
		  
		NBTTagCompound nbt = new NBTTagCompound(); 
		NBTTagCompound display = new NBTTagCompound();
		display.setString("Name", customNameTag);//NOT "CustomName" implied by commandblocks/google 
		nbt.setTag("display",display);
		nbt.setInteger("RepairCost", 1);
		
		nameTag.setTagCompound(nbt);//put the data into the item stack
		 
		return nameTag;
	}

	public static ItemStack buildNamedPlayerSkull(String displayNameString) 
	{
		ItemStack skull =  new ItemStack(Items.skull,1,Reference.skull_player);
		 
		SamsUtilities.setItemStackNotNull(skull);
		
		skull.getTagCompound().setString("SkullOwner",displayNameString);
		
		return skull; 
	}
	public static ItemStack buildNamedPlayerSkull(EntityPlayer player) 
	{
		return SamsUtilities.buildNamedPlayerSkull(player.getDisplayNameString());
	}

	public static BlockPos getBedLocationSafe(World world, EntityPlayer player) 
	{
		 BlockPos realBedPos = null;
		
		 BlockPos coords = player.getBedLocation(0);
		  
		 if(coords != null)
		 { 
			 Block block = world.getBlockState(coords).getBlock();
			 
			 if (block.equals(Blocks.bed) || block.isBed(world, coords, player))
			 {
				 //then move over according to how/where the bed wants me to spawn
				 realBedPos = block.getBedSpawnPosition(world, coords, player); 
			 }
		 }
		 
		 return realBedPos;
	}

	public static void decrHeldStackSize(EntityPlayer entityPlayer) 
	{
		if (entityPlayer.capabilities.isCreativeMode == false)
        {
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, 1);
        }
	}
}
