package com.lothrazar.samscontent.entity.projectile; 

import java.util.ArrayList;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World; 

public class EntityTorchBolt extends EntityThrowable
{ 
	public static int damageToNormal = 0;
	
    public EntityTorchBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityTorchBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityTorchBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float damage = damageToNormal;
 
            //do the snowball damage, which should be none. put out the fire
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
             
        }
        
        BlockPos pos = mop.getBlockPos();
        BlockPos offset = null;
        
  
        if( mop.sideHit != null)
        {
    
        	offset = mop.getBlockPos().offset(mop.sideHit);
        }
        
   
    	if(this.isInWater() == false )
        {  
    		/*if(this.worldObj.isAirBlock(pos)  ) 
    		{
        		this.worldObj.setBlockState(pos, Blocks.torch.getDefaultState()); 
    		}
    		else */if(offset != null && this.worldObj.isAirBlock(offset)  ) 
    		{
        		this.worldObj.setBlockState(offset, Blocks.torch.getDefaultState()); 
        	}
        }
    	 
        	 
        this.setDead();
 
    }  
}