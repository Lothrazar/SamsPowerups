package com.lothrazar.samscontent;

import java.util.ArrayList; 
import org.apache.logging.log4j.Logger;   
import com.lothrazar.samscontent.block.*;
import com.lothrazar.samscontent.cfg.ConfigRegistry; 
import com.lothrazar.samscontent.common.PlayerPowerups; 
import com.lothrazar.samscontent.event.*;
import com.lothrazar.samscontent.item.*;
import com.lothrazar.samscontent.potion.*; 
import com.lothrazar.samscontent.proxy.*;  
import com.lothrazar.samscontent.stats.*;
import com.lothrazar.samscontent.tileentity.TileEntityBucketStorage; 
import com.lothrazar.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = Reference.MODID, version = Reference.VERSION,	name = Reference.NAME, useMetadata = true )  
public class ModMain
{
	@Instance(value = Reference.MODID)
	public static ModMain instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	public static ConfigRegistry cfg;
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
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
		//PotionRegistry.registerPotionEffects();

		BlockRegistry.registerBlocks();
		
		ItemRegistry.registerItems();
		
		ArmorRegistry.registerItems();
		
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
   
		RecipeRegistry.registerRecipes();
		 
		StackSizeRegistry.registerChanges(); 
 
  		if(ModMain.cfg.moreFuel) 
  		{
  			GameRegistry.registerFuelHandler(new FurnaceFuelRegistry()); 
  		}
   
