package com.lothrazar.samscontent.stats;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.util.Reference; 

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
		registerVanillaPage(); 
		
		registerConsolePage();
		
		registerMyPage();
	}

	private void registerConsolePage() 
	{
		ArrayList<Achievement> console = new ArrayList<Achievement>();
		//http://minecraft.gamepedia.com/Achievements
 
		final int xSpacing = 2;
		final int ySpacing = 2;
		
		final int xStart = 0;
		final int yStart = 0;
		
		int xCurrent = xStart;
		int yCurrent = yStart;
		
		//TODO: can page name be from lang file?
		AchievementPage consolepage = new AchievementPage("From Console",(Achievement[]) console.toArray(new Achievement[0]));


		Achievement moarTools = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement dispense = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement leaderOfPack;
		
		Achievement awardedAll = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement porkChop = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement passingTime = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement haggler = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement potPlanter = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement sign = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement ironBelly = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement shear = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement rainbow = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement stayinFrosty = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement chestfulCob = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement renewableEnergy = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement music = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement ironman = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement zombieDoc = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement lionTamer = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		Achievement archer = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		
		xCurrent += xSpacing;
		
		
		
		
		
	 	AchievementPage.registerAchievementPage(consolepage);
	}

	public void registerMyPage() 
	{ 
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
			xCurrent += xSpacing;
			appleEnder = new Achievement(Reference.MODID + "_appleEnder", "appleEnder" ,xCurrent, yCurrent,ItemRegistry.apple_ender,null);
			register(appleEnder);
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

	public void registerVanillaPage() 
	{ 
		if(ItemRegistry.beetrootSeed != null)
		{  
			beetrootSeed = new Achievement(Reference.MODID + "_beetrootSeed", "beetrootSeed" , AchievementList.buildHoe.displayColumn - 2, AchievementList.buildHoe.displayRow + 1, ItemRegistry.beetrootSeed, AchievementList.buildHoe);
			beetrootSeed.registerStat(); //not on my page
		}
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
		else if(event.crafting.getItem() == ItemRegistry.apple_ender)
		{ 
			addStatSafe(appleEnder,event.player);  
		}
	}
	
	private void addStatSafe(Achievement stat, EntityPlayer player)
	{
		if(stat != null) 
			player.addStat(stat, 1);
	} 
}
