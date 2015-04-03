package com.lothrazar.samscontent.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.util.SamsUtilities;

public class PlayerSleep 
{
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		if(event.entityLiving instanceof EntityPlayer)
		{ 
			EntityPlayer p = (EntityPlayer)event.entityLiving;

//worldtime x => didnt sleep
			
			if(event.entity.worldObj.getWorldTime() == 13000 - 10)//opr it was 24000
			{

				PlayerPowerups props = PlayerPowerups.get(p);
				props.increment(PlayerPowerups.AWAKE_WATCHER);
	 			System.out.println("did not sleep the night,,.. "+props.getInt(PlayerPowerups.AWAKE_WATCHER));
			}
			
			
		}
	}
	@SubscribeEvent
 	public void onEntityConstructing(EntityConstructing event)
 	{ 
 		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
 		{ 
 			PlayerPowerups.register((EntityPlayer) event.entity);
 		} 
 	}

// keep track of nights slept. but also nights not slept since last death? timestamps on player spawn to start.
	@SubscribeEvent
	public void onPlayerWakeUpEvent(PlayerWakeUpEvent event)
	{
		if(event.entity.worldObj.isRemote == false)
		{ 
		//.fires on both remotes
		//wakeup early always false.
	//	must check time of day for full night
			//: wakeup  13000
			System.out.println("wakeup  " +event.entity.worldObj.getWorldTime());
			 
			if(SamsUtilities.isNighttime(event.entity.worldObj))
			{ 
			//	event.entityPlayer.getEntityData().setLong("last_sleep", event.entity.worldObj.getWorldTime());
				PlayerPowerups props = PlayerPowerups.get(event.entityPlayer);
	
				props.increment(PlayerPowerups.SLEEP_WATCHER);
	 			System.out.println("iextended properties "+props.getInt(PlayerPowerups.SLEEP_WATCHER));
	 			
	 			
	 			
	 			
				//TODO: this is unfinished. need to check current time
				//and check number of days awake, and give tired buff
				
			} 
		
		}

	}
}
