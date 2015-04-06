package com.lothrazar.samscontent.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
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

			PlayerPowerups props = PlayerPowerups.get(p);
			
			if(event.entity.worldObj.getWorldTime() % oneDay == oneDay - 10) 
			{ 
				props.increment(PlayerPowerups.AWAKE_WATCHER);
				 
	 			//System.out.println(event.entity.worldObj.getWorldTime());
	 			//System.out.println(event.entity.worldObj.getWorldTime() % oneDay );
	 			//System.out.println("# awake = "+props.getInt(PlayerPowerups.AWAKE_WATCHER));
			}

			int numSleeps = props.getInt(PlayerPowerups.SLEEP_WATCHER);
			int numAwake  = props.getInt(PlayerPowerups.AWAKE_WATCHER);
			
			//TODO: this is unfinished. should count # awake/asleep and give tired buff
			//for example, if numSleeps=50 and numAwake=75, then theres the tired buff
			//if(numAwake > numSleeps)
			int seconds=(int) (event.entity.worldObj.getWorldTime() / Reference.TICKS_PER_SEC);
			
			
			if(seconds % 10 == 0)//hit once every ten seconds
			{

				
				System.out.println(numAwake+"  ?  "+numSleeps * 1.45);
				
				if(numAwake >= numSleeps * 1.45)
				{
					//50 >= 
					
					System.out.println("add potion tired");
					p.addPotionEffect(new PotionEffect(PotionRegistry.tired.id,11));
				}	
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
		//called when the player leaves the bed, may or may not be morning
		//System.out.println("wakeup  " +event.entity.worldObj.getWorldTime());
		if(event.entity.worldObj.isRemote == false
				&& event.entity.worldObj.isDaytime() == false)//.fires on both remotes
		{ 
		 
			PlayerPowerups props = PlayerPowerups.get(event.entityPlayer);
			
			props.increment(PlayerPowerups.SLEEP_WATCHER);
			
 			//System.out.println("#sleeps = "+props.getInt(PlayerPowerups.SLEEP_WATCHER));
 		 
		}

	}
}
