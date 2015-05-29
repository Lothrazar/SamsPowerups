package com.lothrazar.samscontent.cfg;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;  
import com.lothrazar.samscontent.command.*;  
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.item.*; 
import com.lothrazar.samscontent.spell.UtilBlockTransform;
import com.lothrazar.samscontent.spell.UtilPistonSpell;
import com.lothrazar.util.Reference;

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

		blocks();
		commands();
		creative();
		debug_info();
		harvesting_changes();
		inventory();
		items(); 
		mob_changes();
		mob_spawning(); 
		more_chest_loot(); 
		nature();
		ocean_generation();
		player();
		pocket_edition();  
		potions(); 
	    recipes_new();   
		recipes_changes();
		
		  
		category = "tweaks";//these are the misc. changes i made that have no clear category yet
		
		skullSignNames = instance.getBoolean("skull_sign_names",category, true,
    			"Hit a sign with a player skull to make the skull take on the name (skin) of the first word/line on the sign");

		fragileTorches = instance.getBoolean("fragile_torches",category, true,
				"Torches have a chance to break when living entity colides with it (unless it is a sneaking player).");  
		 
		experience_bottle_return = instance.getBoolean("experience_bottle_return",category, true,
				"Experience bottles in survival mode return an empty glass bottle to you (if used on a block).");  
		  
		if(instance.hasChanged()){ instance.save(); }
	}

	private void inventory() 
	{
		category = "inventory";
		
		increasedStackSizes = instance.getBoolean("stack_size",category, true,
			"While true, most vanilla items and blocks have their max stack size increased to 64 (not tools/armor/potions).  ");

		swiftDeposit = instance.getBoolean("swift_deposit",category, true,
    			"Punch a chest while sneaking to merge items from your inventory into existing item stacks in the chest."	); 
	}

	private void player() 
	{
		category = "player";

		playerDeathCoordinates = instance.getBoolean("player_death_coordinates",category, false,
    			"Players will have their death point coordinates broadcast in chat.");
		
		dropPlayerSkullOnDeath = instance.getBoolean("drop_player_skull_on_death",category, true,
    			"Players will drop their skull when they die for any reason.");

		sleeping_hunger_seconds = instance.getInt("sleeping_hunger_seconds",category, 60,0,999,
    		"Number of seconds of hunger effect you get if you skip the night in a bed (so 0 for vanilla).");
	}

	public void pocket_edition() 
	{
		category = "pocket_edition";
				
		beetroot =  instance.getBoolean( "beetroot",category,true,
				"Add beetroot to the game.  The same as pocket edition, except they do not work with pigs.  " );
	}
	
	private void mob_changes() 
	{
		category = "mob_changes";
		
		heartsPlayerStart = instance.getInt("health_player_start",category, 3,1,100,
    			"Change the number of hearts a player starts with.  10 hearts is the vanilla default.");
		
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
		 
	}

	private void nature() 
	{
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
	}

	private void ocean_generation() 
	{
		category = "ocean_generation";
	
		worldGenOceansNotUgly = instance.getBoolean("alter_ocean_floor",category, true,
    			"Clay, sand, and dirt can generate in oceans just like they used to in the old days.  Replaces the gravel in patches.");
	 
		clayNumBlocks = instance.get(category,"clay_size",16).getInt();
		clayChance = instance.get(category,"clay_chance",65).getInt();
		
		sandNumBlocks = instance.get(category,"sand_size",20).getInt();
		sandChance = instance.get(category,"sand_chance",45).getInt();

		dirtNumBlocks = instance.get(category,"dirt_size",14).getInt();
		dirtChance = instance.get(category,"dirt_chance",30).getInt();
	}

	private void potions() 
	{ 
		category = "potion_effect_ids";

		instance.addCustomCategoryComment(category, 
				"IDs are only exposed to avoid conflicts with other mods.  Messing with these might break the game.   ");
		
		potionIdWaterwalk = instance.get(category,"waterwalk_id", 40).getInt();
		  
		potionIdSlowfall = instance.get(category,"slowfall_id", 41).getInt();
		  
		potionIdFlying = instance.get(category,"flying_id", 42).getInt();
		
		potionIdLavawalk = instance.get(category,"lavawalk_id", 43).getInt();
 
		potionIdFrozen = instance.get(category,"frost_id", 44).getInt();

		category = "potion_effect_tweaks";
		
		slowfallSpeed = instance.getFloat("potion_slowfall_speed",category, 0.41F,0.1F,1F,
    			"This factor affects how much the slowfall effect slows down the entity.");
	}
  
	private void creative() 
	{
		category = "creative_inventory_added";
		 
		mushroomBlocksCreativeInventory = instance.get(category,"mushroom_blocks", true).getBoolean();

		barrierCreativeInventory = instance.get(category,"barrier", true).getBoolean();
		
		dragonEggCreativeInventory = instance.get(category,"dragon_egg", true).getBoolean();
		
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
 
	}

	private void harvesting_changes() 
	{
		category = "harvesting_changes";
		
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
	}
  
	private void mob_spawning() 
	{
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
	}
	 
	private void debug_info() 
	{
		category = "debug_screen_f3";
		
		debugGameruleInfo = instance.getBoolean("gamerule_info_sneaking",category, true,
    			"If you are sneaking, the right side shows all the game rules, on or off. " );
		 
		reducedDebugImproved = instance.getBoolean("reducedDebugInfo_improved",category, true,
    			"If this gamerule is turned on, then much more useless information is cleared away (with coordinates still hidden), but some is added back in such as the biome name. " );
		
		debugSlime = instance.getBoolean("slime",category, true,
    			"Shows if you are standing in a slime chunk." );
		
		debugHorseInfo = instance.getBoolean("horse",category, true,
    			"Shows info on any horse ridden including speed, jump height, species.");
		
		debugVillageInfo = instance.getBoolean("village",category, true,
    			"Shows info on any village you are standing in.");
	}
	
	private void more_chest_loot() 
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
 
		emerald_armor = instance.getBoolean("emerald_armor",category, true,
				"Adds a full set of emerald armor and tools.  Better enchantability than gold, and better stats than diamond, but requires emerald blocks.");  
		  
		horse_food_upgrades = instance.getBoolean("horse_food_upgrades",category, true,
				"Adds three items that let you upgrade a horses health, change its colour and change it into a zombie or skeleton horse.");  
		  
		enderBook = instance.getBoolean( "ender_book",category,true,
				" Craft an ender book that lets you save a waypoint, and then teleport to it later (single use).  Do not use this outside the overworld.");
		
		ItemEnderBook.DURABILITY  = instance.getInt("ender_book.durability",category, 16,1,64,
    			"Durability of the book, after saving a location in the book.");
	 
		appleChocolate = instance.get( category,"apple_chocolate",true).getBoolean();   
		appleEmerald = instance.get( category,"apple_emerald",true).getBoolean();  
		appleDiamond = instance.get(category, "apple_diamond",true).getBoolean(); 
		appleNetherwart = instance.get(category, "apple_netherwart",true).getBoolean();
		
	
		carbon_paper = instance.getBoolean("carbon_paper",category, true,
    			"Craft a wand that can copy and paste note blocks and signs.");
 
	 
 
		respawn_egg = instance.getBoolean("respawn_egg",category, true,
    			"Use an empty respawn egg to turn an mob into a respawn egg.  This works the same as a regular spawn egg, but does not interact with mob spawners.  Works only on livestock/passive mobs, not hostiles.");
  
		
		
		//TODO: Spell Configs
		EntitySnowballBolt.secondsFrozenOnHit = instance.getInt("frozen_snowball.duration_on_hit",category, 25,1,600,
    			"When something hit by one of these snowballs, it gets the snow effect for this many seconds.");
		
		String csv = instance.getString("spell_piston.ignored",category, "minecraft:cactus,minecraft:piston_head,minecraft:piston_extension,minecraft:lit_furnace,minecraft:melon_stem,minecraft:pumpkin_stem,minecraft:wheat,samscontent:beetroot_crop,wooden_door,minecraft:spruce_door,minecraft:birch_door,minecraft:jungle_door,minecraft:acacia_door,minecraft:dark_oak_door,minecraft:iron_door,minecraft:bedrock,minecraft:tripwire,minecraft:tripwire_hook,minecraft:stone_button,minecraft:wooden_button,minecraft:stone_pressure_plate,minecraft:wooden_pressure_plate,minecraft:heavy_weighted_pressure_plate,minecraft:light_weighted_pressure_plate,minecraft:redstone_wire,minecraft:mob_spawner,minecraft:ladder",
    			"List of ignored blocks that will not be moved.");
		UtilPistonSpell.seIgnoreBlocksFromString(csv);
 
	}
	
	private void blocks() 
	{
		category = "blocks";
		 
		block_fragile  = instance.getBoolean( "scaffolding",category,true,
				"Scaffolding block that decays on its own over time, so its better than spamming dirt everywhere."); 		 
				
		storeBucketsBlock = instance.getBoolean( "store_buckets",category,true,
				"A block that stores any number of milk/water/lava buckets (click to insert / withdraw)."); 
	 
		shearSheepBlock = instance.getBoolean( "shear_sheep",category,true,
				"Shears adult sheep that collide with this block."); 
		 
		fishingNetBlock = instance.getBoolean( "fishing_net",category,true,
				"Place the fishing block in deep water and it will randomly spawn fish with the same odds as a pole (but no treasures or junk)."); 
	 
		weatherBlock = instance.getBoolean( "weather",category,true,
				"Block that will run /toggledownfall whenever it gets a redstone signal.  Uses command block functions but is not editable by players."); 
		
		teleportBedBlock = instance.getBoolean( "teleport_bed",category,true,
				"Block that teleports you to the world spawn.  Uses command block functions but is not editable by players.");
		
		teleportSpawnBlock = instance.getBoolean( "teleport_spawn",category,true,
				"Block that teleports you to your bed.  Uses command block functions but is not editable by players."); 
	}
	
	private void recipes_new() 
	{
		category = "recipes_new";
		
		cheaper_stairs = instance.getBoolean( "cheaper_stairs",category,true,
				"Craft stairs in your inventory 2x2 grid.  This recipe is cheaper and more logical than the original.");
		
		smelt_gravel = instance.getBoolean( "smelt_gravel",category,true,
				"Smelt gravel into flint to save on mindless shovel digging.");
		
		experience_bottle = instance.getBoolean( "experience_bottle",category,true,
				"Craft experience bottles from many (vanilla) mob drops and empty bottles.  Also fish.");
		 
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
	 
	}
	
	private void commands() 
	{
		category = "commands";
 
		cmd_ping = instance.getBoolean("ping",category, true,
    			"A command that simply displays your coordinates in chat.  Also use '/ping nether' to show the nether version of your coords.  Useful only if your F3 coordinates are hidden, for example with reducedDebugInfo = true gamerule.");
		
		PlaceLib.allowedFromConfig = instance.getString("place.filter",category, "minecraft:dirt,samscontent:block_fragile",
    			"Filter which blocks can be placed with ALL place commands.  Empty string in this filter means everything is allowed.");
		PlaceLib.XP_COST_PER_PLACE = instance.getInt("place.xp_cost", category, 1, 0, 99, 
				"Experience drained each time a block is placed with one of these commands.");
		
		cmd_effectpay = instance.getBoolean("effectpay",category, true,
    			"Similar to /effect, but it drains exp for each second you get.");
		CommandEffectPay.XP_COST_PER_SECOND = instance.getInt("effectpay.exp_per_second", category, 5, 1, 64, 
				"Experience cost per second of potion effect.");
		CommandEffectPay.REQUIRES_OP = instance.getBoolean("effectpay.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
	
		cmd_place_blocks = instance.getBoolean("place",category, true,
    			"Use /place to put blocks in the world from your survival inventory.  It will only replace air blocks, and can skip blocks with its arguments.");

		CommandPlaceBlocks.REQUIRES_OP = instance.getBoolean("place.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
 
		cmd_kit = instance.getBoolean("kit",category, true,
    			"Use /kit to give yourself kit items.  Can only be done once each time you die.");

		String csv = instance.getString("kit.items",category, "minecraft:wooden_pickaxe,minecraft:wooden_axe,minecraft:crafting_table",
    			"Using /kit gives the following item.  Each must have minecraft:item or modname:item, no spaces and split by commas.");
		CommandKit.setItemsFromString(csv); 
		
		CommandHome.REQUIRES_OP = instance.getBoolean("home.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		cmd_home = instance.getBoolean("home",category, true,
    			"Use /home to go to the players spawn point, as defined by a bed."); 
		
		CommandHome.REQUIRES_OP = instance.getBoolean("home.needs_op",category, true,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");

		worldhome = instance.getBoolean("worldhome",category, true,
    			"Use /worldhome to go to the worlds global spawn point.");  
		
		CommandWorldHome.REQUIRES_OP = instance.getBoolean("worldhomehome.needs_op",category, true,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		cmd_searchspawner = instance.getBoolean("searchspawner",category, true,
    			"Players can search for spawners placed in the world.  Result is only chat output.");
 
		CommandSearchSpawner.REQUIRES_OP = instance.getBoolean("searchspawner.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		
		cmd_searchtrade = instance.getBoolean("searchtrade",category, true,
    			"Players can search the trades of nearby villagers.  Result is only chat output.");
		
		CommandSearchTrades.REQUIRES_OP = instance.getBoolean("searchtrade.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
 
		cmd_searchitem = instance.getBoolean("searchitem",category, true,
    			"Players can search nearby chests for items.   Result is only chat output."    		); 
		
		CommandSearchItem.REQUIRES_OP = instance.getBoolean("searchitem.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
 
		cmd_enderchest = instance.getBoolean("enderchest",category, true,
    			"Players can open their enderchest with a command, no item needed."    		); 
		
		CommandEnderChest.REQUIRES_OP = instance.getBoolean("enderchest.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
		 
		cmd_simplewaypoint = instance.getBoolean("simplewaypoint",category, true,
    			"Command that lets players save waypoints that then show up in the F3 debug screen, so we can navigate back to it (no tp)."    		); 
		
		CommandSimpleWaypoints.REQUIRES_OP = instance.getBoolean("simplewaypoint.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
 
		cmd_todo = instance.getBoolean("todo",category, true,
    			"Command that lets players use /todo myreminder text, which will then show whatever text they put on the F3 debug screen."); 
		
		CommandTodoList.REQUIRES_OP = instance.getBoolean("todo.needs_op",category, false,
    			"Command is restricted to players with OP (or single player worlds with cheats enabled).");
	
		cmd_recipe = instance.getBoolean("recipe",category, true,
				"Command to display recipe of the players held item in chat."); 
		
		cmd_uses = instance.getBoolean("uses",category, true,
				"Command to display uses of the players held item in chat.");
	
	
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
	public boolean enderBook;
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
	public boolean appleEmerald;
 
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
	public boolean cmd_effectpay;
	public boolean cmd_ping;
	public int heartsPlayerStart;
	public int heartsWolfTamed;
	public int heartsVillager;
	public int heartsCatTamed;
}
