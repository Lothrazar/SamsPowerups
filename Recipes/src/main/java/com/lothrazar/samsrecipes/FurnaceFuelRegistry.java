package com.lothrazar.samsrecipes;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
 
public class FurnaceFuelRegistry implements IFuelHandler
{    
	private ArrayList<ItemStack> burnAsStick = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> burnAsSlabs = new ArrayList<ItemStack>();
	private ArrayList<ItemStack> burnAsTools = new ArrayList<ItemStack>();

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
		burnAsStick.add(new ItemStack(Blocks.deadbush));
		burnAsStick.add(new ItemStack(Items.wheat_seeds));
		burnAsStick.add(new ItemStack(Items.pumpkin_seeds));
		burnAsStick.add(new ItemStack(Items.melon_seeds));
		burnAsStick.add(new ItemStack(Blocks.leaves));
		burnAsStick.add(new ItemStack(Blocks.leaves2));
		burnAsStick.add(new ItemStack(Blocks.tallgrass));
		burnAsStick.add(new ItemStack(Blocks.red_flower));
		burnAsStick.add(new ItemStack(Blocks.yellow_flower));
		burnAsStick.add(new ItemStack(Blocks.reeds));
		burnAsStick.add(new ItemStack(Blocks.red_mushroom));
		burnAsStick.add(new ItemStack(Blocks.brown_mushroom));
		burnAsStick.add(new ItemStack(Blocks.brown_mushroom_block));
		burnAsStick.add(new ItemStack(Blocks.red_mushroom_block)); 
		burnAsStick.add(new ItemStack(Blocks.double_plant));
		burnAsStick.add(new ItemStack(Items.arrow));
		burnAsStick.add(new ItemStack(Items.paper)); 
		burnAsTools.add(new ItemStack(Items.bow));
		burnAsTools.add(new ItemStack(Items.boat));
		burnAsTools.add(new ItemStack(Items.bed));
		burnAsTools.add(new ItemStack(Blocks.ladder)); 
		burnAsSlabs.add(new ItemStack(Items.jungle_door));
		burnAsSlabs.add(new ItemStack(Items.acacia_door));
		burnAsSlabs.add(new ItemStack(Items.oak_door));
		burnAsSlabs.add(new ItemStack(Items.spruce_door));
		burnAsSlabs.add(new ItemStack(Items.birch_door));
		burnAsSlabs.add(new ItemStack(Items.dark_oak_door));
	}
	
	@Override
	public int getBurnTime(ItemStack fuel) 
	{    
		if(fuel == null){return 0;}//I have never seen this happen, but just to be safe
		//changed to loops instead of arraylist.contains, so that we can sue the isEqual function
		
		for(ItemStack i : burnAsStick)
			if(i.isItemEqual(fuel))   
		{
			return FurnaceBurnTime.Sticks;
		}
		for(ItemStack i : burnAsTools)
			if(i.isItemEqual(fuel)) 
		{
			return FurnaceBurnTime.WoodenTools;
		}
		for(ItemStack i : burnAsSlabs)
			if(i.isItemEqual(fuel)) 
		{
			return FurnaceBurnTime.WoodenSlabs;
		}
		 
		return 0;
	}	
}