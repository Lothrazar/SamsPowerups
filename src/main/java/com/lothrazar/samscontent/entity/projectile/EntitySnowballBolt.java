package com.lothrazar.samscontent.entity.projectile; 

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

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

public class EntitySnowballBolt extends EntitySnowball
{ 
	public static int secondsFrozenOnHit;
	
    public EntitySnowballBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntitySnowballBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntitySnowballBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float damage = 0;

            if (mop.entityHit instanceof EntityBlaze)
            {
                damage = 3;
            }

            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
             
            if(mop.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase e = (EntityLivingBase)mop.entityHit;
            	
            	e.addPotionEffect(new PotionEffect(PotionRegistry.frozen.id, secondsFrozenOnHit * Reference.TICKS_PER_SEC,0));
            } 
            
            
      }

        for (int i = 0; i < 10; ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
  
        if (this.worldObj.isRemote == false)
        {
            if( mop.sideHit != null && this.getThrower() instanceof EntityPlayer)
            {
            	this.worldObj.extinguishFire((EntityPlayer)this.getThrower(), mop.getBlockPos(), mop.sideHit);
            }
            
        	if(this.isInWater() )
	        { 
        		BlockPos waterPos = this.getPosition();
        		
        		if(this.worldObj.getBlockState(waterPos) != Blocks.water.getDefaultState() ) 
        		{
        			waterPos = null;//look for the closest water source, sometimes it was air and we got ice right above the water if we dont do this check
        			 
        			if(this.worldObj.getBlockState(mop.getBlockPos()) == Blocks.water.getDefaultState())
        				waterPos = mop.getBlockPos();
        			else if(this.worldObj.getBlockState(mop.getBlockPos().offset(mop.sideHit)) == Blocks.water.getDefaultState())
        				waterPos = mop.getBlockPos().offset(mop.sideHit); 
        		}

            	if(waterPos != null) //rarely happens but it does
            	{
            		this.worldObj.setBlockState(waterPos, Blocks.ice.getDefaultState()); 
            	}
	        }
        	else
        	{
        		//on land, so snow?
        		System.out.println("try to set snow layer?");
        		if(
        				this.worldObj.isAirBlock(this.getPosition().down()) == false //one below us cannot be air
        				&& //and we landed at air or replaceable
        				this.worldObj.getBlockState(this.getPosition()).getBlock().isReplaceable(this.worldObj, this.getPosition()))
        		{

            		this.worldObj.setBlockState(this.getPosition(), Blocks.snow_layer.getDefaultState()); 
        		}
        		
        		if(this.worldObj.getBlockState(this.getPosition()).getBlock() == Blocks.snow_layer)
        		{

            		System.out.println("LANDED on snow layer, increase it by +1?");
        		}
        		
        	}
        	 
            this.setDead();
        }
    }
}