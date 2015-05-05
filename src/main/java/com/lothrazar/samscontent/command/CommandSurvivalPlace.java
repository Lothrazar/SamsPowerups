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

public class CommandSurvivalPlace implements ICommand
{
	public static boolean REQUIRES_OP=false; //TODO: FROM CONFIG
	private ArrayList<String> aliases = new ArrayList<String>();
	public CommandSurvivalPlace()
	{
		this.aliases.add("PLACE");
	}
	@Override
	public int compareTo(Object arg0) 
	{
		return 0;
	}

	@Override
	public String getName() 
	{
		return "place";
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
	public static final int MaxPlace = 64;
	@Override
	public void execute(ICommandSender sender, String[] args)	throws CommandException 
	{
		// TODO Auto-generated method stub
		
		EntityPlayer player = (EntityPlayer)sender;
		
		
		if(player == null){return;}//was sent by commadn block or something, ignore it
		
		
		if(player.inventory.getCurrentItem() == null || player.inventory.getCurrentItem().stackSize == 0){return;}
		
		//TODO: maybe delays; http://www.minecraftforge.net/forum/index.php?topic=22903.0

 
		Block pblock = Block.getBlockFromItem(player.inventory.getCurrentItem().getItem());

		if(pblock == null){return;}
			
		World world = player.worldObj;
		
	// http://www.minecraftforge.net/forum/index.php?topic=6514.0
		int max = Math.min(MaxPlace, player.inventory.getCurrentItem().stackSize);
		
		///
		int yaw = (int)player.rotationYaw;

        if (yaw<0)              //due to the yaw running a -360 to positive 360
           yaw+=360;    //not sure why it's that way

        yaw+=22;     //centers coordinates you may want to drop this line
        yaw%=360;  //and this one if you want a strict interpretation of the zones

        int facing = yaw/45;   //  360degrees divided by 45 == 8 zones
       // System.out.println("Yaw is " + yaw + "facing is " + facing);
/*
0 = North
1 = North East
2 = East
3 = South East
4 = South
5 = South West
6 = West
7 = North West
*/
		  
		BlockPos off;
		IBlockState placing = pblock.getDefaultState();
		EnumFacing efacing = (player.isSneaking()) ? EnumFacing.DOWN : EnumFacing.getHorizontal( facing/2 );
		
		for(int i = 0; i < max; i++)
		{
			off = player.getPosition().offset(efacing, i);
			
			if(world.isAirBlock(off))
			{
				world.setBlockState(off, placing);
				Util.decrHeldStackSize(player);
			}
		}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender ic) 
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,			BlockPos pos) {

		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) 
	{

		return false;
	}

}
