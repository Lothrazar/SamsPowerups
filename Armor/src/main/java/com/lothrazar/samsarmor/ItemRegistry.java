package com.lothrazar.samsarmor;

import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();

	
	public final static int I = 0; 
	public final static int II = 1;
	public final static int III = 2;
	public final static int IV = 3;
	public final static int V = 4;
  
	//String name,
	public static ToolMaterial MATERIAL_EMERALD;
	public static int timePotionShort = 90; // 1:30
	public static int timePotionLong = 8 * 60;// 8:00
	

	public static void registerItems()
	{   
 
			MATERIAL_EMERALD = EnumHelper.addToolMaterial("emerald", 
					3, //harvestLevel 3 same as diamond
					1600, //maxUses more than diamond
					15.0F, //efficiency more than gold
					3.5F, //damage more than diamond
					5+25 );//enchantability more than gold 
			    
		 /* REFERENCE: TO COMPARE:   public static enum ToolMaterial
		    {
		 // int harvestLevel, int maxUses, float efficiency, float damage, int enchantability)
		        WOOD(0, 59, 2.0F, 0.0F, 15),
		        STONE(1, 131, 4.0F, 1.0F, 5),
		        IRON(2, 250, 6.0F, 2.0F, 14),
		        GOLD(0, 32, 12.0F, 0.0F, 22);
		        DIAMOND(3, 1561, 8.0F, 3.0F, 10),
		      */
			 
			//thanks for help: http://bedrockminer.jimdo.com/modding-tutorials/basic-modding-1-7/custom-tools-swords/
			 int xp = 1;
			
			ItemSword emerald_sword = new ItemSword(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_sword, "emerald_sword");
			emerald_sword.setCreativeTab(ModArmor.tabSamsContent);
			
			GameRegistry.addShapedRecipe(new ItemStack(emerald_sword)
				, " e "," e "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			

 // gggg these Constructors are protected./ this is the only reason i have subclasses, need subclasses
			
			ItemSamPickaxe emerald_pickaxe = new ItemSamPickaxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_pickaxe, "emerald_pickaxe");
			emerald_pickaxe.setCreativeTab(ModArmor.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_pickaxe)
				, "eee"," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));

			ItemAxe emerald_axe = new ItemSamAxe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_axe, "emerald_axe");
			emerald_axe.setCreativeTab(ModArmor.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, "ee ","es "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_axe)
				, " ee"," se"," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			
			ItemSpade emerald_spade = new ItemSpade(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_spade, "emerald_spade");
			emerald_spade.setCreativeTab(ModArmor.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_spade)
				, " e "," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			
			ItemHoe emerald_hoe = new ItemHoe(MATERIAL_EMERALD);
			ItemRegistry.registerItem(emerald_hoe, "emerald_hoe");
			emerald_hoe.setCreativeTab(ModArmor.tabSamsContent);
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, "ee "," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			GameRegistry.addShapedRecipe(new ItemStack(emerald_hoe)
				, " ee"," s "," s "
				,'e',new ItemStack(Blocks.emerald_block)
				,'s',new ItemStack(Items.stick));
			

			//if(ModMain.cfg.uncraftGeneral)
			//{
				GameRegistry.addSmelting(emerald_spade, new ItemStack(Blocks.emerald_block,1), xp);
				GameRegistry.addSmelting(emerald_sword, new ItemStack(Blocks.emerald_block,2), xp);
				GameRegistry.addSmelting(emerald_hoe, new ItemStack(Blocks.emerald_block,2), xp);
				GameRegistry.addSmelting(emerald_pickaxe, new ItemStack(Blocks.emerald_block,3), xp);
				GameRegistry.addSmelting(emerald_axe, new ItemStack(Blocks.emerald_block,3), xp);
			//}
 
	    
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
}
