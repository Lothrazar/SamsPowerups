package com.lothrazar.samsarmor;

import java.util.ArrayList; 

import org.apache.logging.log4j.Logger;    
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = ModArmor.MODID, version = ModArmor.VERSION,	name = ModArmor.NAME, useMetadata = true )  
public class ModArmor
{ 
	public static final String MODID = "samsapples";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Sam's Apples";

	@Instance(value = MODID)
	public static ModArmor instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	//public static ConfigRegistry cfg;
	//public static SimpleNetworkWrapper network; 
	//public static AchievementRegistry achievements;  
	
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsArmor") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ArmorRegistry.emerald_chestplate;
		}
	};    
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		//cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	//network = NetworkRegistry.INSTANCE.newSimpleChannel( MODID );     	
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	//network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
		//PotionRegistry.registerPotionEffects();

		
		ItemRegistry.registerItems();
		
		ArmorRegistry.registerItems();
		
		//achievements = new AchievementRegistry();
		 
		this.registerEventHandlers(); 
		
		//BlockHardnessRegistry.registerChanges(); 
		 
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		//achievements.registerAll();
		
	//	CreativeInventoryRegistry.registerTabImprovements();
	
		//MobSpawningRegistry.registerSpawns();
   
		//RecipeRegistry.registerRecipes();
	
  		
   
		proxy.registerRenderers();
	}
	 
	private void registerEventHandlers() 
	{ 
    	ArrayList<Object> handlers = new ArrayList<Object>();
  
      //	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      //	handlers.add(new DebugScreenText()          );  //This one can stay  
     	handlers.add(instance                         ); 
     	//handlers.add(achievements);  
		
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
