package com.lothrazar.powerinventory.inventory.client;

import com.lothrazar.powerinventory.Const; 
import com.lothrazar.powerinventory.ModConfig;
import com.lothrazar.powerinventory.inventory.BigContainerPlayer;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
 
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class GuiBigInventory extends GuiInventory
{
	private BigContainerPlayer container;
	private EntityPlayer player;


	GuiButton btnEnder;
	GuiButton btnExp;
	GuiButton[] btnPages = new GuiButton[Const.PAGES];
	GuiButton btnMoveLeft;
	GuiButton btnMoveRight;
	public GuiBigInventory(EntityPlayer p)
	{
		super(p);
		container = p.inventoryContainer instanceof BigContainerPlayer? (BigContainerPlayer)p.inventoryContainer : null;
		player=p;
		this.xSize = Const.textureWidth();
		this.ySize = Const.textureHeight();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
    { 
		super.initGui();
		
		if(this.container != null && this.mc.playerController.isInCreativeMode() == false)
		{
			final int height = 20;
			final int width = 26;
			final int widthlrg = 58;
			final int padding = 6;
			//final int tiny = 12;
			int button_id = 99;
			
			this.buttonList.add(new GuiButtonDump(button_id++,
					this.guiLeft + this.xSize - widthlrg - padding, 
					this.guiTop + padding,
					widthlrg,height));

			this.buttonList.add(new GuiButtonFilter(button_id++,
					this.guiLeft + this.xSize - widthlrg - 2*padding - widthlrg, 
					this.guiTop + padding,
					widthlrg,height));

			btnEnder = new GuiButtonOpenInventory(button_id++, 
					this.guiLeft + container.echestX + 19, 
					this.guiTop + container.echestY-1,
					12,height
					, "",Const.INV_ENDER);
			
			this.buttonList.add(btnEnder); 
			btnEnder.enabled = false;// turn it on based on ender chest present or not

			btnExp = new GuiButtonExp(button_id++, 
					this.guiLeft + container.bottleX - width - padding+1, 
					this.guiTop + container.bottleY-2,
					width,height,StatCollector.translateToLocal("button.exp"));
			this.buttonList.add(btnExp);
			btnExp.enabled = false;
			
			int x_spacing = width + padding/2;
			int x = guiLeft + this.xSize -  4*x_spacing - padding+1;
			int y = guiTop + this.ySize - height - padding        -22;
		
	 
			btnMoveLeft = new GuiButtonSort(button_id++, x, y ,width,height, Const.SORT_LEFTALL,"<<");
			this.buttonList.add(btnMoveLeft); 
			x += x_spacing;
			btnMoveRight = new GuiButtonSort(button_id++, x, y ,width,height, Const.SORT_RIGHTALL,">>");
			this.buttonList.add(btnMoveRight);

			x_spacing = width + padding/2;
			x = guiLeft + this.xSize -  4*x_spacing - padding+1;
			y = this.guiTop + container.bottleY-2;//guiTop + this.ySize - height - padding        -22;
			
			for(int pg = 0; pg < Const.PAGES;pg++)
			{
				btnPages[pg] = new GuiButtonPage(button_id++, x, y,width,height, pg,""+pg);
				this.buttonList.add(btnPages[pg]);

				x += x_spacing;
			} 
		}
    }

	private void updateButtons()
	{
		final int s = 16;
 
		if(container.invo.getStackInSlot(Const.BONUS_START+Const.type_echest) == null)
		{
			btnEnder.enabled = false;
 
			drawTextureSimple("textures/items/empty_enderchest.png",container.echestX, container.echestY,s,s); 
		}
		else 
		{ 
			btnEnder.enabled = true; 
		}
		
		if(container.invo.getStackInSlot(Const.BONUS_START+Const.type_bottle) == null || 
		   container.invo.getStackInSlot(Const.BONUS_START+Const.type_bottle).getItem() == Items.experience_bottle	)
		{
			btnExp.enabled = false;
  
			drawTextureSimple("textures/items/empty_bottle.png",container.bottleX, container.bottleY,s,s); 
		}
		else 
		{ 
			btnExp.enabled = true; 
		}

		if(container.invo.getStackInSlot(Const.BONUS_START+Const.type_epearl) == null)
		{  
			drawTextureSimple("textures/items/empty_enderpearl.png",container.pearlX, container.pearlY,s,s);
		}

		if(container.invo.getStackInSlot(Const.BONUS_START+Const.type_compass) == null)
		{ 
			drawTextureSimple("textures/items/empty_compass.png",container.compassX, container.compassY,s,s);
		}

		if(container.invo.getStackInSlot(Const.BONUS_START+Const.type_clock) == null)
		{  
			drawTextureSimple("textures/items/empty_clock.png",container.clockX, container.clockY,s,s);
		}
		
		int pg = this.container.invo.getCurrentPage();
		btnPages[pg].enabled = false;
		/*
		if(pg <= 0)
		{
			btnpgLeft.displayString = "";
			btnpgLeft.enabled=false;
		}
		else
		{
			btnpgLeft.displayString = ""+(pg-1);
			btnpgLeft.enabled=true;
		}
		if(pg >= Const.PAGES-1)
		{
			btnpgRight.displayString = "";
			btnpgRight.enabled=false;
		}
		else
		{
			btnpgRight.displayString = ""+(pg+1);
			btnpgRight.enabled=true;
		}*/
	}
	 
	public void drawTextureSimple(String texture,double x, double y, double width, double height)
	{
		//wrapper for drawTexturedQuadFit
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, texture)); 
		drawTexturedQuadFit(x,y,width,height,0);
	}
	public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel)
	{
		//because the vanilla code REQUIRES textures to be powers of two AND are force dto be max of 256??? WHAT?
		//so this one actually works
		//THANKS hydroflame  ON FORUMS 
		//http://www.minecraftforge.net/forum/index.php/topic,11229.msg57594.html#msg57594
		
		Tessellator tessellator = Tessellator.getInstance();
  
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        
        worldrenderer.addVertexWithUV(x + 0, y + height, zLevel, 0,1);
        worldrenderer.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
        worldrenderer.addVertexWithUV(x + width, y + 0, zLevel, 1,0);
        worldrenderer.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, Const.getInventoryTexture()));

        drawTexturedQuadFit(this.guiLeft, this.guiTop,this.xSize,this.ySize,0);
      
        if(ModConfig.showCharacter)
        	drawEntityOnScreen(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{ 
		this.updateButtons();
		
		if(ModConfig.showText)
			this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 87, 32, 4210752);

		
		 /*

	 
		Slot s;
		 
		for(Object o : this.container.inventorySlots)
		{
			//vanilla code does not declare ArrayList<Slot>, even though every object in there really is one
			s = (Slot)o;
	 
			//each slot has two different numbers. the slotNumber is UNIQUE, the index is not
	 
		if(s.slotNumber >= 10 && s.slotNumber < 10 +Const.HOTBAR_SIZE/2)
				this.drawString(this.fontRendererObj, "" + (s.getSlotIndex()+1), s.xDisplayPosition+1, s.yDisplayPosition +  4, 1210752);
		//	else//this is debug mode now
			//	this.drawString(this.fontRendererObj, "" + s.getSlotIndex(), s.xDisplayPosition, s.yDisplayPosition +  4, 16777120);
		}*/ 
	}
}
