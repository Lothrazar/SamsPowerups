package com.lothrazar.samscontent.item;

import java.util.List;

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

	public static void onHorseInteract(EntityHorse h,EntityPlayer entityPlayer, ItemStack held) 
	{

//TODO: on ly if //h.isAdultHorse()
		//and
		/*check if owner is ME
		 * if (tagCompund.hasKey("OwnerUUID", 8))
        {
            s = tagCompund.getString("OwnerUUID");
        }
        */
		
		//TYPE . 0 = Normal, 1 = Donkey, 2 = Mule, 3 = Undead Horse, 4 = Skeleton Horse
		
		
		
		//TODO: finish and flesh out this feature. all types/variants 

		System.out.println("type="+h.getHorseType());
		System.out.println("variant="+h.getHorseVariant());
		h.setHorseType(Reference.horse.type_zombie);
		 
		SamsUtilities.decrHeldStackSize(entityPlayer);
		
		
		
		//HORSES? change them on the fly without breeeding new wones
		//Custom items!!!!!!
	//	 that when fed to a horse, will increase speed and/or jump height.
		//and an item that changes the color/variant/etc
		//and two more for skel/zomb                 
		                        
		            //  h.getAlwaysRenderNameTag()
		            //  h.getCurrentArmor(arg0)
		            //  h.getEquipmentInSlot(arg0)
		                //h.setTamedBy(arg0)
		                
		                //h.isChild()
		            //  h.isUndead()
		            //  h.setLeashedToEntity(arg0, arg1);
		            //  h.setHorseVariant(arg0);// A TOOL TO CHANGE THIS!L?!?!?!?
		            //  h.setHorseType(arg0);
		                //TODO: can we spawn zombie horse? 
		                //USE EXISTING WAND TRANSFORM?
		                     //Magic wand to turn horses to zombie/skeleton?
		                                //yeah i think we can in the sapwn event and roll a dice and check the biome
		                                //http://www.minecraftforge.net/forum/index.php?topic=8937.0
		                                //and then just tag all biome horses as the undead type 
		                                /*
		                                 * Tamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3,Tame:1}
		                            Untamed Zombie Horse: /summon EntityHorse ~ ~ ~ {Type:3}
		                            Tamed Skeleton Horse: /summon EntityHorse ~ ~ ~ {Type:4,Tame:1}
		                            Untamed S
		    keleton Horse: /summon EntityHorse ~ ~ ~ {Type:4}
		                                */ 
		
	}
}
