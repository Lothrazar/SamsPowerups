package com.lothrazar.samscontent;

import java.util.ArrayList;  
import java.util.List;  
import org.apache.logging.log4j.Logger;    
import com.lothrazar.util.Reference;  
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class Recipes  
{  
	static int EXP = 0;
	
	public static void registerRecipes() 
	{ 
		Recipes.netherwartPurple();
		
		Recipes.bookNoLeather(); 
		  
		Recipes.mushroomBlocks(); 
		
		Recipes.bonemealWool();
		
		Recipes.simpleDispenser();
		
		Recipes.records();
		  
   		Recipes.uncrafting();
 
   		Recipes.smoothstoneRequired();
   		
   		Recipes.furnaceNeedsCoal();  
   		
   		Recipes.tieredArmor();
		  
   		Recipes.woolDyeSavings();
		  
   		Recipes.repeaterSimple();
		
   		Recipes.minecartsSimple();
	}
	
	public static void netherwartPurple()
	{
		if(!ModLoader.configSettings.netherwartPurpleDye)  {return;}
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye,2,Reference.dye_purple)
			,Items.nether_wart
			,new ItemStack(Items.dye,1,Reference.dye_bonemeal));
		
		ModLoader.changelog.log("Recipe: Purple Dye Netherwart");
	}

	public static void gravelToClay()
	{
		if(!ModLoader.configSettings.gravelToClay)  {return;}

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.clay, 4)
			 ,Items.water_bucket
			 ,Blocks.gravel
			 ,Blocks.gravel
			 ,Blocks.gravel
			 ,Blocks.gravel
			 ,Blocks.dirt
			 ,Blocks.dirt
			 ,Blocks.dirt
			 ,Blocks.dirt);
	}
	
	
	public static void mushroomBlocks()
	{
		if(!ModLoader.configSettings.craftableMushroomBlocks)  {return;}

		GameRegistry.addRecipe(new ItemStack(Blocks.red_mushroom_block),
				"mm",
				"mm",
				'm', Blocks.red_mushroom);
		GameRegistry.addRecipe(new ItemStack(Blocks.brown_mushroom_block),
				"mm",
				"mm",
				'm', Blocks.brown_mushroom);
		 
		
	//	http://minecraft.gamepedia.com/Mushroom_%28block%29#Block_state
		/*
		//item model data for these does not exist sadly
		int otherSide = 0;
		for (int side = 0; side < 16; side++)
		{
			otherSide = side + 1;
			if(otherSide == 16){otherSide = 0;}
			// since normally we have 2 wool making 3 carpet, we do the
			// reverse here
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.red_mushroom_block, 1, side),
					new ItemStack(Blocks.red_mushroom_block, 1,  otherSide));
			 
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.brown_mushroom_block, 1, side),
					new ItemStack(Blocks.brown_mushroom_block, 1,  otherSide));
		}
		*/
	}

	public static void uncrafting()
	{
		if(!ModLoader.configSettings.uncraftGeneral) {return;}
			
		GameRegistry.addRecipe(new ItemStack(Blocks.sandstone, 6), "xx", "xx",
				'x', Blocks.sandstone_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 6), "xx", "xx",
				'x', Blocks.stone_brick_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 6), "xx", "xx",
				'x', Blocks.stone_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.brick_block, 6), "xx",
				"xx", 'x', Blocks.brick_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.quartz_block, 6), "xx", "xx",
				'x', Blocks.quartz_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 6), "xx", "xx",
				'x', Blocks.nether_brick_stairs);

		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_oak), "xx", "xx",
				'x', Blocks.oak_stairs);
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_spruce), "xx",
				"xx", 'x', Blocks.spruce_stairs);
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_birch), "xx",
				"xx", 'x', Blocks.birch_stairs);
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_jungle), "xx",
				"xx", 'x', Blocks.jungle_stairs);
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_acacia), "xx",
				"xx", 'x', Blocks.acacia_stairs);
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 6, Reference.planks_darkoak), "xx",
				"xx", 'x', Blocks.dark_oak_stairs);
		
		
 		//turn slabs back into raw materials
		GameRegistry.addRecipe(new ItemStack(Blocks.stone, 3), "   ", "xxx","xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_stone));
		GameRegistry.addRecipe(new ItemStack(Blocks.sandstone, 3), "   ", "xxx","xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_sandstone));
		GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 3), "   ", "xxx","xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1, Reference.stone_slab_cobble));
		GameRegistry.addRecipe(new ItemStack(Blocks.brick_block, 3), "   ",	"xxx", "xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1,Reference.stone_slab_brickred));
		
		GameRegistry.addRecipe(new ItemStack(Blocks.stonebrick, 3), "   ", "xxx","xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1,Reference.stone_slab_stonebrick));
		GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 3), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1,	Reference.stone_slab_netehrbrick));
		GameRegistry.addRecipe(new ItemStack(Blocks.quartz_block, 3), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.stone_slab, 1,	Reference.stone_slab_quartz));
		
		//get blanks back from wooden slabs
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_oak), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_oak));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_spruce), "   ",	"xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_spruce));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_birch), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_birch));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_jungle), "   ",	"xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_jungle));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_acacia), "   ",	"xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_acacia));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 3, Reference.planks_darkoak), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.wooden_slab, 1, Reference.planks_darkoak));
  	 
		GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_oak),     "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_oak));
		GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_spruce),  "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_spruce));
		GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_birch),   "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_birch));
		GameRegistry.addRecipe(new ItemStack(Blocks.log, 1, Reference.log_jungle),  "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_jungle));
		GameRegistry.addRecipe(new ItemStack(Blocks.log2,1, Reference.log2_acacia), "x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1,Reference.planks_acacia));
		GameRegistry.addRecipe(new ItemStack(Blocks.log2,1, Reference.log2_darkoak),"x  ","x  ","xx ", 'x', new ItemStack(Blocks.planks, 1, Reference.planks_darkoak));
 		  
		
		//four planks makes 3 fences
		//so same in reverse
		
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_acacia)
			,new ItemStack(Blocks.acacia_fence )
			,new ItemStack(Blocks.acacia_fence )
			,new ItemStack(Blocks.acacia_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_acacia)
			,new ItemStack(Blocks.acacia_fence_gate )
			,new ItemStack(Blocks.acacia_fence_gate ) );

		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_jungle)
			,new ItemStack(Blocks.jungle_fence )
			,new ItemStack(Blocks.jungle_fence )
			,new ItemStack(Blocks.jungle_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_jungle)
			,new ItemStack(Blocks.jungle_fence_gate )
			,new ItemStack(Blocks.jungle_fence_gate ) );

		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_birch)
			,new ItemStack(Blocks.birch_fence )
			,new ItemStack(Blocks.birch_fence )
			,new ItemStack(Blocks.birch_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_birch)
			,new ItemStack(Blocks.birch_fence_gate )
			,new ItemStack(Blocks.birch_fence_gate ) );

		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_oak)
			,new ItemStack(Blocks.oak_fence )
			,new ItemStack(Blocks.oak_fence )
			,new ItemStack(Blocks.oak_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_oak)
			,new ItemStack(Blocks.oak_fence_gate )
			,new ItemStack(Blocks.oak_fence_gate ) );

		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_spruce)
			,new ItemStack(Blocks.spruce_fence )
			,new ItemStack(Blocks.spruce_fence )
			,new ItemStack(Blocks.spruce_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_spruce)
			,new ItemStack(Blocks.spruce_fence_gate )
			,new ItemStack(Blocks.spruce_fence_gate ) );

		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 4, Reference.planks_darkoak)
			,new ItemStack(Blocks.dark_oak_fence )
			,new ItemStack(Blocks.dark_oak_fence )
			,new ItemStack(Blocks.dark_oak_fence )); 
		GameRegistry.addShapelessRecipe( new ItemStack(Blocks.planks, 2, Reference.planks_darkoak)
			,new ItemStack(Blocks.dark_oak_fence_gate )
			,new ItemStack(Blocks.dark_oak_fence_gate ) );
	 
		//6 planks => 3 doors. therefore 1 door = 2 planks
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_acacia),
				new ItemStack(Blocks.acacia_door));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_birch),
				new ItemStack(Blocks.birch_door));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_spruce),
				new ItemStack(Blocks.spruce_door));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_darkoak),
				new ItemStack(Blocks.dark_oak_door));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_oak),
				new ItemStack(Blocks.oak_door));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.planks, 2,Reference.planks_jungle),
				new ItemStack(Blocks.jungle_door));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 3)
			, new ItemStack(Items.bowl)
			, new ItemStack(Items.bowl));
 		
		// every two sticks is one plank. so 8 sticks plus one in the middle
		// is five planks
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_oak),    
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_oak));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_spruce), 
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_spruce));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_birch),  
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_birch));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_jungle), 
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_jungle));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5, Reference.planks_acacia), 
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_acacia));
		GameRegistry.addRecipe(new ItemStack(Blocks.planks, 5,Reference.planks_darkoak),
				"xxx","xpx", "xxx", 
				'x', Items.stick, 'p', new ItemStack(Blocks.planks, 1,	Reference.planks_darkoak));
		
		// seven sticks make three ladders, therefore three ladders revert
		// back into
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 7),
				new ItemStack(Blocks.ladder), 
				new ItemStack(Blocks.ladder),
				new ItemStack(Blocks.ladder));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 13),
				new ItemStack(Items.sign), new ItemStack(Items.sign), new ItemStack(Items.sign));

		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Blocks.crafting_table));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 16),		new ItemStack(Blocks.chest));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.chest),		new ItemStack(Blocks.trapped_chest));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Items.painting));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 8),		new ItemStack(Items.item_frame));
		
		// break down bookshelves
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book, 3),		new ItemStack(Blocks.bookshelf));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.paper, 3),		new ItemStack(Items.book));
		
  	 
		// only if  they have no damage
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 3),			new ItemStack(Items.bow, 1, 0));
		 
		GameRegistry.addSmelting(new ItemStack(Items.flint_and_steel,1,0), new ItemStack(Items.iron_ingot, 1), 	EXP);
		
		GameRegistry.addSmelting(Items.bucket, new ItemStack(Items.iron_ingot, 3, 0),EXP); 
		GameRegistry.addSmelting(Items.minecart, new ItemStack(Items.iron_ingot, 5, 0),EXP);
		GameRegistry.addSmelting(Items.cauldron, new ItemStack(Items.iron_ingot, 7, 0),EXP); 
		GameRegistry.addSmelting(Items.compass, new ItemStack(Items.iron_ingot, 4, 0),EXP);
		GameRegistry.addSmelting(Items.clock, new ItemStack(Items.gold_ingot, 4, 0),EXP);
		
		GameRegistry.addSmelting(new ItemStack(Items.golden_pickaxe, 1, 0),
				new ItemStack(Items.gold_ingot, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_axe, 1, 0),
				new ItemStack(Items.gold_ingot, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_shovel, 1, 0),
				new ItemStack(Items.gold_ingot, 1, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_sword, 1, 0),
				new ItemStack(Items.gold_ingot, 2, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_hoe, 1, 0),
				new ItemStack(Items.gold_ingot, 2, 0), 0);
  	
		GameRegistry.addSmelting(new ItemStack(Items.iron_pickaxe, 1, 0),
				new ItemStack(Items.iron_ingot, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_axe, 1, 0),
				new ItemStack(Items.iron_ingot, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_shovel, 1, 0),
				new ItemStack(Items.iron_ingot, 1, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_sword, 1, 0),
				new ItemStack(Items.iron_ingot, 2, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_hoe, 1, 0),
				new ItemStack(Items.iron_ingot, 2, 0), 0);
  	
		GameRegistry.addSmelting(new ItemStack(Items.diamond_pickaxe, 1, 0),
				new ItemStack(Items.diamond, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_axe, 1, 0),
				new ItemStack(Items.diamond, 3, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_shovel, 1, 0),
				new ItemStack(Items.diamond, 1, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_sword, 1, 0),
				new ItemStack(Items.diamond, 2, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_hoe, 1, 0),
				new ItemStack(Items.diamond, 2, 0), 0);
		
  	
		GameRegistry.addSmelting(new ItemStack(Items.shears, 1, 0),
				new ItemStack(Items.iron_ingot, 2, 0), 0);
  	 
		GameRegistry.addSmelting(new ItemStack(Items.diamond_boots, 1, 0),
				new ItemStack(Items.diamond, 4, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_leggings, 1, 0),
				new ItemStack(Items.diamond, 7, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_chestplate, 1, 0),
				new ItemStack(Items.diamond, 8, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.diamond_helmet, 1, 0),
				new ItemStack(Items.diamond, 5, 0), 0);
  	
		GameRegistry.addSmelting(new ItemStack(Items.iron_boots, 1, 0),
				new ItemStack(Items.iron_ingot, 4, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_leggings, 1, 0),
				new ItemStack(Items.iron_ingot, 7, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_chestplate, 1, 0),
				new ItemStack(Items.iron_ingot, 8, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.iron_helmet, 1, 0),
				new ItemStack(Items.iron_ingot, 5, 0), 0);
  	
		GameRegistry.addSmelting(new ItemStack(Items.golden_boots, 1, 0),
				new ItemStack(Items.gold_ingot, 4, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_leggings, 1, 0),
				new ItemStack(Items.gold_ingot, 7, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_chestplate, 1, 0),
				new ItemStack(Items.gold_ingot, 8, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.golden_helmet, 1, 0),
				new ItemStack(Items.gold_ingot, 5, 0), 0);
  	
		GameRegistry.addSmelting(new ItemStack(Items.leather_boots, 1, 0),
				new ItemStack(Items.leather, 4, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.leather_leggings, 1, 0),
				new ItemStack(Items.leather, 7, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.leather_chestplate, 1, 0),
				new ItemStack(Items.leather, 8, 0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.leather_helmet, 1, 0),
				new ItemStack(Items.leather, 5, 0), 0);
 		
 		
 		// keeping it simple: buttons into sticks
 		// one blank = one button, and one plank = four sticks. so we should
 		// get 2 sticks per button
  	 
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.piston),
				new ItemStack(Blocks.sticky_piston));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),
				new ItemStack(Blocks.piston));
		
		GameRegistry.addSmelting(Items.iron_door, new ItemStack(Items.iron_ingot, 6),				EXP);
		
		GameRegistry.addSmelting(Blocks.daylight_detector, new ItemStack(Items.quartz, 3), EXP);

		// peel the redstone off the lamp
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone, 4),	new ItemStack(Blocks.redstone_lamp));

		GameRegistry.addSmelting(Blocks.noteblock, new ItemStack(Items.redstone), EXP);
		GameRegistry.addSmelting(Blocks.hopper, new ItemStack(Items.iron_ingot, 5), EXP);
		GameRegistry.addSmelting(Blocks.redstone_lamp, new ItemStack(Blocks.glowstone),	EXP);// the block

		// crafting makes you lose the ball and keep piston, so this is
		// reverse
		GameRegistry.addSmelting(Blocks.sticky_piston, new ItemStack(Items.slime_ball),	EXP);// the block
		
		GameRegistry.addSmelting(Items.repeater, new ItemStack(Items.redstone, 3), EXP);
		GameRegistry.addSmelting(Items.comparator, new ItemStack(Items.redstone, 3),EXP);
		
		GameRegistry.addSmelting(Blocks.redstone_torch,		new ItemStack(Items.redstone, 1), EXP);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot, 2),
				new ItemStack(Blocks.light_weighted_pressure_plate));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 2),
				new ItemStack(Blocks.heavy_weighted_pressure_plate));

		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone, 2),
				new ItemStack(Blocks.stone_pressure_plate));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 4),
				new ItemStack(Blocks.wooden_pressure_plate));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),		new ItemStack(Blocks.dispenser));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.redstone),		new ItemStack(Blocks.dropper));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot),
				new ItemStack(Blocks.tripwire_hook), new ItemStack(Blocks.tripwire_hook));

		// 6 planks = craft sticks three times = 12 sticks = 2 trapdoors
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 12),
				new ItemStack(Blocks.trapdoor), new ItemStack(Blocks.trapdoor));
		// 6 sticks = 2 fence , therefore 1 fence becomes 3 sticks
		// 8 sticks is one fence gate .

		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 4),
				new ItemStack(Blocks.wooden_button), 
				new ItemStack(Blocks.wooden_button));
  	
		// stone buttons back to stone as well
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.stone),
				new ItemStack(Blocks.stone_button), 
				new ItemStack(Blocks.stone_button));
  	
		GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz),		new ItemStack(Items.comparator));
		
		// SMELTING IS (input , output)

		GameRegistry.addSmelting(Blocks.trapped_chest,			new ItemStack(Blocks.tripwire_hook), EXP);
		
		GameRegistry.addSmelting(Blocks.stone_pressure_plate, new ItemStack(		Blocks.stone, 1), EXP);
		GameRegistry.addSmelting(Blocks.wooden_pressure_plate, new ItemStack(		Items.stick, 2), EXP);
  	 
		// all the rails!!
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gold_ingot, 6),
				new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail),
				new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail),
				new ItemStack(Blocks.golden_rail), new ItemStack(Blocks.golden_rail));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 6),
				new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail),
				new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail),
				new ItemStack(Blocks.detector_rail), new ItemStack(Blocks.detector_rail));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 6),
				new ItemStack(Blocks.activator_rail),
				new ItemStack(Blocks.activator_rail),
				new ItemStack(Blocks.activator_rail),
				new ItemStack(Blocks.activator_rail),
				new ItemStack(Blocks.activator_rail),
				new ItemStack(Blocks.activator_rail));
		                      
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 3),
				  new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail)
				, new ItemStack(Blocks.rail));

		GameRegistry.addSmelting(Blocks.dropper, new ItemStack(Blocks.cobblestone, 8),EXP);
		
		GameRegistry.addSmelting(Blocks.dispenser, new ItemStack(Blocks.cobblestone, 8),EXP);
		
		GameRegistry.addSmelting(Blocks.piston, new ItemStack(Items.iron_ingot), EXP);
 
   
 
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.pumpkin),			new ItemStack(Blocks.lit_pumpkin));
		 
		GameRegistry.addShapelessRecipe(new ItemStack(Items.melon, 9),			new ItemStack(Blocks.melon_block));

		GameRegistry.addSmelting(Items.golden_carrot, new ItemStack(Items.gold_nugget,			8), EXP);
		
		GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 0),
				new ItemStack(Items.gold_ingot, 8), EXP);
		GameRegistry.addSmelting(new ItemStack(Items.golden_apple, 1, 1),
				new ItemStack(Blocks.gold_block, 8), EXP);
   
		// get 4 string back from wool (white only, that's okay)
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 4),			new ItemStack(Blocks.wool));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 3),
			new ItemStack(Items.bed));
		 
		for (int color = 0; color < 16; color++)
		{
			// since normally we have 2 wool making 3 carpet, we do the
			// reverse here
			GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 2, color),
					new ItemStack(Blocks.carpet, 1, color)
				  , new ItemStack(Blocks.carpet, 1, color)
				  , new ItemStack(Blocks.carpet, 1, color));
		} 
  	 
		GameRegistry.addSmelting(Blocks.sandstone, new ItemStack(Blocks.sand, 4), EXP); 
		
		// all damage values into empty//1 for chiseled 2 for pillar
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block, 1, Reference.quartz_block),
				new ItemStack(Blocks.quartz_block, 1, Reference.quartz_chiselled));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.quartz_block, 1, Reference.quartz_block),
				new ItemStack(Blocks.quartz_block, 1, Reference.quartz_pillar));
  	
		GameRegistry.addShapelessRecipe(new ItemStack(Items.quartz, 4),
				new ItemStack(Blocks.quartz_block));
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.glowstone_dust, 4),
				new ItemStack(Blocks.glowstone));
		
		GameRegistry.addSmelting(Blocks.nether_brick, new ItemStack(Items.netherbrick, 4), EXP);
		
		GameRegistry.addSmelting(Blocks.stained_hardened_clay, new ItemStack(Blocks.hardened_clay), EXP);
		// smelt red brick into component parts
		GameRegistry.addSmelting(Blocks.brick_block, new ItemStack(Items.brick,		4), EXP);
			
 		
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.grass));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt), new ItemStack(Blocks.mycelium));
  	
		// smelt smoothstone to uncraft it into cobblestone
		GameRegistry.addSmelting(Blocks.stone, new ItemStack(Blocks.cobblestone), 0);
   
		GameRegistry.addShapelessRecipe(new ItemStack(Items.snowball, 4),		new ItemStack(Blocks.snow));
  	
		// three blocks give us six layers, so this is reverse
		GameRegistry.addRecipe(new ItemStack(Blocks.snow, 3), "   ", "xxx",	"xxx", 'x', Blocks.snow_layer);
		GameRegistry.addRecipe(new ItemStack(Blocks.snow, 3), "xxx", "xxx",	"   ", 'x', Blocks.snow_layer);
  	
		// craft clay block into four balls, avoid using shovel
		GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4),		new ItemStack(Blocks.clay));
  	 
		// remember, craft is output , input, OPPOSITE OF SMELT
		GameRegistry.addRecipe(new ItemStack(Blocks.stone, 4), "xx", "xx",	'x', Blocks.stonebrick);
   
  	 
		//three glass blocks makes three bottles, so this reverses it
		GameRegistry.addSmelting(Items.glass_bottle, new ItemStack(Blocks.glass), EXP);
		 
		// also, since 6 blocks is 16 panes, we cut that in half and get 8
		// panes into three blocks
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.glass, 3),
				new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
				new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
				new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane),
				new ItemStack(Blocks.glass_pane), new ItemStack(Blocks.glass_pane));
		 
		GameRegistry.addSmelting(Blocks.glass, new ItemStack(Blocks.sand), EXP);
   
		GameRegistry.addSmelting(Blocks.stained_glass, new ItemStack(Blocks.glass, 1, 0),EXP);
		
		GameRegistry.addSmelting(Blocks.stained_glass_pane, new ItemStack(Blocks.glass_pane, 1, 0), EXP);
		 
  	 

		GameRegistry.addSmelting(Blocks.brewing_stand, new ItemStack(Items.blaze_rod),			EXP);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 5),
				new ItemStack(Blocks.tnt));
		 
		// un damaged anvil gives all, damged gives you either one or two
 		 
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 31),
				new ItemStack(Blocks.anvil, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 18),
				new ItemStack(Blocks.anvil, 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.iron_ingot, 9),
				new ItemStack(Blocks.anvil, 1, 2));
		 
		GameRegistry.addRecipe(new ItemStack(Blocks.nether_brick, 6), "   ",
				"xxx", "xxx", 'x', Blocks.nether_brick_fence);
		 
		GameRegistry.addSmelting(new ItemStack(Blocks.flower_pot), new ItemStack(	 Items.brick, 3), EXP);
		  
		GameRegistry.addSmelting(Items.magma_cream, new ItemStack(Items.slime_ball), EXP);
  	  
		GameRegistry.addSmelting(Items.item_frame, new ItemStack(Items.leather), EXP);
		GameRegistry.addSmelting(Blocks.jukebox, new ItemStack(Items.diamond), EXP);
  	 
		// back into an eye
		GameRegistry.addSmelting(Items.ender_eye, new ItemStack(Items.ender_pearl), EXP);
		// only regular sandstone
		GameRegistry.addSmelting(Blocks.furnace, new ItemStack(Blocks.cobblestone, 8),EXP);
  	
		// un-enchant any book
		GameRegistry.addSmelting(Items.enchanted_book, new ItemStack(Items.book, 1, 0),				EXP);
		
		//2 diamonds from the table
		GameRegistry.addSmelting(Blocks.enchanting_table, new ItemStack(Items.diamond,2),				EXP);
  	
		// remove dye from hardened clay 
		GameRegistry.addSmelting(Items.book, new ItemStack(Items.leather, 1), EXP);
		 
		GameRegistry.addSmelting(Items.filled_map, new ItemStack(Items.compass), EXP);
		GameRegistry.addSmelting(Items.map, new ItemStack(Items.compass), EXP);
		
		 
		// same setup for cobble wall, turn them back into blocks
		GameRegistry.addRecipe(new ItemStack(Blocks.cobblestone, 6), "   ", "xxx","xxx", 
				'x', new ItemStack(Blocks.cobblestone_wall, 1, Reference.cobblestone_wall_plain));
		
		GameRegistry.addRecipe(new ItemStack(Blocks.mossy_cobblestone, 6), "   ","xxx", "xxx", 
				'x', new ItemStack(Blocks.cobblestone_wall, 1, Reference.cobblestone_wall_mossy));
		 
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.obsidian, 8),
				new ItemStack(Blocks.ender_chest));
		 
  	 
		// / lead to slime, since its one slime that gives 2 leads anyway
		GameRegistry.addShapelessRecipe(new ItemStack(Items.slime_ball, 2),
				new ItemStack(Items.lead), new ItemStack(Items.lead));
	}

	public static void records()
	{
		if(!ModLoader.configSettings.craftableTransmuteRecords) {return;}
		// iterate down the list, 8 emeralds each time
		
		GameRegistry.addRecipe(new ItemStack(Items.record_13), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.emerald)
				, 's', new ItemStack(Items.record_blocks));
		
		GameRegistry.addRecipe(new ItemStack(Items.record_blocks), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.emerald)
				, 's', new ItemStack(Items.record_chirp));
		
		GameRegistry.addRecipe(new ItemStack(Items.record_chirp), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.emerald)
				, 's', new ItemStack(Items.record_far));
		
		GameRegistry.addRecipe(new ItemStack(Items.record_far), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.emerald)
				, 's', new ItemStack(Items.record_mall));
		
		GameRegistry.addRecipe(new ItemStack(Items.record_mall), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_mellohi));
		GameRegistry.addRecipe(new ItemStack(Items.record_mellohi), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_cat)); 
		GameRegistry.addRecipe(new ItemStack(Items.record_cat), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_stal));
		GameRegistry.addRecipe(new ItemStack(Items.record_stal), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_strad));
		GameRegistry.addRecipe(new ItemStack(Items.record_strad), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_ward));
		GameRegistry.addRecipe(new ItemStack(Items.record_ward), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_11));
		GameRegistry.addRecipe(new ItemStack(Items.record_11), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_wait));
		GameRegistry.addRecipe(new ItemStack(Items.record_wait), "xxx", "xsx",
				"xxx", 'x', new ItemStack(Items.emerald), 's', new ItemStack(
						Items.record_13));
	}

	public static void bonemealWool()
	{
		if(!ModLoader.configSettings.craftableBonemealColouredWool){return;}
		//use bonemeal to bleach colored wool back to white
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 1), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal)); 
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 2), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 3), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 4), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 5), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 6), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 7), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 8), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 9), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 10), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 11), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 12), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 13), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 14), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0),
				new ItemStack(Blocks.wool, 1, 15), new ItemStack(Items.dye, 1,				Reference.dye_bonemeal));
	}
