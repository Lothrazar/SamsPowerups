package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

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
	public static int durability = 99;//TODO: config 
	public ItemWandLightning()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(durability);
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
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		
		if(ModLoader.configSettings.wandLightning == false ) {return;}
		if(held.getItem() != ItemRegistry.wandLightning ) {return;}
		 
		if( event.action.RIGHT_CLICK_BLOCK == event.action )
		{    
			if(event.entityPlayer.isSneaking() == false)
			{ 
				EntityLightningBolt lb = new EntityLightningBolt(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ());
			//TODO: do a circle or radius, or random spots
			    event.world.spawnEntityInWorld(lb);

				SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
			} 
		} 
  	}
}
