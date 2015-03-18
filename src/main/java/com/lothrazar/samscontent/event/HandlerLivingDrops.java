package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDrops
{ 
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		if(ModLoader.configSettings.removeZombieCarrotPotato 
		  && event.entity instanceof EntityZombie)
		{  
			for(int i = 0; i < event.drops.size(); i++) 
			{
				EntityItem item = event.drops.get(i);
				
				if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
				{ 
					event.drops.remove(i);
				}
			}
		} 
	}
}