		proxy.registerRenderers();
	}
	 
	private void registerEventHandlers() 
	{ 
    	ArrayList<Object> handlers = new ArrayList<Object>();
  
      	handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      	handlers.add(new DebugScreenText()          );  //This one can stay  
     	handlers.add(instance                         ); 
     	handlers.add(achievements);  
		handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
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
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		//PotionRegistry.onEntityUpdate(event);
		
		//TODO: make class/event handler for fragile torches
		if(ModMain.cfg.fragileTorches && 
				event.entityLiving.worldObj.getBlockState(event.entityLiving.getPosition()).getBlock() == Blocks.torch) 
		{ 
			float oddsWillBreak = 0.01F;//TODO: in config or something? or make this 1/100
			boolean playerCancelled = false;
			if(event.entityLiving instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer)event.entityLiving;
				if(p.isSneaking())
				{
					playerCancelled = true;//torches are safe from breaking
				}
			}
			
			if(playerCancelled == false //if its a player, then the player is not sneaking
					&& event.entityLiving.worldObj.rand.nextDouble() < oddsWillBreak
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
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		
		if(held != null && held.getItem() == ItemRegistry.respawn_egg_empty )
		{
			ItemRespawnEggEmpty.entitySpawnEgg(event.entityPlayer, event.target); 
		}
		  
		if(ModMain.cfg.canNameVillagers &&  //how to get this all into its own class
		  held != null && held.getItem() == Items.name_tag && 
		  held.hasDisplayName()  )
		{    
			if(event.target instanceof EntityVillager)
			{
				
				EntityVillager v = (EntityVillager)event.target;
				  
				v.setCustomNameTag(held.getDisplayName()); 
				
				Util.decrHeldStackSize(event.entityPlayer); 
				
				event.setCanceled(true);//stop the GUI inventory opening 
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

		if(held != null && held.getItem() == Items.experience_bottle  && 
				event.action.RIGHT_CLICK_BLOCK == event.action && 
				event.entityPlayer.capabilities.isCreativeMode == false && 
				ModMain.cfg.experience_bottle_return)
		{ 
			Util.dropItemStackInWorld(event.world, event.pos, Items.glass_bottle);
		}
		
		if(held != null && held.getItem() == ItemRegistry.carbon_paper &&   
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			ItemPaperCarbon.rightClickBlock(event); 
		}
	
		if(held != null && held.getItem() == ItemRegistry.itemChestSack &&  //how to get this all into its own class
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			/*
			if(blockClicked instanceof BlockChest && container instanceof TileEntityChest)// && event.entityPlayer.isSneaking()
			{    
				 
				ItemChestSackEmpty.convertChestToSack(event.entityPlayer,held,(TileEntityChest)container,event.pos);  
			 
				//TODO: other containers could go here: dispenser, trapped chest. 
				//heck maybe thers another way such as with IInventory?
				
			} 
			else
			{
				*/
				if(event.face != null && event.world.isAirBlock(event.pos.offset(event.face)) == false)
					ItemChestSack.createAndFillChest(event.entityPlayer, held, event.pos.offset(event.face));
			//}
		}
	
		
		if(ModMain.cfg.swiftDeposit  &&  //how to get this all into its own class
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
		  	    TileEntityChest teAdjacent = Util.getChestAdj(chest);
		  		if(teAdjacent != null)
		  		{
		  			ChestDeposit.sortFromPlayerToChestEntity(event.world,teAdjacent,event.entityPlayer);
		  		}
	  	  	}
		}
		
		if(  event.action == event.action.RIGHT_CLICK_BLOCK && 
  				Util.isBonemeal(held)  && 
  				blockClicked != null ) 
		{    
			BonemealExt.useBonemeal(event.world, event.entityPlayer, event.pos, blockClicked);
		}
		
		if(ModMain.cfg.flintPumpkin &&  //how to get this all into its own class
				held != null && held.getItem() == Items.flint_and_steel && 
				event.action.RIGHT_CLICK_BLOCK == event.action )
		{   
			if(blockClicked == Blocks.pumpkin)
			{
				event.world.setBlockState(event.pos, Blocks.lit_pumpkin.getDefaultState());
				 
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
			
				Util.playSoundAt(event.entityPlayer, "fire.ignite"); 
			}
			else if(blockClicked == Blocks.lit_pumpkin)//then un-light it
			{
				event.world.setBlockState(event.pos, Blocks.pumpkin.getDefaultState());
				 
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos);
				Util.spawnParticle(event.world, EnumParticleTypes.FLAME, event.pos.offset(event.entityPlayer.getHorizontalFacing()));
				
				Util.playSoundAt(event.entityPlayer, "random.fizz"); 
			}
		}		
		
		if(ModMain.cfg.skullSignNames &&  //how to get this all into its own class
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
			&& event.world.rand.nextInt(16) == 0) //it is a 1/15 chance
		{			
			if(event.world.isRemote == false)
			{
				Util.dropItemStackInWorld(event.world, event.pos, ItemRegistry.beetroot_seed);
			}

			event.entityPlayer.addStat(achievements.beetrootSeed, 1);
		}
	}

	@SubscribeEvent
	public void onPlayerWakeUpEvent(PlayerWakeUpEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote == false)
		{
			boolean didSleepAllNight = !event.updateWorld;
			
			if(didSleepAllNight && ModMain.cfg.sleeping_hunger_seconds > 0)
			{ 
				int levelBoost = 0;//1 means Hunger II. int = 2 means Hunger III, etc.
				
				event.entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id,  ModMain.cfg.sleeping_hunger_seconds * Reference.TICKS_PER_SEC, levelBoost));
			}
		}
	}
	/*
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entityLiving != null && event.entityLiving.isPotionActive(PotionRegistry.ender))
		{
			event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts
		}
	}*/
	  
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( ModMain.cfg.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
 
			IBlockState bs = mob.func_175489_ck();//mcp/forge just did not translate this
			
			if(bs != null && bs.getBlock() != null && event.entity.worldObj.isRemote == false)
			{
				Util.dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModMain.cfg.dropPlayerSkullOnDeath)
			{  
				ItemStack skull = Util.buildNamedPlayerSkull(player);
				 
				Util.dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModMain.cfg.playerDeathCoordinates)
			{
				String coordsStr = Util.posToString(player.getPosition()); 
				Util.addChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		PlayerPowerups.get(event.entityPlayer).copy(PlayerPowerups.get(event.original));
	}
	
	@SubscribeEvent
 	public void onEntityConstructing(EntityConstructing event)
 	{ 
 		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
 		{ 
 			PlayerPowerups.register((EntityPlayer) event.entity);
 		} 
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
			
			Util.setItemStackNBT(stack, "buckets", t.getBuckets());
		
			Util.dropItemStackInWorld(event.world, event.pos, stack);

			t.setBuckets(0);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{ 
		if(event.entity instanceof EntityLivingBase && event.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase)event.entity;
		
			if(living instanceof EntityWolf && ((EntityWolf)living).isTamed())
			{
				Util.setMaxHealth(living,ModMain.cfg.heartsWolfTamed*2);
			}
			if(living instanceof EntityOcelot && ((EntityOcelot)living).isTamed())
			{
				Util.setMaxHealth(living,ModMain.cfg.heartsCatTamed*2);
			}
			
			if(living instanceof EntityVillager && ((EntityVillager)living).isChild() == false)
			{
				Util.setMaxHealth(living,ModMain.cfg.heartsVillager*2);			
			}
		}
	}
	
	/*
	 old code; a few unused events: put back in if needed
	  
	@EventHandler 
	public void onPostInit(FMLPostInitializationEvent event)
	{ 		
	
	}
	@SubscribeEvent
	public void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event)
	{
		
	}
	@SubscribeEvent
	public void onHarvestDropsEvent(HarvestDropsEvent event)
	{
		
	}
	
	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event)
	{
		//every time the player joins the world
		//Util.addChatMessage(event.player, "login.new.first");

	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{     
		EntityPlayer player = event.player;
		
		//this one only applies to players
		PotionRegistry.tickEnder(player); 
	} */
	
}
