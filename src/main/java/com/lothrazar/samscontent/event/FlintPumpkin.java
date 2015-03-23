package com.lothrazar.samscontent.event;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.item.ItemRegistry;

public class FlintPumpkin 
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(ModLoader.configSettings.flintPumpkin == false){return;}
		if(event.world.isRemote){ return; }//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		
		if(held.getItem() == Items.flint_and_steel && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked == Blocks.pumpkin)
			{
				event.world.setBlockState(event.pos, Blocks.lit_pumpkin.getDefaultState());
			}
		}
  	}
}
