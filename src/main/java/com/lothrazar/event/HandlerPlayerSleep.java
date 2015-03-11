package com.lothrazar.event;

import java.util.ArrayList;

import com.lothrazar.util.SamsUtilities;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerSleep 
{
	private ArrayList<Item> food;
	public HandlerPlayerSleep()
	{
		food = new ArrayList<Item>();
		food.add(Items.apple);
		food.add(Items.cooked_beef);
		food.add(Items.cooked_chicken);
		food.add(Items.cooked_fish);
		food.add(Items.cooked_mutton);
		food.add(Items.cooked_porkchop);
		food.add(Items.cooked_rabbit);
		food.add(Items.baked_potato);
		food.add(Items.beef);
		food.add(Items.chicken);
		food.add(Items.cookie);
		food.add(Items.fish);
		food.add(Items.golden_apple);
		food.add(Items.golden_carrot);
		food.add(Items.mushroom_stew);
		food.add(Items.rabbit_stew);
		food.add(Items.melon);
		food.add(Items.mutton);
		food.add(Items.porkchop);
		food.add(Items.potato);
		food.add(Items.spider_eye);
		food.add(Items.rotten_flesh);
		food.add(Items.pumpkin_pie);
		food.add(Items.rabbit);
		
		//TODO: add some from config CSV< include this mods apples
	}
	@SubscribeEvent
	public void onEat(PlayerUseItemEvent.Finish event)
	{
		if(event.entity.worldObj.isRemote == false)
		{  
			Item item = event.item.getItem();
			
			if(food.contains(item))
			{
				String iname = item.getUnlocalizedName();
				
				System.out.println(iname);
				SamsUtilities.incrementPlayerIntegerNBT(event.entityPlayer, iname, 1);
			} 
		}
	}
//TODO:keep track of nights slept. but also nights not slept since last death? timestamps on player spawn to start.
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
				SamsUtilities.incrementPlayerIntegerNBT(event.entityPlayer, "full_sleeps", 1);
  
				event.entityPlayer.getEntityData().setLong("last_sleep", event.entity.worldObj.getWorldTime());
				 
				
			} 
		
		}

	}
}
