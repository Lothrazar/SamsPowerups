package com.lothrazar.samscontent.stats;

import java.util.ArrayList;

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.util.Reference; 

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent; 
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AchievementRegistry 
{
	//reference http://www.minecraftforge.net/wiki/How_to_add_an_Achievement
 
	private AchievementPage page;
	
	private ArrayList<Achievement> ach = new ArrayList<Achievement>();
	
	public Achievement appleChoc;
	public Achievement appleLapis;
	public Achievement appleDiamond;
	public Achievement appleEmerald; 
	public Achievement appleEnder;
	public Achievement beetrootSeed; 
	 
	public Achievement appleNether;
	public Achievement lapisCarrot;
	public Achievement emeraldCarrot;
	public Achievement diamondCarrot;
	//public Achievement wandWater;
 
 
	

 
 
	public Achievement carbonPaper;
	
	public Achievement soulstone;
	public Achievement enderBook;

	
	public Achievement scaffolding;

	private Achievement command_block_weather;

	private Achievement command_block_tpspawn;

	private Achievement command_block_tpbed;

	private Achievement block_fragile;

	private Achievement block_fishing;

	private Achievement block_shear_sheep;

	private Achievement block_storeempty;

	private Achievement block_xray;
	
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
			appleEnder = new Achievement(Reference.MODID + "_appleEnder", "appleEnder" ,xCurrent, yCurrent,ItemRegistry.apple_ender,null);
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

		if(ItemRegistry.diamondCarrot != null)//DIAMOND
		{ 
			xCurrent += xSpacing;
			diamondCarrot = new Achievement(Reference.MODID + "_diamondCarrot", "diamondCarrot" ,xCurrent, yCurrent,ItemRegistry.diamondCarrot,null);
			register(diamondCarrot);
		}
		if(ItemRegistry.emeraldCarrot != null)//DIAMOND
		{ 
			xCurrent += xSpacing;
			emeraldCarrot = new Achievement(Reference.MODID + "_emeraldCarrot", "emeraldCarrot" ,xCurrent, yCurrent,ItemRegistry.emeraldCarrot,null);
			register(emeraldCarrot);
		}
		if(ItemRegistry.lapisCarrot != null)//DIAMOND
		{ 
			xCurrent += xSpacing;
			lapisCarrot = new Achievement(Reference.MODID + "_lapisCarrot", "lapisCarrot" ,xCurrent, yCurrent,ItemRegistry.lapisCarrot,null);
			register(lapisCarrot);
		} 

		xCurrent = xStart;//new row
		yCurrent += ySpacing;
 
 
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
 
		if(ItemRegistry.soulstone != null) 
		{ 
			xCurrent += xSpacing;
			soulstone = new Achievement(Reference.MODID + "_soulstone", "soulstone" ,xCurrent, yCurrent,ItemRegistry.soulstone,null);
			register(soulstone);
		}
 

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
		 
		if(ItemRegistry.itemEnderBook != null) 
		{ 
			xCurrent += xSpacing;
			enderBook = new Achievement(Reference.MODID + "_enderBook", "enderBook" ,xCurrent, yCurrent,ItemRegistry.itemEnderBook,null);
			register(enderBook);
		} 
		if(BlockRegistry.block_fragile != null) 
		{ 
			xCurrent += xSpacing;
			block_fragile = new Achievement(Reference.MODID + "_block_fragile", "block_fragile" ,xCurrent, yCurrent,BlockRegistry.block_fragile,null);
			register(block_fragile);
		}
		if(BlockRegistry.block_xray != null) 
		{ 
			xCurrent += xSpacing;
			block_xray = new Achievement(Reference.MODID + "_block_xray", "block_xray" ,xCurrent, yCurrent,BlockRegistry.block_xray,null);
			register(block_xray);
		}
		
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
		if(BlockRegistry.block_fishing != null) 
		{ 
			xCurrent += xSpacing;
			block_fishing = new Achievement(Reference.MODID + "_block_fishing", "block_fishing" ,xCurrent, yCurrent,BlockRegistry.block_fishing,null);
			register(block_fishing);
		}
		if(BlockRegistry.block_shear_sheep != null) 
		{ 
			xCurrent += xSpacing;
			block_shear_sheep = new Achievement(Reference.MODID + "_block_shear_sheep", "block_shear_sheep" ,xCurrent, yCurrent,BlockRegistry.block_shear_sheep,null);
			register(block_shear_sheep);
		}
		if(BlockRegistry.block_storeempty != null) 
		{ 
			xCurrent += xSpacing;
			block_storeempty = new Achievement(Reference.MODID + "_block_storeempty", "block_storeempty" ,xCurrent, yCurrent,BlockRegistry.block_storeempty,null);
			register(block_storeempty);
		}
		
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
		if(BlockRegistry.command_block_tpbed != null) 
		{ 
			xCurrent += xSpacing;
			command_block_tpbed = new Achievement(Reference.MODID + "_command_block_tpbed", "command_block_tpbed" ,xCurrent, yCurrent,BlockRegistry.command_block_tpbed,null);
			register(command_block_tpbed);
		}
		if(BlockRegistry.command_block_tpspawn != null) 
		{ 
			xCurrent += xSpacing;
			command_block_tpspawn = new Achievement(Reference.MODID + "_command_block_tpspawn", "command_block_tpspawn" ,xCurrent, yCurrent,BlockRegistry.command_block_tpspawn,null);
			register(command_block_tpspawn);
		}
		if(BlockRegistry.command_block_weather != null) 
		{ 
			xCurrent += xSpacing;
			command_block_weather = new Achievement(Reference.MODID + "_command_block_weather", "command_block_weather" ,xCurrent, yCurrent,BlockRegistry.command_block_weather,null);
			register(command_block_weather);
		}
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
		else if(item == ItemRegistry.apple_lapis)
		{ 
			addStatSafe(appleLapis,event.player); 
		}
		else if(item == ItemRegistry.apple_emerald)
		{ 
			addStatSafe(appleEmerald,event.player); 
		}
		else if(item == ItemRegistry.apple_diamond)
		{ 
			addStatSafe(appleDiamond,event.player);  
		}
		else if(item == ItemRegistry.apple_ender)
		{ 
			addStatSafe(appleEnder,event.player);  
		} 
 
		else if(item == ItemRegistry.apple_netherwart)
		{ 
			addStatSafe(appleNether,event.player);  
		} 
		else if(item == ItemRegistry.diamondCarrot)
		{ 
			addStatSafe(diamondCarrot,event.player);  
		} 
		else if(item == ItemRegistry.emeraldCarrot)
		{ 
			addStatSafe(emeraldCarrot,event.player);  
		} 
		else if(item == ItemRegistry.lapisCarrot)
		{ 
			addStatSafe(lapisCarrot,event.player);  
		} 
	  
		else if(item == ItemRegistry.soulstone)//TODO: v2
		{ 
			addStatSafe(soulstone,event.player);  
		} 
		else if(item == ItemRegistry.itemEnderBook) 
		{ 
			addStatSafe(enderBook,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.block_fishing) 
		{ 
			addStatSafe(block_fishing,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.block_fragile) 
		{ 
			addStatSafe(block_fragile,event.player);  
		} 	
		else if(Block.getBlockFromItem(item) == BlockRegistry.block_shear_sheep) 
		{ 
			addStatSafe(block_shear_sheep,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.block_storeempty) 
		{ 
			addStatSafe(block_storeempty,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.block_xray) 
		{ 
			addStatSafe(block_xray,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.command_block_tpbed) 
		{ 
			addStatSafe(command_block_tpbed,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.command_block_tpspawn) 
		{ 
			addStatSafe(command_block_tpspawn,event.player);  
		} 
		else if(Block.getBlockFromItem(item) == BlockRegistry.command_block_weather) 
		{ 
			addStatSafe(command_block_weather,event.player);  
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
