package com.lothrazar.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandCopyPaste  extends Item
{
	public ItemWandCopyPaste()
	{  
		super();  
		this.setCreativeTab(ModLoader.tabSamsContent);
    	this.setMaxDamage(99); 
		this.setMaxStackSize(1);
	}
 
	public static void init()
	{
		//if(!ModLoader.configSettings.wandCopy) {return;}//TODO: config
		ItemRegistry.wandCopy = new ItemWandCopyPaste();

		SamsRegistry.registerItem(ItemRegistry.wandCopy, "wand_copy");
  
	}

	public static void copySign(World world, EntityPlayer entityPlayer,	TileEntitySign sign, ItemStack held) 
	{  
		SamsUtilities.setItemStackNBT(held, "sign_0", sign.signText[0].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_1", sign.signText[1].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_2", sign.signText[2].getUnformattedText());
		SamsUtilities.setItemStackNBT(held, "sign_3", sign.signText[3].getUnformattedText());

		entityPlayer.swingItem();
		 
	}

	public static void pasteSign(World world, EntityPlayer entityPlayer,	TileEntitySign sign, ItemStack held) 
	{ 
 
		//sign.setPlayer(entityPlayer);
		sign.signText[0] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_0"));
		sign.signText[1] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_1"));
		sign.signText[2] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_2"));
		sign.signText[3] = new ChatComponentText(SamsUtilities.getItemStackNBT(held, "sign_3"));

		//sign.setEditable(false);
		entityPlayer.swingItem();
	 
		world.markBlockForUpdate(sign.getPos());//so update is refreshed on client side
 
	}
 
	 
	 
}
