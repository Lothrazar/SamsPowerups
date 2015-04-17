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
	public Configuration instance()
	{
		return instance;
	}
	
	//private static String LevelSep = ".";//to go between main and sub levels nested in the json style cfg file
	String category = "";
	
	public ConfigFile(Configuration c)
	{
		instance = c; 

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
		 
		spawning(); 
		 
		wands();
		 
		potions();
		 
		nature();
 
		animals();
		 
		pocket_edition();  
		
		category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		endermenDropCarryingBlock = instance.getBoolean("endermenDropCarryingBlock",category, true,
    			"Endermen will always drop any block they are carrying.");
		 

		flintPumpkin = instance.getBoolean("flintPumpkin",category, true,
    			"Lighting a pumpkin with a flint and steel turns it into a lit pumpkin (jack-o-lantern). ");
		 
		betterBonemeal = instance.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers, and reeds. ");
 
		increasedStackSizes = instance.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."); 
		
		potionStackSize  = instance.getInt("potionStackSize",category, 1,1,3,
    			"Potion stack size can be increased to three, but not by default.");
		
		moreFuel = instance.getBoolean("moreFuel",category, true,
    			"More can be used as furnace fuel: seeds, leaves, paper, shrubs, and more."); 
	 
		swiftDeposit = instance.getBoolean("swiftDeposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	); 
		
		smartEnderchest = instance.getBoolean("smartEnderchest",category, true,
    			"Attack with the ender chest to open it without placing it."	);
		 
		skullSignNames = instance.getBoolean("skullSignNames",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");
		 
		removeZombieCarrotPotato = instance.getBoolean("removeZombieCarrotPotato",category, true,
    			"Disable these zombie drops."); 

		chanceZombieChildFeather   = instance.getInt("chanceZombieChildFeather",category, 10,0,100,
    			"Percent chance that a child zombie will drop a feather.");
		
		chanceZombieVillagerEmerald  = instance.getInt("chanceZombieVillagerEmerald",category, 5,0,100,
    			"Percent chance that a villager zombie will drop an emerald.");
		
		
		playerDeathCoordinates = instance.getBoolean("playerDeathCoordinates",category, true,
    			"Players will have their death point coordinates broadcast in chat.");
		
		dropPlayerSkullOnDeath = instance.getBoolean("dropPlayerSkullOnDeath",category, true,
    			"Players will drop their skull when they die.");

		fragileTorches = instance.getBoolean("fragileTorches",category, true,
				"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		
		if(instance.hasChanged()){ instance.save(); }
	}

	public void pocket_edition() 
	{
		category = "pocket_edition";
				
		beetroot =  instance.getBoolean( "beetroot",category,true,
				"Add beetroot, similar to pocket edition.  Use a golden hoe to get seeds. " );
	}
public int livestockLootScaleFactor;
	private void animals() 
	{
		category = "animals";
		
		livestockLootScaleFactor  = instance.getInt("livestockLootScaleFactor",category, 5,0,32,
	    			"Scale factor to multiply drops from livestock: including sheep, chicken, horse, cow, rabbit, and also pigs get double this factor again.");
			 
		petNametagDrops = instance.getBoolean("petNametagDrops",category, true,
	    			"Some mobs that are named drop a name tag when they die (wolf, ocelot, villager, bat, rabbit, horse).");
 
		petNametagChat  = instance.getBoolean("nametagDeathMessages",category, true,
	    			"Non-player entities that are named with a Name Tag send a chat death message when they die.");
	}

	private void nature() 
	{
		category = "nature";
  
		plantDespawningSaplings = instance.getBoolean("plantDespawningSaplings",category, true,
    			"When a sapling (or mushroom) despawns while sitting on grass or dirt, it will instead attempt to plant itself.");

		saplingGrowthRestricted = instance.getBoolean("saplingGrowthRestricted",category, true,
    			"Sapling growth is restricted to only their native biomes (for example, birch trees will not grow in roofed forests).");
		 
		saplingAllNether = instance.getBoolean("saplingAllNether",category, false,
    			"Sapling growth restrictions are lifted in the nether.");
		
		saplingAllEnd = instance.getBoolean("saplingAllEnd",category, false,
    			"Sapling growth restrictions are lifted in the end.");
		  
	}

	private void terrain_generation() 
	{
		category = "terrain_generation";
		
		worldGenClayOceans = instance.getBoolean("worldGenClayOceans",category, true,
    			"Clay can generate in oceans just like it used to in the old days.");
	}

	private void potions() 
	{ 
		category = "potions";
		
		potionIdWaterwalk = instance.getInt("potionIdWaterwalk",category, 40,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		  
		potionIdSlowfall = instance.getInt("potionIdSlowfall",category, 41,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		  
		potionIdFlying = instance.getInt("potionIdFlying",category, 42,33,200,
    			"ID is only exposed to avoid conflicts with other mods.  THIS IS INTENDED FOR USE ONLY IN SINGLE PLAYER.");
		
		potionIdLavawalk = instance.getInt("potionIdLavawalk",category, 43,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		
		potionIdEnder = instance.getInt("potionIdEnder",category, 44,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		
		potionIdFrozen = instance.getInt("potionIdFrozen",category, 45,33,200,
    			"ID is only exposed to avoid conflicts with other mods.");
		

		//	potionIdTired = instance.getInt("potionIdTired",category, 46,33,200,
	    //			"ID is only exposed to avoid conflicts with other mods.");
		
		slowfallSpeed = instance.getFloat("slowfallSpeed",category, 0.41F,0.1F,1F,
    			"This factor affects how much the slowfall potion slows down the entity.");
	}

	private void wands() 
	{ 
		String parentCateory = "items"; 
 
		category = parentCateory + ".wandFireball";
 
		wandFireball = instance.getBoolean("wandFireball",category, true,
    			"Craft a wand that can shoots fire charges.");
 
		category = parentCateory + ".wandSnowball";
 
		frozen_snowball = instance.getBoolean("wandSnowball",category, true,
    			"Craft a wand that can shoots snowballs.");
		
		category = parentCateory + ".wandWater";

		ItemWandWater.DURABILITY  = instance.getInt("durability",category, 200,1,999,
    			"Durability (number of uses in survival).");
		
		wandWater = instance.getBoolean("wandWater",category, true,
    			"Craft a wand that places water.");

		category = parentCateory + ".wandLightning";

		ItemWandLightning.DURABILITY  = instance.getInt("durability",category, 200,1,999,
    			"Durability (number of uses in survival).");
		
		wandLightning = instance.getBoolean("wandLightning",category, true,
    			"Craft a wand that spawns lightning bolts.");
 
		category = parentCateory + ".wandCopy";
 
		wandCopy = instance.getBoolean("wandCopy",category, true,
    			"Craft a wand that can copy and paste note blocks and signs.");

		category = parentCateory + ".wandHarvest";

	 
		ItemMagicHarvester.RADIUS  = instance.getInt("radius",category, 16,1,64,
    			"Range in all directions.");
		
		wandHarvest = instance.getBoolean("wandHarvest",category, true,
    			"Craft a wand that will harvest the crops in the area around you.");

		category = parentCateory + ".wandLivestock";
 
		respawn_egg = instance.getBoolean("wandLivestock",category, true,
    			"Craft a wand that will transform livestock animals into spawn eggs.");

		category = parentCateory + ".wandTransform";

		ItemWandTransform.DURABILITY  = instance.getInt("durability",category, 200,1,999,
    			"Durability (number of uses in survival).");
		
		wandTransform = instance.getBoolean("wandTransform",category, true,
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
		category = parentCateory + ".wandBuilding"; 
		
		wandBuilding = instance.getBoolean( "wandBuilding", category,true,
				"Can craft and use a building wand that can store many stacks of items, and replace blocks without mining.");  
		
		ItemWandBuilding.DURABILITY   = instance.getInt("durability",category, 200,1,999,
    			"Durability (number of uses in survival).");

		ItemWandBuilding.replaceBedrock = instance.getBoolean("replaceBedrock", category ,true,
			"Set true to allow the building wand to affect bedrock.  "	);
		
		ItemWandBuilding.replaceObsidian = instance.getBoolean("replaceObsidian", category ,true,
			 "Set true to allow the building wand to affect obsidian.  "	);
		 
		ItemWandBuilding.replaceTileEntities = instance.getBoolean("replaceTileEntities", category ,true,
			 "Set true to allow the building wand to affect Tile Entities - which is anything with an invnetory " +
			 "(such as chest or dispenser).   "	); 
	}

	private void creative() 
	{
		category = "creative";
		
		mushroomBlocksCreativeInventory = instance.getBoolean("mushroomBlocks",category, true,
    			"Add the (missing) mushroom blocks into creative inventory where they should be.");

		barrierCreativeInventory = instance.getBoolean("barrier",category, true,
    			"Add barrier into creative inventory.");
		
		dragonEggCreativeInventory = instance.getBoolean("dragonEgg",category, true,
    			"Add dragon egg into creative inventory.");
		
		farmlandCreativeInventory = instance.getBoolean("farmland",category, true,
    			"Add farmland into creative inventory.");
		
		spawnerCreativeInventory = instance.getBoolean("spawner",category, true,
    			"Add spawner into creative inventory.");
	}

	private void recipes_changes() 
	{
		category = "recipes_changes";
		
		furnaceNeedsCoal = instance.getBoolean("furnaceNeedsCoal",category, true,
				"If true, you cannot craft a furnace with only 8 cobblestone, it will also require one coal in the center.");  
		
		smoothstoneToolsRequired = instance.getBoolean("smoothstoneToolsRequired",category, true,
				"If true, all stone tools will require smoothstone instead of cobble.");  

		tieredArmor = instance.getBoolean("tieredArmor",category, true,
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
		harvestGlassPickaxe  = instance.getBoolean("harvestGlassPickaxe",category, true,
    			"Sets the pickaxe as the correct tool to harvest glass (by default there is no correct glass tool)."); 
		
		obsidianHardness  = instance.getInt("obsidianHardness",category, 10,1,50,
	    			"Hardness level of Obsidian (vanilla is 50).");

		diamondOreHardness  = instance.getInt("diamondOreHardness",category, 10,1,50,
	    			"Hardness level of diamond ore (vanilla is 3).");
		 
		emeraldOreHardness  = instance.getInt("emeraldOreHardness",category, 12,1,50,
	    			"Hardness level of emerald ore (vanilla is 3).");
		 
		spawnerHardness  = instance.getInt("spawnerHardness",category, 50,1,50,
	    			"Hardness level of mob spawners (vanilla is 5).");
	}
  
	private void spawning() 
	{
		category = "spawning";
		
		spawnBlazeDesertHills = instance.getBoolean("blazeDesertHills",category, true,
    			"Blazes spawn naturally in Desert Hills."); 
    		
		spawnMagmaCubeDesert = instance.getBoolean("magmaCubeDesert",category, true,
    			"Magma cubes spawn naturally in Desert."); 
    		
		spawnCaveSpiderMesa = instance.getBoolean("caveSpiderMesa",category, true,
    			"Cave spiders spawn naturally in Mesa. "); 
    		
		spawnCaveSpiderRoofedForest = instance.getBoolean("caveSpiderRoofedForest",category, true,
    			"Cave Spiders spawn naturally in Roofed Forest."); 
    		
		spawnSnowgolemsIceMountains = instance.getBoolean("snowgolemsIceMountains",category, true,
    			"Snow Golems spawn naturally in Ice Mountains. "); 
    		
		spawnGhastDeepOcean = instance.getBoolean("ghastDeepOcean",category, true,
    			"Ghasts spawn naturally in Deep Ocean (above). "); 
    		
		spawnHorseIcePlains = instance.getBoolean("horseIcePlains",category, true,
    			"Horses spawn naturally in Ice Plains. "); 
    		
		spawnHorseOceanIslands = instance.getBoolean("horseOceanIslands",category, true,
    			"Horses pawn naturally in Deep Ocean (islands). "); 
    		
		spawnHorseExtremeHills = instance.getBoolean("horseExtremeHills",category, true,
    			"Horses pawn naturally in Extreme Hills. "); 
    		
		spawnVillagerExtremeHills = instance.getBoolean("villagerExtremeHills",category, true,
    			"Villagers pawn naturally in Extreme Hills (not village buildings, it just rarely spawns a villager instead of another passive mob). "); 
    
		spawnCaveSpiderJungle = instance.getBoolean("caveSpiderJungle",category, true,
    			"Cave Spiderspawn naturally in Jungle. "); 
	}
	 
	private void debug_info() 
	{
		category = "debug_screen";
		  
		debugClearRight = instance.getBoolean("clearRight",category, true,
    			"Clears the right side of the debug screen (F3). " );
		
		debugSlime = instance.getBoolean("slime",category, true,
    			"It will show if you are standing in a slime chunk." );
		
		debugHorseInfo = instance.getBoolean("horse",category, true,
    			"It will show info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = instance.getBoolean("village",category, true,
    			"It will show info on any village you are standing in.");
	}
	
	private void dungeon_chests() 
	{
		category = "dungeon_chests";
 
		lootObsidian = instance.getBoolean("obsidian",category, true,
    			"Add obsidian as a random treasure from naturally spawned chests "	);
  
		lootAllRecords = instance.getBoolean("records",category, true,
    			"Add all record types as a random treasure from naturally spawned chests ");
 
		lootGlowstone = instance.getBoolean("glowstone",category, true,
    			"Add glowstone dust as a random treasure from naturally spawned chests ");
 
		lootQuartz = instance.getBoolean("quartz",category, true,
    			"Add quartz as a random treasure from naturally spawned chests ");
	}
	
	private void items() 
	{
		
		
		category = "items";
		
		chest_sack = instance.getBoolean("chest_sack",category, true,
    			"Craft an empty sack that can transport chests by turning them into sacks.  Items with NBT data will pop out.");

		/*
		
		flintTool = instance.getBoolean( "flintTool",category,true,
				"Flint Tool: Harvest leaves the same speed as shears, the difference is you get normal drops instead of leaf blocks.  " );  
		*/
		appleChocolate = instance.getBoolean( "appleChocolate",category,true,
			"An apple surrounded by either chocolate or cookies gives a short buff of Haste when eaten.  " );  
		
		appleEmerald = instance.getBoolean( "appleEmerald",category,true,
				"An apple surrounded by emeralds gives a short buff of Absorption V when eaten. " );  
		
		appleLapis = instance.getBoolean( "appleLapis",category,true,
				"An apple surrounded by lapis gives a short buff of Resistance when eaten.  " );  
		 
		appleDiamond = instance.getBoolean( "appleDiamond",category,true,
				"Eating a diamond apple gives you two extra persistant hearts (until you die)."); 
		 
		appleNetherStar = instance.getBoolean( "appleNetherStar",category,true,
				"A nether star surrounded by apples.  Eating this gives you the power of flight for a number of seconds (visible in the debug screen)."); 

		category = "items" + ".wandWater";
		
		enderBook = instance.getBoolean( "enderBook",category,true,
				" Craft an ender book that lets you save waypoints, and then teleport to them later (only in the overworld).");
 
	}
	
	private void blocks() 
	{
		category = "blocks";
		storeMilkBlock = instance.getBoolean( "storeMilkBlock",category,true,
				"A block that stores any number of milk buckets (click to insert / withdraw)."); 
		storeLavaBlock = instance.getBoolean( "storeLavaBlock",category,true,
				"A block that stores any number of lava buckets (click to insert / withdraw)."); 
		storeWaterBlock = instance.getBoolean( "storeWaterBlock",category,true,
				"A block that stores any number of water buckets (click to insert / withdraw)."); 
		shearSheepBlock = instance.getBoolean( "shearSheepBlock",category,true,
				"A spiky block that damages and knocks back any colliding enitity."); 
		 
		fishingNetBlock = instance.getBoolean( "fishingNetBlock",category,true,
				"Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		 
		xRayBlock = instance.getBoolean( "xRayBlock",category,true,
				"Create an xray block to see through the world.  Intended for single player, not for cheating on servers."); 

		weatherBlock = instance.getBoolean( "weatherBlock",category,true,
				"Craft block that will run /toggledownfall whenever it gets a redstone signal."); 
		
		teleportBedBlock = instance.getBoolean( "teleportBedBlock",category,true,
				"Command block that teleports you to the world spawn");
		
		teleportSpawnBlock = instance.getBoolean( "teleportSpawnBlock",category,true,
				"Command block that teleports you to your bed"); 
		 
		gameruleBlockRegen = instance.getBoolean( "gameruleBlock_Regen",category,true,
				"Craft blocks that toggle '/gamerule naturalRegenration' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockDaylight = instance.getBoolean( "gameruleBlock_Daylight",category,true,
				"Craft blocks that toggle '/gamerule doDaylightCycle' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockFiretick = instance.getBoolean( "gameruleBlock_Firetick",category,true,
				"Craft blocks that toggle '/gamerule doFireTick' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockMobgrief = instance.getBoolean( "gameruleBlock_Mobgrief",category,true,
				"Craft blocks that toggle '/gamerule doMobGriefing' on redstone signal.  (Can never be opened or edited like a regular command block).");
	}
	
	private void recipes_new() 
	{
		category = "recipes_new";
		
		gravelToClay = instance.getBoolean( "gravelToClay",category,true,
				"Since clay in oceans has been replaced by pure gravel, this recipe lets you turn 4 clay, 4 dirt, and one bucket worth of water into Clay Blocks");
		
		netherwartPurpleDye = instance.getBoolean( "netherwartPurpleDye",category,true,
				"Craft bonemeal and netherwart into purple dye.");
		
		simpleDispenser = instance.getBoolean( "simpleDispenser",category,true,
				"Craft a dispenser with string in the center instead of a bow.  (Since string is stackable, this makes crafting tons of them much faster and cheaper).");
		 
		craftBooksWithoutLeather = instance.getBoolean( "craftBooksWithoutLeather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed.");
		
		craftableTransmuteRecords = instance.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record.");
    
		craftableBonemealColouredWool =  instance.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"			); 
  
		uncraftGeneral = instance.getBoolean( "uncrafting",category,true,
				"uncrafting: craft or smelt blocks back into their ingredients.  Often it is not a perfect trade.  " +
				"Example: Craft stairs back into blocks using a 4x4 pattern."	); 
		
		craftableMushroomBlocks =  instance.getBoolean( "craftableMushroomBlocks",category,true
				,"Craft mushroom blocks. ");
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
		
		CommandSearchItem.REQUIRES_OP = instance.getBoolean("searchitem_needs_op",category, false,
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
	public boolean gameruleBlockRegen;
	public boolean gameruleBlockDaylight;
	public boolean gameruleBlockFiretick;
	public boolean gameruleBlockMobgrief;
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
	public boolean smoothstoneToolsRequired;
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
	public boolean gravelToClay;
	public boolean fragileTorches;
	public boolean removeZombieCarrotPotato;
	public boolean petNametagChat;
	public boolean playerDeathCoordinates;
	public int obsidianHardness; 
	public int diamondOreHardness;
	public int emeraldOreHardness;
	public int spawnerHardness; 
	public boolean chest_sack;
	public boolean wandCopy;
	public boolean wandHarvest;
	public boolean respawn_egg;
	public boolean wandTransform;
	//public boolean wandProspect;
	//public int potionIdTired;
	public int potionIdWaterwalk;
	public int potionIdSlowfall;
	public int potionIdFlying;
	public float slowfallSpeed;
	public boolean flintTool;
	public int potionIdLavawalk;
	public boolean netherwartPurpleDye;
	public boolean worldGenClayOceans;
	public boolean saplingGrowthRestricted;
	public boolean saplingAllNether;
	public boolean saplingAllEnd;
	public int potionIdEnder; 
	public boolean wandWater;
	public boolean harvestGlassPickaxe;
	public boolean wandLightning;
	public boolean shearSheepBlock;
	public boolean storeLavaBlock;
	public boolean storeWaterBlock;
	public boolean storeMilkBlock;
	public boolean beetroot;
	public boolean flintPumpkin;
	public boolean endermenDropCarryingBlock;
	public boolean wandFireball;
	public boolean frozen_snowball;
	public int potionStackSize;
	public int potionIdFrozen;
	public int chanceZombieChildFeather;
	public int chanceZombieVillagerEmerald;
}
