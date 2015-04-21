package com.lothrazar.samscontent.world;

import org.apache.logging.log4j.Level;

import com.lothrazar.samscontent.ModMain;

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
		if(ModMain.cfg.spawnBlazeDesertHills) 
			EntityRegistry.addSpawn(EntityBlaze.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desertHills} );
 
		if(ModMain.cfg.spawnMagmaCubeDesert) 
			EntityRegistry.addSpawn(EntityMagmaCube.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.desert} );

		if(ModMain.cfg.spawnCaveSpiderMesa)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.mesa} );

		if(ModMain.cfg.spawnCaveSpiderRoofedForest)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.roofedForest} );
		
		if(ModMain.cfg.spawnCaveSpiderJungle)
			EntityRegistry.addSpawn(EntityCaveSpider.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.jungle} );
 
		if(ModMain.cfg.spawnSnowgolemsIceMountains) 
			EntityRegistry.addSpawn(EntitySnowman.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.iceMountains} );
		
		if(ModMain.cfg.spawnGhastDeepOcean) 
			EntityRegistry.addSpawn(EntityGhast.class, group, min, max, EnumCreatureType.MONSTER, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );

		//existing horses only spawn in plains and savanah
		//horses dont like trees, so biomes without them makes sense. ocean means those little islands

		if(ModMain.cfg.spawnHorseIcePlains) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.icePlains} );

		if(ModMain.cfg.spawnHorseOceanIslands) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.deepOcean} );
		
		if(ModMain.cfg.spawnHorseExtremeHills) 
			EntityRegistry.addSpawn(EntityHorse.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		if(ModMain.cfg.spawnVillagerExtremeHills) 
			EntityRegistry.addSpawn(EntityVillager.class, group, min, max, EnumCreatureType.CREATURE, new BiomeGenBase[]{ BiomeGenBase.extremeHills} );
		
		//WOLVES only spawn naturally in forest, taiga, mega taiga, cold taiga, and cold taiga M

		//irongolem - rare in jungle/?? 
	}
}
