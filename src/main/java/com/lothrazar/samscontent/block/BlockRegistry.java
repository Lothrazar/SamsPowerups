package com.lothrazar.samscontent.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.block.BlockCommandBlockCraftable.CommandType;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;

public class BlockRegistry 
{ 
	public static BlockCommandBlockCraftable command_block_regen;
	public static BlockCommandBlockCraftable command_block_mobgrief;
	public static BlockCommandBlockCraftable command_block_firetick;
	public static BlockCommandBlockCraftable command_block_daycycle;
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed; 
	public static BlockFishing block_fishing ;
	public static BlockXRay block_xray ;
	public static BlockShearWool block_spike;
	public static BlockBucketStorage block_storelava;
	public static BlockBucketStorage block_storewater;
	public static BlockBucketStorage block_storemilk;
	public static BlockCropBeetroot beetrootCrop;
	public static void registerBlocks() 
	{  
		if(ModLoader.configSettings.beetroot)
		{
			beetrootCrop = (BlockCropBeetroot) new BlockCropBeetroot();

			SamsRegistry.registerBlock(beetrootCrop, "beetroot_crop"); 
		}
		 
		if(ModLoader.configSettings.storeWaterBlock)
		{
			BlockRegistry.block_storewater = new BlockBucketStorage(Items.water_bucket); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_storewater, "block_storewater");

			BlockRegistry.block_storewater.addRecipe();
		}
		if(ModLoader.configSettings.storeMilkBlock)
		{
			BlockRegistry.block_storemilk = new BlockBucketStorage(Items.milk_bucket); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_storemilk, "block_storemilk");

			BlockRegistry.block_storemilk.addRecipe();
		}
		if(ModLoader.configSettings.storeLavaBlock)
		{
			BlockRegistry.block_storelava = new BlockBucketStorage(Items.lava_bucket); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_storelava, "block_storelava");
	  
			BlockRegistry.block_storelava.addRecipe();
		}

		if(ModLoader.configSettings.storeLavaBlock || ModLoader.configSettings.storeWaterBlock)
		{
			GameRegistry.registerTileEntity(com.lothrazar.samscontent.block.TileEntityBucketStorage.class, Reference.MODID);
			
		}
		if(ModLoader.configSettings.shearSheepBlock)
		{
			BlockRegistry.block_spike = new BlockShearWool(); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_spike, "block_spike");

			BlockShearWool.addRecipe();
		}
		
		if(ModLoader.configSettings.fishingNetBlock)
		{
			BlockRegistry.block_fishing = new BlockFishing(); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_fishing,BlockFishing.name);

			BlockFishing.addRecipe();
		}
  
		if(ModLoader.configSettings.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
		 
		if(ModLoader.configSettings.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
		}

		if(ModLoader.configSettings.teleportBedBlock) 
		{ 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.ender_pearl));
		}
		
		if(ModLoader.configSettings.gameruleBlockRegen)
		{ 
			BlockRegistry.command_block_regen = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.naturalRegeneration);
	 
			SamsRegistry.registerBlock(BlockRegistry.command_block_regen, "command_block_regen");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_regen,new ItemStack(Items.golden_apple) );  
		} 
		
		if(ModLoader.configSettings.gameruleBlockDaylight)
		{
			BlockRegistry.command_block_daycycle = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doDaylightCycle);
			 
			SamsRegistry.registerBlock(BlockRegistry.command_block_daycycle, "command_block_daycycle");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_daycycle,new ItemStack( Blocks.glowstone) ); 
		}
		
		if(ModLoader.configSettings.gameruleBlockFiretick)
		{ 
			BlockRegistry.command_block_firetick = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.doFireTick);
 
			SamsRegistry.registerBlock(BlockRegistry.command_block_firetick, "command_block_firetick");

			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_firetick,new ItemStack( Items.lava_bucket) ); 
		}
		
		if(ModLoader.configSettings.gameruleBlockMobgrief)
		{ 
			BlockRegistry.command_block_mobgrief = new BlockCommandBlockCraftable(CommandType.Gamerule,Reference.gamerule.mobGriefing);
 
			SamsRegistry.registerBlock(BlockRegistry.command_block_mobgrief, "command_block_mobgrief");
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_mobgrief,new ItemStack( Blocks.tnt) );  
		}
 
		if(ModLoader.configSettings.xRayBlock)
		{ 
			BlockRegistry.block_xray = new BlockXRay(); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_xray,"block_xray");
 
			BlockXRay.addRecipe();
		}
	}
}
