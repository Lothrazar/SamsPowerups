package com.lothrazar.samscontent.item;//.entity.projectile;

import com.lothrazar.samscontent.MessagePotion;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

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

public class EntitySnowBolt extends EntitySnowball
{ 
    public EntitySnowBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntitySnowBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntitySnowBolt(World worldIn, double x, double y, double z)
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
            
            System.out.println("thrower  "+this.getThrower().getName());
    
            if(mop.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase e = (EntityLivingBase)mop.entityHit;
            	
            	e.addPotionEffect(new PotionEffect(PotionRegistry.frozen.id,5 * Reference.TICKS_PER_SEC,0));
            }
            
            //  on block impact, extenguish fire
             
      }

        for (int i = 0; i < 10; ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
  
        if (this.worldObj.isRemote == false)
        {
            if( mop.sideHit != null && this.getThrower() instanceof EntityPlayer)
            	this.worldObj.extinguishFire((EntityPlayer)this.getThrower(), mop.getBlockPos(), mop.sideHit);
     
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
            		this.worldObj.setBlockState(waterPos, Blocks.ice.getDefaultState()); 
	        }
        	 
            this.setDead();
        }
    }
}