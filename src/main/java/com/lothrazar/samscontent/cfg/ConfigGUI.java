package com.lothrazar.samscontent.cfg;

  
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiMessageDialog;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;

public class ConfigGUI extends GuiConfig 
{ 
	//http://jabelarminecraft.blogspot.ca/p/minecraft-modding-configuration-guis.html
	
    public ConfigGUI(GuiScreen parent) 
    {
    	//                                i never use  Configuration.CATEGORY_GENERAL
    	
    	

//this seems to mostly work, but its based on beta unfinished forge code so i am not going to worry about
    	//perfecting or testing this until they sort that out on the other end.
    	//QUOTE: " It works mostly, although there is some not finished parts."
    	//ref http://www.minecraftforge.net/forum/index.php?topic=28735.0
    	
    	//Things we need to make this actually useable
    	
    	//multiple categories
    	
    	//those flags down there that require world restart / mc restart: they need to be
    	//individual to each config variable, not just the whole category.
    	//
    	
        super(parent,
                new ConfigElement(ModMain.cfg.instance().getCategory(ModMain.cfg.GUI_CATEGORY)).getChildElements(),
                Reference.MODID, 
                false,// boolean allRequireWorldRestart,
                false,// boolean allRequireMcRestart
                "Sam's Content controls"////this is the title at top of screen.
               , GuiConfig.getAbridgedConfigPath(ModMain.cfg.instance().toString())
                );

       // titleLine2 = ModMain.cfg.instance().getAbsolutePath();
    } 
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        // You can do things like create animations, draw additional elements, etc. here

    	super.drawScreen(mouseX, mouseY, partialTicks);
    }
    private static final int BTN_DONE = 2000;
    @Override
    protected void actionPerformed(GuiButton button)
    {
    	if (button.id == BTN_DONE)
        {
    		 boolean flag = true;
             try
             {
                 if (  (      configID != null 
                       || parentScreen == null 
                       || !(parentScreen instanceof GuiConfig)
                       ) 
                       && (entryList.hasChangedEntry(true)))
                 {
              
 
                     boolean requiresMcRestart = entryList.saveConfigElements();

                     if (Loader.isModLoaded(modID))
                     {
                    	 
                         ConfigChangedEvent event = new ConfigChangedEvent(modID, 
                               configID, isWorldRunning, requiresMcRestart);
                         FMLCommonHandler.instance().bus().post(event);
                         /*
                          //well, Result is not found... and PostConfigChanged is not found...so...
                         if (!event.getResult().equals(Result.DENY))
                             FMLCommonHandler.instance().bus().post(new PostConfigChangedEvent(modID, 
                                   configID, isWorldRunning, requiresMcRestart));
                        */
                         
                         if (requiresMcRestart)
                         {
                             flag = false;
                             mc.displayGuiScreen(new GuiMessageDialog(parentScreen, 
                                   "fml.configgui.gameRestartTitle", 
                                   new ChatComponentText(I18n.format("fml.configgui.gameRestartRequired")), 
                                         "fml.configgui.confirmRestartMessage"));
                         }
                         
                         if (parentScreen instanceof GuiConfig)
                             ((GuiConfig) parentScreen).needsRefresh = true;
                     }
                 }
             }
             catch (Throwable e)
             {
//System.out.println("Exception..........");
                 e.printStackTrace();
             }
             
             if (flag)
                 mc.displayGuiScreen(parentScreen);
        } 
    }
}