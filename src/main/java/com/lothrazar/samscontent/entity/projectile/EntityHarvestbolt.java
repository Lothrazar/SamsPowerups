package com.lothrazar.samscontent.entity.projectile; 

import java.util.ArrayList;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.spell.SpellHarvest;
import com.lothrazar.samscontent.spell.SpellSoulstone;
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
         
    	BlockPos pos = mop.getBlockPos().offset(mop.sideHit);
    	System.out.println("Harvest landing?" + Util.posToString(pos));
    	
    	if(this.getThrower() instanceof EntityPlayer && mop.sideHit != null)
    	{

        	SpellHarvest.harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), pos);
        	SpellHarvest.harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), pos.up());
    	}
		 
        this.setDead();
 
    }  
}