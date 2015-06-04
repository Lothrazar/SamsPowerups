package com.lothrazar.samsnature;


import net.minecraft.init.Blocks;

public class BlockHardnessRegistry 
{ 
	public static void registerChanges()
	{ 
		Blocks.obsidian.setHardness(ModNature.cfg.obsidianHardness);//vanilla = 50
		Blocks.diamond_ore.setHardness(ModNature.cfg.diamondOreHardness);//v=3
		Blocks.emerald_ore.setHardness(ModNature.cfg.emeraldOreHardness); //v=3
		Blocks.mob_spawner.setHardness(ModNature.cfg.spawnerHardness);//v=5
		Blocks.redstone_ore.setHardness(ModNature.cfg.redstoneOreHardness); //v=3
		
		if(ModNature.cfg.harvestGlassPickaxe)
		{ 
			Blocks.glass.setHarvestLevel("pickaxe", 3);
		}
	}
}
