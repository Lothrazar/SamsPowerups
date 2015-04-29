package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List; 

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper; 

import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class CommandSearchSpawner implements ICommand
{ 
	public static boolean REQUIRES_OP;  
	
	public CommandSearchSpawner()
	{
		aliases.add("SEARCHSPAWNER");
		aliases.add("searchdungeon"); 
		aliases.add("SEARCHDUNGEON");  
		aliases.add("searchs"); 
		aliases.add("searchd"); 
	}

	@Override
	public int compareTo(Object arg0) 
	{ 
		return 0;
	}

	@Override
	public String getName() 
	{ 
		return "searchspawner";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{ 
		return "/" + getName() + " [radius]";
	}

	private ArrayList<String> aliases = new ArrayList<String>();
	@Override
	public List getAliases() 
	{ 
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException 
	{ 
		EntityPlayer player = (EntityPlayer)sender;
		int radius = 0;
		if(args.length > 0)
		{
			radius = Integer.parseInt(args[0]);
		}
		
		if(radius > 128) { radius = 128; }//Maximum // 
		if(radius <= 0 ) { radius = 64;  }//default
		
		BlockPos found = Util.findClosestBlock(player, Blocks.mob_spawner, radius);
		
		String m = "None Found with radius "+radius;
		
		if(found != null)
		{ 
			m = "Found at : "+Util.getCoordsOrReduced(player, found);
		}
		
		((EntityPlayer)sender).addChatMessage(new ChatComponentTranslation( m )); 
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
