package com.lothrazar.samscontent.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandLivestock extends ItemTool
{
	public static int DURABILITY;
  
	public ItemWandLivestock( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	  
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandLivestock),
			ItemRegistry.baseWand, 
			Items.porkchop  );
	}

	public void entitySpawnEgg(EntityPlayer entityPlayer, Entity target) 
	{
		int entity_id = 0;
		 
		if(target instanceof EntityCow
			&& ((EntityCow) target).isChild() == false)
		{ 
			entity_id = Reference.entity_cow; 
		}
		if(target instanceof EntityPig
				&& ((EntityPig) target).isChild() == false)
		{ 
			entity_id = Reference.entity_pig; 
		}
		if(target instanceof EntitySheep
				&& ((EntitySheep) target).isChild() == false)
		{ 
			entity_id = Reference.entity_sheep; 
		} 
		if(target instanceof EntityChicken
				&& ((EntityChicken) target).isChild() == false)
		{ 
			entity_id = Reference.entity_chicken; 
		} 
		if(target instanceof EntityMooshroom
				&& ((EntityMooshroom) target).isChild() == false)
		{ 
			entity_id = Reference.entity_mooshroom; 
		} 
		if(target instanceof EntityBat)
		{  
			entity_id = Reference.entity_bat; 
		}
		
		if(entity_id > 0)
		{
			entityPlayer.swingItem();
			entityPlayer.worldObj.removeEntity(target); 
			
			if(entityPlayer.worldObj.isRemote) 
				SamsUtilities.spawnParticle(entityPlayer.worldObj, EnumParticleTypes.VILLAGER_HAPPY, target.getPosition());
			else
				entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.spawn_egg,1,entity_id),true);

			SamsUtilities.playSoundAt(entityPlayer, "random.fizz");
			SamsUtilities.damageOrBreakHeld(entityPlayer);
		} 
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		if(held == null || held.getItem() != ItemRegistry.wandLivestock ){ return;}
		 
		ItemRegistry.wandLivestock.entitySpawnEgg(event.entityPlayer, event.target); 
  	} 
}
