package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;

public class PlaceCmdLib
{

	//library of functions/configs that apply to all /place[] commands

	public static ArrayList<Block> allowed = new ArrayList<Block>();
	public static String allowedFromConfig = "";

	public static void translateCSV()
	{
		//do this on the fly, could be items not around yet during config change
		if(PlaceCmdLib.allowed.size() == 0)
			PlaceCmdLib.allowed = Util.getBlockListFromCSV(PlaceCmdLib.allowedFromConfig); 
	}

	public static boolean isAllowed(Block pblock)
	{
		translateCSV();
		
		return PlaceCmdLib.allowed.size() == 0 || PlaceCmdLib.allowed.contains(pblock);
	}
	
	
	
}
