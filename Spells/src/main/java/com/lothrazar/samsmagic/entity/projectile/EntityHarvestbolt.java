package com.lothrazar.samsmagic.entity.projectile; 

import com.lothrazar.samsmagic.spell.SpellHarvest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World; 

public class EntityHarvestbolt extends EntityThrowable//EntitySnowball
{ 
	//public static int damageToNormal = 0;//TODO CONFIG
 
    public EntityHarvestbolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityHarvestbolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityHarvestbolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
    	if(this.getThrower() instanceof EntityPlayer && mop.sideHit != null)
    	{
        	BlockPos offset = mop.getBlockPos().offset(mop.sideHit);
        	
    		//it harvests a horizontal slice each time
        	SpellHarvest.harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), mop.getBlockPos(),4);
        	SpellHarvest.harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), offset,6);
        	SpellHarvest.harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), offset.up(),4);
    	}
		 
        this.setDead();
 
    }  
}