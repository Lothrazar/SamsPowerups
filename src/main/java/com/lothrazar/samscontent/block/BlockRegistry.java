package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

public class BlockRegistry 
{ 
	public static BlockCommandBlockCraftable command_block_regen;
	public static BlockCommandBlockCraftable command_block_mobgrief;
	public static BlockCommandBlockCraftable command_block_firetick;
	public static BlockCommandBlockCraftable command_block_daycycle;
	public static BlockCommandBlockCraftable command_block_weather ;
	public static BlockCommandBlockCraftable command_block_tpspawn;
	public static BlockCommandBlockCraftable command_block_tpbed; 
	public static BlockFishing block_fishing ;
	public static BlockXRay block_xray ;
	
	public static void registerBlocks() 
	{  
		if(ModLoader.configSettings.fishingNetBlock)
		{
			BlockRegistry.block_fishing = new BlockFishing(); 
			
			SamsRegistry.registerBlock(BlockRegistry.block_fishing,BlockFishing.name);

			BlockFishing.addRecipe();
		}
  
		BlockCommandBlockCraftable.initWeatherBlock();
		
		BlockCommandBlockCraftable.initTeleportBlock();

		BlockCommandBlockCraftable.initTeleportBedBlock();
		
		BlockCommandBlockCraftable.initRegen(); 
		
		BlockCommandBlockCraftable.initDaylight();
		
		BlockCommandBlockCraftable.initFiretick();
		
		BlockCommandBlockCraftable.initMobgrief();
 
		BlockXRay.initXray();
	}
}
