package com.lothrazar.samscontent.block;

import java.util.Random;

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.samscontent.tileentity.TileEntityBucketStorage;
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
import net.minecraft.util.ChatComponentTranslation;
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
	private Item bucketItem;
	
	public BlockBucketStorage(Item bucketIn) 
	{
		super(Material.iron);
		this.setHardness(5F);
		this.setResistance(5F);
		
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 1);
		bucketItem = bucketIn;
	}

	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
	}
	
	@Override
	public boolean hasComparatorInputOverride() 
	{
		return true; //allows it to emit redstone power directly to comp. in following function
	}
	
	@Override
	public int getComparatorInputOverride(World world, BlockPos pos)
	{
		TileEntityBucketStorage container = (TileEntityBucketStorage)world.getTileEntity(pos);
		return container.getBuckets(); 
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(BlockRegistry.block_storeempty);
    }
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		TileEntityBucketStorage container = (TileEntityBucketStorage)world.getTileEntity(pos);

		Block blockClicked = player.worldObj.getBlockState(pos).getBlock();
		BlockBucketStorage block = (BlockBucketStorage)blockClicked;
		
		if(block.bucketItem != null)//yes thats not a mistake, the null check and <=
		for(int i = 0; i <= container.getBuckets(); i++)//since they are not stackable
		{
			SamsUtilities.dropItemStackInWorld(world, pos, new ItemStack(block.bucketItem));
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{       
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  

		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		 
		if(blockClicked == null || blockClicked == Blocks.air ){return;}
		if((blockClicked instanceof BlockBucketStorage) == false) {return;} 
	
		BlockBucketStorage block = (BlockBucketStorage)blockClicked;
		
		TileEntityBucketStorage container = (TileEntityBucketStorage)event.world.getTileEntity(event.pos);
 
		if(event.entityPlayer.isSneaking() && event.action.LEFT_CLICK_BLOCK == event.action
				&& this.bucketItem == null)
		{
			int inside;
			if(blockClicked == BlockRegistry.block_storeempty) 
				inside = 0;
			else
				inside = container.getBuckets() + 1;//yess its messed up?
			event.entityPlayer.addChatMessage(new ChatComponentTranslation(inside+""));
			
			return;//no sounds just tell us how much
		}
		
		if(event.entityPlayer.isSneaking()) {return;}//consistent
		 
		if(held == null && event.action.RIGHT_CLICK_BLOCK == event.action //RIGHT CLICK REMOVE from block
				&& block.bucketItem  != null
				&& block.bucketItem ==  this.bucketItem)  
		{ 
			if(container.getBuckets() > 0)
			{ 
				removeBucket(event.entityPlayer, event.world, container, block.bucketItem);
			}
			else //it is also empty
			{ 
				removeBucket(event.entityPlayer, event.world, container, block.bucketItem);
				event.world.setBlockState(event.pos, BlockRegistry.block_storeempty.getDefaultState());
			}
			event.world.updateComparatorOutputLevel(event.pos, blockClicked);
			SamsUtilities.playSoundAt(event.entityPlayer, "tile.piston.out");
			SamsUtilities.spawnParticle(event.world,EnumParticleTypes.LAVA, event.pos.offset(event.face)); 
		}
		
		if(event.action.LEFT_CLICK_BLOCK == event.action) //LEFT CLICK DEPOSIT INTO block		 
		{    
			//before we add the bucket, wait and should we set the block first?
			if(blockClicked == BlockRegistry.block_storeempty && block.bucketItem == null && held != null)	//then set this block based on bucket
			{ 
				IBlockState state = null;
			
				if(held.getItem() == Items.lava_bucket)
				{
					state = BlockRegistry.block_storelava.getDefaultState();  
				}
				else if(held.getItem()  == Items.water_bucket)
				{
					state = BlockRegistry.block_storewater.getDefaultState(); 
				} 
				if(held.getItem() == Items.milk_bucket)
				{ 
					state = BlockRegistry.block_storemilk.getDefaultState();  
				}
				
				if(state != null)
				{ 
					event.world.setBlockState(event.pos, state);
					addBucket(event.entityPlayer, event.world, container); 
					
					event.world.updateComparatorOutputLevel(event.pos, blockClicked);
					
					SamsUtilities.playSoundAt(event.entityPlayer, "tile.piston.in"); 
					SamsUtilities.spawnParticleSixAround(event.world,EnumParticleTypes.LAVA, event.pos.offset(event.face)); 
				}
			}
			else if(held != null &&  held.getItem() == block.bucketItem)
			{  
				addBucket(event.entityPlayer, event.world, container); 
				event.world.updateComparatorOutputLevel(event.pos, blockClicked);
				SamsUtilities.playSoundAt(event.entityPlayer, "tile.piston.in"); 
				SamsUtilities.spawnParticle(event.world,EnumParticleTypes.LAVA, event.pos.offset(event.face));  
			} 
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
		 
		entityPlayer.destroyCurrentEquippedItem();
	}

	public void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_storeempty), 
				"ioi", 
				"ogo", 
				"ioi", 
				'o', Blocks.obsidian, 
				'i', Items.iron_ingot,
				'g', Blocks.glass ); 
	}
}
