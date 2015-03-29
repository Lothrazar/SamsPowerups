package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
//import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class CommandWorldHome  implements ICommand
{
	public static boolean REQUIRES_OP; 
	
	private ArrayList<String> aliases = new ArrayList<String>();
	
	@Override
	public int compareTo(Object o)
	{ 
		return 0;
	}

	@Override
	public String getName()
	{ 
		return "worldhome";
	}

	@Override
	public String getCommandUsage(ICommandSender ic)
	{ 
		return "/" + getName();
	}

	@Override
	public List getAliases()
	{ 
		return aliases;
	}

	@Override
	public void execute(ICommandSender ic, String[] args)
	{
		//EntityPlayer player = ((EntityPlayer)ic); 
		//World world = player.worldObj; 
		
		World world = ic.getCommandSenderEntity().worldObj; 
		EntityPlayer player = world.getClosestPlayer(ic.getPosition().getX(), ic.getPosition().getY(), ic.getPosition().getZ(), 5);
 
		if(player.dimension != 0)
		{
			 player.addChatMessage(new ChatComponentTranslation("Can only teleport to worldhome in the overworld"));
			 return;
		}
		
		//this tends to always get something at y=64, regardless if there is AIR or not 
		//so we need to safely push the player up out of any blocks they are in
		
		SamsUtilities.teleportWallSafe(player, world, world.getSpawnPoint()); 
		world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
	}
 
	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}
 
	@Override
	public boolean isUsernameIndex(String[] ic, int args)
	{ 
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	} 
}
