package com.lothrazar.samscontent;

import com.lothrazar.samscontent.item.ItemEmeraldArmor;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArmorRegistry 
{
	//from tutorial : http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-armor/
	
	//identical to gold properties
	public static ArmorMaterial MATERIAL_EMERALD = EnumHelper.addArmorMaterial("emerald","emerald", 7, new int[]{2, 5, 3, 1} , 25);
	
	public static Item emerald_helmet;
	public static Item emerald__chestplate;
	public static Item emerald_leggings;
	public static Item emerald_boots;

	public static void registerItems()
	{
		if(ModMain.cfg.emerald_armor)
		{
				
			emerald_helmet = new ItemEmeraldArmor(0);//TODO: Reference.armor_type_helmet
			ItemRegistry.registerItem(emerald_helmet, "emerald_helmet");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_helmet)
				,"eee","e e","   "
				,'e',new ItemStack(Items.emerald));
			if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_helmet, new ItemStack(Items.emerald,5), 0);
			
			emerald__chestplate = new ItemEmeraldArmor( 1);
			ItemRegistry.registerItem(emerald__chestplate, "emerald_chestplate");
			GameRegistry.addShapedRecipe(new ItemStack(emerald__chestplate)
				,"e e","eee","eee"
				,'e',new ItemStack(Items.emerald));
			if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald__chestplate, new ItemStack(Items.emerald,8), 0);
			
			emerald_leggings = new ItemEmeraldArmor( 2);
			ItemRegistry.registerItem(emerald_leggings, "emerald_leggings");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_leggings)
				,"eee","e e","e e"
				,'e',new ItemStack(Items.emerald));
			if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_leggings, new ItemStack(Items.emerald,7), 0);
			
			emerald_boots = new ItemEmeraldArmor(  3);
			ItemRegistry.registerItem(emerald_boots, "emerald_boots");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_boots)
				,"e e","e e","   "
				,'e',new ItemStack(Items.emerald));
			if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_boots, new ItemStack(Items.emerald,4), 0);
		

		}
	}


}
