package com.lothrazar.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemToolFlint extends ItemTool
{

//	private Set blocks = {Bocks.leaves;};
 
	protected ItemToolFlint(float attackDamage, ToolMaterial material,	Set effectiveBlocks) 
	{ 
		super(attackDamage, material,effectiveBlocks);
		this.setMaxStackSize(1);
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setMaxDamage(100);//TODO: config 
	}
	public static void init()
	{
		//TODO: inconfig
		ItemRegistry.flintTool = new ItemToolFlint(1,ToolMaterial.WOOD,new HashSet<Block>());
 
		SamsRegistry.registerItem(ItemRegistry.flintTool, "flint_tool");

		GameRegistry.addShapelessRecipe(new ItemStack(Items.flint), 
				Items.stick);
	}
}
