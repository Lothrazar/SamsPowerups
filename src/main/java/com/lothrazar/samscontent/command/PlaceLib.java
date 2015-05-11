package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class PlaceLib
{

	
	//library of functions/configs that apply to all /place[] commands
	//for all of these, we allow the player to be null
	
	
	public static ArrayList<Block> allowed = new ArrayList<Block>();
	public static String allowedFromConfig = "";

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
	
	//place commands will need: world, player, pos, blockstate
	
	
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
			if(world.isAirBlock(p) == false){continue;}//do not break, fill what we can
			
			world.setBlockState(p, placing);
			
			Util.decrHeldStackSize(player);

			Util.playSoundAt(world, pos, placing.getBlock().stepSound.getPlaceSound());
			
			System.out.println("drain xp "+costPerBlock);
			if(costPerBlock > 0)
			{
				if(Util.drainExp(player, costPerBlock) == false)
					break;
			}
		}
	}
}
