package com.lothrazar.samsblocks;

import java.util.ArrayList;

import com.lothrazar.samsblocks.BlockCommandBlockCraftable.CommandType;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry; 
 

public class BlockRegistry 
{  
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed; 

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public static void registerBlock(Block s, String name)
	{    
		s.setUnlocalizedName(name); 
		 
		GameRegistry.registerBlock(s, name);
		  
		blocks.add(s);
	}

	public static void registerBlocks() 
	{  
		
		if(ModBlocks.cfg.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
		 
		if(ModBlocks.cfg.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
		}

		if(ModBlocks.cfg.teleportBedBlock) 
		{ 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.bed));
		} 
	}
}
