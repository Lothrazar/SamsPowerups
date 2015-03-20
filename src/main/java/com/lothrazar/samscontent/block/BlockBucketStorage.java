package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.item.ItemRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockBucketStorage extends Block implements ITileEntityProvider //extends BlockContainer
{
	//http://www.minecraftforge.net/wiki/Basic_Tile_Entity
	//to be used in tandem with ItemBucketStorage. the (block/item) will remember how many full buckets are inside of it.
	
	protected BlockBucketStorage() 
	{
		super(Material.iron);
	}
	 
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{ 
		return new TileEntityBucketStorage(meta);
	} 
	
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return; }//server side only!

		if(event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			if(blockClicked instanceof BlockBucketStorage)// && event.entityPlayer.isSneaking()
			{   
				TileEntity container = event.world.getTileEntity(event.pos);
				
				if(container == null) 
					System.out.println("instance NULL");
				
				if(container instanceof TileEntityBucketStorage)
				{
					System.out.println("instance of is true");
					TileEntityBucketStorage storage = (TileEntityBucketStorage)container;
					

					storage.addBucket();
					
					int b = storage.getBuckets();
					
					//Testing confirms this works, since we do it on server side only 
					//AND since the block data does not affect renderign on the client -> we do not need custom Packets
					System.out.println("bbb==="+b);
					
				}
			} 
		}
  	}
}
