package com.lothrazar.samscontent.event;

import java.util.ArrayList;
import java.util.Set;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerHarvest 
{ 
	public static ArrayList<Block> blocksOnlyShovel = new ArrayList<Block> (); 
	public static ArrayList<Block> blocksOnlyAxe    = new ArrayList<Block> ();

	public static void setShovelFromCSV(String csv)
	{
		blocksOnlyShovel = SamsUtilities.getBlockListFromCSV(csv);
	}
	
	public static void seAxeFromCSV(String csv)
	{
		blocksOnlyAxe = SamsUtilities.getBlockListFromCSV(csv); 
	}
	 
	@SubscribeEvent
    public void onHarvestDrops(BlockEvent.HarvestDropsEvent event)
    { 
		if(event.world.isRemote){return;}
		if(event.harvester == null){return;}
  	 
		ItemStack held = event.harvester.getHeldItem();
		boolean playerUsingShovel = false;
		boolean playerUsingAxe = false; 
	   
		if(held != null)
		{ 
			Set<String> classes = held.getItem().getToolClasses(held);
			 
			for(String toolClass : classes)//can be empty, it has no tool classes
			{  
				if(toolClass == Reference.toolClassShovel ) {playerUsingShovel = true;}
				   
				if(toolClass == Reference.toolClassAxe ) {playerUsingAxe = true;}
			}
		}

		Block bh = null;
		for(int i = 0; i < event.drops.size(); i++)
		{ 
			bh = Block.getBlockFromItem(event.drops.get(i).getItem());
			if(bh == null) {continue;}
			
			if(		blocksOnlyShovel.contains(bh) && 
					playerUsingShovel == false) 
			{
				//item is in the restricted list, and its not a matching tool  
				event.drops.remove(i);
			} 	
			else if(		blocksOnlyAxe.contains(bh) && 
					playerUsingAxe == false) 
			{
				//item is in the restricted list, and its not a matching tool  
				event.drops.remove(i);
			} 
			 
			bh = null;
		}
    } 
}
