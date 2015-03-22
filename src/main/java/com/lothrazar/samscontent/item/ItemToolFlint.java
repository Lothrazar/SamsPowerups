package com.lothrazar.samscontent.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	public static int durability = 100;
 
	protected ItemToolFlint(float attackDamage, ToolMaterial material,	Set effectiveBlocks) 
	{ 
		super(attackDamage, material,effectiveBlocks);
		this.setMaxStackSize(1);
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setMaxDamage(durability); 
	}
	
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.flintTool), 
				Items.flint,
				Items.stick,
				Items.string);
	}
}
