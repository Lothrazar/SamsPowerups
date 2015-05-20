package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class CommandEffectPay implements ICommand
{
	public static boolean REQUIRES_OP;  
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
		return "/"+getName() + " <potionid> <seconds> [lvl]";
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
		
 
		int pid = 0;
		try
		{
        	pid = Integer.parseInt(args[0]);
        	
    		Potion p = Potion.potionTypes[pid];
    		
    		if(p == null)//if invalid id
    		{
    			Util.addChatMessage(player, getCommandUsage(sender));
    			return;
    		}
		}
		catch (Exception e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}

		 
		int sec = 0;
		try
		{
        	sec = Integer.parseInt(args[1]);
		}
		catch (Exception e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
		int lvl = 1;
		try
		{
			lvl = Integer.parseInt(args[2]);
		}
		catch (Exception e)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
		if(pid < 0 || sec <= 0 || lvl < 1)
		{
			Util.addChatMessage(player, getCommandUsage(sender));
			return;
		}
		
		int xpCost = sec * XP_COST_PER_SECOND * lvl;
		
		lvl--;//previously, it cannot be smaller than 1, but it could be 1
		
		if(Util.getExpTotal(player) < xpCost)
		{
			Util.addChatMessage(player, Util.lang("command.effect.xp")+" "+ xpCost);
			return;
		}
		
		Util.drainExp(player, xpCost);
		
		int ticks = sec * Reference.TICKS_PER_SEC;
	 
		player.addPotionEffect(new PotionEffect(pid,ticks,lvl));
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
