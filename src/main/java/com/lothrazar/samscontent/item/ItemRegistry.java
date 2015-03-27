package com.lothrazar.samscontent.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.block.BlockRegistry;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference; 

public class ItemRegistry 
{ 
	public static ItemEnderBook itemEnderBook = null;
	public static ItemWandBuilding wandBuilding;
	public static ItemWandChest wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemWandHarvest wandHarvest;
	public static ItemWandTransform wandTransform; 
	public static ItemWandLivestock wandLivestock;
	public static ItemWandProspect wandProspect;  
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star;
	public static ItemWandFire wandFire;
	public static ItemWandCopyPaste wandCopy;
	public static ItemBaseWand baseWand;
	//public static ItemToolFlint flintTool;
	public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_ender;
	public static ItemWandWater wandWater;
	public static ItemWandLightning wandLightning;
	public static 	Item beetrootSeed ;
	public static Item beetrootItem;
	public static Item beetrootSoup;
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	
	 public static void registerItem(Item item, String name)
	 { 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	 }
	
	
	public static void registerItems()
	{
		//needed for all wands; no config.
		ItemRegistry.baseWand = new ItemBaseWand(); 
		ItemRegistry.registerItem(ItemRegistry.baseWand, "base_wand" );   
		ItemBaseWand.addRecipe();	
		
		if(ModLoader.configSettings.beetroot)
		{ 
			beetrootSeed = new ItemSeeds(BlockRegistry.beetrootCrop, Blocks.farmland).setCreativeTab(ModLoader.tabSamsContent);
			ItemRegistry.registerItem(beetrootSeed, "beetroot_seed");
			
			beetrootItem = new ItemFood(3, false).setCreativeTab(ModLoader.tabSamsContent);
			ItemRegistry.registerItem(beetrootItem, "beetroot_item");
			
			beetrootSoup = new ItemSoup(8).setCreativeTab(ModLoader.tabSamsContent); 
			ItemRegistry.registerItem(beetrootSoup, "beetroot_soup");
		}
		   /*
		if(ModLoader.configSettings.flintTool) 
		{
			//TODO: redo texture higher res
			
			//its like shears for leaves in the speed it goes, but does not give leaf blocks
			
			Set harvests = new HashSet<Block>();
			harvests.add(Blocks.leaves);
			harvests.add(Blocks.leaves2); 
			harvests.add(Blocks.tallgrass); //ferns and grass
			ItemRegistry.flintTool = new ItemToolFlint(1,ToolMaterial.EMERALD, harvests);
	 
			SamsRegistry.registerItem(ItemRegistry.flintTool, "flint_tool");
	
			ItemToolFlint.addRecipe();
		}*/
	 
		if(ModLoader.configSettings.wandFire)
		{ 
			ItemRegistry.wandFire = new ItemWandFire();

			registerItem(ItemRegistry.wandFire, "wand_fire");
	 
			ItemWandFire.addRecipe();		 
		}
		
		if(ModLoader.configSettings.wandWater)
		{  
			ItemRegistry.wandWater = new ItemWandWater();

			ItemRegistry.registerItem(ItemRegistry.wandWater, "wand_water");
	 
			ItemWandWater.addRecipe();		 
		}
		
		if(ModLoader.configSettings.wandLightning)
		{  
			ItemRegistry.wandLightning = new ItemWandLightning();

			ItemRegistry.registerItem(ItemRegistry.wandLightning, "wand_lightning");
	 
			ItemWandLightning.addRecipe();		 
		}

		if(ModLoader.configSettings.wandCopy)
		{ 
			ItemRegistry.wandCopy = new ItemWandCopyPaste();

			ItemRegistry.registerItem(ItemRegistry.wandCopy, "wand_copy");

			ItemWandCopyPaste.addRecipe();  
		}
		   
		if(ModLoader.configSettings.wandBuilding)
		{ 
			ItemRegistry.wandBuilding = new ItemWandBuilding(); 
			ItemRegistry.registerItem(ItemRegistry.wandBuilding, "wand_building" );  
			 
			ItemWandBuilding.addRecipe(); 
		}
		 
		if(ModLoader.configSettings.wandChest)
		{   
			ItemRegistry.itemChestSack = new ItemChestSack();   
			ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
			
			ItemRegistry.wandChest = new ItemWandChest(); 
			ItemRegistry.registerItem(ItemRegistry.wandChest, "wand_chest");
	 
			ItemWandChest.addRecipe();  
		}

		if(ModLoader.configSettings.wandTransform)
		{   
			ItemRegistry.wandTransform = new ItemWandTransform(); 
			ItemRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");

			ItemWandTransform.addRecipe();  
		}

		if(ModLoader.configSettings.wandHarvest)
		{   
			ItemRegistry.wandHarvest = new ItemWandHarvest();
			ItemRegistry.registerItem(ItemRegistry.wandHarvest, "wand_harvest");

			ItemWandHarvest.addRecipe();  
		}
		
		if(ModLoader.configSettings.wandLivestock)
		{   
			ItemRegistry.wandLivestock = new ItemWandLivestock();
	  
			ItemRegistry.registerItem(ItemRegistry.wandLivestock, "wand_livestock");

			ItemWandLivestock.addRecipe();  
		}

		if(ModLoader.configSettings.wandProspect)
		{   
			ItemRegistry.wandProspect = new ItemWandProspect();
	  
			ItemRegistry.registerItem(ItemRegistry.wandProspect, "wand_prospect");

			ItemWandProspect.addRecipe();  
		}

		if(ModLoader.configSettings.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook();

			ItemRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender");

			ItemEnderBook.addRecipe();
		}
		
		if(ModLoader.configSettings.appleEmerald) 
		{
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_emerald.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_emerald.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
			 
			ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_emerald_rich.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_emerald_rich.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Blocks.emerald_block));
			 
		}   
		 
		if(ModLoader.configSettings.appleDiamond) 
		{ 
			 
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_diamond.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_diamond.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);   
			ItemRegistry.apple_diamond.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));
		 
			ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_diamond_rich.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond_rich,new ItemStack(Blocks.diamond_block));
		 
		}
 
		if(ModLoader.configSettings.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_lapis.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );
	 
			ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_lapis_rich.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeLong,PotionRegistry.I);
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis_rich,new ItemStack(Blocks.lapis_block));
			
 
		}
		  
		if(ModLoader.configSettings.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(Potion.digSpeed.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I + 1); 
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
		  
			ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);  
			ItemRegistry.apple_chocolate_rich.addEffect(Potion.digSpeed.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
			 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate_rich, new ItemStack(Items.cookie));
		 
		}
	 
		if(ModLoader.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
		 
			ItemRegistry.apple_nether_star.addEffect( PotionRegistry.lavawalk.id, ItemFoodAppleMagic.timeLong,  PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_nether_star,new ItemStack(Items.nether_wart));
		}
	 
		if(ModLoader.configSettings.appleNetherStar)//TODO: apple ender in config 
		{ 
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(PotionRegistry.ender.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_ender,new ItemStack(Items.ender_pearl)) ;
		} 
		 
	}
}
