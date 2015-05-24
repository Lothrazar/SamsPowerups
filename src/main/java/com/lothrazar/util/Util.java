package com.lothrazar.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;  
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;  
import com.lothrazar.samscontent.ModMain; 
import com.lothrazar.samscontent.potion.MessagePotion;

public class Util
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

 		if(worldObj.isRemote==false)//do not spawn a second 'ghost' one on client side
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
		//if(player.getCurrentEquippedItem()==null){return;}//better than crashing
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
	public static void playSoundAt(World world,BlockPos pos, String sound)
	{ 
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), sound, 1.0F, 1.0F, false);
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
	public static void setItemStackNBT(ItemStack item,	String prop, int value) 
	{
		setItemStackNotNull(item); 
		item.getTagCompound().setInteger(prop, value);
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
		int prev = player.getEntityData().getInteger(prop);
		prev += inc; 
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
				 ModMain.logger.log(Level.WARN, "getBlockListFromCSV : Block not found : "+id);
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
				ModMain.logger.log(Level.WARN, "Item not found : "+ ids[i]);
			}
			else
			{
				items.add(isItNull);
			} 
		}  
		
		return items;
	}
	 
	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
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
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}
	public static void printChatMessage(IChatComponent string) 
	{ 
		 Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(string); 
	}
	
	public static String posToString(BlockPos position) 
	{ 
		return "["+ position.getX() + ", "+position.getY()+", "+position.getZ()+"]";
	} 
	public static String posToStringCSV(BlockPos position) 
	{ 
		return position.getX() + ","+position.getY()+","+position.getZ();
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
		 
		Util.setItemStackNotNull(skull);
		
		skull.getTagCompound().setString("SkullOwner",displayNameString);
		
		return skull; 
	}
	public static ItemStack buildNamedPlayerSkull(EntityPlayer player) 
	{
		return Util.buildNamedPlayerSkull(player.getDisplayNameString());
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
		decrHeldStackSize(entityPlayer,1);
	}
	public static void decrHeldStackSize(EntityPlayer entityPlayer, int by) 
	{
		if (entityPlayer.capabilities.isCreativeMode == false)
        {
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, by);
        }
	}

	public static int getMaxDmgFraction(Item tool, int d) 
	{
		return tool.getMaxDamage() - (int)MathHelper.floor_double(tool.getMaxDamage() / d);
	}
	
	public static String getCoordsOrReduced(EntityPlayer player, BlockPos pos)
	{
		boolean showCoords = !player.worldObj.getGameRules().getGameRuleBooleanValue(Reference.gamerule.reducedDebugInfo);
		
		if(showCoords)
			return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
		else
			return Util.getDirectionsString(player, pos); 
	}
	
	//TODO: The showCoords on all the commands is not working at all. must hook to the gamerule
	//AND use this
	public static String getDirectionsString(EntityPlayer player, BlockPos pos  )
	{    
		/*
		if(showCoords )
		{ 
			return "(" + xLoop + ", " + yLoop + ", " + zLoop + ")" + " : "+ totalsStr;
		}
		*/
 
		int xDist,yDist,zDist;
		
		xDist = (int) player.posX - pos.getX();
		yDist = (int) player.posY - pos.getY();
		zDist = (int) player.posZ - pos.getZ();
		
		//in terms of directon copmass:
		//North is -z;  south is +z		
		//east is +x, west is -x
		
		//so for Distances: 
		
		boolean isNorth = (zDist > 0);
		boolean isSouth = (zDist < 0);
		
		boolean isWest = (xDist > 0);
		boolean isEast = (xDist < 0);

		boolean isUp   = (yDist < 0);
		boolean isDown = (zDist > 0);
		
		String xStr = "";
		String yStr = "";
		String zStr = "";

		if(isWest) xStr = Math.abs(xDist) + " west ";
		if(isEast) xStr = Math.abs(xDist) + " east ";
		
		if(isNorth) zStr = Math.abs(zDist) + " north ";
		if(isSouth) zStr = Math.abs(zDist) + " south ";

		if(isUp)   yStr = Math.abs(yDist) + " up ";
		if(isDown) yStr = Math.abs(yDist) + " down ";
		 
		return xStr +  yStr +  zStr ;
	}
	
	public static double getExpTotal(EntityPlayer player)
	{
		int level = player.experienceLevel;
		
		//this is the exp between previous and last level, not the real total
		float experience = player.experience;
	
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		double totalExp = getXpForLevel(level);
 
		//so now we knwo how much was used to get to current level

		//double nextLevelExp = getXpToGainLevel(level);
 
		double progress = Math.round(player.xpBarCap() * player.experience);

		totalExp += (int)progress;
		
		return totalExp;
	}

	public static boolean drainExp(EntityPlayer player, float f) 
	{  
		double totalExp = getExpTotal(player);
  
		//System.out.println("Drain from total = "+totalExp+" - "+f);
		
		if(totalExp - f < 0)
		{
			return false;
		}
		
		setXp(player, (int)(totalExp - f));
		
		return true;
		
	}
	
	public static int getXpToGainLevel(int level)
	{
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		//so if our current level is 5, we pass in5 here and find out
		//how much exp to get from 5 to 6
		int nextLevelExp = 0;

		if(level <= 15)
			nextLevelExp = 2*level + 7;
		else if(level <= 30)
			nextLevelExp = 5*level - 38;
		else //level >= 31 
			nextLevelExp = 9*level - 158;
		
		return nextLevelExp;
	}
	public static int getXpForLevel(int level)
	{
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		int totalExp = 0;
		
		if(level <= 15)
			totalExp = level*level + 6*level;
		else if(level <= 30)
			totalExp = (int)(2.5*level*level - 40.5*level + 360);
		else //level >= 31 
			totalExp = (int)(4.5*level*level - 162.5*level + 2220);//fixed. was +162... by mistake
		
		return totalExp;
	}
	public static int getLevelForXp(int xp) 
	{
		int lev = 0;
		while (getXpForLevel(lev) < xp) 
		{
			lev++;
		}
		return lev - 1;
	}
	public static void setXp(EntityPlayer player, int xp)
	{
		player.experienceTotal = xp;
		player.experienceLevel = getLevelForXp(xp);
		int next = getXpForLevel(player.experienceLevel);

		//System.out.println("getXpForLevel =    "+ player.experienceLevel+" => " + next);
		
		player.experience = (float)(player.experienceTotal - next) / (float)player.xpBarCap(); 
		
		
//should be always less than one...
		//System.out.println("experience =    "+ player.experience);

		//System.out.println("experienceTotal =    "+ player.experienceTotal);

		//System.out.println("experienceLevel =    "+ player.experienceLevel);
	}

	public static EnumFacing getPlayerFacing(EntityPlayer player) 
	{
    	int yaw = (int)player.rotationYaw;

        if (yaw<0)              //due to the yaw running a -360 to positive 360
           yaw+=360;    //not sure why it's that way

        yaw += 22;     //centers coordinates you may want to drop this line
        yaw %= 360;  //and this one if you want a strict interpretation of the zones

        int facing = yaw/45;   //  360degrees divided by 45 == 8 zones
       
		return EnumFacing.getHorizontal( facing/2 );
	}

	public static void addChatShapedRecipe(EntityPlayer player,	ItemStack[] recipeItems, boolean isInventory )
	{ 
		int size;
		
		//needed only becuase MC forge stores as a flat array not a 2D
		if(isInventory) size = 4;
		else size = 9;
		String[] grid = new String[size];
		for(int i = 0; i < grid.length; i++)grid[i] = "- ";
 
		
		for(int i = 0; i < recipeItems.length; i++)
    	{
    		if(recipeItems[i] != null)
    		{
    			Util.addChatMessage(player, i+" : "+recipeItems[i].getDisplayName());
    			grid[i] = i+" ";
    		}
    	}
		
		if(isInventory)
		{
			Util.addChatMessage(player, grid[0]+grid[1]);
			Util.addChatMessage(player, grid[2]+grid[3]);
		}
		else
		{
			Util.addChatMessage(player, grid[0]+grid[1]+grid[2]);
			Util.addChatMessage(player, grid[3]+grid[4]+grid[5]);
			Util.addChatMessage(player, grid[6]+grid[7]+grid[8]);
		}
	}

	public static void addChatShapelessRecipe(EntityPlayer player,	ItemStack[]  recipeItems)
	{
    	for(int i = 0; i < recipeItems.length; i++) 
    	{
    		ItemStack is = recipeItems[i];
    		
    		//list.add(is.getDisplayName());
        	Util.addChatMessage(player, " - "+is.getDisplayName());
    		
    	}
    	//TODO: cleanup/make ncer,etc
    	//Util.addChatMessage(player, "SHAPELESS " +String.join(" + ", list));
	}

	public static ItemStack[] getRecipeInput(IRecipe recipe)
	{
		ItemStack[] recipeItems = null;
	    if(recipe instanceof ShapedRecipes)
	    { 
	    	ShapedRecipes r = ((ShapedRecipes)recipe);
	    	recipeItems = r.recipeItems;
	    }
	    else if(recipe instanceof ShapedOreRecipe)
	    {
	    	ShapedOreRecipe r = (ShapedOreRecipe)recipe;
		
		    recipeItems = new ItemStack[r.getInput().length];
	    	
	    	for(int i = 0; i < r.getInput().length; i++) 
	    	{
	    		Object o = r.getInput()[i];
	    		if(o == null){continue;}
	    		if(o instanceof ItemStack)
	    		{
	    			recipeItems[i] = (ItemStack)o;
	    		}
	    		else
	    		{
				    List<ItemStack> c = (List<ItemStack>)o;
				    	
				    if(c != null && c.size() > 0)
				    {
				    	recipeItems[i] = c.get(0);
				    }
	    		}
	    	} 
	    } 
	    else if(recipe instanceof ShapelessRecipes)
		{
	    	ShapelessRecipes r = (ShapelessRecipes)recipe;
	    	
	    	recipeItems = new ItemStack[r.recipeItems.size()];
    		
	    	for(int i = 0; i < r.recipeItems.size(); i++) 
	    	{
	    		Object o = r.recipeItems.get(i);
	    		if(o != null && o instanceof ItemStack)
	    		{
	    			recipeItems[i] = (ItemStack)o;
	    		}
	    	} 
		}
	    else if(recipe instanceof ShapelessOreRecipe)
	    {
	    	ShapelessOreRecipe r = (ShapelessOreRecipe)recipe;

	    	recipeItems = new ItemStack[r.getInput().size()];
    		
	    	for(int i = 0; i < r.getInput().size(); i++) 
	    	{
	    		Object o = r.getInput().get(i);
	    		if(o == null){continue;}
	    		if(o instanceof ItemStack)
	    		{
	    			recipeItems[i] = (ItemStack)o;
	    		}
	    		else
	    		{
				    List<ItemStack> c = (List<ItemStack>)o;
				    	
				    if(c != null && c.size() > 0)
				    {
				    	recipeItems[i] = c.get(0);
				    }
	    		}
	    	} 
	    }

		return recipeItems;
	}

	//IMPORTANT: if you are on the client side already, DO NOT use this, use the regular one
	//this sends a packet from server to client, telling what particle to use
	public static void spawnParticlePacketByID(BlockPos position, int particleID)
	{
		//this. fires only on server side. so send packet for client to spawn particles and so on
		ModMain.network.sendToAll(new MessagePotion(position, particleID));
    	
		
	}
	/*
	public static final String power_slowfall = "power.slowfall";
	
	public static void addPowerup(EntityPlayer player, String powerid)
	{
		Util.incrementPlayerIntegerNBT(player, powerid, 1);
		//player.getEntityData().setInteger(key, value);
		
	}
	
	public static void addPowerSlowfall(EntityPlayer player)
	{
		addPowerup(player,power_slowfall);
	}
	
	public static boolean hasPowerup(EntityPlayer player, String powerid)
	{
		return  player.getEntityData().hasKey(powerid) && 
				player.getEntityData().getInteger(powerid) > 0;
	}
*/
	public static void execute(EntityPlayer player, String cmd)
	{
		MinecraftServer.getServer().getCommandManager().executeCommand(player, cmd);
	}

	public static void addOrMergePotionEffect(EntityPlayer player, PotionEffect newp)
	{
		if(player.isPotionActive(newp.getPotionID()))
		{
			//do not use built in 'combine' function, just add up duration myself
			PotionEffect p = player.getActivePotionEffect(Potion.potionTypes[newp.getPotionID()]);
			
			int ampMax = Math.max(p.getAmplifier(), newp.getAmplifier());
		
			player.addPotionEffect(new PotionEffect(newp.getPotionID()
					,newp.getDuration()+p.getDuration()
					,ampMax));
		}
		else
		{
			player.addPotionEffect(newp);
		}
	}
}
