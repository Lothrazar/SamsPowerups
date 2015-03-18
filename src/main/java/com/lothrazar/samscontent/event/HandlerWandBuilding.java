package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.samscontent.item.ItemWandBuilding;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class HandlerWandBuilding 
{
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) 
	{
		ItemWandBuilding.onPlayerTick(event);
	}

	@SubscribeEvent
	public void onPlayerClick(PlayerInteractEvent event)
  	{ 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held==null || held.getItem() != ItemRegistry.wandBuilding){return;}
		 
		if(event.action.LEFT_CLICK_BLOCK == event.action  )
		{ 
			ItemWandBuilding.onPlayerLeftClick(event);
		}
		else
		{
			if(event.world.isRemote){return;}
			ItemWandBuilding.onPlayerRightClick(event);
		} 
  	}
}
