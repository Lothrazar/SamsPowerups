package com.lothrazar.samscontent;

import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class AchievementRegistry 
{
	//reference http://www.minecraftforge.net/wiki/How_to_add_an_Achievement
	 
	private AchievementPage page;
	
	public Achievement appleChoc;
	public Achievement appleLapis;
	public Achievement appleDiamond;
	public Achievement appleEmerald;
	 
	
	public void registerAll()
	{
		//Achievement constructor (id, name, x, y, icon, pre-requisite achievement 
		
		int xSpacing = 3;
		
		int xStart = 0;
		int yStart = 0;
		
		int xCurrent = xStart;
		int yCurrent = yStart;
		
		if(ItemRegistry.apple_chocolate != null)
		{ 
			xCurrent += xSpacing;
			appleChoc = new Achievement(Reference.MODID + "_appleChoc", "appleChoc", xCurrent, yCurrent, ItemRegistry.apple_chocolate, null);
			appleChoc.registerStat(); 
		} 
		if(ItemRegistry.apple_lapis != null)
		{ 
			xCurrent += xSpacing;
			appleLapis = new Achievement(Reference.MODID + "_appleLapis", "appleLapis", xCurrent, yCurrent, ItemRegistry.apple_lapis, null);
			appleLapis.registerStat(); 
		} 
		if(ItemRegistry.apple_diamond != null)
		{ 
			xCurrent += xSpacing;
			appleDiamond = new Achievement(Reference.MODID + "_appleDiamond", "appleDiamond", xCurrent, yCurrent, ItemRegistry.apple_diamond, null);
			appleDiamond.registerStat(); 
		} 
		if(ItemRegistry.apple_emerald != null)
		{ 
			xCurrent += xSpacing;
			appleEmerald = new Achievement(Reference.MODID + "_appleEmerald", "appleEmerald", xCurrent, yCurrent, ItemRegistry.apple_emerald, null);
			appleEmerald.registerStat(); 
		} 
 

		
		//TODO: can page name be from lang file?
		page = new AchievementPage("Sam's Content",appleChoc,appleDiamond,appleLapis,appleEmerald);//, ach1, ach2, ach3, ach4
  
	 	AchievementPage.registerAchievementPage(page);
	}
	
	private void addStatSafe(Achievement stat, EntityPlayer player)
	{
		if(stat != null) 
			player.addStat(stat, 1);
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{
		if(event.crafting == null){return;}

		if(event.crafting.getItem() == ItemRegistry.apple_chocolate)
		{ 
			addStatSafe(appleChoc,event.player);
		}
		else if(appleLapis != null && event.crafting.getItem() == ItemRegistry.apple_lapis)
		{ 
			addStatSafe(appleLapis,event.player); 
		}
		else if(appleEmerald != null && event.crafting.getItem() == ItemRegistry.apple_emerald)
		{ 
			addStatSafe(appleEmerald,event.player); 
		}
		else if(appleDiamond != null && event.crafting.getItem() == ItemRegistry.apple_diamond)
		{ 
			addStatSafe(appleDiamond,event.player);  
		}
	}
}
