package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandFireball  extends Item
{ 
	public static int DURABILITY;
	public ItemWandFireball()
	{  
		super();  
		this.setCreativeTab(ModSamsContent.tabSamsContent);
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandFire),
			ItemRegistry.baseWand, 
			Items.fire_charge );
	}
	

	public static void cast(World world, EntityPlayer entityPlayer ) 
	{ 
		BlockPos up = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), 1).up();
		
		
		int velX = 1;
		int velY = 0;
		int velZ = 1;//TOOD: fix these directions based on facing...
		 world.spawnEntityInWorld(new EntityLargeFireball(world,up.getX(),up.getY(),up.getZ()
				 ,velX,velY,velZ));
		 
		//SamsUtilities.playSoundAt(entityPlayer, "fire.ignite");
		SamsUtilities.damageOrBreakHeld(entityPlayer);
	}
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandFireball && 
				event.action.RIGHT_CLICK_AIR == event.action)
		{  
			//if(event.entityPlayer.isSneaking() == false)
			//{ 
				ItemWandFireball.cast(event.world,event.entityPlayer );  
		//	}
			/*
			else
			{
				castExtinguish(event.world,event.entityPlayer,held); 
			} */
		}
  	}
}
