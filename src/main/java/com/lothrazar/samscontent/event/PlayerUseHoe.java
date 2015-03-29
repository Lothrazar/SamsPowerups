package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerUseHoe 
{
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{ 
		if(ModSamsContent.configSettings.beetroot == false){return;}
		if(event.world.isRemote){return;}
		if(event.world.isAirBlock(event.pos.up()) == false){return;}
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.current.getItem() == Items.golden_hoe  //TODO:  maybe also in config
			&& event.world.rand.nextInt(16) == 0) //pocket edition vanilla is 1/16
		{					
			SamsUtilities.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetrootSeed);
		}
	}
}
