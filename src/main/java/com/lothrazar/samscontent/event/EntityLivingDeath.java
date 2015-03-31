package com.lothrazar.samscontent.event;

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityLivingDeath 
{
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( ModSamsContent.configSettings.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
			System.out.println("endermen death");//TODO: delete debug sts
			IBlockState bs = mob.func_175489_ck();
			
			if(bs != null && bs.getBlock() != null)
			{
				System.out.println(bs.getBlock().getUnlocalizedName());//TODO: delete debug sts
				
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModSamsContent.configSettings.dropPlayerSkullOnDeath)
			{ 
				ItemStack skull =  new ItemStack(Items.skull,1,Reference.skull_player);
				if(skull.getTagCompound() == null) skull.setTagCompound(new NBTTagCompound()); 
				skull.getTagCompound().setString("SkullOwner",player.getDisplayNameString());
				
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModSamsContent.configSettings.playerDeathCoordinates)
			{
				String coordsStr = SamsUtilities.posToString(player.getPosition()); 
				SamsUtilities.printChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
		}
	}

}
