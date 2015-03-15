package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerRichAnimals 
{  
	public static int livestockLootScaleFactor = 4; //if livestock is killed by player, buff the loot by this factor

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDropsEvent event)
	{ 
		if(event.entity.worldObj.isRemote) {return;}
		
		if(ModLoader.configSettings.petNametagDrops && 
				isPet(event.entity) )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{ 
				ItemStack nameTag = new ItemStack(Items.name_tag, 1); 
				 
				//build multi-level NBT tag so it matches a freshly enchanted one
				NBTTagCompound nbt = new NBTTagCompound(); 
				NBTTagCompound display = new NBTTagCompound();
				display.setString("Name", event.entity.getCustomNameTag());//NOT "CustomName" implied by commandblocks/google 
				nbt.setTag("display",display);
				nbt.setInteger("RepairCost", 1);
				
				nameTag.setTagCompound(nbt);//put the data into the item stack
				 
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, event.entity.getPosition(), nameTag); 
			}
		}
		if(ModLoader.configSettings.petNametagChat && 
				event.entity instanceof EntityLiving )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{    
				 SamsUtilities.printChatMessage(
						 (event.source.getDeathMessage((EntityLiving)event.entity)));
			}
		}
		
		if(isLivestock(event.entity))
		{ 
			if(event.source.getSourceOfDamage() != null 
					&& event.source.getSourceOfDamage() instanceof EntityPlayer 
					&& livestockLootScaleFactor > 0) 
			{ 
				//if livestock is killed by a palyer, then multiply the loot by the scale factor
				for(EntityItem ei : event.drops)
				{ 
					//the stack size does not seem to be mutable
					//so we just get and set the stack with a new size
					
					//double it again for pigs
					int factor = (event.entity instanceof EntityPig) ? 2 * livestockLootScaleFactor : livestockLootScaleFactor;
					
					ei.setEntityItemStack(new ItemStack(ei.getEntityItem().getItem(),ei.getEntityItem().stackSize * livestockLootScaleFactor,ei.getEntityItem().getItemDamage()));
				}
			}
			else
			{  
				event.drops.clear();////nope, was not killed by playerso the cow/whatever drops nothing.
			}
		} 
	}
 
	public boolean isLivestock(Entity entity)
	{
		return  (entity instanceof EntityPig) || 
				(entity instanceof EntitySheep)|| 
				(entity instanceof EntityChicken) || 
				(entity instanceof EntityHorse) || 
				(entity instanceof EntityCow) ||
				(entity instanceof EntityRabbit); 
	}
	
	public boolean isPet(Entity entity)
	{
		return  (entity instanceof EntityWolf) || 
				(entity instanceof EntityOcelot) || 
				(entity instanceof EntityVillager) || 
				(entity instanceof EntityBat)||
				(entity instanceof EntityRabbit) ||
				(entity instanceof EntityHorse)  ; 
	}
}
