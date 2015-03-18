package com.lothrazar.samscontent.item;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;

public class ItemRegistry 
{
	public static ItemBucketStorage itemWater;
	public static ItemBucketStorage itemLava;
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
	public static ItemToolFlint flintTool;
	public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_ender;
	 
	public static void registerItems()
	{
		ItemRegistry.baseWand = new ItemBaseWand(); 
		SamsRegistry.registerItem(ItemRegistry.baseWand, "base_wand" );   
		ItemBaseWand.addRecipe();	  
 
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
		}
	 
		if(ModLoader.configSettings.wandFire)
		{ 
			ModLoader.changelog.log("ItemWandFire registered");
			ItemRegistry.wandFire = new ItemWandFire();

			SamsRegistry.registerItem(ItemRegistry.wandFire, "wand_fire");
	 
			ItemWandFire.addRecipe();		 
		}

		if(ModLoader.configSettings.wandCopy)
		{ 
			ItemRegistry.wandCopy = new ItemWandCopyPaste();

			SamsRegistry.registerItem(ItemRegistry.wandCopy, "wand_copy");

			ItemWandCopyPaste.addRecipe();  
		}
		
		if(ModLoader.configSettings.lavaStorage)
		{ 
			ItemRegistry.itemLava = new ItemBucketStorage();

			SamsRegistry.registerItem(ItemRegistry.itemLava, "bucket_storage_lava");
	 
			ItemBucketStorage.addRecipeLava();
		}

		if(ModLoader.configSettings.waterStorage)
		{ 
			ItemRegistry.itemWater = new ItemBucketStorage();

			SamsRegistry.registerItem(ItemRegistry.itemWater, "bucket_storage_water");

			ItemBucketStorage.addRecipeWater();
		}

		if(ModLoader.configSettings.wandBuilding)
		{ 
			ItemRegistry.wandBuilding = new ItemWandBuilding(); 
			SamsRegistry.registerItem(ItemRegistry.wandBuilding, "wand_building" );  
			 
			ItemWandBuilding.addRecipe(); 
		}
		 
		if(ModLoader.configSettings.wandChest)
		{   
			ItemRegistry.itemChestSack = new ItemChestSack();   
			SamsRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
			
			ItemRegistry.wandChest = new ItemWandChest(); 
			SamsRegistry.registerItem(ItemRegistry.wandChest, "wand_chest");
	 
			ItemWandChest.addRecipe();  
		}

		if(ModLoader.configSettings.wandTransform)
		{   
			ItemRegistry.wandTransform = new ItemWandTransform(); 
			SamsRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");

			ItemWandTransform.addRecipe();  
		}

		if(ModLoader.configSettings.wandHarvest)
		{   
			ItemRegistry.wandHarvest = new ItemWandHarvest();
			SamsRegistry.registerItem(ItemRegistry.wandHarvest, "wand_harvest");

			ItemWandHarvest.addRecipe();  
		}
		
		if(ModLoader.configSettings.wandLivestock)
		{   
			ItemRegistry.wandLivestock = new ItemWandLivestock();
	  
			SamsRegistry.registerItem(ItemRegistry.wandLivestock, "wand_livestock");

			ItemWandLivestock.addRecipe();  
		}

		if(ModLoader.configSettings.wandProspect)
		{   
			ItemRegistry.wandProspect = new ItemWandProspect();
	  
			SamsRegistry.registerItem(ItemRegistry.wandProspect, "wand_prospect");

			ItemWandProspect.addRecipe();  
		}

		if(ModLoader.configSettings.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook();

			SamsRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender");

			ItemEnderBook.addRecipe();
		}
		
		if(ModLoader.configSettings.appleEmerald) 
		{
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_emerald.addEffect(ItemFoodAppleMagic.emeraldPotion, ItemFoodAppleMagic.timeShort, ItemFoodAppleMagic.emeraldLevel);  
			SamsRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
			 
			ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_emerald_rich.addEffect(ItemFoodAppleMagic.emeraldPotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.emeraldLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Blocks.emerald_block));
		}   
		 
		if(ModLoader.configSettings.appleDiamond) 
		{ 
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_diamond.addEffect(ItemFoodAppleMagic.diamondPotion, ItemFoodAppleMagic.timeShort, ItemFoodAppleMagic.dimondLevel);  
			ItemRegistry.apple_diamond.addEffect(ItemFoodAppleMagic.diamondPotion2, ItemFoodAppleMagic.timeShort, ItemFoodAppleMagic.dimond2Level);  
			SamsRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));
		 
			ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_diamond_rich.addEffect(ItemFoodAppleMagic.diamondPotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.dimondLevel); 
			ItemRegistry.apple_diamond_rich.addEffect(ItemFoodAppleMagic.diamondPotion2, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.dimond2Level); 
			SamsRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond_rich,new ItemStack(Blocks.diamond_block));
		 
		}
 
		if(ModLoader.configSettings.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_lapis.addEffect(ItemFoodAppleMagic.lapisPotion, ItemFoodAppleMagic.timeShort, ItemFoodAppleMagic.lapisLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );
	 
			ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);
			ItemRegistry.apple_lapis_rich.addEffect(ItemFoodAppleMagic.lapisPotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.lapisLevel); 
			SamsRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis_rich,new ItemStack(Blocks.lapis_block));
		}
		  
		if(ModLoader.configSettings.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(ItemFoodAppleMagic.chocolatePotion, ItemFoodAppleMagic.timeShort, ItemFoodAppleMagic.chocolateLevel + 1); 
			SamsRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
		  
			ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);  
			ItemRegistry.apple_chocolate_rich.addEffect(ItemFoodAppleMagic.chocolatePotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.chocolateLevel);  
			SamsRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
			 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate_rich, new ItemStack(Items.cookie));
		}
	 
		if(ModLoader.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
			ItemRegistry.apple_nether_star.addEffect(ItemFoodAppleMagic.netherwartApplePotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.netherwartAppleLevel);  
	
			SamsRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_nether_star,new ItemStack(Items.nether_wart));
		}
	 
		if(ModLoader.configSettings.appleNetherStar) 
		{ 
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(ItemFoodAppleMagic.enderPotion, ItemFoodAppleMagic.timeLong, ItemFoodAppleMagic.enderLevel);  
	
			SamsRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");
			
			GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_ender)
				,"lll","lal","lll"  
				,'l', Items.ender_pearl
				,'a', Items.apple); 
			
			if(ModLoader.configSettings.uncraftGeneral) 
				GameRegistry.addSmelting(ItemRegistry.apple_ender, new ItemStack(Items.ender_pearl, 8),	0); 
		} 
	}
}
