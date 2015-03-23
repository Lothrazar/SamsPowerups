package com.lothrazar.samscontent.stats;

import java.util.ArrayList;

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
	
	private ArrayList<Achievement> ach = new ArrayList<Achievement>();
	
	public Achievement appleChoc;
	public Achievement appleLapis;
	public Achievement appleDiamond;
	public Achievement appleEmerald;
	public Achievement baseWand;
	public Achievement wandLightning;
	public Achievement appleEnder;
	public Achievement beetrootSeed;
	
	private void register(Achievement a)
	{
		a.registerStat();
		ach.add(a);
	}
	
	public void registerAll()
	{
		//Achievement constructor (id, name, x, y, icon, pre-requisite achievement 
		
		final int xSpacing = 2;
		final int ySpacing = 2;
		
		final int xStart = 0;
		final int yStart = 0;
		
		int xCurrent = xStart;
		int yCurrent = yStart;
		
		if(ItemRegistry.apple_chocolate != null)
		{ 
			xCurrent += xSpacing;
			appleChoc = new Achievement(Reference.MODID + "_appleChoc", "appleChoc", xCurrent, yCurrent, ItemRegistry.apple_chocolate, null);
			register(appleChoc);
		} 
		
		if(ItemRegistry.apple_lapis != null)
		{ 
			xCurrent += xSpacing;
			appleLapis = new Achievement(Reference.MODID + "_appleLapis", "appleLapis", xCurrent, yCurrent, ItemRegistry.apple_lapis, null);
			register(appleLapis);
		} 
		
		if(ItemRegistry.apple_diamond != null)
		{ 
			xCurrent += xSpacing;
			appleDiamond = new Achievement(Reference.MODID + "_appleDiamond", "appleDiamond", xCurrent, yCurrent, ItemRegistry.apple_diamond, null);
			register(appleDiamond); 
		} 
		
		if(ItemRegistry.apple_emerald != null)
		{ 
			xCurrent += xSpacing;
			appleEmerald = new Achievement(Reference.MODID + "_appleEmerald", "appleEmerald", xCurrent, yCurrent, ItemRegistry.apple_emerald, null);
			register(appleEmerald);
		} 
		
		if(ItemRegistry.apple_ender != null)
		{ 
			//xCurrent += xSpacing;
			appleEnder = new Achievement(Reference.MODID + "_appleEnder", "appleEnder" , AchievementList.buildBetterPickaxe.displayColumn + 2, AchievementList.buildBetterPickaxe.displayRow, ItemRegistry.apple_ender, AchievementList.buildBetterPickaxe);
			appleEnder.registerStat(); 
		} 
		

		if(ItemRegistry.beetrootSeed != null)
		{  
			beetrootSeed = new Achievement(Reference.MODID + "_beetrootSeed", "beetrootSeed" , AchievementList.buildHoe.displayColumn - 2, AchievementList.buildHoe.displayRow + 1, ItemRegistry.beetrootSeed, AchievementList.buildHoe);
			beetrootSeed.registerStat(); 
		} 
		
/*
		//next row down
		yCurrent += ySpacing;
		xCurrent = xStart;
		
		if(ItemRegistry.baseWand != null)
		{ 
			xCurrent += xSpacing;
			baseWand = new Achievement(Reference.MODID + "_baseWand", "baseWand", xCurrent, yCurrent, ItemRegistry.baseWand, null);
			register(baseWand);
		} 

		//next row down
		yCurrent += ySpacing;
		xCurrent = xStart;
		
		if(ItemRegistry.wandLightning != null)
		{ 
			xCurrent += xSpacing;
			wandLightning = new Achievement(Reference.MODID + "_" + "wandLightning", "wandLightning", xCurrent, yCurrent, ItemRegistry.wandLightning, baseWand);
			register(wandLightning);
		} 
*/		
		
		//TODO: IDEA: importing achieves from Xpos/PS versions 
		// http://minecraft.gamepedia.com/Achievements 
		
		
		
		
		//TODO: can page name be from lang file?
		page = new AchievementPage("Sam's Content",(Achievement[]) ach.toArray(new Achievement[0]));

	 	AchievementPage.registerAchievementPage(page);
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{
		if(event.crafting == null){return;}

		if(event.crafting.getItem() == ItemRegistry.apple_chocolate)
		{ 
			addStatSafe(appleChoc,event.player);
		}
		else if(event.crafting.getItem() == ItemRegistry.apple_lapis)
		{ 
			addStatSafe(appleLapis,event.player); 
		}
		else if(event.crafting.getItem() == ItemRegistry.apple_emerald)
		{ 
			addStatSafe(appleEmerald,event.player); 
		}
		else if(event.crafting.getItem() == ItemRegistry.apple_diamond)
		{ 
			addStatSafe(appleDiamond,event.player);  
		}
	}
	
	private void addStatSafe(Achievement stat, EntityPlayer player)
	{
		if(stat != null) 
			player.addStat(stat, 1);
	}
	
}
