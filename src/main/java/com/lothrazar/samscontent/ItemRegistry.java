package com.lothrazar.samscontent;

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

import com.lothrazar.samscontent.item.*; 
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference; 

public class ItemRegistry 
{ 
	public static ItemEnderBook itemEnderBook;
	public static ItemWandBuilding wandBuilding;
	public static ItemChestSackEmpty wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemMagicHarvester harvest_charge;
	public static ItemWandTransform wandTransform; 
	public static ItemRespawnEggEmpty respawn_egg_empty; 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star; 
	public static ItemPaperCarbon carbon_paper;  
	public static ItemFoodAppleMagic apple_diamond_rich;
	public static ItemFoodAppleMagic apple_ender;
	public static ItemWandWater wandWater;
	public static ItemLightning lightning_charge;
	public static Item beetroot_seed ;
	public static Item beetrootItem;
	public static Item beetrootSoup;	
	
	public static ItemHorseFood horse_upgrade_type; 
	public static ItemHorseFood horse_upgrade_variant; 
	public static ItemHorseFood horse_upgrade_health; 
	
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ItemFireballThrowable fire_charge_throw;
	public static ItemSnowballFrozen frozen_snowball; 
	public static ItemRespawnEggAnimal respawn_egg;
	public static ItemWandPiston wand_piston;
	 public static ItemSoulstone soulstone;
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
	public static void registerItems()
	{   
		if(ModMain.cfg.item_soulstone)
		{
			soulstone = new ItemSoulstone();
			ItemRegistry.registerItem(soulstone, "soulstone");
			soulstone.addRecipe();
		}
		
		if(ModMain.cfg.wandPiston)
		{
			wand_piston = new ItemWandPiston();
			ItemRegistry.registerItem(wand_piston, "wand_piston");
 
			wand_piston.addRecipe();
		}
		
		if(ModMain.cfg.horse_food_upgrades )
		{  
			horse_upgrade_type = new ItemHorseFood();
			ItemRegistry.registerItem(horse_upgrade_type, "horse_upgrade_type");
			
			horse_upgrade_variant = new ItemHorseFood();
			ItemRegistry.registerItem(horse_upgrade_variant, "horse_upgrade_variant");
			
			horse_upgrade_health = new ItemHorseFood();
			ItemRegistry.registerItem(horse_upgrade_health, "horse_upgrade_health"); 
			
			ItemHorseFood.addRecipes();
		}
		
		if(ModMain.cfg.beetroot)
		{  
			beetroot_seed = new ItemSeeds(BlockRegistry.beetroot_crop, Blocks.farmland).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetroot_seed, "beetroot_seed");
			
			beetrootItem = new ItemFood(3, false).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetrootItem, "beetroot_item");
		
