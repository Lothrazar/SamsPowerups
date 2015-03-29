package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
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
	public static int DURABILITY; 
	public int range = 8; //TODO: in config
	
	public ItemWandLightning()
	{  
		super();  
		this.setCreativeTab(ModSamsContent.tabSamsContent);
    	this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandLightning),
			ItemRegistry.baseWand, 
			Items.ghast_tear  );
	}
	 
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{       
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing 
		if(ModSamsContent.configSettings.wandLightning == false ) {return;}
		if(held.getItem() != ItemRegistry.wandLightning ) {return;}
		 
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{    
			if(event.entityPlayer.isSneaking() == false) //normal attack: the clicked block
			{	     
				BlockPos hit = event.pos;
				
				if(event.face != null) {hit = event.pos.offset(event.face);}
				
				event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, hit.getX(), hit.getY(), hit.getZ()));
			
				SamsUtilities.damageOrBreakHeld(event.entityPlayer);
			}
			else //radius all around the player
			{ 
				BlockPos center = event.entityPlayer.getPosition();
				ArrayList<BlockPos> hits = new ArrayList<BlockPos>();
				hits.add(center.east(range));
				hits.add(center.west(range));
				hits.add(center.north(range));
				hits.add(center.south(range));//TODO: do a circle or radius, or random spots?? different modes one day?
				
				for(BlockPos hit : hits)
				{ 
				    event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, hit.getX(), hit.getY(), hit.getZ()));
				}
				 
				SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
			} 
		} 
  	}
}
