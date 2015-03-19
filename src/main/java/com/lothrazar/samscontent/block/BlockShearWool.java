package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockShearWool extends Block
{
	protected BlockShearWool() 
	{
		//TODO: we may delete this whole block/feature.  It is identical to cactus but does not kill Items.
		//is too OP for mob farms, and too useless for much else.
		super(Material.iron); 
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setStepSound(soundTypeMetal);
		//next two are the same as iron bars
		this.setHardness(5.0F);  
		this.setResistance(10.0F);   	     
	}
 
	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
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
		if(entity instanceof EntitySheep)
		{
			EntitySheep sheep = (EntitySheep)entity;

			if(sheep.getSheared() == false && sheep.worldObj.isRemote == false)
			{ 
				//this part is the same as how EntitySheep goes
				sheep.setSheared(true);
                int i = 1 + sheep.worldObj.rand.nextInt(3);

                for (int j = 0; j < i; ++j)
                {
                    EntityItem entityitem = sheep.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, sheep.getFleeceColor().getMetadata()), 1.0F);
                    entityitem.motionY += (double)(sheep.worldObj.rand.nextFloat() * 0.05F);
                    entityitem.motionX += (double)((sheep.worldObj.rand.nextFloat() - sheep.worldObj.rand.nextFloat()) * 0.1F);
                    entityitem.motionZ += (double)((sheep.worldObj.rand.nextFloat() - sheep.worldObj.rand.nextFloat()) * 0.1F);
                }
                
                sheep.playSound("mob.sheep.shear", 1.0F, 1.0F);
			} 
		}
		/*
		else if(entity instanceof EntityLivingBase) //any non sheeps get damaged
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
						 diffX /3.5   , 
						 diffZ /3.5
						 );//living.motionZ*0.6000000238418579D);
				
				SamsUtilities.moveEntityWallSafe(living, world);	 
			}
		}
		*/
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_spike), 
				"iii", 
				"i i", 
				"iii", 
				'i', Blocks.iron_bars );
	}
}
