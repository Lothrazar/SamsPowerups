package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ModMain;
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
	public BlockShearWool() 
	{
		super(Material.iron); 
		this.setCreativeTab(ModMain.tabSamsContent);
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
		//if we dont make the box biger than 1x1x1, the 'Collided' event will Never fire
        float f = 0.0625F; //same as cactus.
        return new AxisAlignedBB((double)((float)pos.getX() + f), (double)pos.getY(), (double)((float)pos.getZ() + f), (double)((float)(pos.getX() + 1) - f), (double)((float)(pos.getY() + 1) - f), (double)((float)(pos.getZ() + 1) - f));
    }
	
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
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(BlockRegistry.block_shear_sheep), 
				new ItemStack(Items.shears,1,0),
				Blocks.obsidian );
	}
}
