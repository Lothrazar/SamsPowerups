package com.lothrazar.samscontent.event;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerBonemealUse
{
	private boolean isUsingBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		return (heldItem.equals(Items.dye)  && held.getItemDamage() == Reference.dye_bonemeal); 
	}
	 
  	@SubscribeEvent
	public void onPlayerLeftClick(PlayerInteractEvent event)
  	{    
  		if(event.world.isRemote){return;}//stop it from doing a secod ghost item drop
  		
  		if(ModLoader.configSettings.betterBonemeal == false) { return; }
  		
		if(event.action == event.action.LEFT_CLICK_BLOCK) {return;}
		 
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		
		if(isUsingBonemeal(held)  ) 
		{   
			Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
			 
			if(blockClicked == null || blockClicked == Blocks.air ){return;}
			
			boolean showParticles = false;
			boolean decrementInv = false;
			
			//event.entityPlayer.worldObj.getBlockState(event.pos)
			//new method: the Block itself tells what number to return, not the world.  
			//the world wraps up the state of the block that we can query, and the 
			//block class translates
	  
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{  
			  	event.entity.entityDropItem( new ItemStack(Blocks.yellow_flower ,1), 1); 

		 		decrementInv = true;
			  	showParticles = true;
		 	}
		 	else if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{   
				int blockClickedDamage = Blocks.red_flower.getMetaFromState(event.entityPlayer.worldObj.getBlockState(event.pos)); 

			  	event.entity.entityDropItem( new ItemStack(Blocks.red_flower ,1,blockClickedDamage), 1);//quantity = 1

		 		decrementInv = true;
			  	showParticles = true; 
		 	}
		 	else if ( blockClicked.equals(Blocks.waterlily))
		 	{ 
			  	event.entity.entityDropItem( new ItemStack(Blocks.waterlily ,1), 1);

		 		decrementInv = true;
			  	showParticles = true;
		 	} 
		 	else if ( blockClicked.equals(Blocks.reeds))
		 	{
		 		//reeds can only be three tall so we stop there
		 		Block blockAbove = event.entityPlayer.worldObj.getBlockState(event.pos.up(1)).getBlock();
		 		Block blockAbove2 = event.entityPlayer.worldObj.getBlockState(event.pos.up(2)).getBlock();
		 		
		 		int goUp = 0;
		 		
		 		if(event.entityPlayer.worldObj.isAirBlock(event.pos.up(1))) goUp = 1;
		 		else if(event.entityPlayer.worldObj.isAirBlock(event.pos.up(2))) goUp = 2;

				if(goUp > 0)
				{
			 		event.entityPlayer.worldObj.setBlockState(event.pos.up(goUp), Blocks.reeds.getDefaultState());

				  	showParticles = true;
			 		decrementInv = true;
				} 
		 	}
		 	
		 	if(decrementInv)
		 	{ 
		 		if(event.entityPlayer.capabilities.isCreativeMode == false)
		 			held.stackSize--;
		 		
		 		if(held.stackSize == 0) 
		 			event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null); 		 
		 	}
		 	if(showParticles)
		 	{
		 		event.entityPlayer.worldObj.spawnParticle(EnumParticleTypes.SPELL, event.pos.getX(), event.pos.getY(), event.pos.getZ(), 0, 0, 0, 4);	
		 	} 
		}
  	}
}
