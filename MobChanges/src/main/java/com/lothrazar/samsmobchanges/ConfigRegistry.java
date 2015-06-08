package com.lothrazar.samsmobchanges;

import net.minecraftforge.common.config.Configuration;

public class ConfigRegistry
{ 
	private Configuration instance;
	private String category = "";
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigRegistry(Configuration c)
	{
		instance = c; 
		instance.load();
   
		category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		skullSignNames = instance.getBoolean("skull_sign_names",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");

		fragileTorches = instance.getBoolean("fragile_torches",category, true,
				"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		 
		experience_bottle_return = instance.getBoolean("experience_bottle_return",category, true,
				"Experience bottles in survival mode return an empty glass bottle to you (if used on a block).");  
		  
		category = "player";

		playerDeathCoordinates = instance.getBoolean("player_death_coordinates",category, false,
    			"Players will have their death point coordinates broadcast in chat.");
		
		dropPlayerSkullOnDeath = instance.getBoolean("drop_player_skull_on_death",category, true,
    			"Players will drop their skull when they die for any reason.");

		sleeping_hunger_seconds = instance.getInt("sleeping_hunger_seconds",category, 60,0,999,
    		"Number of seconds of hunger effect you get if you skip the night in a bed (so 0 for vanilla).");
 
		category = "mob_changes";
		 
		heartsWolfTamed = instance.getInt("health_tamed_wolf",category, 40,1,900,
    			"Change the number of hearts a tamed wolf has.  4 hearts is the vanilla default.");
		
		heartsCatTamed = instance.getInt("health_tamed_cat",category, 40,1,900,
    			"Change the number of hearts a tamed cat has.  4 hearts is the vanilla default.");
		
		heartsVillager = instance.getInt("health_villager",category, 80,1,900,
    			"Change the number of hearts a tamed wolf has.  10 hearts is the vanilla default.");
		
		canNameVillagers = instance.get(category, "nametag_usable_on_villagers",true).getBoolean();
		  
		petNametagDrops = instance.getBoolean("nametag_drops",category, true,
	    			"Any mobs that is named will drop the name tag again when they die.  ");
		
		petNametagChat  = instance.getBoolean("nametag_death_messages",category, true,
	    			"Non-player entities that are named with a Name Tag send a chat death message when they die as if they were a player.");
	
		removeZombieCarrotPotato = instance.get(category,"remove_zombie_drop_carrot_potato", true).getBoolean(); 

		chanceZombieChildFeather = instance.getInt("chance_zombie_child_feather",category, 5,0,100,
    			"Percent chance that a child zombie will drop a feather (so 0 for vanilla).");
 
		chanceZombieVillagerEmerald = instance.getInt("chance_zombie_villager_emerald",category, 5,0,100,
    			"Percent chance that a villager zombie will drop an emerald (so 0 for vanilla).");
		
		endermenDropCarryingBlock = instance.get(category,"endermen_drop_carrying_block", true).getBoolean();
		
		cowExtraLeather =instance.getInt("cow_extra_leather",category, 2,0,10,
    			"Extra leather that is dropped by cows.  Normally they drop 0-2 leather, so with this setting at '2', you will get 2-4 leather per cow.");
		 
		category = "mob_spawning";
		
		spawnBlazeDesertHills = instance.get(category,"blaze_deserthills", true).getBoolean(); 
    		
		spawnMagmaCubeDesert = instance.get(category,"magmacube_desert", true).getBoolean(); 
    		
		spawnCaveSpiderMesa = instance.get(category,"cavespider_mesa", true).getBoolean(); 
    		
		spawnCaveSpiderRoofedForest = instance.get(category,"cavespider_roofedforest", true).getBoolean(); 
    		
		spawnSnowgolemsIceMountains = instance.get(category,"snowgolems_icemountains", true).getBoolean(); 
    		
		spawnGhastDeepOcean = instance.get(category,"ghast_deepocean", true).getBoolean(); 
    		
		spawnHorseIcePlains = instance.get(category,"horse_iceplains", true).getBoolean(); 
    		
		spawnHorseOceanIslands = instance.get(category,"horse_ocean", true).getBoolean(); 
    		
		spawnHorseExtremeHills = instance.get(category,"horse_extremehills", true).getBoolean(); 
    		
		spawnVillagerExtremeHills = instance.get(category,"villagersingle_extremehills", true).getBoolean();
    	
		spawnCaveSpiderJungle = instance.get(category,"cavespider_jungle", true).getBoolean(); 
	
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
	//public boolean horse_food_upgrades;
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
	//public boolean appleFrost;
}
