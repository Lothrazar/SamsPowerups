package com.lothrazar.samscontent;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.block.BlockBucketStorage;
import com.lothrazar.samscontent.block.BlockCommandBlockCraftable;
import com.lothrazar.samscontent.block.BlockCropBeetroot;
import com.lothrazar.samscontent.block.BlockFishing;
import com.lothrazar.samscontent.block.BlockShearWool;
import com.lothrazar.samscontent.block.BlockXRay;
import com.lothrazar.samscontent.block.BlockCommandBlockCraftable.CommandType;
import com.lothrazar.util.Reference; 

public class BlockRegistry 
{  
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed; 
	public static BlockFishing block_fishing ;
	public static BlockXRay block_xray ;
	public static BlockShearWool block_shear_sheep;
	public static BlockBucketStorage block_storelava;
	public static BlockBucketStorage block_storewater;
	public static BlockBucketStorage block_storemilk;
	public static BlockBucketStorage block_storeempty;
	public static BlockCropBeetroot beetroot_crop;
	
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	 public static void registerBlock(Block s, String name)
	 {    
		 s.setUnlocalizedName(name); 
		 
		 GameRegistry.registerBlock(s, name);
		  
		 blocks.add(s);
	 }
	
	
	public static void registerBlocks() 
	{  
		/*
		Block lapis_pot = (new BlockFlowerPot()).setHardness(0.0F).setStepSound(Block.soundTypeStone);
		lapis_pot.setCreativeTab(ModSamsContent.tabSamsContent);
		BlockRegistry.registerBlock(lapis_pot, "lapis_pot"); 
		*/
		
		if(ModSamsContent.cfg.beetroot)
		{
			beetroot_crop = (BlockCropBeetroot) new BlockCropBeetroot();

			BlockRegistry.registerBlock(beetroot_crop, "beetroot_crop"); 
		}
		 
		if(ModSamsContent.cfg.storeBucketsBlock)
		{
			BlockRegistry.block_storewater = new BlockBucketStorage(Items.water_bucket);  
			registerBlock(BlockRegistry.block_storewater, "block_storewater");

			BlockRegistry.block_storemilk = new BlockBucketStorage(Items.milk_bucket);  
			BlockRegistry.registerBlock(BlockRegistry.block_storemilk, "block_storemilk");
		 
			BlockRegistry.block_storelava = new BlockBucketStorage(Items.lava_bucket);  
			BlockRegistry.registerBlock(BlockRegistry.block_storelava, "block_storelava");	  
	
			GameRegistry.registerTileEntity(com.lothrazar.samscontent.tileentity.TileEntityBucketStorage.class, Reference.MODID);
		
			BlockRegistry.block_storeempty = new BlockBucketStorage(null); 		
			BlockRegistry.block_storeempty.setCreativeTab(ModSamsContent.tabSamsContent); 
			BlockRegistry.registerBlock(BlockRegistry.block_storeempty, "block_storeempty");
			
			BlockRegistry.block_storeempty.addRecipe();
			
		}
		
		if(ModSamsContent.cfg.shearSheepBlock)
		{
			BlockRegistry.block_shear_sheep = new BlockShearWool(); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_shear_sheep, "block_shear_sheep");

			BlockShearWool.addRecipe();
		}
		
		if(ModSamsContent.cfg.fishingNetBlock)
		{
			BlockRegistry.block_fishing = new BlockFishing(); 
			
			registerBlock(BlockRegistry.block_fishing, "block_fishing");

			BlockFishing.addRecipe();
		}
  
		if(ModSamsContent.cfg.weatherBlock) 
		{
			BlockRegistry.command_block_weather = new BlockCommandBlockCraftable(CommandType.Weather);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_weather,"command_block_weather");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_weather,new ItemStack(Items.water_bucket));
		}
		 
		if(ModSamsContent.cfg.teleportSpawnBlock) 
		{ 
			BlockRegistry.command_block_tpspawn = new BlockCommandBlockCraftable(CommandType.TeleportSpawn);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpspawn,"command_block_tpspawn");
	
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpspawn,new ItemStack(Items.ender_eye));
		}

		if(ModSamsContent.cfg.teleportBedBlock) 
		{ 
			BlockRegistry.command_block_tpbed = new BlockCommandBlockCraftable(CommandType.TeleportBed);
	 
			BlockRegistry.registerBlock(BlockRegistry.command_block_tpbed,"command_block_tpbed");
			
			BlockCommandBlockCraftable.addRecipe(BlockRegistry.command_block_tpbed,new ItemStack(Items.ender_pearl));
		} 
		
		if(ModSamsContent.cfg.xRayBlock)
		{ 
			BlockRegistry.block_xray = new BlockXRay(); 
			
			BlockRegistry.registerBlock(BlockRegistry.block_xray,"block_xray");
 
			BlockXRay.addRecipe();
		}
	}
}
