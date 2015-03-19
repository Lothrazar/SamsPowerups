package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
    {
        float f = 0.0625F; //same as cactus
        return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)((float)(pos.getY() + 1) - f), (double)((float)(pos.getZ() + 1) - f));
    }
	public int damageDealt = 1;
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(entity instanceof EntityLivingBase) //could vbe only Player
		{
			EntityLivingBase living = (EntityLivingBase)entity;
			
			if(living.onGround)
			{
			
			living.attackEntityFrom(DamageSource.cactus, damageDealt);
			
			BlockPos offset = pos.offset(entity.getHorizontalFacing());

			double diffX = (pos.getX() - living.getPosition().getX()) * living.motionX;
			double diffZ = (pos.getZ() - living.getPosition().getZ()) * living.motionY;

			if(Double.isNaN(diffX) || Double.isNaN(diffZ)) {return;}
			if(diffX == 0 && diffZ == 0) {return;}
			
			System.out.println("spike "+diffX+" : "+diffZ);
			
			living.knockBack(living, 0F, 
					 diffX /4    , 
					 diffZ /4
					 );//living.motionZ*0.6000000238418579D);
			
			SamsUtilities.moveEntityWallSafe(living, world);	
			
			}
		}
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.spikeBlock), 
				"iii", 
				"i i", 
				"iii", 
				'i', Blocks.iron_bars );
	}
}
