package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
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

public class ItemWandFire  extends Item
{
	public ItemWandFire()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandFire),
			ItemRegistry.baseWand, 
			Items.flint_and_steel  );
	}
	
	public static int RADIUS;
	public static int DURABILITY;

	public static void castFire(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{ 
		BlockPos fr;
		for(int i = 2; i < RADIUS; i++)
		{
			//previously used 
			fr = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), i);
			
			setBlockIfAir(world,fr, Blocks.fire.getDefaultState());
			setBlockIfAir(world,fr.up(), Blocks.fire.getDefaultState()); 
			setBlockIfAir(world,fr.down(), Blocks.fire.getDefaultState()); 
			setBlockIfAir(world,fr.south(), Blocks.fire.getDefaultState()); 
			setBlockIfAir(world,fr.north(), Blocks.fire.getDefaultState()); 
			setBlockIfAir(world,fr.east(), Blocks.fire.getDefaultState()); 
			setBlockIfAir(world,fr.west(), Blocks.fire.getDefaultState()); 
		
		}
		 
		SamsUtilities.playSoundAt(entityPlayer, "fire.ignite");
	}
	
	public static void setBlockIfAir(World world, BlockPos pos, IBlockState state)
	{
		if(world.isAirBlock(pos))
			world.setBlockState(pos, state); 
	}

	public void castExtinguish(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{ 
		ArrayList<BlockPos> fires = SamsUtilities.findBlocks(entityPlayer, Blocks.fire, RADIUS);
		
		for(BlockPos p : fires)
		{ 
			world.extinguishFire(entityPlayer, p.down(), EnumFacing.UP);//from above
			SamsUtilities.spawnParticle(world, EnumParticleTypes.DRIP_WATER, p);
		}
		
		if(fires.size() > 0)
		{
			SamsUtilities.playSoundAt(entityPlayer, "liquid.water");
		} 
	}
	  
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandFire && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{  
			if(event.entityPlayer.isSneaking() == false)
			{ 
				ItemWandFire.castFire(event.world,event.entityPlayer,held); //?,event.face
			}
			else
			{
				castExtinguish(event.world,event.entityPlayer,held); 
			} 
		}
  	}
}
