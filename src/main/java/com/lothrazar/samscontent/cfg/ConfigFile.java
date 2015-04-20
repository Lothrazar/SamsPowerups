package com.lothrazar.samscontent.cfg;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration; 

import com.lothrazar.samscontent.command.*;  
import com.lothrazar.samscontent.item.*; 

public class ConfigFile
{ 
	private Configuration instance;
	private String category = "";
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigFile(Configuration c)
	{
		instance = c; 
 
		mob_changes();

		commands();
 
		blocks();
		
	    recipes_new();  
	    
		recipes_changes();
		 
		creative();
		 
		items(); 

		harvesting_changes();
		
		terrain_generation();
		
		dungeon_chests();
		 
		debug_info();
		 
		mob_spawning(); 
		  
		potions();
		 
		nature();
		 
		pocket_edition();  

		inventory();
		  
		player();
		
		category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		canNameVillagers  = instance.getBoolean("name_villagers",category, true,
    			"You can name villagers with a nametag. ");
		 
		flintPumpkin = instance.getBoolean("flint_pumpkin",category, true,
    			"Lighting a pumpkin with a flint and steel turns it into a lit pumpkin (jack-o-lantern). ");
		 
		betterBonemeal = instance.getBoolean("better_bonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers, and reeds. ");

		skullSignNames = instance.getBoolean("skull_sign_names",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");

		
		fragileTorches = instance.getBoolean("fragile_torches",category, true,
				"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		 
		if(instance.hasChanged()){ instance.save(); }
	}

	private void inventory() 
	{
		category = "inventory";
		
		increasedStackSizes = instance.getBoolean("stack_size",category, true,
			"While true, most vanilla items and blocks have their max stack size increased to 64 (not tools/armor/potions).  ");
		
		potionStackSize = instance.getInt("stack_size_potion",category, 1,1,3,
    			"Potion stack size can be increased to three, but not by default.");
		 
		swiftDeposit = instance.getBoolean("swift_deposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	); 
		
		smartEnderchest = instance.getBoolean("smart_enderchest",category, true,
    			"Attack with the ender chest to open it without placing it."	);
	}

	private void player() 
	{
		category = "player";

		playerDeathCoordinates = instance.getBoolean("player_death_coordinates",category, true,
    			"Players will have their death point coordinates broadcast in chat.");
		
		dropPlayerSkullOnDeath = instance.getBoolean("drop_player_skull",category, true,
    			"Players will drop their skull when they die.");
	}

	public void pocket_edition() 
	{
		category = "pocket_edition";
				
		beetroot =  instance.getBoolean( "beetroot",category,true,
				"Add beetroot, similar to pocket edition.  You must use a golden hoe to get seeds while making farmland. " );
	}
	
	private void mob_changes() 
	{
		category = "mob_changes";
		
		livestockLootMultiplier  = instance.getInt("livestock_multiplier",category, 2,1,5,
	    			"Factor to increase drops from livestock: including sheep, chicken, horse, cow, rabbit, pigs, and squid.  Useful because less animals being collected and bred means less lag.  (use 1 for vanilla behavior)");
			 
		petNametagDrops = instance.getBoolean("nametag_drops",category, true,
	    			"Some mobs that are named drop a name tag when they die (wolf, ocelot, villager, bat, rabbit, horse).");
 //TODO: RESPAWNING?
		petNametagChat  = instance.getBoolean("nametag_death_messages",category, true,
	    			"Non-player entities that are named with a Name Tag send a chat death message when they die.");
	
		removeZombieCarrotPotato = instance.getBoolean("remove_zombie_carrot_potato",category, true,
    			"Disable these zombie drops."); 

		chanceZombieChildFeather = instance.getInt("chance_zombie_child_feather",category, 5,0,100,
    			"Percent chance that a child zombie will drop a feather (so 0 for vanilla).");
 
		chanceZombieVillagerEmerald = instance.getInt("chance_zombie_villager_emerald",category, 5,0,100,
    			"Percent chance that a villager zombie will drop an emerald (so 0 for vanilla).");
		
		endermenDropCarryingBlock = instance.getBoolean("endermen_drop_carrying_block",category, true,
    			"Endermen will always drop any block they are carrying.");	
	}

	private void nature() 
	{
		category = "nature";
  
		plantDespawningSaplings = instance.getBoolean("sapling_plant_despawn",category, true,
    			"When a sapling (or mushroom) despawns while sitting on grass or dirt, it will instead attempt to plant itself.");

		saplingGrowthRestricted = instance.getBoolean("sapling_biome_restricted",category, true,
    			"Sapling growth is restricted to only their native biomes (for example, birch trees will not grow in roofed forests).");
		 
		saplingAllNether = instance.getBoolean("sapling_nether",category, false,
    			"If true, all saplings grow in the nether (ignoring sapling_biome_restricted).");
		
		saplingAllEnd = instance.getBoolean("sapling_end",category, false,
    			"If true, all saplings grow in the end (ignoring sapling_biome_restricted)");
	}

	private void terrain_generation() 
	{
		category = "terrain_generation";
		
		worldGenOceansNotUgly = instance.getBoolean("ocean_floor",category, true,
    			"Clay, sand, and dirt can generate in oceans just like they used to in the old days.  Replaces the gravel in patches.");
	 
		clayNumBlocks = instance.getInt("clay_num",category, 16,1,32,
    			"Blocks per vein.");
		clayChance = instance.getInt("clay_chance",category, 65,1,99,
    			"Chance of spawning a vein.");
		
		sandNumBlocks = instance.getInt("sand_num",category, 20,1,32,
    			"Blocks per vein.");
		sandChance = instance.getInt("sand_chance",category, 45,1,99,
    			"Chance of spawning a vein.");

		dirtNumBlocks = instance.getInt("dirt_num",category, 14,1,32,
    			"Blocks per vein.");
		dirtChance = instance.getInt("dirt_chance",category, 30,1,99,
    			"Chance of spawning a vein.");
	}

	private void potions() 
	{ 
		category = "potions";
		
		potionIdWaterwalk = instance.getInt("potion_waterwalk_id",category, 40,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		  
		potionIdSlowfall = instance.getInt("potion_slowfall_id",category, 41,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		  
		potionIdFlying = instance.getInt("potion_flying_id",category, 42,33,200,
    			"ID is only exposed to avoid conflicts with other mods. ");
		
		potionIdLavawalk = instance.getInt("potion_lavawalk_id",category, 43,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		
		potionIdEnder = instance.getInt("potion_ender_id",category, 44,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		
		potionIdFrozen = instance.getInt("potion_frost_id",category, 45,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		
		slowfallSpeed = instance.getFloat("potion_slowfall_speed",category, 0.41F,0.1F,1F,
    			"This factor affects how much the slowfall potion slows down the entity.");
	}
  
	private void creative() 
	{
		category = "creative_inventory_added";
		
		//no comment on purpose. more readable, less vertical space
		mushroomBlocksCreativeInventory = instance.get(category,"mushroomBlocks", true).getBoolean();

		barrierCreativeInventory = instance.get(category,"barrier", true).getBoolean();
		
		dragonEggCreativeInventory = instance.get(category,"dragonEgg", true).getBoolean();
		
		farmlandCreativeInventory = instance.get(category,"farmland", true).getBoolean();
		
		spawnerCreativeInventory = instance.get(category,"spawner", true).getBoolean(); 
	}

	private void recipes_changes() 
	{
		category = "recipes_changes";
		
		furnaceNeedsCoal = instance.getBoolean("furnace_coal",category, true,
				"If true, you cannot craft a furnace with only 8 cobblestone, it will also require one coal in the center.");  
		
		smoothstoneTools = instance.getBoolean("smoothstone_tools",category, true,
				"Making stone tools out of cobblestone gives damaged tools.  Making stone tools out of smoothstone gives the fully repaired tool.");  

		tieredArmor = instance.getBoolean("tiered_armor",category, true,
				"If true, crafting iron armor requires repaired leather armor as part of the recipe, AND diamond armor requires chain mail.");  
	}

	private void harvesting_changes() 
	{
		category = "harvesting_changes";
		 /*
		String csv = instance.getString("harvestOnlyShovel",category, "minecraft:dirt,minecraft:sand",
    			"If these blocks are not harvested by a shovel, they will break but have no drops."); 
		HandlerPlayerHarvest.setShovelFromCSV(csv);
	
		String csvaxe = instance.getString("harvestOnlyAxe",category, "minecraft:log,minecraft:log2",
    			"If these blocks are not harvested by an axe, they will break but have no drops."); 
		HandlerPlayerHarvest.seAxeFromCSV(csvaxe);
		 */
		harvestGlassPickaxe  = instance.getBoolean("harvest_glass_pickaxe",category, true,
    			"Sets the pickaxe as the correct tool to harvest glass (by default there is no correct glass tool)."); 
		
		obsidianHardness  = instance.getInt("obsidian_hardness",category, 10,1,50,
	    		"Hardness level of Obsidian (vanilla is 50).");

		redstoneOreHardness = instance.getInt("redstone_ore_hardness",category, 6,1,50,
    			"Hardness level of redstone ore (vanilla is 3).");
		
		diamondOreHardness  = instance.getInt("diamond_ore_hardness",category, 10,1,50,
	    		"Hardness level of diamond ore (vanilla is 3).");
		 
		emeraldOreHardness  = instance.getInt("emerald_ore_hardness",category, 12,1,50,
	    		"Hardness level of emerald ore (vanilla is 3).");
		 
		spawnerHardness  = instance.getInt("spawner_hardness",category, 50,1,50,
	    		"Hardness level of mob spawners (vanilla is 5)."); 
	}
  
	private void mob_spawning() 
	{
		category = "mob_spawning";
		
		spawnBlazeDesertHills = instance.get(category,"blaze_desertHills", true).getBoolean(); 
    		
		spawnMagmaCubeDesert = instance.get(category,"magmaCube_Desert", true).getBoolean(); 
    		
		spawnCaveSpiderMesa = instance.get(category,"caveSpider_Mesa", true).getBoolean(); 
    		
		spawnCaveSpiderRoofedForest = instance.get(category,"caveSpider_RoofedForest", true).getBoolean(); 
    		
		spawnSnowgolemsIceMountains = instance.get(category,"snowgolems_IceMountains", true).getBoolean(); 
    		
		spawnGhastDeepOcean = instance.get(category,"ghast_DeepOcean", true).getBoolean(); 
    		
		spawnHorseIcePlains = instance.get(category,"horse_IcePlains", true).getBoolean(); 
    		
		spawnHorseOceanIslands = instance.get(category,"horse_OceanIslands", true).getBoolean(); 
    		
		spawnHorseExtremeHills = instance.get(category,"horse_ExtremeHills", true).getBoolean(); 
    		
		spawnVillagerExtremeHills = instance.get(category,"villager_ExtremeHills", true).getBoolean();
    			//"Villagers pawn naturally in Extreme Hills (not village buildings, it just rarely spawns a villager instead of another passive mob). "); 
    
		spawnCaveSpiderJungle = instance.get(category,"caveSpider_Jungle", true).getBoolean(); 
	}
	 
	private void debug_info() 
	{
		category = "debug_screen_f3";
		  
		debugClearRight = instance.getBoolean("clear_right",category, false,
    			"Clears the right side. " );
		
		debugSlime = instance.getBoolean("slime",category, true,
    			"Shows if you are standing in a slime chunk." );
		
		debugHorseInfo = instance.getBoolean("horse",category, true,
    			"Shows info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = instance.getBoolean("village",category, true,
    			"Shows info on any village you are standing in.");
	}
	
	private void dungeon_chests() 
	{
		category = "more_chest_loot";
 
		lootObsidian = instance.get(category,"obsidian", true).getBoolean();
  
		lootAllRecords = instance.get(category,"records", true).getBoolean();
 
		lootGlowstone = instance.get(category,"glowstone", true).getBoolean();
 
		lootQuartz = instance.get(category,"quartz", true).getBoolean();
	}
	
	private void items() 
	{
		category = "items";
  
		horse_food_upgrades = instance.getBoolean("horse_food_upgrades",category, true,
				"Adds three items that let you upgrade a horses health, change its colour and change it into a zombie or skeleton horse.");  
		  
		enderBook = instance.getBoolean( "ender_book",category,true,
				" Craft an ender book that lets you save a waypoint, and then teleport to it later (single use).  Do not use this outside the overworld.");
 
		chest_sack = instance.getBoolean("chest_sack",category, true,
    			"Craft an empty sack that can transport chests by turning them into sacks; place the full sack to re-create the full chest.  Items with NBT data (enchantments and more) will pop out on the ground.");
 
		appleChocolate = instance.get( category,"apple_chocolate",true).getBoolean();  
		
		appleEmerald = instance.get( category,"apple_emerald",true).getBoolean();
	 
		appleLapis = instance.get(category, "apple_lapis",true).getBoolean();
	  
		appleDiamond = instance.get(category, "apple_diamond",true).getBoolean();
	  
		appleNetherStar = instance.get(category, "apple_netherwart",true).getBoolean();
		
		fire_charge_throw = instance.getBoolean("fire_charge_throw",category, true,
    			"Craft new version of the fire charge that is throwable (as if it came out of a dispenser).");
 
		frozen_snowball = instance.getBoolean("frozen_snowball",category, true,
    			"Throw a frozen snowball that freezes water and causes a short icy potion effect to anything it hits.");
		
		carbon_paper = instance.getBoolean("carbon_paper",category, true,
    			"Craft a wand that can copy and paste note blocks and signs.");
 
		ItemMagicHarvester.RADIUS  = instance.getInt("harvest_charge.radius",category, 16,1,64,
    			"Range in all directions.");
		
		harvest_charge = instance.getBoolean("harvest_charge",category, true,
    			"This harvests a large area of crops at once while also replanting for you.");
 
		respawn_egg = instance.getBoolean("respawn_egg",category, true,
    			"Use an empty respawn egg to turn an mob into a respawn egg.  This works the same as a regular spawn egg, but does not interact with mob spawners.  Works only on livestock/passive mobs, not hostiles.");
 
		ItemWandTransform.DURABILITY  = instance.getInt("wand_transform.durability",category, 200,1,999,
    			"Durability (number of uses in survival).");
		
		wandTransform = instance.getBoolean("wand_transform",category, true,
    			"Craft a wand that will transform the targeted block by its metadata value.  Does not work on every block in the game, but it does allow you to use otherwise obtainable values (mushroom blocks, logs, etc).  ");
		/*
		category = parentCateory + ".wandProspect";
			
		ItemWandProspect.DURABILITY  = instance.getInt("durability",category, 200,1,999,
    			"Durability (number of uses in survival).");

		ItemWandProspect.RADIUS  = instance.getInt("radius",category, 16,1,64,
    			"Range in all directions.");

		wandProspect = instance.getBoolean("wandProspect",category, true,
    			"Craft a wand that will prospect the nearby area for diamonds."); 
	*/	
		 
		wandBuilding = instance.getBoolean( "wand_building", category,true,
				"Can craft and use a building wand that can store many stacks of items, and replace blocks without mining.");  
		
		ItemWandBuilding.DURABILITY   = instance.getInt("wand_building.durability",category, 200,1,999,
    			"Durability (number of uses in survival).");

		ItemWandBuilding.replaceBedrock = instance.getBoolean("wand_building.replaceBedrock", category ,true,
			"Set true to allow the building wand to affect bedrock.  "	);
		
		ItemWandBuilding.replaceObsidian = instance.getBoolean("wand_building.replaceObsidian", category ,true,
			 "Set true to allow the building wand to affect obsidian.  "	);
		 
		ItemWandBuilding.replaceTileEntities = instance.getBoolean("wand_building.replaceTileEntities", category ,true,
			 "Set true to allow the building wand to affect Tile Entities - which is anything with an invnetory " +
			 "(such as chest or dispenser).   "	); 
		 
		ItemWandWater.DURABILITY  = instance.getInt("wand_water.durability",category, 50,1,999,
    			"Durability (number of uses in survival).");
		
		wandWater = instance.getBoolean("wand_water",category, true,
    			"Craft a wand that places water.");
	 
		lightning_charge = instance.getBoolean("lightning_charge",category, true,
    			"Works like a fire charge, but it spawns lightning instead of fire.");
	}
	
	private void blocks() 
	{
		category = "blocks";
		 
		storeBucketsBlock = instance.getBoolean( "storeBuckets",category,true,
				"A block that stores any number of milk/water/lava buckets (click to insert / withdraw)."); 
	 
		shearSheepBlock = instance.getBoolean( "shearSheep",category,true,
				"Shears adult sheep that collide with this block."); 
		 
		fishingNetBlock = instance.getBoolean( "fishing_net",category,true,
				"Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		 
		xRayBlock = instance.getBoolean( "chunk_error_xray",category,true,
				"Create an xray block to see through the world at the block location, in the same way a chunk error would.  Intended for single player, not for cheating on servers."); 

		weatherBlock = instance.getBoolean( "weather",category,true,
				"Craft block that will run /toggledownfall whenever it gets a redstone signal."); 
		
		teleportBedBlock = instance.getBoolean( "teleport_bed",category,true,
				"Command block that teleports you to the world spawn");
		
		teleportSpawnBlock = instance.getBoolean( "teleport_spawn",category,true,
				"Command block that teleports you to your bed"); 
	}
	
	private void recipes_new() 
	{
		category = "recipes_new";
		 
		netherwartPurpleDye = instance.getBoolean( "netherwart_purple_dye",category,true,
				"Craft bonemeal and netherwart into purple dye.");
		
		simpleDispenser = instance.getBoolean( "simple_dispenser",category,true,
				"Craft a dispenser with string in the center instead of a bow.  (Since string is stackable, this makes crafting tons of them much faster and cheaper).");
		 
		craftBooksWithoutLeather = instance.getBoolean( "books_without_leather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed.");
		
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
	 
	}
	
	private void commands() 
	{
		category = "commands";

		kit = instance.getBoolean("kit",category, true,
    			"Use /kit to give yourself kit items.  Can only be done once each time you die.");

		String csv = instance.getString("kit.items",category, "minecraft:wooden_pickaxe,minecraft:wooden_sword",
    			"Using /kit gives the following item.  Each must have minecraft:item or modname:item, no spaces and split by commas.");
		CommandKit.setItemsFromString(csv);
		
		CommandHome.REQUIRES_OP = instance.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		home = instance.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed."); 
		
		CommandHome.REQUIRES_OP = instance.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		worldhome = instance.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point.");  
		
		CommandWorldHome.REQUIRES_OP = instance.getBoolean("worldhomehome.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchspawner = instance.getBoolean("searchspawner",category, true,
    			"Players can search for spawners placed in the world.  Result is only chat output.");
		
		CommandSearchSpawner.REQUIRES_OP = instance.getBoolean("searchspawner.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		searchtrade = instance.getBoolean("searchtrade",category, true,
    			"Players can search the trades of nearby villagers.  Result is only chat output.");
		
		CommandSearchTrades.REQUIRES_OP = instance.getBoolean("searchtrade.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		searchitem = instance.getBoolean("searchitem",category, true,
    			"Players can search nearby chests for items.   Result is only chat output."    		); 
		
		CommandSearchItem.REQUIRES_OP = instance.getBoolean("searchitem.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
  
		enderchest = instance.getBoolean("enderchest",category, true,
    			"Players can open their enderchest with a command, no item needed."    		); 
		
		CommandEnderChest.REQUIRES_OP = instance.getBoolean("enderchest.needs_op",category, true,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		 
		simplewaypoint = instance.getBoolean("simplewaypoint",category, true,
    			"Command that lets players save waypoints that then show up in the F3 debug screen, so we can navigate back to it (no tp)."    		); 
		
		CommandSimpleWaypoints.REQUIRES_OP = instance.getBoolean("simplewaypoint.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		todo = instance.getBoolean("todo",category, true,
    			"Command that lets players use /todo myreminder text, which will then show whatever text they put on the F3 debug screen."); 
		
		CommandTodoList.REQUIRES_OP = instance.getBoolean("todo.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
	}
	 
	public boolean swiftDeposit;
	public boolean smartEnderchest;
	public boolean increasedStackSizes;
	public boolean moreFuel;
	public boolean skullSignNames; 
	public boolean craftableTransmuteRecords;    
	public boolean craftableBonemealColouredWool;   
	public boolean craftBooksWithoutLeather; 
	public boolean betterBonemeal;
	public boolean decorativeBlocks;  
	public boolean uncraftGeneral; 
	public boolean fishingNetBlock;
	public boolean xRayBlock; 
	public boolean enderBook;
	public boolean weatherBlock; 
	public boolean craftableMushroomBlocks;
	public boolean searchtrade;
	public boolean searchitem;
	public boolean killall;
	public boolean enderchest;
	public boolean simplewaypoint;
	public boolean todo;
	public boolean kit; 
	public boolean home;
	public boolean worldhome;
	public boolean lootObsidian;
	public boolean lootAllRecords;
	public boolean lootGlowstone;
	public boolean lootQuartz;
	public boolean appleDiamond;
	public boolean appleLapis;
	public boolean appleChocolate;
	public boolean appleEmerald;
//	public boolean gameruleBlockRegen; 
	public boolean debugSlime;
	public boolean debugHorseInfo;
	public boolean debugClearRight;
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
	public boolean appleNetherStar;
	public boolean smoothstoneTools;
	public boolean tieredArmor;
	public boolean furnaceNeedsCoal;  
	public boolean plantDespawningSaplings; 
	public boolean wandBuilding;
	public boolean simpleDispenser; 
	public boolean dropPlayerSkullOnDeath;
	public boolean searchspawner; 
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
	public boolean chest_sack;
	public boolean carbon_paper;
	public boolean harvest_charge;
	public boolean respawn_egg;
	public boolean wandTransform;
	public int livestockLootMultiplier;
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
	public int potionIdEnder; 
	public boolean wandWater;
	public boolean harvestGlassPickaxe;
	public boolean lightning_charge;
	public boolean shearSheepBlock; 
	public boolean storeBucketsBlock;
	public boolean beetroot;
	public boolean flintPumpkin;
	public boolean endermenDropCarryingBlock;
	public boolean fire_charge_throw;
	public boolean frozen_snowball;
	public int potionStackSize;
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
}
