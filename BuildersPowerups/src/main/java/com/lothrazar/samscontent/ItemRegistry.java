package com.lothrazar.samscontent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
import com.lothrazar.samscontent.item.*;  
import com.lothrazar.util.Reference; 

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();

	
	public final static int I = 0; 
	public final static int II = 1;
	public final static int III = 2;
	public final static int IV = 3;
	public final static int V = 4;
	
	//public static ItemEnderBook itemEnderBook; 
	public static ItemChestSack itemChestSack; 
	public static ItemRespawnEggEmpty respawn_egg_empty; 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleHeart apple_diamond; 
	public static ItemFoodAppleMagic apple_ender; 
	public static ItemFoodAppleMagic apple_frost; 
 
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_netherwart; 
	public static ItemPaperCarbon carbon_paper;  
 
	public static Item beetroot_seed ;
	public static Item beetrootItem;
	public static Item beetrootSoup;	
	 
	 
	public static ItemRespawnEggAnimal respawn_egg; 
	public static Item soulstone;
 
	//String name,
	public static ToolMaterial MATERIAL_EMERALD;
	public static int timePotionShort = 90; // 1:30
	public static int timePotionLong = 8 * 60;// 8:00
	
	public static Item spell_water_dummy;
	public static Item spell_frostbolt_dummy;
	public static Item spell_waterwalk_dummy;
	public static Item spell_harvest_dummy;
	public static Item spell_lightning_dummy;
	public static Item spell_jump_dummy;
	public static Item spell_ghost_dummy;
	public static Item spell_enderinv_dummy;
	public static Item exp_cost_dummy;
	public static Item exp_cost_empty_dummy;
	public static Item spell_heart_dummy;
	public static Item spell_torch_dummy;
	public static Item spell_haste_dummy;
	public static void registerItems()
	{   
		ItemRegistry.itemChestSack = new ItemChestSack();   
		ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
	
		soulstone = new Item();
		ItemRegistry.registerItem(soulstone, "soulstone");
		spell_heart_dummy = new Item();
		ItemRegistry.registerItem(spell_heart_dummy, "spell_heart_dummy");
		exp_cost_dummy = new Item();
		ItemRegistry.registerItem(exp_cost_dummy, "exp_cost_dummy");
		exp_cost_empty_dummy = new Item();
		ItemRegistry.registerItem(exp_cost_empty_dummy, "exp_cost_empty_dummy");
		spell_torch_dummy = new Item();
		ItemRegistry.registerItem(spell_torch_dummy, "spell_torch_dummy");
		spell_water_dummy = new Item();
		ItemRegistry.registerItem(spell_water_dummy, "spell_water_dummy"); 
		spell_jump_dummy = new Item();
		ItemRegistry.registerItem(spell_jump_dummy, "spell_jump_dummy");
		spell_frostbolt_dummy = new Item();
		ItemRegistry.registerItem(spell_frostbolt_dummy, "spell_frostbolt_dummy");
		spell_waterwalk_dummy = new Item();
		ItemRegistry.registerItem(spell_waterwalk_dummy, "spell_waterwalk_dummy");
		spell_harvest_dummy = new Item();
		ItemRegistry.registerItem(spell_harvest_dummy, "spell_harvest_dummy");
		spell_lightning_dummy = new Item();
		ItemRegistry.registerItem(spell_lightning_dummy, "spell_lightning_dummy");
		spell_ghost_dummy = new Item();
		ItemRegistry.registerItem(spell_ghost_dummy, "spell_ghost_dummy");
		spell_enderinv_dummy = new Item();
		ItemRegistry.registerItem(spell_enderinv_dummy, "spell_enderinv_dummy");
		spell_haste_dummy = new Item();
		ItemRegistry.registerItem(spell_haste_dummy, "spell_haste_dummy");
		if(ModMain.cfg.emerald_armor)
		{ 
			MATERIAL_EMERALD = EnumHelper.addToolMaterial("emerald", 
					3, //harvestLevel 3 same as diamond
					1600, //maxUses more than diamond
					15.0F, //efficiency more than gold
					3.5F, //damage more than diamond
					5+25 );//enchantability more than gold 
			    
		 /* REFERENCE: TO COMPARE:   public static enum ToolMaterial
		    {
		 // int harvestLevel, int maxUses, float efficiency, float damage, int enchantability)
		        WOOD(0, 59, 2.0F, 0.0F, 15),
		        STONE(1, 131, 4.0F, 1.0F, 5),
		        IRON(2, 250, 6.0F, 2.0F, 14),
		        GOLD(0, 32, 12.0F, 0.0F, 22);
		        DIAMOND(3, 1561, 8.0F, 3.0F, 10),
		      */
			 
			//thanks for help: http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-tools-swords/
			 int xp = 1;
			
			ItemSword emerald_sword = new ItemSword(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_sword, "emerald_sword");
			emerald_sword.setCreativeTab(ModMain.tabSamsContent);
			
			GameRegistry.addShapedRecipe(new ItemStack(emerald_sword)
				, " e "," e "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			

 // gggg these Constructors are protected./ this is the only reason i have subclasses, need subclasses
			
			ItemSamPickaxe emerald_pickaxe = new ItemSamPickaxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_pickaxe, "emerald_pickaxe");
			emerald_pickaxe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_pickaxe)
				, "eee"," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));

			ItemAxe emerald_axe = new ItemSamAxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_axe, "emerald_axe");
			emerald_axe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, "ee ","es "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, " ee"," se"," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			
			ItemSpade emerald_spade = new ItemSpade(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_spade, "emerald_spade");
			emerald_spade.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_spade)
				, " e "," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			
			ItemHoe emerald_hoe = new ItemHoe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_hoe, "emerald_hoe");
			emerald_hoe.setCreativeTab(ModMain.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, "ee "," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, " ee"," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			

			if(ModMain.cfg.uncraftGeneral)
			{
				GameRegistry.addSmelting(emerald_spade, new ItemStack(Blocks.emerald_block,1), xp);
				GameRegistry.addSmelting(emerald_sword, new ItemStack(Blocks.emerald_block,2), xp);
				GameRegistry.addSmelting(emerald_hoe, new ItemStack(Blocks.emerald_block,2), xp);
				GameRegistry.addSmelting(emerald_pickaxe, new ItemStack(Blocks.emerald_block,3), xp);
				GameRegistry.addSmelting(emerald_axe, new ItemStack(Blocks.emerald_block,3), xp);
			}
		}

	
 /*
		if(ModMain.cfg.beetroot)
		{  
			
			beetroot_seed = new ItemSeeds(BlockRegistry.beetroot_crop, Blocks.farmland).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetroot_seed, "beetroot_seed");
			
			beetrootItem = new ItemFood(3, false).setCreativeTab(ModMain.tabSamsContent);
			ItemRegistry.registerItem(beetrootItem, "beetroot_item");
		
			beetrootSoup = new ItemSoup(8).setCreativeTab(ModMain.tabSamsContent); 
			ItemRegistry.registerItem(beetrootSoup, "beetroot_soup");
			
			GameRegistry.addRecipe(new ItemStack(beetrootSoup), 
					"bbb", 
					"bbb",
					" u ", 
					'b', beetrootItem, 
					'u', Items.bowl
					);
		}
  
*/
		if(ModMain.cfg.carbon_paper)
		{ 
			ItemRegistry.carbon_paper = new ItemPaperCarbon();

			ItemRegistry.registerItem(ItemRegistry.carbon_paper, "carbon_paper");

			ItemPaperCarbon.addRecipe();  
		}
	 
		if(ModMain.cfg.respawn_egg)
		{   
			respawn_egg = new ItemRespawnEggAnimal();
			ItemRegistry.registerItem(respawn_egg, "respawn_egg");
			
			ItemRegistry.respawn_egg_empty = new ItemRespawnEggEmpty(); 
			ItemRegistry.registerItem(ItemRegistry.respawn_egg_empty, "respawn_egg_empty"); 
			ItemRespawnEggEmpty.addRecipe();  
		}
  
		if(ModMain.cfg.appleEmerald) 
		{
			//potion effects
			 
			ItemRegistry.apple_emerald = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);
			ItemRegistry.apple_emerald.addEffect(Potion.digSpeed.id, timePotionLong, II);
			ItemRegistry.apple_emerald.addEffect(Potion.moveSpeed.id, timePotionLong, I);  
			ItemRegistry.apple_emerald.addEffect(Potion.absorption.id, timePotionLong, I);  
			ItemRegistry.apple_emerald.addEffect(Potion.resistance.id, timePotionLong, I); 
			ItemRegistry.apple_emerald.addEffect(Potion.jump.id, timePotionLong, I); 
		//	ItemRegistry.apple_emerald.addEffect(PotionRegistry.slowfall.id, timePotionLong, I); 
			ItemRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
			
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_emerald,new ItemStack(Items.emerald));
	
			
			
		}   
		/*
		if(ModMain.cfg.appleEnder) 
		{
			ItemRegistry.apple_ender = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerLarge, false);
			//one hour!
			System.out.println("apple ender"+PotionRegistry.ender.id);
			ItemRegistry.apple_ender.addEffect(PotionRegistry.ender.id, 60*60, PotionRegistry.I);  
			ItemRegistry.registerItem(ItemRegistry.apple_ender, "apple_ender");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_ender,new ItemStack(Items.ender_pearl));
		}
 */
		
		if(ModMain.cfg.appleChocolate)
		{
			ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, false); 
			ItemRegistry.apple_chocolate.addEffect(Potion.weakness.id, timePotionLong, I);
			ItemRegistry.apple_chocolate.addEffect(Potion.moveSpeed.id, timePotionLong, I);
			ItemRegistry.apple_chocolate.addEffect(Potion.digSpeed.id, timePotionLong, I); 
			ItemRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_chocolate, new ItemStack(Items.dye, 1, Reference.dye_cocoa) );
	
		}
		 
		if(ModMain.cfg.appleDiamond) 
		{  
			ItemRegistry.apple_diamond = new ItemFoodAppleHeart();
  
			ItemRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
			
			ItemFoodAppleHeart.addRecipe(ItemRegistry.apple_diamond);

		}/*
		if(ModMain.cfg.appleFrost) 
		{
			System.out.println("apple_frost  "+PotionRegistry.frost.id);
			apple_frost = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true); 
			ItemRegistry.apple_frost.addEffect(PotionRegistry.frost.id, timePotionLong, PotionRegistry.I);
			ItemRegistry.registerItem(ItemRegistry.apple_frost, "apple_frost");
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_frost, new ItemStack(Blocks.ice ));
		}
		if(ModMain.cfg.appleNetherwart) 
		{ 
			ItemRegistry.apple_netherwart = new ItemFoodAppleMagic(ItemFoodAppleMagic.hungerSmall, true);  
		 
			ItemRegistry.apple_netherwart.addEffect( PotionRegistry.lavawalk.id, timePotionLong,  PotionRegistry.I);  
			 
			ItemRegistry.registerItem(ItemRegistry.apple_netherwart, "apple_nether_star");
		 
			ItemFoodAppleMagic.addRecipe(ItemRegistry.apple_netherwart,new ItemStack(Items.nether_wart));
		}*/
 
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
}
