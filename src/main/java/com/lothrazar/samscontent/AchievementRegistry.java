package com.lothrazar.samscontent;

import com.lothrazar.samscontent.item.ItemRegistry;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class AchievementRegistry 
{
	//private AchievementPage page;
	
	public Achievement test;
	 
	
	public void registerAll()
	{
		
		//reference http://www.minecraftforge.net/wiki/How_to_add_an_Achievement
		
		//First, you need to make a new object. (Make the achievement itself) The parameters for this is: 
		//Achievement([ID], [NAME], [X COORD], [Y COORD], [ICON (BLOCK/ITEM ITSELF)], [ACHIEVEMENT REQUIRED]) For my achievement I did the following:
		test = new Achievement("2002", "TimeAchieve", 1, -2, (Item)ItemRegistry.apple_chocolate, null);
				
		//optional//.setSpecial()		
				//;;(2 );//AchievementList.blazeRod
		 
		//page = new AchievementPage("Sam's Content",test);//, ach1, ach2, ach3, ach4

		//TODO: howcome players start with this trigered as on? should start as off?
		
		test.registerStat();
		

	//	AchievementPage.registerAchievementPage(page);
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{
		System.out.println("onCraft");//TODO: WHY DOESNT THIS EVER HAPPEN. it isregistered
		if(event.crafting != null && event.crafting.getItem() == ItemRegistry.apple_chocolate)
		{
			System.out.println("addstat");
			event.player.addStat(test, 1);
		}
	}
}
