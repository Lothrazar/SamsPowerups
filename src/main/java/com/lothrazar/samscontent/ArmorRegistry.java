package com.lothrazar.samscontent;

import com.lothrazar.samscontent.item.ItemEmeraldArmor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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
		emerald_helmet = new ItemEmeraldArmor(0);
		ItemRegistry.registerItem(emerald_helmet, "emerald_helmet");
		
		emerald__chestplate = new ItemEmeraldArmor( 1);
		ItemRegistry.registerItem(emerald__chestplate, "emerald_chestplate");
		
		emerald_leggings = new ItemEmeraldArmor( 2);
		ItemRegistry.registerItem(emerald_leggings, "emerald_leggings");
		
		emerald_boots = new ItemEmeraldArmor(  3);
		ItemRegistry.registerItem(emerald_boots, "emerald_boots");
		
		
	}


}
