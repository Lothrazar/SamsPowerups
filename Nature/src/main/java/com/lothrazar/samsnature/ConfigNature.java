package com.lothrazar.samsnature;

import net.minecraftforge.common.config.Configuration;

public class ConfigNature
{ 
	private Configuration instance;
	private String category = "";
	public boolean plantDespawningSaplings;
	//public boolean beetroot;
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigNature(Configuration c)
	{
		instance = c; 
		instance.load();
  
 
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
 
		category = "block_properties";
		
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

		category = "natural_chests";

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


	public boolean moreFuel;
	public boolean skullSignNames; 

 
	public boolean bonemealAllFlowers;
	public boolean bonemealLilypads;
	public boolean bonemealReeds;
	public boolean lootObsidian;
	public boolean lootAllRecords;
	public boolean lootGlowstone;
	public boolean lootQuartz; 
	public boolean simpleDispenser; 
	public boolean dropPlayerSkullOnDeath;

	public int obsidianHardness; 
	public int diamondOreHardness;
	public int emeraldOreHardness;
	public int spawnerHardness; 
  
 
  
	public boolean worldGenOceansNotUgly;
	public boolean saplingGrowthRestricted;
	public boolean saplingAllNether;
	public boolean saplingAllEnd; 
	public boolean harvestGlassPickaxe;

	public boolean flintPumpkin;
	public boolean endermenDropCarryingBlock;

	public float redstoneOreHardness;
 
}
