package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemSoulstone extends Item
{
	public ItemSoulstone()
	{  
		super();    
		this.setCreativeTab(ModMain.tabSamsContent); 
	}
	
	private static final String KEY_STONED = "soulstone";
	
	public void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.soulstone), 
				new ItemStack(Items.dye,1,Reference.dye_purple),
				new ItemStack(Items.ender_eye),
				new ItemStack(Items.gold_nugget)
		);
	}
	
	public static void onEntityInteract(EntityInteractEvent event) 
	{ 
		if(event.target.getEntityData().getBoolean(KEY_STONED))
		{ 
			return;//mob has been soulstoned already, do not double up
		}

		event.target.getEntityData().setBoolean(KEY_STONED, true);
		
		SamsUtilities.decrHeldStackSize(event.entityPlayer); 
		
		SamsUtilities.spawnParticle(event.target.worldObj, EnumParticleTypes.PORTAL, event.target.getPosition());
		
		SamsUtilities.playSoundAt(event.target, "mob.endermen.death");
	}
	 
	public static void onLivingHurt(LivingHurtEvent event) 
	{
		//thanks for the help:
		//http://www.minecraftforge.net/forum/index.php?topic=7475.0
		
		if(event.entityLiving.getEntityData().getBoolean(KEY_STONED))
		{ 
			//yes there is a typo in the word 'amount' but it is not in my code  
			float amount = event.ammount;
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{ 
				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//this is possible but not needed
				
				SamsUtilities.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				
				boolean isTamedPet = false;
				
				//functions are different since wolf/ocelot extend EntityTameable, but horse has its own way
				if(event.entityLiving instanceof EntityWolf)
				{
					EntityWolf dog = (EntityWolf)event.entityLiving; 
					isTamedPet = dog.isTamed();
				}
				if(event.entityLiving instanceof EntityOcelot)
				{
					EntityOcelot cat = (EntityOcelot)event.entityLiving; 
					isTamedPet = cat.isTamed();
				}
				if(event.entityLiving instanceof EntityHorse)
				{
					EntityHorse horse = (EntityHorse)event.entityLiving; 
					isTamedPet = horse.isTame();
				}
				
				if(isTamedPet == false)
				{
					event.entityLiving.getEntityData().setBoolean(KEY_STONED, false);
				}
				//else, since pets are buggy and teleport/suicide all the time, just let it stay forever
			}
		} 
	} 
}
