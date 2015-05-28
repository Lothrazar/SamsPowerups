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
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntitySoulstoneBolt;
import com.lothrazar.util.Util;

public class SpellSoulstone implements ISpell
{
	

	private static final String KEY_STONED = "soulstone";
	private static final int VALUE_SINGLEUSE = -1;
	private static final int VALUE_PERSIST = 1;
	private static final int VALUE_EMPTY = 0;
	

	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.soulstone;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		System.out.println("TODO: soulstone projectile");
		
		world.spawnEntityInWorld(new EntitySoulstoneBolt(world,player 	 ));
		

		
		
		this.onCastSuccess(world,player,pos);
	}
	public static void addEntitySoulstone(EntityLivingBase e) 
	{ 
		//Item item = event.entityPlayer.getHeldItem() == null ? null : event.entityPlayer.getHeldItem().getItem();
		
		//if(item != ItemRegistry.soulstone && item != ItemRegistry.soulstone_persist){return;}
		 
		//getInteger by default returns zero if no value exists
		if(e.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{ 
			return;//for single use, only apply if existing is empty (do not overwrite persist)
		}
		/*
		if(item == ItemRegistry.soulstone_persist  && 
				event.target.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST)
		{ 
			return;//if we are using a persisting soulstone, it can overwrite single use or empty
			//just do not overwrite if we already have a persisting one applied
		}*/
		
		//(item == ItemRegistry.soulstone_persist) ? 
		int newValue = VALUE_PERSIST;// : VALUE_SINGLEUSE;
		 
		e.getEntityData().setInteger(KEY_STONED, newValue);
		
		//Util.decrHeldStackSize(event.entityPlayer); 
		
		Util.spawnParticle(e.worldObj, EnumParticleTypes.PORTAL, e.getPosition());
		
		
		Util.playSoundAt(e, "mob.endermen.death");
	} 
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{


		
		
		
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{


		
		
		
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.soulstone);
	}

	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public void drainExpCost(EntityPlayer player)
	{ 
		 Util.drainExp(player, getExpCost());
	}

	private int cost = 10;
	@Override
	public void setExpCost(int c)
	{
		cost = c;
	}
	@Override
	public int getExpCost()
	{
		return cost;
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
}
