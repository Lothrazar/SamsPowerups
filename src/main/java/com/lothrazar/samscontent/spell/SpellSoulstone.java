package com.lothrazar.samscontent.spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.entity.projectile.EntitySoulstoneBolt;
import com.lothrazar.util.Util;

public class SpellSoulstone extends BaseSpellExp implements ISpell
{
	private static final String KEY_STONED = "soulstone";
	private static final int VALUE_SINGLEUSE = -1;
	private static final int VALUE_PERSIST = 1;
	private static final int VALUE_EMPTY = 0;
	
	@Override
	public String getSpellID()
	{
		return "soulstone";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntitySoulstoneBolt(world,player 	 ));
	}
	
	public static void addEntitySoulstone(EntityLivingBase e) 
	{  
		//getInteger by default returns zero if no value exists
		if(e.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{ 
			return;//for single use, only apply if existing is empty (do not overwrite persist)
		}
 
		//(item == ItemRegistry.soulstone_persist) ? 
		int newValue = VALUE_PERSIST;// : VALUE_SINGLEUSE;
		 
		e.getEntityData().setInteger(KEY_STONED, newValue);
	} 
	
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.spawnParticle(world, EnumParticleTypes.PORTAL, pos);
		
		Util.playSoundAt(player, "mob.endermen.death");

		super.onCastSuccess(world, player, pos);
	}
 

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.soulstone);
	}

	@Override
	public int getExpCost()
	{
		return 200;
	}
	
	public static void onLivingHurt(LivingHurtEvent event) 
	{
		//called from ModMain event handler
		//thanks for the help:
		//http://www.minecraftforge.net/forum/index.php?topic=7475.0
		
		if(event.entityLiving.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{  
			float amount = event.ammount;//yes there is a typo in the word 'amount' but it is not in my code  
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{ 
				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//this is possible but not needed
				
				Util.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				boolean isPersist = event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST;
				 
				if(event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_SINGLEUSE)
				{
					//if it does not persist, then consume this use
					event.entityLiving.getEntityData().setInteger(KEY_STONED, VALUE_EMPTY);
				} 
			}
		} 
	} 
	@Override
	public ISpell next()
	{
		return SpellRegistry.phase;
	}

	@Override
	public ISpell prev()
	{
		return SpellRegistry.haste;
	}
}
