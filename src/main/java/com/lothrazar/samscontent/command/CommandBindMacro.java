package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandBindMacro implements ICommand
{
	public static boolean REQUIRES_OP;  //TODO:CONFIG
	public static String KEY_MACRO = "macro";
	private ArrayList<String> aliases = new ArrayList<String>();

	public CommandBindMacro()
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
		return "bind";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/"+getName();
	}

	@Override
	public List getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)		throws CommandException
	{ 
		EntityPlayer player = (EntityPlayer)sender;
		if(player == null){return;}

		if(args.length == 0)
		{ 
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		String full = "/";
		for(int i = 0; i < args.length; i++)
		{
			full += args[i]+" ";
		}
		
		full = full.replace("//", "/");//in case it is typed in for us
		player.getEntityData().setString(KEY_MACRO, full);

		Util.addChatMessage(player, Util.lang("command.bind.done")+" "+full);
		
		
		System.out.println("bind. = "+full);
	}
	
	public static String getPlayerMacro(EntityPlayer player)
	{
		return player.getEntityData().getString(KEY_MACRO);
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender)
	{ 
		return (REQUIRES_OP) ? sender.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,		BlockPos pos)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
}
