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
import net.minecraft.util.ChatComponentTranslation;
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
		return "/"+getName() + " <radius>";
	}

	@Override
	public List getAliases() 
	{
		return aliases;
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args)	throws CommandException 
	{
		if(PlaceLib.canSenderPlace(sender) == false) {return;}

		EntityPlayer player = (EntityPlayer)sender;
		
		if(args.length == 0)
		{ 
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		int radius = 0;
		
		try
		{
			radius =  Math.min(Integer.parseInt(args[0]), RADIUS_MAX);
		}
		catch (NumberFormatException e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		World world = player.worldObj;
		 
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		PlaceLib.square(player.worldObj, player, player.getPosition().down(), placing, radius, XP_COST_PER_PLACE);
	}
	
	@Override
	public boolean canCommandSenderUse(ICommandSender ic) 
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}
}
