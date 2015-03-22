package com.lothrazar.samscontent.world;

import org.apache.logging.log4j.Level;

import com.lothrazar.samscontent.ModLoader;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MobSpawningRegistry 
{
	static int group = 3;
	static int min = 1;
	static int max = 4; 
	public static void registerSpawns()
	{  
		if(ModLoader.configSettings.spawnBlazeDesertHills) 
			EntityRegistry.addSpawn(EntityBlaze.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desertHills} );
 
		if(ModLoader.configSettings.spawnMagmaCubeDesert) 
			EntityRegistry.addSpawn(EntityMagmaCube.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desert} );

		if(ModLoader.configSettings.spawnCaveSpiderMesa)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.mesa} );

		if(ModLoader.configSettings.spawnCaveSpiderRoofedForest)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.roofedForest} );
		
		if(ModLoader.configSettings.spawnCaveSpiderJungle)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.jungle} );
 
		if(ModLoader.configSettings.spawnSnowgolemsIceMountains) 
			EntityRegistry.addSpawn(EntitySnowman.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.iceMountains} );
		
		if(ModLoader.configSettings.spawnGhastDeepOcean) 
			EntityRegistry.addSpawn(EntityGhast.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );

		//existing horses only spawn in plains and savanah
		//horses dont like trees, so biomes without them makes sense. ocean means those little islands

		if(ModLoader.configSettings.spawnHorseIcePlains) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.icePlains} );

		if(ModLoader.configSettings.spawnHorseOceanIslands) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );
		
		if(ModLoader.configSettings.spawnHorseExtremeHills) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		if(ModLoader.configSettings.spawnVillagerExtremeHills) 
			EntityRegistry.addSpawn(EntityVillager.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		//WOLVES only spawn naturally in forest, taiga, mega taiga, cold taiga, and cold taiga M

		//irongolem - rare in jungle/?? 
	}
}
