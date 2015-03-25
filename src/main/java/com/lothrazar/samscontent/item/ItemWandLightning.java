package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.effect.EntityLightningBolt;
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

public class ItemWandLightning  extends Item
{
	public static int DURABILITY = 99; 
	public ItemWandLightning()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandLightning),
			ItemRegistry.baseWand, 
			Items.ghast_tear  );
	}
	 
	public int range = 8; //TODO: in config
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{       
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		if(ModLoader.configSettings.wandLightning == false ) {return;}
		if(held.getItem() != ItemRegistry.wandLightning ) {return;}
		 
		ArrayList<BlockPos> hits = new ArrayList<BlockPos>();
		hits.add(event.pos.east(range));
		hits.add(event.pos.west(range));
		hits.add(event.pos.north(range));
		hits.add(event.pos.south(range));//TODO: do a circle or radius, or random spots?? different modes one day?
		
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{    
			if(event.entityPlayer.isSneaking() == false)
			{ 
				for(BlockPos hit : hits)
				{ 
				    event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, hit.getX(), hit.getY(), hit.getZ()));
				}
				 
				if(event.world.isRemote == false)//only damage the item on the server.
				{ 
					//unlike other events, we spawn the bolt in both client and server side.
					//if the spawnEntity was only server side, it would be invisible
					SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
				}
			} 
		} 
  	}
}
