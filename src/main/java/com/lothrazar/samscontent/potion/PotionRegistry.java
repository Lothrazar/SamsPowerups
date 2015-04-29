package com.lothrazar.samscontent.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier; 

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.item.ItemFoodGhost;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util; 

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionRegistry 
{ 
	//public static Potion tired;//http://www.minecraftforge.net/wiki/Potion_Tutorial
	public static Potion waterwalk;
	public static Potion slowfall; 
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
		//PotionRegistry.tired = (new PotionCustom(ModSamsContent.configSettings.potionIdTired,  new ResourceLocation("tired"), false, 0)).setPotionName("potion.tired");
		
		PotionRegistry.waterwalk = (new PotionCustom(ModMain.cfg.potionIdWaterwalk,  new ResourceLocation("waterwalk"), false, 0)).setPotionName("potion.waterwalk");
		
		PotionRegistry.lavawalk = (new PotionCustom(ModMain.cfg.potionIdLavawalk,  new ResourceLocation("lavawalk"), false, 0)).setPotionName("potion.lavawalk");
		
		PotionRegistry.slowfall = (new PotionCustom(ModMain.cfg.potionIdSlowfall,  new ResourceLocation("slowfall"), false, 0)).setPotionName("potion.slowfall");
		 
		PotionRegistry.ender = (new PotionCustom(ModMain.cfg.potionIdEnder,  new ResourceLocation("ender"), false, 0)).setPotionName("potion.ender");	  
		
		PotionRegistry.frozen = (new PotionCustom(ModMain.cfg.potionIdFrozen,  new ResourceLocation("frozen"), false, 0)).setPotionName("potion.frozen");	  
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
	 
	public static void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer p = (EntityPlayer)event.entity;
			
			if(p.isPotionActive(PotionRegistry.ender))
			{
				//Feature 1: : remove damage 
				event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts
			  
				//feature 2: odds to return pearl
				int rawChance = 50;//ModLoader.configSettings.chanceReturnEnderPearl;
				
				double pct = ((double)rawChance)/100.0; 
				 
				if(p.worldObj.rand.nextDouble() < pct) //so event.entity.pos is their position BEFORE teleport
				{ 
					EntityItem ei = new EntityItem(p.worldObj, event.targetX, event.targetY, event.targetZ, new ItemStack(Items.ender_pearl));
					p.worldObj.spawnEntityInWorld(ei);
				} 
			}
		}
	} 
	 
	public static void onEntityUpdate(LivingUpdateEvent event) 
	{  
		if(event.entityLiving == null){return;}
		
	    // tickTired(event); 
	        
	     //tickFlying(event);
		if(event.entityLiving instanceof EntityPlayer)
		{
			ItemFoodGhost.onPlayerUpdate(event); 
		}
	  
	     tickSlowfall(event);
	     
	     tickWaterwalk(event);
	     
	     tickLavawalk(event);

	     tickEnder(event); 
	     
	     tickFrozen(event); 
	}
	
	private static void doPotionParticle(World world, EntityLivingBase living, EnumParticleTypes particle)
	{
		if(world.getTotalWorldTime() % Reference.TICKS_PER_SEC/2 == 0) // every half second
    	{
    		//this. fires only on server side. so send packet for client to spawn particles and so on
    		ModMain.network.sendToAll(new MessagePotion(living.getPosition(), particle.getParticleID()));
    	}
	}

	private static void tickFrozen(LivingUpdateEvent event) 
	{ 
		if(event.entityLiving.isPotionActive(PotionRegistry.frozen)) 
	    { 
			event.entityLiving.motionX = event.entityLiving.motionX / 5;
			event.entityLiving.motionY = event.entityLiving.motionY / 5;
			event.entityLiving.motionZ = event.entityLiving.motionZ / 5;
			
			event.entityLiving.setInWeb(); 
			
			doPotionParticle(event.entityLiving.worldObj,event.entityLiving,EnumParticleTypes.SNOWBALL);

			Util.setBlockIfAir(event.entityLiving.worldObj, event.entityLiving.getPosition(), Blocks.snow_layer.getDefaultState());
     
			if(event.entityLiving instanceof EntityPlayer)
			{ 
				EntityPlayer p = (EntityPlayer)event.entityLiving;
   			 	if(p.isSprinting())
   			 	{
   			 		p.setSprinting(false);
   			 	}
			}
	    } 
	}
	
	private static void tickEnder(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.ender)) 
	    { 
			//also  see HandlerEnderpearlTeleport, and handlerPlayerFall 
			doPotionParticle(event.entityLiving.worldObj,event.entityLiving,EnumParticleTypes.PORTAL); 
	    } 
	}

	private static void tickLavawalk(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.lavawalk)) 
	    {
			tickLiquidWalk(event,Blocks.lava);
	    }
	}

	private static void tickWaterwalk(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.waterwalk)) 
	    {
			tickLiquidWalk(event,Blocks.water);
	    }
	}
 
	private static void tickLiquidWalk(LivingUpdateEvent event, Block liquid)
	{
    	 World world = event.entityLiving.worldObj;
    	 
    	 if(world.getBlockState(event.entityLiving.getPosition().down()).getBlock() == liquid && 
    			 world.isAirBlock(event.entityLiving.getPosition()) && 
    			 event.entityLiving.motionY < 0)
    	 { 
    		 if(event.entityLiving instanceof EntityPlayer)  //now wait here, since if we are a sneaking player we cancel it
    		 {
    			 EntityPlayer p = (EntityPlayer)event.entityLiving;
    			 if(p.isSneaking())
    				 return;//let them slip down into it
    		 }
    		 
    		 event.entityLiving.motionY  = 0;//stop falling
    		 event.entityLiving.onGround = true; //act as if on solid ground
    		 event.entityLiving.setAIMoveSpeed(0.1F);//walking and not sprinting is this speed
    	 }  
	}
	
	private static void tickSlowfall(LivingUpdateEvent event) 
	{
		 if(event.entityLiving.isPotionActive(PotionRegistry.slowfall)) 
	     { 
			 if(event.entityLiving instanceof EntityPlayer)  //now wait here, since if we are a sneaking player we cancel
			 {
    			 EntityPlayer p = (EntityPlayer)event.entityLiving;
    			 if(p.isSneaking())
    				 return;//so fall normally for now
    		 }
	    	 //a normal fall seems to go up to 0, -1.2, -1.4, -1.6, then flattens out at -0.078 
	    	 if(event.entityLiving.motionY < 0)
	    	 { 
				event.entityLiving.motionY *= ModMain.cfg.slowfallSpeed;
				  
				event.entityLiving.fallDistance = 0f; //for no fall damage
	    	 } 
	     }
	}
/*
	private static void tickFlying(LivingUpdateEvent event) 
	{
		 if(event.entityLiving.isPotionActive(PotionRegistry.flying)) 
	     { 
	    	 if(event.entityLiving instanceof EntityPlayer && event.entity.worldObj.isRemote)
        	 { 
	    		 EntityPlayer player = (EntityPlayer)event.entityLiving;
	    		  
			 	 player.capabilities.allowFlying = true; 
			 	 
				 if (player.capabilities.isFlying)
				 { 
					 player.fallDistance = 0F;
				 } 
        	 }  
	     }
	     else
	     { 
	    	 if(event.entityLiving instanceof EntityPlayer && event.entity.worldObj.isRemote  )
	    	 {
	    		 if( Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.ADVENTURE  || 
	        		 Minecraft.getMinecraft().playerController.getCurrentGameType() == GameType.SURVIVAL )
				 { 
		    		 EntityPlayer player = (EntityPlayer)event.entityLiving;
	
		    		 if (player.capabilities.isFlying)
					 { 
						 player.fallDistance = 0F;
					 	 player.capabilities.allowFlying = false;//when it times out, OR milk hits
					 	 player.capabilities.isFlying = false;
					 }
				 }
	    	 }
	     }
	}
 */
}
