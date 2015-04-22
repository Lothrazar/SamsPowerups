package com.lothrazar.samscontent.item;

import java.util.List;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemHorseFood extends Item
{ 
	public ItemHorseFood()
	{  
		super();  
		this.setMaxStackSize(64);
		this.setCreativeTab(ModMain.tabSamsContent);
	}
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer 
    }
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) 
	{
		//list.add("");
	}
	public static void addRecipes() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.horse_upgrade_type)
			,Items.carrot
			,Items.emerald);

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.horse_upgrade_variant)
			,Items.carrot
			,new ItemStack(Items.dye,1,Reference.dye_lapis)); 
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.horse_upgrade_health)
			,Items.carrot
			,Items.diamond); 
	}

	public static void onHorseInteract(EntityHorse horse,EntityPlayer player, ItemStack held) 
	{
		boolean success = false;
		/*
		String ownerID = "..untamed..";
		if(horse.isTame() && horse.getEntityData().hasKey("OwnerUUID"))
		{
			ownerID = horse.getEntityData().getString("OwnerUUID");
		}


		//or let it through if no owner exists
		System.out.println("owner = "+ownerID);
		System.out.println("player = "+player.getUniqueID().toString());
		   */
		if(held.getItem() == ItemRegistry.horse_upgrade_type)
		{ 
			switch(horse.getHorseType())
			{
			case Reference.horse.type_standard:
				horse.setHorseType(Reference.horse.type_zombie);  
				success = true;
				break;
			case Reference.horse.type_zombie:
				horse.setHorseType(Reference.horse.type_skeleton);  
				success = true;
				break;
			case Reference.horse.type_skeleton:
				horse.setHorseType(Reference.horse.type_standard);  
				success = true;
				break;
				//donkey and mule ignored by design
			}
		} 
		else if(held.getItem() == ItemRegistry.horse_upgrade_variant)
		{	 
			int var = horse.getHorseVariant();
			int var_reduced = 0;
			int var_new = 0;
			while(var - 256 > 0)
			{
				var_reduced += 256;//this could be done with modulo % arithmetic too, but meh doesnt matter either way
				var -= 256;
			} // invalid numbers make horse invisible, but this is somehow safe. and easier than doing bitwise ops
			switch(var)
			{
			case Reference.horse.variant_black:
				var_new = Reference.horse.variant_brown;  
				break;
			case Reference.horse.variant_brown:
				var_new = Reference.horse.variant_brown_dark;  
				break;
			case Reference.horse.variant_brown_dark:
				var_new = Reference.horse.variant_chestnut;  
				break;
			case Reference.horse.variant_chestnut:
				var_new = Reference.horse.variant_creamy;  
				break;
			case Reference.horse.variant_creamy:
				var_new = Reference.horse.variant_gray;  
				break;
			case Reference.horse.variant_gray:
				var_new = Reference.horse.variant_white;  
				break;
			case Reference.horse.variant_white:
				var_new = Reference.horse.variant_black;  
				break;
			}
			var_new += var_reduced;

			horse.setHorseVariant(var_new);

			success = true;
		}
		else if(held.getItem() == ItemRegistry.horse_upgrade_health)
		{
			float mh =  (float)horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
		
			if(mh < 40) //20 hearts
			{ 
				horse.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(mh + 2);
				 
				success = true;
			} 
		}
		 
		if(success)
		{ 
			SamsUtilities.decrHeldStackSize(player); 
			SamsUtilities.spawnParticle(horse.worldObj, EnumParticleTypes.SMOKE_LARGE, horse.getPosition());
			SamsUtilities.playSoundAt(player, "random.eat"); 
			horse.setEating(true); //makes horse animate and bend down to eat
		}
	}
}
