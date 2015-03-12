package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerPotionTick 
{ 
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{ 
		 
		     if(event.entityLiving.isPotionActive(ModLoader.potionTired)) 
		     { 
		    	if(event.entityLiving.worldObj.rand.nextInt(Reference.TICKS_PER_SEC) == 0) //pick out one random tick from each second
		      {
	        	 System.out.println("potionTired potion hit");
	        	 
	        	 //need drawbacks ... food cannot be full?
	        	 
	        	 //it only affects players. sets saturation out
	        	 
	        	 if(event.entityLiving instanceof EntityPlayer)
	        	 {
	        		 EntityPlayer player = (EntityPlayer)event.entityLiving;
	        		 
	        		 player.getFoodStats().setFoodSaturationLevel(0F);
	        		  
	        		 if(player.getFoodStats().needFood() == false)//we are maxed out at 19 food
	        		 {
	        			 player.getFoodStats().addStats(-1, 0F);
	        		 }
	        	 }
	        	 
		      }
	        	 
	         } 
		     if(event.entityLiving.isPotionActive(ModLoader.potionFlying)) 
		     { 
		    	 //TODO: importthe existing flying feature
		     }
		     if(event.entityLiving.isPotionActive(ModLoader.potionSlowfall)) 
		     { 
		    	 //a normal fall seems to go up to 0, -1.2, -1.4, -1.6, then flattens out at -0.078
		    	 //https://github.com/OpenMods/OpenBlocks/blob/bfc6edd31e43982b434bcabcc21081c7e1fa6bc2/src/main/java/openblocks/common/entity/EntityHangGlider.java
		    	
		    		//  double horizontalSpeed;
		    		 
		    	 if(event.entityLiving.motionY < 0)
		    	 {
		    		 System.out.println("Reduce motion "+event.entityLiving.motionY);
					 
					 
						//horizontalSpeed = 0.03;
						//verticalSpeed = 0.4;
					 

						event.entityLiving.motionY *= ModLoader.configSettings.slowfallSpeed;
					  
					event.entityLiving.fallDistance = 0f; //for no fall damage
		    	 }
					
		     }
		     if(event.entityLiving.isPotionActive(ModLoader.potionWaterwalk)) 
		     { 
		    	 
		    	 //if the block at y-1 is water, and the block where i am is air, then
		    	 //set motionY to zero, and somehow increase Movementfact/roWalking speed up increased
		    	 //and set onGround = true.
		    	 //Sneaking should give motionY -= 0.1F??
	 
		     }
	     //}
	     
	     
	     
	     
	     
	     
	     
         /*//TODO: do we even need this
         if (event.entityLiving.getActivePotionEffect(ModLoader.potionSlowfall).getDuration() == 0) 
         {
                 event.entityLiving.removePotionEffect(ModLoader.potionSlowfall.id);
                 return;
         }
         */
	}
}
