package com.lothrazar.samscontent.block;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.item.ItemRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBucketStorage extends Block implements ITileEntityProvider //extends BlockContainer
{
	//http://www.minecraftforge.net/wiki/Basic_Tile_Entity
	//to be used in tandem with ItemBucketStorage. the (block/item) will remember how many full buckets are inside of it.
	private Item bucketItem;
	
	protected BlockBucketStorage(Item bucketIn) 
	{
		super(Material.iron);
		this.setHardness(5F);
		this.setResistance(5F);
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 1);
		bucketItem = bucketIn;
	}
	 
	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
	}
	
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{ 
		return new TileEntityBucketStorage(meta);
	} 
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		TileEntityBucketStorage container = (TileEntityBucketStorage)world.getTileEntity(pos);

		Block blockClicked = player.worldObj.getBlockState(pos).getBlock();
		BlockBucketStorage block = (BlockBucketStorage)blockClicked;
		
		if(block.bucketItem != null)
		for(int i = 0; i < container.getBuckets(); i++)//since they are not stackable
		{
			SamsUtilities.dropItemStackInWorld(world, pos, new ItemStack(block.bucketItem));
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return; }//server side only!
		if(event.entityPlayer.isSneaking()) {return;}//consistent
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  

		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		if((blockClicked instanceof BlockBucketStorage) == false) {return;} 
	
		BlockBucketStorage block = (BlockBucketStorage)blockClicked;
		//TODO: set block based on 
	//	if(block.bucketItem != null), if this is empty
		if(block.bucketItem != this.bucketItem){return;}//not optimal but it fixes things
		
		TileEntityBucketStorage container = (TileEntityBucketStorage)event.world.getTileEntity(event.pos);
 
		if(held == null && event.action.RIGHT_CLICK_BLOCK == event.action
				&& container.getBuckets() > 0) //empty hand 
		{ 
			removeBucket(event.entityPlayer, event.world, container, block.bucketItem);
			
			SamsUtilities.playSoundAt(event.entityPlayer, "tile.piston.out");
			SamsUtilities.spawnParticle(event.world,EnumParticleTypes.LAVA, event.pos.up()); 
		}
		
		if(event.action.LEFT_CLICK_BLOCK == event.action 
				&& held != null
				&& held.getItem() == block.bucketItem )
		{  
			addBucket(event.entityPlayer, event.world, container); 
			
			SamsUtilities.playSoundAt(event.entityPlayer, "tile.piston.in"); 
			SamsUtilities.spawnParticle(event.world,EnumParticleTypes.LAVA, event.pos.up()); 
		}
		
  	}

	private void removeBucket(EntityPlayer entityPlayer,World world,TileEntityBucketStorage storage, Item bucketItem) 
	{
		storage.removeBucket();
 
		SamsUtilities.dropItemStackInWorld(world, entityPlayer.getPosition(), new ItemStack(bucketItem)); 
	}

	public void addBucket(EntityPlayer entityPlayer,	World world, TileEntityBucketStorage storage) 
	{  
		storage.addBucket();
		
		int b = storage.getBuckets();
		
		//Testing confirms this works, since we do it on server side only 
		//AND since the block data does not affect renderign on the client -> we do not need custom Packets
		 
		entityPlayer.destroyCurrentEquippedItem();
	}

	public void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(this), 
				"iii", 
				"ibi", 
				"iii", 
				'b', this.bucketItem, 
				'i', Items.iron_ingot   );
		
	}
}
