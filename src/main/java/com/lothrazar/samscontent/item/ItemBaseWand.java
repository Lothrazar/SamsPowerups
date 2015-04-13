package com.lothrazar.samscontent.item;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

public class ItemBaseWand extends Item
{ 
	public ItemBaseWand()
	{
		super();
		this.setCreativeTab(ModSamsContent.tabSamsContent);
		this.setMaxStackSize(1); 
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.baseWand)
			,"b  "
			," b "
			,"  b" 
			,'b', Items.blaze_rod  );
		
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.baseWand)
			,"  b"
			," b "
			,"b  " 
			,'b', Items.blaze_rod  );
		

		if(ModSamsContent.configSettings.uncraftGeneral)
			GameRegistry.addSmelting(ItemRegistry.baseWand, new ItemStack(Items.blaze_rod,3), 0);
	} 
}
