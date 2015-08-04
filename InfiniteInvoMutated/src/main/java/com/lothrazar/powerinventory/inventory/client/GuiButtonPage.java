package com.lothrazar.powerinventory.inventory.client;

import com.lothrazar.powerinventory.proxy.PageButtonPacket;
import com.lothrazar.powerinventory.proxy.SortButtonPacket;
import com.lothrazar.powerinventory.ModInv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class GuiButtonPage extends GuiButton 
{ 
	private int page;
    public GuiButtonPage(int buttonId, int x, int y, int w,int h,  int p, String text)
    {
    	super(buttonId, x, y, w,h, text ); 
    	page = p;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{ 
    		NBTTagCompound tags = new NBTTagCompound();
  
    		tags.setInteger(PageButtonPacket.NBT_PAGE, page);
    		ModInv.instance.network.sendToServer(new PageButtonPacket(tags));
    		
    		
    
    	}
    	
    	return pressed;
    }
}

