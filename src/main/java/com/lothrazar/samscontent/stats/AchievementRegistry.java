package com.lothrazar.samscontent.stats;

import java.util.ArrayList;

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.util.Reference; 

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
	public Achievement appleGhost;
	public Achievement appleNether;
	public Achievement lapisCarrot;
	public Achievement emeraldCarrot;
	public Achievement diamondCarrot;
	public Achievement wandWater;
	public Achievement wandTransform;
	public Achievement wandPiston;
	

	public Achievement frostSnowball;
	public Achievement lightningCharge;
	public Achievement harvestCharge;
	public Achievement throwFirecharge;
	
	//public Achievement scaffolding;
	public Achievement carbonPaper;
	
	public Achievement soulstone;
	public Achievement enderBook;
	
	
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
		if(ItemRegistry.apple_ghost != null)
		{ 
			xCurrent += xSpacing;
			appleGhost = new Achievement(Reference.MODID + "_appleGhost", "appleGhost" ,xCurrent, yCurrent,ItemRegistry.apple_ghost,null);
			register(appleGhost);
		}
		if(ItemRegistry.apple_nether_star != null)
		{ 
			xCurrent += xSpacing;
			appleNether = new Achievement(Reference.MODID + "_appleNether", "appleNether" ,xCurrent, yCurrent,ItemRegistry.apple_nether_star,null);
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

		if(ItemRegistry.wandWater != null) 
		{ 
			xCurrent += xSpacing;
			wandWater = new Achievement(Reference.MODID + "_wandWater", "wandWater" ,xCurrent, yCurrent,ItemRegistry.wandWater,null);
			register(wandWater);
		}
		if(ItemRegistry.wandTransform != null) 
		{ 
			xCurrent += xSpacing;
			wandTransform = new Achievement(Reference.MODID + "_wandTransform", "wandTransform" ,xCurrent, yCurrent,ItemRegistry.wandTransform,null);
			register(wandTransform);
		}
		if(ItemRegistry.wand_piston != null) 
		{ 
			xCurrent += xSpacing;
			wandPiston = new Achievement(Reference.MODID + "_wandPiston", "wandPiston" ,xCurrent, yCurrent,ItemRegistry.wand_piston,null);
			register(wandPiston);
		}	
		if(ItemRegistry.itemEnderBook != null) 
		{ 
			xCurrent += xSpacing;
			enderBook = new Achievement(Reference.MODID + "_enderBook", "enderBook" ,xCurrent, yCurrent,ItemRegistry.itemEnderBook,null);
			register(enderBook);
		}
 
		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
		if(ItemRegistry.frozen_snowball != null) 
		{ 
			xCurrent += xSpacing;
			frostSnowball = new Achievement(Reference.MODID + "_frostSnowball", "frostSnowball" ,xCurrent, yCurrent,ItemRegistry.frozen_snowball,null);
			register(frostSnowball);
		}
		if(ItemRegistry.soulstone != null) 
		{ 
			xCurrent += xSpacing;
			soulstone = new Achievement(Reference.MODID + "_soulstone", "soulstone" ,xCurrent, yCurrent,ItemRegistry.soulstone,null);
			register(soulstone);
		}
		if(ItemRegistry.carbon_paper != null) 
		{ 
			xCurrent += xSpacing;
			carbonPaper = new Achievement(Reference.MODID + "_carbonPaper", "carbonPaper" ,xCurrent, yCurrent,ItemRegistry.carbon_paper,null);
			register(carbonPaper);
		}

		xCurrent = xStart;//new row
		yCurrent += ySpacing;
		
		if(ItemRegistry.lightning_charge != null) 
		{ 
			xCurrent += xSpacing;
			lightningCharge = new Achievement(Reference.MODID + "_lightningCharge", "lightningCharge" ,xCurrent, yCurrent,ItemRegistry.lightning_charge,null);
			register(lightningCharge);
		}
		if(ItemRegistry.harvest_charge != null) 
		{ 
			xCurrent += xSpacing;
			harvestCharge = new Achievement(Reference.MODID + "_harvestCharge", "harvestCharge" ,xCurrent, yCurrent,ItemRegistry.harvest_charge,null);
			register(harvestCharge);
		}
		if(ItemRegistry.fire_charge_throw != null) 
		{ 
			xCurrent += xSpacing;
			throwFirecharge = new Achievement(Reference.MODID + "_throwFirecharge", "throwFirecharge" ,xCurrent, yCurrent,ItemRegistry.fire_charge_throw,null);
			register(throwFirecharge);
		}
		/*
		if(BlockRegistry.block_fragile != null) 
		{ 
			xCurrent += xSpacing;
			scaffolding = new Achievement(Reference.MODID + "_scaffolding", "scaffolding" ,xCurrent, yCurrent,BlockRegistry.block_fragile,null);
			register(scaffolding);
		}
   */
		
		
		page = new AchievementPage("Sam's Content",(Achievement[]) ach.toArray(new Achievement[0]));

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
		else if(item == ItemRegistry.apple_ghost)
		{ 
			addStatSafe(appleGhost,event.player);  
		} 
		else if(item == ItemRegistry.apple_nether_star)
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
		else if(item == ItemRegistry.wand_piston)
		{ 
			addStatSafe(wandPiston,event.player);  
		} 
		else if(item == ItemRegistry.wandTransform)
		{ 
			addStatSafe(wandTransform,event.player);  
		} 
		else if(item == ItemRegistry.wandWater)
		{ 
			addStatSafe(wandWater,event.player);  
		} 
		else if(item == ItemRegistry.frozen_snowball)
		{ 
			addStatSafe(frostSnowball,event.player);  
		} 
		else if(item == ItemRegistry.soulstone)//TODO: v2
		{ 
			addStatSafe(soulstone,event.player);  
		} 
		else if(item == ItemRegistry.itemEnderBook) 
		{ 
			addStatSafe(enderBook,event.player);  
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
