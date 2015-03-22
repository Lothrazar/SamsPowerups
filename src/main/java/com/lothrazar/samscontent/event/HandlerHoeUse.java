package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerHoeUse 
{
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{ 
		if(event.world.isRemote){return;}
		if(event.world.isAirBlock(event.pos.up()) == false){return;}
		//this fires BEFORE the block turns into farmland (is cancellable)
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		//TODO: put the 1/16 odds in config file?
		//TODO: restrict to only diamond or gold hoe? mabye also in config
		if( clicked == Blocks.grass || clicked == Blocks.dirt )
			if(event.world.rand.nextInt(16) == 0)
			{					
				SamsUtilities.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetrootSeed);
			}
	}
}
