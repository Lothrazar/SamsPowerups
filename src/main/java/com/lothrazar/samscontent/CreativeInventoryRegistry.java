package com.lothrazar.samscontent;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class CreativeInventoryRegistry 
{
	public static void registerTabImprovements() 
	{ 
		//http://minecraft.gamepedia.com/Creative#Missing_blocks_and_items
		
		if(ModMain.cfg.mushroomBlocksCreativeInventory)
		{
			Blocks.red_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
			Blocks.brown_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
		}
		
		if(ModMain.cfg.barrierCreativeInventory)
			Blocks.barrier.setCreativeTab(CreativeTabs.tabDecorations); 

		if(ModMain.cfg.dragonEggCreativeInventory)
			Blocks.dragon_egg.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModMain.cfg.farmlandCreativeInventory)
			Blocks.farmland.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModMain.cfg.spawnerCreativeInventory)
			Blocks.mob_spawner.setCreativeTab(CreativeTabs.tabDecorations);  
	} 
}
