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
		this.setMaxDamage(durability);//TODO: config 
	}
	
	public static void init()
	{
		if(ModLoader.configSettings.flintTool == false) {return;} 
		//TODO: redo texture higher res
		
		//its like shears for leaves in the speed it goes, but does not give leaf blocks
		
		Set harvests = new HashSet<Block>();
		harvests.add(Blocks.leaves);
		harvests.add(Blocks.leaves2); 
		harvests.add(Blocks.tallgrass); //ferns and grass
		ItemRegistry.flintTool = new ItemToolFlint(1,ToolMaterial.EMERALD, harvests);
 
		SamsRegistry.registerItem(ItemRegistry.flintTool, "flint_tool");

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.flintTool), 
				Items.flint,
				Items.stick,
				Items.string);
	}
}
