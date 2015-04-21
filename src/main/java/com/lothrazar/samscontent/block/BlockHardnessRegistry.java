package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModMain;

import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{ 
	public static void registerChanges()
	{ 
		Blocks.obsidian.setHardness(ModMain.cfg.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModMain.cfg.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModMain.cfg.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModMain.cfg.spawnerHardness);//v=5
		Blocks.redstone_ore.setHardness(ModMain.cfg.redstoneOreHardness); //v=3
		
		if(ModMain.cfg.harvestGlassPickaxe)
		{ 
			Blocks.glass.setHarvestLevel("pickaxe", 3);
		}
	}
}
