package com.lothrazar.event;

import java.util.HashMap; 

import org.apache.logging.log4j.Logger; 

import com.lothrazar.item.ItemFoodAppleMagic;
import com.lothrazar.item.ItemFoodAppleMagic.MagicType;
import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities; 

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.Configuration; 
import net.minecraft.world.World;   
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class HandlerPlayerTickApplesEaten  
{   
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{ 
		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
		{ 
			PlayerPowerups.register((EntityPlayer) event.entity);
		} 
	}
	 
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{    	       
		if( event.player.worldObj.isRemote  == false )
		{ 	  
			tickHearts(event.player);  
		}  
	}
 
	private void tickHearts(EntityPlayer player) 
	{
		int countApplesEaten = SamsUtilities.getPlayerIntegerNBT(player, Reference.MODID + MagicType.Hearts.toString());
		
		int healthBoostLevel = countApplesEaten - 1; //you get 2 red hearts per level
		
		if(healthBoostLevel >= 0  && 
		   player.isPotionActive(Reference.potion_HEALTH_BOOST) == false)
		{ 
			//so we have eaten at least one apple, and the potion effect has been cleared, so we apply it
			int duration = 60 * 60 * Reference.TICKS_PER_SEC;
			player.addPotionEffect(new PotionEffect(Reference.potion_HEALTH_BOOST, duration, healthBoostLevel,false,false)); 
		}
	}
}
