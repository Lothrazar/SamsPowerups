package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandWater  extends Item
{
	public static int DURABILITY; 
	public ItemWandWater()
	{  
		super();  
		this.setCreativeTab(ModSamsContent.tabSamsContent);
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandWater),
			ItemRegistry.baseWand, 
			Items.water_bucket  );
	}
	  
	public static void cast(PlayerInteractEvent event) 
	{
		BlockPos hit;
		
		if(event.face == null || event.world.getBlockState(event.pos).getBlock().isReplaceable(event.world, event.pos))
		{ 
			hit = event.pos; //if we clicked water, then it does not have a face
			//or  maybe its replaceable, such as if we clicked on a flower 
		}
		else 
		{ 
			hit = event.pos.offset(event.face);//get the neighbouring block, like if we hit the side of dirt
		}
		
		boolean success = false;
		
		ArrayList<Block> waterBoth = new ArrayList<Block>();
		waterBoth.add(Blocks.flowing_water);
		waterBoth.add(Blocks.water);
		
		Block hitBlock = event.world.getBlockState(hit).getBlock();
		
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{     
			if(event.world.isAirBlock(hit) || waterBoth.contains(hitBlock) || hitBlock.isReplaceable(event.world, hit))
			{  
				//if its water or air, we just set the block , but if its replaceable then break it first for drops
				
				if(hitBlock.isReplaceable(event.world, hit))
				{ 
					event.world.destroyBlock(hit, true);
				}
				
				event.world.setBlockState(hit, Blocks.water.getDefaultState()); 
				 
				success = true; 
			}
		}
		 
		if(success)
		{ 
			if(event.world.isRemote)
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.WATER_BUBBLE, hit);
			else
				SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
			
			SamsUtilities.playSoundAt(event.entityPlayer, "liquid.water"); 
		}
	}
}
