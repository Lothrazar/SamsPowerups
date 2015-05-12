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
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CommandPlaceLine implements ICommand
{
	public static boolean REQUIRES_OP=false;  
	public static int XP_COST_PER_PLACE = 1; 
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandPlaceLine()
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
		return "placeline";
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
		
		int want = player.inventory.getCurrentItem().stackSize;
		
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());
		
        if(args.length > 0 && args[0] != null)
        {
        	want =  Math.min(Integer.parseInt(args[0]), player.inventory.getCurrentItem().stackSize);
        }
		 
        int skip = 1;
        if(args.length > 1 && args[1] != null)
        {
        	skip =  Math.max(Integer.parseInt(args[1]), 1);
        }
        
        PlaceLib.line(player.worldObj, player, player.getPosition(), placing, want, skip, XP_COST_PER_PLACE);
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
