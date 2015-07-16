package com.lothrazar.samsinvcrafting;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModInvCrafting.MODID, useMetadata=true)
public class ModInvCrafting
{
    public static final String MODID = "samsinvcrafting"; 
	@Instance(value = MODID)
	public static ModInvCrafting instance;
	@SidedProxy(clientSide="com.lothrazar.samsinvcrafting.ClientProxy", serverSide="com.lothrazar.samsinvcrafting.CommonProxy")
	public static CommonProxy proxy;   
	//public static Logger logger; 
	public SimpleNetworkWrapper network;
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    	//logger = event.getModState().get
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	//In minecraft 1.4.x, 1.5.x i had a non-forge mod that inserted a 3x3 crafting table sized area to your inventory
    	//try to remake this being forge-friendly.
    	//use what similar ideas to modInvPages, if i get that kinda working
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
    	if (event.gui != null && event.gui.getClass() == net.minecraft.client.gui.inventory.GuiInventory.class && event.gui instanceof GuiInventoryCrafting == false  )
    	{
    		 event.gui = new GuiInventoryCrafting(Minecraft.getMinecraft().thePlayer);
    	}  
    }
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event) 
    {
    	
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
    	if (event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		
    		PlayerPowerups power = PlayerPowerups.get(player);
    		if(power != null)
    		{
    			power.onJoinWorld();
    		}
    	}
    }
    
    @SubscribeEvent
    public void onEntityConstruct(EntityConstructing event)
    {
    	if ((event.entity instanceof EntityPlayer))
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
  
    		if (PlayerPowerups.get(player) == null)
    		{
    			PlayerPowerups.register(player);
    		}
    	}
    }
}
