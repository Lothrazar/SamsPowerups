package com.lothrazar.samspowerups.modules;

import java.util.ArrayList; 

import com.lothrazar.samspowerups.BaseModule;
import com.lothrazar.samspowerups.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent; 
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeChangeModule extends BaseModule
{ 
	private static ArrayList<ItemStack> stoneToolsFurnaces = new ArrayList<ItemStack>();
	
	public void init()
	{
		stoneToolsFurnaces.add(new ItemStack(Items.stone_sword));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_hoe));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_pickaxe));
		stoneToolsFurnaces.add(new ItemStack(Items.stone_shovel));
		stoneToolsFurnaces.add(new ItemStack(Blocks.furnace));
		
		//since we cant get logs by hand: player will break leaves to make damaged axe
		int STICKS_PER_SAPLING = 1;
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_oak));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_spruce));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_birch));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_jungle));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_acacia));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick)
			,new ItemStack(Blocks.sapling,STICKS_PER_SAPLING,Reference.sapling_darkoak));
		GameRegistry.addRecipe(new ItemStack(Items.wooden_axe,1,55)
		,"t "
		," t"
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.wooden_axe,1,55)
		," t"
		,"t "
		, 't', Items.stick);
		
		ArrayList recipes = (ArrayList)CraftingManager.getInstance().getRecipeList();
		IRecipe current;
		ItemStack currentOutput;
		
		for(int i = 0; i < stoneToolsFurnaces.size(); i++)
		{
			for (int r = 0; r < recipes.size(); r++)
			{
				current = (IRecipe)recipes.get(r);
				currentOutput = current.getRecipeOutput();
				if (currentOutput != null &&
				currentOutput.getItem() == stoneToolsFurnaces.get(i).getItem() &&
				currentOutput.getItemDamage() == stoneToolsFurnaces.get(i).getItemDamage() )
				{
				recipes.remove(r);
				r--;//to keep it in sync, since we are altering the collection that we are looping over
				}
			}
		}
		
		GameRegistry.addRecipe(new ItemStack(Blocks.furnace)
		,"sss"
		,"scs"
		,"sss"
		,'s', Blocks.cobblestone
		,'c', new ItemStack(Items.coal,1,0));
		GameRegistry.addRecipe(new ItemStack(Items.stone_pickaxe)
		,"sss"
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		," s "
		," s "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		,"s "
		,"s "
		,"t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_sword)
		," s"
		," s"
		," t"
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		," s "
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		,"s "
		,"t "
		,"t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_shovel)
		," s"
		," t"
		," t"
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe)
		," ss"
		," ts"
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_axe)
		,"ss "
		,"st "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe)
		," ss"
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
		GameRegistry.addRecipe(new ItemStack(Items.stone_hoe)
		,"ss "
		," t "
		," t "
		, 's', Blocks.stone
		, 't', Items.stick);
	}


	public void loadConfig() 
	{ 
	}
	
	@Override
	public boolean isEnabled() 
	{
		return true;
	}
}