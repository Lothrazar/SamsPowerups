package com.lothrazar.samscontent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList; 

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;   

import com.lothrazar.samscontent.block.*;
import com.lothrazar.samscontent.cfg.ConfigFile;
import com.lothrazar.samscontent.command.*;
import com.lothrazar.samscontent.event.*;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.potion.*; 
import com.lothrazar.samscontent.proxy.*; 
import com.lothrazar.samscontent.stats.*;
import com.lothrazar.samscontent.world.*; 
import com.lothrazar.util.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = Reference.MODID, version = Reference.VERSION	, canBeDeactivated = false, name = Reference.NAME, useMetadata = true ,guiFactory = "com.lothrazar.samscontent.cfg.ConfigGuiFactory") 
public class ModSamsContent
{
	@Instance(value = Reference.MODID)
	public static ModSamsContent instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	
	public static Logger logger; 
	public static ConfigFile configSettings;
	public static SimpleNetworkWrapper network; 
	public static AchievementRegistry achievements; 
	
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsContent") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.apple_chocolate;
		}
	};
		
	private void initModInfo(ModMetadata mcinfo)
	{ 
		mcinfo.modId = Reference.MODID;
		mcinfo.name = Reference.NAME;
		mcinfo.version = Reference.VERSION;
		mcinfo.description = "Sam's content.";
		ArrayList<String> authorList = new ArrayList<String>();
		authorList.add("Lothrazar");
		mcinfo.authorList = authorList;
	}
 
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		initModInfo(event.getModMetadata());
		
		configSettings = new ConfigFile(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, 0, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, 0, Side.CLIENT);
