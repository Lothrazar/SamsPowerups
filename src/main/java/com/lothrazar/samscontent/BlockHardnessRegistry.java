package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{

	public static void registerChanges()
	{

		//obs hardness is 50, the biggest by far. ender chest is 22.5
		//Monster Spawner	5   hardness. //TODO:WHAT was the old 1.7 beta hardness?
		//TOOD: config file for each? ores (diamond/emerald) increase hardness 
		
		Blocks.obsidian.setHardness(10);//vanilla = 50
		Blocks.diamond_ore.setHardness(10);//v=3
		Blocks.emerald_ore.setHardness(10); //v=3
		Blocks.mob_spawner.setHardness(50);//v=5
	}
}
