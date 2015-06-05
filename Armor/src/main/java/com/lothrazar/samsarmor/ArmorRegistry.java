package com.lothrazar.samsarmor;
 
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ArmorRegistry 
{
	//from tutorial : http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-armor/
	
	//identical to gold properties for now

	public static int emerald_durability=9;
	public static int[] reductionAmounts =  new int[]{2, 5, 3, 1};
	public static int emerald_enchantability = 30;
	public static ArmorMaterial MATERIAL_EMERALD;
	
	public static Item emerald_helmet;
	public static Item emerald_chestplate;
	public static Item emerald_leggings;
	public static Item emerald_boots;

	public static final String toolClassShovel = "shovel";
	public static final String toolClassAxe = "axe";
	public static final int armor_type_helmet = 0;
	public static final int armor_type_chest = 1;
	public static final int armor_type_leg = 2;
	public static final int armor_type_boots = 3;
	
	public static void registerItems()
	{
		MATERIAL_EMERALD = EnumHelper.addArmorMaterial("emerald","emerald", emerald_durability, reductionAmounts , emerald_enchantability);
	 
			emerald_helmet = new ItemEmeraldArmor(armor_type_helmet);//TODO: Reference.armor_type_helmet
			ItemRegistry.registerItem(emerald_helmet, "emerald_helmet");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_helmet)
				,"eee","e e","   "
				,'e',new ItemStack(Blocks.emerald_block));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_helmet)
				,"   ","eee","e e"
				,'e',new ItemStack(Blocks.emerald_block));
			//if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_helmet, new ItemStack(Blocks.emerald_block,5), 0);
			
			emerald_chestplate = new ItemEmeraldArmor(armor_type_chest);
			ItemRegistry.registerItem(emerald_chestplate, "emerald_chestplate");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_chestplate)
				,"e e","eee","eee"
				,'e',new ItemStack(Blocks.emerald_block));
		//	if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_chestplate, new ItemStack(Blocks.emerald_block,8), 0);
			
			emerald_leggings = new ItemEmeraldArmor(armor_type_leg);
			ItemRegistry.registerItem(emerald_leggings, "emerald_leggings");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_leggings)
				,"eee","e e","e e"
				,'e',new ItemStack(Blocks.emerald_block));
			//if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_leggings, new ItemStack(Blocks.emerald_block,7), 0);
			
			emerald_boots = new ItemEmeraldArmor(armor_type_boots);
			ItemRegistry.registerItem(emerald_boots, "emerald_boots");
			GameRegistry.addShapedRecipe(new ItemStack(emerald_boots)
				,"e e","e e","   "
				,'e',new ItemStack(Blocks.emerald_block));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_boots)
				,"   ","e e","e e"
				,'e',new ItemStack(Blocks.emerald_block));
			//if(ModMain.cfg.uncraftGeneral)
				GameRegistry.addSmelting(emerald_boots, new ItemStack(Blocks.emerald_block,4), 0);
	 
	}


}
