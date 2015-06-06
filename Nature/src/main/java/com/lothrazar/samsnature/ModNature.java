package com.lothrazar.samsnature;

import java.util.ArrayList; 

import org.apache.logging.log4j.Logger;     
 



import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
  
@Mod(modid = ModNature.MODID, version = ModNature.VERSION,	name = ModNature.NAME, useMetadata = true )  
public class ModNature
{	
	public static final String MODID = "samsnature";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.0.0";
	public static final String NAME = "Sam's Nature";

	@Instance(value = MODID)
	public static ModNature instance;	
	@SidedProxy(clientSide="com.lothrazar.samsnature.ClientProxy", serverSide="com.lothrazar.samsnature.CommonProxy")
	public static CommonProxy proxy;   

	public static Logger logger; 
	public static ConfigNature cfg;
//	public static SimpleNetworkWrapper network;  

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigNature(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	//network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    //	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		ItemBlockRegistry.registerItems();
 
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
	public static final int dye_bonemeal = 15;
	public static boolean isBonemeal(ItemStack held )
	{ 
		Item heldItem = (held == null) ? null : held.getItem();
		
		if(heldItem == null){return false;}
	 
		return (heldItem.equals(Items.dye)  && held.getItemDamage() == dye_bonemeal); 
	}
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		if(  event.action == event.action.RIGHT_CLICK_BLOCK && 
  				isBonemeal(held)  && 
  				blockClicked != null ) 
		{    
			useBonemeal(event.world, event.entityPlayer, event.pos, blockClicked);
		}
  	}
	private void useBonemeal(World world, EntityPlayer entityPlayer, BlockPos pos, Block blockClicked)
	{
		boolean success = false; 
		 
  		//we could do a 1/3 odds chance or something, but tall flowers work everytime so following that
  		
  		if(ModNature.cfg.bonemealAllFlowers )
  		{ 
		 	if ( blockClicked.equals(Blocks.yellow_flower))//yellow flowers have no damage variations
		 	{   
		 		ModNature.dropItemStackInWorld(world, pos,new ItemStack(Blocks.yellow_flower));
		
			  	success = true;
		 	}
		 	else if ( blockClicked.equals(Blocks.red_flower)) 	//the red flower is ALL the flowers
		 	{    
		 		ModNature.dropItemStackInWorld(world, pos, new ItemStack(Blocks.red_flower ,1,Blocks.red_flower.getMetaFromState(world.getBlockState(pos))));
		
			  	success = true; 
		 	} 
  		}
	 	
	 	if(ModNature.cfg.bonemealLilypads && blockClicked.equals(Blocks.waterlily))
	 	{ 
	 		ModNature.dropItemStackInWorld(world, pos,new ItemStack(Blocks.waterlily));
	 	 
		  	success = true;
	 	} 
	 	if(ModNature.cfg.bonemealReeds && blockClicked.equals(Blocks.reeds))
	 	{
	 		//do not drop items, just grow them upwards
	 	 
	 		int goUp = 0;
	 		
	 		if(world.isAirBlock(pos.up(1))) goUp = 1;
	 		else if(world.isAirBlock(pos.up(2))) goUp = 2;

			if(goUp > 0)
			{
				world.setBlockState(pos.up(goUp), Blocks.reeds.getDefaultState());

			  	success = true; 
			} 
	 	}
	 	
	 	if(success)
	 	{ 
	 		//the game also uses VILLAGER_HAPPY for their bonemeal events so i copy
	 		ModNature.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, pos);
	 		 
	 		ModNature.decrHeldStackSize(entityPlayer); 
	 	} 
	}
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{  
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.world.isAirBlock(event.pos.up()) 
			&& ItemBlockRegistry.beetroot_seed != null
			&& event.world.rand.nextInt(16) == 0) //it is a 1/15 chance
		{			
			if(event.world.isRemote == false)
			{
				dropItemStackInWorld(event.world, event.pos, ItemBlockRegistry.beetroot_seed);
			}

			//event.entityPlayer.addStat(achievements.beetrootSeed, 1);
		}
	}
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		ChestLootGenerator.regsiterLoot();

  		if(ModNature.cfg.worldGenOceansNotUgly)
		{ 
  			int weight = 0;
			GameRegistry.registerWorldGenerator(new WorldGeneratorOcean(), weight);
		}
  		 
 
		proxy.registerRenderers();
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
