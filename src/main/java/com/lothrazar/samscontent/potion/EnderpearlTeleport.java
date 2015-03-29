package com.lothrazar.samscontent.potion;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ModSamsContent;

public class EnderpearlTeleport 
{ 
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)event.entity;
			
			if(p.isPotionActive(PotionRegistry.ender))
			{
				//Feature 1: : remove damage 
				event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts
			  
				//feature 2: odds to return pearl
				int rawChance = 50;//ModLoader.configSettings.chanceReturnEnderPearl;
				
				double pct = ((double)rawChance)/100.0; 
				 
				if(p.worldObj.rand.nextDouble() < pct) //so event.entity.pos is their position BEFORE teleport
				{ 
					EntityItem ei = new EntityItem(p.worldObj, event.targetX, event.targetY, event.targetZ, new ItemStack(Items.ender_pearl));
					p.worldObj.spawnEntityInWorld(ei);
				} 
			}
		}
	} 
}
