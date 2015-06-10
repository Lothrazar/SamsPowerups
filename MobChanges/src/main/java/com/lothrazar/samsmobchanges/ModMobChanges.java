package com.lothrazar.samsmobchanges;
 
import org.apache.logging.log4j.Logger;   
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
  
@Mod(modid = ModMobChanges.MODID, useMetadata = true )  
public class ModMobChanges
{
	public static final String MODID = "samsmobchanges";
	public static final String TEXTURE_LOCATION = ModMobChanges.MODID + ":";
	@Instance(value = ModMobChanges.MODID)
	public static ModMobChanges instance;
  
	public static Logger logger; 
	public static ConfigRegistry cfg;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
 
		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
		MinecraftForge.TERRAIN_GEN_BUS.register(instance);
		MinecraftForge.ORE_GEN_BUS.register(instance); 
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		MobSpawningRegistry.registerSpawns();
	}
	 
	@SubscribeEvent
	public void onLivingDropsEvent(LivingDropsEvent event)
	{
		BlockPos pos = event.entity.getPosition();
		World world = event.entity.worldObj;

		if( event.entity instanceof EntityZombie) //how to get this all into its own class
		{  
			EntityZombie z = (EntityZombie)event.entity;
			
			if(ModMobChanges.cfg.removeZombieCarrotPotato)
				for(int i = 0; i < event.drops.size(); i++) 
				{
					EntityItem item = event.drops.get(i);
					
					if(item.getEntityItem().getItem() == Items.carrot || item.getEntityItem().getItem() == Items.potato)
					{ 
						event.drops.remove(i);
					}
				}
	
			if(z.isChild() && ModMobChanges.cfg.chanceZombieChildFeather > 0 && 
					event.entity.worldObj.rand.nextInt(100) <= ModMobChanges.cfg.chanceZombieChildFeather)
			{ 
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ()
						,new ItemStack(Items.feather)));
			}
			 
			if(z.isVillager() && ModMobChanges.cfg.chanceZombieVillagerEmerald > 0
					&& event.entity.worldObj.rand.nextInt(100) <=  ModMobChanges.cfg.chanceZombieVillagerEmerald)
			{
				event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ() 
						,new ItemStack(Items.emerald)));
			} 
		} 
		 
		if(ModMobChanges.cfg.petNametagDrops //no need to restrict to pets && SamsUtilities.isPet(event.entity)
		  && event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		   event.entity.getCustomNameTag() != ""   
		   ) 
		{  
			//item stack NBT needs the name enchanted onto it
			ItemStack nameTag = buildEnchantedNametag(event.entity.getCustomNameTag());
		  
			dropItemStackInWorld(world, event.entity.getPosition(), nameTag);  
		}
		
		if(ModMobChanges.cfg.petNametagChat && 
			event.entity instanceof EntityLivingBase && 
			event.entity.getCustomNameTag() != null && //'custom' is blank if no nametag
		    event.entity.getCustomNameTag() != ""   &&
		    event.entity.worldObj.isRemote == false) 
		{    
			//show message as if player, works since EntityLiving extends EntityLivingBase
	 
			addChatMessage((event.source.getDeathMessage((EntityLivingBase)event.entity)));
		}
		 
		if(ModMobChanges.cfg.cowExtraLeather > 0 && event.entity instanceof EntityCow)
		{
			event.drops.add(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(), new ItemStack(Items.leather,ModMobChanges.cfg.cowExtraLeather)));
		} 
	}
	
	@SubscribeEvent
	public void onEntityInteractEvent(EntityInteractEvent event)
  	{
		ItemStack held = event.entityPlayer.getCurrentEquippedItem(); 
		/*
		if(held != null && held.getItem() == ItemRegistry.respawn_egg_empty )
		{
			ItemRespawnEggEmpty.entitySpawnEgg(event.entityPlayer, event.target); 
		}*/
		  
		if(ModMobChanges.cfg.canNameVillagers &&  //how to get this all into its own class
		  held != null && held.getItem() == Items.name_tag && 
		  held.hasDisplayName()  )
		{    
			if(event.target instanceof EntityVillager)
			{
				
				EntityVillager v = (EntityVillager)event.target;
				  
				v.setCustomNameTag(held.getDisplayName()); 
				
				if (event.entityPlayer.capabilities.isCreativeMode == false)
		        {
					event.entityPlayer.inventory.decrStackSize(event.entityPlayer.inventory.currentItem, 1);
		        }
				
				event.setCanceled(true);//stop the GUI inventory opening 
			} 
		} 
  	} 
	 /*
	  //TODO: standalone for this?
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		if(event.pos == null){return;}
		IBlockState bstate = event.entityPlayer.worldObj.getBlockState(event.pos);
		if(bstate == null){return;}
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();

		TileEntity container = event.world.getTileEntity(event.pos);
 
		if(ModMobChanges.cfg.skullSignNames &&  //how to get this all into its own class
				event.action == event.action.LEFT_CLICK_BLOCK && 
				event.entityPlayer.isSneaking() && 
				held != null && held.getItem() == Items.skull && 
				held.getItemDamage() == skull_player	&& 
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
   	*/
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
	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	public static final int skull_skeleton = 0;	
	public static final int skull_wither = 1;
	public static final int skull_zombie = 2;
	public static final int skull_player = 3;
	public static final int skull_creeper = 4;
	
	/*
	 //TODO: this should be in standalone , but what goes with it?
	@SubscribeEvent
	public void onPlayerWakeUpEvent(PlayerWakeUpEvent event)
	{
		if(event.entityPlayer.worldObj.isRemote == false)
		{
			boolean didSleepAllNight = !event.updateWorld;
			
			if(didSleepAllNight && ModMobChanges.cfg.sleeping_hunger_seconds > 0)
			{ 
				int levelBoost = 0;//1 means Hunger II. int = 2 means Hunger III, etc.
				
				event.entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id,  ModMobChanges.cfg.sleeping_hunger_seconds * 20, levelBoost));
			}
		}
	}*/

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if( ModMobChanges.cfg.endermenDropCarryingBlock
			&& event.entity instanceof EntityEnderman)
		{ 
			EntityEnderman mob = (EntityEnderman)event.entity;
 
			IBlockState bs = mob.func_175489_ck();//mcp/forge just did not translate this
			
			if(bs != null && bs.getBlock() != null && event.entity.worldObj.isRemote == false)
			{
				dropItemStackInWorld(event.entity.worldObj, mob.getPosition(), bs.getBlock());
			} 
		}
		
		if(event.entity instanceof EntityPlayer)
		{ 
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(ModMobChanges.cfg.dropPlayerSkullOnDeath)
			{  
				ItemStack skull = buildNamedPlayerSkull(player);
				 
				dropItemStackInWorld(event.entity.worldObj, player.getPosition(), skull);
			}
			
			if(ModMobChanges.cfg.playerDeathCoordinates)
			{
				String coordsStr = posToString(player.getPosition()); 
				addChatMessage(player.getDisplayNameString() + " has died at " + coordsStr);
			}
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
	public static String posToString(BlockPos position) 
	{ 
		return "["+ position.getX() + ", "+position.getY()+", "+position.getZ()+"]";
	} 

	public static ItemStack buildNamedPlayerSkull(EntityPlayer player) 
	{
		return buildNamedPlayerSkull(player.getDisplayNameString());
	}
	public static ItemStack buildNamedPlayerSkull(String displayNameString) 
	{
		ItemStack skull =  new ItemStack(Items.skull,1,skull_player);

		if(skull.getTagCompound() == null) {skull.setTagCompound(new NBTTagCompound());}
		
		skull.getTagCompound().setString("SkullOwner",displayNameString);
		
		return skull; 
	}
	public static void addChatMessage(String string) 
	{ 
		addChatMessage(new ChatComponentTranslation(string)); 
	}
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}
	public static void addChatMessage(IChatComponent string) 
	{ 
		 Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(string); 
	}
	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent event)
	{ 
		if(event.entity instanceof EntityLivingBase && event.world.isRemote)
		{
			EntityLivingBase living = (EntityLivingBase)event.entity;
		
			if(living instanceof EntityWolf && ((EntityWolf)living).isTamed())
			{
				setMaxHealth(living,ModMobChanges.cfg.heartsWolfTamed*2);
			}
			if(living instanceof EntityOcelot && ((EntityOcelot)living).isTamed())
			{
				setMaxHealth(living,ModMobChanges.cfg.heartsCatTamed*2);
			}
			
			if(living instanceof EntityVillager && ((EntityVillager)living).isChild() == false)
			{
				setMaxHealth(living,ModMobChanges.cfg.heartsVillager*2);			
			}
		}
	}

	public static void setMaxHealth(EntityLivingBase living,int max)
	{	
		living.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(max);
	}
	public static ItemStack buildEnchantedNametag(String customNameTag) 
	{
		//build multi-level NBT tag so it matches a freshly enchanted one
		
		ItemStack nameTag = new ItemStack(Items.name_tag, 1); 
		  
		NBTTagCompound nbt = new NBTTagCompound(); 
		NBTTagCompound display = new NBTTagCompound();
		display.setString("Name", customNameTag);//NOT "CustomName" implied by commandblocks/google 
		nbt.setTag("display",display);
		nbt.setInteger("RepairCost", 1);
		
		nameTag.setTagCompound(nbt);//put the data into the item stack
		 
		return nameTag;
	}
}
