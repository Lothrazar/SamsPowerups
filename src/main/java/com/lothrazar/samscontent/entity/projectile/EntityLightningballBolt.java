package com.lothrazar.samscontent.entity.projectile; 

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase; 
import net.minecraft.entity.effect.EntityLightningBolt;
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

public class EntityLightningballBolt extends EntitySnowball
{ 
    public EntityLightningballBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityLightningballBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityLightningballBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
    
        this.worldObj.spawnEntityInWorld(new EntityLightningBolt(this.worldObj, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ()));

        this.setDead();
         
    }
}