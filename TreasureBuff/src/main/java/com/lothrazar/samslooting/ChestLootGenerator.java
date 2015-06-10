package com.lothrazar.samslooting;
 
import net.minecraft.init.Blocks;
import net.minecraft.init.Items; 
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import static net.minecraftforge.common.ChestGenHooks.*;

public class ChestLootGenerator
{ 
	public static void lootGlowstone() 
	{
		ItemStack[] allRecords = new ItemStack[] //TODO: rename these variables, possibly refactor the whole class
		{
			new ItemStack(Items.glowstone_dust ,64)
		}; 

		addToAllChests(allRecords,16,64); //each stack in this range
	 
	}

	public static void lootQuartz() 
	{
		ItemStack[] allRecords = new ItemStack[] 
		{
			new ItemStack(Items.quartz ,64)
		}; 

		addToAllChests(allRecords,16,64); //each stack in this range
	
	}

	public static void lootObsidian() 
	{
		ItemStack[] allRecords = new ItemStack[] 
		{
			new ItemStack(Blocks.obsidian,64)
		}; 

		addToAllChests(allRecords,16,64); //each stack in this range
	
	}

	public static void lootAllRecords() 
	{ 
		ItemStack[] allRecords = new ItemStack[] 
		{
			new ItemStack(Items.record_11 ,     1),
			new ItemStack(Items.record_13 ,     1),
			new ItemStack(Items.record_blocks,  1),
			new ItemStack(Items.record_cat,     1),
			new ItemStack(Items.record_chirp,   1),
			new ItemStack(Items.record_far,     1),
			new ItemStack(Items.record_mall,    1),
			new ItemStack(Items.record_mellohi, 1),
			new ItemStack(Items.record_stal,    1),
			new ItemStack(Items.record_strad,   1),
			new ItemStack(Items.record_wait,    1),
			new ItemStack(Items.record_ward  ,  1)
		};// all the records
		 
		addToAllChests(allRecords); 
	 
	}
	
	static int weightDefault = 3;
 
	private static void addToAllChests(ItemStack[] items)
	{
		addToAllChests(items,1,1,weightDefault); //defaults
	} 
	private static void addToAllChests(ItemStack[] items,int min,int max)
	{
		addToAllChests(items,min,max,weightDefault); //default of 3 weight
	} 
	private static void addToAllChests(ItemStack[] items,int min,int max,int weight)
	{ 
		for(int i = 0; i < items.length; i++)
		{
			max = items[i].stackSize;//will this upp the obsidian size to 1?
			//args are item, min, max, ?odds
			ChestGenHooks.addItem(PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(items[i], min, max, weight));
			ChestGenHooks.addItem(PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(items[i], min, max, weight)); 
			ChestGenHooks.addItem(VILLAGE_BLACKSMITH, new WeightedRandomChestContent(  items[i], min, max, weight));
			ChestGenHooks.addItem(DUNGEON_CHEST, new WeightedRandomChestContent(       items[i], min, max, weight));
			ChestGenHooks.addItem(MINESHAFT_CORRIDOR, new WeightedRandomChestContent(  items[i], min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_CORRIDOR, new WeightedRandomChestContent( items[i], min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_CROSSING, new WeightedRandomChestContent (items[i], min, max, weight));
			ChestGenHooks.addItem(STRONGHOLD_LIBRARY, new WeightedRandomChestContent(  items[i], min, max, weight));
			ChestGenHooks.addItem(NETHER_FORTRESS, new WeightedRandomChestContent(     items[i], min, max, weight));
			
			//brainstorm what would make sense, be Fun, and Not overpowered in bonus chests
			// if food then not too much
			//stack of fun potions
			//
			
			//this was for testing. on a new world we got 4 records right away
			//weight=9;
			//ChestGenHooks.addItem(BONUS_CHEST, new WeightedRandomChestContent(items[i], min, max, weight));
		}
	}

	public static void regsiterLoot() 
	{
		if(ModLoot.cfg.lootAllRecords) ChestLootGenerator.lootAllRecords();
		
		if(ModLoot.cfg.lootObsidian) ChestLootGenerator.lootObsidian(); 
		
		if(ModLoot.cfg.lootQuartz) ChestLootGenerator.lootQuartz(); 
		
		if(ModLoot.cfg.lootGlowstone) ChestLootGenerator.lootGlowstone(); 
	}
}
