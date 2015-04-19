package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{ 
	public static void registerChanges()
	{ 
		Blocks.obsidian.setHardness(ModSamsContent.cfg.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModSamsContent.cfg.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModSamsContent.cfg.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModSamsContent.cfg.spawnerHardness);//v=5
		Blocks.redstone_ore.setHardness(ModSamsContent.cfg.redstoneOreHardness); //v=3
		
		if(ModSamsContent.cfg.harvestGlassPickaxe)
		{ 
			Blocks.glass.setHarvestLevel("pickaxe", 3);
		}
	}
}
