package com.lothrazar.samslooting;

import org.apache.logging.log4j.Logger;     
 




import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
  
@Mod(modid = ModLoot.MODID, useMetadata = true )  
public class ModLoot
{	
	public static final String MODID = "samslooting";
	public static final String TEXTURE_LOCATION = MODID + ":";

	@Instance(value = MODID)
	public static ModLoot instance;	

	public static Logger logger; 
	public static ConfigLoot cfg;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigLoot(new Configuration(event.getSuggestedConfigurationFile()));
	  
	}
	 
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{ 
		ChestLootGenerator.regsiterLoot();
	}
}
