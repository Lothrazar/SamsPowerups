package com.lothrazar.samsblocks;

import net.minecraftforge.common.config.Configuration;

public class ConfigRegistry
{ 
	private Configuration instance;
	private String category = ModBlocks.MODID;
	
	public Configuration instance()
	{
		return instance;
	}
	
	public ConfigRegistry(Configuration c)
	{
		instance = c; 
		instance.load();
  
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

		if(instance.hasChanged()){ instance.save(); }
	}
	 
	
 
 
	public boolean fishingNetBlock; 

	public boolean weatherBlock;  
  
	public boolean teleportBedBlock;
	public boolean teleportSpawnBlock;   
	
 
	public boolean shearSheepBlock; 
	public boolean storeBucketsBlock;


	public boolean block_fragile; 

	//public boolean appleFrost;
}
