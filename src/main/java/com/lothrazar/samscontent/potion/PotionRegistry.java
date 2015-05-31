package com.lothrazar.samscontent.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier; 
import com.lothrazar.samscontent.ModMain; 
import com.lothrazar.samscontent.spell.SpellGhost;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util; 
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
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
	public static Potion frost;
	
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
		PotionRegistry.waterwalk = (new PotionCustom(ModMain.cfg.potionIdWaterwalk,  new ResourceLocation("waterwalk"), false, 0)).setPotionName("potion.waterwalk");
		
		PotionRegistry.lavawalk = (new PotionCustom(ModMain.cfg.potionIdLavawalk,  new ResourceLocation("lavawalk"), false, 0)).setPotionName("potion.lavawalk");
		
		PotionRegistry.slowfall = (new PotionCustom(ModMain.cfg.potionIdSlowfall,  new ResourceLocation("slowfall"), false, 0)).setPotionName("potion.slowfall");
		 
		PotionRegistry.ender = (new PotionCustom(ModMain.cfg.potionIdEnder,  new ResourceLocation("ender"), false, 0)).setPotionName("potion.ender");	  
		
		PotionRegistry.frost = (new PotionCustom(ModMain.cfg.potionIdFrozen,  new ResourceLocation("frozen"), false, 0)).setPotionName("potion.frozen");	  
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

	public static void onEntityUpdate(LivingUpdateEvent event) 
	{  
		if(event.entityLiving == null){return;}
		
		if(event.entityLiving instanceof EntityPlayer)
		{
			SpellGhost.onPlayerUpdate(event); 
		}
		
		tickSlowfall(event);
	     
	    tickWaterwalk(event);
	     
	    tickLavawalk(event);

	    tickEnder(event); 
	     
	    tickFrost(event); 
	}
	
	private static void doPotionParticle(World world, EntityLivingBase living, EnumParticleTypes particle)
	{
		if(world.getTotalWorldTime() % Reference.TICKS_PER_SEC/2 == 0) // every half second
    	{
    		Util.spawnParticlePacketByID(living.getPosition(), particle.getParticleID());
    	}
	}

	private static void tickFrost(LivingUpdateEvent event) 
	{ 
		if(event.entityLiving.isPotionActive(PotionRegistry.frost)) 
	    { 
			World world = event.entityLiving.worldObj;
			BlockPos pos = event.entityLiving.getPosition();

			if(world.rand.nextDouble() < 0.5)
				doPotionParticle(world,event.entityLiving,EnumParticleTypes.SNOWBALL);

			if(world.rand.nextDouble() < 0.3 && 
				//world.getBlockState(pos).getBlock().isReplaceable(world, pos) &&
				world.getBlockState(pos.down()).getBlock() != Blocks.snow_layer && 
				//world.getBlockState(pos).getBlock().isReplaceable(world, pos.down()) == false &&//dont place above torches/snow/grass
				
				world.isAirBlock(pos.down()) == false)//dont place above air
			{
				Util.setBlockIfAir(world, pos, Blocks.snow_layer.getDefaultState());
			}
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
	private static void tickEnder(LivingUpdateEvent event) 
	{
		if(event.entity.worldObj.rand.nextDouble() < 0.1)
			doPotionParticle(event.entity.worldObj,event.entityLiving,EnumParticleTypes.PORTAL);
	}
	
	private static void tickSlowfall(LivingUpdateEvent event) 
	{
		 if(event.entityLiving.isPotionActive(PotionRegistry.slowfall)) 
	     { 
			 if(event.entityLiving instanceof EntityPlayer)  //now wait here, since if we are a sneaking player we cancel
			 {
    			 EntityPlayer p = (EntityPlayer)event.entityLiving;
    			 if(p.isSneaking())
    			 {
    				 return;//so fall normally for now
    			 }
    		 }
			 //else: so we are either a non-sneaking player, or a non player entity
			  
	    	 //a normal fall seems to go up to 0, -1.2, -1.4, -1.6, then flattens out at -0.078 
	    	 if(event.entityLiving.motionY < 0)
	    	 { 
				event.entityLiving.motionY *= ModMain.cfg.slowfallSpeed;
				  
				event.entityLiving.fallDistance = 0f; //for no fall damage
	    	 } 
	     }
	}	
}
