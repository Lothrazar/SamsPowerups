package com.lothrazar.samslooting;

import net.minecraftforge.common.config.Configuration;

public class ConfigLoot
{ 
	private Configuration instance;
	private String category = ModLoot.MODID; 

	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigLoot(Configuration c)
	{
		instance = c; 
		instance.load();
   
		lootObsidian = instance.get(category,"obsidian", true).getBoolean();
  
		lootAllRecords = instance.get(category,"records", true).getBoolean();
 
		lootGlowstone = instance.get(category,"glowstone", true).getBoolean();
 
		lootQuartz = instance.get(category,"quartz", true).getBoolean();
		
		if(instance.hasChanged()){ instance.save(); }
	}
 
	public boolean lootObsidian;
	public boolean lootAllRecords;
	public boolean lootGlowstone;
	public boolean lootQuartz;
}
