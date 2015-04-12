package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
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
	public int range = 8; //TODO: in config??
	
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

	public static void cast(PlayerInteractEvent event) 
	{//http://www.asstr.org/files/Collections/Alt.Sex.Stories.Moderated/Year2015/63345
	//
		 // event.action.RIGHT_CLICK_BLOCK == event.action
		BlockPos up = event.entityPlayer.getPosition().offset(event.entityPlayer.getHorizontalFacing(), 1).up();
		 
		EntityLightningballBolt ball = new EntityLightningballBolt(event.world,event.entityPlayer 	 );
		 
		event.world.spawnEntityInWorld(ball);
		SamsUtilities.damageOrBreakHeld(event.entityPlayer);
			
		
		
		/*
		BlockPos hit = event.pos;
		
		if(event.face != null) {hit = event.pos.offset(event.face);}
		
		 
		event.world.spawnEntityInWorld(new EntityLightningBolt(event.world, hit.getX(), hit.getY(), hit.getZ()));
	
		SamsUtilities.damageOrBreakHeld(event.entityPlayer);
		
		*/
	}
	  
}
