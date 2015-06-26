package com.lothrazar.samsmagic;

import java.util.ArrayList; 

import org.apache.logging.log4j.Logger;    

import com.lothrazar.samsmagic.potion.*; 
import com.lothrazar.samsmagic.entity.projectile.*; 
import com.lothrazar.samsmagic.item.ItemChestSack;
import com.lothrazar.samsmagic.proxy.*; 
import com.lothrazar.samsmagic.spell.*; 

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent; 
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
  
@Mod(modid = ModSpells.MODID,  useMetadata = true )  
public class ModSpells
{
	//TODO: DO NOT RESET TIMER IF CASTING FAILS
	public static final String MODID = "samsmagic";
	public static final String TEXTURE_LOCATION = MODID + ":"; 
	@Instance(value = ModSpells.MODID)
	public static ModSpells instance;
	@SidedProxy(clientSide="com.lothrazar.samsmagic.proxy.ClientProxy", serverSide="com.lothrazar.samsmagic.proxy.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	public static ConfigSpells cfg;
	public static SimpleNetworkWrapper network; 

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		cfg = new ConfigSpells(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( MODID );     	
    	
    	network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);

		ItemRegistry.registerItems();
		
		PotionRegistry.registerPotionEffects();


		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
		//MinecraftForge.TERRAIN_GEN_BUS.register(instance);
		//MinecraftForge.ORE_GEN_BUS.register(instance); 
		
		SpellRegistry.setup();
	}
        
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		 
  		//TODO: we could make our own custom projectileRegistry, that acts as our other ones above do.
  		
  		//TODO: Entity ids are the 999,1000,1001 -> config file
        //EntityRegistry.registerModEntity(EntitySoulstoneBolt.class, "soulstonebolt",999, ModSpells.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",1000, ModSpells.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(EntityHarvestbolt.class, "harvestbolt",1001, ModSpells.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",1002, ModSpells.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",1003, ModSpells.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",1004, ModSpells.instance, 64, 1, true);
		
