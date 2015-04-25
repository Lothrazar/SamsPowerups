package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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

public class ItemLightning  extends Item
{ 
	public ItemLightning()
	{  
		super();    
    	this.setMaxStackSize(64); 
		this.setCreativeTab(ModMain.tabSamsContent);  
	}
 //Lightning Spear?
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.lightning_charge,4),
			Items.ghast_tear,   
			Items.ender_eye ); 
	}

	public static void cast(PlayerInteractEvent event) 
	{
		if(  event.action.RIGHT_CLICK_AIR == event.action)
		{
			BlockPos up = event.entityPlayer.getPosition().offset(event.entityPlayer.getHorizontalFacing(), 1).up();
			 
			EntityLightningballBolt ball = new EntityLightningballBolt(event.world,event.entityPlayer 	 );
			 
			event.world.spawnEntityInWorld(ball);
			SamsUtilities.damageOrBreakHeld(event.entityPlayer); 
		}
		
		else if( event.action.RIGHT_CLICK_BLOCK == event.action)
		{
			BlockPos hit = event.pos;
			
			if(event.face != null) {hit = event.pos.offset(event.face);}
			
			 
			event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, hit.getX(), hit.getY(), hit.getZ()));
		
			//SamsUtilities.damageOrBreakHeld(event.entityPlayer);
			SamsUtilities.decrHeldStackSize(event.entityPlayer);
		} 
	} 
}