			beetrootSoup = new ItemSoup(8).setCreativeTab(ModMain.tabSamsContent); 
			ItemRegistry.registerItem(beetrootSoup, "beetroot_soup");
			

		}
		 
		if(ModMain.cfg.fire_charge_throw)
		{ 
			ItemRegistry.fire_charge_throw = new ItemFireballThrowable();

			registerItem(ItemRegistry.fire_charge_throw, "fire_charge_throw");
	 
			ItemFireballThrowable.addRecipe();		 
		}
		 
		if(ModMain.cfg.frozen_snowball)
		{ 
			ItemRegistry.frozen_snowball = new ItemSnowballFrozen();

			registerItem(ItemRegistry.frozen_snowball, "frozen_snowball");
	 
			ItemSnowballFrozen.addRecipe();		 
		}
		 
		if(ModMain.cfg.wandWater)
		{  
			ItemRegistry.wandWater = new ItemWandWater();

			ItemRegistry.registerItem(ItemRegistry.wandWater, "wand_water");
	 
			ItemWandWater.addRecipe();		 
		}
		
		if(ModMain.cfg.lightning_charge)
		{  
			ItemRegistry.lightning_charge = new ItemLightning();

			ItemRegistry.registerItem(ItemRegistry.lightning_charge, "lightning_charge");
	 
			ItemLightning.addRecipe();		 
		}

		if(ModMain.cfg.carbon_paper)
		{ 
			ItemRegistry.carbon_paper = new ItemPaperCarbon();

			ItemRegistry.registerItem(ItemRegistry.carbon_paper, "carbon_paper");

			ItemPaperCarbon.addRecipe();  
		}
		   /*
		if(ModSamsContent.cfg.wandBuilding)
		{ 
			ItemRegistry.wandBuilding = new ItemWandBuilding(); 
			ItemRegistry.registerItem(ItemRegistry.wandBuilding, "wand_building" );  
			 
			ItemWandBuilding.addRecipe(); 
		}
		 */
		if(ModMain.cfg.chest_sack)
		{   
			ItemRegistry.itemChestSack = new ItemChestSack();   
			ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
			
			ItemRegistry.wandChest = new ItemChestSackEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.wandChest, "chest_sack_empty");
	 
			ItemChestSackEmpty.addRecipe();  
		}

		if(ModMain.cfg.wandTransform)
		{   
			ItemRegistry.wandTransform = new ItemWandTransform(); 
			ItemRegistry.registerItem(ItemRegistry.wandTransform, "wand_transform");

			ItemWandTransform.addRecipe();  
		}

		if(ModMain.cfg.harvest_charge)
		{   
			ItemRegistry.harvest_charge = new ItemMagicHarvester();
			ItemRegistry.registerItem(ItemRegistry.harvest_charge, "harvest_charge");

			ItemMagicHarvester.addRecipe();  
		}
		
		if(ModMain.cfg.respawn_egg)
		{   
			respawn_egg = new ItemRespawnEggAnimal();
			ItemRegistry.registerItem(respawn_egg, "respawn_egg");
			
			ItemRegistry.respawn_egg_empty = new ItemRespawnEggEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.respawn_egg_empty, "respawn_egg_empty"); 
			ItemRespawnEggEmpty.addRecipe();  
		}
 
		if(ModMain.cfg.enderBook)
		{ 
			ItemRegistry.itemEnderBook = new ItemEnderBook(); 
			ItemRegistry.registerItem(ItemRegistry.itemEnderBook, "book_ender"); 
			ItemEnderBook.addRecipe();
		}
		 
		if(ModMain.cfg.appleEmerald) 
		{
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_emerald.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_emerald.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
			 
			ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);
			ItemRegistry.apple_emerald_rich.addEffect(PotionRegistry.slowfall.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_emerald_rich.addEffect(Potion.jump.id, ItemFoodAppleMagic.timeShort, PotionRegistry.V); 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald_rich,new ItemStack(Blocks.emerald_block)); 
		}   
		 
		if(ModMain.cfg.appleDiamond) 
		{  
			ItemRegistry.apple_diamond = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_diamond.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.apple_diamond.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);   
			ItemRegistry.apple_diamond.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond,new ItemStack(Items.diamond));
		 
			ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);
			ItemRegistry.apple_diamond_rich.addEffect(PotionRegistry.flying.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.resistance.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I); 
			ItemRegistry.apple_diamond_rich.addEffect(Potion.absorption.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_diamond_rich,new ItemStack(Blocks.diamond_block));
		}
 
		if(ModMain.cfg.appleLapis)
		{ 
			ItemRegistry.apple_lapis = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false);
			ItemRegistry.apple_lapis.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeShort, PotionRegistry.I); 
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis,new ItemStack(Items.dye, 1, Reference.dye_lapis) );
	 
			ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, true);
			ItemRegistry.apple_lapis_rich.addEffect(PotionRegistry.waterwalk.id, ItemFoodAppleMagic.timeLong,PotionRegistry.I);
			 
			ItemRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_lapis_rich,new ItemStack(Blocks.lapis_block));	 
		}
		  
		if(ModMain.cfg.appleChocolate)
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
	 
		if(ModMain.cfg.appleNetherStar) 
		{ 
			ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
		 
			ItemRegistry.apple_nether_star.addEffect( PotionRegistry.lavawalk.id, ItemFoodAppleMagic.timeLong,  PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_nether_star,new ItemStack(Items.nether_wart));
		}
	 
		if(ModMain.cfg.appleNetherStar) 
		{ 
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);   
			ItemRegistry.apple_ender.addEffect(PotionRegistry.ender.id, ItemFoodAppleMagic.timeLong, PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");

			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_ender,new ItemStack(Items.ender_pearl)) ;
		}  
	}
}
