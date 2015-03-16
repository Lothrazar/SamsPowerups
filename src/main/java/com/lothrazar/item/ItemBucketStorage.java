package com.lothrazar.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemBucketStorage  extends Item
{
	public ItemBucketStorage()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(9); 
		this.setMaxStackSize(1);
	}

	
	//TODO: Place a 9x9 square of lava on click, IF we click on the top of a block only (CHeck face)
	
	public static void initLava()
	{
		if(!ModLoader.configSettings.lavaStorage) {return;}//TODO: config
		
		ItemRegistry.itemLava = new ItemBucketStorage();

		SamsRegistry.registerItem(ItemRegistry.itemLava, "bucket_storage_lava");

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.itemLava), 
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket,
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket,
				Items.lava_bucket, Items.lava_bucket, Items.lava_bucket);
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket,9), 
				itemLava);
				*/
	}
	
	public static void initWater()
	{
		if(!ModLoader.configSettings.waterStorage) {return;}//TODO: config
		
		ItemRegistry.itemWater = new ItemBucketStorage();

		SamsRegistry.registerItem(ItemRegistry.itemWater, "bucket_storage_water");

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.itemWater), 
				Items.water_bucket, Items.water_bucket, Items.water_bucket,
				Items.water_bucket, Items.water_bucket, Items.water_bucket,
				Items.water_bucket, Items.water_bucket, Items.water_bucket);
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket,9), 
				itemWater);
				*/
	}
	  
	public static void placeLiquid(EntityPlayer player, ItemStack held,BlockPos pos) 
	{ 
		player.swingItem();
		 
		if(player.worldObj.isAirBlock(pos))
		{
			if(held.getItem() == ItemRegistry.itemLava)
				player.worldObj.setBlockState(pos, Blocks.lava.getDefaultState());
			else if(held.getItem() == ItemRegistry.itemWater)
				player.worldObj.setBlockState(pos, Blocks.water.getDefaultState()); 
			 
			held.damageItem(1, player);
			
			if(held.getItemDamage() <= 0)
			{  
				player.playSound("random.break", 2F,2f);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket,9));
			 
			}
		} 
	} 
}
