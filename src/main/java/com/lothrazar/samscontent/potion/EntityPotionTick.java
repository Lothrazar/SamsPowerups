package com.lothrazar.samscontent.potion;

import com.lothrazar.samscontent.MessagePotion;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent; 
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityPotionTick 
{ 
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		if(event.entityLiving == null){return;}
		
	     tickTired(event); 
	        
	     tickFlying(event);
	  
	     tickSlowfall(event);
	     
	     tickWaterwalk(event);
	     
	     tickLavawalk(event);

	     tickEnder(event); 
	     
	     tickFrozen(event); 
	}

	private void tickFrozen(LivingUpdateEvent event) 
	{ 
		if(event.entityLiving.isPotionActive(PotionRegistry.frozen)) 
	    { 
			event.entityLiving.motionX = event.entityLiving.motionX / 5;
			event.entityLiving.motionY = event.entityLiving.motionY / 5;
			event.entityLiving.motionZ = event.entityLiving.motionZ / 5;
			
			event.entityLiving.setInWeb(); 
  
        	if(event.entityLiving.worldObj.getTotalWorldTime() % Reference.TICKS_PER_SEC == 0) //once per second
        	{
        		//this. fires only on server side. so send packet for client to spawn particles and so on
        		ModSamsContent.network.sendToAll(new MessagePotion(event.entityLiving.getPosition()));
        	}
             
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
	
	private void tickEnder(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.ender)) 
	    { 
			//does nothing here, exactly.  see HandlerEnderpearlTeleport, and handlerPlayerFall 
	    } 
	}

	private void tickLavawalk(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.lavawalk)) 
	    {
			tickLiquidWalk(event,Blocks.lava);
	    }
	}

	private void tickWaterwalk(LivingUpdateEvent event) 
	{
		if(event.entityLiving.isPotionActive(PotionRegistry.waterwalk)) 
	    {
			tickLiquidWalk(event,Blocks.water);
	    }
	}
 
	private void tickLiquidWalk(LivingUpdateEvent event, Block liquid)
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
	
	private void tickSlowfall(LivingUpdateEvent event) 
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
				event.entityLiving.motionY *= ModSamsContent.configSettings.slowfallSpeed;
				  
				event.entityLiving.fallDistance = 0f; //for no fall damage
	    	 } 
	     }
	}

	private void tickFlying(LivingUpdateEvent event) 
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

	private void tickTired(LivingUpdateEvent event) 
	{
		 if(event.entityLiving.isPotionActive(PotionRegistry.tired)) 
	     { 
	    	 if(event.entityLiving.worldObj.rand.nextInt(Reference.TICKS_PER_SEC) == 0) //pick out one random tick from each second
	    	 {
	        	 //System.out.println("potionTired potion hit");
	        	 
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
	}
}
