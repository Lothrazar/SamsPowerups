package com.lothrazar.samscontent.item;

import java.util.List;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHorseFood extends Item
{ 
	public ItemHorseFood()
	{  
		super();  
		this.setMaxStackSize(64);
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{
		//list.add("");
	}
	public static void addRecipe() 
	{
		
	}

	public static void onHorseInteract(EntityHorse horse,EntityPlayer player, ItemStack held) 
	{
		String ownerID = "..untamed..";
		if(horse.isTame() && horse.getEntityData().hasKey("OwnerUUID"))
		{
			ownerID = horse.getEntityData().getString("OwnerUUID");
		}

		//TODO: only let the OWNER change the types
		//or let it through if no owner exists
		System.out.println("owner = "+ownerID);
		System.out.println("player = "+player.getUniqueID().toString());
		  
		System.out.println("type = "+horse.getHorseType());
		System.out.println("variant = "+horse.getHorseVariant());
		
		
		if(held.getItem() == ItemRegistry.horse_upgrade_type)
		{ 
			switch(horse.getHorseType())
			{
			case Reference.horse.type_standard:
				horse.setHorseType(Reference.horse.type_zombie);  
				break;
			case Reference.horse.type_zombie:
				horse.setHorseType(Reference.horse.type_skeleton);  
				break;
			case Reference.horse.type_skeleton:
				horse.setHorseType(Reference.horse.type_standard);  
				break;
			}
		}
		else if(held.getItem() == ItemRegistry.horse_upgrade_variant)
		{
			switch(horse.getHorseVariant())
			{
			case Reference.horse.variant_black: 
				horse.setHorseVariant(Reference.horse.variant_brown);
				break;
			case Reference.horse.variant_brown:   
				horse.setHorseVariant(Reference.horse.variant_brown_dark);
				break;
			case Reference.horse.variant_brown_dark: 
				horse.setHorseVariant(Reference.horse.variant_chestnut);  
				break;
			case Reference.horse.variant_chestnut: 
				horse.setHorseVariant(Reference.horse.variant_creamy);  
				break;
			case Reference.horse.variant_creamy: 
				horse.setHorseVariant(Reference.horse.variant_gray);  
				break;
			case Reference.horse.variant_gray:   
				horse.setHorseVariant(Reference.horse.variant_white);
				break;
			case Reference.horse.variant_white: 
				horse.setHorseVariant(Reference.horse.variant_black);  
				break;
			}
		}
		//TODO:we could do speed/jump/health upgrades too
		 
		//TODO: sound and particle
		SamsUtilities.decrHeldStackSize(player); 
	}
}
