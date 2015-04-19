package com.lothrazar.samscontent.world;

import org.apache.logging.log4j.Level;

import com.lothrazar.samscontent.ModSamsContent;

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
		if(ModSamsContent.cfg.spawnBlazeDesertHills) 
			EntityRegistry.addSpawn(EntityBlaze.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desertHills} );
 
		if(ModSamsContent.cfg.spawnMagmaCubeDesert) 
			EntityRegistry.addSpawn(EntityMagmaCube.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desert} );

		if(ModSamsContent.cfg.spawnCaveSpiderMesa)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.mesa} );

		if(ModSamsContent.cfg.spawnCaveSpiderRoofedForest)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.roofedForest} );
		
		if(ModSamsContent.cfg.spawnCaveSpiderJungle)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.jungle} );
 
		if(ModSamsContent.cfg.spawnSnowgolemsIceMountains) 
			EntityRegistry.addSpawn(EntitySnowman.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.iceMountains} );
		
		if(ModSamsContent.cfg.spawnGhastDeepOcean) 
			EntityRegistry.addSpawn(EntityGhast.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );

		//existing horses only spawn in plains and savanah
		//horses dont like trees, so biomes without them makes sense. ocean means those little islands

		if(ModSamsContent.cfg.spawnHorseIcePlains) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.icePlains} );

		if(ModSamsContent.cfg.spawnHorseOceanIslands) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );
		
		if(ModSamsContent.cfg.spawnHorseExtremeHills) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		if(ModSamsContent.cfg.spawnVillagerExtremeHills) 
			EntityRegistry.addSpawn(EntityVillager.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		//WOLVES only spawn naturally in forest, taiga, mega taiga, cold taiga, and cold taiga M

		//irongolem - rare in jungle/?? 
	}
}
