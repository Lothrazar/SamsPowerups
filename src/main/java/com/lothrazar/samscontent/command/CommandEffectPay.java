package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandEffectPay implements ICommand
{
	public static boolean REQUIRES_OP;  //TODO:CONFIG
	public static int XP_COST_PER_SECOND;
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandEffectPay()
	{
		this.aliases.add(getName().toUpperCase());
		this.aliases.add("efp");
		this.aliases.add("EFP");
	}

	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "effectpay";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/"+getName() + " <potionid> <seconds>";
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

		if(args.length == 0)
		{ 
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
//
		
		
		
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender)
	{
		return (REQUIRES_OP) ? sender.canUseCommand(2, this.getName()) : true; 
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
