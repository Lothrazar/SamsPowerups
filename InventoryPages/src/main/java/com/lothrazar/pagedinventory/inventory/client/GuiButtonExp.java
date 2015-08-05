package com.lothrazar.pagedinventory.inventory.client;

import com.lothrazar.pagedinventory.proxy.ExpButtonPacket; 
import com.lothrazar.pagedinventory.ModInv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class GuiButtonExp extends GuiButton 
{  
    public GuiButtonExp(int buttonId, int x, int y, int w,int h,  String text)
    {
    	super(buttonId, x, y, w,h, text );  
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{   
    		ModInv.instance.network.sendToServer(new ExpButtonPacket(new NBTTagCompound()));
    	}
    	
    	return pressed;
    }
}

