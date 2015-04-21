package com.lothrazar.samscontent.world;

import java.util.Random;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGeneratorOcean implements IWorldGenerator
{
	//Thanks to ref :  http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/world-generation/

	private WorldGenerator genClay;  
	private WorldGenerator genSand;  
	private WorldGenerator genDirt;  
	
	private final int MIN_HEIGHT = 20; 
	private final int MAX_HEIGHT = 128;
 
	public WorldGeneratorOcean() 
	{   
	    this.genClay = new WorldGenMinable(Blocks.clay.getDefaultState(), ModMain.cfg.clayNumBlocks,BlockHelper.forBlock(Blocks.gravel));
	    this.genSand = new WorldGenMinable(Blocks.dirt.getDefaultState(), ModMain.cfg.dirtNumBlocks,BlockHelper.forBlock(Blocks.gravel));
	    this.genDirt = new WorldGenMinable(Blocks.sand.getDefaultState(), ModMain.cfg.sandNumBlocks,BlockHelper.forBlock(Blocks.gravel));
	}
	 
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{ 
		if(world.provider.getDimensionId() == Reference.Dimension.overworld) 
		{ 
			this.run(this.genClay, world, random, chunkX * Reference.CHUNK_SIZE, chunkZ * Reference.CHUNK_SIZE, 
					ModMain.cfg.clayChance, MIN_HEIGHT, MAX_HEIGHT);
			this.run(this.genSand, world, random, chunkX * Reference.CHUNK_SIZE, chunkZ * Reference.CHUNK_SIZE, 
					ModMain.cfg.sandChance, MIN_HEIGHT, MAX_HEIGHT);
			this.run(this.genDirt, world, random, chunkX * Reference.CHUNK_SIZE, chunkZ * Reference.CHUNK_SIZE,
					ModMain.cfg.dirtChance, MIN_HEIGHT, MAX_HEIGHT);
		} 
	}
	
	private void run(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) 
	{
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
 
	    int heightDiff = maxHeight - minHeight;
 
	    BlockPos pos;
	    BiomeGenBase biome;
	    
	    for (int i = 0; i < chancesToSpawn; i ++) 
	    { 
	        int x = chunk_X + rand.nextInt(Reference.CHUNK_SIZE);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z + rand.nextInt(Reference.CHUNK_SIZE);
	        
	        pos = new BlockPos(x, y, z);
	        biome = world.getBiomeGenForCoords(pos);
 
	        if( biome == BiomeGenBase.ocean || 
	        	biome == BiomeGenBase.deepOcean)
	        {  
	        	generator.generate(world, rand, pos);  
	        }
	    }
	} 
}
