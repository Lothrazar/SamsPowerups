package com.lothrazar.event;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.item.ItemBucketStorage;
import com.lothrazar.item.ItemWandTransform;

public class HandlerBucketStorage 
{ 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{       
		if(event.world.isRemote){ return ;}//server side only!
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		   
		if(held.getItem() instanceof ItemBucketStorage && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{
			ItemBucketStorage.placeLiquid(event.entityPlayer, held, event.pos.offset(event.face));
		} 
  	}
}
