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

public class CommandBindMacro implements ICommand
{
	public static boolean REQUIRES_OP = false;//not in config on purpose
	private static String KEY_MACRO_base = "macro";
	public static String KEY_MACRO1 = "macro1";
	public static String KEY_MACRO2 = "macro2";
	public static String KEY_MACRO3 = "macro3";
	public static String KEY_MACRO4 = "macro4";
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
		return "/"+getName() +" <index> <command> <args>";
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
		
		int mac = 0;
		
		try
		{
			mac = Integer.parseInt(args[0]);
		}
		catch (Exception e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
		String full = "/";
		for(int i = 1; i < args.length; i++)
		{
			full += args[i]+" ";
		}
		 
		full = full.replace("//", "/");//in case it is typed in for us
		player.getEntityData().setString(KEY_MACRO_base + mac, full);

		Util.addChatMessage(player, Util.lang("command.bind.done")+" "+full);
		
		//System.out.println(KEY_MACRO_base + mac);
		
	}
	  
	public static String getPlayerMacro(EntityPlayer player,String macro)
	{
		return player.getEntityData().getString(macro);
	}

	public static void tryExecuteMacro(EntityPlayer player,String macro)
	{
		String cmd = CommandBindMacro.getPlayerMacro(player,macro);

		//System.out.println("execute : "+KEY_MACRO_base + macro);
		
		if(cmd==null||cmd.isEmpty())
		{
			Util.addChatMessage(player, Util.lang("command.bind.empty"));
			return;
		}
		
		Util.addChatMessage(player, "."+cmd);
		
		MinecraftServer.getServer().getCommandManager().executeCommand(player, cmd);
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
