package com.lothrazar.powerinventory;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import com.lothrazar.powerinventory.inventory.InventoryPersistProperty;
import com.lothrazar.powerinventory.inventory.client.GuiBigInventory;
import com.lothrazar.powerinventory.inventory.client.GuiButtonClose; 
import com.lothrazar.powerinventory.inventory.client.GuiButtonOpenInventory; 
import com.lothrazar.powerinventory.proxy.ClientProxy;
import com.lothrazar.powerinventory.proxy.EnderPearlPacket; 

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class EventHandler
{
	public static File worldDir;
	public static HashMap<String, Integer> unlockCache = new HashMap<String, Integer>();
	public static HashMap<String, Container> lastOpened = new HashMap<String, Container>();
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyEnder.isPressed() )
        { 	     
        	 ModInv.instance.network.sendToServer( new EnderPearlPacket());   
        }  
    }
	
	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event) // More reliable than on entity join
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(InventoryPersistProperty.get(player) == null)
			{
				InventoryPersistProperty.register(player);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(InventoryPersistProperty.get(player) != null)
			{
				InventoryPersistProperty.get(player).onJoinWorld();
			} 
		}
	}
 
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			if(!event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
			{
				InventoryPersistProperty.keepInvoCache.put(event.entityLiving.getUniqueID(), ((EntityPlayer)event.entityLiving).inventory.writeToNBT(new NBTTagList()));
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
		if(event.gui != null && event.gui.getClass() == GuiInventory.class && !(event.gui instanceof GuiBigInventory))
		{
			event.gui = new GuiBigInventory(Minecraft.getMinecraft().thePlayer);
		}
	}


	
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiPostInit(InitGuiEvent.Post event)
	{
		if(event.gui == null){return;}//probably doesnt ever happen
		
		if(event.gui instanceof net.minecraft.client.gui.inventory.GuiChest || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiDispenser || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiBrewingStand || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiBeacon || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiCrafting || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiFurnace || 
		   event.gui instanceof net.minecraft.client.gui.inventory.GuiScreenHorseInventory
		   )
		{
			//trapped, regular chests, minecart chests, and enderchest all use this class
			//which extends  GuiContainer

			int padding = 10;
			
			int x,y = padding,w = 20,h = w;
			
			x = Minecraft.getMinecraft().displayWidth/2 - w - padding;//align to right side
			
			event.buttonList.add(new GuiButtonClose(256, x,y,w,h));
			
			x = x - padding - w;
			event.buttonList.add(new GuiButtonOpenInventory(256, x,y,w,h,"I",Const.INV_PLAYER));
		}
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
		if(!event.world.isRemote && worldDir == null && MinecraftServer.getServer().isServerRunning())
		{
			MinecraftServer server = MinecraftServer.getServer();
			
			if(ModInv.proxy.isClient())
			{
				worldDir = server.getFile("saves/" + server.getFolderName());
			} 
			else
			{
				worldDir = server.getFile(server.getFolderName());
			}

			new File(worldDir, "data/").mkdirs();
			LoadCache(new File(worldDir, "data/SlotUnlockCache"));
		}
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
		if(!event.world.isRemote && worldDir != null && MinecraftServer.getServer().isServerRunning())
		{
			new File(worldDir, "data/").mkdirs();
			SaveCache(new File(worldDir, "data/SlotUnlockCache"));
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if(!event.world.isRemote && worldDir != null && !MinecraftServer.getServer().isServerRunning())
		{
			new File(worldDir, "data/").mkdirs();
			SaveCache(new File(worldDir, "data/SlotUnlockCache"));
			
			worldDir = null;
			unlockCache.clear();
			InventoryPersistProperty.keepInvoCache.clear();
		}
	}
	
	public static void SaveCache(File file)
	{
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(unlockCache);
			
			oos.close();
			fos.close();
		} 
		catch(Exception e)
		{
			ModInv.logger.log(Level.ERROR, "Failed to save slot unlock cache", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void LoadCache(File file)
	{
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileInputStream fis = new FileInputStream(file);
			
			if(fis.available() <= 0)
			{
				fis.close();
				return;
			}
			
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			unlockCache = (HashMap<String,Integer>)ois.readObject();
			
			ois.close();
			fis.close();
		} catch(Exception e)
		{
			ModInv.logger.log(Level.ERROR, "Failed to load slot unlock cache", e);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
		//System.out.println("EH RenderGameOverlayEventRenderGameOverlayEvent");
		//http://www.minecraftforge.net/wiki/Gui_Overlay
		onRenderExperienceBar(event);
    }
	  private static final int BUFF_ICON_SIZE = 18;
	  private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; // 2 pixels between buff icons
	  private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	  private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	  private static final int BUFF_ICONS_PER_ROW = 8;
	@SideOnly(Side.CLIENT)
	 public void onRenderExperienceBar(RenderGameOverlayEvent event)
	  {
		//  System.out.println("onRenderExperienceBar "+event.isCancelable());
	    // 
	    // We draw after the ExperienceBar has drawn.  The event raised by GuiIngameForge.pre()
	    // will return true from isCancelable.  If you call event.setCanceled(true) in
	    // that case, the portion of rendering which this event represents will be canceled.
	    // We want to draw *after* the experience bar is drawn, so we make sure isCancelable() returns
	    // false and that the eventType represents the ExperienceBar event.
	    if(event.isCancelable() || event.type != ElementType.EXPERIENCE){return;}

	    Minecraft mc = Minecraft.getMinecraft();
	    // Starting position for the buff bar - 2 pixels from the top left corner.
	    int xPos = 22;
	    int yPos = 22;
	    Collection<PotionEffect> collection = ( Collection<PotionEffect> )mc.thePlayer.getActivePotionEffects();
	    if (!collection.isEmpty())
	    {
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glDisable(GL11.GL_LIGHTING);      
	    //  this.mc.renderEngine.bindTexture("/gui/inventory.png");      

	      for (Iterator iterator = mc.thePlayer.getActivePotionEffects()
	          .iterator(); iterator.hasNext(); xPos += BUFF_ICON_SPACING)
	      {
	        PotionEffect potioneffect = (PotionEffect) iterator.next();
	        Potion potion = Potion.potionTypes[potioneffect.getPotionID()];

	  	  System.out.println(potion.getName());
	        if (potion.hasStatusIcon())
	        {
	          int iconIndex = potion.getStatusIconIndex();
	    
	        
	          if(mc.currentScreen != null)
	          {
	        	  System.out.println(xPos+" "+yPos );
		         mc.currentScreen.drawTexturedModalRect(
		              xPos, yPos, 
		              BUFF_ICON_BASE_U_OFFSET + iconIndex % BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE, BUFF_ICON_BASE_V_OFFSET + iconIndex / BUFF_ICONS_PER_ROW * BUFF_ICON_SIZE,
		              BUFF_ICON_SIZE, BUFF_ICON_SIZE);
		         
		         
	          }
	        /*  else
	          {
	        	  System.out.println("null");
	          }*/
	        }       
	      }
	    }
	  }
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;  
	
		if(player.isSneaking() && 
			player.worldObj.isRemote == true)//client side only -> possibly redundant because of SideOnly
		{
			int size = 16;
			
			int xLeft = 20;
			int xRight = Minecraft.getMinecraft().displayWidth/2 - size*2;
			int yBottom = Minecraft.getMinecraft().displayHeight/2 - size*2;

			if(player.inventory.getStackInSlot(Const.BONUS_START+Const.type_clock) != null)
				renderItemAt(new ItemStack(Items.clock),xLeft,yBottom,size);
			
			if(player.inventory.getStackInSlot(Const.BONUS_START+Const.type_compass) != null)
				renderItemAt(new ItemStack(Items.compass),xRight,yBottom,size);
 
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void renderItemAt(ItemStack stack, int x, int y, int dim)
	{
		@SuppressWarnings("deprecation")
		IBakedModel iBakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		@SuppressWarnings("deprecation")
		TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iBakedModel.getTexture().getIconName());
		
		renderTexture( textureAtlasSprite, x, y, dim);
	}
	@SideOnly(Side.CLIENT)
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
