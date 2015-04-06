package com.lothrazar.samscontent.stats;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.PlayerPowerups;
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

	private Achievement archer;

	private Achievement lionTamer;

	private Achievement zombieDoc;

	private Achievement ironman;

	private Achievement bodyguard;

	private Achievement music;

	private Achievement renewableEnergy;

	private Achievement chestfulCob;

	private Achievement stayinFrosty;

	private Achievement rainbow;

	private Achievement shear;

	private Achievement ironBelly;

	private Achievement sign;

	private Achievement potPlanter;

	private Achievement haggler;

	private Achievement passingTime;

	private Achievement porkChop;

	private Achievement leaderOfPack;

	private Achievement dispense;

	private Achievement moarTools;
	
	private void register(Achievement a)
	{
		a.registerStat();
		ach.add(a);
	}
	
	public void registerAll()
	{
		registerVanillaPage(); 
		
		//registerConsolePage();//TODO: need work on triggering each one, big feature
		
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
	
		moarTools = new Achievement(Reference.MODID + "moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools); 
		xCurrent += xSpacing;
		
		dispense = new Achievement(Reference.MODID + "dispense", "dispense", xCurrent, yCurrent, Items.golden_axe, null);
		dispense.registerStat();
		console.add(dispense); 
		xCurrent += xSpacing;
		
		leaderOfPack = new Achievement(Reference.MODID + "leaderOfPack", "leaderOfPack", xCurrent, yCurrent, Items.golden_axe, null);
		leaderOfPack.registerStat();
		console.add(leaderOfPack);
		xCurrent += xSpacing;
		/*
		Achievement awardedAll = new Achievement(Reference.MODID + "_moarTools", "moarTools", xCurrent, yCurrent, Items.golden_axe, null);
		moarTools.registerStat();
		console.add(moarTools);
		xCurrent += xSpacing;
		*/
		
		
		porkChop = new Achievement(Reference.MODID + "porkChop", "porkChop", xCurrent, yCurrent, Items.golden_axe, null);
		porkChop.registerStat();
		console.add(porkChop);
		
		xCurrent += xSpacing;
		
		passingTime = new Achievement(Reference.MODID + "passingTime", "passingTime", xCurrent, yCurrent, Items.golden_axe, null);
		passingTime.registerStat();
		console.add(passingTime); 
		xCurrent += xSpacing;
		

		yCurrent += ySpacing;  //next row down
		xCurrent = xStart;
		
		haggler = new Achievement(Reference.MODID + "haggler", "haggler", xCurrent, yCurrent, Items.golden_axe, null);
		haggler.registerStat();
		console.add(haggler);
		
		xCurrent += xSpacing;
		
		potPlanter = new Achievement(Reference.MODID + "potPlanter", "potPlanter", xCurrent, yCurrent, Items.golden_axe, null);
		potPlanter.registerStat();
		console.add(potPlanter); 
		xCurrent += xSpacing;
		
		sign = new Achievement(Reference.MODID + "sign", "sign", xCurrent, yCurrent, Items.golden_axe, null);
		sign.registerStat();
		console.add(sign); 
		xCurrent += xSpacing;
		
		ironBelly = new Achievement(Reference.MODID + "ironBelly", "ironBelly", xCurrent, yCurrent, Items.golden_axe, null);
		ironBelly.registerStat();
		console.add(ironBelly); 
		xCurrent += xSpacing;
		
		shear = new Achievement(Reference.MODID + "shear", "shear", xCurrent, yCurrent, Items.golden_axe, null);
		shear.registerStat();
		console.add(shear); 
		xCurrent += xSpacing;
		

		yCurrent += ySpacing;  //next row down
		xCurrent = xStart;
		
		rainbow = new Achievement(Reference.MODID + "rainbow", "rainbow", xCurrent, yCurrent, Items.golden_axe, null);
		rainbow.registerStat();
		console.add(rainbow); 
		xCurrent += xSpacing;
		
		stayinFrosty = new Achievement(Reference.MODID + "stayinFrosty", "stayinFrosty", xCurrent, yCurrent, Items.golden_axe, null);
		stayinFrosty.registerStat();
		console.add(stayinFrosty); 
		xCurrent += xSpacing;
		
		chestfulCob = new Achievement(Reference.MODID + "chestfulCob", "chestfulCob", xCurrent, yCurrent, Items.golden_axe, null);
		chestfulCob.registerStat();
		console.add(chestfulCob); 
		xCurrent += xSpacing;
		
		renewableEnergy = new Achievement(Reference.MODID + "renewableEnergy", "renewableEnergy", xCurrent, yCurrent, Items.golden_axe, null);
		renewableEnergy.registerStat();
		console.add(renewableEnergy); 
		xCurrent += xSpacing;
		
		music = new Achievement(Reference.MODID + "music", "music", xCurrent, yCurrent, Items.golden_axe, null);
		music.registerStat();
		console.add(music); 
		xCurrent += xSpacing;
		

		yCurrent += ySpacing;  //next row down
		xCurrent = xStart;
		
		bodyguard = new Achievement(Reference.MODID + "bodyguard", "bodyguard", xCurrent, yCurrent, Items.golden_axe, null);
		bodyguard.registerStat();
		console.add(bodyguard); 
		xCurrent += xSpacing;
		
		ironman = new Achievement(Reference.MODID + "ironman", "ironman", xCurrent, yCurrent, Items.golden_axe, null);
		ironman.registerStat();
		console.add(ironman); 
		xCurrent += xSpacing;
		
		zombieDoc = new Achievement(Reference.MODID + "zombieDoc", "zombieDoc", xCurrent, yCurrent, Items.golden_axe, null);
		zombieDoc.registerStat();
		console.add(zombieDoc); 
		xCurrent += xSpacing;
		
		lionTamer = new Achievement(Reference.MODID + "lionTamer", "lionTamer", xCurrent, yCurrent, Items.golden_axe, null);
		lionTamer.registerStat();
		console.add(lionTamer); 
		xCurrent += xSpacing;
		
		archer = new Achievement(Reference.MODID + "archer", "archer", xCurrent, yCurrent, Items.golden_axe, null);
		archer.registerStat();
		console.add(archer); 
		xCurrent += xSpacing;

		// http://minecraft.gamepedia.com/Achievements 
		AchievementPage consolepage = new AchievementPage("From Console",(Achievement[]) console.toArray(new Achievement[0]));


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
 	public void onEntityConstructing(EntityConstructing event)
 	{ 
 		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
 		{ 
 			PlayerPowerups.register((EntityPlayer) event.entity);
 		} 
 	}
	 
	@SubscribeEvent
	public void onPickup(ItemPickupEvent event)
	{
		if(event.pickedUp == null || event.pickedUp.getEntityItem() == null){return;}
		
		ItemStack items = event.pickedUp.getEntityItem();
		
		if(items.getItem() == Items.emerald && items.stackSize >= 30)
		{
			//TODO: dont think this counts trading..??
			addStatSafe(haggler, event.player);	
			
			
		} 
		
		if(items.getItem() == Item.getItemFromBlock(Blocks.wool))
		{
			PlayerPowerups ext = PlayerPowerups.get(event.player);
			
			
			//ext.triggerWoolColor(items.getMetadata());
			
		}
		
		//TODO: count to 16 different wool types
		//but we dont want ot have to keep all at once
	}
	
	@SubscribeEvent
	public void onSmelt(ItemSmeltedEvent event)
	{
		if(event.smelting == null){return;}
		
		Item item = event.smelting.getItem();
		int meta = event.smelting.getMetadata();

		if(item == Items.coal && meta == 1)//charcoal
		{ 
			addStatSafe(renewableEnergy,event.player);
		} 
	}
	
	public void onPlace()
	{
		//TODO: this for pot Plant placed
	}
	
	public void onEaten()
	{
		//TODO: set this up/ porkChop - also on smelted? on player Eat event - so on use but with Action.type == EAT or something
		
		
		//TODO: if eaten zombie flesh & starving
		
		
	}
	
	//TODO: how to detect iron golem creation?
	public void onIronGolemCreated(EntityPlayer player)//, BlockPos pos
	{
		
	}
	
	//TODO: on Armor Worn?
	
	//TODO: on villager cvured/gold apple
	
	//TODO: on mob killed by player
	public void onMobKilledByPlayer(EntityLivingBase mob, EntityPlayer player)
	{
		
	}
	
	//TODO: on shear sheep event
	
	//TODO: swim in lava event (and check potion for fire prot)
	
	//TODO: no idea how to do cobble one
	
	
	
	//TODO: on tame wolf event, and count them for leaderOfPack
	//TODO: tame ocelt event
	
	//TODO: play for 100 days trigger
	
	
	@SubscribeEvent
	public void onCraft(ItemCraftedEvent event)
	{ 
		if(event.crafting == null){return;}
		
		Item item = event.crafting.getItem();
		int meta = event.crafting.getMetadata();

	//	System.out.println("   "+item.getUnlocalizedName());
		

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
		else if(item == Item.getItemFromBlock(Blocks.dispenser))
		{  
			addStatSafe(dispense,event.player);  
		}
		else if(item == Item.getItemFromBlock(Blocks.flower_pot))
		{ 
//TODO: potPlanter counts this plus the placement of it together
		}
		else if(item == Items.sign)
		{ 
//TODO: also this plus if placed, two things for that stat
		}
		//TODO: for moarTools, craft pickaxe,shovel,axe,hoe, kind of four hidden achievements and all four then give the last one
		
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
