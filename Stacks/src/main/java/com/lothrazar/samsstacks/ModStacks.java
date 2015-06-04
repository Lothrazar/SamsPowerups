package com.lothrazar.samsstacks;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModStacks.MODID, version = ModStacks.VERSION,name = ModStacks.NAME, useMetadata = true )
public class ModStacks
{
	public static final String MODID = "samsstacks";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Builder's Stackables";

    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		ArrayList<Item> to64 = new ArrayList<Item>();
 
		to64.add(Items.ender_pearl);
		to64.add(Items.egg);
		to64.add(Items.sign);
		to64.add(Items.snowball);
		to64.add(Items.cookie);
		to64.add(Items.mushroom_stew);
		to64.add(Items.boat);
		to64.add(Items.minecart);
		to64.add(Items.iron_door);
		to64.add(Items.acacia_door);
		to64.add(Items.jungle_door);
		to64.add(Items.spruce_door);
		to64.add(Items.oak_door);
		to64.add(Items.dark_oak_door);
		to64.add(Items.birch_door);
		to64.add(Items.cake);
		to64.add(Items.saddle);
		to64.add(Items.bucket);
		to64.add(Items.bed);
		to64.add(Items.chest_minecart);
		to64.add(Items.furnace_minecart);
		to64.add(Items.tnt_minecart);
		to64.add(Items.hopper_minecart);
		to64.add(Items.iron_horse_armor);
		to64.add(Items.golden_horse_armor);
		to64.add(Items.diamond_horse_armor); 
		to64.add(Items.record_13);
		to64.add(Items.record_blocks);
		to64.add(Items.record_chirp);
		to64.add(Items.record_far);
		to64.add(Items.record_mall);
		to64.add(Items.record_mellohi);
		to64.add(Items.record_cat);
		to64.add(Items.record_stal);
		to64.add(Items.record_strad);
		to64.add(Items.record_ward);
		to64.add(Items.record_11);
		to64.add(Items.record_wait);
		 
		for(Item item : to64)
		{
			item.setMaxStackSize(64);
		}
    }
}
