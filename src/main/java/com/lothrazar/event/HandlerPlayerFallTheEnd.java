package com.lothrazar.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

public class HandlerPlayerFallTheEnd 
{ 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	 
		if(!ModLoader.configSettings.theEndSafeFall){return;}
		if( event.player.capabilities.isCreativeMode){return;} 

		EntityPlayer player = event.player;
		  
		if(player.dimension == Reference.Dimension.end && //hence the name of the class
				 player.posY < -50 && 
				 player.worldObj.isRemote  == false && 
				 player.capabilities.isCreativeMode == false
				)
		{  
			SamsUtilities.teleportWallSafe(event.player, event.player.worldObj, event.player.getPosition().up(256)); 
					
			int duration = 20 * Reference.TICKS_PER_SEC;
 
			//TODO: put each potion effect in config file. or one CSV.
			event.player.addPotionEffect(new PotionEffect(Reference.potion_WITHER, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_NAUSEA, duration, 0));
			event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0)); 
		} 
	} 
}
