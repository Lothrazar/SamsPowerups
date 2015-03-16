package com.lothrazar.samscontent;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{ 
	public static void registerChanges()
	{ 
		Blocks.obsidian.setHardness(ModLoader.configSettings.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModLoader.configSettings.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModLoader.configSettings.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModLoader.configSettings.spawnerHardness);//v=5
	}
}
