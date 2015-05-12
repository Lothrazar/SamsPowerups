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
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CommandPlaceStair implements ICommand
{
	public static boolean REQUIRES_OP;   
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandPlaceStair()
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
		return "placestair";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/"+getName() + " <qty> [skip]";
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
		ItemStack held = player.inventory.getCurrentItem();
		
		IBlockState placing = Block.getBlockFromItem(held.getItem()).getStateFromMeta(held.getMetadata());

		if(args.length == 0)
		{ 
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		int want = held.stackSize;
        try
		{
        	want =  Math.min(Integer.parseInt(args[0]), held.stackSize);
		}
		catch (NumberFormatException e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		 
        int skip = 1;
        if(args.length > 1 && args[1] != null)
        {
    		try
    		{
            	skip =  Math.max(Integer.parseInt(args[1]), 1);
    		}
    		catch (NumberFormatException e)
    		{
    			Util.addChatMessage(player, getCommandUsage(sender));
    			return;
    		}
        }

        PlaceLib.stairway(player.worldObj,player,player.getPosition(),placing,want,skip);       
	}
	
	@Override
	public boolean canCommandSenderUse(ICommandSender ic) 
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) 
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{
		return false;
	}
}
