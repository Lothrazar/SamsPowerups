package com.lothrazar.samscontent.event;

import java.util.ArrayList;   
import org.apache.logging.log4j.Logger;  

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item; 
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
 
public class HandlerSkullSignNames  
{   
  	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(ModLoader.configSettings.skullSignNames == false){ return; }
		
		if(event.action != event.action.LEFT_CLICK_BLOCK) { return; }
	 
		if(event.entityPlayer.isSneaking() == false){ return; }
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		
		if(held != null && held.getItem() == Items.skull && held.getItemDamage() == Reference.skull_player	)
		{
			TileEntity maybesign = event.world.getTileEntity(event.pos);
			if(maybesign != null && maybesign instanceof TileEntitySign)
			{
				TileEntitySign sign = (TileEntitySign)maybesign;
				if(sign != null) //does a tile entity exist here and is it a sign
				{  
					String firstLine = sign.signText[0].getUnformattedText();
					
					if(firstLine == null) { firstLine = ""; }
					if(firstLine.isEmpty() || firstLine.split(" ").length == 0)
					{
						held.setTagCompound(null); 
					}
					else
					{
						//get the first word
						firstLine = firstLine.split(" ")[0];
						
						if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
						
						held.getTagCompound().setString("SkullOwner",firstLine);
					}
				}
			}
		}
   	}
}
