package com.lothrazar.event;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
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
	        			 player.getFoodStats().addStats(-2, 0F);
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
	    
	    	 if(event.entityLiving.motionY < 0)
	    	 { 
				event.entityLiving.motionY *= ModLoader.configSettings.slowfallSpeed;
				  
				event.entityLiving.fallDistance = 0f; //for no fall damage
	    	 } 
	     }
	     if(event.entityLiving.isPotionActive(ModLoader.potionWaterwalk)) 
	     { 
	    	 World world = event.entityLiving.worldObj;
	    	 
	    	 if(world.getBlockState(event.entityLiving.getPosition().down()).getBlock() == Blocks.water && 
	    			 world.isAirBlock(event.entityLiving.getPosition()) && 
	    			 event.entityLiving.motionY < 0)
	    	 {
	    		 event.entityLiving.motionY  = 0;//stop falling
	    		 event.entityLiving.onGround = true; //act as if on solid ground
	    		 event.entityLiving.setAIMoveSpeed(0.1F);//walking and not sprinting is this speed
	    	 }  
	     }
  
         /*//TODO: do we even need this
         if (event.entityLiving.getActivePotionEffect(ModLoader.potionSlowfall).getDuration() == 0) 
         {
                 event.entityLiving.removePotionEffect(ModLoader.potionSlowfall.id);
                 return;
         }
         */
	}
}
