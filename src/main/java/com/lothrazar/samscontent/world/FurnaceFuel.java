package com.lothrazar.samscontent.world;

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
 
public class FurnaceFuel    implements IFuelHandler
{    
	@Override
	public int getBurnTime(ItemStack fuel) 
	{  
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.deadbush)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.wheat_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.pumpkin_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.melon_seeds))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves)))
		{
			return FurnaceBurnTime.Sticks;
		}
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.leaves2)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.tallgrass)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.red_flower)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.yellow_flower)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.reeds)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.red_mushroom)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.brown_mushroom)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.red_mushroom_block)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Item.getItemFromBlock(Blocks.brown_mushroom_block)))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.arrow))
		{
			return FurnaceBurnTime.Sticks;
		} 
		if(fuel.getItem().equals(Items.bow))
		{
			return FurnaceBurnTime.Sticks;
		}  
		if(fuel.getItem().equals(Items.paper))
		{
			return FurnaceBurnTime.Sticks;
		}
		
		return 0;
	}	
	
	public class FurnaceBurnTime // inner class
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
}