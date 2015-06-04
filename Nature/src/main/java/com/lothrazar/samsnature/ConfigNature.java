package com.lothrazar.samsnature;

import net.minecraftforge.common.config.Configuration;

public class ConfigNature
{ 
	private Configuration instance;
	private String category = "";
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigNature(Configuration c)
	{
		instance = c; 
		instance.load();
  
		//fragileTorches = instance.getBoolean("fragile_torches",category, true,
		//		"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		 
	 
 
 	
		//beetroot =  instance.getBoolean( "beetroot",category,true,
		//		"Add beetroot to the game.  The same as pocket edition, except they do not work with pigs.  " );
 
		category = "nature";

		flintPumpkin = instance.getBoolean("flint_pumpkin",category, true,
    			"Lighting a pumpkin with a flint and steel turns it into a lit pumpkin (jack-o-lantern). ");
  
		bonemealAllFlowers = instance.get(category,"bonemeal_all_flowers", true).getBoolean();

		bonemealLilypads = instance.get(category,"bonemeal_lilypads", true).getBoolean();

		bonemealReeds = instance.get(category,"bonemeal_reeds", true).getBoolean();
		
		plantDespawningSaplings = instance.getBoolean("sapling_plant_despawn",category, true,
    			"When a sapling (or mushroom) despawns while sitting on grass or dirt, it will instead attempt to plant itself.");

		saplingGrowthRestricted = instance.getBoolean("sapling_biome_restricted",category, true,
    			"Sapling growth is restricted to only their native biomes (for example, birch trees will not grow in roofed forests).");
		 
		saplingAllNether = instance.getBoolean("sapling_nether",category, false,
    			"If true, all saplings grow in the nether (ignoring sapling_biome_restricted).");
		
		saplingAllEnd = instance.getBoolean("sapling_end",category, false,
    			"If true, all saplings grow in the end (ignoring sapling_biome_restricted)");
 
 
		category = "ocean_generation";
	
		worldGenOceansNotUgly = instance.getBoolean("alter_ocean_floor",category, true,
    			"Clay, sand, and dirt can generate in oceans just like they used to in the old days.  Replaces the gravel in patches.");
	 
		clayNumBlocks = instance.get(category,"clay_size",16).getInt();
		clayChance = instance.get(category,"clay_chance",65).getInt();
		
		sandNumBlocks = instance.get(category,"sand_size",20).getInt();
		sandChance = instance.get(category,"sand_chance",45).getInt();

		dirtNumBlocks = instance.get(category,"dirt_size",14).getInt();
		dirtChance = instance.get(category,"dirt_chance",30).getInt();

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


		lootObsidian = instance.get(category,"obsidian", true).getBoolean();
  
		lootAllRecords = instance.get(category,"records", true).getBoolean();
 
		lootGlowstone = instance.get(category,"glowstone", true).getBoolean();
 
		lootQuartz = instance.get(category,"quartz", true).getBoolean();
		
		if(instance.hasChanged()){ instance.save(); }
	}
 /*
	private void creative() 
	{
		category = "creative_inventory_added";
		 
		mushroomBlocksCreativeInventory = instance.get(category,"mushroom_blocks", true).getBoolean();

		barrierCreativeInventory = instance.get(category,"barrier", true).getBoolean();
		
		dragonEggCreativeInventory = instance.get(category,"dragon_egg", true).getBoolean();
		
		farmlandCreativeInventory = instance.get(category,"farmland", true).getBoolean();
		
		spawnerCreativeInventory = instance.get(category,"spawner", true).getBoolean(); 
	}
*/ 
   
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
	public boolean appleNetherwart;
	public boolean appleEnder;
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
	public boolean cheaper_stairs; 
	public boolean cmd_recipe;
	public boolean cmd_uses; 
	//public boolean cmd_effectpay;
	public boolean cmd_ping; 
	public int heartsWolfTamed;
	public int heartsVillager;
	public int heartsCatTamed;
	public boolean appleFrost;
}
