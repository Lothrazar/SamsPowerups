package com.lothrazar.samsfooddetails;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModFoodDetails.MODID, useMetadata=true)
public class ModFoodDetails
{
    public static final String MODID = "samsfooddetails";
	@Instance(value = ModFoodDetails.MODID)
	public static ModFoodDetails instance;
    
    @EventHandler
	public void onPreInit(FMLPostInitializationEvent event)
	{ 
    	FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }

    @SubscribeEvent
    public void onItemTooltipEvent(ItemTooltipEvent event)
    {
    	//https://www.reddit.com/r/minecraftsuggestions/comments/3brh7v/when_hovering_over_a_food_it_shows_how_many_food/
    	//right now shows regardless of flag event.showAdvancedItemTooltips,... could change with config
    	if(event.itemStack != null && event.itemStack.getItem() instanceof ItemFood)
    	{
    		ItemFood food = (ItemFood)event.itemStack.getItem();

    		int hunger = food.func_150905_g(event.itemStack);//getHealAmount==func_150905_g==healAmount
    		float satur = food.func_150906_h(event.itemStack);//getSaturationModifier==func_150906_h==saturationModifier
    		
    		event.toolTip.add(hunger+" ("+satur+")");
    	} 
    }
}
