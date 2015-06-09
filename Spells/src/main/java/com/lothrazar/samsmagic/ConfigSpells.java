package com.lothrazar.samsmagic;

import net.minecraftforge.common.config.Configuration;  
import com.lothrazar.samsmagic.entity.projectile.EntitySnowballBolt; 

public class ConfigSpells
{ 
	private Configuration instance;
	private String category = "";
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigSpells(Configuration c)
	{
		instance = c; 
		instance.load();
 
		category = ModSpells.MODID;
		
		experience_bottle_return = instance.getBoolean("experience_bottle_return",category, true,
				"Experience bottles in survival mode return an empty glass bottle to you (if used on a block).");  
	
		category = "effect_ids";

		instance.addCustomCategoryComment(category, 
				"IDs are only exposed to avoid conflicts with other mods.  Messing with these might break the game.   ");
		
		potionIdWaterwalk = instance.get(category,"waterwalk_id", 40).getInt();
		  
		potionIdSlowfall = instance.get(category,"slowfall_id", 41).getInt();
		  
		//potionIdFlying = instance.get(category,"flying_id", 42).getInt();
		
		//potionIdLavawalk = instance.get(category,"lavawalk_id", 43).getInt();
 
		potionIdFrozen = instance.get(category,"frost_id", 43).getInt();
 
		category = "effect_tweaks";
		
		slowfallSpeed = instance.getFloat("slowfall_speed",category, 0.41F,0.1F,1F,
    			"This factor affects how much the slowfall effect slows down the entity.");
 
		//TODO: Spell Configs
		EntitySnowballBolt.secondsFrozenOnHit = instance.getInt("frost_duration_on_hit",category, 25,1,600,
    			"When something hit by one of these snowballs, it gets the snow effect for this many seconds.");
		
		//String csv = instance.getString("spell_piston.ignored",category, "minecraft:cactus,minecraft:piston_head,minecraft:piston_extension,minecraft:lit_furnace,minecraft:melon_stem,minecraft:pumpkin_stem,minecraft:wheat,samscontent:beetroot_crop,wooden_door,minecraft:spruce_door,minecraft:birch_door,minecraft:jungle_door,minecraft:acacia_door,minecraft:dark_oak_door,minecraft:iron_door,minecraft:bedrock,minecraft:tripwire,minecraft:tripwire_hook,minecraft:stone_button,minecraft:wooden_button,minecraft:stone_pressure_plate,minecraft:wooden_pressure_plate,minecraft:heavy_weighted_pressure_plate,minecraft:light_weighted_pressure_plate,minecraft:redstone_wire,minecraft:mob_spawner,minecraft:ladder",
    	//		"List of ignored blocks that will not be moved.");
		//UtilPistonSpell.seIgnoreBlocksFromString(csv);
		
		
		
		if(instance.hasChanged()){ instance.save(); }
 
	}
	
	public boolean experience_bottle_return;
 
	public int potionIdWaterwalk;
	public int potionIdSlowfall;
	//public int potionIdFlying;
	public float slowfallSpeed; 
	//public int potionIdLavawalk;
	  
	public int potionIdFrozen;  
}
