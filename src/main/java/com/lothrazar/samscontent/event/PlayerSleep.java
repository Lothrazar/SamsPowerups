package com.lothrazar.samscontent.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.util.SamsUtilities;

public class PlayerSleep 
{
	
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
			System.out.println("wakeup  " +event.entity.worldObj.getWorldTime());
			 
			if(SamsUtilities.isNighttime(event.entity.worldObj))
			{
			//	SamsUtilities.incrementPlayerIntegerNBT(event.entityPlayer, "full_sleeps", 1);
  
			//	event.entityPlayer.getEntityData().setLong("last_sleep", event.entity.worldObj.getWorldTime());
				PlayerPowerups props = PlayerPowerups.get(event.entityPlayer);
				
							props.incrementCurrentFly();
				 			System.out.println("iextended properties "+props.getCurrentSleep());
				//TODO: this is unfinished. need to check current time
				//and check number of days awake, and give tired buff
				
			} 
		
		}

	}
}
