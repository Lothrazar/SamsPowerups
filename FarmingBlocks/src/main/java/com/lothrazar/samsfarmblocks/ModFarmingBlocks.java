package com.lothrazar.samsfarmblocks;
 
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModFarmingBlocks.MODID, useMetadata=true)
public class ModFarmingBlocks
{
	@Instance(value = ModFarmingBlocks.MODID)
	public static ModFarmingBlocks instance;
	public static CreativeTabs tabSFarming = new CreativeTabs("tabSFarming") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return Item.getItemFromBlock(BlockRegistry.block_fishing);
		}
	};    

	@SidedProxy(clientSide="com.lothrazar.samsfarmblocks.ClientProxy", serverSide="com.lothrazar.samsfarmblocks.CommonProxy")
	public static CommonProxy proxy;  
    public static final String MODID = "samsfarmblocks";
	public static final String TEXTURE_LOCATION = MODID + ":"; 
	
    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
    	BlockRegistry.registerBlocks();
    	
    	FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		proxy.registerRenderers();
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
}
