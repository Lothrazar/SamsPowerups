package com.lothrazar.beetroot;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class BeetrootSeedItem extends ItemFood implements IPlantable
{

    private final Block theBlockPlant;
	public BeetrootSeedItem(int amount, float saturation, boolean isWolfFood,Block plant) 
	{
		super(amount, saturation, isWolfFood); 
		theBlockPlant = plant; 
	
	}
	 
	
	//thanks to http://jabelarminecraft.blogspot.ca/p/minecraft-forge-172-creating-custom.html
	
	@Override
	public boolean onItemUse(ItemStack parItemStack, EntityPlayer parPlayer, 
	          World parWorld, BlockPos pos, EnumFacing side, float hitX, 
	          float hitY, float hitZ)
	{ 
	    if (side != EnumFacing.UP) //up is side==1
	    {
	        return false;
	    }
	    // check if player has capability to edit
	    else if (parPlayer.canPlayerEdit(pos.up(), side, parItemStack))
	    { 
	        // check that the soil block can sustain the plant
	        // and that block above is air so there is room for plant to grow
	    	 
	        if (parWorld.getBlockState(pos).getBlock().canSustainPlant(parWorld, pos, side, this)
	        		&& parWorld.isAirBlock(pos.up()))
	        {
	         // place the plant block
	            parWorld.setBlockState(pos.up(), getPlant(parWorld,pos));
	            // decrement the stack of seed items
	            --parItemStack.stackSize;
	            return true;
	        }
	        else
	        {
	            return false;
	        }
	    }
	    else
	    {
	        return false;
	    }
	}
	 
	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) 
	{  
		return net.minecraftforge.common.EnumPlantType.Crop;
	}
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) 
	{ 
		return theBlockPlant.getDefaultState();
	} 
}
