package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{ 
	public static void registerChanges()
	{ 
		Blocks.obsidian.setHardness(ModSamsContent.configSettings.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModSamsContent.configSettings.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModSamsContent.configSettings.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModSamsContent.configSettings.spawnerHardness);//v=5
		Blocks.redstone_ore.setHardness(ModSamsContent.configSettings.redstoneOreHardness); //v=3
		
		if(ModSamsContent.configSettings.harvestGlassPickaxe)
		{ 
			Blocks.glass.setHarvestLevel("pickaxe", 3);
		}
	}
}
