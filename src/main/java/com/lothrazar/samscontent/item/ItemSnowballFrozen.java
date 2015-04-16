package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball; 
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

public class ItemSnowballFrozen  extends Item 
{ 
	public ItemSnowballFrozen()
	{  
		super();   
		this.setCreativeTab(ModSamsContent.tabSamsContent);
		this.setMaxStackSize(64);   
	}
 
	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.frozen_snowball,8),
				"sss",
				"sis",
				"sss",
				'i',Blocks.ice,
				's',Items.snowball );
	}
	 
	public static void cast(World world, EntityPlayer entityPlayer ) 
	{ 
		BlockPos up = entityPlayer.getPosition().offset(entityPlayer.getHorizontalFacing(), 1).up();
 
		EntitySnowballBolt snow = new EntitySnowballBolt(world,entityPlayer 	 );
		 
		 world.spawnEntityInWorld(snow);
	 
		SamsUtilities.playSoundAt(entityPlayer, Reference.sounds.bowtoss); 
		
		SamsUtilities.decrHeldStackSize(entityPlayer); 
	} 
}
