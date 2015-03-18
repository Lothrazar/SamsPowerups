package com.lothrazar.samscontent.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.potion.PotionRegistry;

public class HandlerEnderpearlTeleport 
{ 
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)event.entity;
			
			if(p.isPotionActive(PotionRegistry.ender))
			{
				//FIRST: remove damage
				 //starts 5.0 which is 2.5hearts
				event.attackDamage = 0; 
			 
				//SECOND: get pearl back
				
				int rawChance = 50;//ModLoader.configSettings.chanceReturnEnderPearl;
				
				double pct = ((double)rawChance)/100.0; 
				
				//so event.entity.pos is their position BEFORE teleport
				if(p.worldObj.rand.nextDouble() < pct)
				{ 
					EntityItem ei = new EntityItem(p.worldObj, event.targetX, event.targetY, event.targetZ, new ItemStack(Items.ender_pearl));
					p.worldObj.spawnEntityInWorld(ei);
				} 
			}
		}
	} 
}
