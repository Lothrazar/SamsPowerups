package com.lothrazar.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandFire  extends Item
{
	public ItemWandFire()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	public static void init()
	{
		//if(!ModLoader.settings.enderBook) {return;}//TODO: config
		ItemRegistry.wandFire = new ItemWandFire();

		SamsRegistry.registerItem(ItemRegistry.wandFire, "wand_fire");
 
 /*
		GameRegistry.addShapelessRecipe(new ItemStack(Items.lava_bucket,9), 
				itemLava);
				*/
	}

	public static void castFire(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{
		int range = 9;
		
		for(int i = 0; i < range; i++)
		{

			BlockPos fr = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), i);
			
			
			if(world.isAirBlock(fr))
				world.setBlockState(fr, Blocks.fire.getDefaultState());
			 
		}
		
	}

	public static void castExtinguish(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{
		int radius = 8;
		//TODO: radius in config?
		ArrayList<BlockPos> fires = SamsUtilities.findBlocks(entityPlayer, Blocks.fire, radius);
		
		for(BlockPos p : fires)
		{
			//System.out.println("extinguishFire "+p.getX()+"    "+p.getZ());
			//event.world.extinguishFire(event.entityPlayer, p, EnumFacing.DOWN);
			world.extinguishFire(entityPlayer, p.down(), EnumFacing.UP);//from above
		}
		
	}
	 
	 
}