		proxy.registerRenderers();
	}
 


	public static EntityItem dropItemStackInWorld(World worldObj, BlockPos pos, ItemStack stack)
	{
		EntityItem entityItem = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), stack); 

 		if(worldObj.isRemote  ==  false)//do not spawn a second 'ghost' one on client side
 		{
 			worldObj.spawnEntityInWorld(entityItem);
 		}
 		
    	return entityItem;
	}
	 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		if(event.pos == null){return;}
		IBlockState bstate = event.entityPlayer.worldObj.getBlockState(event.pos);
		if(bstate == null){return;}
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();
		//Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		//TileEntity container = event.world.getTileEntity(event.pos);

		if(held != null && held.getItem() == Items.experience_bottle  && 
				event.action.RIGHT_CLICK_BLOCK == event.action && 
				event.entityPlayer.capabilities.isCreativeMode == false && 
				ModSpells.cfg.experience_bottle_return)
		{ 
			dropItemStackInWorld(event.world, event.pos, new ItemStack(Items.glass_bottle));
		}
 
		if(held != null && held.getItem() == ItemRegistry.itemChestSack &&  //how to get this all into its own class
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{   
			if(event.face != null && event.world.isAirBlock(event.pos.offset(event.face)))
			{ 
				ItemChestSack.createAndFillChest(event.entityPlayer, held, event.pos.offset(event.face));
			}
		} 
   	}
  
	/*@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entityLiving != null && event.entityLiving.isPotionActive(PotionRegistry.ender))
		{
			event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts
		}
	}*/
	 
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
		 
        if(ClientProxy.keySpellToggle.isPressed())
        {
       		ModSpells.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellToggle.getKeyCode()));
        }
        else if(ClientProxy.keySpellCast.isPressed())
        {
       		ModSpells.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellCast.getKeyCode()));
        }
        else if(ClientProxy.keySpellUp.isPressed())
        {
       		ModSpells.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellUp.getKeyCode()));
        }
        else if(ClientProxy.keySpellDown.isPressed())
        {
       		ModSpells.network.sendToServer( new MessageKeyPressed(ClientProxy.keySpellDown.getKeyCode()));
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
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{  
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 
		PlayerPowerups props = PlayerPowerups.get(player);
		
		if(props.getSpellToggle() != SpellRegistry.SPELL_TOGGLE_HIDE)
		{
			drawSpell(event);

		 	drawHud(player); 
		}
	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) 
	{  
		if(event.entityLiving == null){return;}
		
		if(event.entityLiving instanceof EntityPlayer)
		{
			SpellGhost.onPlayerUpdate(event); 
			
			SpellRegistry.tickSpellTimer((EntityPlayer)event.entityLiving);
		}

		PotionRegistry.tickSlowfall(event);
	     
		PotionRegistry.tickWaterwalk(event);
	     
		//PotionRegistry.tickLavawalk(event);
 
		PotionRegistry.tickFrost(event); 
	}
	private static void renderItemAt(ItemStack stack, int x, int y, int dim)
	{
		//int height = dim, width = dim;

		@SuppressWarnings("deprecation")
		IBakedModel iBakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		@SuppressWarnings("deprecation")
		TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iBakedModel.getTexture().getIconName());
		
		renderTexture( textureAtlasSprite, x, y, dim);
	}
	public static void renderTexture( TextureAtlasSprite textureAtlasSprite , int x, int y, int dim)
	{	
		//special thanks to http://www.minecraftforge.net/forum/index.php?topic=26613.0
		
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.getInstance();

		int height = dim, width = dim;
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV((double)(x),          (double)(y + height),  0.0, (double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV((double)(x + width),  (double)(y + height),  0.0, (double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV((double)(x + width),  (double)(y),           0.0, (double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV());
		worldrenderer.addVertexWithUV((double)(x),          (double)(y),           0.0, (double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV());
		tessellator.draw();
		
	}
	private void drawSpell(RenderGameOverlayEvent.Text event)
	{ 
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 

		ISpell spell = SpellRegistry.getPlayerCurrentISpell(player);

		if(Minecraft.getMinecraft().gameSettings.showDebugInfo)
		{
			event.left.add(lang("key.spell."+spell.getSpellID()));
		}
		else
		{
			int ymain = 12;
			int dim = 12;
				
			int x = 12, y = 2;
			
			Item ptr = SpellRegistry.canPlayerCastAnything(player) ? ItemRegistry.exp_cost_dummy : ItemRegistry.exp_cost_empty_dummy;
			//spell.getIconDisplayHeader()
			renderItemAt(new ItemStack(ptr),x,y,dim);
				
			//int ysmall = ymain - 3;
			int xmain = 10;
			ymain = 14;
			if(spell.getIconDisplay() != null)
			{
				x = xmain; 
				y = ymain;
				dim = 16;
				renderItemAt(spell.getIconDisplay(),x,y,dim);
			}
			
			
			ISpell spellNext = spell.left();//SpellRegistry.getSpellFromType(spell.getSpellID().next());
			ISpell spellPrev = spell.right();//SpellRegistry.getSpellFromType(spell.getSpellID().prev());
			
			
			if(spellNext != null)// && spellNext.getIconDisplay() != null
			{
				x = xmain-3; 
				y = ymain + 16;
				dim = 16/2;
				renderItemAt(spellNext.getIconDisplay(),x,y,dim);
				
				ISpell sLeftLeft = spellNext.left();//SpellRegistry.getSpellFromType(spellNext.getSpellID().next());

				if(sLeftLeft != null && sLeftLeft.getIconDisplay() != null)
				{
					x = xmain-3 - 1; 
					y = ymain + 16+14;
					dim = 16/2 - 2;
					renderItemAt(sLeftLeft.getIconDisplay(),x,y,dim);
					
					ISpell another = sLeftLeft.left();
					if(another != null)
					{
						x = xmain-3 - 3; 
						y = ymain + 16+14+10;
						dim = 16/2 - 4;
						renderItemAt(another.getIconDisplay(),x,y,dim);
					}
				}
			}
			if(spellPrev != null)// && spellPrev.getIconDisplay() != null
			{
				x = xmain+6; 
				y = ymain + 16;
				dim = 16/2;
				renderItemAt(spellPrev.getIconDisplay(),x,y,dim);

				ISpell sRightRight = spellPrev.right();//SpellRegistry.getSpellFromType(spellPrev.getSpellID().prev());

				if(sRightRight != null && sRightRight.getIconDisplay() != null)
				{
					x = xmain+6 + 4; 
					y = ymain + 16+14;
					dim = 16/2 - 2;
					renderItemAt(sRightRight.getIconDisplay(),x,y,dim);
					
					ISpell another = sRightRight.right();
					if(another != null)
					{
						x = xmain+6 +7; 
						y = ymain + 16+14+10;
						dim = 16/2 - 4;
						renderItemAt(another.getIconDisplay(),x,y,dim);
					}
				}
			}
			
		
			
		}
	}

	private void drawHud(EntityPlayerSP player)
	{
		//TESTING OUT PLAYER COMPASS CLOCKS PELLS

		//int xMiddle = Minecraft.getMinecraft().displayWidth/4;
		//int yMiddle = Minecraft.getMinecraft().displayHeight/4;
		int yBottom = Minecraft.getMinecraft().displayHeight/2 - 32;
		int xRight = Minecraft.getMinecraft().displayWidth/2 - 32;
	 
		
		//PlayerPowerups props = PlayerPowerups.get(player);

		renderItemAt(new ItemStack(Items.clock),20,yBottom,16);//works at mid left
		renderItemAt(new ItemStack(Items.compass),xRight,yBottom,16);//works at mid top//was ,16
	
	}
	 
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
		ModSpells.network.sendToAll(new MessagePotion(position, particleID));
    	
		
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
		//float experience = player.experience;
	
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
