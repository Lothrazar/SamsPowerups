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
		 
		  
		//TODO: finish and flesh out this feature. all types/variants 

		System.out.println("type = "+horse.getHorseType());
		System.out.println("variant = "+horse.getHorseVariant());
		
		
		if(held.getItem() == ItemRegistry.horse_upgrade_type)
		{ 
			horse.setHorseType(Reference.horse.type_zombie);//hardcoded test
		}
		else if(held.getItem() == ItemRegistry.horse_upgrade_variant)
		{
			horse.setHorseVariant(Reference.horse.variant_white);//hardcoded test
		}
		 
		SamsUtilities.decrHeldStackSize(player); 
	}
}
