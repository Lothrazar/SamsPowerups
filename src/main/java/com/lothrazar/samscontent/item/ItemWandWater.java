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
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		//if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		if(ModSamsContent.configSettings.wandWater == false ) {return;}
		if(held.getItem() != ItemRegistry.wandWater ) {return;}
		
		if(event.face == null){return;}//n o idea why this is null sometimes but it is
		 
		boolean success = false;
		BlockPos offset = event.pos.offset(event.face);
		
		ArrayList<Block> waterBoth = new ArrayList<Block>();
		waterBoth.add(Blocks.flowing_water);
		waterBoth.add(Blocks.water);
		
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{   
			if(event.entityPlayer.isSneaking())
			{ 
				if(waterBoth.contains(event.world.getBlockState(offset).getBlock()))
				{
					event.world.setBlockToAir(offset);
					
					success = true;
					//also do neighbours, if they are water. here we include flowing now
					
					if(waterBoth.contains(event.world.getBlockState(offset.north()).getBlock()))
					{
						event.world.setBlockToAir(offset.north()); 
					} 
					if(waterBoth.contains(event.world.getBlockState(offset.south()).getBlock()))
					{
						event.world.setBlockToAir(offset.south()); 
					} 
					if(waterBoth.contains(event.world.getBlockState(offset.east()).getBlock()))
					{
						event.world.setBlockToAir(offset.east()); 
					} 
					if(waterBoth.contains(event.world.getBlockState(offset.west()).getBlock()))
					{
						event.world.setBlockToAir(offset.west()); 
					} 
				}  
			}
			else //not sneaking, regular item use
			{
				if(event.world.isAirBlock(offset) )
				{ 
					event.world.setBlockState(offset,  Blocks.water.getDefaultState()); 
					
					success = true;
				}
			} 
		}
		

		if(success)
		{ 
			if(event.world.isRemote)
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.WATER_BUBBLE, offset);
			else
				SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
			
			SamsUtilities.playSoundAt(event.entityPlayer, "liquid.water"); 
		}
  	}
}
