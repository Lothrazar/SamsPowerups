package com.lothrazar.samscontent.entity.projectile; 

import java.util.ArrayList;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.potion.MessagePotion;
import com.lothrazar.samscontent.potion.PotionRegistry;
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

public class EntitySoulstoneBolt extends EntitySnowball
{ 
	public static int secondsFrozenOnHit;
	public static int damageToNormal = 0;//TODO CONFIG
 
    public EntitySoulstoneBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntitySoulstoneBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntitySoulstoneBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float damage = damageToNormal;

            if (mop.entityHit instanceof EntityBlaze)
            {
               // damage = damageToBlaze;//TODO: config file blaze damage
            }
            
            //do the snowball damage, which should be none. put out the fire
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
            
            
            if(mop.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase e = (EntityLivingBase)mop.entityHit;

            	SpellSoulstone.addEntitySoulstone(e);
            	
            	 if(this.getThrower() instanceof EntityPlayer)
            	 {
            		 Util.addChatMessage((EntityPlayer)this.getThrower(), Util.lang("spell.soulstone.complete") + e.getDisplayName().getFormattedText());
            	 }
            	 
            	 
            	 

            	//if thrower is player?
        		//Util.addChatMessage(player, string);
            	//e.addPotionEffect(new PotionEffect(PotionRegistry.frozen.id, secondsFrozenOnHit * Reference.TICKS_PER_SEC,0));
            } 
        }
         
        this.setDead();
 
    }  
}