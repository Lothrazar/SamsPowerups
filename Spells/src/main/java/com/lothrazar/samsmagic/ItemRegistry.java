package com.lothrazar.samsmagic;

import java.util.ArrayList; 
import com.lothrazar.samsmagic.item.ItemChestSack; 
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();
   
	//String name, 
	public static ItemChestSack itemChestSack; 
	public static int timePotionShort = 90; // 1:30
	public static int timePotionLong = 8 * 60;// 8:00

	public static Item soulstone;
	public static Item spell_water_dummy;
	public static Item spell_frostbolt_dummy;
	public static Item spell_waterwalk_dummy;
	public static Item spell_harvest_dummy;
	public static Item spell_lightning_dummy;
	public static Item spell_jump_dummy;
	public static Item spell_ghost_dummy;
	public static Item spell_enderinv_dummy;
	public static Item exp_cost_dummy;
	public static Item exp_cost_empty_dummy;
	public static Item spell_heart_dummy;
	public static Item spell_torch_dummy;
	public static Item spell_haste_dummy;
	public static void registerItems()
	{   
		ItemRegistry.itemChestSack = new ItemChestSack();   
		ItemRegistry.registerItem(ItemRegistry.itemChestSack, "chest_sack");
	
		soulstone = new Item();
		ItemRegistry.registerItem(soulstone, "soulstone");
		spell_heart_dummy = new Item();
		ItemRegistry.registerItem(spell_heart_dummy, "spell_heart_dummy");
		exp_cost_dummy = new Item();
		ItemRegistry.registerItem(exp_cost_dummy, "exp_cost_dummy");
		exp_cost_empty_dummy = new Item();
		ItemRegistry.registerItem(exp_cost_empty_dummy, "exp_cost_empty_dummy");
		spell_torch_dummy = new Item();
		ItemRegistry.registerItem(spell_torch_dummy, "spell_torch_dummy");
		spell_water_dummy = new Item();
		ItemRegistry.registerItem(spell_water_dummy, "spell_water_dummy"); 
		spell_jump_dummy = new Item();
		ItemRegistry.registerItem(spell_jump_dummy, "spell_jump_dummy");
		spell_frostbolt_dummy = new Item();
		ItemRegistry.registerItem(spell_frostbolt_dummy, "spell_frostbolt_dummy");
		spell_waterwalk_dummy = new Item();
		ItemRegistry.registerItem(spell_waterwalk_dummy, "spell_waterwalk_dummy");
		spell_harvest_dummy = new Item();
		ItemRegistry.registerItem(spell_harvest_dummy, "spell_harvest_dummy");
		spell_lightning_dummy = new Item();
		ItemRegistry.registerItem(spell_lightning_dummy, "spell_lightning_dummy");
		spell_ghost_dummy = new Item();
		ItemRegistry.registerItem(spell_ghost_dummy, "spell_ghost_dummy");
		spell_enderinv_dummy = new Item();
		ItemRegistry.registerItem(spell_enderinv_dummy, "spell_enderinv_dummy");
		spell_haste_dummy = new Item();
		ItemRegistry.registerItem(spell_haste_dummy, "spell_haste_dummy");
		
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
}