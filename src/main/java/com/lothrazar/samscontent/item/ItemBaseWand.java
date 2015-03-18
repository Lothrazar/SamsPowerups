package com.lothrazar.samscontent.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

public class ItemBaseWand extends Item
{
	public static void Init()
	{   
		ItemRegistry.baseWand = new ItemBaseWand(); 
		SamsRegistry.registerItem(ItemRegistry.baseWand, "base_wand" );   
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.baseWand)
			,"beb"
			," b "
			," b "
			, 'e', Items.emerald
			, 'b', Items.blaze_rod  );
		
		if(ModLoader.configSettings.uncraftGeneral)
			GameRegistry.addSmelting(ItemRegistry.wandBuilding, new ItemStack(Items.emerald,1,0),0);	  
	}

}
