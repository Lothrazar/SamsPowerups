package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandPing implements ICommand
{
	public static boolean REQUIRES_OP;  //TODO:CONFIG
	private ArrayList<String> aliases = new ArrayList<String>();

	public CommandPing()
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
		return "ping";
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
	public void execute(ICommandSender sender, String[] args)	throws CommandException
	{
		EntityPlayer player = (EntityPlayer)sender;
		if(player == null){return;}
		
		
		Util.addChatMessage(player, Util.posToString(player.getPosition()));
		
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
