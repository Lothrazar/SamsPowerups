package com.lothrazar.event;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;  

import com.lothrazar.item.*;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities; 

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S42PacketCombatEvent.Event;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;   
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class HandlerWand  
{    
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandChest && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked instanceof BlockChest)// && event.entityPlayer.isSneaking()
			{   
				TileEntity container = event.world.getTileEntity(event.pos);
				if(container instanceof TileEntityChest)
				{
					ItemRegistry.wandChest.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
				}
			} 
		}
		else if(held.getItem() == ItemRegistry.wandHarvest && 
				event.action.RIGHT_CLICK_BLOCK == event.action && 
		    (blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes))
		{ 
			ItemRegistry.wandHarvest.replantField(event.entityPlayer,held,event.pos); 
		}
		else if(held.getItem() == ItemRegistry.wandProspect && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			ItemRegistry.wandProspect.searchProspect(event.entityPlayer,held,event.pos);   
		}
		else if(held.getItem() == ItemRegistry.wandFire && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{  
			if(event.entityPlayer.isSneaking())
			{ 
				ItemWandFire.castFire(event.world,event.entityPlayer,held); 
			}
			else
			{
				ItemWandFire.castExtinguish(event.world,event.entityPlayer,held); 
			} 
		}
		else if(held.getItem() == ItemRegistry.wandCopy &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			if(blockClicked == Blocks.wall_sign || blockClicked == Blocks.standing_sign )
			{
				TileEntitySign sign = (TileEntitySign)event.world.getTileEntity(event.pos);
				 
				if(event.entityPlayer.isSneaking())
				{ 
					ItemWandCopyPaste.copySign(event.world,event.entityPlayer,sign,held); 
				}
				else
				{
					ItemWandCopyPaste.pasteSign(event.world,event.entityPlayer,sign,held); 
				} 
			}
			if(blockClicked == Blocks.noteblock)
			{
				TileEntityNote noteblock = (TileEntityNote)event.world.getTileEntity(event.pos);
				 
				if(event.entityPlayer.isSneaking())
				{ 
					ItemWandCopyPaste.copyNote(event.world,event.entityPlayer,noteblock,held); 
				}
				else
				{
					ItemWandCopyPaste.pasteNote(event.world,event.entityPlayer,noteblock,held); 
				} 
			}
			//TODO: copy or paste if shift or not
		//	ItemWandCopyPaste.castExtinguish(event.world,event.entityPlayer,held); 
			
			
			
			
		}
  	}
  
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != ItemRegistry.wandLivestock ){ return;}
		if(event.entityPlayer.worldObj.isRemote ){ return;}//so do nothing on client side
     
		ItemRegistry.wandLivestock.entitySpawnEgg(event.entityPlayer, event.target); 
  	} 
	/*
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.entityPlayer == null || event.itemStack == null || event.itemStack.getItem() == null) { return; }
		
		if (event.getResult() == Result.DENY) { return; }

 //TODO: fix this and put it back in, make it accurate to each item/wand/whatever
		
		Item item = event.itemStack.getItem();
		if(item == ItemWandTransform.itemWand)
		{
			event.toolTip.add("For use only on Mushroom blocks and some Double Slabs.");
		}
		 
		if(item == ItemWandDungeon.itemWand)
			if(SamsUtilities.isShiftKeyDown())  //thanks to http://www.minecraftforge.net/forum/index.php?topic=24991.0 
			{   
				String s = SamsUtilities.getItemStackNBT(event.itemStack, "found");
 
				if(s != "")
					event.toolTip.add(s);
			}
			else
			{
				event.toolTip.add("[Hold Shift for details]");
			} 
			
	}
	*/
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent event)
	{
		//TODO: make dungeon wand/searching stuff consumables?
	}
}
