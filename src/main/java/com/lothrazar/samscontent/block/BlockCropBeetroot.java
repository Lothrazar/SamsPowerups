package com.lothrazar.samscontent.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCropBeetroot extends BlockBush implements IGrowable
{
	 public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
	 protected BlockCropBeetroot()
	 {
		 setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
		 setTickRandomly(true);
		 setStepSound(Block.soundTypeGrass);
		 setCreativeTab((CreativeTabs)null);
		 setHardness(0.0F);
		 
		 float size = 0.5F;
		 setBlockBounds(0.5F - size, 0.0F, 0.5F - size, 0.5F + size, 0.25F, 0.5F + size);
		 
		 disableStats();
	 }
	 
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state,	boolean isClient) 
	{


		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,	IBlockState state) 
	{


		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{

		
	}

}
