package com.lothrazar.pagedinventory.inventory.client;

import com.lothrazar.pagedinventory.proxy.SortButtonPacket;

import com.lothrazar.pagedinventory.ModInv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class GuiButtonSort extends GuiButton 
{ 
	private int sortType;
    public GuiButtonSort(int buttonId, int x, int y, int w,int h,  int sort, String text)
    {
    	super(buttonId, x, y, w,h, text ); 
    	sortType = sort;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{ 
    		NBTTagCompound tags = new NBTTagCompound();
  
    		tags.setInteger(SortButtonPacket.NBT_SORT, sortType);
    		ModInv.instance.network.sendToServer(new SortButtonPacket(tags));
    		
    		
    
    	}
    	
    	return pressed;
    }
}

