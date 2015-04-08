package com.lothrazar.samscontent.proxy;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;   

import  net.minecraft.item.Item;

import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.util.*;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown; 
	public static KeyBinding keyBarUp;
	public static KeyBinding keyBarDown; 
 	public static KeyBinding keyPlayerFlip;

    @Override
    public void registerRenderers() 
    {  
    	keyShiftUp = new KeyBinding(Reference.keyUpName, Keyboard.KEY_C, Reference.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
     
		keyShiftDown = new KeyBinding(Reference.keyDownName, Keyboard.KEY_V, Reference.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 

        keyBarUp = new KeyBinding(Reference.keyBarUpName, Keyboard.KEY_N, Reference.keyCategory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarUp);
         
        keyBarDown = new KeyBinding(Reference.keyBarDownName, Keyboard.KEY_M, Reference.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarDown); 
 
        keyPlayerFlip = new KeyBinding(Reference.keyPlayerFlipName, Keyboard.KEY_R, Reference.keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPlayerFlip); 
      
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
        Item item;
	 
        for(Block b : BlockRegistry.blocks)
        { 
        	item = Item.getItemFromBlock(b);
        	name = Reference.TEXTURE_LOCATION + b.getUnlocalizedName().replaceAll("tile.", "");

   			mesher.register(item, 0, new ModelResourceLocation( name , "inventory"));	 
        }
         
        for(Item i : ItemRegistry.items)
        {  
        	name = Reference.TEXTURE_LOCATION + i.getUnlocalizedName().replaceAll("item.", "");

   			mesher.register(i, 0, new ModelResourceLocation( name , "inventory"));	 
        }
    
         //More info on proxy rendering
         //http://www.minecraftforge.net/forum/index.php?topic=27684.0
        //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
    
    } 
}
