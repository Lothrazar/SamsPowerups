package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDeath 
{
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(event.entity instanceof EntityPlayer == false){return;} 

		System.out.println("PLAYERDEATH");
		
		EntityPlayer player = (EntityPlayer)event.entity;
		
		if(ModLoader.configSettings.dropPlayerSkullOnDeath)
		{ 
			ItemStack skull =  new ItemStack(Items.skull,1,Reference.skull_player);
			if(skull.getTagCompound() == null) skull.setTagCompound(new NBTTagCompound()); 
			skull.getTagCompound().setString("SkullOwner",player.getDisplayNameString());
			
			//EntityItem ei = new EntityItem(event.entity.worldObj, player.posX, player.posY, player.posZ,skull);
			//event.drops.add(ei);   
			 
			SamsUtilities.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
		}
		
		if(ModLoader.configSettings.playerDeathCoordinates)
		{
			String coordsStr = "["+ player.getPosition().getX() + ", "+player.getPosition().getY()+", "+player.getPosition().getZ()+"]";
			SamsUtilities.printChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
		}
	}

}
