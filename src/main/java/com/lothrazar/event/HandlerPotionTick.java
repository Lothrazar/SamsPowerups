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
		//if(event.entityLiving.worldObj.rand.nextInt(Reference.TICKS_PER_SEC) == 0) //pick out one random tick from each second
	//	{ 
		     if(event.entityLiving.isPotionActive(ModLoader.potionTired)) 
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
		     if(event.entityLiving.isPotionActive(ModLoader.potionFlying)) 
		     { 
		    	 //TODO: importthe existing flying feature
		     }
		     if(event.entityLiving.isPotionActive(ModLoader.potionSlowfall)) 
		     { 
		    	 //a normal fall seems to go up to 0, -1.2, -1.4, -1.6, then flattens out at -0.078
    			 System.out.println("entityLiving fallDistance =" +event.entityLiving.fallDistance );
		    	 if(event.entityLiving.fallDistance > 5)
		    	 {
		    		 if(event.entityLiving.motionY <= -0.1)
		    		 {
		    			 System.out.println("reduce motionY" );
		    			// event.entityLiving.motionY =  -0.4F;
		    			 
		    			 event.entityLiving.addVelocity(0, 0.8, 0);
		    		 }
		    	 }
		    	 
		     }
		     if(event.entityLiving.isPotionActive(ModLoader.potionWaterwalk)) 
		     { 
		    	 
		     }
	//     }
	     
	     
	     
	     
	     
	     
	     
         /*//TODO: do we even need this
         if (event.entityLiving.getActivePotionEffect(ModLoader.potionSlowfall).getDuration() == 0) 
         {
                 event.entityLiving.removePotionEffect(ModLoader.potionSlowfall.id);
                 return;
         }
         */
	}
}
