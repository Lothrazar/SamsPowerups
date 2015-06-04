package com.lothrazar.samscontent.stats;

import java.util.ArrayList;
 
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.util.Reference; 

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

public class AchievementRegistry 
{
	//reference http://www.minecraftforge.net/wiki/How_to_add_an_Achievement
 
	private AchievementPage page;
	
	private ArrayList<Achievement> ach = new ArrayList<Achievement>();
	
	public Achievement appleChoc;
 
	public Achievement appleDiamond;
	public Achievement appleEmerald; 
	public Achievement appleEnder;
	public Achievement beetrootSeed; 
	 
	public Achievement appleNether; 
	//public Achievement wandWater;
 
 
	

 
 
	public Achievement carbonPaper;
	
	//public Achievement soulstone;
	//public Achievement enderBook;

	
	public Achievement scaffolding;

	private Achievement command_block_weather;

	private Achievement command_block_tpspawn;

	private Achievement command_block_tpbed;

	private Achievement block_fragile;

	private Achievement block_fishing;

	private Achievement block_shear_sheep;

	private Achievement block_storeempty;
 
	
	private void register(Achievement a)
	{
		a.registerStat();
		ach.add(a);
	}
	
	public void registerAll()
	{
		registerVanillaPage(); 
		 
		registerMyPage();
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
  
		if(ItemRegistry.apple_diamond != null)
		{ 
			xCurrent += xSpacing;
			appleDiamond = new Achievement(Reference.MODID + "_appleDiamond", "appleDiamond", xCurrent, yCurrent, ItemRegistry.apple_diamond, null);
			register(appleDiamond); 
		}  
		
		xCurrent = xStart;//new row
		yCurrent += ySpacing;

		if(ItemRegistry.apple_emerald != null)
		{ 
			xCurrent += xSpacing;
			appleEmerald = new Achievement(Reference.MODID + "_appleEmerald", "appleEmerald", xCurrent, yCurrent, ItemRegistry.apple_emerald, null);
			register(appleEmerald);
		}  

		if(ItemRegistry.apple_ender != null)
		{ 
			xCurrent += xSpacing;
			appleEnder = new Achievement(Reference.MODID + "_appleEnder", "appleEnder", xCurrent, yCurrent, ItemRegistry.apple_ender, null);
			register(appleEnder);
		}  
		if(ItemRegistry.apple_netherwart != null)
		{ 
			xCurrent += xSpacing;
			appleNether = new Achievement(Reference.MODID + "_appleNether", "appleNether" ,xCurrent, yCurrent,ItemRegistry.apple_netherwart,null);
			register(appleNether);
		} 
		
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
 
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
 
 
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
 
 

		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
	 
		 
		if(ItemRegistry.carbon_paper != null) 
		{ 
			xCurrent += xSpacing;
			carbonPaper = new Achievement(Reference.MODID + "_carbonPaper", "carbonPaper" ,xCurrent, yCurrent,ItemRegistry.carbon_paper,null);
			register(carbonPaper);
		}

		xCurrent = xStart;//new row
		yCurrent += ySpacing;
	 
		 
	 
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
		 
		
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
	 
		//xCurrent = xStart;//new row
		//yCurrent += ySpacing;
		
		
		page = new AchievementPage(Reference.NAME,(Achievement[]) ach.toArray(new Achievement[0]));

	 	AchievementPage.registerAchievementPage(page);
	}

	public void registerVanillaPage() 
	{ 
		if(ItemRegistry.beetroot_seed != null)
		{  
			beetrootSeed = new Achievement(Reference.MODID + "_beetrootSeed", "beetrootSeed" , AchievementList.buildHoe.displayColumn - 2, AchievementList.buildHoe.displayRow + 1, ItemRegistry.beetroot_seed, AchievementList.buildHoe);
			beetrootSeed.registerStat(); //not on my page
		}
	}

	 
	@SubscribeEvent
	public void onPickup(ItemPickupEvent event)
	{
		//if(event.pickedUp == null || event.pickedUp.getEntityItem() == null){return;}

		//PlayerPowerups ext = PlayerPowerups.get(event.player);
		//ItemStack items = event.pickedUp.getEntityItem();
 
	}
	
	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event)
	{
		//if(event.smelting == null){return;}
		
		//Item item = event.smelting.getItem();
		//int meta = event.smelting.getMetadata();
 
	}
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{ 
		if(event.crafting == null){return;}
		
		Item item = event.crafting.getItem();
		int meta = event.crafting.getMetadata();
 
		if(item == ItemRegistry.apple_chocolate)
		{ 
			addStatSafe(appleChoc,event.player);
		} 
		else if(item == ItemRegistry.apple_emerald)
		{ 
			addStatSafe(appleEmerald,event.player); 
		}
		else if(item == ItemRegistry.apple_ender)
		{ 
			addStatSafe(appleEnder,event.player); 
		}
		else if(item == ItemRegistry.apple_diamond)
		{ 
			addStatSafe(appleDiamond,event.player);  
		} 
		else if(item == ItemRegistry.apple_netherwart)
		{ 
			addStatSafe(appleNether,event.player);  
		} 
   
	}
	
	private void addStatSafe(Achievement stat, EntityPlayer player)
	{
		addStatSafe(stat, player, 1);
	} 
	private void addStatSafe(Achievement stat, EntityPlayer player, int amt)
	{
		if(stat != null && player != null) 
			player.addStat(stat, amt);
	} 
}
