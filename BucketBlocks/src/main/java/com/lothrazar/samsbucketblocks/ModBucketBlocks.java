package com.lothrazar.samsbucketblocks;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = ModBucketBlocks.MODID, useMetadata=true)
public class ModBucketBlocks
{
    public static final String MODID = "samsbucketblocks";
	public static final String TEXTURE_LOCATION = ModBucketBlocks.MODID + ":"; 
	@Instance(value = ModBucketBlocks.MODID)
	public static ModBucketBlocks instance;
	public static Logger logger; 

	@SidedProxy(clientSide="com.lothrazar.samsbucketblocks.ClientProxy", serverSide="com.lothrazar.samsbucketblocks.CommonProxy")
	public static CommonProxy proxy;  
   
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  

		BlockRegistry.registerBlocks();
	 
	  	ArrayList<Object> handlers = new ArrayList<Object>();
	    
     	handlers.add(instance                         ); 
     	handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   

     	for(Object h : handlers)
     	{ 
    		FMLCommonHandler.instance().bus().register(h); 
    		MinecraftForge.EVENT_BUS.register(h); 
     	} 
	}
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.registerRenderers();
    }
  
	@SubscribeEvent
	public void onBreakEvent(BreakEvent event)
	{
		TileEntity ent = event.world.getTileEntity(event.pos);
		  
		//TODO; check tool/pickaxe? if notHarvestable or whatever, drop the buckets and the ..glass?
		 
		if(ent != null && ent instanceof TileEntityBucketStorage)
		{
			TileEntityBucketStorage t = (TileEntityBucketStorage)ent;
			ItemStack stack = new ItemStack(event.state.getBlock());
			
			setItemStackNBT(stack, BlockBucketStorage.NBTBUCKETS, t.getBuckets());
		
			dropItemStackInWorld(event.world, event.pos, stack);

			t.setBuckets(0);
		}
	}

	public static void setItemStackNBT(ItemStack item,	String prop, int value) 
	{
		if(item.getTagCompound() == null) {item.setTagCompound(new NBTTagCompound());}
		item.getTagCompound().setInteger(prop, value);
	} 
	
	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 
 
 		if(worldObj.isRemote==false)//do not spawn a second 'ghost' one on client side
 		{
 			worldObj.spawnEntityInWorld(entityItem);
 		}
    	return entityItem;
	}
	
	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
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
}