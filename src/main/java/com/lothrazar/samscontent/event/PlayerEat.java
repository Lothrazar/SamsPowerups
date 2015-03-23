package com.lothrazar.samscontent.event;

import java.util.ArrayList;

import com.lothrazar.util.SamsUtilities;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEat 
{
	/*
	@SubscribeEvent
	public void onEat(PlayerUseItemEvent.Finish event)
	{
		if(event.entity.worldObj.isRemote == false)
		{  
			Item item = event.item.getItem();
			
			if(item instanceof ItemFood)
			{
				String iname = item.getUnlocalizedName();
				 
				SamsUtilities.incrementPlayerIntegerNBT(event.entityPlayer, iname, 1);
				//TODO: this is unfinished. need to check for unbalance and hurt player?
			} 
		}
	}
*/
}
