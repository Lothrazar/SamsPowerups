package com.lothrazar.samsmagic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList; 

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;    

import com.lothrazar.samsmagic.potion.*; 
import com.lothrazar.samsmagic.entity.projectile.*; 
import com.lothrazar.samsmagic.item.ItemChestSack;
import com.lothrazar.samsmagic.proxy.*; 
import com.lothrazar.samsmagic.spell.*; 

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
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = ModMain.MODID, version = ModMain.VERSION,	name = ModMain.NAME, useMetadata = true )  
public class ModMain
{
	public static final String MODID = "samsmagic";
	public static final String TEXTURE_LOCATION = MODID + ":";
	public static final String VERSION = "1.8-1.4.0";
	public static final String NAME = "Builder's Powerups";
	@Instance(value = ModMain.MODID)
	public static ModMain instance;
	@SidedProxy(clientSide="com.lothrazar.samscontent.proxy.ClientProxy", serverSide="com.lothrazar.samscontent.proxy.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	//public static ConfigRegistry cfg;
	public static SimpleNetworkWrapper network; 
	/*public static AchievementRegistry achievements;  
	
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsContent") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.apple_chocolate;
		}
	};    
	*/
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		//cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( MODID );     	
    	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 		
		PotionRegistry.registerPotionEffects();

		ItemRegistry.registerItems();
		
		this.registerEventHandlers(); 
		
		SpellRegistry.setup();
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		 
  		//TODO: we could make our own custom projectileRegistry, that acts as our other ones above do.
  		
  		//TODO: Entity ids are the 999,1000,1001 -> config file
        EntityRegistry.registerModEntity(EntitySoulstoneBolt.class, "soulstonebolt",999, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",1000, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHarvestbolt.class, "harvestbolt",1001, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",1002, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",1003, ModMain.instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",1004, ModMain.instance, 64, 1, true);
		
		proxy.registerRenderers();
	}
	
	private void registerEventHandlers() 
	{ 
    	ArrayList<Object> handlers = new ArrayList<Object>();
  
      	//handlers.add(new SaplingDespawnGrowth());//this is only one needs terrain gen buff, plus one of the regular ones
      	//handlers.add(new DebugScreenText()          );  //This one can stay  
     	handlers.add(instance                         ); 
     	/*
     	handlers.add(achievements);  
		handlers.add(BlockRegistry.block_storelava    );//TODO: why are these four done so weirdly
		handlers.add(BlockRegistry.block_storewater   );
		handlers.add(BlockRegistry.block_storemilk    ); 
		handlers.add(BlockRegistry.block_storeempty   );   
*/
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
	public void onLivingHurt(LivingHurtEvent event) 
	{ 
		SpellSoulstone.onLivingHurt(event);
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
 
	
		if(held != null && held.getItem() == ItemRegistry.itemChestSack &&  //how to get this all into its own class
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
	 
				if(event.face != null && event.world.isAirBlock(event.pos.offset(event.face)) == false)
				{
					ItemChestSack.createAndFillChest(event.entityPlayer, held, event.pos.offset(event.face));
				}
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
	
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entityLiving != null && event.entityLiving.isPotionActive(PotionRegistry.ender))
		{
			event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts
		}
	}
	 
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
		 
        if(ClientProxy.keySpellToggle.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellToggle.getKeyCode()));
        }
        else if(ClientProxy.keySpellCast.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellCast.getKeyCode()));
        }
        else if(ClientProxy.keySpellUp.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellUp.getKeyCode()));
        }
        else if(ClientProxy.keySpellDown.isPressed())
        {
       		ModMain.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellDown.getKeyCode()));
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

	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}

	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
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
	public static void spawnParticlePacketByID(BlockPos position, int particleID)
	{
		//this. fires only on server side. so send packet for client to spawn particles and so on
		ModMain.network.sendToAll(new MessagePotion(position, particleID));
    	
		
	}
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}
	//samsmagic
	

	public static double getExpTotal(EntityPlayer player)
	{
		int level = player.experienceLevel;
		
		//this is the exp between previous and last level, not the real total
		float experience = player.experience;
	
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		double totalExp = getXpForLevel(level);
 
		//so now we knwo how much was used to get to current level

		//double nextLevelExp = getXpToGainLevel(level);
 
		double progress = Math.round(player.xpBarCap() * player.experience);

		totalExp += (int)progress;
		
		return totalExp;
	}

	public static boolean drainExp(EntityPlayer player, float f) 
	{  
		double totalExp = getExpTotal(player);
  
		//System.out.println("Drain from total = "+totalExp+" - "+f);
		
		if(totalExp - f < 0)
		{
			return false;
		}
		
		setXp(player, (int)(totalExp - f));
		
		return true;
		
	}
	
	public static int getXpToGainLevel(int level)
	{
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		//so if our current level is 5, we pass in5 here and find out
		//how much exp to get from 5 to 6
		int nextLevelExp = 0;

		if(level <= 15)
			nextLevelExp = 2*level + 7;
		else if(level <= 30)
			nextLevelExp = 5*level - 38;
		else //level >= 31 
			nextLevelExp = 9*level - 158;
		
		return nextLevelExp;
	}
	public static int getXpForLevel(int level)
	{
		//numeric reference: http://minecraft.gamepedia.com/Experience#Leveling_up
		int totalExp = 0;
		
		if(level <= 15)
			totalExp = level*level + 6*level;
		else if(level <= 30)
			totalExp = (int)(2.5*level*level - 40.5*level + 360);
		else //level >= 31 
			totalExp = (int)(4.5*level*level - 162.5*level + 2220);//fixed. was +162... by mistake
		
		return totalExp;
	}
	public static int getLevelForXp(int xp) 
	{
		int lev = 0;
		while (getXpForLevel(lev) < xp) 
		{
			lev++;
		}
		return lev - 1;
	}
	public static void setXp(EntityPlayer player, int xp)
	{
		player.experienceTotal = xp;
		player.experienceLevel = getLevelForXp(xp);
		int next = getXpForLevel(player.experienceLevel);
 
		player.experience = (float)(player.experienceTotal - next) / (float)player.xpBarCap(); 
		 
	}

	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		moveEntityWallSafe(player, world);
	}
	
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	public static void addOrMergePotionEffect(EntityPlayer player, PotionEffect newp)
	{
		if(player.isPotionActive(newp.getPotionID()))
		{
			//do not use built in 'combine' function, just add up duration myself
			PotionEffect p = player.getActivePotionEffect(Potion.potionTypes[newp.getPotionID()]);
			
			int ampMax = Math.max(p.getAmplifier(), newp.getAmplifier());
		
			player.addPotionEffect(new PotionEffect(newp.getPotionID()
					,newp.getDuration()+p.getDuration()
					,ampMax));
		}
		else
		{
			player.addPotionEffect(newp);
		}
	}
	public static String posToStringCSV(BlockPos position) 
	{ 
		return position.getX() + ","+position.getY()+","+position.getZ();
	} 
	public static void incrementPlayerIntegerNBT(EntityPlayer player, String prop, int inc)
	{
		int prev = player.getEntityData().getInteger(prop);
		prev += inc; 
		player.getEntityData().setInteger(prop, prev);
	}
	
}
