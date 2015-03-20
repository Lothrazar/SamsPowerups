package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

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
	public static int durability = 99;//TODO: config 
	public ItemWandWater()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(durability);
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
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		if(ModLoader.configSettings.wandWater == false ) {return;}
		if(held.getItem() != ItemRegistry.wandWater ) {return;}
		
		if(event.face == null){return;}//n o idea why this is null sometimes but it is
		 
		boolean success = false;
		BlockPos offset = event.pos.offset(event.face);
		
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{   
			if(event.entityPlayer.isSneaking())
			{ 
				if(event.world.getBlockState(offset).getBlock() == Blocks.water)
				{
					event.world.setBlockToAir(offset);
					
					success = true;
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
			SamsUtilities.spawnParticle(event.world, EnumParticleTypes.WATER_BUBBLE, event.pos);
			SamsUtilities.playSoundAt(event.entityPlayer, "liquid.water");
			
			SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
			 
		}
  	}
}