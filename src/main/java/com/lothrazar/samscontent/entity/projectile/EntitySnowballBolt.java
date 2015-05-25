package com.lothrazar.samscontent.entity.projectile; 

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
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
        BlockPos pos = mop.getBlockPos();
        System.out.println("test "+this.posX+"  "+this.posZ);
        System.out.println("hit at "+Util.posToString(pos));
        
        if( mop.sideHit  != null)
        {
            System.out.println("Face"+  mop.sideHit.name());
            //BlockPos posOffset = mop.getBlockPos().offset(mop.sideHit);
        	
        }
  
      //  if (this.worldObj.isRemote == false)
       // {
            if( mop.sideHit != null && this.getThrower() instanceof EntityPlayer)
            {
            	this.worldObj.extinguishFire((EntityPlayer)this.getThrower(), mop.getBlockPos(), mop.sideHit);
            }
            
        	if(this.isInWater() )
	        { 
        		BlockPos posWater = this.getPosition();
        		
        		if(this.worldObj.getBlockState(posWater) != Blocks.water.getDefaultState() ) 
        		{
        			posWater = null;//look for the closest water source, sometimes it was air and we got ice right above the water if we dont do this check
        			 
        			if(this.worldObj.getBlockState(mop.getBlockPos()) == Blocks.water.getDefaultState())
        				posWater = mop.getBlockPos();
        			else if(this.worldObj.getBlockState(mop.getBlockPos().offset(mop.sideHit)) == Blocks.water.getDefaultState())
        				posWater = mop.getBlockPos().offset(mop.sideHit); 
        		}

            	if(posWater != null) //rarely happens but it does
            	{
            		this.worldObj.setBlockState(posWater, Blocks.ice.getDefaultState()); 
            	}
	        }
        	else
        	{
        		//on land, so snow?
        		BlockPos hit = pos;
        		BlockPos hitDown = hit.down();
        		BlockPos hitUp = hit.up();

        		IBlockState hitState = this.worldObj.getBlockState(hit);
        		if(this.worldObj.getBlockState(hit).getBlock() == Blocks.snow_layer)
        		{
        			setMoreSnow(this.worldObj,hit);

        		}//these other cases do not really fire, i think. unless the entity goes inside a block before despawning
        		else if(this.worldObj.getBlockState(hitDown).getBlock() == Blocks.snow_layer)
        		{
        			setMoreSnow(this.worldObj,hitDown);
        		}
        		else if(this.worldObj.getBlockState(hitUp).getBlock() == Blocks.snow_layer)
        		{
        			setMoreSnow(this.worldObj,hitUp);
        		}
        		else if(
        				this.worldObj.isAirBlock(hit) == false //one below us cannot be air
        				&& //and we landed at air or replaceable
        				this.worldObj.isAirBlock(hitUp) == true )
        				//this.worldObj.getBlockState(this.getPosition()).getBlock().isReplaceable(this.worldObj, this.getPosition()))
        		{

        			setNewSnow(this.worldObj,hitUp);
        		}  
        		else if(
        				this.worldObj.isAirBlock(hit) == false //one below us cannot be air
        				&& //and we landed at air or replaceable
        				this.worldObj.isAirBlock(hitUp) == true )
        				//this.worldObj.getBlockState(this.getPosition()).getBlock().isReplaceable(this.worldObj, this.getPosition()))
        		{
        			setNewSnow(this.worldObj,hitUp);
        		} 
        	}
        	 
            this.setDead();
       // }
    } 
    private static void setMoreSnow(World world, BlockPos pos)
    { 		
    	//so of the block hit, get metadata, and add +1 to it, unless its full like 8 or more
    	
    	IBlockState hitState = world.getBlockState(pos);
		int m = hitState.getBlock().getMetaFromState(hitState);
		//when it hits 7, same size as full block
		if(m+1 < 8)
			world.setBlockState(pos, Blocks.snow_layer.getStateFromMeta(m+1));
		
	
    }
    private static void setNewSnow(World world, BlockPos pos)
    {

    	world.setBlockState(pos, Blocks.snow_layer.getDefaultState()); 
    	
    }
}