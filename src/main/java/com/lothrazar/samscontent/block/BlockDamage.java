package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockDamage  extends Block
{
	protected BlockDamage() 
	{
		super(Material.glass); 
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setHardness(4F); 
		this.setResistance(5F); 
	}

	@Override
	public boolean isCollidable()
	{ 
	    return false;//True would mean it acts as a water block, can pass through
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world,BlockPos pos, IBlockState state)
	{
		double r = 0.1;
		
		return new AxisAlignedBB((double)pos.getX() + r, (double)pos.getY() + r, (double)pos.getZ() + r, (double)pos.getX() + r, (double)pos.getY() + r, (double)pos.getZ() + r);
	}

	public int damageDealt = 1;
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(entity instanceof EntityLivingBase) //could vbe only Player
		{
			EntityLivingBase living = (EntityLivingBase)entity;
			
			living.attackEntityFrom(DamageSource.cactus, damageDealt);
			
			BlockPos offset = pos.offset(entity.getHorizontalFacing());

			living.knockBack(living, 0F, 
					 pos.getX() - living.getPosition().getX(), 
					 pos.getZ() - living.getPosition().getZ());//living.motionZ*0.6000000238418579D);
			
			SamsUtilities.moveEntityWallSafe(living, world);
		}
	}
	
	public static void addRecipe() 
	{
		
	}
}
