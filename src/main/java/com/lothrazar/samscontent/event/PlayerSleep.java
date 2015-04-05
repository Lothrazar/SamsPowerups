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
			
			//wakeup first night is 24000
			//wakeup 2 is 48000, and so on
			int oneDay = 24000;//so morning after first sleep goes from 0 to this.
			
			
			if(event.entity.worldObj.getWorldTime() % oneDay == oneDay - 10) 
			{ 
				PlayerPowerups props = PlayerPowerups.get(p);
				props.increment(PlayerPowerups.AWAKE_WATCHER);
				 
	 			System.out.println(event.entity.worldObj.getWorldTime());
	 			System.out.println(event.entity.worldObj.getWorldTime() % oneDay );
	 			System.out.println("# awake = "+props.getInt(PlayerPowerups.AWAKE_WATCHER));
			}
			
			
			//TODO: this is unfinished. should count # awake/asleep and give tired buff
			 
			
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
		//called when the player leaves the bed, may or may not be morning
		System.out.println("wakeup  " +event.entity.worldObj.getWorldTime());
		if(event.entity.worldObj.isRemote == false
				&& event.entity.worldObj.isDaytime() == false)//.fires on both remotes
		{ 
		 
			PlayerPowerups props = PlayerPowerups.get(event.entityPlayer);
			
			props.increment(PlayerPowerups.SLEEP_WATCHER);
			
 			System.out.println("#sleeps = "+props.getInt(PlayerPowerups.SLEEP_WATCHER));
 		 
		}

	}
}
