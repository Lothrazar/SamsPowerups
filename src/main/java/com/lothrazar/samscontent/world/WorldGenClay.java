package com.lothrazar.samscontent.world;

import java.util.Random;

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

public class WorldGenClay implements IWorldGenerator
{
	//i used http://bedrockminer.jimdo.com/modding-tutorials/basic-modding/world-generation/
	   //TODO worldgen ocean: decorate with reeds down there?
	//TODO: patches of dirt/sand like before
	private WorldGenerator genClay;  

	private int clayChance = 60;
	private int clayNumBlocks = 12;
	private int clayMinHeight = 30; 
	private int clayMaxHeight = 64;
	
	public WorldGenClay() 
	{  
	    this.genClay = new WorldGenMinable(Blocks.clay.getDefaultState(), clayNumBlocks,BlockHelper.forBlock(Blocks.gravel));
	}
	 
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{ 
		if(world.provider.getDimensionId() == Reference.Dimension.overworld) 
		{
			this.run(this.genClay, world, random, chunkX * Reference.CHUNK_SIZE, chunkZ * Reference.CHUNK_SIZE, clayChance, clayMinHeight, clayMaxHeight);

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
