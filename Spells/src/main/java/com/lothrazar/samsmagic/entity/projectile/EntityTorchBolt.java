package com.lothrazar.samsmagic.entity.projectile; 

import java.util.ArrayList;
import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.potion.MessagePotion;
import com.lothrazar.samsmagic.potion.PotionRegistry; 
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
import net.minecraft.util.EnumFacing;
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
        
		if(this.isInWater() == false && offset != null && this.worldObj.isAirBlock(offset)  && this.worldObj.isRemote ) 
		{
			//http://minecraft.gamepedia.com/Torch#Block_data
			int faceEast = 1;
			int faceWest = 2;
			int faceSouth = 3;
			int faceNorth = 4;
			int faceUp = 5;
			int blockdata;
			
			switch(mop.sideHit)
			{ 
			case WEST:
				blockdata = faceWest;
				break;
			case EAST:
				blockdata = faceEast;
				break;
			case NORTH:
				blockdata = faceNorth;
				break;
			case SOUTH:
				blockdata = faceSouth;
				break; 
			default:
				blockdata = faceUp;
				break;
			}
			
    		this.worldObj.setBlockState(offset, Blocks.torch.getStateFromMeta(blockdata)); 
    		
    	}
	 
        this.setDead();
    }  
}