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
	public static int XP_COST_PER_PLACE=7; //TODO from config file
	public static int RADIUS_MAX = 10; //TODO from config file
	public static int RADIUS_MIN = 2; //TODO from config file
	private ArrayList<String> aliases = new ArrayList<String>();
  
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
				
		if(PlaceLib.canSenderPlace(sender) == false) {return;}

		EntityPlayer player = (EntityPlayer)sender;

		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		int radius = 2;
        if(args.length > 0 && args[0] != null)
        {
        	radius = Integer.parseInt(args[0]);
        	if(radius > RADIUS_MAX) radius = RADIUS_MAX;
        	if(radius < RADIUS_MIN) radius = RADIUS_MIN;
        }
                
		PlaceLib.circle(sender.getEntityWorld(), player, player.getPosition(), placing, radius, XP_COST_PER_PLACE);

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
