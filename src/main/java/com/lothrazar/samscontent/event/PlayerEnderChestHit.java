package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.ModSamsContent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEnderChestHit
{ 
 	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
 	{      
		if(event.action == event.action.LEFT_CLICK_BLOCK)
		{
			if(ModSamsContent.configSettings.smartEnderchest && 
					event.entityPlayer.getCurrentEquippedItem() != null && 
					event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest) )
			{
				event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest()); 
			}
		} 
 	}
}
