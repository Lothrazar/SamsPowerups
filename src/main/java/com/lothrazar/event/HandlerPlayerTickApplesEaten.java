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
	private static boolean doesDrainHunger = false;
	private static boolean doesWeakness = true; //TODO: hook more like this to config?
	private static boolean doesFatigue = true; 
	/*
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		//Only need to synchronize when the world is remote (i.e. we're on the server side)
		///this would be IF we were using packets instead of a datawatcher
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
			PlayerPowerups.get((EntityPlayer) event.entity).sync();
	}
	*/
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
			
			if( Minecraft.getMinecraft().playerController.getCurrentGameType() != GameType.CREATIVE   && 
					Minecraft.getMinecraft().playerController.getCurrentGameType() != GameType.SPECTATOR//  &&
				
					
					)
				{//
				//then we are in either adventure or survival mode. and flying. 
		 
				//PlayerPowerups props = PlayerPowerups.get(event.player);
			 
				//int countAppleTicks = props.getCurrentFly();
				
				if(	event.player.capabilities.isFlying )//this never ever happens
				{
						//props.incrementCurrentFly(-1);//reduce it by 1 then eh
 
				}
				 
				}
			
		} 
		else //isRemote == true //tickFlying if used in isRemote==false will not work at all
		{ 	   
			//client side
			//tickFlying(event.player);  //affects game modes 0,2 (survival,adventure) 
		}   
	}

	private void tickFlying(EntityPlayer player) 
	{
		if( Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.CREATIVE  || 
			Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.SPECTATOR )
		{
			return;
		}

		PlayerPowerups props = PlayerPowerups.get(player);

		
		//whenever we eat a nether apple, we are given a bunch of 'flying  ticks' that add up
		int countAppleTicks = 0;//props.getCurrentFly();
 
		//SamsUtilities.getPlayerIntegerNBT(player, Reference.MODID + MagicType.Flying.toString());
 
		
		if (countAppleTicks > 0)
		{  
			player.capabilities.allowFlying = true;  
		}
		else
		{   
			// disable flying now & in future
			player.capabilities.allowFlying = false;  
			player.capabilities.isFlying = false;  
		}
		
		//now check, are we currently flying right now
		if (player.capabilities.isFlying)
		{   
			//every tick that we fly uses up one resource tick that was given by the apple (hence the -1_
		//	SamsUtilities.incrementPlayerIntegerNBT(player, Reference.MODID + MagicType.Flying.toString(), -1);
			//props.incrementCurrentFly(-1);
			
			int level = 4;//no number is actually default, so this makes potion effect 2 == III, 4 == V
			int duration = 2 * Reference.TICKS_PER_SEC;  //2 seconds
			
			if(doesWeakness) 
				player.addPotionEffect(new PotionEffect(Reference.potion_FATIGUE, duration, level));
			 
			if(doesFatigue) 
				player.addPotionEffect(new PotionEffect(Reference.potion_WEAKNESS, duration, level)); 
			  
			if(doesDrainHunger)  
				player.addPotionEffect(new PotionEffect(Reference.potion_HUNGER, duration, 0));
			 
		}  
		else //isFlying == false
		{  

			//erase the potion effects , dont just let them tick down
			//(in case it bugs out-  leave them lingering with 0:00 potions forever )
			if(doesWeakness)
				 player.removePotionEffect(Reference.potion_FATIGUE);
			
			if(doesFatigue)
				 player.removePotionEffect(Reference.potion_WEAKNESS);
			 
			if(doesDrainHunger) 
				player.removePotionEffect(Reference.potion_HUNGER); 
			/*
			if (player.posY < player.prevPosY) // we are falling 
			{  
				player.capabilities.allowFlying = false;// to enable  fall distance

			} 
			*/
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
