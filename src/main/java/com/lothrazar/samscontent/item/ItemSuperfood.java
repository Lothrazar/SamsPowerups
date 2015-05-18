package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSuperfood extends ItemFood
{
	public ItemSuperfood()
	{  
		super(32,32.0F,false);
	 
		this.setCreativeTab(ModMain.tabSamsContent);
	}
	public void addRecipe() 
	{
		addMeatRecipe(Items.cooked_beef);
		addMeatRecipe(Items.cooked_mutton);
		addMeatRecipe(Items.cooked_porkchop);
		addMeatRecipe(Items.cooked_rabbit);
		addMeatRecipe(Items.cooked_fish);
		addMeatRecipe(Items.cooked_chicken);
	}
	
	
	private void addMeatRecipe(Item meat)
	{
		if(ItemRegistry.beetrootItem == null){return;}

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.food_super)
		    ,new ItemStack(meat)
			,new ItemStack(Items.bread)
			,new ItemStack(Items.carrot)
			,new ItemStack(Items.baked_potato)
			,new ItemStack(ItemRegistry.beetrootItem));
	}
}
