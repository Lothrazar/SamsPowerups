package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandPlaceCircle  implements ICommand
{
	public static boolean REQUIRES_OP;  
	public static int XP_COST_PER_PLACE; 
	public static int RADIUS_MAX = 8; //TODOfrom config file
	private ArrayList<String> aliases = new ArrayList<String>();
	private static ArrayList<Block> allowed = new ArrayList<Block>();
	public static String allowedFromConfig = "";

	public CommandPlaceCircle()
	{
		this.aliases.add(getName().toUpperCase());
	}
	
	@Override
	public int compareTo(Object o) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "placecircle";
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
	public void execute(ICommandSender sender, String[] args)		throws CommandException 
	{
		// based on http://stackoverflow.com/questions/1022178/how-to-make-a-circle-on-a-grid
		//also http://rosettacode.org/wiki/Bitmap/Midpoint_circle_algorithm
				
		EntityPlayer player = (EntityPlayer)sender;
		
		if(player == null){return;}//was sent by command block or something, ignore it
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0)
		{
			Util.addChatMessage(player, "command.place.empty"); 
			return;
		}
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null){return;}
			
		if(PlaceCmdLib.allowed.size() > 0 && PlaceCmdLib.allowed.contains(pblock) == false)
		{ 
			Util.addChatMessage(player, "command.place.notallowed"); 
			return;
		}
		
		World world = player.worldObj;
		
		BlockPos posCurrent;
		int centerX = (int)player.posX;
		int centerZ = (int)player.posZ;
		
		int height = (int)player.posY - 1;
		int radius = 2;
        if(args.length > 0 && args[0] != null)
        {
        	radius =  Math.min(Integer.parseInt(args[0]), RADIUS_MAX);
        }
		
		int z = radius;
		int x = 0;
		int d = 2 - (2 * radius);
		
		ArrayList<BlockPos> pos = new ArrayList<BlockPos>(); 
		
		do 
		{
			pos.add(new BlockPos(centerX + x, height, centerZ + z)); 
	        pos.add(new BlockPos(centerX + x, height, centerZ - z));
	        pos.add(new BlockPos(centerX - x, height, centerZ + z));
	        pos.add(new BlockPos(centerX - x, height, centerZ - z));
	        pos.add(new BlockPos(centerX + z, height, centerZ + x));
	        pos.add(new BlockPos(centerX + z, height, centerZ - x));
	        pos.add(new BlockPos(centerX - z, height, centerZ + x));
	        pos.add(new BlockPos(centerX - z, height, centerZ - x));
	        
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
		
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		int numPlaced = 0;
		
		for(BlockPos p : pos)
		{
			if(world.isAirBlock(p))
			{
				world.setBlockState(p, placing);
				Util.decrHeldStackSize(player);
	 
				Util.playSoundAt(player, pblock.stepSound.getPlaceSound());
				numPlaced++;
			}
		}
		
        Util.tryDrainXp(player,numPlaced * XP_COST_PER_PLACE);
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) 
	{
		return (REQUIRES_OP) ? sender.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,	BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}
}
