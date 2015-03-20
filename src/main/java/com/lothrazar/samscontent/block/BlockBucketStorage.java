package com.lothrazar.samscontent.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBucketStorage extends Block
{
	//to be used in tandem with ItemBucketStorage. the (block/item) will remember how many full buckets are inside of it.
	
	protected BlockBucketStorage() 
	{
		super(Material.iron);
	}
	
	public void addRecipe()
	{
		
	}
	
}
