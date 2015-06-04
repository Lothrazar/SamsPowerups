package com.lothrazar.samsnature;

import java.util.ArrayList; 

import org.apache.logging.log4j.Logger;    

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
  
@Mod(modid = ModNature.MODID, version = ModNature.VERSION,	name = ModNature.NAME, useMetadata = true )  
public class ModNature
{	
	public static final String MODID = "samsnature";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Builder's Nature";

	@Instance(value = MODID)
	public static ModNature instance;
	//@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	//public static CommonProxy proxy;   
	public static Logger logger; 
	public static ConfigRegistry cfg;
//	public static SimpleNetworkWrapper network;  
 
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	//network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    //	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
 
		 
		this.registerEventHandlers(); 
		 
		 
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
 
	//	MobSpawningRegistry.registerSpawns();
  
		ChestLootGenerator.regsiterLoot();
		   
 /*
  		if(ModMain.cfg.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuelRegistry()); 
  		}*/
  		
  		if(ModNature.cfg.worldGenOceansNotUgly)
		{ 
  			int weight = 0;
			GameRegistry.registerWorldGenerator(new WorldGeneratorOcean(), weight);
		}
  		 
 
		//proxy.registerRenderers();
	}
	 
	private void registerEventHandlers() 
	{ 
    	ArrayList<Object> handlers = new ArrayList<Object>();
  
      	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      	handlers.add(instance                         );  

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h); 
	    		MinecraftForge.EVENT_BUS.register(h); 
	    		MinecraftForge.TERRAIN_GEN_BUS.register(h);
	    		MinecraftForge.ORE_GEN_BUS.register(h); 
	     	} 
	}
	public static void decrHeldStackSize(EntityPlayer entityPlayer) 
	{
		decrHeldStackSize(entityPlayer,1);
	}
	public static void decrHeldStackSize(EntityPlayer entityPlayer, int by) 
	{
		if (entityPlayer.capabilities.isCreativeMode == false)
        {
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, by);
        }
	}
 /*
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		BlockPos pos = event.entity.getPosition();
		World world = event.entity.worldObj;

		if( event.entity instanceof EntityZombie) //how to get this all into its own class
		{  
			EntityZombie z = (EntityZombie)event.entity;
			
			if(ModMain.cfg.removeZombieCarrotPotato)
				for(int i = 0; i < event.drops.size(); i++) 
				{
					EntityItem item = event.drops.get(i);
					
					if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
					{ 
						event.drops.remove(i);
					}
				}
			
			
			if(z.isChild() && ModMain.cfg.chanceZombieChildFeather > 0 && 
					event.entity.worldObj.rand.nextInt(100) <= ModMain.cfg.chanceZombieChildFeather)
			{ 
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ()
						,new ItemStack(Items.feather)));
			}
			 
			if(z.isVillager() && ModMain.cfg.chanceZombieVillagerEmerald > 0
					&& event.entity.worldObj.rand.nextInt(100) <=  ModMain.cfg.chanceZombieVillagerEmerald)
			{
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ() 
						,new ItemStack(Items.emerald)));
			} 
		} 
		 
		if(ModMain.cfg.petNametagDrops //no need to restrict to pets && SamsUtilities.isPet(event.entity)
		  && event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		   event.entity.getCustomNameTag() != ""   
		   ) 
		{  
			//item stack NBT needs the name enchanted onto it
			ItemStack nameTag = Util.buildEnchantedNametag(event.entity.getCustomNameTag());
		  
			Util.dropItemStackInWorld(world, event.entity.getPosition(), nameTag);  
		}
		
		if(ModMain.cfg.petNametagChat && 
			event.entity instanceof EntityLivingBase && 
			event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		    event.entity.getCustomNameTag() != ""   &&
		    event.entity.worldObj.isRemote == false) 
		{    
			//show message as if player, works since EntityLiving extends EntityLivingBase
	 
			Util.addChatMessage((event.source.getDeathMessage((EntityLivingBase)event.entity)));
		}
		 
		if(ModMain.cfg.cowExtraLeather > 0 && event.entity instanceof EntityCow)
		{
			event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(), new ItemStack(Items.leather,ModMain.cfg.cowExtraLeather)));
		} 
	} */
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		if(pos != null)
			spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }
 
	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double)0.8, y + world.rand.nextDouble() * (double)1.5 - (double)0.1, z + (world.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
		} 
    }
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, Block block)
	{
		return dropItemStackInWorld(worldObj, pos, new ItemStack(block));  
	}
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, Item item)
	{
		return dropItemStackInWorld(worldObj, pos, new ItemStack(item)); 
	}
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 

 		if(worldObj.isRemote==false)//do not spawn a second 'ghost' one on client side
 			worldObj.spawnEntityInWorld(entityItem);
    	return entityItem;
	}
	
}
