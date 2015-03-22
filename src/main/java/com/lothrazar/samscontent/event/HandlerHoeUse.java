package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerHoeUse 
{
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{ 
		if(event.world.isRemote){return;}
		if(event.world.isAirBlock(event.pos.up()) == false){return;}
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.current.getItem() == Items.golden_hoe  //TODO:  maybe also in config
			&& event.world.rand.nextInt(32) == 0)  //TODO: put the 1/x odds in config file?
		{					
			SamsUtilities.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetrootSeed);
		}
	}
}
