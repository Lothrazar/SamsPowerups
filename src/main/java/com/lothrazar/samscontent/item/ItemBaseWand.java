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
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.baseWand)
			,"beb"
			," b "
			," b "
			, 'e', Items.emerald
			, 'b', Items.blaze_rod  );
		
		if(ModSamsContent.configSettings.uncraftGeneral)
			GameRegistry.addSmelting(ItemRegistry.wandBuilding, new ItemStack(Items.emerald,1,0),0);
	} 
}
