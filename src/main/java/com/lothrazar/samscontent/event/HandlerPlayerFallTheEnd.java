package com.lothrazar.samscontent.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

public class HandlerPlayerFallTheEnd 
{ 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{     
		EntityPlayer player = event.player;
		  
		if( player.isPotionActive(PotionRegistry.ender) && //the potion gives us this safe(ish)falling
			 	 player.dimension == Reference.Dimension.end && //hence the name of the class
				 player.posY < -50 && 
				 player.worldObj.isRemote  == false && 
				 player.capabilities.isCreativeMode == false
				)
		{  
			SamsUtilities.teleportWallSafe(player, player.worldObj, player.getPosition().up(256)); 
					
			int duration = 20 * Reference.TICKS_PER_SEC;
 
			//TODO: put each potion effect in config file. or one CSV.
			//event.player.addPotionEffect(new PotionEffect(Reference.potion_WITHER, duration, 0));
			//event.player.addPotionEffect(new PotionEffect(Reference.potion_NAUSEA, duration, 0));
			////event.player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0)); 
		} 
	} 
}
