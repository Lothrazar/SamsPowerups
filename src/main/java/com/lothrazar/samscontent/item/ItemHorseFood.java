package com.lothrazar.samscontent.item;

import java.util.List;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.SharedMonsterAttributes;
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
		boolean success = false;
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
			success = true;
		}
		///261!?!?!
		//double currJump = horse.getHorseJumpStrength();
	//	horse.getEntityAttribute(EntityHorse.horseJumpStrength).setAttributeValue(3);
		
		
		else if(held.getItem() == ItemRegistry.horse_upgrade_variant)
		{	

			int var = horse.getHorseVariant();
			int reduced = 0;
			while(var - 256 > 0)
			{
				reduced += 256;
				var -= 256;
			}//TODO: needs work. invalid numbers maek horse invisible
			
			/*case Reference.horse.variant_white: variant = "White";break; 
			case Reference.horse.variant_creamy: variant = "Creamy";break;
			case Reference.horse.variant_chestnut: variant = "Chestnut";break;
			case Reference.horse.variant_brown: variant = "Brown";break;
			case Reference.horse.variant_black: variant = "Black";break;
			case Reference.horse.variant_gray: variant = "Gray";break;
			case Reference.horse.variant_brown_dark: variant = "Dark Brown";break; */
			
			horse.setHorseVariant(horse.getHorseVariant() + 16);

			success = true;
		}
		else if(held.getItem() == ItemRegistry.horse_upgrade_health)
		{
			float mh =  (float)horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		
			if(mh < 40)
			{

				horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mh + 2);
				
 
				success = true;
			}
			
		}
		//TODO:we could do speed/jump/health upgrades too	
		//	horse.getEntityAttribute(EntityHorse.horseJumpStrength).setAttributeValue(3);
		 
		
		 
		if(success)
		{
			//TODO: sound and particle
			SamsUtilities.decrHeldStackSize(player); 
			
		}
	}
}
