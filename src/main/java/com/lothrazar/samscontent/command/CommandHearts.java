package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandHearts implements ICommand
{
	private static boolean REQUIRES_OP = true; // always
	private ArrayList<String> aliases = new ArrayList<String>();

	@Override
	public int compareTo(Object arg0)
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return "sethearts";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/"+getName()+" <hearts>";
	}

	@Override
	public List getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)		throws CommandException
	{ 
		int health = 0;
		try
		{
			health = Integer.parseInt(args[0]);
		}
		catch (Exception e)
		{
			Util.addChatMessage(getCommandUsage(sender));
			return;
		}
		
		if(args.length > 2)
		{
			String target = args[2];
			
			System.out.println("target");
			
			EntityPlayer ptarget = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(target);
			
			if(ptarget == null)
			{
				Util.addChatMessage(getCommandUsage(sender));
				return;
			}

			Util.setMaxHealth(ptarget, health*2);
		}
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