//new one here
		
		PotionRegistry.registerPotionEffects();

		BlockRegistry.registerBlocks();
		
		ItemRegistry.registerItems();
		
		achievements = new AchievementRegistry();
		 
		this.registerEventHandlers(); 
		
		BlockHardnessRegistry.registerChanges(); 
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		//TODO: LexManos et all have not yet fixed IVillageTradeHandler
	//	VillageTrading v = new VillageTrading(); 
        //VillagerRegistry.instance().registerVillageTradeHandler(1, v);
      //  VillagerRegistry.instance().registerVillageTradeHandler(2, v);
        
		achievements.registerAll();
		
		CreativeInventoryTweaks.registerTabImprovements();
	
		MobSpawningRegistry.registerSpawns();
  
		ChestGen.regsiterLoot();
		  
		RecipeRegistry.registerRecipes();
		 
		StackSizeIncreaser.registerChanges(); 
 
  		if(ModSamsContent.configSettings.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
  		}
  		
  		if(ModSamsContent.configSettings.worldGenClayOceans)
		{ 
			GameRegistry.registerWorldGenerator(new WorldGenClay(), 0); //zero is Weight of generator
		}

		proxy.registerRenderers();
	}
	
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(ModSamsContent.configSettings.searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModSamsContent.configSettings.searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModSamsContent.configSettings.searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModSamsContent.configSettings.simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModSamsContent.configSettings.todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModSamsContent.configSettings.kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModSamsContent.configSettings.home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModSamsContent.configSettings.worldhome) 
			event.registerServerCommand(new CommandHome());
	}
  
	private void registerEventHandlers() 
	{
		//TODO: version checker
		//FMLInterModComms.sendRuntimeMessage(MODID, "VersionChecker", "addVersionCheck", "http://www.lothrazar.net/api/mc/samscontent/version.json");
		 
    	ArrayList<Object> handlers = new ArrayList<Object>();
 
     	  
      	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      	handlers.add(new DebugScreenText()          );  //This one can stay  
     	handlers.add(instance                         ); 
     	handlers.add(achievements); 
		handlers.add(ItemRegistry.itemEnderBook       );
		handlers.add(ItemRegistry.wandTransform       );
		handlers.add(ItemRegistry.itemChestSack       );
		handlers.add(ItemRegistry.wandBuilding        );
		handlers.add(ItemRegistry.wandChest           );
		handlers.add(ItemRegistry.wandCopy            );
		handlers.add(ItemRegistry.wandFire            );
		handlers.add(ItemRegistry.wandFireball        );
		handlers.add(ItemRegistry.wandSnowball        );
		handlers.add(ItemRegistry.wandHarvest         );
		handlers.add(ItemRegistry.wandLivestock       );
		handlers.add(ItemRegistry.wandProspect        );
		handlers.add(ItemRegistry.wandTransform       );
		handlers.add(ItemRegistry.wandWater           );
		handlers.add(ItemRegistry.wandLightning       );
		handlers.add(BlockRegistry.block_storelava    );
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   

     	for(Object h : handlers)
     		if(h != null)
	     	{ 
	    		FMLCommonHandler.instance().bus().register(h);
	    		MinecraftForge.EVENT_BUS.register(h);
	    		MinecraftForge.TERRAIN_GEN_BUS.register(h);
	    		MinecraftForge.ORE_GEN_BUS.register(h); 
	     	} 
	}
	
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{ 
		PotionRegistry.onEnderTeleportEvent(event); 
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		PotionRegistry.onEntityUpdate(event);
		
		if(ModSamsContent.configSettings.fragileTorches)  // && event.entityLiving != null
		{ 
			boolean playerCancelled = false;
			if(event.entityLiving instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer)event.entityLiving;
				if(p.isSneaking())
				{
					playerCancelled = true;//torches are safe from breaking
				}
			}
			
			if(playerCancelled == false 
					&& event.entityLiving.worldObj.getBlockState(event.entityLiving.getPosition()).getBlock() == Blocks.torch
					&& event.entityLiving.worldObj.rand.nextDouble() < 0.01
					&& event.entityLiving.worldObj.isRemote == false)
			{ 
				event.entityLiving.worldObj.destroyBlock(event.entityLiving.getPosition(), true);  
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{

		if(ModSamsContent.configSettings.removeZombieCarrotPotato 
		  && event.entity instanceof EntityZombie)
		{  
			for(int i = 0; i < event.drops.size(); i++) 
			{
				EntityItem item = event.drops.get(i);
				
				if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
				{ 
					event.drops.remove(i);
				}
			}
			//TODO: child zombie feathers %
			//TODO: villaer emeralds %
		} 
		
		if(event.entity.worldObj.isRemote) {return;}
		
		if(ModSamsContent.configSettings.petNametagDrops && 
				SamsUtilities.isPet(event.entity) )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{ 
				ItemStack nameTag = new ItemStack(Items.name_tag, 1); 
				 
				//build multi-level NBT tag so it matches a freshly enchanted one
				NBTTagCompound nbt = new NBTTagCompound(); 
				NBTTagCompound display = new NBTTagCompound();
				display.setString("Name", event.entity.getCustomNameTag());//NOT "CustomName" implied by commandblocks/google 
				nbt.setTag("display",display);
				nbt.setInteger("RepairCost", 1);
				
				nameTag.setTagCompound(nbt);//put the data into the item stack
				 
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, event.entity.getPosition(), nameTag); 
			}
		}
		if(ModSamsContent.configSettings.petNametagChat && 
				event.entity instanceof EntityLiving )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{    
				 SamsUtilities.printChatMessage(
						 (event.source.getDeathMessage((EntityLiving)event.entity)));
			}
		}
		
		if(SamsUtilities.isLivestock(event.entity))
		{ 
			if(event.source.getSourceOfDamage() != null 
					&& event.source.getSourceOfDamage() instanceof EntityPlayer 
					&& configSettings.livestockLootScaleFactor > 0) 
			{ 
				//if livestock is killed by a palyer, then multiply the loot by the scale factor
				for(EntityItem ei : event.drops)
				{ 
					//the stack size does not seem to be mutable
					//so we just get and set the stack with a new size
					
					//double it again for pigs
					int factor = (event.entity instanceof EntityPig) ? 2 * configSettings.livestockLootScaleFactor : configSettings.livestockLootScaleFactor;
					
					ei.setEntityItemStack(new ItemStack(ei.getEntityItem().getItem(),ei.getEntityItem().stackSize * factor,ei.getEntityItem().getItemDamage()));
				}
			}
			else
			{  
				event.drops.clear();////nope, was not killed by playerso the cow/whatever drops nothing.
			}
		} 
		
		
	}
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		TileEntity maybesign = event.world.getTileEntity(event.pos);
		Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		//TODO: a way to Name Villagers with name tags (is there a vanilla way)
    //i can use entityInteractEvent, detect name tag and then cancel the event
        //       then the entity has a 'setCustomNameTag' function

		
		if (held != null && 
			held.getItem() != null && 
			ItemRegistry.itemEnderBook != null &&
			held.getItem() == ItemRegistry.itemEnderBook && 
			event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			if(event.entityPlayer.isSneaking())
			{ 			 
				ItemEnderBook.saveCurrentLocation(event.world,event.entityPlayer, held);		 
			} 
			else
			{ 
				ItemEnderBook.teleport(event.world,event.entityPlayer, held);	
			} 
		}
		
		if(event.action == event.action.LEFT_CLICK_BLOCK && 
			ModSamsContent.configSettings.smartEnderchest && 
			event.entityPlayer.getCurrentEquippedItem() != null && 
			event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest))
		{
			event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest()); 
		} 
		
		if(ModSamsContent.configSettings.swiftDeposit  && 
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking()  && 
				event.entityPlayer.getCurrentEquippedItem() == null)
		{ 
	  	  	TileEntity te =	event.entity.worldObj.getTileEntity(event.pos);
	  
	  	  	if(te != null && (te instanceof TileEntityChest))
	  	  	{ 
				TileEntityChest chest = (TileEntityChest)te ; 
				 
		  		ChestDeposit.sortFromPlayerToChestEntity(event.world,chest,event.entityPlayer);

		  	  	//check for double chest 
		  	    TileEntityChest teAdjacent = SamsUtilities.getChestAdj(chest);
		  		if(teAdjacent != null)
		  		{
		  			ChestDeposit.sortFromPlayerToChestEntity(event.world,teAdjacent,event.entityPlayer);
		  		}
	  	  	}
		}
		
		if(ModSamsContent.configSettings.betterBonemeal 
  				&& event.action != event.action.LEFT_CLICK_BLOCK 
  				&& SamsUtilities.isBonemeal(held)  && 
  				blockClicked != null ) 
		{    
			BonemealExt.useBonemeal(event.world, event.entityPlayer, event.pos, blockClicked);
		}
		
		if(ModSamsContent.configSettings.flintPumpkin && 
				held != null && held.getItem() == Items.flint_and_steel && 
				event.action.RIGHT_CLICK_BLOCK == event.action )
		{   
			if(blockClicked == Blocks.pumpkin)
			{
				event.world.setBlockState(event.pos, Blocks.lit_pumpkin.getDefaultState());
				 
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
			
				SamsUtilities.playSoundAt(event.entityPlayer, "fire.ignite"); 
			}
			else if(blockClicked == Blocks.lit_pumpkin)//then un-light it
			{
				event.world.setBlockState(event.pos, Blocks.pumpkin.getDefaultState());
				 
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				SamsUtilities.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
				
				SamsUtilities.playSoundAt(event.entityPlayer, "random.fizz"); 
			}
		}		
		
		if(ModSamsContent.configSettings.skullSignNames && 
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking() && 
				held != null && held.getItem() == Items.skull && 
				held.getItemDamage() == Reference.skull_player	&& 
				maybesign != null &&
				maybesign instanceof TileEntitySign)
		{
			TileEntitySign sign = (TileEntitySign)maybesign; 
			String firstLine = sign.signText[0].getUnformattedText();
			
			if(firstLine == null) { firstLine = ""; }
			if(firstLine.isEmpty() || firstLine.split(" ").length == 0)
			{
				held.setTagCompound(null); 
			}
			else
			{
				//get the first word
				firstLine = firstLine.split(" ")[0];
				
				if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
				
				held.getTagCompound().setString("SkullOwner",firstLine);
			} 
		} //end of skullSignNames
   	}
	
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{ 
		if(ModSamsContent.configSettings.beetroot == false){return;}
		if(event.world.isRemote){return;}
		if(event.world.isAirBlock(event.pos.up()) == false){return;}
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.current.getItem() == Items.golden_hoe  //TODO:  maybe also in config
			&& event.world.rand.nextInt(16) == 0) //pocket edition vanilla is 1/16
		{					
			SamsUtilities.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetrootSeed);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{     
		EntityPlayer player = event.player;
		  //TODO: why isnt this in potionregistry
		if( player.isPotionActive(PotionRegistry.ender) && //the potion gives us this safe(ish)falling
			 	 player.dimension == Reference.Dimension.end && //hence the name of the class
				 player.posY < -50 && 
				 player.worldObj.isRemote  == false && 
				 player.capabilities.isCreativeMode == false
				)
		{  
			SamsUtilities.teleportWallSafe(player, player.worldObj, player.getPosition().up(256)); 
		} 
	} 
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	     
        	 ModSamsContent.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }        
        else if(ClientProxy.keyShiftDown.isPressed() )
        { 	      
        	 ModSamsContent.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }      
        else if(ClientProxy.keyBarDown.isPressed() )
        { 	      
        	 ModSamsContent.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarDown.getKeyCode()));  
        }  
        else if(ClientProxy.keyBarUp.isPressed() )
        { 	      
        	 ModSamsContent.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarUp.getKeyCode()));  
        }  
    } 
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( ModSamsContent.configSettings.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
 
			IBlockState bs = mob.func_175489_ck();
			
			if(bs != null && bs.getBlock() != null && event.entity.worldObj.isRemote == false)
			{
				//drop it on server side
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModSamsContent.configSettings.dropPlayerSkullOnDeath)
			{ 
				ItemStack skull =  new ItemStack(Items.skull,1,Reference.skull_player);
				if(skull.getTagCompound() == null) skull.setTagCompound(new NBTTagCompound()); 
				skull.getTagCompound().setString("SkullOwner",player.getDisplayNameString());
				
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModSamsContent.configSettings.playerDeathCoordinates)
			{
				String coordsStr = SamsUtilities.posToString(player.getPosition()); 
				SamsUtilities.printChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
		}
	}
}
