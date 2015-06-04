package com.lothrazar.samshorsefood;

import net.minecraft.creativetab.CreativeTabs;
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
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	public static void addRecipes() 
	{
		int dye_lapis = 4;
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.emeraldCarrot)
			,Items.carrot
			,Items.emerald);

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.lapisCarrot)
			,Items.carrot
			,new ItemStack(Items.dye,1,dye_lapis)); 
		
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.diamondCarrot)
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
		 ("owner = "+ownerID);
		 ("player = "+player.getUniqueID().toString());
		   */
		if(held.getItem() == ItemRegistry.emeraldCarrot)
		{ 
			switch(horse.getHorseType())
			{
			case Horse.type_standard:
				horse.setHorseType(Horse.type_zombie);  
				success = true;
				break;
			case Horse.type_zombie:
				horse.setHorseType(Horse.type_skeleton);  
				success = true;
				break;
			case Horse.type_skeleton:
				horse.setHorseType(Horse.type_standard);  
				success = true;
				break;
				//donkey and mule ignored by design
			}
		} 
		else if(held.getItem() == ItemRegistry.lapisCarrot)
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
			case Horse.variant_black:
				var_new = Horse.variant_brown;  
				break;
			case Horse.variant_brown:
				var_new = Horse.variant_brown_dark;  
				break;
			case Horse.variant_brown_dark:
				var_new = Horse.variant_chestnut;  
				break;
			case Horse.variant_chestnut:
				var_new = Horse.variant_creamy;  
				break;
			case Horse.variant_creamy:
				var_new = Horse.variant_gray;  
				break;
			case Horse.variant_gray:
				var_new = Horse.variant_white;  
				break;
			case Horse.variant_white:
				var_new = Horse.variant_black;  
				break;
			}
			var_new += var_reduced;

			horse.setHorseVariant(var_new);

			success = true;
		}
		else if(held.getItem() == ItemRegistry.diamondCarrot)
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
			//ModHorseFood.decrHeldStackSize(player); 
			
			if (player.capabilities.isCreativeMode == false)
	        {
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
	        }
			
			
			//ModHorseFood.spawnParticle(horse.worldObj, , horse.getPosition());
			
			for(int countparticles = 0; countparticles <= 10; ++countparticles)
			{
				double x = horse.getPosition().getX();
				double y = horse.getPosition().getY();
				double z = horse.getPosition().getZ();
				
				horse.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x + (horse.worldObj.rand.nextDouble() - 0.5D) * (double)0.8, y + horse.worldObj.rand.nextDouble() * (double)1.5 - (double)0.1, z + (horse.worldObj.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
			} 
 
			//ModHorseFood.playSoundAt(player, "random.eat"); 
			
			player.worldObj.playSoundAtEntity(player, "random.eat", 1.0F, 1.0F);
			
			horse.setEating(true); //makes horse animate and bend down to eat
		}
	}
	
	public static class Horse
	{
		public static final int variant_white = 0;
		public static final int variant_creamy = 1;
		public static final int variant_chestnut = 2;
		public static final int variant_brown = 3;
		public static final int variant_black = 4;
		public static final int variant_gray = 5;
		public static final int variant_brown_dark = 6; 
		
		public static final int type_standard = 0;
		public static final int type_donkey = 1;
		public static final int type_mule = 2;
		public static final int type_zombie = 3;
		public static final int type_skeleton = 4;
	}
	
	
	
	
	
}
