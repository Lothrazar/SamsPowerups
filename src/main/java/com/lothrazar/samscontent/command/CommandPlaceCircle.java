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
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandPlaceCircle  implements ICommand
{
	public static boolean REQUIRES_OP;   
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
		return "/"+getName() + " <radius>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)		throws CommandException 
	{
		if(PlaceLib.canSenderPlace(sender) == false) {return;}

		EntityPlayer player = (EntityPlayer)sender;
		
		if(args.length == 0)
		{ 
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
		ItemStack held = player.inventory.getCurrentItem();

		IBlockState placing = Block.getBlockFromItem(held.getItem()).getStateFromMeta(held.getMetadata());

		int radius = 2;
		try
		{
        	radius = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
	
    	if(radius > RADIUS_MAX) radius = RADIUS_MAX;
    	if(radius < RADIUS_MIN) radius = RADIUS_MIN;
       
		PlaceLib.circle(player.worldObj, player, player.getPosition(), placing, radius);
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
