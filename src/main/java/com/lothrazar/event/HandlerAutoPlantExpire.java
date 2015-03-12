package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerAutoPlantExpire
{ 
	
	@SubscribeEvent
	public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event)
	{
		System.out.println("SaplingGrowTreeEvent ");
		System.out.println(event.hasResult());

		System.out.println("b    "+event.world.getBiomeGenForCoords(event.pos).biomeName
				+"   "+event.isCancelable());
		
		//event.setResult(Result.DENY);
		
		Block b = event.world.getBlockState(event.pos).getBlock();
		
		if(b == Blocks.sapling)
		{
			int meta = Blocks.sapling.getMetaFromState(event.world.getBlockState(event.pos));

			int biomeID = event.world.getBiomeGenForCoords(event.pos).biomeID;
			
			System.out.println("GROW "+ b.getUnlocalizedName()+"   META   "+meta + " " 	+"   "+event.isCancelable());
			
			
			
			
			
		}
	}
	
	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{ 
		System.out.println("onItemExpireEvent " );
		
		 if(ModLoader.configSettings.plantDespawningSaplings == false) {return;}
		 
		 ItemStack is = event.entityItem.getEntityItem();
		 if(is != null )
		 { 
			 Block blockhere = event.entity.worldObj.getBlockState(event.entityItem.getPosition()).getBlock(); 
			 Block blockdown = event.entity.worldObj.getBlockState(event.entityItem.getPosition().down()).getBlock();
			   
			if(blockhere == Blocks.air && 
				blockdown == Blocks.dirt || //includes podzol and such
				blockdown == Blocks.grass 
				)
			{
				//plant the sapling, replacing the air and on top of dirt/plantable
				
				if(Block.getBlockFromItem(is.getItem()) == Blocks.sapling)
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.sapling.getStateFromMeta(is.getItemDamage()));
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.red_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.red_mushroom.getDefaultState());
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.brown_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.brown_mushroom.getDefaultState());
				
					
						
			
			}
		 }
	} 
}
