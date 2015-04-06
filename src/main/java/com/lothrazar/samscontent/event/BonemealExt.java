package com.lothrazar.samscontent.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BonemealExt 
{
	public static void useBonemeal(World world, EntityPlayer entityPlayer, BlockPos pos, Block blockClicked)
	{
		boolean showParticles = false;
		boolean decrementInv = false;
		
		//event.entityPlayer.worldObj.getBlockState(event.pos)
		//new method: the Block itself tells what number to return, not the world.  
		//the world wraps up the state of the block that we can query, and the 
		//block class translates

  		if(world.isRemote){return;}//stop it from doing a second ghost item drop clientsideonly
  		
	 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
	 	{  
	 		entityPlayer.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 

	 		decrementInv = true;
		  	showParticles = true;
	 	}
	 	else if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
	 	{   
			int blockClickedDamage = Blocks.red_flower.getMetaFromState(world.getBlockState(pos)); 

			entityPlayer.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1

	 		decrementInv = true;
		  	showParticles = true; 
	 	}
	 	else if ( blockClicked.equals(Blocks.waterlily))
	 	{ 
	 		entityPlayer.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);

	 		decrementInv = true;
		  	showParticles = true;
	 	} 
	 	else if ( blockClicked.equals(Blocks.reeds))
	 	{
	 		//reeds can only be three tall so we stop there
	 		Block blockAbove = world.getBlockState(pos.up(1)).getBlock();
	 		Block blockAbove2 = world.getBlockState(pos.up(2)).getBlock();
	 		
	 		int goUp = 0;
	 		
	 		if(world.isAirBlock(pos.up(1))) goUp = 1;
	 		else if(world.isAirBlock(pos.up(2))) goUp = 2;

			if(goUp > 0)
			{
				world.setBlockState(pos.up(goUp), Blocks.reeds.getDefaultState());

			  	showParticles = true;
		 		decrementInv = true;
			} 
	 	}
	 	
	 	if(decrementInv)
	 	{ 
			ItemStack held = entityPlayer.getCurrentEquippedItem();
			
	 		if(entityPlayer.capabilities.isCreativeMode == false)
	 			held.stackSize--;
	 		
	 		if(held.stackSize == 0) 
	 			entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null); 		 
	 	}
	 	if(showParticles)
	 	{
	 		world.spawnParticle(EnumParticleTypes.SPELL, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0, 4);	
	 	} 
	}
}
