package com.lothrazar.powerinventory;

import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import com.lothrazar.powerinventory.proxy.CommonProxy; 
import com.lothrazar.powerinventory.proxy.DumpButtonPacket;
import com.lothrazar.powerinventory.proxy.EnderChestPacket;
import com.lothrazar.powerinventory.proxy.ExpButtonPacket;
import com.lothrazar.powerinventory.proxy.FilterButtonPacket;
import com.lothrazar.powerinventory.proxy.EnderPearlPacket;
import com.lothrazar.powerinventory.proxy.SortButtonPacket; 

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Initially Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 * before later being merged into my main project
 */
@Mod(modid = Const.MODID, useMetadata=true)
public class ModInv
{
	//??POSSIBLE additions? 
	
	//a back button in inventory. shows only IF we use I, then back goes back into where we were??
	
	// on item pickup (pearl/chest) put it in the special slot by default-if possible
	//shift click out of hotbar should go directly to special slots, work same way as armor
	// left/right buttons could merge stacks ? OR add a middle button that does some sort of merge/sort?

	 
	//idea: liquid//potion storage? bucket slot, fillButton, drainButton, and a # showing whats stored (leaves empty behind)
		//but only one type of lq at a time, and have a max /64
	
	//Display exact exp numbers? such as  450/5200 = for next level (90453= total)  
 
	@Instance(Const.MODID)
	public static ModInv instance;
	
	@SidedProxy(clientSide = "com.lothrazar.powerinventory.proxy.ClientProxy", serverSide = "com.lothrazar.powerinventory.proxy.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	public static Configuration config;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
    	
    	int packetID = 0;
    	network.registerMessage(EnderChestPacket.class,  EnderChestPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(SortButtonPacket.class,  SortButtonPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(FilterButtonPacket.class,FilterButtonPacket.class,packetID++, Side.SERVER);
    	network.registerMessage(EnderPearlPacket.class,  EnderPearlPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(ExpButtonPacket.class,   ExpButtonPacket.class,   packetID++, Side.SERVER);
    	network.registerMessage(DumpButtonPacket.class,  DumpButtonPacket.class,  packetID++, Side.SERVER);
    	 
		config = new Configuration(event.getSuggestedConfigurationFile(), true);
    	loadConfig();
		
    	proxy.registerHandlers();
    }
    
	private void loadConfig() 
	{
    	config.load();
    	 
    	String category = Configuration.CATEGORY_GENERAL;
		
    	ModConfig.filterRange = config.getInt("button_filter_range", category, 12, 1, 32, "Range of the filter button to reach nearby chests");
		ModConfig.showCharacter = config.getBoolean("show_character",category,true,"Show or hide the animated character text in the inventory");
		ModConfig.showSortButtons = config.getBoolean("move_inventory_buttons",category,true,"Show or hide the inventory shifting buttons << >>");
		ModConfig.showCornerButtons = config.getBoolean("show_corner_buttons",category,true,"Show or hide the corner inventory buttons in other GUI's");
		ModConfig.enderPearl64 = config.getBoolean("ender_pearl_64", category, true, "Stack to 64 instead of 16");
		ModConfig.showMergeDeposit = config.getBoolean("merge_deposit_buttons", category, true, "Show or hide the merge deposit buttons in upper right corner.");
		ModConfig.expPerBottle = config.getInt("exp_per_bottle", category, 10, 1, 11, "The exp cost of filling a single bottle.  Remember, the Bottle 'o Enchanting gives 3-11 experience when used, so it is never an exact two-way conversion.  ");
		  
		
		ModConfig.smallMedLarge = config.getString("small_med_large", category, "med", "Valid values are only exactly small/med/large.  WARNING: BACKUP YOUR WORLD BEFORE CHANGING THIS.  Changes your inventory size, for use if your GUI Scale requirements are different.");
		//todo validate/use/etc
		if(ModConfig.smallMedLarge == "large")//only place magics get used
		{
			 
	 //testing

			Const.MORE_ROWS = 15;
		 
			Const.MORE_COLS = 2*9;

			Const.texture_width = 464;
			Const.texture_height = 382;
		    Const.INVENTORY_TEXTURE = "textures/gui/inventory_18x27.png";//18x27
		}
		else if(ModConfig.smallMedLarge == "med")
		{

			Const.MORE_ROWS = 12;//texture 15x25
		 
			Const.MORE_COLS = 16;

			Const.texture_width = 464;
			Const.texture_height = 382;
		    Const.INVENTORY_TEXTURE = "textures/gui/inventory_15x25.png";
		}
		//12x18 is abandoned
		else//assume its small
		{
			Const.MORE_ROWS = 3;
		 
			Const.MORE_COLS = 9;

			Const.texture_width = 336;
			Const.texture_height = 241;
		    Const.INVENTORY_TEXTURE = "textures/gui/inventory_6x18.png";
		}
		
		
		if(config.hasChanged()){config.save();}
	}
    
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    	if(ModConfig.enderPearl64)
    	{
    		Items.ender_pearl.setMaxStackSize(64);
    	}
    }
}
