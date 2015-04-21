package com.lothrazar.samscontent.cfg;

  
import com.lothrazar.samscontent.ModMain;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGUI extends GuiConfig 
{ 
	 
    public ConfigGUI(GuiScreen parent) 
    {
        super(parent,
                new ConfigElement(ModMain.cfg.instance().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "Sams Powerups", false, false, 
                GuiConfig.getAbridgedConfigPath(ModMain.cfg.instance().toString()));
        
    }
}