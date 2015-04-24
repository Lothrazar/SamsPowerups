package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class ItemSoulstone extends Item
{
	public ItemSoulstone()
	{  
		super();    
		this.setCreativeTab(ModMain.tabSamsContent); 
	}
	
	private static final String KEY_STONED = "soulstone";
	
	public static void addRecipe() 
	{
		
	}
	public static void onEntityInteract(EntityInteractEvent event) 
	{
//System.out.println("soulstone on an ent");


		event.target.getEntityData().setBoolean(KEY_STONED, true);
		
		
	}
	
	//http://www.minecraftforge.net/forum/index.php?topic=7475.0
	//TODO: replace with living death event, and check if evt.livingEntity.getHealth - evt.damageAmount <= 0)
	
	//then cancel the HURT event, so death will not even happen
	/*
	public static void onEntityDeath(LivingDeathEvent event) 
	{

		if(event.entity.getEntityData().getBoolean(KEY_STONED))
		{
//cancelling this event stilll lets entity die
		//	System.out.println("living soulstone = and so isCancelable = "+event.isCancelable());
			
		//	event.setCanceled(true);//does not stop them dying
			
			
			EntityLivingBase living = (EntityLivingBase)event.entity;
			
			living.heal(20);
			
			living.isDead = false;
			SamsUtilities.teleportWallSafe((EntityLivingBase)event.entity, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());
			
			
			
			
		}
		
		
	}
	*/
	public static void onEntityDrops(LivingDropsEvent event) 
	{

		if(event.entity.getEntityData().getBoolean(KEY_STONED))
		{

			//System.out.println("living soulstone CLEAR DROPS");

			event.drops.clear();
			
			
		}
		
	}
	public static void onLivingHurt(LivingHurtEvent event) 
	{
		if(event.entityLiving.getEntityData().getBoolean(KEY_STONED))
		{

			//System.out.println("living soulstone Damage");
			//yes there is a typo in the word 'amount' but it is not in my code  
			float amount = event.ammount;
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{
				//System.out.println("living soulstone Damage DYING");

				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//maybe?
				
				SamsUtilities.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				event.entityLiving.getEntityData().setBoolean(KEY_STONED, false);
			}
		} 
	}
	
	
}
