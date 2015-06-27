package com.lothrazar.samsapples;

import org.apache.logging.log4j.Logger;    

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
  
@Mod(modid = ModApples.MODID, useMetadata=true)  
public class ModApples
{
	public static final String MODID = "samsapples";
	public static final String TEXTURE_LOCATION = MODID + ":";
	//public static final String VERSION = "1.8-1.0.0";
	//public static final String NAME = "Sam's Apples";, version = ModApples.VERSION,	name = ModApples.NAME, useMetadata = true 

	@Instance(value = MODID)
	public static ModApples instance;
	@SidedProxy(clientSide="com.lothrazar.samsapples.ClientProxy", serverSide="com.lothrazar.samsapples.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	//public static ConfigRegistry cfg;
	//public static SimpleNetworkWrapper network; 
	//public static AchievementRegistry achievements;  
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsApples") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.apple_chocolate;
		}
	};    
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
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		logger = event.getModLog();  
		
		//cfg = new ConfigRegistry(new Configuration(event.getSuggestedConfigurationFile()));
	  
    	//network = NetworkRegistry.INSTANCE.newSimpleChannel( Reference.MODID );     	
    	
    	//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	//network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 	

		PotionRegistry.registerPotionEffects();
		ItemRegistry.registerItems();
 
  	    FMLCommonHandler.instance().bus().register(instance); 
  	    MinecraftForge.EVENT_BUS.register(instance); 
  	   
	}
	@SubscribeEvent
	public void onEnderTeleportEvent(EnderTeleportEvent event)
	{  
		if(event.entityLiving != null && event.entityLiving.isPotionActive(PotionRegistry.ender))
		{
			event.attackDamage = 0;  //starts at exactly  5.0 which is 2.5hearts

			if(event.entityLiving.worldObj.isRemote == false)//do not spawn a second 'ghost' one on client side
			{
				EntityItem entityItem = new EntityItem(event.entityLiving.worldObj, event.targetX,event.targetY,event.targetZ, new ItemStack(Items.ender_pearl)); 
				event.entityLiving.worldObj.spawnEntityInWorld(entityItem);
			}
		}
	}
 
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		proxy.registerRenderers();
	}
	
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		//World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 
 
		if(player.isPotionActive(PotionRegistry.nav_id) && 
				Minecraft.getMinecraft().gameSettings.showDebugInfo == false)
		{
			int size = 16;
			
			int xLeft = 20;
			int xRight = Minecraft.getMinecraft().displayWidth/2 - size*2;
			int yBottom = Minecraft.getMinecraft().displayHeight/2 - size*2;

			renderItemAt(new ItemStack(Items.clock),xLeft,yBottom,size);//works at mid left
			renderItemAt(new ItemStack(Items.compass),xRight,yBottom,size);//works at mid top//was ,16
		}
	}
	
	private static void renderItemAt(ItemStack stack, int x, int y, int dim)
	{
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
}
