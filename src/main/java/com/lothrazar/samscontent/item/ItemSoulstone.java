package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulstone extends Item
{
	private boolean hasEffect;
	public ItemSoulstone(boolean shiny)
	{  
		super();    
		hasEffect = shiny;
		this.setCreativeTab(ModMain.tabSamsContent); 
	}
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return hasEffect; //give it shimmer, depending on if this was set in constructor
    }
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		 if(hasEffect)
			 return EnumRarity.EPIC; //dynamic text to match the two apple colours
		 else 
			 return EnumRarity.RARE;
	} 
	 
	
	private static final String KEY_STONED = "soulstone";
	private static final int VALUE_SINGLEUSE = -1;
	private static final int VALUE_PERSIST = 1;
	private static final int VALUE_EMPTY = 0;
	
	public void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.soulstone), 
			new ItemStack(Items.dye,1,Reference.dye_purple),
			new ItemStack(Items.ender_eye),
			new ItemStack(Items.gold_nugget)
		);
		 
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.soulstone_persist)
			,"lll","lal","lll"  
			,'l', Items.emerald
			,'a', ItemRegistry.soulstone);
	
		if(ModMain.cfg.uncraftGeneral) 
		{
			GameRegistry.addSmelting(ItemRegistry.soulstone, new ItemStack(Items.ender_eye, 8),	0);
			
			GameRegistry.addSmelting(ItemRegistry.soulstone_persist, new ItemStack(Items.emerald, 8),	0);
		} 
	}
	
	public static void onEntityInteract(EntityInteractEvent event) 
	{ 
		Item item = event.entityPlayer.getHeldItem() == null ? null : event.entityPlayer.getHeldItem().getItem();
		
		if(item != ItemRegistry.soulstone && item != ItemRegistry.soulstone_persist){return;}
		 
		//getInteger by default returns zero if no value exists
		if(item == ItemRegistry.soulstone  && 
				event.target.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{ 
			return;//for single use, only apply if existing is empty (do not overwrite persist)
		}
		if(item == ItemRegistry.soulstone_persist  && 
				event.target.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST)
		{ 
			return;//if we are using a persisting soulstone, it can overwrite single use or empty
			//just do not overwrite if we already have a persisting one applied
		}
		
		int newValue = (item == ItemRegistry.soulstone_persist) ? VALUE_PERSIST : VALUE_SINGLEUSE;
		 
		event.target.getEntityData().setInteger(KEY_STONED, newValue);
		
		Util.decrHeldStackSize(event.entityPlayer); 
		
		Util.spawnParticle(event.target.worldObj, EnumParticleTypes.PORTAL, event.target.getPosition());
		
		Util.playSoundAt(event.target, "mob.endermen.death");
	}
	 
	public static void onLivingHurt(LivingHurtEvent event) 
	{
		//thanks for the help:
		//http://www.minecraftforge.net/forum/index.php?topic=7475.0
		
		if(event.entityLiving.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{  
			float amount = event.ammount;//yes there is a typo in the word 'amount' but it is not in my code  
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{ 
				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//this is possible but not needed
				
				Util.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				boolean isPersist = event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST;
				 
				if(event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_SINGLEUSE)
				{
					//if it does not persist, then consume this use
					event.entityLiving.getEntityData().setInteger(KEY_STONED, VALUE_EMPTY);
				} 
			}
		} 
	} 
}
