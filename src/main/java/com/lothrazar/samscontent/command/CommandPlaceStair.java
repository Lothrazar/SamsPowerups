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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CommandPlaceStair implements ICommand
{
	public static boolean REQUIRES_OP;  
	public static int XP_COST_PER_PLACE; 
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
		return "/"+getName() + "<qty> <skip=1>";
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
		
		if(player == null){return;}//was sent by command block or something, ignore it
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0)
		{
			Util.addChatMessage(player, "command.place.empty"); 
			return;
		}
		
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null)
		{
			Util.addChatMessage(player, "command.place.empty");//TODO: lang file should get this
			return;
		}


		if(PlaceCmdLib.isAllowed(pblock) == false)
		{ 
			Util.addChatMessage(player, "command.place.notallowed"); 
			return;
		}
		
		World world = player.worldObj;
	 
        boolean isLookingUp = (player.getLookVec().yCoord >= 0);//TODO: use this somehow? to place up/down? 
        
		IBlockState placing = pblock.getStateFromMeta(player.inventory.getCurrentItem().getMetadata());

		int want = player.inventory.getCurrentItem().stackSize;
        if(args.length > 0 && args[0] != null)
        {
        	want =  Math.min(Integer.parseInt(args[0]), player.inventory.getCurrentItem().stackSize);
        }
        int skip = 1;
        if(args.length > 1 && args[1] != null)
        {
        	skip =  Math.max(Integer.parseInt(args[1]), 1);
        }
		boolean goUp = true;	
	
		EnumFacing pfacing = Util.getPlayerFacing(player);
		//System.out.println(efacing.toString());

        //it starts at eye level, so do down and forward one first
		BlockPos off = player.getPosition().down().offset(pfacing);
		
		int numPlaced = 0;
		for(int i = 1; i < want + 1; i = i + skip)
		{
			if(goUp)
			{
				if(isLookingUp)
					off = off.up();
				else
					off = off.down();
			}
			else
				off = off.offset(pfacing);
			
			goUp = (i % 2 == 0);

			if(world.isAirBlock(off) == false){break;}
			//halted, do not continue the path
			
			world.setBlockState(off, placing);
			Util.decrHeldStackSize(player);
 
			Util.playSoundAt(player, pblock.stepSound.getPlaceSound());
			
			numPlaced ++;
		}
		
        Util.tryDrainXp(player,numPlaced * XP_COST_PER_PLACE);
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
