package com.lothrazar.samscontent;

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

import com.lothrazar.command.*; 
import com.lothrazar.event.HandlerPlayerHarvest;
import com.lothrazar.event.HandlerRichAnimals;
import com.lothrazar.item.ItemWandBuilding;
import com.lothrazar.item.ItemWandHarvest;

public class ConfigFile
{ 
	private Configuration instance;
	public Configuration instance()
	{
		return instance;
	}
	//to go between main and sub levels nested in the json style cfg file
	private static String LevelSep = ".";
	String category = "";
	
	//public boolean moreFutureTrades = true; 
	public boolean swiftDeposit;
	public boolean smartEnderchest;
	public boolean increasedStackSizes;
	public boolean moreFuel;
	public boolean skullSignNames; 
	public boolean craftableTransmuteRecords;    
	public boolean craftableBonemealColouredWool;   
	public boolean craftBooksWithoutLeather;
	//public boolean craftableMobHeads;
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
	public boolean theEndSafeFall;
	public boolean plantDespawningSaplings;
	public boolean noDamageEnderPearl;
	public boolean buildingWand;
	public boolean simpleDispenser; 
	public boolean dropPlayerSkullOnDeath;
	public boolean searchspawner;
	public int chanceReturnEnderPearl;
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
	public boolean wandFire;
	public boolean wandChest;
	public boolean wandCopy;
	public boolean wandHarvest;
	public boolean wandLivestock;
	public boolean wandTransform;
	public boolean wandProspect;
	public int potionIdTired;
	public int potionIdWaterwalk;
	public int potionIdSlowfall;
	public int potionIdFlying;
	public float slowfallSpeed; 
	
	public ConfigFile(Configuration c)
	{
		instance = c; 

		commands();
		
	    crafting();  
		 
		new_blocks_items(); 

		loot_chests();
		 
		debug_info();
		 
		spawning(); 
		
		toolChanges();
		 
		convenience();

		 builderswand(); 
		 
		 wands();
		 
		 potions();
  
		 HandlerRichAnimals.LivestockLootScaleFactor  = instance.getInt("LivestockLootScaleFactor",category, 5,0,32,
	    			"Scale factor to multiply drops from livestock: including sheep, chicken, horse, cow, rabbit, and also pigs get double this factor again.");
			 
		 petNametagDrops = instance.getBoolean("petNametagDrops",category, true,
	    			"Pets (Wolf, ocelot, villager, bat, rabbit, horse) that are named drop a name tag when they die.");
 
		 petNametagChat  = instance.getBoolean("nametagDeathMessages",category, true,
	    			"Non player entities that are named with a Name Tag send a chat death message when they die.");
		 
		//category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		if(instance.hasChanged()){ instance.save(); }
	}

	private void potions() {

		category = "potion_ids";
		
		potionIdWaterwalk = instance.getInt("potionIdWaterwalk",category, 40,33,200,
    			"ID is only exposed to avoid conflicts with other mods");
		 
		potionIdTired = instance.getInt("potionIdTired",category, 41,33,200,
    			"ID is only exposed to avoid conflicts with other mods");
		  
		potionIdSlowfall = instance.getInt("potionIdSlowfall",category, 42,33,200,
    			"ID is only exposed to avoid conflicts with other mods");
		  
		potionIdFlying = instance.getInt("potionIdFlying",category, 43,33,200,
    			"ID is only exposed to avoid conflicts with other mods");
		
		
		slowfallSpeed = instance.getFloat("slowfallSpeed",category, 0.41F,0.1F,1F,
    			"ID is only exposed to avoid conflicts with other mods");
	}

	private void wands() 
	{

		wandFire = instance.getBoolean("wandFire",category, true,
    			" .");
		wandChest = instance.getBoolean("wandChest",category, true,
    			" .");
		wandCopy = instance.getBoolean("wandCopy",category, true,
    			" .");
		wandHarvest = instance.getBoolean("wandHarvest",category, true,
    			" .");
		wandLivestock = instance.getBoolean("wandLivestock",category, true,
    			" .");
		wandTransform = instance.getBoolean("wandTransform",category, true,
    			" .");
		wandProspect = instance.getBoolean("wandProspect",category, true,
    			" ."); 
		
	}

