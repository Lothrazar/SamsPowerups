package com.lothrazar.beetroot;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BeetrootBlock extends BlockBush implements IGrowable
{
	protected int maxGrowthStage = 7;
	public BeetrootBlock()
    {
     // Basic block setup
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f); 
        setHardness(0.0F);
        setStepSound(soundTypeGrass);
        disableStats();
    }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state,	boolean isClient) 
	{ 
		return this.getMetaFromState(state) != maxGrowthStage;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,	IBlockState state) 
	{ 
		return true;
	}

	@Override
	public void grow(World parWorld, Random rand, BlockPos pos, IBlockState state) 
	{
		//grow from bonemeal
		 
	    incrementGrow(parWorld, pos, state);
	}
	
	//natural growth
	@Override
	public void updateTick(World parWorld, BlockPos pos, IBlockState state, Random parRand)
	{ 
	    super.updateTick(parWorld, pos,state, parRand);
	    
	    //grow from nature
	    incrementGrow(parWorld, pos, state);
	}

	private void incrementGrow(World parWorld, BlockPos pos, IBlockState state) {
		int growStage = this.getMetaFromState(state) + 1;

	    if (growStage > 7)
	    {
	        growStage = 7;
	    }

	    parWorld.setBlockState(pos, this.getStateFromMeta(growStage), 2);
	}
 
	@Override
    protected boolean canPlaceBlockOn(Block parBlock)
    {
        return parBlock == Blocks.farmland;
    }
    @Override
    public Item getItemDropped(IBlockState i, Random parRand, int parFortune)
    { 
        return Item.getItemFromBlock(this);
    }
    @Override
    public int getRenderType()
    {
        return 1; // Cross like flowers
    } 
}
