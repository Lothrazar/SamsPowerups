package com.lothrazar.samscontent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionRegistry 
{

	public static void registerPotionEffects()
	{ 
		initPotionTypesReflection();
	     
	    registerNewPotionEffects(); 
	}
 
	private static void registerNewPotionEffects() 
	{
		
		ModLoader.potionTired = (new PotionTired(ModLoader.configSettings.potionIdTired,  new ResourceLocation("tired"), false, 0)).setPotionName("potion.tired");
		
		ModLoader.potionWaterwalk = (new PotionTired(ModLoader.configSettings.potionIdWaterwalk,  new ResourceLocation("waterwalk"), false, 0)).setPotionName("potion.tired");
		
		ModLoader.potionSlowfall = (new PotionTired(ModLoader.configSettings.potionIdSlowfall,  new ResourceLocation("slowfall"), false, 0)).setPotionName("potion.tired");
		
		ModLoader.potionFlying = (new PotionTired(ModLoader.configSettings.potionIdFlying,  new ResourceLocation("flying"), false, 0)).setPotionName("potion.tired");
		 
		
		
	}

	private static void initPotionTypesReflection() 
	{
		//because it is final, and because forge does not natively do this, we must change it this way
		Potion[] potionTypes = null;  //  public static final Potion[] potionTypes = new Potion[32];
	    for (Field f : Potion.class.getDeclaredFields()) 
	    {
	        f.setAccessible(true);
	        try 
	        { 
	            if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) 
	            {
	                Field modfield = Field.class.getDeclaredField("modifiers");
	                modfield.setAccessible(true);
	                modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

	                potionTypes = (Potion[])f.get(null);
	                final Potion[] newPotionTypes = new Potion[256];
	               
	                System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
	                f.set(null, newPotionTypes);
	                
	            }
	        }
	        catch (Exception e) 
	        {
	            System.err.println("Severe error, please report this to the mod author:");
	            System.err.println(e);
	        }
	    }
	}
}
