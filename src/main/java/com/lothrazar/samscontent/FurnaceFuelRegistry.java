package com.lothrazar.samscontent;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger; 

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge; 
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;  
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class FurnaceFuelRegistry implements IFuelHandler
{    
	private ArrayList<Item> burnAsStick = new ArrayList<Item>();
	private ArrayList<Item> burnAsSlabs = new ArrayList<Item>();
	private ArrayList<Item> burnAsTools = new ArrayList<Item>();

	public class FurnaceBurnTime  
	{
		public static final int Sticks = 100;
		public static final int WoodenSlabs = 150;
		public static final int WoodenTools = 200;
		public static final int WoodStuff = 300;
		public static final int Coal = 1600; 
		public static final int LavaBucket = 20000;
		public static final int Sapling = 100;
		public static final int BlazeRod = 2400; 
	}
	
	public FurnaceFuelRegistry()
	{
		burnAsStick.add(Item.getItemFromBlock(Blocks.deadbush));
		burnAsStick.add(Items.wheat_seeds);
		burnAsStick.add(Items.pumpkin_seeds);
		burnAsStick.add(Items.melon_seeds);
		burnAsStick.add(Item.getItemFromBlock(Blocks.leaves));
		burnAsStick.add(Item.getItemFromBlock(Blocks.leaves2));
		burnAsStick.add(Item.getItemFromBlock(Blocks.tallgrass));
		burnAsStick.add(Item.getItemFromBlock(Blocks.red_flower));
		burnAsStick.add(Item.getItemFromBlock(Blocks.yellow_flower));
		burnAsStick.add(Item.getItemFromBlock(Blocks.reeds));
		burnAsStick.add(Item.getItemFromBlock(Blocks.red_mushroom));
		burnAsStick.add(Item.getItemFromBlock(Blocks.brown_mushroom));
		burnAsStick.add(Item.getItemFromBlock(Blocks.brown_mushroom_block));
		burnAsStick.add(Item.getItemFromBlock(Blocks.red_mushroom_block));
		burnAsStick.add(Items.arrow);
		burnAsStick.add(Items.paper);
		
		burnAsTools.add(Items.bow);
		burnAsTools.add(Items.bed);
		
		burnAsSlabs.add(Items.jungle_door);
		burnAsSlabs.add(Items.acacia_door);
		burnAsSlabs.add(Items.oak_door);
		burnAsSlabs.add(Items.spruce_door);
		burnAsSlabs.add(Items.birch_door);
		burnAsSlabs.add(Items.dark_oak_door);
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) 
	{  
		if(fuel == null){return 0;}//I have never seen this happen, but just to be safe
		
		if(burnAsStick.contains(fuel.getItem()))
		{
			return FurnaceBurnTime.Sticks;
		}
		else if(burnAsTools.contains(fuel.getItem()))
		{
			return FurnaceBurnTime.WoodenTools;
		}
		else if(burnAsSlabs.contains(fuel.getItem()))
		{
			return FurnaceBurnTime.WoodenSlabs;
		}
		 
		return 0;
	}	
}