package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class PlaceLib
{

	
	//library of functions/configs that apply to all /place[] commands
	//for all of these, we allow the player to be null
	
	//TODO: should xp cost be here as well?
	public static ArrayList<Block> allowed = new ArrayList<Block>();
	public static String allowedFromConfig = "";

	public static boolean canSenderPlace(ICommandSender sender)
	{
		EntityPlayer player = (EntityPlayer)sender;
		
		if(player == null){return false;}//was sent by command block or something, ignore it
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0)
		{
			Util.addChatMessage(player, "command.place.empty"); 
			return false;
		}
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null){return false;}
			
		if(PlaceLib.isAllowed(pblock) == false)
		{ 
			Util.addChatMessage(player, "command.place.notallowed"); 
			return false;
		}
		
		return true;
	}
	
	public static void translateCSV()
	{
		//do this on the fly, could be items not around yet during config change
		if(PlaceLib.allowed.size() == 0)
			PlaceLib.allowed = Util.getBlockListFromCSV(PlaceLib.allowedFromConfig); 
	}         

	public static boolean isAllowed(Block pblock)
	{
		translateCSV();
		
		return PlaceLib.allowed.size() == 0 || PlaceLib.allowed.contains(pblock);
	}
	
	private static boolean payExpAndCheckValid(World world, EntityPlayer player, BlockPos pos, int xpRequired)
	{
		if(world.isAirBlock(pos)){return false;}

		//but for the next 2 checks, halt if we run out of blocks/cost
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem() .stackSize == 0) {return false;}
		
		if(xpRequired > 0) //do nothing if we cannot pay the cost
		{
			if(Util.drainExp(player, xpRequired) == false)
			{
				Util.addChatMessage(player, "command.place.exp"); 
				return false;//could not drain xp, so not valid
			}
		}
		
		return true;
	}
	
	public static void circle(World world, EntityPlayer player, BlockPos pos, IBlockState placing, int radius, int costPerBlock) 
	{
		int centerX = pos.getX();
		int centerZ = pos.getZ();
		
		int height = (int)pos.getY();
		
		int z = radius;
		int x = 0;
		int d = 2 - (2 * radius);
		
		ArrayList<BlockPos> circleList = new ArrayList<BlockPos>(); 
		
		do 
		{
			circleList.add(new BlockPos(centerX + x, height, centerZ + z)); 
	        circleList.add(new BlockPos(centerX + x, height, centerZ - z));
	        circleList.add(new BlockPos(centerX - x, height, centerZ + z));
	        circleList.add(new BlockPos(centerX - x, height, centerZ - z));
	        circleList.add(new BlockPos(centerX + z, height, centerZ + x));
	        circleList.add(new BlockPos(centerX + z, height, centerZ - x));
	        circleList.add(new BlockPos(centerX - z, height, centerZ + x));
	        circleList.add(new BlockPos(centerX - z, height, centerZ - x));
	        
	        if (d < 0) 
	        {
	            d = d + (4 * x) + 6;
	        } 
	        else 
	        {
	            d = d + 4 * (x - z) + 10;
	            z--;
	        }
	        
	        x++;
	    } 
		while (x <= z);
		
		for(BlockPos p : circleList)
		{
			/*
			if(world.isAirBlock(p) == false){continue;}//do not break, fill what we can
			
			//but for the next 2 checks, halt if we run out of blocks/cost
			if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem() .stackSize == 0) {break;}
			
			if(costPerBlock > 0) //do nothing if we cannot pay the cost
			{
				if(Util.drainExp(player, costPerBlock) == false)
				{
					
					break;
				}
			}
			*/

			if(payExpAndCheckValid(world,player,p,costPerBlock) == false){break;}
			
			world.setBlockState(p, placing);
			
			Util.decrHeldStackSize(player);

			Util.playSoundAt(world, pos, placing.getBlock().stepSound.getPlaceSound());
		}
	}

	public static void square(World world, EntityPlayer player, BlockPos pos, IBlockState placing, int radius, int costPerBlock)
	{
		//EntityPlayer player = (EntityPlayer)sender;
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		//World world = player.worldObj;
		 
        boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
        
		//IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

	 
        int x = (int)player.posX;
		int z = (int)player.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;

		int y = (int)player.posY - 1;
		
		BlockPos posCurrent;

		int numPlaced = 0;
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				posCurrent = new BlockPos(xLoop, y, zLoop);
				
				if(payExpAndCheckValid(world,player,posCurrent,costPerBlock) == false){break;}
				 
				if(world.isAirBlock(posCurrent))
				{ 
					world.setBlockState(posCurrent, placing);
					Util.decrHeldStackSize(player);
		 
					Util.playSoundAt(player, pblock.stepSound.getPlaceSound()); 
				} 
			}  
		} //end of the outer loop
   
	}

	public static void stairway(World world, EntityPlayer player,BlockPos position, IBlockState placing, int want, int skip,int costPerBlock)
	{ 
		boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
    
		boolean goVert = true;	
	
		EnumFacing pfacing = Util.getPlayerFacing(player);

        //it starts at eye level, so do down and forward one first
		BlockPos off = player.getPosition().down().offset(pfacing);
		
		for(int i = 1; i < want + 1; i = i + skip)
		{
			if(goVert)
			{
				if(isLookingUp)
					off = off.up();
				else
					off = off.down();
			}
			else
				off = off.offset(pfacing);
			
			goVert = (i % 2 == 0);//alternate between going forward and going vertical
			
			if(payExpAndCheckValid(world,player,off,costPerBlock) == false){break;}
			
			world.setBlockState(off, placing);
			
			Util.decrHeldStackSize(player);
 
			Util.playSoundAt(player, placing.getBlock().stepSound.getPlaceSound());
		}
	}
	
	public static void line(World world, EntityPlayer player,BlockPos pos, IBlockState placing, int want, int skip,int costPerBlock)
	{
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());
 
        boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
        
		BlockPos off;
		EnumFacing efacing = (player.isSneaking()) ? EnumFacing.DOWN : Util.getPlayerFacing(player);
		
		int numPlaced = 0;
		for(int i = 1; i < want + 1; i = i + skip)
		{
			off = player.getPosition().offset(efacing, i);
			
			if(world.isAirBlock(off) == false){break;}
			//halted, do not continue the path
			
			world.setBlockState(off, placing);
			Util.decrHeldStackSize(player);
 
			Util.playSoundAt(player, pblock.stepSound.getPlaceSound());
			
			numPlaced ++;
		}
		
        Util.drainExp(player,numPlaced * costPerBlock);
	}


}
