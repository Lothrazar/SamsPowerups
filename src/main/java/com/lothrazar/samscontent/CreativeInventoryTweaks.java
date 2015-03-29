package com.lothrazar.samscontent;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

public class CreativeInventoryTweaks 
{
	public static void registerTabImprovements() 
	{ 
		//http://minecraft.gamepedia.com/Creative#Missing_blocks_and_items
		
		if(ModSamsContent.configSettings.mushroomBlocksCreativeInventory)
		{
			Blocks.red_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
			Blocks.brown_mushroom_block.setCreativeTab(CreativeTabs.tabDecorations); 
		}
		
		if(ModSamsContent.configSettings.barrierCreativeInventory)
			Blocks.barrier.setCreativeTab(CreativeTabs.tabDecorations); 

		if(ModSamsContent.configSettings.dragonEggCreativeInventory)
			Blocks.dragon_egg.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModSamsContent.configSettings.farmlandCreativeInventory)
			Blocks.farmland.setCreativeTab(CreativeTabs.tabDecorations); 
		
		if(ModSamsContent.configSettings.spawnerCreativeInventory)
			Blocks.mob_spawner.setCreativeTab(CreativeTabs.tabDecorations);  
	} 
}
