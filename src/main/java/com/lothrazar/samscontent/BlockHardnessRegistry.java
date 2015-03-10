package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{

	public static void registerChanges()
	{

		//obs hardness is 50, the biggest by far. ender chest is 22.5
		//Monster Spawner	5   hardness. //TODO:WHAT was the old 1.7 beta hardness?
		//TOOD: config file for each? ores (diamond/emerald) increase hardness 
		 
		Blocks.obsidian.setHardness(ModLoader.configSettings.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModLoader.configSettings.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModLoader.configSettings.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModLoader.configSettings.spawnerHardness);//v=5
	}
}