/*
	public static void mobHeads()
	{
		if(!ModLoader.settings.craftableMobHeads) 	{return;}
	 
		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_skeleton), "xxx", "xsx","xxx"
				, 'x', Items.bone
				, 's',new ItemStack(Items.skull,1,Reference.skull_wither) );
				 
		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_zombie), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.rotten_flesh)
				, 's', new ItemStack(Items.skull,1,Reference.skull_wither));
		 
		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_player), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Items.cake)
				, 's',new ItemStack(Items.skull,1,Reference.skull_wither));		
				 
		GameRegistry.addRecipe(new ItemStack(Items.skull,1,Reference.skull_creeper), "xxx", "xsx","xxx"
				, 'x', new ItemStack(Blocks.tnt)
				, 's', new ItemStack(Items.skull,1,Reference.skull_wither));		

		GameRegistry.addSmelting(Items.skull ,new ItemStack(Items.skull,1,Reference.skull_wither), 0);
	}
*/
	  
	public static void bookNoLeather()
	{ 
		if(!ModLoader.configSettings.craftBooksWithoutLeather)  {return;}
			
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book,1)
				,new ItemStack(Items.paper)
				,new ItemStack(Items.paper)
				,new ItemStack(Items.paper)  	);  
	}

	public static void repeaterSimple()
	{ 
		if(!ModLoader.configSettings.craftRepeaterSimple) {return;}
		
		GameRegistry.addRecipe(new ItemStack(Items.repeater), "r r", "srs","ttt"
				, 't', new ItemStack(Blocks.stone)
				, 's', new ItemStack(Items.stick)
				, 'r', new ItemStack(Items.redstone) );
	}

	public static void minecartsSimple()
	{
		if(!ModLoader.configSettings.craftMinecartsSimple){return;}

		//minecart stuffs: use five iron plus chest for it, instead of making the  cart first
		//etc for other minecarts too
		//normally you would need the minecart created in a different step. this is better


		GameRegistry.addRecipe(new ItemStack(Items.chest_minecart), 
				"   ","ici", "iii", 
				'i', Items.iron_ingot,
				'c', Blocks.chest);
		 
		GameRegistry.addRecipe(new ItemStack(Items.tnt_minecart), 
				"   ","ici", "iii", 
				'i', Items.iron_ingot,
				'c', Blocks.tnt);

		GameRegistry.addRecipe(new ItemStack(Items.hopper_minecart), 
				"   ","ici", "iii", 
				'i', Items.iron_ingot,
				'c', Blocks.hopper);

		GameRegistry.addRecipe(new ItemStack(Items.furnace_minecart), 
				"   ","ici", "iii", 
				'i', Items.iron_ingot,
				'c', Blocks.furnace);
	}

	public static void woolDyeSavings()
	{
		if(!ModLoader.configSettings.craftWoolDye8) {return;}
		 
		//so any color that is not white, add the new recipe with all 8 blocks
		for(int dye = 0; dye < 15; dye++)//only since we know that the dyes are these numbers
		{ 
			if(dye != Reference.dye_bonemeal)
			{
				//removeRecipe(new ItemStack(Blocks.wool,1,dye));				
			
				GameRegistry.addRecipe(new ItemStack(Blocks.wool,8,dye), 
						"www","wdw", "www", 
						'w', new ItemStack(Blocks.wool,1,Reference.dye_bonemeal),
						'd', new ItemStack(Items.dye,1, dye));
			}
		} 
	}
	
	public static void furnaceNeedsCoal()
	{ 
		if(!ModLoader.configSettings.furnaceNeedsCoal) {return;}
		
		removeRecipe(Blocks.furnace);

		GameRegistry.addRecipe(new ItemStack(Blocks.furnace), 
				"bbb",
				"bcb", 
				"bbb", 
				'b', Blocks.cobblestone,  
				'c', Items.coal );
	}

	public static void smoothstoneRequired()
	{ 
		if(!ModLoader.configSettings.smoothstoneToolsRequired) {return;}
		   
		removeRecipe(Items.stone_pickaxe);
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe), 
				"sss",
				" t ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );

		removeRecipe(Items.stone_sword);
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword), 
				" s ",
				" s ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );
 
		removeRecipe(Items.stone_axe);
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe), 
				"ss ",
				"st ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe), 
				" ss",//i dont think forge does the horizonal mirrored version so adding
				" ts", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );

		removeRecipe(Items.stone_hoe);
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe), 
				"ss ",
				" t ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe), 
				" ss",//i dont think forge does the horizonal mirrored version so adding
				" t ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );

		removeRecipe(Items.stone_shovel);
		
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel), 
				" s ",
				" t ", 
				" t ", 
				's', Blocks.stone,  
				't', Items.stick );
	} 
 
	public static void tieredArmor() 
	{
		if(!ModLoader.configSettings.tieredArmor) {return;}

		removeRecipe(Items.iron_chestplate);
		removeRecipe(Items.iron_boots);
		removeRecipe(Items.iron_leggings);
		removeRecipe(Items.iron_helmet);
		
		GameRegistry.addRecipe(new ItemStack(Items.iron_chestplate), 
				"ixi",
				"iii", 
				"iii", 
				'i', Items.iron_ingot,  
				'x', Items.leather_chestplate );

		GameRegistry.addRecipe(new ItemStack(Items.iron_boots), 
				"   ",
				"i i", 
				"ixi", 
				'i', Items.iron_ingot,  
				'x', Items.leather_boots );

		GameRegistry.addRecipe(new ItemStack(Items.iron_leggings), 
				"iii",
				"ixi", 
				"i i", 
				'i', Items.iron_ingot,  
				'x', Items.leather_leggings );

		GameRegistry.addRecipe(new ItemStack(Items.iron_helmet), 
				"iii",
				"ixi", 
				"   ", 
				'i', Items.iron_ingot,  
				'x', Items.leather_helmet);
		 
		removeRecipe(Items.diamond_chestplate);
		removeRecipe(Items.diamond_boots);
		removeRecipe(Items.diamond_leggings);
		removeRecipe(Items.diamond_helmet);
		
		GameRegistry.addRecipe(new ItemStack(Items.diamond_chestplate), 
				"ixi",
				"iii", 
				"iii", 
				'i', Items.diamond,  
				'x', Items.chainmail_chestplate );

		GameRegistry.addRecipe(new ItemStack(Items.diamond_boots), 
				"   ",
				"i i", 
				"ixi", 
				'i', Items.diamond,  
				'x', Items.chainmail_boots );

		GameRegistry.addRecipe(new ItemStack(Items.diamond_leggings), 
				"iii",
				"ixi", 
				"i i", 
				'i', Items.diamond,  
				'x', Items.chainmail_leggings );

		GameRegistry.addRecipe(new ItemStack(Items.diamond_helmet), 
				"iii",
				"ixi", 
				"   ", 
				'i', Items.diamond,  
				'x', Items.chainmail_helmet); 
	}
	
	public static void simpleDispenser() 
	{
		if(!ModLoader.configSettings.simpleDispenser) {return;}

		GameRegistry.addRecipe(new ItemStack(Blocks.dispenser), 
				"ccc",
				"csc", 
				"crc", 
				'c', Blocks.cobblestone,  
				's', Items.string,
				'r', Items.redstone ); 
		
		
	}
	
	private static void removeRecipe(Item resultItem)
	{     
		removeRecipe(new ItemStack(resultItem));
	}
	private static void removeRecipe(Block resultItem)
	{     
		removeRecipe(new ItemStack(resultItem));
	}
	private static void removeRecipe(ItemStack resultItem)
	{     
		//REFERENCES
		//http://www.minecraftforge.net/forum/index.php/topic,7146.0.html
		//http://stackoverflow.com/questions/27459815/minecraft-forge-1-7-10-removing-recipes-from-id
	
	    List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
	    IRecipe tmpRecipe;
	    ItemStack recipeResult;
	    for (int i = 0; i < recipes.size(); i++)
	    {
	        tmpRecipe = recipes.get(i);

	        recipeResult = tmpRecipe.getRecipeOutput();
	        if(recipeResult != null) 
	        {
	            recipeResult.stackSize = 1;
	            //recipeResult.setItemDamage(0);
	        }

	        if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
	        {
	            recipes.remove(i--);
	        }
	    }
	}
}
