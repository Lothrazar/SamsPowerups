package com.lothrazar.samscontent.event;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsUtilities;

public class FlintPumpkin 
{
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{       
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		   
		if(ModSamsContent.configSettings.flintPumpkin && 
				held != null && held.getItem() == Items.flint_and_steel && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked == Blocks.pumpkin)
			{
				event.world.setBlockState(event.pos, Blocks.lit_pumpkin.getDefaultState());
				 
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
			
				SamsUtilities.playSoundAt(event.entityPlayer, "fire.ignite"); 
			}
			else if(blockClicked == Blocks.lit_pumpkin)//then un-light it
			{
				event.world.setBlockState(event.pos, Blocks.pumpkin.getDefaultState());
				 
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
				
				SamsUtilities.playSoundAt(event.entityPlayer, "random.fizz"); 
			}
		}
  	}
}
