package com.lothrazar.powerinventory.proxy;

import org.lwjgl.input.Keyboard;

import com.lothrazar.powerinventory.EventHandler; 

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
 
public class ClientProxy extends CommonProxy
{
	public static KeyBinding keyEnderPearl;  
	public static KeyBinding keySwapbar;
	public static KeyBinding keyEnderChest;
	public static final String keyEnderName = "key.enderpearl";
	public static final String keySwapbarName = "key.swapbar";
	public static final String keyEnderChestName = "key.enderchest";
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
		
		keyEnderPearl = new KeyBinding(keyEnderName, Keyboard.KEY_Z, keyCategory); 
        ClientRegistry.registerKeyBinding(keyEnderPearl);

        keySwapbar = new KeyBinding(keySwapbarName, Keyboard.KEY_B, keyCategory); 
        ClientRegistry.registerKeyBinding(keySwapbar); 

        keyEnderChest = new KeyBinding(keyEnderChestName, Keyboard.KEY_I, keyCategory); 
        ClientRegistry.registerKeyBinding(keyEnderChest); 
	}
}
