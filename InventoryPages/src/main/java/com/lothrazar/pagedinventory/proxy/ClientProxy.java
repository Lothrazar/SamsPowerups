package com.lothrazar.pagedinventory.proxy;

import org.lwjgl.input.Keyboard;

import com.lothrazar.pagedinventory.EventHandler; 

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
 
public class ClientProxy extends CommonProxy
{
	public static KeyBinding keySwapbar;
  
	public static final String keyCategory = "key.categories.inventory";

	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		

        keySwapbar = new KeyBinding("key.swapbar", Keyboard.KEY_B, keyCategory); 
        ClientRegistry.registerKeyBinding(keySwapbar); 

	}
}
