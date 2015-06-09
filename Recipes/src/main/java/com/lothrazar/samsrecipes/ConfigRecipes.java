package com.lothrazar.samsrecipes;

import net.minecraftforge.common.config.Configuration;

public class ConfigRecipes
{ 
	private Configuration instance;
	private String category = ModRecipes.MODID;
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigRecipes(Configuration c)
	{
		instance = c; 
		instance.load();
	//	category = "recipes_changes";
		
		furnaceNeedsCoal = instance.getBoolean("furnace_coal",category, true,
				"If true, you cannot craft a furnace with only 8 cobblestone, it will also require one coal in the center.");  
		
		smoothstoneTools = instance.getBoolean("smoothstone_tools",category, true,
				"Making stone tools out of cobblestone gives damaged tools.  Making stone tools out of smoothstone gives the fully repaired tool.");  

		
		//category = "recipes_new";
		
		//cheaper_stairs = instance.getBoolean( "cheaper_stairs",category,true,
		//		"Craft stairs in your inventory 2x2 grid.  This recipe is cheaper and more logical than the original.");
		
		smelt_gravel = instance.getBoolean( "smelt_gravel",category,true,
				"Smelt gravel into flint to save on mindless shovel digging.");
		
		experience_bottle = instance.getBoolean( "experience_bottle",category,true,
				"Craft experience bottles from many mob drops with empty bottles.");
		 
		netherwartPurpleDye = instance.getBoolean( "netherwart_purple_dye",category,true,
				"Craft bonemeal and netherwart into purple dye.");
		
		simpleDispenser = instance.getBoolean( "simple_dispenser",category,true,
				"Craft a dispenser with string in the center instead of a bow.  (Since string is stackable, this makes crafting tons of them much faster and cheaper).");
		
		quartz_from_prismarine = instance.getBoolean( "quartz_from_prismarine",category,true,
				"Craft quartz from prismarine shards, iron, bonemeal, and a potato.");			
	    
		craftableTransmuteRecords = instance.getBoolean( "transmute_records",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record.");
    
		craftableBonemealColouredWool =  instance.getBoolean( "bonemeal_coloured_wool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"			); 
  
		uncraftGeneral = instance.getBoolean( "uncrafting",category,true,
				"uncrafting: craft or smelt blocks back into their ingredients.  Often it is not a perfect trade.  " +
				"Example: Craft stairs back into blocks using a 4x4 pattern."	); 
		
		craftableMushroomBlocks =  instance.getBoolean( "mushroom_blocks",category,true
				,"Craft mushroom blocks. ");
 
		moreFuel = instance.getBoolean("more_fuel",category, true,
    			"More can be used as furnace fuel: seeds, leaves, paper, shrubs, and more."); 
		
		
		/*
		harvestGlassPickaxe  = instance.getBoolean("harvest_glass_pickaxe",category, true,
    			"Sets the pickaxe as the correct tool to harvest glass (by default there is no correct glass tool)."); 
		
		obsidianHardness  = instance.getInt("obsidian_hardness",category, 10,1,50,
	    		"Hardness level of Obsidian (vanilla is 50).");

		redstoneOreHardness = instance.getInt("redstone_ore_hardness",category, 6,1,50,
    			"Hardness level of redstone ore (vanilla is 3).");
		
		diamondOreHardness  = instance.getInt("diamond_ore_hardness",category, 8,1,50,
	    		"Hardness level of diamond ore (vanilla is 3).");
		 
		emeraldOreHardness  = instance.getInt("emerald_ore_hardness",category, 12,1,50,
	    		"Hardness level of emerald ore (vanilla is 3).");
		 
		spawnerHardness  = instance.getInt("spawner_hardness",category, 50,1,50,
	    		"Hardness level of mob spawners (vanilla is 5)."); 
 */
		if(instance.hasChanged()){ instance.save(); }
	}

       
      
	 
 
	public boolean swiftDeposit; 
	public boolean increasedStackSizes;
	public boolean moreFuel;
	public boolean skullSignNames; 
	public boolean craftableTransmuteRecords;    
	public boolean craftableBonemealColouredWool;    
	public boolean bonemealAllFlowers;
	public boolean bonemealLilypads;
	public boolean bonemealReeds;
	public boolean decorativeBlocks;  
	public boolean uncraftGeneral; 
	public boolean fishingNetBlock; 
	//public boolean enderBook;
	public boolean weatherBlock; 
	public boolean craftableMushroomBlocks;
	public boolean cmd_searchtrade;
	public boolean cmd_searchitem;
	public boolean killall;
	public boolean cmd_enderchest;
	public boolean cmd_simplewaypoint;
	public boolean cmd_todo;
	public boolean cmd_kit; 
	public boolean cmd_home;
	public boolean worldhome;
	public boolean lootObsidian;
	public boolean lootAllRecords;
	public boolean lootGlowstone;
	public boolean lootQuartz;
	public boolean appleDiamond;
	//public boolean appleLapis;
	public boolean appleChocolate;
 
 
	public boolean debugSlime;
	public boolean debugHorseInfo;
	public boolean reducedDebugImproved;
	public boolean debugVillageInfo;
	public boolean spawnBlazeDesertHills;
	public boolean spawnMagmaCubeDesert;
	public boolean spawnCaveSpiderMesa;
	public boolean spawnCaveSpiderRoofedForest;
	public boolean spawnSnowgolemsIceMountains;
	public boolean spawnGhastDeepOcean;
	public boolean spawnHorseIcePlains;
	public boolean spawnHorseOceanIslands;
	public boolean spawnHorseExtremeHills;
	public boolean craftWoolDye8;
	public boolean craftRepeaterSimple;
	public boolean craftMinecartsSimple;
	public boolean petNametagDrops;
	public boolean spawnVillagerExtremeHills;
	public boolean teleportBedBlock;
	public boolean teleportSpawnBlock;
	public boolean spawnCaveSpiderJungle;
	//public boolean appleNetherwart;
	//public boolean appleEnder;
	public boolean appleEmerald;
	public boolean smoothstoneTools; 
	public boolean furnaceNeedsCoal;  
	public boolean plantDespawningSaplings; 
	//public boolean wandPiston;
	public boolean simpleDispenser; 
	public boolean dropPlayerSkullOnDeath;
	public boolean cmd_searchspawner; 
	public boolean mushroomBlocksCreativeInventory;
	public boolean barrierCreativeInventory;
	public boolean dragonEggCreativeInventory;
	public boolean farmlandCreativeInventory;
	public boolean spawnerCreativeInventory; 
	public boolean fragileTorches;
	public boolean removeZombieCarrotPotato;
	public boolean petNametagChat;
	public boolean playerDeathCoordinates;
	public int obsidianHardness; 
	public int diamondOreHardness;
	public int emeraldOreHardness;
	public int spawnerHardness; 
 
	public boolean carbon_paper;
 
	public boolean respawn_egg;
 
	public int potionIdWaterwalk;
	public int potionIdSlowfall;
	public int potionIdFlying;
	public float slowfallSpeed;
	public boolean flintTool;
	public int potionIdLavawalk;
	public boolean netherwartPurpleDye;
	public boolean worldGenOceansNotUgly;
	public boolean saplingGrowthRestricted;
	public boolean saplingAllNether;
	public boolean saplingAllEnd; 
	public boolean harvestGlassPickaxe;
 
	public boolean shearSheepBlock; 
	public boolean storeBucketsBlock;
	public boolean beetroot;
	public boolean flintPumpkin;
	public boolean endermenDropCarryingBlock;
	public int potionIdEnder;
	public int potionIdFrozen;
	public int chanceZombieChildFeather;
	public int chanceZombieVillagerEmerald;
	public float redstoneOreHardness;
	public int clayChance;
	public int clayNumBlocks;
	public int dirtChance;
	public int dirtNumBlocks;
	public int sandChance;
	public int sandNumBlocks;
	public boolean canNameVillagers;
	public boolean horse_food_upgrades;
	public int cowExtraLeather;
	public int sleeping_hunger_seconds;  
	public boolean experience_bottle;
	public boolean experience_bottle_return;
	public boolean block_fragile; 
	public boolean quartz_from_prismarine;
	public boolean debugGameruleInfo;
	public boolean smelt_gravel;
	public boolean cmd_place_blocks; 
	public boolean emerald_armor; 
	//public boolean cheaper_stairs; 
	public boolean cmd_recipe;
	public boolean cmd_uses; 
	//public boolean cmd_effectpay;
	public boolean cmd_ping; 
	public int heartsWolfTamed;
	public int heartsVillager;
	public int heartsCatTamed;
	//public boolean appleFrost;
}
