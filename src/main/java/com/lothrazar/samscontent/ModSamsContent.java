package com.lothrazar.samscontent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList; 

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;   

import com.lothrazar.samscontent.block.*;
import com.lothrazar.samscontent.cfg.ConfigFile;
import com.lothrazar.samscontent.command.*;
import com.lothrazar.samscontent.event.*;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.potion.EnderpearlTeleport;
import com.lothrazar.samscontent.potion.EntityPotionTick;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.proxy.ClientProxy;
import com.lothrazar.samscontent.proxy.CommonProxy;
import com.lothrazar.samscontent.stats.AchievementRegistry;
import com.lothrazar.samscontent.world.ChestGen;
import com.lothrazar.samscontent.world.FurnaceFuel;
import com.lothrazar.samscontent.world.MobSpawningRegistry;
import com.lothrazar.samscontent.world.WorldGenClay; 
import com.lothrazar.util.*;

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
import net.minecraft.item.ItemSeeds;
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
  
@Mod(modid = Reference.MODID, version = Reference.VERSION	, canBeDeactivated = false, name = Reference.NAME, useMetadata = true ,guiFactory = "com.lothrazar.samscontent.cfg.ConfigGuiFactory") 
public class ModSamsContent
{
	@Instance(value = Reference.MODID)
	public static ModSamsContent instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	
	public static Logger logger; 
	public static ConfigFile configSettings;
	public static SimpleNetworkWrapper network; 
	public static AchievementRegistry achievements; 
	
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
		
		initModInfo(event.getModMetadata());
		
		configSettings = new ConfigFile(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);

		
		PotionRegistry.registerPotionEffects();

		BlockRegistry.registerBlocks();
		
		ItemRegistry.registerItems();
		
		achievements = new AchievementRegistry();
		 
		this.registerEventHandlers(); 
		
		BlockHardnessRegistry.registerChanges(); 
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		//TODO: LexManos et all have not yet fixed IVillageTradeHandler
	//	VillageTrading v = new VillageTrading(); 
        //VillagerRegistry.instance().registerVillageTradeHandler(1, v);
      //  VillagerRegistry.instance().registerVillageTradeHandler(2, v);
        
		achievements.registerAll();
		
		CreativeInventoryTweaks.registerTabImprovements();
	
		MobSpawningRegistry.registerSpawns();
  
		ChestGen.regsiterLoot();
		  
		RecipeRegistry.registerRecipes();
		 
		StackSizeIncreaser.registerChanges(); 
 
  		if(ModSamsContent.configSettings.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
  		}
  		
  		if(ModSamsContent.configSettings.worldGenClayOceans)
		{ 
			GameRegistry.registerWorldGenerator(new WorldGenClay(), 0); //zero is Weight of generator
		}

		proxy.registerRenderers();
	}
	
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(ModSamsContent.configSettings.searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModSamsContent.configSettings.searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModSamsContent.configSettings.searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModSamsContent.configSettings.simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModSamsContent.configSettings.todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModSamsContent.configSettings.kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModSamsContent.configSettings.home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModSamsContent.configSettings.worldhome) 
			event.registerServerCommand(new CommandHome());
	}
  
	private void registerEventHandlers() 
	{
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");
		 
    	ArrayList<Object> handlers = new ArrayList<Object>();
 
     	 
     	handlers.add(new PlayerBonemealUse()         );
      	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
     	handlers.add(new EntityPotionTick()          );
     	handlers.add(new PlayerEat()         );
     	handlers.add(new PlayerSleep()         );
     	handlers.add(new ZombieDrops()         );
     	handlers.add(new EntityTorchCollide()        );
      	//handlers.add(new HandlerPlayerHarvest()       );
      	handlers.add(new EnderpearlTeleport()  );
     	handlers.add(new PlayerEnderChestHit()       );
     	handlers.add(new KeyboardInput()            );
     	handlers.add(new EntityLivingDeath()         ); 
      	handlers.add(new LivestockDrops()         );
      	handlers.add(new DebugScreenText()          );
     	handlers.add(new SkullSignNames()      );
        handlers.add(new PlayerFallTheEnd()    );
      	handlers.add(new ChestDeposit()        );
     	handlers.add(instance                         ); 
     	handlers.add(ItemRegistry.itemEnderBook       );
		handlers.add(ItemRegistry.itemEnderBook       );
		handlers.add(ItemRegistry.wandTransform       );
		handlers.add(ItemRegistry.itemChestSack       );
		handlers.add(ItemRegistry.wandBuilding        );
		handlers.add(ItemRegistry.wandChest           );
		handlers.add(ItemRegistry.wandCopy            );
		handlers.add(ItemRegistry.wandFire            );
		handlers.add(ItemRegistry.wandFireball        );
		handlers.add(ItemRegistry.wandSnowball        );
		handlers.add(ItemRegistry.wandHarvest         );
		handlers.add(ItemRegistry.wandLivestock       );
		handlers.add(ItemRegistry.wandProspect        );
		handlers.add(ItemRegistry.wandTransform       );
		handlers.add(ItemRegistry.wandWater           );
		handlers.add(ItemRegistry.wandLightning       );
		handlers.add(BlockRegistry.block_storelava    );
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   ); 
		handlers.add(new PlayerUseHoe() 			  );
		handlers.add(new FlintPumpkin()				  ); 

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h);
	    		MinecraftForge.EVENT_BUS.register(h);
	    		MinecraftForge.TERRAIN_GEN_BUS.register(h);
	    		MinecraftForge.ORE_GEN_BUS.register(h); 
	     	}
	}
}