	private void convenience() 
	{
		category = "convenience"; 	
		 
		mushroomBlocksCreativeInventory = instance.getBoolean("mushroomBlocksCreativeInventory",category, true,
    			"Add the (missing) mushroom blocks into creative inventory where they should be.");

		barrierCreativeInventory = instance.getBoolean("barrierCreativeInventory",category, true,
    			"Add barrier into creative inventory.");
		
		dragonEggCreativeInventory = instance.getBoolean("dragonEggCreativeInventory",category, true,
    			"Add dragon egg into creative inventory.");
		
		farmlandCreativeInventory = instance.getBoolean("farmlandCreativeInventory",category, true,
    			"Add farmland into creative inventory.");
		
		spawnerCreativeInventory = instance.getBoolean("spawnerCreativeInventory",category, true,
    			"Add spawner into creative inventory.");
    	
		playerDeathCoordinates = instance.getBoolean("playerDeathCoordinates",category, true,
    			"Players will have their death point coordinates broadcast in chat.");
		
		dropPlayerSkullOnDeath = instance.getBoolean("dropPlayerSkullOnDeath",category, true,
    			"Players will drop their skull when they die.");
 		
		//numbers are Default, Min, Max
		chanceReturnEnderPearl = instance.getInt("noDamageEnderPearl",category, 50,0,100,
    			"No damage taken from an ender pearl throw");
				
				
		noDamageEnderPearl = instance.getBoolean("noDamageEnderPearl",category, true,
    			"No damage taken from an ender pearl throw");

		plantDespawningSaplings = instance.getBoolean("plantDespawningSaplings",category, true,
    			"When a sapling (or mushroom) despawns while sitting on grass or dirt, it will instead attempt to plant itself.");

		theEndSafeFall = instance.getBoolean("theEndSafeFall",category, true,
    			"Falling off the world in the end will instead teleport you to the top, making a falling loop.");

		increasedStackSizes = instance.getBoolean("increasedStackSizes",category, true,
			"While true, many items and blocks (not tools/armor/potions) have their max stack size increased to 64.  " +
			"Included are: ender pearl, egg, snowball, cookie, mushroom stew, boat, all minecarts, all doors, cake, saddle, " +
			"horse armor, empty bucket, bed, all records."); 
		
		moreFuel = instance.getBoolean("moreFuel",category, true,
    			"More can be used as furnace fuel: seeds, leaves, paper, shrubs"); 
	 
		swiftDeposit = instance.getBoolean("swiftDeposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	); 
		
		smartEnderchest = instance.getBoolean("smartEnderchest",category, true,
    			"Attack with the ender chest to open it without placing it."	);
		 
		skullSignNames = instance.getBoolean("skullSignNames",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");
		 
		betterBonemeal = instance.getBoolean("betterBonemeal",category, true,
    			"Bonemeal grows more things: lilypads, all flowers, and reeds. ");
	}

	private void toolChanges() 
	{
		category = "tools_armor";
		

		 obsidianHardness  = instance.getInt("obsidianHardness",category, 10,1,50,
	    			"Hardness level of Obsidian (vanilla is 50).");

		 diamondOreHardness  = instance.getInt("diamondOreHardness",category, 10,1,50,
	    			"Hardness level of Obsidian (vanilla is 3).");
		 
		 emeraldOreHardness  = instance.getInt("emeraldOreHardness",category, 10,1,50,
	    			"Hardness level of Obsidian (vanilla is 3).");
		 
		 spawnerHardness  = instance.getInt("spawnerHardness",category, 50,1,50,
	    			"Hardness level of Obsidian (vanilla is 5).");
		 
		fragileTorches = instance.getBoolean("fragileTorches",category, true,
				"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		
		furnaceNeedsCoal = instance.getBoolean("furnaceNeedsCoal",category, true,
				"Crafting a furnace now requires one coal in the center.");  
		
		smoothstoneToolsRequired = instance.getBoolean("smoothstoneToolsRequired",category, true,
				"Stone tools will require smoothstone instead of cobble.");  

		tieredArmor = instance.getBoolean("tieredArmor",category, true,
				"Crafting Iron armor requires repaired leather armor as part of the recipe.  Diamond armor requires chain mail.");  
		
		String csv = instance.getString("harvestOnlyShovel",category, "minecraft:dirt,minecraft:sand",
    			"If these blocks are not harvested by a shovel, they will break but have no drops (the same way that breaking stone by hand gives no drops)."); 
		HandlerPlayerHarvest.setShovelFromCSV(csv);
	 
		String csvaxe = instance.getString("harvestOnlyAxe",category, "minecraft:log,minecraft:log2",
    			"harvestOnlyAxeharvestOnlyAxeharvestOnlyAxeharvestOnlyAxeharvestOnlyAxeno drops)."); 
		HandlerPlayerHarvest.seAxeFromCSV(csvaxe);
		 
	}
	
	private void spawning() 
	{
		category = "spawning";
		removeZombieCarrotPotato = instance.getBoolean("removeZombieCarrotPotato",category, true,
    			"Disable these zombie drops."); 
		spawnBlazeDesertHills = instance.getBoolean("spawn.BlazeDesertHills",category, true,
    			"Blazes spawn naturally in Desert Hills."); 
    		
		spawnMagmaCubeDesert = instance.getBoolean("spawn.MagmaCubeDesert",category, true,
    			"Magma cubes spawn naturally in Desert."); 
    		
		spawnCaveSpiderMesa = instance.getBoolean("spawn.CaveSpiderMesa",category, true,
    			"Cave spiders spawn naturally in Mesa. "); 
    		
		spawnCaveSpiderRoofedForest = instance.getBoolean("spawn.CaveSpiderRoofedForest",category, true,
    			"Cave Spiders spawn naturally in Roofed Forest."); 
    		
		spawnSnowgolemsIceMountains = instance.getBoolean("spawn.SnowgolemsIceMountains",category, true,
    			"Snow Golems spawn naturally in Ice Mountains. "); 
    		
		spawnGhastDeepOcean = instance.getBoolean("spawn.GhastDeepOcean",category, true,
    			"Ghasts spawn naturally in Deep Ocean (above). "); 
    		
		spawnHorseIcePlains = instance.getBoolean("spawn.HorseIcePlains",category, true,
    			"Horses spawn naturally in Ice Plains. "); 
    		
		spawnHorseOceanIslands = instance.getBoolean("spawn.HorseOceanIslands",category, true,
    			"Horses pawn naturally in Deep Ocean (islands). "); 
    		
		spawnHorseExtremeHills = instance.getBoolean("spawn.HorseExtremeHills",category, true,
    			"Horses pawn naturally in Extreme Hills. "); 
    		
		spawnVillagerExtremeHills = instance.getBoolean("spawn.VillagerExtremeHills",category, true,
    			"Villagers pawn naturally in Extreme Hills (not village buildings, it just rarely spawns a villager instead of another passive mob). "); 
    
		spawnCaveSpiderJungle = instance.getBoolean("spawn.CaveSpiderJungle",category, true,
    			"Cave Spiderspawn naturally in Jungle. "); 
	}
	 
	private void debug_info() 
	{
		category = "debugScreen";
		  
		debugClearRight = instance.getBoolean("debugClearRight",category, false,
    			"Clears the right side of F3 completely. "
    		);
		debugSlime = instance.getBoolean("slimeChunk",category, true,
    			"Screen will show if you are standing in a slime chunk."
    		);
		debugHorseInfo = instance.getBoolean("horse",category, true,
    			"Screen will show info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = instance.getBoolean("village",category, true,
    			"Screen will show info on any village you are standing in.");
	}
	
	private void loot_chests() 
	{
		category = "lootMore";
 
		lootObsidian = instance.getBoolean("obsidian",category, true,
    			"Add obsidian as a random treasure from naturally spawned chests "	);
  
		lootAllRecords = instance.getBoolean("allRecords",category, true,
    			"Add all record types as a random treasure from naturally spawned chests ");
 
		lootGlowstone = instance.getBoolean("glowstone",category, true,
    			"Add glowstone dust as a random treasure from naturally spawned chests ");
 
		lootQuartz = instance.getBoolean("quartz",category, true,
    			"Add quartz as a random treasure from naturally spawned chests ");
	}
	
	private void new_blocks_items() 
	{
		category = "new_blocks_items";

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
		 
		fishingNetBlock = instance.getBoolean( "fishingNetBlock",category,true,
				" Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
		 
		xRayBlock = instance.getBoolean( "xRayBlock",category,true,
				" Create an xray block to see through the world.  Intended for single player, not for cheating on servers."); 
	  
		enderBook = instance.getBoolean( "enderBook",category,true,
				" Craft an ender book that lets you save waypoints, and then teleport to them later (only in the overworld)."); 
		
		weatherBlock = instance.getBoolean( "weatherBlock",category,true,
				"Craft block that will run /toggledownfall whenever it gets a redstone signal."); 
		
		teleportBedBlock = instance.getBoolean( "teleportBedBlock",category,true,
				"Command block that teleports you to the world spawn");
		
		teleportSpawnBlock = instance.getBoolean( "teleportSpawnBlock",category,true,
				"Command block that teleports you to your bed"); 
		 
		gameruleBlockRegen = instance.getBoolean( "gameruleBlock.Regen",category,true,
				"Craft blocks that toggle '/gamerule naturalRegenration' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockDaylight = instance.getBoolean( "gameruleBlock.Daylight",category,true,
				"Craft blocks that toggle '/gamerule doDaylightCycle' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockFiretick = instance.getBoolean( "gameruleBlock.Firetick",category,true,
				"Craft blocks that toggle '/gamerule doFireTick' on redstone signal.  (Can never be opened or edited like a regular command block)."); 
		
		gameruleBlockMobgrief = instance.getBoolean( "gameruleBlock.Mobgrief",category,true,
				"Craft blocks that toggle '/gamerule doMobGriefing' on redstone signal.  (Can never be opened or edited like a regular command block).");
	
	
		//TODO: config for each wand
	
	
	
	}
	
	private void crafting() 
	{
		category = "morecrafting";
		gravelToClay = instance.getBoolean( "gravelToClay",category,true,
				"Since clay in oceans has been replaced by pure gravel, this recipe lets you turn 4 clay, 4 dirt, and one bucket worth of water into Clay Blocks");
		 
		/*
		craftObsidian = instance.getBoolean( "craftObsidian",category,true,
				"Create obsidian with lava bucket and ice.  Create a lava bucket with obsidian, a fire charge, and an empty bucket.");
	 */
		simpleDispenser = instance.getBoolean( "simpleDispenser",category,true,
				"Craft a dispenser with string in the center instead of a bow.  (Since string is stackable, this makes crafting tons of them much faster and cheaper).");
		 
		craftBooksWithoutLeather = instance.getBoolean( "craftBooksWithoutLeather",category,true,
				"This allows use the old book crafting recipe from previous versions of the game; three paper but no leather needed.");
		
		craftableTransmuteRecords = instance.getBoolean( "transmuteRecords",category,true,
			"This allows you to surround any record in emeralds to transmute it into a different record.");
   
	//	craftableFlatDoubleSlab = instance.getBoolean( "craftableFlatDoubleSlab",category,true,
		//	"Craft the stone and sandstone hidden double slabs - 43:8 and 43:9, by making a 'door' shape with the regular stone slabs."		);

		craftableBonemealColouredWool =  instance.getBoolean( "craftableBonemealColouredWool",category,true
				,"Allows you to dye coloured wool back to white using bonemeal"			); 
  /*
		craftableMobHeads =  instance.getBoolean( "craftableMobHeads",category,true
				,"Allows you to craft all mob heads out of wither skulls.  Surround the skull with "+
				"TNT, flesh, cake, or bones. "		);  
 */
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
	
	public void builderswand( )
	{ 
		String category = "builders_wand";

		buildingWand = instance.getBoolean( "enabled",category,true,
				"Can craft and use a building wand that can store many stacks of items, and replace blocks without mining.");  
		
		ItemWandBuilding.replaceBedrock = instance.getBoolean(category, "replaceBedrock",true,
			"Set true to allow the building wand to affect bedrock.  "
		);
		
		ItemWandBuilding.replaceObsidian = instance.getBoolean(category, "replaceObsidian",true,
			 "Set true to allow the building wand to affect obsidian.  "
		);
		 
		ItemWandBuilding.replaceTileEntities = instance.getBoolean(category, "replaceTileEntities",true,
			 "Set true to allow the building wand to affect Tile Entities - which is anything with an invnetory " +
			 "(such as chest or dispenser).   "
		);
	}
}
