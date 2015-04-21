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
import net.minecraft.block.BlockChest;
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
import net.minecraft.entity.effect.EntityLightningBolt;
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
import net.minecraft.tileentity.TileEntityNote;
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
import net.minecraftforge.event.entity.player.EntityInteractEvent;
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
	public static ConfigFile cfg;
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
		
		cfg = new ConfigFile(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
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
		achievements.registerAll();
		
		CreativeInventoryRegistry.registerTabImprovements();
	
		MobSpawningRegistry.registerSpawns();
  
		ChestGen.regsiterLoot();
		  
		RecipeRegistry.registerRecipes();
		 
		StackSizeIncreaser.registerChanges(); 
 
  		if(ModSamsContent.cfg.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuel()); 
  		}
  		
  		if(ModSamsContent.cfg.worldGenOceansNotUgly)
		{ 
			GameRegistry.registerWorldGenerator(new WorldGeneratorOcean(), 0); //zero is Weight of generator
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
		if(ModSamsContent.cfg.searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(ModSamsContent.cfg.searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(ModSamsContent.cfg.searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(ModSamsContent.cfg.simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(ModSamsContent.cfg.todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(ModSamsContent.cfg.kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(ModSamsContent.cfg.home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(ModSamsContent.cfg.worldhome) 
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
		
		if(ModSamsContent.cfg.fragileTorches)  // && event.entityLiving != null
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
		BlockPos pos = event.entity.getPosition();
		World world = event.entity.worldObj;

		if(ModSamsContent.cfg.removeZombieCarrotPotato 
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
			
			EntityZombie z = (EntityZombie)event.entity;
			
			if(z.isChild())
			{
				
				int pct = ModSamsContent.cfg.chanceZombieChildFeather;
				if(event.entity.worldObj.rand.nextInt(100) <= pct)
				{
					event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ()
							,new ItemStack(Items.feather)));
				}
			}
			 
			if(z.isVillager())
			{
				int pct = ModSamsContent.cfg.chanceZombieVillagerEmerald;
				if(event.entity.worldObj.rand.nextInt(100) <= pct)
				{
					event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ() ,new ItemStack(Items.emerald)));
				}
			} 
		} 
		 
		if(ModSamsContent.cfg.petNametagDrops )//no need to restrict to pets && SamsUtilities.isPet(event.entity)
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{  
				//item stack NBT needs the name enchanted onto it
				ItemStack nameTag = SamsUtilities.buildEnchantedNametag(event.entity.getCustomNameTag());
			  
				SamsUtilities.dropItemStackInWorld(world, event.entity.getPosition(), nameTag);  
			}
		}
		
		if(ModSamsContent.cfg.petNametagChat && 
				event.entity instanceof EntityLiving )
		{ 
			if(event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
			   event.entity.getCustomNameTag() != ""   
			   ) 
			{    
	            //TODO: pet respawning block/spot with nametags
				//show message as if player, works since EntityLiving extends EntityLivingBase
				 SamsUtilities.printChatMessage((event.source.getDeathMessage((EntityLiving)event.entity)));
			}
		}
		/*
		if(SamsUtilities.isLivestock(event.entity))
		{ 
			if(event.source.getSourceOfDamage() != null 
					&& event.source.getSourceOfDamage() instanceof EntityPlayer 
					&& cfg.livestockLootMultiplier > 0) 
			{ 
				//if livestock is killed by a palyer, then multiply the loot by the scale factor
				for(EntityItem ei : event.drops)
				{  
					//the stack size does not seem to be mutable  so we just get and set the stack with a new size 
					int newdrops = ei.getEntityItem().stackSize * cfg.livestockLootMultiplier;
					
					//do not exceed max stack size.  Example: if a sword drops, do not make it a 2stack
					newdrops = Math.min(newdrops, ei.getEntityItem().getMaxStackSize());
					
					ei.setEntityItemStack(new ItemStack(ei.getEntityItem().getItem(), newdrops,ei.getEntityItem().getItemDamage()));
				}
			} 
		}  */
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		
		if(held != null && held.getItem() == ItemRegistry.respawn_egg_empty )
		{
			ItemRespawnEggEmpty.entitySpawnEgg(event.entityPlayer, event.target); 
		}
		  
		if(ModSamsContent.cfg.canNameVillagers && 
		  held != null && held.getItem() == Items.name_tag && held.hasDisplayName())
		{    
			if(event.target instanceof EntityVillager)
			{
				EntityVillager v = (EntityVillager)event.entity;
				 
				v.setCustomNameTag(held.getDisplayName()); 
				
				SamsUtilities.decrHeldStackSize(event.entityPlayer);
			} 
		} 
  
		if(held != null && held.getItem() instanceof ItemHorseFood)
		{     
			if(event.target instanceof EntityHorse)
			{ 
				ItemHorseFood.onHorseInteract((EntityHorse)event.target,event.entityPlayer,held);  
				
				event.setCanceled(true);//stop the GUI inventory opening
			}  
		}  
  	} 
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		TileEntity container = event.world.getTileEntity(event.pos);
	  
		if(held != null && held.getItem() == ItemRegistry.wandWater )
		{
			ItemWandWater.cast(event);
		}
		
		if(held != null && held.getItem() == ItemRegistry.fire_charge_throw && 
				event.action.RIGHT_CLICK_AIR == event.action)
		{   
			ItemFireballThrowable.cast(event.world,event.entityPlayer );   
		}
/*
		if(held != null && held.getItem() == ItemRegistry.wandProspect && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			ItemWandProspect.searchProspect(event.entityPlayer,held,event.pos);   
		}
*/
		if(held != null && held.getItem() == ItemRegistry.wandTransform && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			ItemWandTransform.transformBlock(event.entityPlayer, event.world, held, event.pos); 
		}
		
		if(held != null && held.getItem() == ItemRegistry.frozen_snowball && 
				event.action.RIGHT_CLICK_AIR == event.action)
		{  
			ItemSnowballFrozen.cast(event.world,event.entityPlayer );  
		}
		
		if(held != null && held.getItem() == ItemRegistry.harvest_charge && 
				event.action.RIGHT_CLICK_BLOCK == event.action    )
		{ 
			ItemMagicHarvester.replantField(event.world,event.entityPlayer,held,event.pos); 
		}
		
		if(held != null && held.getItem() == ItemRegistry.lightning_charge )
		{     
			ItemLightning.cast(event); 
		} 
	 
		if(held != null && held.getItem() == ItemRegistry.carbon_paper &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemPaperCarbon.rightClickBlock(event); 
		}
		
		if(held != null && held.getItem() == ItemRegistry.wandBuilding)
		{
			if(event.action.LEFT_CLICK_BLOCK == event.action  )
			{ 
				ItemWandBuilding.onPlayerLeftClick(event);
			}
			else
			{
				
				ItemWandBuilding.onPlayerRightClick(event);
			}
		}
		
		if(held != null && held.getItem() == ItemRegistry.itemChestSack && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			if(blockClicked instanceof BlockChest)// && event.entityPlayer.isSneaking()
			{    
				if(container instanceof TileEntityChest)
				{
					ItemChestSackEmpty.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
				}
			} 
		}
		
		if(held != null && ItemRegistry.itemChestSack != null && 
				held.getItem() == ItemRegistry.itemChestSack && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			if(blockClicked == Blocks.chest)
			{ 
				TileEntityChest chest = (TileEntityChest)event.entityPlayer.worldObj.getTileEntity(event.pos.up()); 
					   
				TileEntityChest teAdjacent = SamsUtilities.getChestAdj(chest); 
				
		  		ItemChestSack.sortFromSackToChestEntity(chest,held,event);
		  		
		  		if(teAdjacent != null)
		  		{
		  			ItemChestSack.sortFromSackToChestEntity(teAdjacent,held,event); 
		  		} 	
			}
			else
			{
				BlockPos chestPos;
				if(event.face != null) chestPos = event.pos.offset(event.face);
				else chestPos = event.pos.up();
				//if the up one is air, then build a chest at this spot 
				if(event.entityPlayer.worldObj.isAirBlock(chestPos)) 
				{
					ItemChestSack.createAndFillChest(event.entityPlayer,held,  chestPos);
				} 
			}
		}  
		
		if (held != null && 
			held.getItem() != null && 
			ItemRegistry.itemEnderBook != null &&
			held.getItem() == ItemRegistry.itemEnderBook && 
			event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemEnderBook.rightClickBlock(event.world,event.entityPlayer, held);
			
		}
		
		if(event.action == event.action.LEFT_CLICK_BLOCK && 
			ModSamsContent.cfg.smartEnderchest && 
			event.entityPlayer.getCurrentEquippedItem() != null && 
			event.entityPlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.ender_chest))
		{
			event.entityPlayer.displayGUIChest(event.entityPlayer.getInventoryEnderChest()); 
		} 
		
		if(ModSamsContent.cfg.swiftDeposit  && 
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
		
		if(//&&
  				event.action == event.action.RIGHT_CLICK_BLOCK && 
  				SamsUtilities.isBonemeal(held)  && 
  				blockClicked != null ) 
		{    
			BonemealExt.useBonemeal(event.world, event.entityPlayer, event.pos, blockClicked);
		}
		
		if(ModSamsContent.cfg.flintPumpkin && 
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
		
		if(ModSamsContent.cfg.skullSignNames && 
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking() && 
				held != null && held.getItem() == Items.skull && 
				held.getItemDamage() == Reference.skull_player	&& 
				container != null &&
				container instanceof TileEntitySign)
		{
			TileEntitySign sign = (TileEntitySign)container; 
			String firstLine = sign.signText[0].getUnformattedText();
			
			if(firstLine == null) { firstLine = ""; }
			if(firstLine.isEmpty() || firstLine.split(" ").length == 0)
			{
				held.setTagCompound(null); 
			}
			else
			{ 
				firstLine = firstLine.split(" ")[0];
				
				if(held.getTagCompound() == null) held.setTagCompound(new NBTTagCompound());
				
				held.getTagCompound().setString("SkullOwner",firstLine);
			} 
		} //end of skullSignNames
   	}
	
	@SubscribeEvent
	public void onHoeUse(UseHoeEvent event)
	{  
		//this fires BEFORE the block turns into farmland (is cancellable) so check for grass and dirt, not farmland
		
		Block clicked = event.world.getBlockState(event.pos).getBlock();
		
		if( (clicked == Blocks.grass || clicked == Blocks.dirt ) 
			&& event.world.isAirBlock(event.pos.up()) 
			&& ItemRegistry.beetroot_seed != null
			&& event.current.getItem() == Items.golden_hoe  
			&& event.world.rand.nextInt(16) == 0)
		{			
			if(event.world.isRemote == false)
				SamsUtilities.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetroot_seed);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{     
		EntityPlayer player = event.player;
		ItemStack held = player.getCurrentEquippedItem(); 
		
		if(held != null && 
				Item.getIdFromItem(held.getItem()) == Item.getIdFromItem(ItemRegistry.wandBuilding) ) 
		{
			ItemWandBuilding.setCompoundIfNull(held);
			
			ItemWandBuilding.tickTimeout(held); 
		}
		
		  //   why isnt this in potionregistry
		if( player.isPotionActive(PotionRegistry.ender) && //the potion gives us this safe(ish)falling
			 	 player.dimension == Reference.Dimension.end && //hence the name of the class
				 player.posY < -50 && 
				// player.worldObj.isRemote  == false && 
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
		if( ModSamsContent.cfg.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
 
			IBlockState bs = mob.func_175489_ck();//mcp/forge just did not translate this
			
			if(bs != null && bs.getBlock() != null && event.entity.worldObj.isRemote == false)
			{
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModSamsContent.cfg.dropPlayerSkullOnDeath)
			{  
				ItemStack skull = SamsUtilities.buildNamedPlayerSkull(player);
				 
				SamsUtilities.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModSamsContent.cfg.playerDeathCoordinates)
			{
				String coordsStr = SamsUtilities.posToString(player.getPosition()); 
				SamsUtilities.printChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
		}
	}
}
