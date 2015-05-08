package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.util.Util;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CommandPlaceFloor implements ICommand
{
	public static boolean REQUIRES_OP=false;  
	public static int XP_COST_PER_PLACE = 1; 
	public static int RADIUS_MAX = 8; //from config file
	private ArrayList<String> aliases = new ArrayList<String>();
	public CommandPlaceFloor()
	{
		this.aliases.add(getName().toUpperCase());
	}
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "placefloor";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/"+getName() + "<qty>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args)	throws CommandException 
	{
		EntityPlayer player = (EntityPlayer)sender;
		
		if(player == null){return;}//was sent by command block or something, ignore it
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0){return;}
		
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null){return;}
			
		World world = player.worldObj;
		 
        boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
        
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		//tick handlers?http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/1430001-solved-1-5-forge-minecrafts-equivalent-to-thread
		int radius = 2;
        if(args.length > 0 && args[0] != null)
        	radius =  Math.min(Integer.parseInt(args[0]), RADIUS_MAX);
      
        
        //
        
		 /*
        int skip = 1;
        if(args.length > 1 && args[1] != null)
        	skip =  Math.max(Integer.parseInt(args[1]), 1);
		 
        */
        
       //TODO: test with chests/torches/signs/etc
        int x = (int)player.posX;
		int z = (int)player.posZ;
		
		//search in a cube
		int xMin = x - RADIUS_MAX;
		int xMax = x + RADIUS_MAX; 
		int zMin = z - RADIUS_MAX;
		int zMax = z + RADIUS_MAX;

		int y = (int)player.posY - 1;
		
		BlockPos posCurrent;

		int numPlaced = 0;
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				if(player.inventory.getCurrentItem() == null 
						|| player.inventory.getCurrentItem().stackSize == 0){break;}
				
				posCurrent = new BlockPos(xLoop, y, zLoop);
				//IBlockState bs = world.getBlockState(posCurrent);
				//Block blockCheck = bs.getBlock(); 

				if(world.isAirBlock(posCurrent))
				{ 
					world.setBlockState(posCurrent, placing);
					Util.decrHeldStackSize(player);
		 
					Util.playSoundAt(player, pblock.stepSound.getPlaceSound());
					numPlaced ++;
				} 
			}  
		} //end of the outer loop
  
        Util.tryDrainXp(player,numPlaced * XP_COST_PER_PLACE);
	}
	
	@Override
	public boolean canCommandSenderUse(ICommandSender ic) 
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,			BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{

		return false;
	}
}
