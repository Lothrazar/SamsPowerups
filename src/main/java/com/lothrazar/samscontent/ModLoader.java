package com.lothrazar.samscontent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList; 

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;  

import com.lothrazar.block.*; 
import com.lothrazar.command.*; 
import com.lothrazar.event.*; 
import com.lothrazar.item.*;   
import com.lothrazar.samscontent.proxy.ClientProxy;
import com.lothrazar.samscontent.proxy.CommonProxy;
import com.lothrazar.samskeyslider.MessageKeyPressed;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry; 

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = Reference.MODID, version = Reference.VERSION	, canBeDeactivated = false, name = Reference.NAME, useMetadata = true ,guiFactory = "com.lothrazar.gui.ConfigGuiFactory") 
public class ModLoader
{
	@Instance(value = Reference.MODID)
	public static ModLoader instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	
	public static Logger logger; 
	public static ConfigFile configSettings;
	public static SimpleNetworkWrapper network; 
	public static RuntimeChangelog changelog;
	
	
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsContent") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.apple_chocolate;
		}
	};
		
	private void initModInfo(ModMetadata mcinfo)
	{ 
		mcinfo.modId = Reference.MODID;
		mcinfo.name = Reference.NAME;
		mcinfo.version = Reference.VERSION;
		mcinfo.description = "Sam's content.";
		ArrayList<String> authorList = new ArrayList<String>();
		authorList.add("Lothrazar");
		mcinfo.authorList = authorList;
	}
 
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog(); 
		changelog = new RuntimeChangelog();
		
		initModInfo(event.getModMetadata());
		
		configSettings = new ConfigFile(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);

		PotionRegistry.registerPotionEffects();
		
		this.registerEventHandlers(); //IEXTENDED properties sasy this goes in init?

		ItemRegistry.registerItems();
		
		BlockRegistry.registerBlocks();

		BlockHardnessRegistry.registerChanges();
		
	
		//.setIconIndex(0, 0);
		/*//TODO: fluid registry
		 * Fluid flows in the world and gets placed but is invisible& transparent
		
		//http://www.minecraftforge.net/wiki/Create_a_Fluid
		fluidMilk = new Fluid("fluid_milk");
		FluidRegistry.registerFluid(fluidMilk);

		ModLoader.blockMilk = new BlockFluidMilk(fluidMilk,Material.water); 
		SamsRegistry.registerBlock(ModLoader.blockMilk, "fluid_milk");
		  
FluidContainerRegistry.registerFluidContainer(new FluidStack(fluidMilk,1), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
*/
	    
	}
	//public static BlockFluidMilk blockMilk;
	//public Fluid fluidMilk;
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{     
		
		CreativeTweaks.registerTabImprovements();
	
		MobSpawningRegistry.registerSpawns();
  
		ChestGen.regsiterLoot();
		  
		Recipes.registerRecipes();
		 
		StackSizeIncreaser.registerChanges(); 
 
  		if(ModLoader.configSettings.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
  		}
  			
		proxy.registerRenderers();
		 
		//this worked in 1.7, adding trades to villagers
		/*
  		if(ModSamsContent.settings.moreFutureTrades)
  		{
	  		VillageTrading v = new VillageTrading();
	  		
			VillagerRegistry.instance().registerVillageTradeHandler(1, v);
			VillagerRegistry.instance().registerVillageTradeHandler(2, v);
  		}
  		*/ 
	}
	
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(ModLoader.configSettings.searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModLoader.configSettings.searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModLoader.configSettings.searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModLoader.configSettings.simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModLoader.configSettings.todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModLoader.configSettings.kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModLoader.configSettings.home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModLoader.configSettings.worldhome) 
			event.registerServerCommand(new CommandHome());
	}
  
	private void registerEventHandlers() 
	{
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");
		 
    	//they are just Objects, because i have not yet found a reason to add an interface/superclass 
     	Object[] handlers = new Object[]
     	{
     		//TODO: dont put every one in every bus, make three lists one day. WARNING that will cause bugs
     		 new HandlerBonemealUse()
      		,new HandlerAutoPlantExpire()//this is only one needs terrain gen buff, plus one of the regular ones
     		,new HandlerPotionTick()
     		,new HandlerPlayerSleep()
     		,new HandlerLivingDrops()
     		,new HandlerTorchCollide()
     		,new HandlerBucketFill() 
     		,new HandlerBucketStorage()
      		,new HandlerPlayerHarvest()
     		,new HandlerEnderBookClick()
      		,new HandlerEnderpearlTeleport()
     		,new HandlerEnderChestHit() 
     		,new HandlerKeyInput()
      		,new HandlerWand() 
     		,new HandlerLivingDeath()
     		,new HandlerWandBuilding()
      		,new HandlerRichAnimals()
      		,new HandlerScreenText()
     		,new HandlerSkullSignNames() 
       		,new HandlerPlayerTickApplesEaten()
        	,new HandlerPlayerFallTheEnd()
      		,new HandlerSwiftDeposit()
     		,instance
     	};
     	
     	for(Object o : handlers)
     	{ 
    		MinecraftForge.EVENT_BUS.register(o);
    		MinecraftForge.TERRAIN_GEN_BUS.register(o);
    		MinecraftForge.ORE_GEN_BUS.register(o); 
    		FMLCommonHandler.instance().bus().register(o); 
     	}
	}
}
