package com.lothrazar.samsinvpages;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModInvPages.MODID, useMetadata=true)
public class ModInvPages
{
    public static final String MODID = "samsinvpages"; 
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	//Plan: have multiple pages in your inventory, the upper 3x9 area without the hotbar.
    	//so add GUI buttons to move left/right, and extend containerplayer and probably have some 
    	//IExtendedProperties as well to save/manage nbt data
		// some example code
        //System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }
}
