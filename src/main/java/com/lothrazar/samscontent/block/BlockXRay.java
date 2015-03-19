package com.lothrazar.samscontent.block;

import java.util.ArrayList;
import java.util.Random; 

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockXRay extends Block
{ 
	public BlockXRay()
	{
		super(Material.glass); 
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setHardness(4F); 
		this.setResistance(5F); 
		this.setTickRandomly(true);
    }

	@Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
	   return true;   
    }
  
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world,BlockPos pos, IBlockState state, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack( Blocks.web, 4, 0));
		ret.add(new ItemStack( Blocks.obsidian, 4, 0));
 
	 	return ret;
	} 
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT; // transparency
    }
	@Override
	public boolean isCollidable()
	{
		//TRUE MEANSZ YOU CAN WALK THROUGH IT LIKE A BUSH! USEFUL?
//this.getCollisionBoundingBox(world, pos, state)
	    return false;//?? http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/1371492-problem-with-onentitycollidedwithblock
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world,BlockPos pos, IBlockState state)
	{
		double r = 0.1;
		
		return new AxisAlignedBB((double)pos.getX() + r, (double)pos.getY() + r, (double)pos.getZ() + r, (double)pos.getX() + r, (double)pos.getY() + r, (double)pos.getZ() + r);
	      
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		System.out.println("?onEntityCollidedWithBlock");
		System.out.println(entity instanceof EntityPlayer);//FOR ENTITY PLAYER THIS IS FALSE
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer living = (EntityPlayer)entity;
			
			living.attackEntityFrom(DamageSource.cactus, 2);
			
			BlockPos offset = pos.offset(entity.getHorizontalFacing());

			System.out.println("?attackEntityFrom");
			
			living.knockBack(living, 1F, living.motionX/2 * -1, living.motionZ/2 * -1);
		}
		if(entity instanceof EntityLiving)
		{
			EntityLiving living = (EntityLiving)entity;
			
			living.attackEntityFrom(DamageSource.cactus, 2);
			
			BlockPos offset = pos.offset(entity.getHorizontalFacing());

			//System.out.println("?attackEntityFrom");
			
			living.knockBack(living, 1F, offset.getX(), offset.getZ());
		}
		//this.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	}
	 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_xray), 
				"owo", 
				"wgw", 
				"owo",
				'w', Blocks.web, 
				'g', Blocks.glass, 
				'o', Blocks.obsidian);

		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(new ItemStack(BlockRegistry.block_xray)
			, new ItemStack(Blocks.web, 4), 0);
	}
}
