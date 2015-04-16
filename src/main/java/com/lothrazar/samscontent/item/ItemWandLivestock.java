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
import net.minecraft.entity.EntityAgeable; 
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandLivestock extends Item
{
	public ItemWandLivestock( )
	{   
		super(); 
		this.setCreativeTab(ModSamsContent.tabSamsContent);
		this.setMaxStackSize(64);   
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

	public static void entitySpawnEgg(EntityPlayer entityPlayer, Entity target) 
	{
		int entity_id = 0;
		
		//DO NOT USE = target.getEntityId();. that is the runtime id, so each instance of EntityCow has its own RUNTIME id
		//different than the class level id which we need here
 
		if( target instanceof EntityHorse ||
			target instanceof EntityWolf)
		{
			return;//disabled flat out.  TODO: maybe turn on from config
		}
 
		if(target instanceof EntitySquid) 
		{  
			entity_id = Reference.entity_squid;
		} 
		else if(target instanceof EntityBat) 
		{  
			entity_id = Reference.entity_bat;
		} 
		else if(target instanceof EntityAgeable )  
		{ 
			EntityAgeable targ = (EntityAgeable) target;
			//these guys all extend EntityAnimal extends EntityAgeable implements IAnimals
			if(targ.isChild() == false)
			{
				if(target instanceof EntityCow )
				{ 
					entity_id = Reference.entity_cow; 
				}
				if(target instanceof EntityPig )
				{ 
					entity_id = Reference.entity_pig; 
				}
				if(target instanceof EntitySheep )
				{ 
					entity_id = Reference.entity_sheep; 
				} 
				if(target instanceof EntityChicken )
				{ 
					entity_id = Reference.entity_chicken; 
				} 
				if(target instanceof EntityMooshroom )
				{ 
					entity_id = Reference.entity_mooshroom; 
				}
				if(target instanceof EntityRabbit )
				{ 
					entity_id = Reference.entity_rabbit; 
				} 
			} 
		}
		
		if(entity_id > 0)
		{
			//TODO: itemstack.setDisplayName
			//only if entiyty.hasDisplayName (for nametag)
			//System.out.println("livestock   "+entity_id);
			entityPlayer.swingItem();
			entityPlayer.worldObj.removeEntity(target); 
			
			if(entityPlayer.worldObj.isRemote) 
				SamsUtilities.spawnParticle(entityPlayer.worldObj, EnumParticleTypes.VILLAGER_HAPPY, target.getPosition());
			else
				entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(ItemRegistry.respawn_egg,1,entity_id),true);

			SamsUtilities.playSoundAt(entityPlayer, "mob.zombie.remedy");
			//SamsUtilities.damageOrBreakHeld(entityPlayer);
			SamsUtilities.decrHeldStackSize(entityPlayer);
			
		} 
	}
	
	
}
