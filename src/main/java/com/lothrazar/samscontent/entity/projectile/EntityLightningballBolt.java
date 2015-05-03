package com.lothrazar.samscontent.entity.projectile; 

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

import net.minecraft.client.Minecraft;
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
import net.minecraftforge.fml.relauncher.Side;

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
    	//happens ONLY for isRemote == false. which means server side.
    	//thats great but, isremote=true means client, so how to make entity show in clident side.
    	EntityLightningBolt ball = new EntityLightningBolt(this.worldObj, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
        this.worldObj.spawnEntityInWorld(ball);

        this.setDead(); 
    }
}