package com.lothrazar.samscontent.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.lothrazar.samscontent.ModSamsContent;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionRegistry 
{ 
	public static Potion tired;//http://www.minecraftforge.net/wiki/Potion_Tutorial
	public static Potion waterwalk;
	public static Potion slowfall;
	public static Potion flying;
	public static Potion lavawalk;
	public static Potion ender;
	public static Potion frozen;
	
	public final static int I = 0; 
	public final static int II = 1;
	public final static int III = 2;
	public final static int IV = 3;
	public final static int V = 4;
	
	public static void registerPotionEffects()
	{ 
		initPotionTypesReflection();
	     
	    registerNewPotionEffects(); 
	}
 
	private static void registerNewPotionEffects() 
	{ 
		PotionRegistry.tired = (new PotionCustom(ModSamsContent.configSettings.potionIdTired,  new ResourceLocation("tired"), false, 0)).setPotionName("potion.tired");
		
		PotionRegistry.waterwalk = (new PotionCustom(ModSamsContent.configSettings.potionIdWaterwalk,  new ResourceLocation("waterwalk"), false, 0)).setPotionName("potion.waterwalk");
		
		PotionRegistry.lavawalk = (new PotionCustom(ModSamsContent.configSettings.potionIdLavawalk,  new ResourceLocation("lavawalk"), false, 0)).setPotionName("potion.lavawalk");
		
		PotionRegistry.slowfall = (new PotionCustom(ModSamsContent.configSettings.potionIdSlowfall,  new ResourceLocation("slowfall"), false, 0)).setPotionName("potion.slowfall");
		
		PotionRegistry.flying = (new PotionCustom(ModSamsContent.configSettings.potionIdFlying,  new ResourceLocation("flying"), false, 0)).setPotionName("potion.flying");
		
		PotionRegistry.ender = (new PotionCustom(ModSamsContent.configSettings.potionIdEnder,  new ResourceLocation("ender"), false, 0)).setPotionName("potion.ender");	  
		
		PotionRegistry.frozen = (new PotionCustom(ModSamsContent.configSettings.potionIdFrozen,  new ResourceLocation("frozen"), false, 0)).setPotionName("potion.frozen");	  
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
