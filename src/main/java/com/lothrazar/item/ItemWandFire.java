package com.lothrazar.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
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
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	public static void init()
	{
		if(!ModLoader.configSettings.wandFire) {return;} 
		
		ModLoader.changelog.log("ItemWandFire registered");
		ItemRegistry.wandFire = new ItemWandFire();

		SamsRegistry.registerItem(ItemRegistry.wandFire, "wand_fire");
 
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandFire),
			ItemRegistry.baseWand, 
			Items.flint_and_steel  );
	
		//if(ModLoader.configSettings.uncraftGeneral)
			//GameRegistry.addSmelting(ItemRegistry.wandBuilding, new ItemStack(Items.emerald,1,0),0);	
	}
	
	public static int range = 9;//TODO: range in config

	public static void castFire(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{ 
		for(int i = 2; i < range; i++)
		{

			BlockPos fr = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), i);
			
			
			if(world.isAirBlock(fr))
			{
				world.setBlockState(fr, Blocks.fire.getDefaultState());
			}
		}
		 
		SamsUtilities.playSoundAt(entityPlayer, "fire.ignite");
	}

	public int radius = 8;//TODO: radius in config 
	public void castExtinguish(World world, EntityPlayer entityPlayer,	ItemStack held) 
	{ 
		ArrayList<BlockPos> fires = SamsUtilities.findBlocks(entityPlayer, Blocks.fire, radius);
		
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
			if(event.entityPlayer.isSneaking())
			{ 
				ItemWandFire.castFire(event.world,event.entityPlayer,held); 
			}
			else
			{
				castExtinguish(event.world,event.entityPlayer,held); 
			} 
		}
  	}
}
