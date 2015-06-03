package com.lothrazar.samsmagic;

import net.minecraftforge.common.config.Configuration;  
import com.lothrazar.samsmagic.entity.projectile.EntitySnowballBolt; 

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
 
 
		category = "potion_effect_ids";

		instance.addCustomCategoryComment(category, 
				"IDs are only exposed to avoid conflicts with other mods.  Messing with these might break the game.   ");
		
		potionIdWaterwalk = instance.get(category,"waterwalk_id", 40).getInt();
		  
		potionIdSlowfall = instance.get(category,"slowfall_id", 41).getInt();
		  
		potionIdFlying = instance.get(category,"flying_id", 42).getInt();
		
		//potionIdLavawalk = instance.get(category,"lavawalk_id", 43).getInt();
 
		potionIdFrozen = instance.get(category,"frost_id", 44).getInt();
 
		category = "potion_effect_tweaks";
		
		slowfallSpeed = instance.getFloat("potion_slowfall_speed",category, 0.41F,0.1F,1F,
    			"This factor affects how much the slowfall effect slows down the entity.");
 
		appleChocolate = instance.get( category,"apple_chocolate",true).getBoolean();   
		appleEmerald = instance.get( category,"apple_emerald",true).getBoolean();  
		appleEnder = instance.get( category,"apple_ender",true).getBoolean();  
		appleDiamond = instance.get(category, "apple_diamond",true).getBoolean(); 
		appleNetherwart = instance.get(category, "apple_netherwart",true).getBoolean();
		appleFrost = instance.get(category, "apple_frost",true).getBoolean(); 
		
		experience_bottle_return = instance.getBoolean("experience_bottle_return",category, true,
				"Experience bottles in survival mode return an empty glass bottle to you (if used on a block).");  
	
		//TODO: Spell Configs
		EntitySnowballBolt.secondsFrozenOnHit = instance.getInt("frozen_snowball.duration_on_hit",category, 25,1,600,
    			"When something hit by one of these snowballs, it gets the snow effect for this many seconds.");
		
		//String csv = instance.getString("spell_piston.ignored",category, "minecraft:cactus,minecraft:piston_head,minecraft:piston_extension,minecraft:lit_furnace,minecraft:melon_stem,minecraft:pumpkin_stem,minecraft:wheat,samscontent:beetroot_crop,wooden_door,minecraft:spruce_door,minecraft:birch_door,minecraft:jungle_door,minecraft:acacia_door,minecraft:dark_oak_door,minecraft:iron_door,minecraft:bedrock,minecraft:tripwire,minecraft:tripwire_hook,minecraft:stone_button,minecraft:wooden_button,minecraft:stone_pressure_plate,minecraft:wooden_pressure_plate,minecraft:heavy_weighted_pressure_plate,minecraft:light_weighted_pressure_plate,minecraft:redstone_wire,minecraft:mob_spawner,minecraft:ladder",
    	//		"List of ignored blocks that will not be moved.");
		//UtilPistonSpell.seIgnoreBlocksFromString(csv);
		
		
		
		if(instance.hasChanged()){ instance.save(); }
 
	}
	public boolean experience_bottle_return;
	 
	public boolean appleDiamond;
	//public boolean appleLapis;
	public boolean appleChocolate;
	public boolean appleEnder;

	public boolean appleNetherwart;

	public boolean appleEmerald;
 
 
  
	public int potionIdWaterwalk;
	public int potionIdSlowfall;
	public int potionIdFlying;
	public float slowfallSpeed; 
	//public int potionIdLavawalk;
	  
	public int potionIdFrozen; 
	public int heartsCatTamed;
	public boolean appleFrost;
}
